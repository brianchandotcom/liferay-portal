/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.var.scope;

import com.liferay.poshi.runner.PoshiRunnerTestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class VariableScopeTest extends PoshiRunnerTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		setUpPoshiRunner(_TEST_BASE_DIR_NAME);
	}

	@Test
	public void testAssertExecuteVarInheritance() throws Exception {
		runPoshiTest("VariableScope#ExecuteVarInheritance");
	}

	@Test
	public void testAssertRootVarInheritance() throws Exception {
		runPoshiTest("VariableScope#RootVarInheritance");
	}

	@Test
	public void testAssertStaticMacroVarInheritance() throws Exception {
		runPoshiTest("VariableScope#MacroStaticVarInheritance");
	}

	@Test
	public void testAssertStaticTestVarInheritance() throws Exception {
		runPoshiTest("VariableScope#TestCaseStaticVarInheritance");
	}

	private static final String _TEST_BASE_DIR_NAME =
		"src/test/resources/com/liferay/poshi/runner/dependencies/var/scope";

}