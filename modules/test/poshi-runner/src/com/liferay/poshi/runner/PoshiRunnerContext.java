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

import com.liferay.poshi.runner.selenium.LiferaySelenium;
import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerContext {

	public static void clear() {
		_actionExtendClassName.clear();
		_commandElements.clear();
		_commandSummaries.clear();
		_filePaths.clear();
		_functionLocatorCounts.clear();
		_pathLocators.clear();
		_rootElements.clear();
		_seleniumParameterCounts.clear();
	}

	public static List<Element> getActionCaseElements(String classCommandName) {
		List<Element> actionCaseElements = new ArrayList<>();

		List<String> relatedClassCommandNames =
			_getRelatedActionClassCommandNames(classCommandName);

		for (String relatedClassCommandName : relatedClassCommandNames) {
			Element commandElement = getActionCommandElement(
				relatedClassCommandName);

			if (commandElement != null) {
				List<Element> caseElements = commandElement.elements();

				for (Element caseElement : caseElements) {
					actionCaseElements.add(caseElement);
				}
			}
		}

		return actionCaseElements;
	}

	public static Element getActionCommandElement(String classCommandName) {
		return _commandElements.get("action#" + classCommandName);
	}

	public static String getActionCommandSummary(String classCommandName) {
		return _commandSummaries.get("action#" + classCommandName);
	}

	public static int getActionLocatorCount(String classCommandName) {
		String commandName =
			PoshiRunnerGetterUtil.getCommandNameFromClassCommandName(
				classCommandName);

		return PoshiRunnerContext.getFunctionLocatorCount(
			StringUtil.upperCaseFirstLetter(commandName));
	}

	public static Element getActionRootElement(String className) {
		return _rootElements.get("action#" + className);
	}

	public static String getFilePathFromClassKey(String classKey) {
		String fileName = PoshiRunnerGetterUtil.getFileNameFromClassKey(
			classKey);

		return _filePaths.get(fileName);
	}

	public static String getFilePathFromFileName(String fileName) {
		return _filePaths.get(fileName);
	}

	public static String[] getFilePathsArray() {
		return _filePathsArray;
	}

	public static Element getFunctionCommandElement(String classCommandName) {
		return _commandElements.get("function#" + classCommandName);
	}

	public static String getFunctionCommandSummary(String classCommandName) {
		return _commandSummaries.get("function#" + classCommandName);
	}

	public static int getFunctionLocatorCount(String className) {
		return _functionLocatorCounts.get(className);
	}

	public static Element getFunctionRootElement(String className) {
		return _rootElements.get("function#" + className);
	}

	public static Element getMacroCommandElement(String classCommandName) {
		return _commandElements.get("macro#" + classCommandName);
	}

	public static String getMacroCommandSummary(String classCommandName) {
		return _commandSummaries.get("macro#" + classCommandName);
	}

	public static Element getMacroRootElement(String className) {
		return _rootElements.get("macro#" + className);
	}

	public static String getPathLocator(String pathLocatorKey) {
		return _pathLocators.get(pathLocatorKey);
	}

	public static Element getPathRootElement(String className) {
		return _rootElements.get("path#" + className);
	}

	public static Map<String, Element> getRootElementsMap() {
		return _rootElements;
	}

	public static int getSeleniumParameterCount(String commandName) {
		return _seleniumParameterCounts.get(commandName);
	}

	public static Element getTestCaseCommandElement(String classCommandName) {
		return _commandElements.get("test-case#" + classCommandName);
	}

	public static String getTestCaseCommandName() {
		return _testClassCommandName;
	}

	public static String getTestCaseName() {
		return _testClassName;
	}

	public static Element getTestCaseRootElement(String className) {
		return _rootElements.get("test-case#" + className);
	}

	public static boolean isCommandElement(String commandElementKey) {
		return _commandElements.containsKey(commandElementKey);
	}

	public static boolean isPathLocator(String pathLocatorKey) {
		return _pathLocators.containsKey(pathLocatorKey);
	}

	public static boolean isRootElement(String rootElementKey) {
		return _rootElements.containsKey(rootElementKey);
	}

	public static void readFiles() throws Exception {
		_readPoshiFiles();
		_readSeleniumFiles();
	}

	public static void setTestCaseCommandName(String testClassCommandName) {
		_testClassCommandName = testClassCommandName;
	}

	public static void setTestCaseName(String testClassName) {
		_testClassName = testClassName;
	}

	private static String _getCommandSummary(
		String classCommandName, String classType, Element commandElement) {

		String summaryIgnore = commandElement.attributeValue("summary-ignore");

		if (Validator.isNotNull(summaryIgnore) &&
			summaryIgnore.equals("true")) {

			return null;
		}

		if (Validator.isNotNull(commandElement.attributeValue("summary"))) {
			return commandElement.attributeValue("summary");
		}

		if (classType.equals("function")) {
			String className =
				PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
					classCommandName);

			Element rootElement = getFunctionRootElement(className);

			if (Validator.isNotNull(rootElement.attributeValue("summary"))) {
				return rootElement.attributeValue("summary");
			}
		}

		return classCommandName;
	}

	private static List<String> _getRelatedActionClassCommandNames(
		String classCommandName) {

		List<String> relatedClassCommandNames = new ArrayList<>();

		relatedClassCommandNames.add(classCommandName);

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);
		String commandName =
			PoshiRunnerGetterUtil.getCommandNameFromClassCommandName(
				classCommandName);

		while (_actionExtendClassName.get(className) != null) {
			String extendClassName = _actionExtendClassName.get(className);

			relatedClassCommandNames.add(extendClassName + "#" + commandName);

			className = extendClassName;
		}

		relatedClassCommandNames.add("BaseLiferay#" + commandName);

		return relatedClassCommandNames;
	}

	private static void _readPathFile(String filePath, String className)
		throws Exception {

		Element rootElement = PoshiRunnerGetterUtil.getRootElementFromFilePath(
			filePath);

		_rootElements.put("path#" + className, rootElement);

		Element bodyElement = rootElement.element("body");

		Element tableElement = bodyElement.element("table");

		Element tBodyElement = tableElement.element("tbody");

		List<Element> trElements = tBodyElement.elements("tr");

		for (Element trElement : trElements) {
			List<Element> tdElements = trElement.elements("td");

			Element locatorKeyElement = tdElements.get(0);

			String locatorKey = locatorKeyElement.getText();

			Element locatorElement = tdElements.get(1);

			String locator = locatorElement.getText();

			if (locatorKey.equals("EXTEND_ACTION_PATH")) {
				for (String extendFilePath : _filePathsArray) {
					String expectedExtendedPath = "/" + locator + ".path";

					if (OSDetector.isWindows()) {
						expectedExtendedPath = "\\" + locator + ".path";
					}

					if (extendFilePath.endsWith(expectedExtendedPath)) {
						extendFilePath = _BASE_DIR + "/" + extendFilePath;

						_readPathFile(extendFilePath, className);

						break;
					}
				}

				_actionExtendClassName.put(className, locator);
			}

			_pathLocators.put(className + "#" + locatorKey, locator);
		}
	}

	private static void _readPoshiFiles() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_BASE_DIR);
		directoryScanner.setIncludes(
			new String[] {
				"**\\*.action", "**\\*.function", "**\\*.macro", "**\\*.path",
				"**\\*.testcase"
			});

		directoryScanner.scan();

		_filePathsArray = directoryScanner.getIncludedFiles();

		for (String filePath : _filePathsArray) {
			filePath = _BASE_DIR + "/" + filePath;

			if (OSDetector.isWindows()) {
				filePath = filePath.replace("/", "\\");
			}

			_filePaths.put(
				PoshiRunnerGetterUtil.getFileNameFromFilePath(filePath),
				filePath);

			String className = PoshiRunnerGetterUtil.getClassNameFromFilePath(
				filePath);
			String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
				filePath);

			if (classType.equals("action") || classType.equals("function") ||
				classType.equals("macro") || classType.equals("test-case")) {

				Element rootElement =
					PoshiRunnerGetterUtil.getRootElementFromFilePath(filePath);

				_rootElements.put(classType + "#" + className, rootElement);

				if (rootElement.element("set-up") != null) {
					Element setUpElement = rootElement.element("set-up");

					String classCommandName = className + "#set-up";

					_commandElements.put(
						classType + "#" + classCommandName, setUpElement);
				}

				if (rootElement.element("tear-down") != null) {
					Element tearDownElement = rootElement.element("tear-down");

					String classCommandName = className + "#tear-down";

					_commandElements.put(
						classType + "#" + classCommandName, tearDownElement);
				}

				List<Element> commandElements = rootElement.elements("command");

				for (Element commandElement : commandElements) {
					String classCommandName =
						className + "#" + commandElement.attributeValue("name");

					if (isCommandElement(classType + "#" + classCommandName)) {
						throw new Exception(
							"Duplicate command name\n" + filePath + ":" +
								commandElement.attributeValue("line-number"));
					}

					_commandElements.put(
						classType + "#" + classCommandName, commandElement);

					_commandSummaries.put(
						classType + "#" + classCommandName,
						_getCommandSummary(
							classCommandName, classType, commandElement));
				}

				if (classType.equals("function")) {
					String defaultClassCommandName =
						className + "#" + rootElement.attributeValue("default");

					Element defaultCommandElement = getFunctionCommandElement(
						defaultClassCommandName);

					_commandElements.put(
						classType + "#" + className, defaultCommandElement);

					_commandSummaries.put(
						classType + "#" + className,
						_getCommandSummary(
							defaultClassCommandName, classType,
							defaultCommandElement));

					String xml = rootElement.asXML();

					for (int i = 1;; i++) {
						if (xml.contains("${locator" + i + "}")) {
							continue;
						}

						if (i > 1) {
							i--;
						}

						_functionLocatorCounts.put(className, i);

						break;
					}
				}
			}
			else if (classType.equals("path")) {
				_readPathFile(filePath, className);
			}
		}
	}

	private static void _readSeleniumFiles() throws Exception {
		Method[] methods = LiferaySelenium.class.getMethods();

		Map<String, Integer> seleniumParameterCounts = new HashMap<>();

		for (Method method : methods) {
			Class[] parameterTypes = method.getParameterTypes();

			_seleniumParameterCounts.put(
				method.getName(), parameterTypes.length);
		}

		_seleniumParameterCounts.put("open", 1);
	}

	private static final String _BASE_DIR =
		PoshiRunnerGetterUtil.getCanonicalPath(PropsValues.TEST_BASE_DIR_NAME);

	private static final Map<String, String> _actionExtendClassName =
		new HashMap<>();
	private static final Map<String, Element> _commandElements =
		new HashMap<>();
	private static final Map<String, String> _commandSummaries =
		new HashMap<>();
	private static final Map<String, String> _filePaths = new HashMap<>();
	private static String[] _filePathsArray;
	private static final Map<String, Integer> _functionLocatorCounts =
		new HashMap<>();
	private static final Map<String, String> _pathLocators = new HashMap<>();
	private static final Pattern _pattern = Pattern.compile(
		"public [a-z]* [A-Za-z0-9_]*\\(.*?\\)");
	private static final Map<String, Element> _rootElements = new HashMap<>();
	private static final Map<String, Integer> _seleniumParameterCounts =
		new HashMap<>();
	private static String _testClassCommandName;
	private static String _testClassName;

}