 /*
  * @(#)NetContainerTest.java   
  *
  * Copyright (C) 2006-2014 www.interpss.org
  *
  * This program is free software; you can redistribute it and/or
  * modify it under the terms of the GNU LESSER GENERAL PUBLIC LICENSE
  * as published by the Free Software Foundation; either version 2.1
  * of the License, or (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * @Author Mike Zhou
  * @Version 1.0
  * @Date 07/15/2014
  * 
  *   Revision History
  *   ================
  *
  */

package test;

import static org.interpss.pssl.plugin.IpssAdapter.FileFormat.IEEECommonFormat;
import static org.junit.Assert.*;

import org.interpss.IpssCorePlugin;
import org.interpss.pssl.plugin.IpssAdapter;
import org.interpss.service.train_data.ITrainCaseBuilder;
import org.interpss.service.train_data.TrainDataBuilderFactory;
import org.interpss.service.train_data.aclf.load_change.BusVoltLoadChangeTrainCaseBuilder;
import org.junit.Test;

import com.interpss.common.exp.InterpssException;
import com.interpss.core.aclf.AclfNetwork;
import com.interpss.core.datatype.Mismatch;

public class AclfFuncTest {
  	@Test 
	public void test() {
  		BusVoltLoadChangeTrainCaseBuilder caseBuilder = (BusVoltLoadChangeTrainCaseBuilder)loadCase("testdata/ieee14.ieee");
  		 
  		caseBuilder.createTestCase();
  		
  		double[] netVolt = caseBuilder.getNetOutput();
  		
  		Mismatch mis = caseBuilder.calMismatch(netVolt);
  		assertTrue("", mis.maxMis.abs() < 0.0001);
  		//System.out.println(caseBuilder.calMismatch(netVolt));
   	}
  	
  	private ITrainCaseBuilder loadCase(String filename) {
		IpssCorePlugin.init();
		
		try {
			AclfNetwork aclfNet = IpssAdapter.importAclfNet(filename)
					.setFormat(IEEECommonFormat)
					.load()
					.getImportedObj();
			
			System.out.println(filename + " aclfNet case loaded");
			return TrainDataBuilderFactory.createITrainCaseBuilder(aclfNet, "BusVoltageTrainCaseBuilder");
		} catch ( InterpssException e) {
			e.printStackTrace();
		}

		return null;
	}
}

