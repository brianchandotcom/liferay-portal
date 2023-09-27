/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void testEscapeRegexChars() throws Exception {
		runPoshiTest("RegexUtil#EscapeRegexChars");
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