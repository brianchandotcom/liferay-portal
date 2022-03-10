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

package com.liferay.poshi.core;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import com.liferay.poshi.core.pql.PQLEntity;
import com.liferay.poshi.core.pql.PQLEntityFactory;
import com.liferay.poshi.core.prose.PoshiProseMatcher;
import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.selenium.LiferaySelenium;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.MathUtil;
import com.liferay.poshi.core.util.OSDetector;
import com.liferay.poshi.core.util.PropsUtil;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.core.util.StringUtil;
import com.liferay.poshi.core.util.Validator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiContext {

	public static final String[] POSHI_SUPPORT_FILE_INCLUDES = {
		"**/*.action", "**/*.function", "**/*.macro", "**/*.path"
	};

	public static final String[] POSHI_TEST_FILE_INCLUDES = {
		"**/*.prose", "**/*.testcase"
	};

	public static void addPoshiPropertyNames(Set<String> poshiPropertyNames) {
		_poshiPropertyNames.addAll(poshiPropertyNames);
	}

	public static void clear() {
		_commandElements.clear();
		_commandSummaries.clear();
		_filePaths.clear();
		_functionFileNames.clear();
		_functionLocatorCounts.clear();
		_macroFileNames.clear();
		_namespacedClassCommandNamePropertiesMap.clear();
		_namespaces.clear();
		_overrideClassNames.clear();
		_pathExtensions.clear();
		_pathLocators.clear();
		_poshiPropertyNames.clear();
		_rootElements.clear();
		_rootVarElements.clear();
		_seleniumParameterCounts.clear();
		_testCaseDescriptions.clear();
		_testCaseNamespacedClassCommandNames.clear();
		_testCaseNamespacedClassNames.clear();
	}

	public static List<String> executePQLQuery() throws Exception {
		return executePQLQuery(PropsValues.TEST_BATCH_PROPERTY_QUERY, true);
	}

	public static List<String> executePQLQuery(String query, boolean readFiles)
		throws Exception {

		if (readFiles) {
			clear();

			readFiles();
		}

		StringBuilder sb = new StringBuilder();

		sb.append("(");
		sb.append(query);
		sb.append(") AND (ignored == null)");

		if (Validator.isNotNull(PropsValues.TEST_RUN_ENVIRONMENT)) {
			sb.append(" AND (test.run.environment == \"");
			sb.append(PropsValues.TEST_RUN_ENVIRONMENT);
			sb.append("\" OR test.run.environment == null)");
		}

		query = sb.toString();

		List<String> classCommandNames = new ArrayList<>();

		PQLEntity pqlEntity = PQLEntityFactory.newPQLEntity(query);

		for (String testCaseNamespacedClassCommandName :
				_testCaseNamespacedClassCommandNames) {

			Properties properties =
				_namespacedClassCommandNamePropertiesMap.get(
					testCaseNamespacedClassCommandName);

			Boolean pqlResultBoolean = (Boolean)pqlEntity.getPQLResult(
				properties);

			if (pqlResultBoolean) {
				classCommandNames.add(testCaseNamespacedClassCommandName);
			}
		}

		sb = new StringBuilder();

		sb.append("The following query returned ");
		sb.append(classCommandNames.size());
		sb.append(" test class command names:\n");
		sb.append(query);
		sb.append("\n");

		System.out.println(sb.toString());

		return classCommandNames;
	}

	public static String getDefaultNamespace() {
		return _DEFAULT_NAMESPACE;
	}

	public static String getFilePathFromFileName(
		String fileName, String namespace) {

		return _filePaths.get(namespace + "." + fileName);
	}

	public static List<String> getFilePaths() {
		return new ArrayList<>(_filePaths.values());
	}

	public static Element getFunctionCommandElement(
		String classCommandName, String namespace) {

		return _commandElements.get(
			"function#" + namespace + "." + classCommandName);
	}

	public static String getFunctionCommandSummary(
		String classCommandName, String namespace) {

		return _commandSummaries.get(
			"function#" + namespace + "." + classCommandName);
	}

	public static Set<String> getFunctionFileNames() {
		return _functionFileNames;
	}

	public static int getFunctionLocatorCount(
		String className, String namespace) {

		String functionLocatorCountKey = namespace + "." + className;

		if (_functionLocatorCounts.containsKey(functionLocatorCountKey)) {
			return _functionLocatorCounts.get(functionLocatorCountKey);
		}

		return 0;
	}

	public static int getFunctionMaxArgumentCount() {
		return _MAX_ARGUMENT_COUNT;
	}

	public static Element getFunctionRootElement(
		String className, String namespace) {

		return _rootElements.get("function#" + namespace + "." + className);
	}

	public static Element getMacroCommandElement(
		String classCommandName, String namespace) {

		return _commandElements.get(
			"macro#" + namespace + "." + classCommandName);
	}

	public static String getMacroCommandSummary(
		String classCommandName, String namespace) {

		return _commandSummaries.get(
			"macro#" + namespace + "." + classCommandName);
	}

	public static Set<String> getMacroFileNames() {
		return _macroFileNames;
	}

	public static Element getMacroRootElement(
		String className, String namespace) {

		return _rootElements.get("macro#" + namespace + "." + className);
	}

	public static Properties getNamespacedClassCommandNameProperties(
		String testCaseNamespacedClassCommandName) {

		return _namespacedClassCommandNamePropertiesMap.get(
			testCaseNamespacedClassCommandName);
	}

	public static String getNamespaceFromFilePath(String filePath) {
		if (Validator.isNull(filePath)) {
			return _DEFAULT_NAMESPACE;
		}

		for (Map.Entry<String, String> entry : _filePaths.entrySet()) {
			String value = entry.getValue();

			if (value.equals(filePath)) {
				String key = entry.getKey();
				String fileName = PoshiGetterUtil.getFileNameFromFilePath(
					filePath);

				return key.substring(0, key.indexOf("." + fileName));
			}
		}

		return _DEFAULT_NAMESPACE;
	}

	public static List<String> getNamespaces() {
		return _namespaces;
	}

	public static String getOverrideClassName(String namespacedClassName) {
		return _overrideClassNames.get(namespacedClassName);
	}

	public static String getPathLocator(
		String pathLocatorKey, String namespace) {

		String pathLocator = _pathLocators.get(
			namespace + "." + pathLocatorKey);

		String className =
			PoshiGetterUtil.getClassNameFromNamespacedClassCommandName(
				pathLocatorKey);

		if ((pathLocator == null) &&
			_pathExtensions.containsKey(namespace + "." + className)) {

			String pathExtension = _pathExtensions.get(
				namespace + "." + className);
			String commandName =
				PoshiGetterUtil.getCommandNameFromNamespacedClassCommandName(
					pathLocatorKey);

			return getPathLocator(pathExtension + "#" + commandName, namespace);
		}

		return pathLocator;
	}

	public static Element getPathRootElement(
		String className, String namespace) {

		return _rootElements.get("path#" + namespace + "." + className);
	}

	public static List<String> getPoshiPropertyNames() {
		Set<String> poshiPropertyNames = new HashSet<>(_poshiPropertyNames);

		poshiPropertyNames.add("ignored");
		poshiPropertyNames.add("known-issues");
		poshiPropertyNames.add("priority");
		poshiPropertyNames.add("property.group");
		poshiPropertyNames.add("test.class.method.name");
		poshiPropertyNames.add("test.class.name");
		poshiPropertyNames.add("test.run.environment");

		String testCaseAvailablePropertyNames =
			PropsValues.TEST_CASE_AVAILABLE_PROPERTY_NAMES;

		if (Validator.isNotNull(testCaseAvailablePropertyNames)) {
			Collections.addAll(
				poshiPropertyNames,
				StringUtil.split(testCaseAvailablePropertyNames));
		}

		String testCaseRequiredPropertyNames =
			PropsValues.TEST_CASE_REQUIRED_PROPERTY_NAMES;

		if (Validator.isNotNull(testCaseRequiredPropertyNames)) {
			Collections.addAll(
				poshiPropertyNames,
				StringUtil.split(testCaseRequiredPropertyNames));
		}

		return new ArrayList<>(poshiPropertyNames);
	}

	public static List<String> getRequiredPoshiPropertyNames() {
		List<String> requiredPoshiPropertyNames = new ArrayList<>();

		String testCaseRequiredPropertyNames =
			PropsValues.TEST_CASE_REQUIRED_PROPERTY_NAMES;

		if (Validator.isNotNull(testCaseRequiredPropertyNames)) {
			Collections.addAll(
				requiredPoshiPropertyNames,
				StringUtil.split(testCaseRequiredPropertyNames));
		}

		return requiredPoshiPropertyNames;
	}

	public static List<Element> getRootVarElements(
		String classType, String className, String namespace) {

		return _rootVarElements.get(
			classType + "#" + namespace + "." + className);
	}

	public static int getSeleniumParameterCount(String commandName) {
		return _seleniumParameterCounts.get(commandName);
	}

	public static List<List<String>> getTestBatchGroups(
			String propertyQuery, int maxGroupSize)
		throws Exception {

		if (maxGroupSize <= 0) {
			maxGroupSize = 1;
		}

		List<String> classCommandNames = executePQLQuery(propertyQuery, false);

		Multimap<Properties, String> multimap = HashMultimap.create();

		for (String classCommandName : classCommandNames) {
			Properties properties = new Properties();

			properties.putAll(
				_namespacedClassCommandNamePropertiesMap.get(classCommandName));

			String testBatchGroupIgnoreRegex = PropsUtil.get(
				"test.batch.group.ignore.regex");

			if (Validator.isNotNull(testBatchGroupIgnoreRegex)) {
				Set<String> propertyNames = properties.stringPropertyNames();

				for (String propertyName : propertyNames) {
					if (propertyName.matches(testBatchGroupIgnoreRegex)) {
						properties.remove(propertyName);
					}
				}
			}

			properties.remove("test.class.method.name");
			properties.remove("test.class.name");

			multimap.put(properties, classCommandName);
		}

		Map<Properties, Collection<String>> map = multimap.asMap();

		Map<String, List<String>> orderedTestBatchGroups = new TreeMap<>();

		for (Collection<String> value : map.values()) {
			List<String> classCommandNameGroup = new ArrayList<>(value);

			Collections.sort(classCommandNameGroup);

			int testCount = classCommandNameGroup.size();

			int groupCount = MathUtil.quotient(testCount, maxGroupSize, true);

			int groupSize = MathUtil.quotient(testCount, groupCount, true);

			List<List<String>> testBatchGroups = Lists.partition(
				classCommandNameGroup, groupSize);

			for (List<String> testBatchGroup : testBatchGroups) {
				orderedTestBatchGroups.put(
					testBatchGroup.get(0), testBatchGroup);
			}
		}

		return new ArrayList<>(orderedTestBatchGroups.values());
	}

	public static Element getTestCaseCommandElement(
		String classCommandName, String namespace) {

		return _commandElements.get(
			"test-case#" + namespace + "." + classCommandName);
	}

	public static String getTestCaseDescription(String classCommandName) {
		return _testCaseDescriptions.get(classCommandName);
	}

	public static String getTestCaseNamespacedClassCommandName() {
		return _testCaseNamespacedClassCommandName;
	}

	public static Element getTestCaseRootElement(
		String className, String namespace) {

		return _rootElements.get("test-case#" + namespace + "." + className);
	}

	public static boolean isCommandElement(
		String classType, String classCommandName, String namespace) {

		return _commandElements.containsKey(
			classType + "#" + namespace + "." + classCommandName);
	}

	public static boolean isPathLocator(
		String pathLocatorKey, String namespace) {

		String pathLocator = getPathLocator(pathLocatorKey, namespace);

		if (pathLocator != null) {
			return true;
		}

		return false;
	}

	public static boolean isRootElement(
		String classType, String rootElementKey, String namespace) {

		return _rootElements.containsKey(
			classType + "#" + namespace + "." + rootElementKey);
	}

	public static void main(String[] args) throws Exception {
		readFiles();

		PoshiValidation.validate();

		_writeTestCaseMethodNamesProperties();
		_writeTestCSVReportFile();
		_writeTestGeneratedProperties();
	}

	public static void readFiles() throws Exception {
		readFiles(null);
	}

	public static void readFiles(String[] includes, String... baseDirNames)
		throws Exception {

		System.out.println("Start reading Poshi files.");

		long start = System.currentTimeMillis();

		if (includes == null) {
			includes = POSHI_TEST_FILE_INCLUDES;
		}

		Set<String> poshiFileIncludes = new HashSet<>();

		Collections.addAll(poshiFileIncludes, includes);
		Collections.addAll(poshiFileIncludes, POSHI_SUPPORT_FILE_INCLUDES);

		_readPoshiFilesFromClassPath(
			poshiFileIncludes.toArray(new String[0]), "default/testFunctional",
			"testFunctional");

		if (((baseDirNames == null) || (baseDirNames.length == 0)) &&
			(Validator.isNull(PropsValues.TEST_BASE_DIR_NAME) ||
			 PropsValues.TEST_BASE_DIR_NAME.isEmpty())) {

			throw new RuntimeException("Please set 'test.base.dir.name'");
		}

		Set<URL> poshiURLs = new HashSet<>();

		Set<String> testDirNames = new HashSet<>();

		if (Validator.isNotNull(baseDirNames)) {
			Collections.addAll(testDirNames, baseDirNames);
		}

		if (Validator.isNotNull(PropsValues.TEST_BASE_DIR_NAME)) {
			testDirNames.add(PropsValues.TEST_BASE_DIR_NAME);
		}

		if (Validator.isNotNull(PropsValues.TEST_DIRS)) {
			Collections.addAll(testDirNames, PropsValues.TEST_DIRS);
		}

		if (Validator.isNotNull(PropsValues.TEST_SUBREPO_DIRS)) {
			Collections.addAll(testDirNames, PropsValues.TEST_SUBREPO_DIRS);
		}

		for (String testDirName : testDirNames) {
			poshiURLs.addAll(
				_getPoshiURLs(
					poshiFileIncludes.toArray(new String[0]), testDirName));
		}

		Set<String> testSupportDirNames = new HashSet<>();

		if (Validator.isNotNull(PropsValues.TEST_INCLUDE_DIR_NAMES)) {
			Collections.addAll(
				testSupportDirNames, PropsValues.TEST_INCLUDE_DIR_NAMES);
		}

		if (Validator.isNotNull(PropsValues.TEST_SUPPORT_DIRS)) {
			Collections.addAll(
				testSupportDirNames, PropsValues.TEST_SUPPORT_DIRS);
		}

		for (String testSupportDirName : testSupportDirNames) {
			poshiURLs.addAll(
				_getPoshiURLs(POSHI_SUPPORT_FILE_INCLUDES, testSupportDirName));
		}

		_readPoshiFiles(poshiURLs);
		_readSeleniumFiles();

		_initComponentCommandNamesMap();

		_throwExceptions();

		PoshiScriptParserException.throwExceptions();

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Completed reading Poshi files in " + duration + "ms.");
	}

	public static void setFunctionFileNames(String... functionFileNames) {
		Collections.addAll(_functionFileNames, functionFileNames);
	}

	public static void setMacroFileNames(String... macroFileNames) {
		Collections.addAll(_macroFileNames, macroFileNames);
	}

	public static void setTestCaseNamespacedClassCommandName(
		String testCaseNamespacedClassCommandName) {

		_testCaseNamespacedClassCommandName =
			testCaseNamespacedClassCommandName;
	}

	private static void _executePoshiFileCallables(
			String poshiFileType, List<PoshiFileCallable> poshiFileCallables,
			int threadPoolSize)
		throws Exception {

		ExecutorService executorService = Executors.newFixedThreadPool(
			threadPoolSize);

		executorService.invokeAll(poshiFileCallables);

		executorService.shutdown();

		if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
			throw new TimeoutException(
				"Timed out while loading " + poshiFileType + " Poshi files");
		}
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

	private static String _getCommandSummary(
		String classCommandName, String classType, Element commandElement,
		Element rootElement) {

		String summaryIgnore = commandElement.attributeValue("summary-ignore");

		if (Validator.isNotNull(summaryIgnore) &&
			summaryIgnore.equals("true")) {

			return null;
		}

		if (Validator.isNotNull(commandElement.attributeValue("summary"))) {
			return commandElement.attributeValue("summary");
		}

		if (classType.equals("function") &&
			Validator.isNotNull(rootElement.attributeValue("summary"))) {

			return rootElement.attributeValue("summary");
		}

		return classCommandName;
	}

	private static Exception _getDuplicateLocatorsException() {
		StringBuilder sb = new StringBuilder();

		sb.append(
			"Duplicate locator(s) found while loading Poshi files into " +
				"context:\n");

		for (String exception : _duplicateLocatorMessages) {
			sb.append(exception);
			sb.append("\n\n");
		}

		return new Exception(sb.toString());
	}

	private static String _getIgnoreAttributeValue(
		Element rootElement, Element commandElement) {

		if (commandElement.attributeValue("ignore") != null) {
			return commandElement.attributeValue("ignore");
		}

		if (rootElement.attributeValue("ignore") != null) {
			return rootElement.attributeValue("ignore");
		}

		return "true";
	}

	private static Set<URL> _getPoshiURLs(
			FileSystem fileSystem, String[] includes, String baseDirName)
		throws IOException {

		File file = new File(baseDirName);

		baseDirName = file.getCanonicalPath();

		Set<URL> urls = new HashSet<>();

		if (fileSystem == null) {
			urls.addAll(
				FileUtil.getIncludedResourceURLs(includes, baseDirName));
		}
		else {
			urls.addAll(
				FileUtil.getIncludedResourceURLs(
					fileSystem, includes, baseDirName));
		}

		return urls;
	}

	private static Set<URL> _getPoshiURLs(String[] includes, String baseDirName)
		throws Exception {

		return _getPoshiURLs(null, includes, baseDirName);
	}

	private static String _getPropertyGroup(
			Element rootElement, Element commandElement)
		throws Exception {

		List<Element> propertyElements = new ArrayList<>();

		propertyElements.addAll(rootElement.elements("property"));

		propertyElements.addAll(commandElement.elements("property"));

		for (Element propertyElement : propertyElements) {
			String propertyName = propertyElement.attributeValue("name");
			String propertyValue = propertyElement.attributeValue("value");

			if (propertyName.equals("property.group") &&
				(propertyValue != null) && !propertyValue.isEmpty()) {

				return propertyValue;
			}
		}

		return null;
	}

	private static Properties _getPropertyGroupProperties(
			Element rootElement, Element commandElement)
		throws Exception {

		String propertyGroup = _getPropertyGroup(rootElement, commandElement);

		if ((propertyGroup == null) || propertyGroup.isEmpty()) {
			return new Properties();
		}

		Properties propertyGroupProperties = new Properties();

		String propertyGroupRegex =
			"test.case.property.group\\[" + propertyGroup +
				"\\]\\[([^\\]]+)\\]";

		Properties poshiProperties = new Properties();

		poshiProperties.putAll(System.getProperties());

		poshiProperties.putAll(PropsUtil.getProperties());

		for (String poshiPropertyName : poshiProperties.stringPropertyNames()) {
			if (!poshiPropertyName.matches(propertyGroupRegex)) {
				continue;
			}

			propertyGroupProperties.setProperty(
				poshiPropertyName.replaceAll(propertyGroupRegex, "$1"),
				poshiProperties.getProperty(poshiPropertyName));
		}

		return propertyGroupProperties;
	}

	private static void _initComponentCommandNamesMap() {
		for (String testCaseNamespacedClassName :
				_testCaseNamespacedClassNames) {

			String className =
				PoshiGetterUtil.getClassNameFromNamespacedClassName(
					testCaseNamespacedClassName);
			String namespace =
				PoshiGetterUtil.getNamespaceFromNamespacedClassName(
					testCaseNamespacedClassName);

			Element rootElement = getTestCaseRootElement(className, namespace);

			if (rootElement.attributeValue("extends") != null) {
				String extendsTestCaseClassName = rootElement.attributeValue(
					"extends");

				Element extendsRootElement = getTestCaseRootElement(
					extendsTestCaseClassName, namespace);

				List<Element> extendsCommandElements =
					extendsRootElement.elements("command");

				for (Element extendsCommandElement : extendsCommandElements) {
					String extendsCommandName =
						extendsCommandElement.attributeValue("name");

					String extendsNamespacedClassCommandName =
						StringUtil.combine(
							namespace, ".", extendsTestCaseClassName, "#",
							extendsCommandName);

					Properties properties =
						_namespacedClassCommandNamePropertiesMap.get(
							extendsNamespacedClassCommandName);

					if (_isIgnorableCommandNames(
							rootElement, extendsCommandElement,
							extendsCommandName)) {

						properties.setProperty(
							"ignored",
							_getIgnoreAttributeValue(
								rootElement, extendsCommandElement));
					}

					String namespacedClassCommandName = StringUtil.combine(
						namespace, ".", className, "#", extendsCommandName);

					_namespacedClassCommandNamePropertiesMap.put(
						namespacedClassCommandName, properties);

					_commandElements.put(
						"test-case#" + namespacedClassCommandName,
						extendsCommandElement);
				}
			}

			List<Element> commandElements = rootElement.elements("command");

			for (Element commandElement : commandElements) {
				String commandName = commandElement.attributeValue("name");

				if (_isIgnorableCommandNames(
						rootElement, commandElement, commandName)) {

					String namespacedClassCommandName = StringUtil.combine(
						namespace, ".", className, "#", commandName);

					Properties properties =
						_namespacedClassCommandNamePropertiesMap.get(
							namespacedClassCommandName);

					properties.setProperty(
						"ignored",
						_getIgnoreAttributeValue(rootElement, commandElement));

					_namespacedClassCommandNamePropertiesMap.put(
						namespacedClassCommandName, properties);
				}

				String namespacedClassCommandName =
					testCaseNamespacedClassName + "#" + commandName;

				_testCaseNamespacedClassCommandNames.add(
					namespacedClassCommandName);
			}
		}
	}

	private static boolean _isClassOverridden(String namespacedClassName) {
		return _overrideClassNames.containsKey(namespacedClassName);
	}

	private static boolean _isIgnorableCommandNames(
		Element rootElement, Element commandElement, String commandName) {

		if (commandElement.attributeValue("ignore") != null) {
			return true;
		}

		List<String> ignorableCommandNames = new ArrayList<>();

		if (rootElement.attributeValue("ignore-command-names") != null) {
			String ignoreCommandNamesString = rootElement.attributeValue(
				"ignore-command-names");

			ignorableCommandNames = Arrays.asList(
				ignoreCommandNamesString.split(","));
		}

		if (ignorableCommandNames.contains(commandName) ||
			(rootElement.attributeValue("ignore") != null)) {

			return true;
		}

		return false;
	}

	private static void _overrideRootElement(
			Element rootElement, String filePath, String namespace)
		throws Exception {

		String className = PoshiGetterUtil.getClassNameFromFilePath(filePath);

		String baseNamespacedClassName = rootElement.attributeValue("override");

		String baseClassName =
			PoshiGetterUtil.getClassNameFromNamespacedClassName(
				baseNamespacedClassName);

		if (!className.equals(baseClassName)) {
			StringBuilder sb = new StringBuilder();

			sb.append("Override class name does not match base class name:\n");
			sb.append("Override: ");
			sb.append(className);
			sb.append("\nBase: ");
			sb.append(baseClassName);

			throw new RuntimeException(sb.toString());
		}

		String baseNamespace =
			PoshiGetterUtil.getNamespaceFromNamespacedClassName(
				baseNamespacedClassName);

		if (_isClassOverridden(baseNamespace + "." + baseClassName)) {
			StringBuilder sb = new StringBuilder();

			sb.append("Duplicate override for class '");
			sb.append(baseNamespace);
			sb.append(".");
			sb.append(baseClassName);
			sb.append("'\n at ");
			sb.append(namespace);
			sb.append(".");
			sb.append(className);
			sb.append("\npreviously overridden by ");
			sb.append(
				getOverrideClassName(baseNamespace + "." + baseClassName));

			throw new RuntimeException(sb.toString());
		}

		String classType = PoshiGetterUtil.getClassTypeFromFilePath(filePath);

		if (classType.equals("test-case")) {
			Element setUpElement = rootElement.element("set-up");

			if (setUpElement != null) {
				String classCommandName = className + "#set-up";

				_commandElements.put(
					classType + "#" + baseNamespace + "." + classCommandName,
					setUpElement);
			}

			Element tearDownElement = rootElement.element("tear-down");

			if (tearDownElement != null) {
				String classCommandName = className + "#tear-down";

				_commandElements.put(
					classType + "#" + baseNamespace + "." + classCommandName,
					tearDownElement);
			}
		}

		if (classType.equals("action") || classType.equals("function") ||
			classType.equals("macro") || classType.equals("test-case")) {

			List<Element> overrideVarElements = rootElement.elements("var");

			if (!overrideVarElements.isEmpty()) {
				List<Element> baseVarElements = getRootVarElements(
					classType, className, baseNamespace);

				Map<String, Element> overriddenVarElementMap = new HashMap<>();

				for (Element baseVarElement : baseVarElements) {
					overriddenVarElementMap.put(
						baseVarElement.attributeValue("name"), baseVarElement);
				}

				for (Element overrideVarElement : overrideVarElements) {
					overriddenVarElementMap.put(
						overrideVarElement.attributeValue("name"),
						overrideVarElement);
				}

				_rootVarElements.put(
					classType + "#" + baseNamespace + "." + className,
					new ArrayList<>(overriddenVarElementMap.values()));
			}

			List<Element> overrideCommandElements = rootElement.elements(
				"command");

			for (Element overrideCommandElement : overrideCommandElements) {
				String commandName = overrideCommandElement.attributeValue(
					"name");

				String classCommandName = className + "#" + commandName;

				String baseNamespacedClassCommandName =
					baseNamespace + "." + className + "#" + commandName;

				_commandElements.put(
					classType + "#" + baseNamespacedClassCommandName,
					overrideCommandElement);

				_commandSummaries.put(
					classType + "#" + baseNamespacedClassCommandName,
					_getCommandSummary(
						classCommandName, classType, overrideCommandElement,
						rootElement));

				String prose = overrideCommandElement.attributeValue("prose");

				if (classType.equals("macro") && (prose != null) &&
					!prose.isEmpty()) {

					PoshiProseMatcher.storePoshiProseMatcher(
						overrideCommandElement.attributeValue("prose"),
						baseNamespacedClassCommandName);
				}

				if (classType.equals("test-case")) {
					Properties baseProperties =
						_namespacedClassCommandNamePropertiesMap.get(
							baseNamespacedClassCommandName);

					Properties overrideProperties =
						_getClassCommandNameProperties(
							rootElement, overrideCommandElement);

					Properties overriddenProperties = new Properties(
						baseProperties);

					overriddenProperties.setProperty(
						"test.class.method.name", classCommandName);
					overriddenProperties.setProperty(
						"test.class.name", className);

					overriddenProperties.putAll(overrideProperties);

					_namespacedClassCommandNamePropertiesMap.put(
						baseNamespacedClassCommandName, overriddenProperties);

					_poshiPropertyNames.addAll(
						overrideProperties.stringPropertyNames());

					if (Validator.isNotNull(
							overrideCommandElement.attributeValue(
								"description"))) {

						_testCaseDescriptions.put(
							baseNamespacedClassCommandName,
							overrideCommandElement.attributeValue(
								"description"));
					}
				}
			}
		}

		if (classType.equals("function")) {
			String defaultClassCommandName =
				className + "#" + rootElement.attributeValue("default");

			Element defaultCommandElement = getFunctionCommandElement(
				defaultClassCommandName, baseNamespace);

			_commandElements.put(
				classType + "#" + baseNamespace + "." + className,
				defaultCommandElement);

			_commandSummaries.put(
				classType + "#" + baseNamespace + "." + className,
				_getCommandSummary(
					defaultClassCommandName, classType, defaultCommandElement,
					rootElement));

			String xml = rootElement.asXML();

			for (int i = 1;; i++) {
				if (xml.contains("${locator" + i + "}")) {
					continue;
				}

				if (i > 1) {
					i--;
				}

				int baseLocatorCount = _functionLocatorCounts.get(
					baseNamespace + "." + className);

				if (i > baseLocatorCount) {
					StringBuilder sb = new StringBuilder();

					sb.append("Overriding function file cannot have a ");
					sb.append("locator count higher than base function file\n");
					sb.append(namespace);
					sb.append(".");
					sb.append(className);
					sb.append(" locator count: ");
					sb.append(i);
					sb.append("\n");
					sb.append(baseNamespace);
					sb.append(".");
					sb.append(className);
					sb.append(" locator count: ");
					sb.append(baseLocatorCount);

					throw new RuntimeException(sb.toString());
				}

				break;
			}
		}
		else if (classType.equals("path")) {
			_storePathElement(rootElement, className, filePath, baseNamespace);
		}
	}

	private static void _readPoshiFiles(Set<URL> poshiURLs) throws Exception {
		_storeRootElements(poshiURLs, _DEFAULT_NAMESPACE);

		if (!_duplicateLocatorMessages.isEmpty()) {
			throw _getDuplicateLocatorsException();
		}
	}

	private static void _readPoshiFilesFromClassPath(
			String[] includes, String... resourceNames)
		throws Exception {

		for (String resourceName : resourceNames) {
			ClassLoader classLoader = PoshiContext.class.getClassLoader();

			Enumeration<URL> enumeration = classLoader.getResources(
				resourceName);

			while (enumeration.hasMoreElements()) {
				String resourceURLString = String.valueOf(
					enumeration.nextElement());

				int x = resourceURLString.indexOf("!");

				try (FileSystem fileSystem = FileSystems.newFileSystem(
						URI.create(resourceURLString.substring(0, x)),
						new HashMap<String, String>(), classLoader)) {

					String namespace = null;

					Matcher matcher = _poshiResourceJarNamePattern.matcher(
						resourceURLString);

					if (matcher.find()) {
						namespace = StringUtils.capitalize(
							matcher.group("namespace"));
					}
					else if (resourceName.equals("default/testFunctional")) {
						namespace = "Default";
					}

					if (namespace == null) {
						throw new RuntimeException(
							"A namespace must be defined for resource: " +
								resourceURLString);
					}

					if (namespace.equals(_DEFAULT_NAMESPACE)) {
						throw new RuntimeException(
							"Namespace: '" + _DEFAULT_NAMESPACE +
								"' is reserved");
					}

					if (_namespaces.contains(namespace)) {
						throw new RuntimeException(
							"Duplicate namespace " + namespace);
					}

					_namespaces.add(namespace);

					Set<URL> poshiURLs = _getPoshiURLs(
						fileSystem, includes,
						resourceURLString.substring(x + 1));

					_storeRootElements(poshiURLs, namespace);
				}
			}
		}
	}

	private static void _readSeleniumFiles() throws Exception {
		Method[] methods = LiferaySelenium.class.getMethods();

		for (Method method : methods) {
			Class<?>[] classes = method.getParameterTypes();

			_seleniumParameterCounts.put(method.getName(), classes.length);
		}

		_seleniumParameterCounts.put("open", 1);
	}

	private static void _storePathElement(
			Element rootElement, String className, String filePath,
			String namespace)
		throws Exception {

		if (rootElement.attributeValue("override") == null) {
			_rootElements.put(
				"path#" + namespace + "." + className, rootElement);
		}

		List<String> locatorKeys = new ArrayList<>();

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

			if (locatorKey.equals("") && locator.equals("")) {
				continue;
			}

			if (locatorKeys.contains(locatorKey)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Duplicate path locator key '");
				sb.append(className + "#" + locatorKey);
				sb.append("' at namespace '");
				sb.append(namespace);
				sb.append("' \n");
				sb.append(filePath);
				sb.append(": ");
				sb.append(PoshiGetterUtil.getLineNumber(locatorKeyElement));

				_duplicateLocatorMessages.add(sb.toString());
			}

			locatorKeys.add(locatorKey);

			if (locatorKey.equals("EXTEND_ACTION_PATH")) {
				_pathExtensions.put(namespace + "." + className, locator);
			}
			else {
				_pathLocators.put(
					namespace + "." + className + "#" + locatorKey, locator);
			}
		}
	}

	private static void _storeRootElement(
			Element rootElement, String filePath, String namespace)
		throws Exception {

		if (rootElement.attributeValue("override") != null) {
			_overrideRootElement(rootElement, filePath, namespace);

			return;
		}

		String className = PoshiGetterUtil.getClassNameFromFilePath(filePath);
		String classType = PoshiGetterUtil.getClassTypeFromFilePath(filePath);

		if (classType.equals("test-case")) {
			_testCaseNamespacedClassNames.add(namespace + "." + className);

			if (rootElement.element("set-up") != null) {
				Element setUpElement = rootElement.element("set-up");

				String classCommandName = className + "#set-up";

				_commandElements.put(
					classType + "#" + namespace + "." + classCommandName,
					setUpElement);
			}

			if (rootElement.element("tear-down") != null) {
				Element tearDownElement = rootElement.element("tear-down");

				String classCommandName = className + "#tear-down";

				_commandElements.put(
					classType + "#" + namespace + "." + classCommandName,
					tearDownElement);
			}
		}

		if (classType.equals("action") || classType.equals("function") ||
			classType.equals("macro") || classType.equals("test-case")) {

			_rootElements.put(
				classType + "#" + namespace + "." + className, rootElement);

			_rootVarElements.put(
				classType + "#" + namespace + "." + className,
				rootElement.elements("var"));

			List<Element> commandElements = rootElement.elements("command");

			for (Element commandElement : commandElements) {
				String commandName = commandElement.attributeValue("name");

				String classCommandName = className + "#" + commandName;

				if (isCommandElement(classType, classCommandName, namespace)) {
					StringBuilder sb = new StringBuilder();

					sb.append("Duplicate command name '");
					sb.append(classCommandName);
					sb.append("' at namespace '");
					sb.append(namespace);
					sb.append("'\n");
					sb.append(filePath);
					sb.append(": ");
					sb.append(PoshiGetterUtil.getLineNumber(commandElement));
					sb.append("\n");

					String duplicateElementFilePath = getFilePathFromFileName(
						PoshiGetterUtil.getFileNameFromFilePath(filePath),
						namespace);

					if (Validator.isNull(duplicateElementFilePath)) {
						duplicateElementFilePath = filePath;
					}

					sb.append(duplicateElementFilePath);
					sb.append(": ");

					Element duplicateElement = _commandElements.get(
						classType + "#" + namespace + "." + classCommandName);

					sb.append(PoshiGetterUtil.getLineNumber(duplicateElement));

					_duplicateLocatorMessages.add(sb.toString());

					continue;
				}

				String namespacedClassCommandName =
					namespace + "." + className + "#" + commandName;

				_commandElements.put(
					classType + "#" + namespacedClassCommandName,
					commandElement);

				_commandSummaries.put(
					classType + "#" + namespacedClassCommandName,
					_getCommandSummary(
						classCommandName, classType, commandElement,
						rootElement));

				String prose = commandElement.attributeValue("prose");

				if (classType.equals("macro") && (prose != null) &&
					!prose.isEmpty()) {

					PoshiProseMatcher.storePoshiProseMatcher(
						commandElement.attributeValue("prose"),
						namespacedClassCommandName);
				}

				if (classType.equals("test-case")) {
					Properties commandProperties =
						_getClassCommandNameProperties(
							rootElement, commandElement);
					String propertyGroup = _getPropertyGroup(
						rootElement, commandElement);
					Properties propertyGroupProperties =
						_getPropertyGroupProperties(
							rootElement, commandElement);

					_validateCommandProperties(
						commandProperties, propertyGroup,
						propertyGroupProperties, filePath, classCommandName);

					Properties properties = new Properties();

					properties.putAll(commandProperties);
					properties.putAll(propertyGroupProperties);

					properties.setProperty(
						"test.class.method.name", classCommandName);
					properties.setProperty("test.class.name", className);

					_namespacedClassCommandNamePropertiesMap.put(
						namespace + "." + classCommandName, properties);

					_poshiPropertyNames.addAll(
						properties.stringPropertyNames());

					if (Validator.isNotNull(
							commandElement.attributeValue("description"))) {

						_testCaseDescriptions.put(
							namespacedClassCommandName,
							commandElement.attributeValue("description"));
					}
				}
			}

			if (classType.equals("function")) {
				String defaultClassCommandName =
					className + "#" + rootElement.attributeValue("default");

				Element defaultCommandElement = getFunctionCommandElement(
					defaultClassCommandName, namespace);

				_commandElements.put(
					classType + "#" + namespace + "." + className,
					defaultCommandElement);

				_commandSummaries.put(
					classType + "#" + namespace + "." + className,
					_getCommandSummary(
						defaultClassCommandName, classType,
						defaultCommandElement, rootElement));

				String xml = rootElement.asXML();

				for (int i = 1;; i++) {
					if (xml.contains("${locator" + i + "}")) {
						continue;
					}

					if (i > 1) {
						i--;
					}

					_functionLocatorCounts.put(namespace + "." + className, i);

					break;
				}
			}
		}
		else if (classType.equals("path")) {
			_storePathElement(rootElement, className, filePath, namespace);
		}
	}

	private static void _storeRootElements(Set<URL> urls, String namespace)
		throws Exception {

		List<PoshiFileCallable> dependencyPoshiFileCallables =
			new ArrayList<>();
		List<PoshiFileCallable> macroPoshiFileCallables = new ArrayList<>();
		List<PoshiFileCallable> testPoshiFileCallables = new ArrayList<>();

		for (URL url : urls) {
			File file = new File(url.getFile());

			String fileName = file.getName();

			if (fileName.endsWith(".function")) {
				_functionFileNames.add(
					StringUtil.replace(fileName, ".function", ""));

				_functionFileNames.add(
					namespace + "." +
						StringUtil.replace(fileName, ".function", ""));
			}

			if (fileName.endsWith(".macro")) {
				_validateUniqueCommandName(
					StringUtil.replace(fileName, ".macro", ""), file.getPath());

				_macroFileNames.add(StringUtil.replace(fileName, ".macro", ""));

				_macroFileNames.add(
					namespace + "." +
						StringUtil.replace(fileName, ".macro", ""));

				macroPoshiFileCallables.add(
					new PoshiFileCallable(url, namespace));

				continue;
			}

			if (fileName.endsWith(".testcase") || fileName.endsWith(".prose")) {
				testPoshiFileCallables.add(
					new PoshiFileCallable(url, namespace));

				continue;
			}

			dependencyPoshiFileCallables.add(
				new PoshiFileCallable(url, namespace));
		}

		_executePoshiFileCallables(
			"dependency", dependencyPoshiFileCallables,
			PropsValues.POSHI_FILE_READ_THREAD_POOL);

		_executePoshiFileCallables(
			"macro", macroPoshiFileCallables,
			PropsValues.POSHI_FILE_READ_THREAD_POOL);

		_executePoshiFileCallables(
			"test", testPoshiFileCallables,
			PropsValues.POSHI_FILE_READ_THREAD_POOL);
	}

	private static void _throwExceptions() throws Exception {
		if (!_exceptions.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			sb.append("\n\n");
			sb.append(_exceptions.size());
			sb.append(" errors in Poshi file reading\n\n");

			for (Exception exception : _exceptions) {
				sb.append(exception.getMessage());
				sb.append("\n");

				Throwable causeThrowable = exception.getCause();

				if (causeThrowable != null) {
					sb.append(causeThrowable.getMessage());
					sb.append("\n");
				}
			}

			System.out.println(sb.toString());

			throw new Exception();
		}
	}

	private static void _validateCommandProperties(
		Properties commandProperties, String propertyGroup,
		Properties propertyGroupProperties, String filePath,
		String classCommandName) {

		List<String> propertyNames = new ArrayList<>();

		for (String propertyName :
				propertyGroupProperties.stringPropertyNames()) {

			if (commandProperties.containsKey(propertyName)) {
				propertyNames.add(propertyName);
			}
		}

		if (propertyNames.isEmpty()) {
			return;
		}

		_exceptions.add(
			new Exception(
				StringUtil.combine(
					filePath, " for \"", classCommandName,
					"\" the properties \"",
					StringUtil.join(propertyNames.toArray(new String[0]), ","),
					"\" cannot be set as they are set in \"", propertyGroup,
					"\"")));
	}

	private static void _validateUniqueCommandName(
		String macroName, String filePath) {

		for (String functionName : _functionFileNames) {
			if (functionName.contains("LocalFile")) {
				functionName = StringUtil.replace(
					functionName, "LocalFile.", "");
			}

			if (functionName.equals(macroName)) {
				throw new RuntimeException(
					"Duplicate macro and function file name: " + macroName +
						"\n" + filePath + "\n");
			}
		}
	}

	private static void _writeTestCaseMethodNamesProperties() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("## Autogenerated\n\n");

		if (PropsValues.TEST_BATCH_PROPERTY_QUERY != null) {
			int maxSubgroupSize = PropsValues.TEST_BATCH_MAX_SUBGROUP_SIZE;

			if (PropsValues.TEST_BATCH_RUN_TYPE.equals("single")) {
				maxSubgroupSize = 1;
			}

			List<List<String>> testBatchGroups = getTestBatchGroups(
				PropsValues.TEST_BATCH_PROPERTY_QUERY, maxSubgroupSize);

			List<List<List<String>>> segments = Lists.partition(
				testBatchGroups, PropsValues.TEST_BATCH_MAX_GROUP_SIZE);

			for (int i = 0; i < segments.size(); i++) {
				List<List<String>> segment = segments.get(i);

				List<String> axisNames = new ArrayList<>();

				for (int j = 0; j < segment.size(); j++) {
					sb.append("RUN_TEST_CASE_METHOD_GROUP_");
					sb.append(i);
					sb.append("_");
					sb.append(j);
					sb.append("=");

					List<String> axis = segment.get(j);

					sb.append(
						StringUtil.join(axis.toArray(new String[0]), ","));

					sb.append("\n");

					axisNames.add(i + "_" + j);
				}

				sb.append("RUN_TEST_CASE_METHOD_GROUP_");
				sb.append(i);
				sb.append("=");

				sb.append(
					StringUtil.join(axisNames.toArray(new String[0]), " "));

				sb.append("\n");
			}

			List<String> segmentNames = new ArrayList<>();

			for (int i = 0; i < segments.size(); i++) {
				segmentNames.add(String.valueOf(i));
			}

			sb.append("RUN_TEST_CASE_METHOD_GROUPS=");
			sb.append(
				StringUtil.join(segmentNames.toArray(new String[0]), " "));
		}

		FileUtil.write("test.case.method.names.properties", sb.toString());
	}

	private static void _writeTestCSVReportFile() throws Exception {
		if (PropsValues.TEST_CSV_REPORT_PROPERTY_NAMES == null) {
			return;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

		File reportCSVFile = new File(
			StringUtil.combine(
				"Report_", simpleDateFormat.format(new Date()), ".csv"));

		try (FileWriter reportCSVFileWriter = new FileWriter(reportCSVFile)) {
			List<String> reportLineItems = new ArrayList<>();

			reportLineItems.add("Namespace");
			reportLineItems.add("Class Name");
			reportLineItems.add("Command Name");

			for (String propertyName :
					PropsValues.TEST_CSV_REPORT_PROPERTY_NAMES) {

				reportLineItems.add(propertyName);
			}

			reportCSVFileWriter.write(StringUtils.join(reportLineItems, ","));

			reportLineItems.clear();

			for (String testCaseNamespacedClassCommandName :
					_testCaseNamespacedClassCommandNames) {

				Matcher matcher = _namespaceClassCommandNamePattern.matcher(
					testCaseNamespacedClassCommandName);

				if (!matcher.find()) {
					throw new RuntimeException(
						"Invalid namespaced class command name " +
							testCaseNamespacedClassCommandName);
				}

				reportLineItems.add(matcher.group("namespace"));
				reportLineItems.add(matcher.group("className"));
				reportLineItems.add(matcher.group("commandName"));

				Properties properties =
					_namespacedClassCommandNamePropertiesMap.get(
						testCaseNamespacedClassCommandName);

				for (String propertyName :
						PropsValues.TEST_CSV_REPORT_PROPERTY_NAMES) {

					if (properties.containsKey(propertyName)) {
						String propertyValue = properties.getProperty(
							propertyName);

						if (propertyValue.contains(",")) {
							reportLineItems.add(
								StringUtils.join(
									ArrayUtils.toArray(
										"\"", propertyValue, "\"")));
						}
						else {
							reportLineItems.add(propertyValue);
						}
					}
					else {
						reportLineItems.add("");
					}
				}

				reportCSVFileWriter.write(
					"\n" + StringUtils.join(reportLineItems, ","));

				reportLineItems.clear();
			}
		}
		catch (IOException ioException) {
			if (reportCSVFile.exists()) {
				reportCSVFile.deleteOnExit();
			}

			throw new RuntimeException(ioException);
		}
	}

	private static void _writeTestGeneratedProperties() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append("## Autogenerated\n\n");

		for (String testCaseNamespacedClassCommandName :
				_testCaseNamespacedClassCommandNames) {

			Properties properties =
				_namespacedClassCommandNamePropertiesMap.get(
					testCaseNamespacedClassCommandName);
			String testNamespacedClassName =
				PoshiGetterUtil.
					getNamespacedClassNameFromNamespacedClassCommandName(
						testCaseNamespacedClassCommandName);
			String testCommandName =
				PoshiGetterUtil.getCommandNameFromNamespacedClassCommandName(
					testCaseNamespacedClassCommandName);

			for (String propertyName : properties.stringPropertyNames()) {
				sb.append(testNamespacedClassName);
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

	private static final String _DEFAULT_NAMESPACE = "LocalFile";

	private static final int _MAX_ARGUMENT_COUNT = 3;

	private static final Map<String, Element> _commandElements =
		Collections.synchronizedMap(new HashMap<>());
	private static final Map<String, String> _commandSummaries =
		Collections.synchronizedMap(new HashMap<>());
	private static final Set<String> _duplicateLocatorMessages =
		Collections.synchronizedSet(new HashSet<>());
	private static final Set<Exception> _exceptions =
		Collections.synchronizedSet(new HashSet<>());
	private static final Map<String, String> _filePaths =
		Collections.synchronizedMap(new HashMap<>());
	private static final Set<String> _functionFileNames =
		Collections.synchronizedSet(new HashSet<>());
	private static final Map<String, Integer> _functionLocatorCounts =
		Collections.synchronizedMap(new HashMap<>());
	private static final Set<String> _macroFileNames =
		Collections.synchronizedSet(new HashSet<>());
	private static final Pattern _namespaceClassCommandNamePattern =
		Pattern.compile(
			"(?<namespace>[^\\.]+)\\.(?<className>[^\\#]+)\\#" +
				"(?<commandName>.+)");
	private static final Map<String, Properties>
		_namespacedClassCommandNamePropertiesMap = Collections.synchronizedMap(
			new HashMap<>());
	private static final List<String> _namespaces =
		Collections.synchronizedList(new ArrayList<>());
	private static final Map<String, String> _overrideClassNames =
		Collections.synchronizedMap(new HashMap<>());
	private static final Map<String, String> _pathExtensions =
		Collections.synchronizedMap(new HashMap<>());
	private static final Map<String, String> _pathLocators =
		Collections.synchronizedMap(new HashMap<>());
	private static final Set<String> _poshiPropertyNames =
		Collections.synchronizedSet(new HashSet<>());
	private static final Pattern _poshiResourceJarNamePattern = Pattern.compile(
		"jar:.*\\/(?<namespace>\\w+)\\-(?<branchName>\\w+" +
			"([\\-\\.]\\w+)*)\\-.*?\\.jar.*");
	private static final Map<String, Element> _rootElements =
		Collections.synchronizedMap(new HashMap<>());
	private static final Map<String, List<Element>> _rootVarElements =
		Collections.synchronizedMap(new HashMap<>());
	private static final Map<String, Integer> _seleniumParameterCounts =
		Collections.synchronizedMap(new HashMap<>());
	private static final Map<String, String> _testCaseDescriptions =
		Collections.synchronizedMap(new HashMap<>());
	private static String _testCaseNamespacedClassCommandName;
	private static final List<String> _testCaseNamespacedClassCommandNames =
		Collections.synchronizedList(new ArrayList<>());
	private static final List<String> _testCaseNamespacedClassNames =
		Collections.synchronizedList(new ArrayList<>());

	private static class PoshiFileCallable implements Callable<URL> {

		public URL call() {
			String filePath = _url.getFile();

			try {
				if (OSDetector.isWindows()) {
					if (filePath.startsWith("/")) {
						filePath = filePath.substring(1);
					}

					filePath = StringUtil.replace(filePath, "/", "\\");
				}

				String fileName = PoshiGetterUtil.getFileNameFromFilePath(
					filePath);

				String namespacedFileName = _namespace + "." + fileName;

				if (_filePaths.containsKey(namespacedFileName)) {
					String duplicateFilePath = _filePaths.get(
						namespacedFileName);

					throw new RuntimeException(
						StringUtil.combine(
							"Duplicate file name '", fileName,
							"' found within the namespace '", _namespace,
							"':\n", filePath, "\n", duplicateFilePath, "\n"));
				}

				Element rootElement = PoshiGetterUtil.getRootElementFromURL(
					_url);

				_storeRootElement(rootElement, filePath, _namespace);

				if (rootElement.attributeValue("override") == null) {
					_filePaths.put(_namespace + "." + fileName, filePath);
				}
			}
			catch (Exception exception) {
				if (!(exception instanceof PoshiScriptParserException)) {
					_exceptions.add(
						new Exception(
							"Unable to read: " + filePath, exception));
				}
			}

			return _url;
		}

		private PoshiFileCallable(URL url, String namespace) {
			_url = url;
			_namespace = namespace;
		}

		private final String _namespace;
		private final URL _url;

	}

}