/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Hashimoto
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SeleniumBuilderContextTest {

	public SeleniumBuilderContextTest() {
		if (_seleniumBuilderContext == null) {
			try {
				_seleniumBuilderContext = new SeleniumBuilderContext(_BASE_DIR);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Test
	public void testAction() throws Exception {
		test("Action.action");
	}

	@Test
	public void testActionCaseElement1010_1() throws Exception {
		test(
			"ActionCaseElement1010_1.action",
			"Error 1010: Invalid locator-key LOCATOR_KEY_1 at " + _DIR_NAME +
				"/ActionCaseElement1010_1.action:3");
	}

	@Test
	public void testActionCaseElement1010_2() throws Exception {
		test(
			"ActionCaseElement1010_2.action",
			"Error 1010: Invalid locator-key FAIL at " + _DIR_NAME +
				"/ActionCaseElement1010_2.action:3");
	}

	@Test
	public void testActionCaseElement1010_3() throws Exception {
		test(
			"ActionCaseElement1010_3.action",
			"Error 1010: Invalid locator-key LOCATOR_ at " + _DIR_NAME +
				"/ActionCaseElement1010_3.action:3");
	}

	@Test
	public void testActionCaseElement1010_4() throws Exception {
		test(
			"ActionCaseElement1010_4.action",
			"Error 1010: Invalid locator-key _KEY at " + _DIR_NAME +
				"/ActionCaseElement1010_4.action:3");
	}

	@Test
	public void testActionCommandElement1009() throws Exception {
		test(
			"ActionCommandElement1009.action",
			"Error 1009: Duplicate command name click at " + _DIR_NAME +
				"/ActionCommandElement1009.action:8");
	}

	@Test
	public void testActionCommandElement2001() throws Exception {
		test(
			"ActionCommandElement2001.action",
			"Error 2001: Action command nameFail does not match a function " +
				"name at " + _DIR_NAME + "/ActionCommandElement2001.action:2");
	}

	@Test
	public void testActionCommandElement2002() throws Exception {
		test(
			"ActionCommandElement2002.action",
			"Error 2002: Missing matching ActionCommandElement2002.path for " +
				_DIR_NAME + "/ActionCommandElement2002.action");
	}

	@Test
	public void testActionExecuteElement1006_1() throws Exception {
		test(
			"ActionExecuteElement1006_1.action",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/ActionExecuteElement1006_1.action:4");
	}

	@Test
	public void testActionExecuteElement1006_2() throws Exception {
		test(
			"ActionExecuteElement1006_2.action",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/ActionExecuteElement1006_2.action:4");
	}

	@Test
	public void testActionExecuteElement1011() throws Exception {
		test(
			"ActionExecuteElement1011.action",
			"Error 1011: Invalid function name ClickX at " + _DIR_NAME +
				"/ActionExecuteElement1011.action:4");
	}

	@Test
	public void testActionExecuteElement1012() throws Exception {
		test(
			"ActionExecuteElement1012.action",
			"Error 1012: Invalid function command clickAtX at " + _DIR_NAME +
				"/ActionExecuteElement1012.action:4");
	}

	@Test
	public void testActionFileName1008() throws Exception {
		test(
			"BaseLiferay.action",
			"Error 1008: Duplicate file name BaseLiferay at " + _DIR_NAME +
				"/BaseLiferay.action");
	}

	@Test
	public void testFunction() throws Exception {
		test("Function.function");
	}

	@Test
	public void testFunctionCommandElement1009() throws Exception {
		test(
			"FunctionCommandElement1009.function",
			"Error 1009: Duplicate command name name at " + _DIR_NAME +
				"/FunctionCommandElement1009.function:6");
	}

	@Test
	public void testFunctionConditionElement1006_5() throws Exception {
		test(
			"FunctionConditionElement1006_5.function",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/FunctionConditionElement1006_5.function:4");
	}

	@Test
	public void testFunctionConditionElement1006_6() throws Exception {
		test(
			"FunctionConditionElement1006_6.function",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/FunctionConditionElement1006_6.function:4");
	}

	@Test
	public void testFunctionConditionElement1011() throws Exception {
		test(
			"FunctionConditionElement1011.function",
			"Error 1011: Invalid function name IsElementPresentX at " +
				_DIR_NAME + "/FunctionConditionElement1011.function:4");
	}

	@Test
	public void testFunctionConditionElement1012_1() throws Exception {
		test(
			"FunctionConditionElement1012_1.function",
			"Error 1012: Invalid function command isElementPresentX at " +
				_DIR_NAME + "/FunctionConditionElement1012_1.function:4");
	}

	@Test
	public void testFunctionConditionElement1012_2() throws Exception {
		test(
			"FunctionConditionElement1012_2.function",
			"Error 1012: Invalid selenium command isElementPresentX at " +
				_DIR_NAME + "/FunctionConditionElement1012_2.function:4");
	}

	@Test
	public void testFunctionExecuteElement1006_3() throws Exception {
		test(
			"FunctionExecuteElement1006_3.function",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/FunctionExecuteElement1006_3.function:3");
	}

	@Test
	public void testFunctionExecuteElement1006_4() throws Exception {
		test(
			"FunctionExecuteElement1006_4.function",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/FunctionExecuteElement1006_4.function:3");
	}

	@Test
	public void testFunctionExecuteElement1011() throws Exception {
		test(
			"FunctionExecuteElement1011.function",
			"Error 1011: Invalid function name ClickX at " + _DIR_NAME +
				"/FunctionExecuteElement1011.function:3");
	}

	@Test
	public void testFunctionExecuteElement1012_1() throws Exception {
		test(
			"FunctionExecuteElement1012_1.function",
			"Error 1012: Invalid function command clickAtX at " + _DIR_NAME +
				"/FunctionExecuteElement1012_1.function:3");
	}

	@Test
	public void testFunctionExecuteElement1012_2() throws Exception {
		test(
			"FunctionExecuteElement1012_2.function",
			"Error 1012: Invalid selenium command clickX at " + _DIR_NAME +
				"/FunctionExecuteElement1012_2.function:3");
	}

	@Test
	public void testFunctionFileName1008() throws Exception {
		test(
			"Click.function",
			"Error 1008: Duplicate file name Click at " + _DIR_NAME +
				"/Click.function");
	}

	@Test
	public void testMacro() throws Exception {
		test("Macro.macro");
	}

	@Test
	public void testMacroCommandElement1009() throws Exception {
		test(
			"MacroCommandElement1009.macro",
			"Error 1009: Duplicate command name name at " + _DIR_NAME +
				"/MacroCommandElement1009.macro:6");
	}

	@Test
	public void testMacroConditionElement1006_5() throws Exception {
		test(
			"MacroConditionElement1006_5.macro",
			"Error 1006: Invalid action attribute value in " +
				_DIR_NAME + "/MacroConditionElement1006_5.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_6() throws Exception {
		test(
			"MacroConditionElement1006_6.macro",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_6.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_7() throws Exception {
		test(
			"MacroConditionElement1006_7.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_7.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_8() throws Exception {
		test(
			"MacroConditionElement1006_8.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_8.macro:4");
	}

	@Test
	public void testMacroConditionElement1010() throws Exception {
		test(
			"MacroConditionElement1010.macro",
			"Error 1010: Invalid locator-key PAGE_NAME_X at " + _DIR_NAME +
				"/MacroConditionElement1010.macro:4");
	}

	@Test
	public void testMacroConditionElement1011_1() throws Exception {
		test(
			"MacroConditionElement1011_1.macro",
			"Error 1011: Invalid action name BaseLiferays at " + _DIR_NAME +
				"/MacroConditionElement1011_1.macro:4");
	}

	@Test
	public void testMacroConditionElement1011_2() throws Exception {
		test(
			"MacroConditionElement1011_2.macro",
			"Error 1011: Invalid macro name Pages at " + _DIR_NAME +
				"/MacroConditionElement1011_2.macro:4");
	}

	@Test
	public void testMacroConditionElement1012_1() throws Exception {
		test(
			"MacroConditionElement1012_1.macro",
			"Error 1012: Invalid action command isElementPresents at " +
				_DIR_NAME + "/MacroConditionElement1012_1.macro:4");
	}

	@Test
	public void testMacroConditionElement1012_2() throws Exception {
		test(
			"MacroConditionElement1012_2.macro",
			"Error 1012: Invalid macro command isPresents at " + _DIR_NAME +
				"/MacroConditionElement1012_2.macro:4");
	}

	@Test
	public void testMacroExecuteElement1006_3() throws Exception {
		test(
			"MacroExecuteElement1006_3.macro",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_3.macro:3");
	}

	@Test
	public void testMacroExecuteElement1006_4() throws Exception {
		test(
			"MacroExecuteElement1006_4.macro",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_4.macro:3");
	}

	@Test
	public void testMacroExecuteElement1006_5() throws Exception {
		test(
			"MacroExecuteElement1006_5.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_5.macro:3");
	}

	@Test
	public void testMacroExecuteElement1006_6() throws Exception {
		test(
			"MacroExecuteElement1006_6.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_6.macro:3");
	}

	@Test
	public void testMacroExecuteElement1010() throws Exception {
		test(
			"MacroExecuteElement1010.macro",
			"Error 1010: Invalid locator-key PAGE_NAME_X at " + _DIR_NAME +
				"/MacroExecuteElement1010.macro:3");
	}

	@Test
	public void testMacroExecuteElement1011_1() throws Exception {
		test(
			"MacroExecuteElement1011_1.macro",
			"Error 1011: Invalid action name BaseLiferays at " + _DIR_NAME +
				"/MacroExecuteElement1011_1.macro:3");
	}

	@Test
	public void testMacroExecuteElement1011_2() throws Exception {
		test(
			"MacroExecuteElement1011_2.macro",
			"Error 1011: Invalid macro name BlogsEntrys at " + _DIR_NAME +
				"/MacroExecuteElement1011_2.macro:3");
	}

	@Test
	public void testMacroExecuteElement1012_1() throws Exception {
		test(
			"MacroExecuteElement1012_1.macro",
			"Error 1012: Invalid action command clicks at " + _DIR_NAME +
				"/MacroExecuteElement1012_1.macro:3");
	}

	@Test
	public void testMacroExecuteElement1012_2() throws Exception {
		test(
			"MacroExecuteElement1012_2.macro",
			"Error 1012: Invalid macro command pgAdder at " + _DIR_NAME +
				"/MacroExecuteElement1012_2.macro:3");
	}

	@Test
	public void testMacroFileName1008() throws Exception {
		test(
			"BlogsEntry.macro",
			"Error 1008: Duplicate file name BlogsEntry at " + _DIR_NAME +
				"/BlogsEntry.macro");
	}

	@Test
	public void testTestCase() throws Exception {
		test("TestCase.testcase");
	}

	@Test
	public void testTestCaseCommandElement1009() throws Exception {
		test(
			"TestCaseCommandElement1009.testcase",
			"Error 1009: Duplicate command name name at " + _DIR_NAME +
				"/TestCaseCommandElement1009.testcase:6");
	}

	@Test
	public void testTestCaseExecuteElement1006_3() throws Exception {
		test(
			"TestCaseExecuteElement1006_3.testcase",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_3.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1006_4() throws Exception {
		test(
			"TestCaseExecuteElement1006_4.testcase",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_4.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1006_5() throws Exception {
		test(
			"TestCaseExecuteElement1006_5.testcase",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_5.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1006_6() throws Exception {
		test(
			"TestCaseExecuteElement1006_6.testcase",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_6.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1010() throws Exception {
		test(
			"TestCaseExecuteElement1010.testcase",
			"Error 1010: Invalid locator-key PAGE_NAME_X at " + _DIR_NAME +
				"/TestCaseExecuteElement1010.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1011_1() throws Exception {
		test(
			"TestCaseExecuteElement1011_1.testcase",
			"Error 1011: Invalid action name BaseLiferays at " + _DIR_NAME +
				"/TestCaseExecuteElement1011_1.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1011_2() throws Exception {
		test(
			"TestCaseExecuteElement1011_2.testcase",
			"Error 1011: Invalid macro name BlogsEntrys at " + _DIR_NAME +
				"/TestCaseExecuteElement1011_2.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1012_1() throws Exception {
		test(
			"TestCaseExecuteElement1012_1.testcase",
			"Error 1012: Invalid action command clicks at " + _DIR_NAME +
				"/TestCaseExecuteElement1012_1.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1012_2() throws Exception {
		test(
			"TestCaseExecuteElement1012_2.testcase",
			"Error 1012: Invalid macro command pgAdder at " + _DIR_NAME +
				"/TestCaseExecuteElement1012_2.testcase:3");
	}

	@Test
	public void testTestCaseFileName1008() throws Exception {
		test(
			"CPBlogsAcceptance.testcase",
			"Error 1008: Duplicate file name CPBlogsAcceptance at " +
				_DIR_NAME + "/CPBlogsAcceptance.testcase");
	}

	@Test
	public void testTestSuite() throws Exception {
		test("TestSuite.testsuite");
	}

	@Test
	public void testTestSuiteExecuteElement1011_1() throws Exception {
		test(
			"TestSuiteExecuteElement1011_1.testsuite",
			"Error 1011: Invalid test-case name test-case-fail at " +
				_DIR_NAME + "/TestSuiteExecuteElement1011_1.testsuite:2");
	}

	@Test
	public void testTestSuiteExecuteElement1011_2() throws Exception {
		test(
			"TestSuiteExecuteElement1011_2.testsuite",
			"Error 1011: Invalid test-suite name test-suite-fail at " +
				_DIR_NAME + "/TestSuiteExecuteElement1011_2.testsuite:2");
	}

	@Test
	public void testTestSuiteFileName1008() throws Exception {
		test(
			"EndUserCollaborationAcceptance.testsuite",
			"Error 1008: Duplicate file name EndUserCollaborationAcceptance " +
				"at " + _DIR_NAME +
				"/EndUserCollaborationAcceptance.testsuite");
	}

	protected void test(String fileName) throws Exception {
		test(fileName, null, false);
	}

	protected void test(String fileName, String expectedErrorMessage)
		throws Exception {

		test(fileName, expectedErrorMessage, true);
	}

	protected void test(
			String fileName, String expectedErrorMessage,
			boolean expectException)
		throws Exception {

		String actualErrorMessage = null;

		try {
			if (fileName.endsWith(".action")) {
				String pathFileName = StringUtil.replace(
					fileName, ".action", ".path");

				if (FileUtil.exists(
						_BASE_DIR + _DIR_NAME + "/" + pathFileName)) {

					_seleniumBuilderContext.addFile(
						_DIR_NAME + "/" + pathFileName);
				}
			}

			_seleniumBuilderContext.addFile(_DIR_NAME + "/" + fileName);

			_seleniumBuilderContext.validateElements(
				_DIR_NAME + "/" + fileName);
		}
		catch (IllegalArgumentException iae) {
			actualErrorMessage = iae.getMessage();
		}
		finally {
			if (expectException) {
				Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
			}
			else {
				Assert.assertEquals(null, actualErrorMessage);
			}
		}
	}

	private static final String _BASE_DIR = "./portal-web/test/functional/";

	private static final String _DIR_NAME =
		"/../../../portal-impl/test/integration/com/liferay/portal/tools/" +
			"seleniumbuilder/dependencies";

	private static Log _log = LogFactoryUtil.getLog(
		SeleniumBuilderContextTest.class);

	private static SeleniumBuilderContext _seleniumBuilderContext;

}