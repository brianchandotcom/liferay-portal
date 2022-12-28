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
public class PoshiTestFunctionalTest extends PoshiRunnerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		setUpPoshiRunner(_TEST_BASE_DIR_NAME);
	}

	@Test
	public void testExternalMethod() throws Exception {
		runPoshiTest("ExternalMethod#ExecuteMethodStringArguments");

		runPoshiTest("ExternalMethod#ExecuteMethodVariableArguments");

		runPoshiTest("ExternalMethod#ExecuteMethodVariableReturn");
	}

	@Test
	public void testJSONCurlUtil() throws Exception {
		runPoshiTest("JSONCurlUtil#AvailablePhysicalMemory");

		runPoshiTest("JSONCurlUtil#BusyExecutors");

		runPoshiTest("JSONCurlUtil#DisplayName");
	}

	@Test
	public void testReturn() throws Exception {
		runPoshiTest("Return#ReturnInMacro");

		runPoshiTest("Return#ReturnInTestCase");
	}

	@Test
	public void testStaticVariables() throws Exception {
		runPoshiTest("StaticVariables#StaticVariables1");

		runPoshiTest("StaticVariables#StaticVariables2");
	}

	private static final String _TEST_BASE_DIR_NAME = "src/testFunctional";

}