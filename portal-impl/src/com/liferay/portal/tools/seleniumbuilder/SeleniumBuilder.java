/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.util.InitUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new SeleniumBuilder(args);
	}

	public SeleniumBuilder(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String baseDir = arguments.get("selenium.base.dir");

		SeleniumBuilderContext seleniumBuilderContext =
			new SeleniumBuilderContext(baseDir);

		Set<String> types = SetUtil.fromArray(
			StringUtil.split(arguments.get("selenium.types")));

		if (types.contains("action")) {
			ActionConverter actionConverter = new ActionConverter(
				seleniumBuilderContext);

			Set<String> actionNames = seleniumBuilderContext.getActionNames();

			for (String actionName : actionNames) {
				actionConverter.convert(actionName);
			}
		}

		if (types.contains("function")) {
			FunctionConverter functionConverter = new FunctionConverter(
				seleniumBuilderContext);

			Set<String> functionNames =
				seleniumBuilderContext.getFunctionNames();

			for (String functionName : functionNames) {
				functionConverter.convert(functionName);
			}
		}

		if (types.contains("macro")) {
			MacroConverter macroConverter = new MacroConverter(
				seleniumBuilderContext);

			Set<String> macroNames = seleniumBuilderContext.getMacroNames();

			for (String macroName : macroNames) {
				macroConverter.convert(macroName);
			}
		}

		if (types.contains("path")) {
			PathConverter pathConverter = new PathConverter(
				seleniumBuilderContext);

			Set<String> pathNames = seleniumBuilderContext.getPathNames();

			for (String pathName : pathNames) {
				pathConverter.convert(pathName);
			}
		}

		if (types.contains("testcase")) {
			TestCaseConverter testCaseConverter = new TestCaseConverter(
				seleniumBuilderContext);

			Set<String> testCaseNames =
				seleniumBuilderContext.getTestCaseNames();

			for (String testCaseName : testCaseNames) {
				testCaseConverter.convert(testCaseName);
			}
		}

		if (types.contains("testsuite")) {
			TestSuiteConverter testSuiteConverter = new TestSuiteConverter(
				seleniumBuilderContext);

			Set<String> testSuiteNames =
				seleniumBuilderContext.getTestSuiteNames();

			for (String testSuiteName : testSuiteNames) {
				testSuiteConverter.convert(testSuiteName);
			}
		}
	}

}