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

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.util.InitUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
				seleniumBuilderContext.validateActionElements(actionName);

				actionConverter.convert(actionName);
			}
		}

		if (types.contains("function")) {
			FunctionConverter functionConverter = new FunctionConverter(
				seleniumBuilderContext);

			Set<String> functionNames =
				seleniumBuilderContext.getFunctionNames();

			for (String functionName : functionNames) {
				seleniumBuilderContext.validateFunctionElements(functionName);

				functionConverter.convert(functionName);
			}
		}

		if (types.contains("macro")) {
			MacroConverter macroConverter = new MacroConverter(
				seleniumBuilderContext);

			Set<String> macroNames = seleniumBuilderContext.getMacroNames();

			for (String macroName : macroNames) {
				seleniumBuilderContext.validateMacroElements(macroName);

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
				seleniumBuilderContext.validateTestCaseElements(testCaseName);

				testCaseConverter.convert(testCaseName);
			}
		}

		if (types.contains("testsuite")) {
			TestSuiteConverter testSuiteConverter = new TestSuiteConverter(
				seleniumBuilderContext);

			Set<String> testSuiteNames =
				seleniumBuilderContext.getTestSuiteNames();

			for (String testSuiteName : testSuiteNames) {
				seleniumBuilderContext.validateTestSuiteElements(testSuiteName);

				testSuiteConverter.convert(testSuiteName);
			}
		}

		SeleniumBuilderFileUtil seleniumBuilderFileUtil =
			new SeleniumBuilderFileUtil(baseDir);

		Set<String> testCaseMethodNames = new TreeSet<String>();
		int testCaseCount = 0;

		Set<String> testCaseNames = seleniumBuilderContext.getTestCaseNames();

		for (String testCaseName : testCaseNames) {
			Element rootElement = seleniumBuilderContext.getTestCaseRootElement(
				testCaseName);

			List<Element> commandElements =
				seleniumBuilderFileUtil.getAllChildElements(
					rootElement, "command");

			for (Element commandElement : commandElements) {
				testCaseMethodNames.add(
					testCaseName + "TestCase#test" +
						commandElement.attributeValue("name"));
			}

			testCaseCount += commandElements.size();
		}

		seleniumBuilderFileUtil.writeFile(
			"../../../test-case-method-names.properties",
			"test.case.method.names=" +
				StringUtil.merge(testCaseMethodNames.toArray(
					new String[testCaseMethodNames.size()]), StringPool.SPACE),
			false);

		System.out.println("\nThere are " + testCaseCount + " test cases.");
	}

}