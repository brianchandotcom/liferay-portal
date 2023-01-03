/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.poshi.runner;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Calum Ragan
 */
public class PoshiRunnerFunctionalTest extends PoshiRunnerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		setUpPoshiRunner(_TEST_BASE_DIR_NAME);
	}

	@Test
	public void testExternalMethodExecuteMethodStringArguments()
		throws Exception {

		runPoshiTest("ExternalMethod#ExecuteMethodStringArguments");
	}

	@Test
	public void testExternalMethodExecuteMethodVariableArguments()
		throws Exception {

		runPoshiTest("ExternalMethod#ExecuteMethodVariableArguments");
	}

	@Test
	public void testExternalMethodExecuteMethodVariableReturn()
		throws Exception {

		runPoshiTest("ExternalMethod#ExecuteMethodVariableReturn");
	}

	@Test
	public void testJSONCurlUtilAvailablePhysicalMemory() throws Exception {
		runPoshiTest("JSONCurlUtil#AvailablePhysicalMemory");
	}

	@Test
	public void testJSONCurlUtilBusyExecutors() throws Exception {
		runPoshiTest("JSONCurlUtil#BusyExecutors");
	}

	@Test
	public void testJSONCurlUtilDisplayName() throws Exception {
		runPoshiTest("JSONCurlUtil#DisplayName");
	}

	@Test
	public void testReturnInMacro() throws Exception {
		runPoshiTest("Return#ReturnInMacro");
	}

	@Test
	public void testReturnInTestCase() throws Exception {
		runPoshiTest("Return#ReturnInTestCase");
	}

	@Test
	public void testStaticVariables1() throws Exception {
		runPoshiTest("StaticVariables#StaticVariables1");
	}

	@Test
	public void testStaticVariables2() throws Exception {
		runPoshiTest("StaticVariables#StaticVariables2");
	}

	private static final String _TEST_BASE_DIR_NAME = "src/testFunctional";

}