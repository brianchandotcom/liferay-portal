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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import com.liferay.poshi.runner.pql.PQLEntity;
import com.liferay.poshi.runner.pql.PQLEntityFactory;
import com.liferay.poshi.runner.selenium.LiferaySelenium;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.MathUtil;
import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.lang.reflect.Method;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerContext {

	public static void clear() {
		_actionExtendClassName.clear();
		_commandElements.clear();
		_commandReturns.clear();
		_commandSummaries.clear();
		_filePaths.clear();
		_filePathsList.clear();
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

		return getFunctionLocatorCount(
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

	public static List<String> getFilePathsList() {
		return _filePathsList;
	}

	public static Element getFunctionCommandElement(String classCommandName) {
		return _commandElements.get("function#" + classCommandName);
	}

	public static String getFunctionCommandSummary(String classCommandName) {
		return _commandSummaries.get("function#" + classCommandName);
	}

	public static int getFunctionLocatorCount(String className) {
		if (_functionLocatorCounts.get(className) == null) {
			return 0;
		}

		return _functionLocatorCounts.get(className);
	}

	public static Element getFunctionRootElement(String className) {
		return _rootElements.get("function#" + className);
	}

	public static Element getMacroCommandElement(String classCommandName) {
		return _commandElements.get("macro#" + classCommandName);
	}

	public static List<String> getMacroCommandReturns(String classCommandName) {
		return _commandReturns.get("macro#" + classCommandName);
	}

	public static String getMacroCommandSummary(String classCommandName) {
		return _commandSummaries.get("macro#" + classCommandName);
	}

	public static Element getMacroRootElement(String className) {
		return _rootElements.get("macro#" + className);
	}

	public static String getPathLocator(String pathLocatorKey)
		throws Exception {

		if (!_pathLocators.containsKey(pathLocatorKey)) {
			throw new Exception("No such locator key " + pathLocatorKey);
		}

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

	public static List<String> getTestCaseAvailablePropertyNames() {
		return _testCaseAvailablePropertyNames;
	}

	public static Element getTestCaseCommandElement(String classCommandName) {
		return _commandElements.get("test-case#" + classCommandName);
	}

	public static Element getTestCaseCommandElement(
		String className, String commandName) {

		String classCommandName = PoshiRunnerGetterUtil.getClassCommandName(
			className, commandName);

		return getTestCaseCommandElement(classCommandName);
	}

	public static String getTestCaseCommandName() {
		return _testClassCommandName;
	}

	public static String getTestCaseDescription(String classCommandName) {
		return _testCaseDescriptions.get(classCommandName);
	}

	public static String getTestCaseName() {
		return _testClassName;
	}

	public static List<String> getTestCaseRequiredPropertyNames() {
		return _testCaseRequiredPropertyNames;
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

	public static boolean isTestToggle(String toggleName) {
		return _testToggleNames.contains(toggleName);
	}

	public static void main(String[] args) throws Exception {
		readFiles();

		PoshiRunnerValidation.validate();

		_writeTestCaseMethodNamesProperties();
		_writeTestGeneratedProperties();
	}

	public static void readFiles() throws Exception {
		_readPoshiFiles();
		_readSeleniumFiles();
		_readTestToggleFiles();
	}

	public static void setTestCaseCommandName(String testClassCommandName) {
		_testClassCommandName = testClassCommandName;
	}

	public static void setTestCaseName(String testClassName) {
		_testClassName = testClassName;
	}

	private static void _addComponentClassCommandNames(
		String componentName, String classCommandName) {

		Set<String> classCommandNames = new TreeSet<>();

		classCommandNames.add(classCommandName);

		if (_componentClassCommandNames.containsKey(componentName)) {
			classCommandNames.addAll(
				_componentClassCommandNames.get(componentName));
		}

		_componentClassCommandNames.put(componentName, classCommandNames);
	}

	private static int _getAllocatedTestGroupSize(int testCount) {
		int groupCount = MathUtil.quotient(
			testCount, PropsValues.TEST_BATCH_MAX_GROUP_SIZE, true);

		return MathUtil.quotient(testCount, groupCount, true);
	}

	private static Properties _getClassCommandNameProperties(
			Element rootElement, Element commandElement)
		throws Exception {

		Properties properties = new Properties();

		List<Element> rootPropertyElements = rootElement.elements("property");

		for (Element propertyElement : rootPropertyElements) {
			String propertyName = propertyElement.attributeValue("name");
			String propertyValue = propertyElement.attributeValue("value");

			properties.setProperty(propertyName, propertyValue);
		}

		List<Element> commandPropertyElements = commandElement.elements(
			"property");

		for (Element propertyElement : commandPropertyElements) {
			String propertyName = propertyElement.attributeValue("name");
			String propertyValue = propertyElement.attributeValue("value");

			properties.setProperty(propertyName, propertyValue);
		}

		if (Validator.isNotNull(
				commandElement.attributeValue("known-issues"))) {

			String knownIssues = commandElement.attributeValue("known-issues");

			properties.setProperty("known-issues", knownIssues);
		}

		if (Validator.isNotNull(commandElement.attributeValue("priority"))) {
			String priority = commandElement.attributeValue("priority");

			properties.setProperty("priority", priority);
		}

		return properties;
	}

	private static List<String> _getCommandReturns(Element commandElement) {
		String returns = commandElement.attributeValue("returns");

		if (returns == null) {
			return new ArrayList<>();
		}

		return Arrays.asList(StringUtil.split(returns));
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

	private static List<String> _getFilePaths(String basedir, String[] includes)
		throws Exception {

		List<String> filePaths = new ArrayList<>();

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);
		directoryScanner.setIncludes(includes);

		directoryScanner.scan();

		for (String filePath : directoryScanner.getIncludedFiles()) {
			filePath = basedir + "/" + filePath;

			if (OSDetector.isWindows()) {
				filePath = filePath.replace("/", "\\");
			}

			filePaths.add(filePath);
		}

		return filePaths;
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

	private static String _getTestBatchGroups() throws Exception {
		String propertyQuery = PropsValues.TEST_BATCH_PROPERTY_QUERY;

		if (propertyQuery == null) {
			String[] propertyNames = PropsValues.TEST_BATCH_PROPERTY_NAMES;
			String[] propertyValues = PropsValues.TEST_BATCH_PROPERTY_VALUES;

			if (propertyNames.length != propertyValues.length) {
				throw new Exception(
					"'test.batch.property.names'" +
						"/'test.batch.property.values' must have matching " +
							"amounts of entries!");
			}

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < propertyNames.length; i++) {
				sb.append(propertyNames[i]);
				sb.append(" == \"");
				sb.append(propertyValues[i]);
				sb.append("\"");

				if (i < (propertyNames.length - 1)) {
					sb.append(" OR ");
				}
			}

			propertyQuery = sb.toString();
		}

		if (Validator.isNotNull(PropsValues.TEST_RUN_ENVIRONMENT)) {
			StringBuilder sb = new StringBuilder();

			sb.append(propertyQuery);
			sb.append(" AND ");
			sb.append("(test.run.environment == \"");
			sb.append(PropsValues.TEST_RUN_ENVIRONMENT);
			sb.append("\" OR test.run.environment == null)");

			propertyQuery = sb.toString();
		}

		List<String> classCommandNames = new ArrayList<>();

		PQLEntity pqlEntity = PQLEntityFactory.newPQLEntity(propertyQuery);

		for (String testCaseClassCommandName : _testCaseClassCommandNames) {
			Properties properties = _classCommandNamePropertiesMap.get(
				testCaseClassCommandName);

			Boolean pqlResultBoolean = (Boolean)pqlEntity.getPQLResult(
				properties);

			if (pqlResultBoolean) {
				classCommandNames.add(testCaseClassCommandName);
			}
		}

		System.out.println(
			"The following query returned " + classCommandNames.size() +
				" test class command names:");
		System.out.println(propertyQuery);

		if (PropsValues.TEST_BATCH_RUN_TYPE.equals("sequential")) {
			return _getTestBatchSequentialGroups(classCommandNames);
		}
		else if (PropsValues.TEST_BATCH_RUN_TYPE.equals("single")) {
			return _getTestBatchSingleGroups(classCommandNames);
		}

		throw new Exception(
			"'test.batch.run.type' must be set to 'single' or 'sequential'");
	}

	private static String _getTestBatchSequentialGroups(
			List<String> classCommandNames)
		throws Exception {

		Multimap<Properties, String> multimap = HashMultimap.create();

		for (String classCommandName : classCommandNames) {
			Properties properties = new Properties();

			properties.putAll(
				_classCommandNamePropertiesMap.get(classCommandName));

			if (Validator.isNotNull(
					PropsValues.TEST_BATCH_GROUP_IGNORE_REGEX)) {

				Set<String> propertyNames = properties.stringPropertyNames();

				for (String propertyName : propertyNames) {
					if (propertyName.matches(
							PropsValues.TEST_BATCH_GROUP_IGNORE_REGEX)) {

						properties.remove(propertyName);
					}
				}
			}

			multimap.put(properties, classCommandName);
		}

		Map<Integer, List<String>> classCommandNameGroups = new HashMap<>();
		int classCommandNameIndex = 0;
		Map<Properties, Collection<String>> map = multimap.asMap();

		for (Collection<String> value : map.values()) {
			List<String> classCommandNameGroup = new ArrayList(value);

			Collections.sort(classCommandNameGroup);

			int groupSize = _getAllocatedTestGroupSize(
				classCommandNameGroup.size());

			List<List<String>> partitions = Lists.partition(
				classCommandNameGroup, groupSize);

			for (int j = 0; j < partitions.size(); j++) {
				classCommandNameGroups.put(
					classCommandNameIndex, partitions.get(j));

				classCommandNameIndex++;
			}
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < classCommandNameGroups.size(); i++) {
			List<String> classCommandNameGroup = classCommandNameGroups.get(i);
			int subgroupSize = PropsValues.TEST_BATCH_MAX_SUBGROUP_SIZE;

			int subgroupCount = MathUtil.quotient(
				classCommandNameGroup.size(), subgroupSize, true);

			sb.append("RUN_TEST_CASE_METHOD_GROUP_");
			sb.append(i);
			sb.append("=");

			for (int j = 0; j < subgroupCount; j++) {
				sb.append(i);
				sb.append("_");
				sb.append(j);

				if (j < (subgroupCount - 1)) {
					sb.append(" ");
				}
			}

			sb.append("\n");

			for (int j = 0; j < classCommandNameGroup.size(); j++) {
				if ((j % subgroupSize) == 0) {
					sb.append("RUN_TEST_CASE_METHOD_GROUP_");
					sb.append(i);
					sb.append("_");
					sb.append(j / subgroupSize);
					sb.append("=");
					sb.append(classCommandNameGroup.get(j));
				}
				else if (((j + 1) % subgroupSize) == 0) {
					sb.append(",");
					sb.append(classCommandNameGroup.get(j));
					sb.append("\n");
				}
				else {
					sb.append(",");
					sb.append(classCommandNameGroup.get(j));
				}
			}

			sb.append("\n");
		}

		sb.append("RUN_TEST_CASE_METHOD_GROUPS=");

		for (int i = 0; i < classCommandNameGroups.size(); i++) {
			sb.append(i);

			if (i < (classCommandNameGroups.size() - 1)) {
				sb.append(" ");
			}
		}

		return sb.toString();
	}

	private static String _getTestBatchSingleGroups(
		List<String> classCommandNames) {

		StringBuilder sb = new StringBuilder();

		int groupSize = 15;

		List<List<String>> partitions = Lists.partition(
			classCommandNames, groupSize);

		for (int i = 0; i < partitions.size(); i++) {
			sb.append("RUN_TEST_CASE_METHOD_GROUP_");
			sb.append(i);
			sb.append("=");

			List<String> partition = partitions.get(i);

			for (int j = 0; j < partition.size(); j++) {
				sb.append(i);
				sb.append("_");
				sb.append(j);

				if (j < (partition.size() - 1)) {
					sb.append(" ");
				}
			}

			sb.append("\n");

			for (int j = 0; j < partition.size(); j++) {
				sb.append("RUN_TEST_CASE_METHOD_GROUP_");
				sb.append(i);
				sb.append("_");
				sb.append(j);
				sb.append("=");
				sb.append(partition.get(j));
				sb.append("\n");
			}
		}

		sb.append("RUN_TEST_CASE_METHOD_GROUPS=");

		for (int i = 0; i < partitions.size(); i++) {
			sb.append(i);

			if (i < (partitions.size() - 1)) {
				sb.append(" ");
			}
		}

		return sb.toString();
	}

	private static Set<String> _getTestCaseCommandNames(String className)
		throws Exception {

		Element rootElement = getTestCaseRootElement(className);

		List<Element> commandElements = rootElement.elements("command");

		Set<String> commandNames = new TreeSet<>();

		for (Element commandElement : commandElements) {
			String commandName = commandElement.attributeValue("name");

			commandNames.add(commandName);
		}

		return commandNames;
	}

	private static void _initComponentCommandNamesMap() {
		for (String testCaseClassName : _testCaseClassNames) {
			Element rootElement = getTestCaseRootElement(testCaseClassName);

			if (Objects.equals(rootElement.attributeValue("ignore"), "true")) {
				continue;
			}

			String componentName = rootElement.attributeValue("component-name");

			if (rootElement.attributeValue("extends") != null) {
				String extendsTestCaseClassName = rootElement.attributeValue(
					"extends");

				Element extendsRootElement = getTestCaseRootElement(
					extendsTestCaseClassName);

				List<Element> extendsCommandElements =
					extendsRootElement.elements("command");

				for (Element extendsCommandElement : extendsCommandElements) {
					String extendsCommandName =
						extendsCommandElement.attributeValue("name");

					if (_isIgnorableCommandNames(
							rootElement, extendsCommandElement,
							extendsCommandName)) {

						continue;
					}

					_addComponentClassCommandNames(
						componentName,
						testCaseClassName + "#" + extendsCommandName);

					_commandElements.put(
						"test-case#" + testCaseClassName + "#" +
							extendsCommandName,
						extendsCommandElement);
				}
			}

			List<Element> commandElements = rootElement.elements("command");

			for (Element commandElement : commandElements) {
				String commandName = commandElement.attributeValue("name");

				if (_isIgnorableCommandNames(
						rootElement, commandElement, commandName)) {

					continue;
				}

				String classCommandName = testCaseClassName + "#" + commandName;

				_testCaseClassCommandNames.add(classCommandName);

				if (commandElement.attributeValue("known-issues") != null) {
					for (String productName : _productNames) {
						if (componentName.startsWith(productName)) {
							_addComponentClassCommandNames(
								productName + "-known-issues",
								classCommandName);

							break;
						}
					}
				}
				else {
					_addComponentClassCommandNames(
						componentName, classCommandName);
				}
			}
		}
	}

	private static boolean _isIgnorableCommandNames(
		Element rootElement, Element commandElement, String commandName) {

		if (commandElement.attributeValue("ignore") != null) {
			String ignore = commandElement.attributeValue("ignore");

			if (ignore.equals("true")) {
				return true;
			}
		}

		List<String> ignorableCommandNames = new ArrayList<>();

		if (rootElement.attributeValue("ignore-command-names") != null) {
			String ignoreCommandNamesString = rootElement.attributeValue(
				"ignore-command-names");

			ignorableCommandNames = Arrays.asList(
				ignoreCommandNamesString.split(","));
		}

		if (ignorableCommandNames.contains(commandName)) {
			return true;
		}

		return false;
	}

	private static void _readPathFile(
			String filePath, String className, String extendedClassName)
		throws Exception {

		Element rootElement = PoshiRunnerGetterUtil.getRootElementFromFilePath(
			filePath);

		if (extendedClassName != null) {
			_rootElements.put("path#" + extendedClassName, rootElement);
		}
		else {
			_rootElements.put("path#" + className, rootElement);
		}

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
				for (String extendFilePath : _filePathsList) {
					String expectedExtendedPath = "/" + locator + ".path";

					if (OSDetector.isWindows()) {
						expectedExtendedPath = "\\" + locator + ".path";
					}

					if (extendFilePath.endsWith(expectedExtendedPath)) {
						_readPathFile(
							extendFilePath, className,
							PoshiRunnerGetterUtil.getClassNameFromFilePath(
								extendFilePath));

						break;
					}
				}

				_actionExtendClassName.put(className, locator);
			}

			_pathLocators.put(className + "#" + locatorKey, locator);
		}
	}

	private static void _readPoshiFile(String filePath) throws Exception {
		String className = PoshiRunnerGetterUtil.getClassNameFromFilePath(
			filePath);
		String classType = PoshiRunnerGetterUtil.getClassTypeFromFilePath(
			filePath);

		if (classType.equals("action") || classType.equals("function") ||
			classType.equals("macro") || classType.equals("test-case")) {

			Element rootElement =
				PoshiRunnerGetterUtil.getRootElementFromFilePath(filePath);

			_rootElements.put(classType + "#" + className, rootElement);

			if (classType.equals("test-case")) {
				_testCaseClassNames.add(className);
			}

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

				_commandReturns.put(
					classType + "#" + classCommandName,
					_getCommandReturns(commandElement));

				if (classType.equals("test-case")) {
					Properties properties = _getClassCommandNameProperties(
						rootElement, commandElement);

					_classCommandNamePropertiesMap.put(
						classCommandName, properties);

					if (Validator.isNotNull(
							commandElement.attributeValue("description"))) {

						_testCaseDescriptions.put(
							classCommandName,
							commandElement.attributeValue("description"));
					}
				}
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
			_readPathFile(filePath, className, null);
		}
	}

	private static void _readPoshiFiles() throws Exception {
		List<String> testBaseDirFilePaths = _getFilePaths(
			_TEST_BASE_DIR_NAME,
			new String[] {
				"**\\*.action", "**\\*.function", "**\\*.macro", "**\\*.path",
				"**\\*.testcase"
			});

		_filePathsList.addAll(testBaseDirFilePaths);

		String[] testIncludeDirNames = PropsValues.TEST_INCLUDE_DIR_NAMES;

		if (Validator.isNotNull(testIncludeDirNames)) {
			for (String testIncludeDirName : testIncludeDirNames) {
				if (Validator.isNull(testIncludeDirName)) {
					continue;
				}

				List<String> testIncludeDirFilePaths = _getFilePaths(
					testIncludeDirName,
					new String[] {
						"**\\*.action", "**\\*.function", "**\\*.macro",
						"**\\*.path"
					});

				_filePathsList.addAll(testIncludeDirFilePaths);
			}
		}

		for (String filePath : _filePathsList) {
			_filePaths.put(
				PoshiRunnerGetterUtil.getFileNameFromFilePath(filePath),
				filePath);

			_readPoshiFile(filePath);
		}

		_initComponentCommandNamesMap();
	}

	private static void _readSeleniumFiles() throws Exception {
		Method[] methods = LiferaySelenium.class.getMethods();

		for (Method method : methods) {
			Class<?>[] parameterTypes = method.getParameterTypes();

			_seleniumParameterCounts.put(
				method.getName(), parameterTypes.length);
		}

		_seleniumParameterCounts.put("open", 1);
	}

	private static void _readTestToggleFiles() throws Exception {
		for (String testToggleFileName : PropsValues.TEST_TOGGLE_FILE_NAMES) {
			if (!FileUtil.exists(testToggleFileName)) {
				continue;
			}

			String content = FileUtil.read(testToggleFileName);

			InputStream inputStream = new ByteArrayInputStream(
				content.getBytes("UTF-8"));

			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(inputStream);

			Element rootElement = document.getRootElement();

			List<Element> toggleElements = rootElement.elements("toggle");

			for (Element toggleElement : toggleElements) {
				String toggleName = toggleElement.attributeValue("name");

				Element dateElement = toggleElement.element("date");

				if (dateElement == null) {
					Exception exception = new Exception(
						"Please set a date for this toggle:\n" +
							testToggleFileName + ":" + toggleName);

					exception.printStackTrace();

					throw exception;
				}
				else {
					try {
						SimpleDateFormat simpleDateFormat =
							new SimpleDateFormat("YYYY-MM-dd");

						simpleDateFormat.parse(dateElement.getText());
					}
					catch (Exception e) {
						Exception exception = new Exception(
							"Please use the date format, YYYY-MM-dd, for " +
								"this toggle:\n" + testToggleFileName + ":" +
									toggleName,
							e);

						exception.printStackTrace();

						throw exception;
					}
				}

				Element ownerElement = toggleElement.element("owner");

				if ((ownerElement == null) ||
					Validator.isNull(ownerElement.getText())) {

					Exception exception = new Exception(
						"Please set an author for this toggle:\n" +
							testToggleFileName + ":" + toggleName);

					exception.printStackTrace();

					throw exception;
				}

				_testToggleNames.add(toggleName);
			}
		}

		System.out.println("Active Toggles:");

		for (String testToggleName : _testToggleNames) {
			System.out.println("* " + testToggleName);
		}

		System.out.println();
	}

	private static void _writeTestCaseMethodNamesProperties() throws Exception {
		StringBuilder sb = new StringBuilder();

		if ((PropsValues.TEST_BATCH_MAX_GROUP_SIZE > 0) &&
			(((PropsValues.TEST_BATCH_PROPERTY_NAMES != null) &&
			  (PropsValues.TEST_BATCH_PROPERTY_VALUES != null)) ||
			 (PropsValues.TEST_BATCH_PROPERTY_QUERY != null))) {

			sb.append(_getTestBatchGroups());
		}
		else {
			for (String componentName : _componentNames) {
				String componentNameKey =
					componentName + "_TEST_CASE_METHOD_NAMES";

				componentNameKey = StringUtil.upperCase(
					componentNameKey.replace("-", "_"));

				sb.append(componentNameKey);

				sb.append("=");

				Set<String> classCommandNames = _componentClassCommandNames.get(
					componentName);

				if (Validator.isNotNull(classCommandNames) &&
					!classCommandNames.isEmpty()) {

					Iterator<String> iterator = classCommandNames.iterator();

					while (iterator.hasNext()) {
						sb.append(iterator.next());

						if (iterator.hasNext()) {
							sb.append(" ");
						}
					}
				}
				else {
					sb.append(PropsValues.TEST_NAME);
				}

				sb.append("\n");
			}
		}

		FileUtil.write("test.case.method.names.properties", sb.toString());
	}

	private static void _writeTestGeneratedProperties() throws Exception {
		StringBuilder sb = new StringBuilder();

		for (String testCaseClassCommandName : _testCaseClassCommandNames) {
			Properties properties = _classCommandNamePropertiesMap.get(
				testCaseClassCommandName);
			String testClassName =
				PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
					testCaseClassCommandName);
			String testCommandName =
				PoshiRunnerGetterUtil.getCommandNameFromClassCommandName(
					testCaseClassCommandName);

			for (String propertyName : properties.stringPropertyNames()) {
				sb.append(testClassName);
				sb.append("TestCase.test");
				sb.append(testCommandName);
				sb.append(".");
				sb.append(propertyName);
				sb.append("=");
				sb.append(properties.getProperty(propertyName));
				sb.append("\n");
			}
		}

		FileUtil.write("test.generated.properties", sb.toString());
	}

	private static final String _TEST_BASE_DIR_NAME =
		PoshiRunnerGetterUtil.getCanonicalPath(PropsValues.TEST_BASE_DIR_NAME);

	private static final Map<String, String> _actionExtendClassName =
		new HashMap<>();
	private static final Map<String, Properties>
		_classCommandNamePropertiesMap = new HashMap<>();
	private static final Map<String, Element> _commandElements =
		new HashMap<>();
	private static final Map<String, List<String>> _commandReturns =
		new HashMap<>();
	private static final Map<String, String> _commandSummaries =
		new HashMap<>();
	private static final Map<String, Set<String>> _componentClassCommandNames =
		new TreeMap<>();
	private static final Set<String> _componentNames = new TreeSet<>();
	private static final Map<String, String> _filePaths = new HashMap<>();
	private static final List<String> _filePathsList = new ArrayList<>();
	private static final Map<String, Integer> _functionLocatorCounts =
		new HashMap<>();
	private static final Map<String, String> _pathLocators = new HashMap<>();
	private static final List<String> _productNames = new ArrayList<>();
	private static final Map<String, Element> _rootElements = new HashMap<>();
	private static final Map<String, Integer> _seleniumParameterCounts =
		new HashMap<>();
	private static final List<String> _testCaseAvailablePropertyNames =
		new ArrayList<>();
	private static final List<String> _testCaseClassCommandNames =
		new ArrayList<>();
	private static final List<String> _testCaseClassNames = new ArrayList<>();
	private static final Map<String, String> _testCaseDescriptions =
		new HashMap<>();
	private static final List<String> _testCaseRequiredPropertyNames =
		new ArrayList<>();
	private static String _testClassCommandName;
	private static String _testClassName;
	private static final Set<String> _testToggleNames = new HashSet<>();

	static {
		_componentNames.addAll(
			Arrays.asList(StringUtil.split(PropsValues.COMPONENT_NAMES)));

		_productNames.addAll(
			Arrays.asList(StringUtil.split(PropsValues.PRODUCT_NAMES)));

		for (String productName : _productNames) {
			_componentNames.add(productName);
			_componentNames.add(productName + "-known-issues");
		}

		String testCaseAvailablePropertyNames =
			PropsValues.TEST_CASE_AVAILABLE_PROPERTY_NAMES;

		if (Validator.isNotNull(testCaseAvailablePropertyNames)) {
			_testCaseAvailablePropertyNames.addAll(
				Arrays.asList(
					StringUtil.split(testCaseAvailablePropertyNames)));
		}

		_testCaseAvailablePropertyNames.add("known-issues");
		_testCaseAvailablePropertyNames.add("priority");
		_testCaseAvailablePropertyNames.add("test.run.environment");

		String testCaseRequiredPropertyNames =
			PropsValues.TEST_CASE_REQUIRED_PROPERTY_NAMES;

		if (Validator.isNotNull(testCaseRequiredPropertyNames)) {
			_testCaseRequiredPropertyNames.addAll(
				Arrays.asList(StringUtil.split(testCaseRequiredPropertyNames)));
		}
	}

}