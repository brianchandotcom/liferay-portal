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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.util.InitUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new SeleniumBuilder(args);
	}

	/**
	 * Constructs a Selenium Builder with an argument array of
	 * <code>String</code>.
	 *
	 * @param  args the command-line arguments
	 * @throws Exception if an exception occurred
	 */
	public SeleniumBuilder(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String baseDir = arguments.get("selenium.base.dir");

		_seleniumBuilderContext = new SeleniumBuilderContext(baseDir);
		_seleniumBuilderFileUtil = new SeleniumBuilderFileUtil(baseDir);

		Set<String> types = SetUtil.fromArray(
			StringUtil.split(arguments.get("selenium.types")));

		if (types.contains("action")) {
			ActionConverter actionConverter = new ActionConverter(
				_seleniumBuilderContext);

			Set<String> actionNames = _seleniumBuilderContext.getActionNames();

			for (String actionName : actionNames) {
				_seleniumBuilderContext.validateActionElements(actionName);

				actionConverter.convert(actionName);
			}
		}

		if (types.contains("function")) {
			FunctionConverter functionConverter = new FunctionConverter(
				_seleniumBuilderContext);

			Set<String> functionNames =
				_seleniumBuilderContext.getFunctionNames();

			for (String functionName : functionNames) {
				_seleniumBuilderContext.validateFunctionElements(functionName);

				functionConverter.convert(functionName);
			}
		}

		if (types.contains("macro")) {
			MacroConverter macroConverter = new MacroConverter(
				_seleniumBuilderContext);

			Set<String> macroNames = _seleniumBuilderContext.getMacroNames();

			for (String macroName : macroNames) {
				_seleniumBuilderContext.validateMacroElements(macroName);

				macroConverter.convert(macroName);
			}
		}

		if (types.contains("path")) {
			PathConverter pathConverter = new PathConverter(
				_seleniumBuilderContext);

			Set<String> pathNames = _seleniumBuilderContext.getPathNames();

			for (String pathName : pathNames) {
				pathConverter.convert(pathName);
			}
		}

		if (types.contains("testcase")) {
			TestCaseConverter testCaseConverter = new TestCaseConverter(
				_seleniumBuilderContext);

			Set<String> testCaseNames =
				_seleniumBuilderContext.getTestCaseNames();

			for (String testCaseName : testCaseNames) {
				_seleniumBuilderContext.validateTestCaseElements(testCaseName);

				testCaseConverter.convert(testCaseName);
			}
		}

		_writeTestCaseMethodNamesFile();
		_writeTestCasePropertiesFile();

		System.out.println(
			"\nThere are " + _getTestCaseMethodCount() + " test cases.");
	}

	/**
	 * Returns the total number of test case methods by adding the number of
	 * methods inside each test case file.
	 *
	 * @return the total number of test case methods
	 */
	private int _getTestCaseMethodCount() {
		int testCaseCount = 0;

		Set<String> testCaseNames = _seleniumBuilderContext.getTestCaseNames();

		for (String testCaseName : testCaseNames) {
			Element rootElement =
				_seleniumBuilderContext.getTestCaseRootElement(testCaseName);

			if (GetterUtil.getBoolean(rootElement.attributeValue("ignore"))) {
				continue;
			}

			String extendsTestCaseName = rootElement.attributeValue("extends");

			if (extendsTestCaseName != null) {
				Element extendsRootElement =
					_seleniumBuilderContext.getTestCaseRootElement(
						extendsTestCaseName);

				List<Element> commandElements =
					_seleniumBuilderFileUtil.getAllChildElements(
						extendsRootElement, "command");

				testCaseCount += commandElements.size();
			}

			List<Element> commandElements =
				_seleniumBuilderFileUtil.getAllChildElements(
					rootElement, "command");

			testCaseCount += commandElements.size();
		}

		return testCaseCount;
	}

	/**
	 * Gets the list of all test case method names, sorts them according to
	 * <code>component-name</code>, and then writes these lists to
	 * <code>test.case.method.names.properties</code> as properties in this
	 * format: <code>componentName + "_TEST_CASE_METHOD_NAMES=" + testCaseName +
	 * "TestCase#test" + commandName</code>.
	 *
	 * <p>
	 * Example <code>test.case.method.names.properties</code> output file:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * MARKETPLACE_TEST_CASE_METHOD_NAMES=PortalSmokeTestCase#testSmoke
	 * PORTAL_APIS_TEST_CASE_METHOD_NAMES=ApisTestCase#testAdd ApisTestCase#test
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @throws Exception if an exception occurred
	 */
	private void _writeTestCaseMethodNamesFile() throws Exception {
		Map<String, Set<String>> testCaseMethodNameMap =
			new TreeMap<String, Set<String>>();

		Set<String> testCaseMethodNames = new TreeSet<String>();

		Set<String> testCaseNames = _seleniumBuilderContext.getTestCaseNames();

		for (String testCaseName : testCaseNames) {
			Element rootElement =
				_seleniumBuilderContext.getTestCaseRootElement(testCaseName);

			if (GetterUtil.getBoolean(rootElement.attributeValue("ignore"))) {
				continue;
			}

			String componentName = rootElement.attributeValue("component-name");

			Set<String> compontentTestCaseMethodNames = new TreeSet<String>();

			if (testCaseMethodNameMap.containsKey(componentName)) {
				compontentTestCaseMethodNames = testCaseMethodNameMap.get(
					componentName);
			}

			String extendsTestCaseName = rootElement.attributeValue("extends");

			if (extendsTestCaseName != null) {
				Element extendsRootElement =
					_seleniumBuilderContext.getTestCaseRootElement(
						extendsTestCaseName);

				List<Element> commandElements =
					_seleniumBuilderFileUtil.getAllChildElements(
						extendsRootElement, "command");

				for (Element commandElement : commandElements) {
					String testCaseMethodName =
						testCaseName + "TestCase#test" +
							commandElement.attributeValue("name");

					compontentTestCaseMethodNames.add(testCaseMethodName);

					testCaseMethodNames.add(testCaseMethodName);
				}
			}

			List<Element> commandElements =
				_seleniumBuilderFileUtil.getAllChildElements(
					rootElement, "command");

			for (Element commandElement : commandElements) {
				String testCaseMethodName =
					testCaseName + "TestCase#test" +
						commandElement.attributeValue("name");

				compontentTestCaseMethodNames.add(testCaseMethodName);

				testCaseMethodNames.add(testCaseMethodName);
			}

			testCaseMethodNameMap.put(
				componentName, compontentTestCaseMethodNames);
		}

		List<String> componentNames =
			_seleniumBuilderFileUtil.getComponentNames();

		StringBundler sb = new StringBundler();

		for (String componentName : componentNames) {
			String componentNameKey = componentName;

			componentName = StringUtil.replace(componentName, "-", "_");
			componentName = StringUtil.upperCase(componentName);

			sb.append(componentName);
			sb.append("_TEST_CASE_METHOD_NAMES=");

			if (testCaseMethodNameMap.containsKey(componentNameKey)) {
				Set<String> compontentTestCaseMethodNames =
					testCaseMethodNameMap.get(componentNameKey);

				String compontentTestCaseMethodNamesString = StringUtil.merge(
					compontentTestCaseMethodNames.toArray(
						new String[compontentTestCaseMethodNames.size()]),
					StringPool.SPACE);

				sb.append(compontentTestCaseMethodNamesString);
				sb.append("\n");
			}
			else {
				sb.append("PortalSmokeTestCase#testSmoke\n");
			}
		}

		sb.append("\nTEST_CASE_METHOD_NAMES=");

		String testCaseMethodNamesString = StringUtil.merge(
			testCaseMethodNames.toArray(new String[testCaseMethodNames.size()]),
			StringPool.SPACE);

		sb.append(testCaseMethodNamesString);

		_seleniumBuilderFileUtil.writeFile(
			"../../../test.case.method.names.properties", sb.toString(), false);
	}

	/**
	 * Gets the list of all test case method properties scoped to the test case
	 * definition, and writes them to <code>test.generated.properties </code> in
	 * this format: <code>testCaseName + "all" + propertyName =
	 * propertyValue</code>.
	 *
	 * <p>
	 * If the test case method properties are scoped to the test case command,
	 * then it writes the properties to <code>test.generated.properties</code>
	 * in this format: <code>testCaseName + "test" + commandName + propertyName
	 * = propertyValue</code>.
	 * </p>
	 *
	 * <p>
	 * Example <code>test.generated.properties</code> output file:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * SOProfileTestCase.all.hook.plugins.includes=deploy-listener-hook,...
	 * SOProfileTestCase.all.portlet.plugins.includes=calendar-portlet,chat-...
	 * SOProfileTestCase.all.theme.plugins.includes=so-theme
	 * SOProfileTestCase.all.web.plugins.includes=resources-importer-web
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @throws Exception if an exception occurred
	 */
	private void _writeTestCasePropertiesFile() throws Exception {
		Set<String> testCaseProperties = new TreeSet<String>();

		Set<String> testCaseNames = _seleniumBuilderContext.getTestCaseNames();

		for (String testCaseName : testCaseNames) {
			Element rootElement =
				_seleniumBuilderContext.getTestCaseRootElement(testCaseName);

			List<Element> rootPropertyElements = rootElement.elements(
				"property");

			for (Element rootPropertyElement : rootPropertyElements) {
				StringBundler sb = new StringBundler();

				sb.append(testCaseName);
				sb.append("TestCase.all.");
				sb.append(rootPropertyElement.attributeValue("name"));
				sb.append("=");
				sb.append(rootPropertyElement.attributeValue("value"));

				testCaseProperties.add(sb.toString());
			}

			List<Element> commandElements =
				_seleniumBuilderFileUtil.getAllChildElements(
					rootElement, "command");

			for (Element commandElement : commandElements) {
				List<Element> commandPropertyElements =
					_seleniumBuilderFileUtil.getAllChildElements(
						commandElement, "property");

				for (Element commandPropertyElement : commandPropertyElements) {
					StringBundler sb = new StringBundler();

					sb.append(testCaseName);
					sb.append("TestCase.test");
					sb.append(commandElement.attributeValue("name"));
					sb.append(".");
					sb.append(commandPropertyElement.attributeValue("name"));
					sb.append("=");
					sb.append(commandPropertyElement.attributeValue("value"));

					testCaseProperties.add(sb.toString());
				}
			}
		}

		String testCasePropertiesString = StringUtil.merge(
			testCaseProperties.toArray(new String[testCaseProperties.size()]),
			StringPool.NEW_LINE);

		_seleniumBuilderFileUtil.writeFile(
			"../../../test.generated.properties", testCasePropertiesString,
			false);
	}

	private SeleniumBuilderContext _seleniumBuilderContext;
	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;

}