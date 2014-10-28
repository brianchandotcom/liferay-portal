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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.ToolDependencies;

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
		ToolDependencies.wireBasic();

		new SeleniumBuilder(args);
	}

	/**
	 * Constructs a Selenium Builder based on the arguments.
	 *
	 * @param  args the command-line arguments
	 * @throws Exception if an exception occurred
	 */
	public SeleniumBuilder(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String baseDirName = arguments.get("selenium.base.dir");
		String projectDirName = arguments.get("project.dir");

		_seleniumBuilderFileUtil = new SeleniumBuilderFileUtil(
			baseDirName, projectDirName);

		_seleniumBuilderContext = new SeleniumBuilderContext(
			_seleniumBuilderFileUtil);

		Set<String> actionNames = _seleniumBuilderContext.getActionNames();

		for (String actionName : actionNames) {
			_seleniumBuilderContext.validateActionElements(actionName);
		}

		Set<String> functionNames = _seleniumBuilderContext.getFunctionNames();

		for (String functionName : functionNames) {
			_seleniumBuilderContext.validateFunctionElements(functionName);
		}

		Set<String> macroNames = _seleniumBuilderContext.getMacroNames();

		for (String macroName : macroNames) {
			_seleniumBuilderContext.validateMacroElements(macroName);
		}

		Set<String> testCaseNames = _seleniumBuilderContext.getTestCaseNames();

		for (String testCaseName : testCaseNames) {
			_seleniumBuilderContext.validateTestCaseElements(testCaseName);
		}

		Set<String> types = SetUtil.fromArray(
			StringUtil.split(arguments.get("selenium.types")));

		if (types.contains("action")) {
			ActionConverter actionConverter = new ActionConverter(
				_seleniumBuilderContext, _seleniumBuilderFileUtil);

			for (String actionName : actionNames) {
				actionConverter.convert(actionName);
			}
		}

		if (types.contains("function")) {
			FunctionConverter functionConverter = new FunctionConverter(
				_seleniumBuilderContext, _seleniumBuilderFileUtil);

			for (String functionName : functionNames) {
				functionConverter.convert(functionName);
			}
		}

		if (types.contains("macro")) {
			MacroConverter macroConverter = new MacroConverter(
				_seleniumBuilderContext, _seleniumBuilderFileUtil);

			for (String macroName : macroNames) {
				macroConverter.convert(macroName);
			}
		}

		if (types.contains("path")) {
			PathConverter pathConverter = new PathConverter(
				_seleniumBuilderContext, _seleniumBuilderFileUtil);

			Set<String> pathNames = _seleniumBuilderContext.getPathNames();

			for (String pathName : pathNames) {
				pathConverter.convert(pathName);
			}
		}

		if (types.contains("testcase")) {
			TestCaseConverter testCaseConverter = new TestCaseConverter(
				_seleniumBuilderContext, _seleniumBuilderFileUtil);

			String testClass = arguments.get("test.class");

			if (!testClass.equals("${test.class}")) {
				String testCaseCommandName = null;
				String testCaseName = null;

				if (testClass.contains("#")) {
					String[] testClassParts = StringUtil.split(testClass, "#");

					testCaseCommandName = testClassParts[1];
					testCaseName = testClassParts[0];
				}
				else {
					testCaseName = testClass;
				}

				if ((testCaseCommandName != null) &&
					testCaseCommandName.startsWith("test")) {

					testCaseCommandName = StringUtil.replaceFirst(
						testCaseCommandName, "test", "");
				}

				if (testCaseName.endsWith("TestCase")) {
					testCaseName = StringUtil.replaceLast(
						testCaseName, "TestCase", "");
				}

				Element rootElement =
					_seleniumBuilderContext.getTestCaseRootElement(
						testCaseName);

				String extendsTestCaseName = rootElement.attributeValue(
					"extends");

				if (extendsTestCaseName != null) {
					testCaseConverter.convert(
						extendsTestCaseName, testCaseCommandName);
				}

				testCaseConverter.convert(testCaseName, testCaseCommandName);
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

	private boolean _isCommandNameOverridden(
		Element rootElement, String commandName) {

		List<Element> commandElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "command");

		for (Element commandElement : commandElements) {
			if (commandName.equals(commandElement.attributeValue("name"))) {
				return true;
			}
		}

		return false;
	}

	private boolean _isIgnoreCommandName(
		Element rootElement, String commandName) {

		String ignoreCommandNamesString = rootElement.attributeValue(
			"ignore-command-names");

		if (ignoreCommandNamesString == null) {
			return false;
		}

		String[] ignoreCommandNames = StringUtil.split(
			ignoreCommandNamesString);

		ignoreCommandNamesString = StringUtil.replace(
			ignoreCommandNamesString, new String[] {" ", "\n", "\t"},
			new String[] {"", "", ""});

		return ArrayUtil.contains(ignoreCommandNames, commandName);
	}

	/**
	 * Writes lists of all test case method names, aggregated by component, to a
	 * properties file named <code>test.case.method.names.properties</code>.
	 *
	 * <p>
	 * Each property follows the format: <code>componentName +
	 * "_TEST_CASE_METHOD_NAMES=" + testCaseName + "TestCase#test" +
	 * commandName</code>
	 * </p>
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
					String commandName = commandElement.attributeValue("name");

					if (_isCommandNameOverridden(rootElement, commandName)) {
						continue;
					}

					if (_isIgnoreCommandName(rootElement, commandName)) {
						continue;
					}

					String testCaseMethodName =
						testCaseName + "TestCase#test" + commandName;

					compontentTestCaseMethodNames.add(testCaseMethodName);
				}
			}

			List<Element> commandElements =
				_seleniumBuilderFileUtil.getAllChildElements(
					rootElement, "command");

			for (Element commandElement : commandElements) {
				String testCaseMethodName =
					testCaseName + "TestCase#test" +
						commandElement.attributeValue("name");

				String knownIssues = commandElement.attributeValue(
					"known-issues");

				if (knownIssues != null) {
					String knownIssuesComponent = "portal-known-issues";

					if (componentName.startsWith("marketplace")) {
						knownIssuesComponent = "marketplace-known-issues";
					}
					else if (componentName.startsWith("social-office")) {
						knownIssuesComponent = "social-office-known-issues";
					}

					Set<String> knownIssuesTestCaseMethodNames =
						new TreeSet<String>();

					if (testCaseMethodNameMap.containsKey(
							knownIssuesComponent)) {

						knownIssuesTestCaseMethodNames =
							testCaseMethodNameMap.get(knownIssuesComponent);
					}

					knownIssuesTestCaseMethodNames.add(testCaseMethodName);

					testCaseMethodNameMap.put(
						knownIssuesComponent, knownIssuesTestCaseMethodNames);
				}
				else {
					compontentTestCaseMethodNames.add(testCaseMethodName);
				}
			}

			if (!compontentTestCaseMethodNames.isEmpty()) {
				testCaseMethodNameMap.put(
					componentName, compontentTestCaseMethodNames);
			}
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

				String testCaseMethodNamesString = StringUtil.merge(
					compontentTestCaseMethodNames.toArray(
						new String[compontentTestCaseMethodNames.size()]),
					StringPool.SPACE);

				sb.append(testCaseMethodNamesString);
				sb.append("\n");
			}
			else {
				sb.append("PortalSmokeTestCase#testSmoke\n");
			}
		}

		_seleniumBuilderFileUtil.writeFile(
			"../../../test.case.method.names.properties", sb.toString(), false);
	}

	/**
	 * Writes lists of all test case method properties, aggregated by test case
	 * definition scope and test case command scope, to a properties file named
	 * <code>test.generated.properties</code>.
	 *
	 * <p>
	 * The test case method properties scoped to test case definitions follow
	 * the format: <code>testCaseName + "TestCase.all." + propertyName =
	 * propertyValue</code>
	 * </p>
	 *
	 * <p>
	 * The test case method properties scoped to test case commands follow the
	 * format: <code>testCaseName + "TestCase.test" + commandName + "." +
	 * propertyName = propertyValue</code>
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

			StringBundler sb = new StringBundler();

			sb.append(testCaseName);
			sb.append("TestCase.all.testray.testcase.product.edition=CE");

			testCaseProperties.add(sb.toString());

			for (Element rootPropertyElement : rootPropertyElements) {
				sb = new StringBundler();

				sb.append(testCaseName);
				sb.append("TestCase.all.");

				String rootPropertyName = rootPropertyElement.attributeValue(
					"name");

				sb.append(rootPropertyName);

				sb.append("=");
				sb.append(rootPropertyElement.attributeValue("value"));

				testCaseProperties.add(sb.toString());

				String rootPropertyDelimiter =
					rootPropertyElement.attributeValue("delimiter");

				if ((rootPropertyDelimiter != null) &&
					rootPropertyName.equals("ignore.errors")) {

					sb = new StringBundler();

					sb.append(testCaseName);
					sb.append("TestCase.all.");
					sb.append(rootPropertyName);
					sb.append(".delimiter=");
					sb.append(rootPropertyDelimiter);

					testCaseProperties.add(sb.toString());
				}
			}

			List<Element> commandElements =
				_seleniumBuilderFileUtil.getAllChildElements(
					rootElement, "command");

			for (Element commandElement : commandElements) {
				List<Element> commandPropertyElements =
					_seleniumBuilderFileUtil.getAllChildElements(
						commandElement, "property");

				for (Element commandPropertyElement : commandPropertyElements) {
					sb = new StringBundler();

					sb.append(testCaseName);
					sb.append("TestCase.test");
					sb.append(commandElement.attributeValue("name"));
					sb.append(".");
					sb.append(commandPropertyElement.attributeValue("name"));
					sb.append("=");
					sb.append(commandPropertyElement.attributeValue("value"));

					testCaseProperties.add(sb.toString());
				}

				sb = new StringBundler();

				sb.append(testCaseName);
				sb.append("TestCase.test");
				sb.append(commandElement.attributeValue("name"));
				sb.append(".testray.testcase.description=");

				if (commandElement.attributeValue("description") != null) {
					sb.append(commandElement.attributeValue("description"));
				}

				testCaseProperties.add(sb.toString());

				sb = new StringBundler();

				sb.append(testCaseName);
				sb.append("TestCase.test");
				sb.append(commandElement.attributeValue("name"));
				sb.append(".testray.testcase.name=");
				sb.append(testCaseName);
				sb.append("#");
				sb.append(commandElement.attributeValue("name"));

				testCaseProperties.add(sb.toString());

				sb = new StringBundler();

				sb.append(testCaseName);
				sb.append("TestCase.test");
				sb.append(commandElement.attributeValue("name"));
				sb.append(".testray.testcase.priority=");
				sb.append(commandElement.attributeValue("priority"));

				testCaseProperties.add(sb.toString());
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