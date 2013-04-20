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

import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Hashimoto
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SeleniumBuilderTest {

	@Before
	public void setUp() throws Exception {
		_seleniumBuilderFileUtil = new SeleniumBuilderFileUtil(".");
	}

	@Test
	public void testAction() throws Exception {
		test("Action.action");
	}

	@Test
	public void testActionCaseElement1001() throws Exception {
		test(
			"ActionCaseElement1001.action",
			"Error 1001: Missing (execute) child element in " + _DIR_NAME +
				"/ActionCaseElement1001.action:3");
	}

	@Test
	public void testActionCaseElement1002() throws Exception {
		test(
			"ActionCaseElement1002.action",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/ActionCaseElement1002.action:4");
	}

	@Test
	public void testActionCaseElement1004_1() throws Exception {
		test(
			"ActionCaseElement1004_1.action",
			"Error 1004: Missing (locator1|locator-key1) attribute in " +
				_DIR_NAME + "/ActionCaseElement1004_1.action:3");
	}

	@Test
	public void testActionCaseElement1004_2() throws Exception {
		test(
			"ActionCaseElement1004_2.action",
			"Error 1004: Missing (locator1|locator-key1) attribute in " +
				_DIR_NAME + "/ActionCaseElement1004_2.action:3");
	}

	@Test
	public void testActionCaseElement1005() throws Exception {
		test(
			"ActionCaseElement1005.action",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/ActionCaseElement1005.action:3");
	}

	@Test
	public void testActionCaseElement1006_1() throws Exception {
		test(
			"ActionCaseElement1006_1.action",
			"Error 1006: Invalid locator1 attribute value in " + _DIR_NAME +
				"/ActionCaseElement1006_1.action:3");
	}

	@Test
	public void testActionCaseElement1006_2() throws Exception {
		test(
			"ActionCaseElement1006_2.action",
			"Error 1006: Invalid locator-key1 attribute value in " + _DIR_NAME +
				"/ActionCaseElement1006_2.action:3");
	}

	@Test
	public void testActionCaseElement1006_3() throws Exception {
		test(
			"ActionCaseElement1006_3.action",
			"Error 1006: Invalid comparator attribute value in " + _DIR_NAME +
				"/ActionCaseElement1006_3.action:3");
	}

	@Test
	public void testActionCaseElement1006_4() throws Exception {
		test(
			"ActionCaseElement1006_4.action",
			"Error 1006: Invalid comparator attribute value in " + _DIR_NAME +
				"/ActionCaseElement1006_4.action:3");
	}

	@Test
	public void testActionCaseElement1007() throws Exception {
		test(
			"ActionCaseElement1007.action",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/ActionCaseElement1007.action");
	}

	@Test
	public void testActionCaseElement2000() throws Exception {
		test(
			"ActionCaseElement2000.action",
			"Error 2000: Too many child elements in the case element in " +
				_DIR_NAME + "/ActionCaseElement2000.action:5");
	}

	@Test
	public void testActionCommandElement1001() throws Exception {
		test(
			"ActionCommandElement1001.action",
			"Error 1001: Missing (case|default) child element in " + _DIR_NAME +
				"/ActionCommandElement1001.action:2");
	}

	@Test
	public void testActionCommandElement1002() throws Exception {
		test(
			"ActionCommandElement1002.action",
			"Error 1002: Invalid case-fail element in " + _DIR_NAME +
				"/ActionCommandElement1002.action:3");
	}

	@Test
	public void testActionCommandElement1003_1() throws Exception {
		test(
			"ActionCommandElement1003_1.action",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/ActionCommandElement1003_1.action:2");
	}

	@Test
	public void testActionCommandElement1003_2() throws Exception {
		test(
			"ActionCommandElement1003_2.action",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/ActionCommandElement1003_2.action:2");
	}

	@Test
	public void testActionCommandElement1006() throws Exception {
		test(
			"ActionCommandElement1006.action",
			"Error 1006: Invalid name attribute value in " + _DIR_NAME +
				"/ActionCommandElement1006.action:2");
	}

	@Test
	public void testActionCommandElement1007() throws Exception {
		test(
			"ActionCommandElement1007.action",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/ActionCommandElement1007.action");
	}

	@Test
	public void testActionDefaultElement1001() throws Exception {
		test(
			"ActionDefaultElement1001.action",
			"Error 1001: Missing (execute) child element in " + _DIR_NAME +
				"/ActionDefaultElement1001.action:3");
	}

	@Test
	public void testActionDefaultElement1002() throws Exception {
		test(
			"ActionDefaultElement1002.action",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/ActionDefaultElement1002.action:4");
	}

	@Test
	public void testActionDefaultElement1005() throws Exception {
		test(
			"ActionDefaultElement1005.action",
			"Error 1005: Invalid locator1 attribute in " + _DIR_NAME +
				"/ActionDefaultElement1005.action:3");
	}

	@Test
	public void testActionDefaultElement1007() throws Exception {
		test(
			"ActionDefaultElement1007.action",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/ActionDefaultElement1007.action");
	}

	@Test
	public void testActionDefaultElement2000() throws Exception {
		test(
			"ActionDefaultElement2000.action",
			"Error 2000: Too many child elements in the default element in " +
				_DIR_NAME + "/ActionDefaultElement2000.action:5");
	}

	@Test
	public void testActionDefinitionElement1000() throws Exception {
		test(
			"ActionDefinitionElement1000.action",
			"Error 1000: Invalid root element in " + _DIR_NAME +
				"/ActionDefinitionElement1000.action:1");
	}

	@Test
	public void testActionDefinitionElement1001() throws Exception {
		test(
			"ActionDefinitionElement1001.action",
			"Error 1001: Missing (command) child element in " + _DIR_NAME +
				"/ActionDefinitionElement1001.action:1");
	}

	@Test
	public void testActionDefinitionElement1002() throws Exception {
		test(
			"ActionDefinitionElement1002.action",
			"Error 1002: Invalid command-fail element in " + _DIR_NAME +
				"/ActionDefinitionElement1002.action:2");
	}

	@Test
	public void testActionDefinitionElement1007() throws Exception {
		test(
			"ActionDefinitionElement1007.action",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/ActionDefinitionElement1007.action");
	}

	@Test
	public void testActionExecuteElement1002() throws Exception {
		test(
			"ActionExecuteElement1002.action",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/ActionExecuteElement1002.action:5");
	}

	@Test
	public void testActionExecuteElement1004_1() throws Exception {
		test(
			"ActionExecuteElement1004_1.action",
			"Error 1004: Missing (function) attribute in " + _DIR_NAME +
				"/ActionExecuteElement1004_1.action:4");
	}

	@Test
	public void testActionExecuteElement1004_2() throws Exception {
		test(
			"ActionExecuteElement1004_2.action",
			"Error 1004: Missing (function) attribute in " + _DIR_NAME +
				"/ActionExecuteElement1004_2.action:4");
	}

	@Test
	public void testActionExecuteElement1004_3() throws Exception {
		test(
			"ActionExecuteElement1004_3.action",
			"Error 1004: Missing (function) attribute in " + _DIR_NAME +
				"/ActionExecuteElement1004_3.action:4");
	}

	@Test
	public void testActionExecuteElement1005_1() throws Exception {
		test(
			"ActionExecuteElement1005_1.action",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/ActionExecuteElement1005_1.action:4");
	}

	@Test
	public void testActionExecuteElement1005_2() throws Exception {
		test(
			"ActionExecuteElement1005_2.action",
			"Error 1005: Invalid locator attribute in " + _DIR_NAME +
				"/ActionExecuteElement1005_2.action:4");
	}

	@Test
	public void testActionExecuteElement1005_3() throws Exception {
		test(
			"ActionExecuteElement1005_3.action",
			"Error 1005: Invalid value attribute in " + _DIR_NAME +
				"/ActionExecuteElement1005_3.action:4");
	}

	@Test
	public void testActionExecuteElement1006() throws Exception {
		test(
			"ActionExecuteElement1006.action",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/ActionExecuteElement1006.action:4");
	}

	@Test
	public void testActionExecuteElement1007() throws Exception {
		test(
			"ActionExecuteElement1007.action",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/ActionExecuteElement1007.action");
	}

	@Test
	public void testFunction() throws Exception {
		test("Function.function");
	}

	@Test
	public void testFunctionCommandElement1001() throws Exception {
		test(
			"FunctionCommandElement1001.function",
			"Error 1001: Missing (execute|if) child element in " + _DIR_NAME +
				"/FunctionCommandElement1001.function:2");
	}

	@Test
	public void testFunctionCommandElement1002() throws Exception {
		test(
			"FunctionCommandElement1002.function",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/FunctionCommandElement1002.function:3");
	}

	@Test
	public void testFunctionCommandElement1003_1() throws Exception {
		test(
			"FunctionCommandElement1003_1.function",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/FunctionCommandElement1003_1.function:2");
	}

	@Test
	public void testFunctionCommandElement1003_2() throws Exception {
		test(
			"FunctionCommandElement1003_2.function",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/FunctionCommandElement1003_2.function:2");
	}

	@Test
	public void testFunctionCommandElement1006() throws Exception {
		test(
			"FunctionCommandElement1006.function",
			"Error 1006: Invalid name attribute value in " + _DIR_NAME +
				"/FunctionCommandElement1006.function:2");
	}

	@Test
	public void testFunctionCommandElement1007() throws Exception {
		test(
			"FunctionCommandElement1007.function",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/FunctionCommandElement1007.function");
	}

	@Test
	public void testFunctionConditionElement1002() throws Exception {
		test(
			"FunctionConditionElement1002.function",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/FunctionConditionElement1002.function:5");
	}

	@Test
	public void testFunctionConditionElement1004_1() throws Exception {
		test(
			"FunctionConditionElement1004_1.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionConditionElement1004_1.function:4");
	}

	@Test
	public void testFunctionConditionElement1004_2() throws Exception {
		test(
			"FunctionConditionElement1004_2.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionConditionElement1004_2.function:4");
	}

	@Test
	public void testFunctionConditionElement1004_3() throws Exception {
		test(
			"FunctionConditionElement1004_3.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionConditionElement1004_3.function:4");
	}

	@Test
	public void testFunctionConditionElement1004_4() throws Exception {
		test(
			"FunctionConditionElement1004_4.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionConditionElement1004_4.function:4");
	}

	@Test
	public void testFunctionConditionElement1005_1() throws Exception {
		test(
			"FunctionConditionElement1005_1.function",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/FunctionConditionElement1005_1.function:4");
	}

	@Test
	public void testFunctionConditionElement1005_2() throws Exception {
		test(
			"FunctionConditionElement1005_2.function",
			"Error 1005: Invalid locator attribute in " + _DIR_NAME +
				"/FunctionConditionElement1005_2.function:4");
	}

	@Test
	public void testFunctionConditionElement1005_3() throws Exception {
		test(
			"FunctionConditionElement1005_3.function",
			"Error 1005: Invalid value attribute in " + _DIR_NAME +
				"/FunctionConditionElement1005_3.function:4");
	}

	@Test
	public void testFunctionConditionElement1005_4() throws Exception {
		test(
			"FunctionConditionElement1005_4.function",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/FunctionConditionElement1005_4.function:4");
	}

	@Test
	public void testFunctionConditionElement1005_5() throws Exception {
		test(
			"FunctionConditionElement1005_5.function",
			"Error 1005: Invalid argument attribute in " + _DIR_NAME +
				"/FunctionConditionElement1005_5.function:4");
	}

	@Test
	public void testFunctionConditionElement1006_1() throws Exception {
		test(
			"FunctionConditionElement1006_1.function",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/FunctionConditionElement1006_1.function:4");
	}

	@Test
	public void testFunctionConditionElement1006_2() throws Exception {
		test(
			"FunctionConditionElement1006_2.function",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/FunctionConditionElement1006_2.function:4");
	}

	@Test
	public void testFunctionConditionElement1006_3() throws Exception {
		test(
			"FunctionConditionElement1006_3.function",
			"Error 1006: Invalid selenium attribute value in " + _DIR_NAME +
				"/FunctionConditionElement1006_3.function:4");
	}

	@Test
	public void testFunctionConditionElement1006_4() throws Exception {
		test(
			"FunctionConditionElement1006_4.function",
			"Error 1006: Invalid selenium attribute value in " + _DIR_NAME +
				"/FunctionConditionElement1006_4.function:4");
	}

	@Test
	public void testFunctionConditionElement1007() throws Exception {
		test(
			"FunctionConditionElement1007.function",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/FunctionConditionElement1007.function");
	}

	@Test
	public void testFunctionDefinitionElement1000() throws Exception {
		test(
			"FunctionDefinitionElement1000.function",
			"Error 1000: Invalid root element in " + _DIR_NAME +
				"/FunctionDefinitionElement1000.function:1");
	}

	@Test
	public void testFunctionDefinitionElement1001() throws Exception {
		test(
			"FunctionDefinitionElement1001.function",
			"Error 1001: Missing (command) child element in " + _DIR_NAME +
				"/FunctionDefinitionElement1001.function:1");
	}

	@Test
	public void testFunctionDefinitionElement1002() throws Exception {
		test(
			"FunctionDefinitionElement1002.function",
			"Error 1002: Invalid command-fail element in " + _DIR_NAME +
				"/FunctionDefinitionElement1002.function:2");
	}

	@Test
	public void testFunctionDefinitionElement1007() throws Exception {
		test(
			"FunctionDefinitionElement1007.function",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/FunctionDefinitionElement1007.function");
	}

	@Test
	public void testFunctionElseElement1001() throws Exception {
		test(
			"FunctionElseElement1001.function",
			"Error 1001: Missing (execute|if) child element in " + _DIR_NAME +
				"/FunctionElseElement1001.function:8");
	}

	@Test
	public void testFunctionElseElement1002() throws Exception {
		test(
			"FunctionElseElement1002.function",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/FunctionElseElement1002.function:9");
	}

	@Test
	public void testFunctionElseElement1007() throws Exception {
		test(
			"FunctionElseElement1007.function",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/FunctionElseElement1007.function");
	}

	@Test
	public void testFunctionExecuteElement1002() throws Exception {
		test(
			"FunctionExecuteElement1002.function",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/FunctionExecuteElement1002.function:4");
	}

	@Test
	public void testFunctionExecuteElement1004_1() throws Exception {
		test(
			"FunctionExecuteElement1004_1.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1004_1.function:3");
	}

	@Test
	public void testFunctionExecuteElement1004_2() throws Exception {
		test(
			"FunctionExecuteElement1004_2.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1004_2.function:3");
	}

	@Test
	public void testFunctionExecuteElement1004_3() throws Exception {
		test(
			"FunctionExecuteElement1004_3.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1004_3.function:3");
	}

	@Test
	public void testFunctionExecuteElement1004_4() throws Exception {
		test(
			"FunctionExecuteElement1004_4.function",
			"Error 1004: Missing (function|selenium) attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1004_4.function:3");
	}

	@Test
	public void testFunctionExecuteElement1005_1() throws Exception {
		test(
			"FunctionExecuteElement1005_1.function",
			"Error 1005: Invalid fail attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1005_1.function:3");
	}

	@Test
	public void testFunctionExecuteElement1005_2() throws Exception {
		test(
			"FunctionExecuteElement1005_2.function",
			"Error 1005: Invalid locator attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1005_2.function:3");
	}

	@Test
	public void testFunctionExecuteElement1005_3() throws Exception {
		test(
			"FunctionExecuteElement1005_3.function",
			"Error 1005: Invalid value attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1005_3.function:3");
	}

	@Test
	public void testFunctionExecuteElement1005_4() throws Exception {
		test(
			"FunctionExecuteElement1005_4.function",
			"Error 1005: Invalid fail attribute in " +
				_DIR_NAME + "/FunctionExecuteElement1005_4.function:3");
	}

	@Test
	public void testFunctionExecuteElement1006_1() throws Exception {
		test(
			"FunctionExecuteElement1006_1.function",
			"Error 1006: Invalid function attribute value in " + _DIR_NAME +
				"/FunctionExecuteElement1006_1.function:3");
	}

	@Test
	public void testFunctionExecuteElement1006_2() throws Exception {
		test(
			"FunctionExecuteElement1006_2.function",
			"Error 1006: Invalid selenium attribute value in " + _DIR_NAME +
				"/FunctionExecuteElement1006_2.function:3");
	}

	@Test
	public void testFunctionExecuteElement1007() throws Exception {
		test(
			"FunctionExecuteElement1007.function",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/FunctionExecuteElement1007.function");
	}

	@Test
	public void testFunctionIfElement1001_1() throws Exception {
		test(
			"FunctionIfElement1001_1.function",
			"Error 1001: Missing (condition) child element in " + _DIR_NAME +
				"/FunctionIfElement1001_1.function:3");
	}

	@Test
	public void testFunctionIfElement1001_2() throws Exception {
		test(
			"FunctionIfElement1001_2.function",
			"Error 1001: Missing (then) child element in " + _DIR_NAME +
				"/FunctionIfElement1001_2.function:3");
	}

	@Test
	public void testFunctionIfElement1001_3() throws Exception {
		test(
			"FunctionIfElement1001_3.function",
			"Error 1001: Missing (condition) child element in " + _DIR_NAME +
				"/FunctionIfElement1001_3.function:3");
	}

	@Test
	public void testFunctionIfElement1001_4() throws Exception {
		test(
			"FunctionIfElement1001_4.function",
			"Error 1001: Missing (condition) child element in " + _DIR_NAME +
				"/FunctionIfElement1001_4.function:3");
	}

	@Test
	public void testFunctionIfElement1002() throws Exception {
		test(
			"FunctionIfElement1002.function",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/FunctionIfElement1002.function:11");
	}

	@Test
	public void testFunctionIfElement1007() throws Exception {
		test(
			"FunctionIfElement1007.function",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/FunctionIfElement1007.function");
	}

	@Test
	public void testFunctionThenElement1001() throws Exception {
		test(
			"FunctionThenElement1001.function",
			"Error 1001: Missing (execute|if) child element in " + _DIR_NAME +
				"/FunctionThenElement1001.function:5");
	}

	@Test
	public void testFunctionThenElement1002() throws Exception {
		test(
			"FunctionThenElement1002.function",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/FunctionThenElement1002.function:6");
	}

	@Test
	public void testFunctionThenElement1007() throws Exception {
		test(
			"FunctionThenElement1007.function",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/FunctionThenElement1007.function");
	}

	@Test
	public void testMacro() throws Exception {
		test("Macro.macro");
	}

	@Test
	public void testMacroCommandElement1001() throws Exception {
		test(
			"MacroCommandElement1001.macro",
			"Error 1001: Missing (echo|execute|fail|if|var|while) child " +
				"element in " + _DIR_NAME + "/MacroCommandElement1001.macro:2");
	}

	@Test
	public void testMacroCommandElement1002() throws Exception {
		test(
			"MacroCommandElement1002.macro",
			"Error 1002: Invalid execute-fail element in " + _DIR_NAME +
				"/MacroCommandElement1002.macro:3");
	}

	@Test
	public void testMacroCommandElement1003_1() throws Exception {
		test(
			"MacroCommandElement1003_1.macro",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/MacroCommandElement1003_1.macro:2");
	}

	@Test
	public void testMacroCommandElement1003_2() throws Exception {
		test(
			"MacroCommandElement1003_2.macro",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/MacroCommandElement1003_2.macro:2");
	}

	@Test
	public void testMacroCommandElement1006() throws Exception {
		test(
			"MacroCommandElement1006.macro",
			"Error 1006: Invalid name attribute value in " + _DIR_NAME +
				"/MacroCommandElement1006.macro:2");
	}

	@Test
	public void testMacroCommandElement1007() throws Exception {
		test(
			"MacroCommandElement1007.macro",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/MacroCommandElement1007.macro");
	}

	@Test
	public void testMacroConditionElement1002_1() throws Exception {
		test(
			"MacroConditionElement1002_1.macro",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/MacroConditionElement1002_1.macro:5");
	}

	@Test
	public void testMacroConditionElement1002_2() throws Exception {
		test(
			"MacroConditionElement1002_2.macro",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/MacroConditionElement1002_2.macro:5");
	}

	@Test
	public void testMacroConditionElement1004_1() throws Exception {
		test(
			"MacroConditionElement1004_1.macro",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/MacroConditionElement1004_1.macro:4");
	}

	@Test
	public void testMacroConditionElement1004_2() throws Exception {
		test(
			"MacroConditionElement1004_2.macro",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/MacroConditionElement1004_2.macro:4");
	}

	@Test
	public void testMacroConditionElement1004_3() throws Exception {
		test(
			"MacroConditionElement1004_3.macro",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/MacroConditionElement1004_3.macro:4");
	}

	@Test
	public void testMacroConditionElement1004_4() throws Exception {
		test(
			"MacroConditionElement1004_4.macro",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/MacroConditionElement1004_4.macro:4");
	}

	@Test
	public void testMacroConditionElement1005_1() throws Exception {
		test(
			"MacroConditionElement1005_1.macro",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/MacroConditionElement1005_1.macro:4");
	}

	@Test
	public void testMacroConditionElement1005_2() throws Exception {
		test(
			"MacroConditionElement1005_2.macro",
			"Error 1005: Invalid locator attribute in " + _DIR_NAME +
				"/MacroConditionElement1005_2.macro:4");
	}

	@Test
	public void testMacroConditionElement1005_3() throws Exception {
		test(
			"MacroConditionElement1005_3.macro",
			"Error 1005: Invalid value attribute in " + _DIR_NAME +
				"/MacroConditionElement1005_3.macro:4");
	}

	@Test
	public void testMacroConditionElement1005_4() throws Exception {
		test(
			"MacroConditionElement1005_4.macro",
			"Error 1005: Invalid macro attribute in " + _DIR_NAME +
				"/MacroConditionElement1005_4.macro:4");
	}

	@Test
	public void testMacroConditionElement1005_5() throws Exception {
		test(
			"MacroConditionElement1005_5.macro",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/MacroConditionElement1005_5.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_1() throws Exception {
		test(
			"MacroConditionElement1006_1.macro",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_1.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_2() throws Exception {
		test(
			"MacroConditionElement1006_2.macro",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_2.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_3() throws Exception {
		test(
			"MacroConditionElement1006_3.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_3.macro:4");
	}

	@Test
	public void testMacroConditionElement1006_4() throws Exception {
		test(
			"MacroConditionElement1006_4.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroConditionElement1006_4.macro:4");
	}

	@Test
	public void testMacroConditionElement1007() throws Exception {
		test(
			"MacroConditionElement1007.macro",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/MacroConditionElement1007.macro");
	}

	@Test
	public void testMacroContainsElement1002() throws Exception {
		test(
			"MacroContainsElement1002.macro",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/MacroContainsElement1002.macro:5");
	}

	@Test
	public void testMacroContainsElement1004_1() throws Exception {
		test(
			"MacroContainsElement1004_1.macro",
			"Error 1004: Missing (string|substring) attribute in " + _DIR_NAME +
				"/MacroContainsElement1004_1.macro:4");
	}

	@Test
	public void testMacroContainsElement1004_2() throws Exception {
		test(
			"MacroContainsElement1004_2.macro",
			"Error 1004: Missing (string|substring) attribute in " + _DIR_NAME +
				"/MacroContainsElement1004_2.macro:4");
	}

	@Test
	public void testMacroContainsElement1004_3() throws Exception {
		test(
			"MacroContainsElement1004_3.macro",
			"Error 1004: Missing (string|substring) attribute in " + _DIR_NAME +
				"/MacroContainsElement1004_3.macro:4");
	}

	@Test
	public void testMacroContainsElement1005() throws Exception {
		test(
			"MacroContainsElement1005.macro",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/MacroContainsElement1005.macro:4");
	}

	@Test
	public void testMacroContainsElement1006_1() throws Exception {
		test(
			"MacroContainsElement1006_1.macro",
			"Error 1006: Invalid string attribute value in " + _DIR_NAME +
				"/MacroContainsElement1006_1.macro:4");
	}

	@Test
	public void testMacroContainsElement1006_2() throws Exception {
		test(
			"MacroContainsElement1006_2.macro",
			"Error 1006: Invalid substring attribute value in " + _DIR_NAME +
				"/MacroContainsElement1006_2.macro:4");
	}

	@Test
	public void testMacroDefinitionElement1000() throws Exception {
		test(
			"MacroDefinitionElement1000.macro",
			"Error 1000: Invalid root element in " + _DIR_NAME +
				"/MacroDefinitionElement1000.macro:1");
	}

	@Test
	public void testMacroDefinitionElement1001() throws Exception {
		test(
			"MacroDefinitionElement1001.macro",
			"Error 1001: Missing (command|var) child element in " + _DIR_NAME +
				"/MacroDefinitionElement1001.macro:1");
	}

	@Test
	public void testMacroDefinitionElement1002() throws Exception {
		test(
			"MacroDefinitionElement1002.macro",
			"Error 1002: Invalid command-fail element in " + _DIR_NAME +
				"/MacroDefinitionElement1002.macro:2");
	}

	@Test
	public void testMacroDefinitionElement1007() throws Exception {
		test(
			"MacroDefinitionElement1007.macro",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/MacroDefinitionElement1007.macro");
	}

	@Test
	public void testMacroEchoElement1005() throws Exception {
		test(
			"MacroEchoElement1005.macro",
			"Error 1005: Invalid message-fail attribute in " + _DIR_NAME +
				"/MacroEchoElement1005.macro:3");
	}

	@Test
	public void testMacroEchoElement1006() throws Exception {
		test(
			"MacroEchoElement1006.macro",
			"Error 1006: Invalid message attribute value in " + _DIR_NAME +
				"/MacroEchoElement1006.macro:3");
	}

	@Test
	public void testMacroElseElement1001() throws Exception {
		test(
			"MacroElseElement1001.macro",
			"Error 1001: Missing (echo|execute|fail|if|var|while) child " +
				"element in " + _DIR_NAME + "/MacroElseElement1001.macro:8");
	}

	@Test
	public void testMacroElseElement1002() throws Exception {
		test(
			"MacroElseElement1002.macro",
			"Error 1002: Invalid execute-fail element in " + _DIR_NAME +
				"/MacroElseElement1002.macro:9");
	}

	@Test
	public void testMacroElseElement1007() throws Exception {
		test(
			"MacroElseElement1007.macro",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/MacroElseElement1007.macro");
	}

	@Test
	public void testMacroElseifElement1001_1() throws Exception {
		test(
			"MacroElseifElement1001_1.macro",
			"Error 1001: Missing (condition|contains|equals|isset) child " +
				"element in " + _DIR_NAME +
				"/MacroElseifElement1001_1.macro:8");
	}

	@Test
	public void testMacroElseifElement1001_2() throws Exception {
		test(
			"MacroElseifElement1001_2.macro",
			"Error 1001: Missing (then) child element in " + _DIR_NAME +
				"/MacroElseifElement1001_2.macro:8");
	}

	@Test
	public void testMacroElseifElement1001_3() throws Exception {
		test(
			"MacroElseifElement1001_3.macro",
			"Error 1001: Missing (condition|contains|equals|isset) child " +
				"element in " + _DIR_NAME +
				"/MacroElseifElement1001_3.macro:8");
	}

	@Test
	public void testMacroElseifElement1002() throws Exception {
		test(
			"MacroElseifElement1002.macro",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/MacroElseifElement1002.macro:13");
	}

	@Test
	public void testMacroEqualsElement1002() throws Exception {
		test(
			"MacroEqualsElement1002.macro",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/MacroEqualsElement1002.macro:5");
	}

	@Test
	public void testMacroEqualsElement1004_1() throws Exception {
		test(
			"MacroEqualsElement1004_1.macro",
			"Error 1004: Missing (arg1|arg2) attribute in " + _DIR_NAME +
				"/MacroEqualsElement1004_1.macro:4");
	}

	@Test
	public void testMacroEqualsElement1004_2() throws Exception {
		test(
			"MacroEqualsElement1004_2.macro",
			"Error 1004: Missing (arg1|arg2) attribute in " + _DIR_NAME +
				"/MacroEqualsElement1004_2.macro:4");
	}

	@Test
	public void testMacroEqualsElement1004_3() throws Exception {
		test(
			"MacroEqualsElement1004_3.macro",
			"Error 1004: Missing (arg1|arg2) attribute in " + _DIR_NAME +
				"/MacroEqualsElement1004_3.macro:4");
	}

	@Test
	public void testMacroEqualsElement1005() throws Exception {
		test(
			"MacroEqualsElement1005.macro",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/MacroEqualsElement1005.macro:4");
	}

	@Test
	public void testMacroEqualsElement1006_1() throws Exception {
		test(
			"MacroEqualsElement1006_1.macro",
			"Error 1006: Invalid arg1 attribute value in " + _DIR_NAME +
				"/MacroEqualsElement1006_1.macro:4");
	}

	@Test
	public void testMacroEqualsElement1006_2() throws Exception {
		test(
			"MacroEqualsElement1006_2.macro",
			"Error 1006: Invalid arg2 attribute value in " + _DIR_NAME +
				"/MacroEqualsElement1006_2.macro:4");
	}

	@Test
	public void testMacroExecuteElement1002_1() throws Exception {
		test(
			"MacroExecuteElement1002_1.macro",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/MacroExecuteElement1002_1.macro:4");
	}

	@Test
	public void testMacroExecuteElement1002_2() throws Exception {
		test(
			"MacroExecuteElement1002_2.macro",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/MacroExecuteElement1002_2.macro:4");
	}

	@Test
	public void testMacroExecuteElement1004_1() throws Exception {
		test(
			"MacroExecuteElement1004_1.macro",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/MacroExecuteElement1004_1.macro:3");
	}

	@Test
	public void testMacroExecuteElement1004_2() throws Exception {
		test(
			"MacroExecuteElement1004_2.macro",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/MacroExecuteElement1004_2.macro:3");
	}

	@Test
	public void testMacroExecuteElement1004_3() throws Exception {
		test(
			"MacroExecuteElement1004_3.macro",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/MacroExecuteElement1004_3.macro:3");
	}

	@Test
	public void testMacroExecuteElement1005_1() throws Exception {
		test(
			"MacroExecuteElement1005_1.macro",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/MacroExecuteElement1005_1.macro:3");
	}

	@Test
	public void testMacroExecuteElement1005_2() throws Exception {
		test(
			"MacroExecuteElement1005_2.macro",
			"Error 1005: Invalid locator attribute in " + _DIR_NAME +
				"/MacroExecuteElement1005_2.macro:3");
	}

	@Test
	public void testMacroExecuteElement1005_3() throws Exception {
		test(
			"MacroExecuteElement1005_3.macro",
			"Error 1005: Invalid value attribute in " + _DIR_NAME +
				"/MacroExecuteElement1005_3.macro:3");
	}

	@Test
	public void testMacroExecuteElement1005_4() throws Exception {
		test(
			"MacroExecuteElement1005_4.macro",
			"Error 1005: Invalid macro attribute in " + _DIR_NAME +
				"/MacroExecuteElement1005_4.macro:3");
	}

	@Test
	public void testMacroExecuteElement1005_5() throws Exception {
		test(
			"MacroExecuteElement1005_5.macro",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/MacroExecuteElement1005_5.macro:3");
	}

	@Test
	public void testMacroExecuteElement1006_1() throws Exception {
		test(
			"MacroExecuteElement1006_1.macro",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_1.macro:3");
	}

	@Test
	public void testMacroExecuteElement1006_2() throws Exception {
		test(
			"MacroExecuteElement1006_2.macro",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/MacroExecuteElement1006_2.macro:3");
	}

	@Test
	public void testMacroExecuteElement1007() throws Exception {
		test(
			"MacroExecuteElement1007.macro",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/MacroExecuteElement1007.macro");
	}

	@Test
	public void testMacroFailElement1005() throws Exception {
		test(
			"MacroFailElement1005.macro",
			"Error 1005: Invalid message-fail attribute in " + _DIR_NAME +
				"/MacroFailElement1005.macro:3");
	}

	@Test
	public void testMacroFailElement1006() throws Exception {
		test(
			"MacroFailElement1006.macro",
			"Error 1006: Invalid message attribute value in " + _DIR_NAME +
				"/MacroFailElement1006.macro:3");
	}

	@Test
	public void testMacroIfElement1001_1() throws Exception {
		test(
			"MacroIfElement1001_1.macro",
			"Error 1001: Missing (condition|contains|equals|isset) child " +
				"element in " + _DIR_NAME + "/MacroIfElement1001_1.macro:3");
	}

	@Test
	public void testMacroIfElement1001_2() throws Exception {
		test(
			"MacroIfElement1001_2.macro",
			"Error 1001: Missing (then) child element in " + _DIR_NAME +
				"/MacroIfElement1001_2.macro:3");
	}

	@Test
	public void testMacroIfElement1001_3() throws Exception {
		test(
			"MacroIfElement1001_3.macro",
			"Error 1001: Missing (condition|contains|equals|isset) child " +
				"element in " + _DIR_NAME + "/MacroIfElement1001_3.macro:3");
	}

	@Test
	public void testMacroIfElement1002() throws Exception {
		test(
			"MacroIfElement1002.macro",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/MacroIfElement1002.macro:11");
	}

	@Test
	public void testMacroIfElement1007() throws Exception {
		test(
			"MacroIfElement1007.macro",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/MacroIfElement1007.macro");
	}

	@Test
	public void testMacroIssetElement1002() throws Exception {
		test(
			"MacroIssetElement1002.macro",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/MacroIssetElement1002.macro:5");
	}

	@Test
	public void testMacroIssetElement1004() throws Exception {
		test(
			"MacroIssetElement1004.macro",
			"Error 1004: Missing (var) attribute in " + _DIR_NAME +
				"/MacroIssetElement1004.macro:4");
	}

	@Test
	public void testMacroIssetElement1005() throws Exception {
		test(
			"MacroIssetElement1005.macro",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/MacroIssetElement1005.macro:4");
	}

	@Test
	public void testMacroIssetElement1006() throws Exception {
		test(
			"MacroIssetElement1006.macro",
			"Error 1006: Invalid var attribute value in " + _DIR_NAME +
				"/MacroIssetElement1006.macro:4");
	}

	@Test
	public void testMacroThenElement1001() throws Exception {
		test(
			"MacroThenElement1001.macro",
			"Error 1001: Missing (echo|execute|fail|if|var|while) child " +
				"element in " + _DIR_NAME + "/MacroThenElement1001.macro:5");
	}

	@Test
	public void testMacroThenElement1002() throws Exception {
		test(
			"MacroThenElement1002.macro",
			"Error 1002: Invalid execute-fail element in " + _DIR_NAME +
				"/MacroThenElement1002.macro:6");
	}

	@Test
	public void testMacroThenElement1007() throws Exception {
		test(
			"MacroThenElement1007.macro",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/MacroThenElement1007.macro");
	}

	@Test
	public void testMacroVarElement1002() throws Exception {
		test(
			"MacroVarElement1002.macro",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/MacroVarElement1002.macro:3");
	}

	@Test
	public void testMacroVarElement1004_1() throws Exception {
		test(
			"MacroVarElement1004_1.macro",
			"Error 1004: Missing (name|value) attribute in " + _DIR_NAME +
				"/MacroVarElement1004_1.macro:2");
	}

	@Test
	public void testMacroVarElement1004_2() throws Exception {
		test(
			"MacroVarElement1004_2.macro",
			"Error 1004: Missing (name|value) attribute in " + _DIR_NAME +
				"/MacroVarElement1004_2.macro:2");
	}

	@Test
	public void testMacroVarElement1004_3() throws Exception {
		test(
			"MacroVarElement1004_3.macro",
			"Error 1004: Missing (name|value) attribute in " + _DIR_NAME +
				"/MacroVarElement1004_3.macro:2");
	}

	@Test
	public void testMacroVarElement1005() throws Exception {
		test(
			"MacroVarElement1005.macro",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/MacroVarElement1005.macro:2");
	}

	@Test
	public void testMacroVarElement1006_1() throws Exception {
		test(
			"MacroVarElement1006_1.macro",
			"Error 1006: Invalid name attribute value in " + _DIR_NAME +
				"/MacroVarElement1006_1.macro:2");
	}

	@Test
	public void testMacroVarElement1006_2() throws Exception {
		test(
			"MacroVarElement1006_2.macro",
			"Error 1006: Invalid value attribute value in " + _DIR_NAME +
				"/MacroVarElement1006_2.macro:2");
	}

	@Test
	public void testMacroVarElement1007() throws Exception {
		test(
			"MacroVarElement1007.macro",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/MacroVarElement1007.macro");
	}

	@Test
	public void testMacroWhileElement1001_1() throws Exception {
		test(
			"MacroWhileElement1001_1.macro",
			"Error 1001: Missing (condition|then) child element in " +
				_DIR_NAME + "/MacroWhileElement1001_1.macro:3");
	}

	@Test
	public void testMacroWhileElement1001_2() throws Exception {
		test(
			"MacroWhileElement1001_2.macro",
			"Error 1001: Missing (condition|then) child element in " +
				_DIR_NAME + "/MacroWhileElement1001_2.macro:3");
	}

	@Test
	public void testMacroWhileElement1001_3() throws Exception {
		test(
			"MacroWhileElement1001_3.macro",
			"Error 1001: Missing (condition|then) child element in " +
				_DIR_NAME + "/MacroWhileElement1001_3.macro:3");
	}

	@Test
	public void testMacroWhileElement1002() throws Exception {
		test(
			"MacroWhileElement1002.macro",
			"Error 1002: Invalid else element in " + _DIR_NAME +
				"/MacroWhileElement1002.macro:8");
	}

	@Test
	public void testMacroWhileElement1007() throws Exception {
		test(
			"MacroWhileElement1007.macro",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/MacroWhileElement1007.macro");
	}

	@Test
	public void testPath() throws Exception {
		test("Path.path");
	}

	@Test
	public void testPathTbodyElement1002_1() throws Exception {
		test(
			"PathTbodyElement1002_1.path",
			"Error 1002: Invalid tbody element in " + _DIR_NAME +
				"/PathTbodyElement1002_1.path");
	}

	@Test
	public void testPathTbodyElement1002_2() throws Exception {
		test(
			"PathTbodyElement1002_2.path",
			"Error 1002: Invalid tbody element in " + _DIR_NAME +
				"/PathTbodyElement1002_2.path");
	}

	@Test
	public void testPathTbodyElement1002_3() throws Exception {
		test(
			"PathTbodyElement1002_3.path",
			"Error 1002: Invalid tbody element in " + _DIR_NAME +
				"/PathTbodyElement1002_3.path");
	}

	@Test
	public void testPathTbodyElement1002_4() throws Exception {
		test(
			"PathTbodyElement1002_4.path",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/PathTbodyElement1002_4.path:11");
	}

	@Test
	public void testPathTdElement1007() throws Exception {
		test(
			"PathTdElement1007.path",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/PathTdElement1007.path");
	}

	@Test
	public void testPathTrElement1001() throws Exception {
		test(
			"PathTrElement1001.path",
			"Error 1001: Missing (td) child element in " + _DIR_NAME +
				"/PathTrElement1001.path:11");
	}

	@Test
	public void testPathTrElement1002_1() throws Exception {
		test(
			"PathTrElement1002_1.path",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/PathTrElement1002_1.path:15");
	}

	@Test
	public void testPathTrElement1002_2() throws Exception {
		test(
			"PathTrElement1002_2.path",
			"Error 1002: Invalid td element in " + _DIR_NAME +
				"/PathTrElement1002_2.path:15");
	}

	@Test
	public void testPathTrElement1007() throws Exception {
		test(
			"PathTrElement1007.path",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/PathTrElement1007.path");
	}

	@Test
	public void testTestCase() throws Exception {
		test("TestCase.testcase");
	}

	@Test
	public void testTestCaseCommandElement1001() throws Exception {
		test(
			"TestCaseCommandElement1001.testcase",
			"Error 1001: Missing (execute|var) child element in " +
				_DIR_NAME + "/TestCaseCommandElement1001.testcase:2");
	}

	@Test
	public void testTestCaseCommandElement1002_1() throws Exception {
		test(
			"TestCaseCommandElement1002_1.testcase",
			"Error 1002: Invalid execute-fail element in " + _DIR_NAME +
				"/TestCaseCommandElement1002_1.testcase:3");
	}

	@Test
	public void testTestCaseCommandElement1002_2() throws Exception {
		test(
			"TestCaseCommandElement1002_2.testcase",
			"Error 1002: Invalid if element in " + _DIR_NAME +
				"/TestCaseCommandElement1002_2.testcase:3");
	}

	@Test
	public void testTestCaseCommandElement1002_3() throws Exception {
		test(
			"TestCaseCommandElement1002_3.testcase",
			"Error 1002: Invalid while element in " + _DIR_NAME +
				"/TestCaseCommandElement1002_3.testcase:3");
	}

	@Test
	public void testTestCaseCommandElement1003_1() throws Exception {
		test(
			"TestCaseCommandElement1003_1.testcase",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/TestCaseCommandElement1003_1.testcase:2");
	}

	@Test
	public void testTestCaseCommandElement1003_2() throws Exception {
		test(
			"TestCaseCommandElement1003_2.testcase",
			"Error 1003: Missing name attribute in " + _DIR_NAME +
				"/TestCaseCommandElement1003_2.testcase:2");
	}

	@Test
	public void testTestCaseCommandElement1006() throws Exception {
		test(
			"TestCaseCommandElement1006.testcase",
			"Error 1006: Invalid name attribute value in " + _DIR_NAME +
				"/TestCaseCommandElement1006.testcase:2");
	}

	@Test
	public void testTestCaseCommandElement1007() throws Exception {
		test(
			"TestCaseCommandElement1007.testcase",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/TestCaseCommandElement1007.testcase");
	}

	@Test
	public void testTestCaseDefinitionElement1000() throws Exception {
		test(
			"TestCaseDefinitionElement1000.testcase",
			"Error 1000: Invalid root element in " + _DIR_NAME +
				"/TestCaseDefinitionElement1000.testcase:1");
	}

	@Test
	public void testTestCaseDefinitionElement1001() throws Exception {
		test(
			"TestCaseDefinitionElement1001.testcase",
			"Error 1001: Missing (command) child element in " + _DIR_NAME +
				"/TestCaseDefinitionElement1001.testcase:1");
	}

	@Test
	public void testTestCaseDefinitionElement1002() throws Exception {
		test(
			"TestCaseDefinitionElement1002.testcase",
			"Error 1002: Invalid command-fail element in " + _DIR_NAME +
				"/TestCaseDefinitionElement1002.testcase:2");
	}

	@Test
	public void testTestCaseDefinitionElement1007() throws Exception {
		test(
			"TestCaseDefinitionElement1007.testcase",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/TestCaseDefinitionElement1007.testcase");
	}

	@Test
	public void testTestCaseExecuteElement1002_1() throws Exception {
		test(
			"TestCaseExecuteElement1002_1.testcase",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/TestCaseExecuteElement1002_1.testcase:4");
	}

	@Test
	public void testTestCaseExecuteElement1002_2() throws Exception {
		test(
			"TestCaseExecuteElement1002_2.testcase",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/TestCaseExecuteElement1002_2.testcase:4");
	}

	@Test
	public void testTestCaseExecuteElement1004_1() throws Exception {
		test(
			"TestCaseExecuteElement1004_1.testcase",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/TestCaseExecuteElement1004_1.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1004_2() throws Exception {
		test(
			"TestCaseExecuteElement1004_2.testcase",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/TestCaseExecuteElement1004_2.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1004_3() throws Exception {
		test(
			"TestCaseExecuteElement1004_3.testcase",
			"Error 1004: Missing (action|macro) attribute in " + _DIR_NAME +
				"/TestCaseExecuteElement1004_3.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1005_1() throws Exception {
		test(
			"TestCaseExecuteElement1005_1.testcase",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/TestCaseExecuteElement1005_1.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1005_2() throws Exception {
		test(
			"TestCaseExecuteElement1005_2.testcase",
			"Error 1005: Invalid locator attribute in " + _DIR_NAME +
				"/TestCaseExecuteElement1005_2.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1005_3() throws Exception {
		test(
			"TestCaseExecuteElement1005_3.testcase",
			"Error 1005: Invalid value attribute in " + _DIR_NAME +
				"/TestCaseExecuteElement1005_3.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1005_4() throws Exception {
		test(
			"TestCaseExecuteElement1005_4.testcase",
			"Error 1005: Invalid macro attribute in " + _DIR_NAME +
				"/TestCaseExecuteElement1005_4.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1005_5() throws Exception {
		test(
			"TestCaseExecuteElement1005_5.testcase",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/TestCaseExecuteElement1005_5.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1006_1() throws Exception {
		test(
			"TestCaseExecuteElement1006_1.testcase",
			"Error 1006: Invalid action attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_1.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1006_2() throws Exception {
		test(
			"TestCaseExecuteElement1006_2.testcase",
			"Error 1006: Invalid macro attribute value in " + _DIR_NAME +
				"/TestCaseExecuteElement1006_2.testcase:3");
	}

	@Test
	public void testTestCaseExecuteElement1007() throws Exception {
		test(
			"TestCaseExecuteElement1007.testcase",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/TestCaseExecuteElement1007.testcase");
	}

	@Test
	public void testTestCaseSetUpElement1001() throws Exception {
		test(
			"TestCaseSetUpElement1001.testcase",
			"Error 1001: Missing (execute|var) child element in " + _DIR_NAME +
				"/TestCaseSetUpElement1001.testcase:2");
	}

	@Test
	public void testTestCaseSetUpElement1002() throws Exception {
		test(
			"TestCaseSetUpElement1002.testcase",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/TestCaseSetUpElement1002.testcase:3");
	}

	@Test
	public void testTestCaseSetUpElement1005() throws Exception {
		test(
			"TestCaseSetUpElement1005.testcase",
			"Error 1005: Invalid name attribute in " + _DIR_NAME +
				"/TestCaseSetUpElement1005.testcase:2");
	}

	@Test
	public void testTestCaseSetUpElement1007() throws Exception {
		test(
			"TestCaseSetUpElement1007.testcase",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/TestCaseSetUpElement1007.testcase");
	}

	@Test
	public void testTestCaseTearDownElement1001() throws Exception {
		test(
			"TestCaseTearDownElement1001.testcase",
			"Error 1001: Missing (execute|var) child element in " + _DIR_NAME +
				"/TestCaseTearDownElement1001.testcase:6");
	}

	@Test
	public void testTestCaseTearDownElement1002() throws Exception {
		test(
			"TestCaseTearDownElement1002.testcase",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/TestCaseTearDownElement1002.testcase:7");
	}

	@Test
	public void testTestCaseTearDownElement1005() throws Exception {
		test(
			"TestCaseTearDownElement1005.testcase",
			"Error 1005: Invalid name attribute in " + _DIR_NAME +
				"/TestCaseTearDownElement1005.testcase:6");
	}

	@Test
	public void testTestCaseTearDownElement1007() throws Exception {
		test(
			"TestCaseTearDownElement1007.testcase",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/TestCaseTearDownElement1007.testcase");
	}

	@Test
	public void testTestCaseVarElement1002() throws Exception {
		test(
			"TestCaseVarElement1002.testcase",
			"Error 1002: Invalid fail element in " + _DIR_NAME +
				"/TestCaseVarElement1002.testcase:3");
	}

	@Test
	public void testTestCaseVarElement1004_1() throws Exception {
		test(
			"TestCaseVarElement1004_1.testcase",
			"Error 1004: Missing (name|value) attribute in " + _DIR_NAME +
				"/TestCaseVarElement1004_1.testcase:2");
	}

	@Test
	public void testTestCaseVarElement1004_2() throws Exception {
		test(
			"TestCaseVarElement1004_2.testcase",
			"Error 1004: Missing (name|value) attribute in " + _DIR_NAME +
				"/TestCaseVarElement1004_2.testcase:2");
	}

	@Test
	public void testTestCaseVarElement1004_3() throws Exception {
		test(
			"TestCaseVarElement1004_3.testcase",
			"Error 1004: Missing (name|value) attribute in " + _DIR_NAME +
				"/TestCaseVarElement1004_3.testcase:2");
	}

	@Test
	public void testTestCaseVarElement1005() throws Exception {
		test(
			"TestCaseVarElement1005.testcase",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/TestCaseVarElement1005.testcase:2");
	}

	@Test
	public void testTestCaseVarElement1006_1() throws Exception {
		test(
			"TestCaseVarElement1006_1.testcase",
			"Error 1006: Invalid name attribute value in " + _DIR_NAME +
				"/TestCaseVarElement1006_1.testcase:2");
	}

	@Test
	public void testTestCaseVarElement1006_2() throws Exception {
		test(
			"TestCaseVarElement1006_2.testcase",
			"Error 1006: Invalid value attribute value in " + _DIR_NAME +
				"/TestCaseVarElement1006_2.testcase:2");
	}

	@Test
	public void testTestCaseVarElement1007() throws Exception {
		test(
			"TestCaseVarElement1007.testcase",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/TestCaseVarElement1007.testcase");
	}

	@Test
	public void testTestSuite() throws Exception {
		test("TestSuite.testsuite");
	}

	@Test
	public void testTestSuiteDefinitionElement1000() throws Exception {
		test(
			"TestSuiteDefinitionElement1000.testsuite",
			"Error 1000: Invalid root element in " + _DIR_NAME +
				"/TestSuiteDefinitionElement1000.testsuite:1");
	}

	@Test
	public void testTestSuiteDefinitionElement1001() throws Exception {
		test(
			"TestSuiteDefinitionElement1001.testsuite",
			"Error 1001: Missing (execute) child element in " + _DIR_NAME +
				"/TestSuiteDefinitionElement1001.testsuite:1");
	}

	@Test
	public void testTestSuiteDefinitionElement1002() throws Exception {
		test(
			"TestSuiteDefinitionElement1002.testsuite",
			"Error 1002: Invalid command element in " + _DIR_NAME +
				"/TestSuiteDefinitionElement1002.testsuite:2");
	}

	@Test
	public void testTestSuiteDefinitionElement1007() throws Exception {
		test(
			"TestSuiteDefinitionElement1007.testsuite",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/TestSuiteDefinitionElement1007.testsuite");
	}

	@Test
	public void testTestSuiteExecuteElement1002() throws Exception {
		test(
			"TestSuiteExecuteElement1002.testsuite",
			"Error 1002: Invalid var element in " + _DIR_NAME +
				"/TestSuiteExecuteElement1002.testsuite:3");
	}

	@Test
	public void testTestSuiteExecuteElement1004_1() throws Exception {
		test(
			"TestSuiteExecuteElement1004_1.testsuite",
			"Error 1004: Missing (test-case|test-suite) attribute in " +
				_DIR_NAME + "/TestSuiteExecuteElement1004_1.testsuite:2");
	}

	@Test
	public void testTestSuiteExecuteElement1004_2() throws Exception {
		test(
			"TestSuiteExecuteElement1004_2.testsuite",
			"Error 1004: Missing (test-case|test-suite) attribute in " +
				_DIR_NAME + "/TestSuiteExecuteElement1004_2.testsuite:2");
	}

	@Test
	public void testTestSuiteExecuteElement1004_3() throws Exception {
		test(
			"TestSuiteExecuteElement1004_3.testsuite",
			"Error 1004: Missing (test-case|test-suite) attribute in " +
				_DIR_NAME + "/TestSuiteExecuteElement1004_3.testsuite:2");
	}

	@Test
	public void testTestSuiteExecuteElement1005_1() throws Exception {
		test(
			"TestSuiteExecuteElement1005_1.testsuite",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/TestSuiteExecuteElement1005_1.testsuite:2");
	}

	@Test
	public void testTestSuiteExecuteElement1005_2() throws Exception {
		test(
			"TestSuiteExecuteElement1005_2.testsuite",
			"Error 1005: Invalid test-suite attribute in " + _DIR_NAME +
				"/TestSuiteExecuteElement1005_2.testsuite:2");
	}

	@Test
	public void testTestSuiteExecuteElement1005_3() throws Exception {
		test(
			"TestSuiteExecuteElement1005_3.testsuite",
			"Error 1005: Invalid fail attribute in " + _DIR_NAME +
				"/TestSuiteExecuteElement1005_3.testsuite:2");
	}

	@Test
	public void testTestSuiteExecuteElement1006_1() throws Exception {
		test(
			"TestSuiteExecuteElement1006_1.testsuite",
			"Error 1006: Invalid test-case attribute value in " + _DIR_NAME +
				"/TestSuiteExecuteElement1006_1.testsuite:2");
	}

	@Test
	public void testTestSuiteExecuteElement1006_2() throws Exception {
		test(
			"TestSuiteExecuteElement1006_2.testsuite",
			"Error 1006: Invalid test-suite attribute value in " + _DIR_NAME +
				"/TestSuiteExecuteElement1006_2.testsuite:2");
	}

	@Test
	public void testTestSuiteExecuteElement1007() throws Exception {
		test(
			"TestSuiteExecuteElement1007.testsuite",
			"Error 1007: Poorly formed XML in " + _DIR_NAME +
				"/TestSuiteExecuteElement1007.testsuite");
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
			_seleniumBuilderFileUtil.getRootElement(_DIR_NAME + "/" + fileName);
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

	private static final String _DIR_NAME =
		"portal-impl/test/integration/com/liferay/portal/tools/" +
			"seleniumbuilder/dependencies";

	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;

}