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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilderContext {

	public SeleniumBuilderContext(String baseDir) throws Exception {
		_baseDir = baseDir;

		_seleniumBuilderFileUtil = new SeleniumBuilderFileUtil(_baseDir);

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_baseDir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\*.action", "**\\*.function", "**\\*.macro", "**\\*.path",
				"**\\*.testcase", "**\\*.testsuite"
			});

		directoryScanner.scan();

		String[] fileNames = directoryScanner.getIncludedFiles();

		for (String fileName : fileNames) {
			addFile(fileName);
		}

		String[] seleniumFileNames = {
			"com/liferay/portalweb/portal/util/liferayselenium/" +
				"LiferaySelenium.java",
			"com/liferay/portalweb/portal/util/liferayselenium/" +
				"SeleniumWrapper.java"
		};

		for (String seleniumFileName : seleniumFileNames) {
			String content = _seleniumBuilderFileUtil.getNormalizedContent(
				seleniumFileName);

			Pattern pattern = Pattern.compile(
				"public [a-z]* [A-Za-z0-9_]*\\(.*?\\)");

			Matcher matcher = pattern.matcher(content);

			while (matcher.find()) {
				String methodSignature = matcher.group();

				int x = methodSignature.indexOf(" ", 7);
				int y = methodSignature.indexOf("(");

				String seleniumCommandName = methodSignature.substring(
					x + 1, y);

				int count = 0;

				int z = methodSignature.indexOf(")");

				String parameters = methodSignature.substring(y + 1, z);

				if (!parameters.equals("")) {
					count = StringUtil.count(parameters, ",") + 1;
				}

				_seleniumParameterCounts.put(seleniumCommandName, count);
			}
		}

		_seleniumParameterCounts.put("open", 1);
	}

	public void addFile(String fileName) throws Exception {
		fileName = _normalizeFileName(fileName);

		if (fileName.endsWith(".action")) {
			String actionName = _getName(fileName);

			if (_actionFileNames.containsKey(actionName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1008, fileName, actionName);
			}

			_actionFileNames.put(actionName, fileName);

			_actionNames.add(actionName);

			_actionRootElements.put(actionName, _getRootElement(fileName));
		}
		else if (fileName.endsWith(".function")) {
			String functionName = _getName(fileName);

			_functionClassNames.put(functionName, _getClassName(fileName));

			_functionFileNames.put(functionName, fileName);

			_functionJavaFileNames.put(
				functionName, _getJavaFileName(fileName));

			Element rootElement = _getRootElement(fileName);

			_functionLocatorCounts.put(
				functionName, _getLocatorCount(rootElement));

			if (_functionNames.contains(functionName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1008, fileName, functionName);
			}

			_functionNames.add(functionName);

			_functionPackageNames.put(functionName, _getPackageName(fileName));

			_functionReturnTypes.put(
				functionName, _getReturnType(functionName));

			_functionRootElements.put(functionName, rootElement);

			_functionSimpleClassNames.put(
				functionName, _getSimpleClassName(fileName));
		}
		else if (fileName.endsWith(".macro")) {
			String macroName = _getName(fileName);

			_macroClassNames.put(macroName, _getClassName(fileName));

			_macroFileNames.put(macroName, fileName);

			_macroJavaFileNames.put(macroName, _getJavaFileName(fileName));

			if (_macroNames.contains(macroName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1008, fileName, macroName);
			}

			_macroNames.add(macroName);

			_macroPackageNames.put(macroName, _getPackageName(fileName));

			_macroSimpleClassNames.put(
				macroName, _getSimpleClassName(fileName));

			_macroRootElements.put(macroName, _getRootElement(fileName));
		}
		else if (fileName.endsWith(".path")) {
			String pathName = _getName(fileName);

			_actionClassNames.put(pathName, _getClassName(fileName, "Action"));

			_actionJavaFileNames.put(
				pathName, _getJavaFileName(fileName, "Action"));

			_actionNames.add(pathName);

			_actionPackageNames.put(pathName, _getPackageName(fileName));

			_actionSimpleClassNames.put(
				pathName, _getSimpleClassName(fileName, "Action"));

			_pathClassNames.put(pathName, _getClassName(fileName));

			_pathFileNames.put(pathName, fileName);

			_pathJavaFileNames.put(pathName, _getJavaFileName(fileName));

			if (_pathNames.contains(pathName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1008, fileName, pathName);
			}

			_pathNames.add(pathName);

			_pathPackageNames.put(pathName, _getPackageName(fileName));

			_pathRootElements.put(pathName, _getRootElement(fileName));

			_pathSimpleClassNames.put(pathName, _getSimpleClassName(fileName));
		}
		else if (fileName.endsWith(".testcase")) {
			String testCaseName = _getName(fileName);

			_testCaseClassNames.put(testCaseName, _getClassName(fileName));

			_testCaseFileNames.put(testCaseName, fileName);

			_testCaseHTMLFileNames.put(
				testCaseName, _getHTMLFileName(fileName));

			_testCaseJavaFileNames.put(
				testCaseName, _getJavaFileName(fileName));

			if (_testCaseNames.contains(testCaseName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1008, fileName, testCaseName);
			}

			_testCaseNames.add(testCaseName);

			_testCasePackageNames.put(testCaseName, _getPackageName(fileName));

			_testCaseRootElements.put(testCaseName, _getRootElement(fileName));

			_testCaseSimpleClassNames.put(
				testCaseName, _getSimpleClassName(fileName));
		}
		else if (fileName.endsWith(".testsuite")) {
			String testSuiteName = _getName(fileName);

			_testSuiteClassNames.put(testSuiteName, _getClassName(fileName));

			_testSuiteFileNames.put(testSuiteName, fileName);

			_testSuiteHTMLFileNames.put(
				testSuiteName, _getHTMLFileName(fileName));

			_testSuiteJavaFileNames.put(
				testSuiteName, _getJavaFileName(fileName));

			if (_testSuiteNames.contains(testSuiteName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1008, fileName, testSuiteName);
			}

			_testSuiteNames.add(testSuiteName);

			_testSuitePackageNames.put(
				testSuiteName, _getPackageName(fileName));

			_testSuiteRootElements.put(
				testSuiteName, _getRootElement(fileName));

			_testSuiteSimpleClassNames.put(
				testSuiteName, _getSimpleClassName(fileName));
		}
		else {
			throw new IllegalArgumentException("Invalid file " + fileName);
		}
	}

	public String getActionClassName(String actionName) {
		return _actionClassNames.get(actionName);
	}

	public String getActionFileName(String actionName) {
		return _actionFileNames.get(actionName);
	}

	public String getActionJavaFileName(String actionName) {
		return _actionJavaFileNames.get(actionName);
	}

	public Set<String> getActionNames() {
		return _actionNames;
	}

	public String getActionPackageName(String actionName) {
		return _actionPackageNames.get(actionName);
	}

	public Element getActionRootElement(String actionName) {
		return _actionRootElements.get(actionName);
	}

	public String getActionSimpleClassName(String actionName) {
		return _actionSimpleClassNames.get(actionName);
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public String getFunctionClassName(String functionName) {
		return _functionClassNames.get(functionName);
	}

	public String getFunctionFileName(String functionName) {
		return _functionFileNames.get(functionName);
	}

	public String getFunctionJavaFileName(String functionName) {
		return _functionJavaFileNames.get(functionName);
	}

	public int getFunctionLocatorCount(String functionName) {
		return _functionLocatorCounts.get(functionName);
	}

	public Set<String> getFunctionNames() {
		return _functionNames;
	}

	public String getFunctionPackageName(String functionName) {
		return _functionPackageNames.get(functionName);
	}

	public String getFunctionReturnType(String functionName) {
		return _functionReturnTypes.get(functionName);
	}

	public Element getFunctionRootElement(String functionName) {
		return _functionRootElements.get(functionName);
	}

	public String getFunctionSimpleClassName(String functionName) {
		return _functionSimpleClassNames.get(functionName);
	}

	public String getMacroClassName(String macroName) {
		return _macroClassNames.get(macroName);
	}

	public String getMacroFileName(String macroName) {
		return _macroFileNames.get(macroName);
	}

	public String getMacroJavaFileName(String macroName) {
		return _macroJavaFileNames.get(macroName);
	}

	public Set<String> getMacroNames() {
		return _macroNames;
	}

	public String getMacroPackageName(String macroName) {
		return _macroPackageNames.get(macroName);
	}

	public Element getMacroRootElement(String macroName) {
		return _macroRootElements.get(macroName);
	}

	public String getMacroSimpleClassName(String macroName) {
		return _macroSimpleClassNames.get(macroName);
	}

	public String getPathClassName(String pathName) {
		return _pathClassNames.get(pathName);
	}

	public String getPathFileName(String pathName) {
		return _pathFileNames.get(pathName);
	}

	public String getPathJavaFileName(String pathName) {
		return _pathJavaFileNames.get(pathName);
	}

	public Set<String> getPathNames() {
		return _pathNames;
	}

	public String getPathPackageName(String pathName) {
		return _pathPackageNames.get(pathName);
	}

	public Element getPathRootElement(String pathName) {
		return _pathRootElements.get(pathName);
	}

	public String getPathSimpleClassName(String pathName) {
		return _pathSimpleClassNames.get(pathName);
	}

	public int getSeleniumParameterCount(String seleniumCommandName) {
		return _seleniumParameterCounts.get(seleniumCommandName);
	}

	public String getTestCaseClassName(String testCaseName) {
		return _testCaseClassNames.get(testCaseName);
	}

	public String getTestCaseFileName(String testCaseName) {
		return _testCaseFileNames.get(testCaseName);
	}

	public String getTestCaseHTMLFileName(String testCaseName) {
		return _testCaseHTMLFileNames.get(testCaseName);
	}

	public String getTestCaseJavaFileName(String testCaseName) {
		return _testCaseJavaFileNames.get(testCaseName);
	}

	public Set<String> getTestCaseNames() {
		return _testCaseNames;
	}

	public String getTestCasePackageName(String testCaseName) {
		return _testCasePackageNames.get(testCaseName);
	}

	public Element getTestCaseRootElement(String testCaseName) {
		return _testCaseRootElements.get(testCaseName);
	}

	public String getTestCaseSimpleClassName(String testCaseName) {
		return _testCaseSimpleClassNames.get(testCaseName);
	}

	public String getTestSuiteClassName(String testSuiteName) {
		return _testSuiteClassNames.get(testSuiteName);
	}

	public String getTestSuiteFileName(String testSuiteName) {
		return _testSuiteFileNames.get(testSuiteName);
	}

	public String getTestSuiteHTMLFileName(String testSuiteName) {
		return _testSuiteHTMLFileNames.get(testSuiteName);
	}

	public String getTestSuiteJavaFileName(String testSuiteName) {
		return _testSuiteJavaFileNames.get(testSuiteName);
	}

	public Set<String> getTestSuiteNames() {
		return _testSuiteNames;
	}

	public String getTestSuitePackageName(String testSuiteName) {
		return _testSuitePackageNames.get(testSuiteName);
	}

	public Element getTestSuiteRootElement(String testSuiteName) {
		return _testSuiteRootElements.get(testSuiteName);
	}

	public String getTestSuiteSimpleClassName(String testCaseName) {
		return _testSuiteSimpleClassNames.get(testCaseName);
	}

	public void validateActionElements(String actionName) {
		String actionFileName = getActionFileName(actionName);

		Element rootElement = getActionRootElement(actionName);

		if (rootElement == null) {
			return;
		}

		if (!_pathNames.contains(actionName)) {
			_seleniumBuilderFileUtil.throwValidationException(
				2002, actionFileName, actionName);
		}

		List<Element> caseElements =
			_seleniumBuilderFileUtil.getAllChildElements(rootElement, "case");

		for (Element caseElement : caseElements) {
			_validateLocatorKeyElement(actionFileName, actionName, caseElement);
		}

		List<Element> commandElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "command");

		Set<String> commandElementNames = new HashSet<String>();

		for (Element commandElement : commandElements) {
			String commandName = commandElement.attributeValue("name");

			if (commandElementNames.contains(commandName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1009, actionFileName, commandElement, commandName);
			}

			commandElementNames.add(commandName);

			if (!_isFunctionName(commandName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					2001, actionFileName, commandElement, commandName);
			}
		}

		List<Element> executeElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "execute");

		for (Element executeElement : executeElements) {
			_validateFunctionElement(actionFileName, executeElement);
		}
	}

	public void validateElements(String fileName) {
		String name = _getName(fileName);

		if (fileName.endsWith(".action")) {
			validateActionElements(name);
		}
		else if (fileName.endsWith(".function")) {
			validateFunctionElements(name);
		}
		else if (fileName.endsWith(".macro")) {
			validateMacroElements(name);
		}
		else if (fileName.endsWith(".testcase")) {
			validateTestCaseElements(name);
		}
		else if (fileName.endsWith(".testsuite")) {
			validateTestSuiteElements(name);
		}
	}

	public void validateFunctionElements(String functionName) {
		Element rootElement = getFunctionRootElement(functionName);

		if (rootElement == null) {
			return;
		}

		String functionFileName = getFunctionFileName(functionName);

		List<Element> commandElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "command");

		Set<String> commandElementNames = new HashSet<String>();

		for (Element commandElement : commandElements) {
			String commandName = commandElement.attributeValue("name");

			if (commandElementNames.contains(commandName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1009, functionFileName, commandElement, commandName);
			}
			else {
				commandElementNames.add(commandName);
			}
		}

		List<Element> conditionAndExecuteElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "condition");

		conditionAndExecuteElements.addAll(
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "execute"));

		for (Element conditionAndExecuteElement : conditionAndExecuteElements) {
			String function = conditionAndExecuteElement.attributeValue(
				"function");
			String selenium = conditionAndExecuteElement.attributeValue(
				"selenium");

			if (function != null) {
				_validateFunctionElement(
					functionFileName, conditionAndExecuteElement);
			}
			else if (selenium != null) {
				_validateSeleniumElement(
					functionFileName, conditionAndExecuteElement);
			}
		}
	}

	public void validateMacroElements(String macroName) {
		Element rootElement = getMacroRootElement(macroName);

		if (rootElement == null) {
			return;
		}

		String macroFileName = getMacroFileName(macroName);

		List<Element> commandElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "command");

		Set<String> commandElementNames = new HashSet<String>();

		for (Element commandElement : commandElements) {
			String commandName = commandElement.attributeValue("name");

			if (commandElementNames.contains(commandName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1009, macroFileName, commandElement, commandName);
			}
			else {
				commandElementNames.add(commandName);
			}
		}

		List<Element> conditionAndExecuteElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "condition");

		conditionAndExecuteElements.addAll(
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "execute"));

		for (Element conditionAndExecuteElement : conditionAndExecuteElements) {
			String action = conditionAndExecuteElement.attributeValue("action");
			String macro = conditionAndExecuteElement.attributeValue("macro");

			if (action != null) {
				_validateActionElement(
					macroFileName, conditionAndExecuteElement);
			}
			else if (macro != null) {
				_validateMacroElement(
					macroFileName, conditionAndExecuteElement);
			}
		}
	}

	public void validateTestCaseElements(String testCaseName) {
		Element rootElement = getTestCaseRootElement(testCaseName);

		if (rootElement == null) {
			return;
		}

		String testCaseFileName = getTestCaseFileName(testCaseName);

		List<Element> commandElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "command");

		Set<String> commandElementNames = new HashSet<String>();

		for (Element commandElement : commandElements) {
			String commandName = commandElement.attributeValue("name");

			if (commandElementNames.contains(commandName)) {
				_seleniumBuilderFileUtil.throwValidationException(
					1009, testCaseFileName, commandElement, commandName);
			}
			else {
				commandElementNames.add(commandName);
			}
		}

		List<Element> executeElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "execute");

		for (Element executeElement : executeElements) {
			String action = executeElement.attributeValue("action");
			String macro = executeElement.attributeValue("macro");

			if (action != null) {
				_validateActionElement(testCaseFileName, executeElement);
			}
			else if (macro != null) {
				_validateMacroElement(testCaseFileName, executeElement);
			}
		}
	}

	public void validateTestSuiteElements(String testSuiteName) {
		Element rootElement = getTestSuiteRootElement(testSuiteName);

		List<Element> executeElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "execute");

		String testSuiteFileName = getTestSuiteFileName(testSuiteName);

		for (Element executeElement : executeElements) {
			String testCase = executeElement.attributeValue("test-case");
			String testSuite = executeElement.attributeValue("test-suite");

			if (testCase != null) {
				_validateTestCaseElement(testSuiteFileName, executeElement);
			}
			else if (testSuite != null) {
				_validateTestSuiteElement(testSuiteFileName, executeElement);
			}
		}
	}

	private String _getClassName(String fileName) {
		return _seleniumBuilderFileUtil.getClassName(fileName);
	}

	private String _getClassName(String fileName, String classSuffix) {
		return _seleniumBuilderFileUtil.getClassName(fileName, classSuffix);
	}

	private String _getHTMLFileName(String fileName) {
		return _seleniumBuilderFileUtil.getHTMLFileName(fileName);
	}

	private String _getJavaFileName(String fileName) {
		return _seleniumBuilderFileUtil.getJavaFileName(fileName);
	}

	private String _getJavaFileName(String fileName, String classSuffix) {
		return _seleniumBuilderFileUtil.getJavaFileName(fileName, classSuffix);
	}

	private int _getLocatorCount(Element rootElement) throws Exception {
		return _seleniumBuilderFileUtil.getLocatorCount(rootElement);
	}

	private String _getName(String fileName) {
		return _seleniumBuilderFileUtil.getName(fileName);
	}

	private String _getPackageName(String fileName) {
		return _seleniumBuilderFileUtil.getPackageName(fileName);
	}

	private String _getReturnType(String name) throws Exception {
		return _seleniumBuilderFileUtil.getReturnType(name);
	}

	private Element _getRootElement(String fileName) throws Exception {
		return _seleniumBuilderFileUtil.getRootElement(fileName);
	}

	private String _getSimpleClassName(String fileName) {
		return _seleniumBuilderFileUtil.getSimpleClassName(fileName);
	}

	private String _getSimpleClassName(String fileName, String classSuffix) {
		return _seleniumBuilderFileUtil.getSimpleClassName(
			fileName, classSuffix);
	}

	private boolean _isActionName(String name) {
		for (String actionName : _actionNames) {
			if (actionName.equals(name)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isFunctionCommand(String name, String command) {
		if (!_isFunctionName(name)) {
			return false;
		}

		Element rootElement = getFunctionRootElement(name);

		List<Element> commandElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "command");

		for (Element commandElement : commandElements) {
			String commandName = commandElement.attributeValue("name");

			if (commandName.equals(command)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isFunctionName(String name) {
		for (String functionName : _functionNames) {
			if (functionName.equals(StringUtil.upperCaseFirstLetter(name))) {
				return true;
			}
		}

		return false;
	}

	private boolean _isMacroCommand(String name, String command) {
		if (!_isMacroName(name)) {
			return false;
		}

		Element rootElement = getMacroRootElement(name);

		List<Element> commandElements =
			_seleniumBuilderFileUtil.getAllChildElements(
				rootElement, "command");

		for (Element commandElement : commandElements) {
			String commandName = commandElement.attributeValue("name");

			if (commandName.equals(command)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isMacroName(String name) {
		for (String macroName : _macroNames) {
			if (macroName.equals(name)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isSeleniumCommand(String command) {
		if (_seleniumParameterCounts.containsKey(command)) {
			return true;
		}

		return false;
	}

	private boolean _isTestCaseName(String name) {
		for (String testCaseName : _testCaseNames) {
			if (testCaseName.equals(name)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isTestSuiteName(String name) {
		for (String testSuiteName : _testSuiteNames) {
			if (testSuiteName.equals(name)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isValidLocatorKey(
		String actionName, String caseComparator, String locatorKey) {

		Element pathRootElement = getPathRootElement(actionName);

		Set<String> pathLocatorKeys =
			_seleniumBuilderFileUtil.getPathLocatorKeys(pathRootElement);

		String partialKey1 = "";
		String partialKey2 = "";

		if (locatorKey.contains("${") && locatorKey.contains("}")) {
			caseComparator = "partial";

			int x = locatorKey.indexOf("${");
			int y = locatorKey.indexOf("}");

			partialKey1 = locatorKey.substring(0, x);
			partialKey2 = locatorKey.substring(y + 1);
		}

		for (String pathLocatorKey : pathLocatorKeys) {
			if (caseComparator == null) {
				if (pathLocatorKey.equals(locatorKey)) {
					return true;
				}
			}
			else {
				if (caseComparator.equals("contains") &&
					pathLocatorKey.contains(locatorKey)) {

					return true;
				}
				else if (caseComparator.equals("endsWith") &&
						 pathLocatorKey.endsWith(locatorKey)) {

					return true;
				}
				else if (caseComparator.equals("partial") &&
						 pathLocatorKey.contains(partialKey1) &&
						 pathLocatorKey.contains(partialKey2)) {

					return true;
				}
				else if (caseComparator.equals("startsWith") &&
						 pathLocatorKey.startsWith(locatorKey)) {

					return true;
				}
			}
		}

		return false;
	}

	private String _normalizeFileName(String fileName) {
		return _seleniumBuilderFileUtil.normalizeFileName(fileName);
	}

	private void _validateActionElement(String fileName, Element element) {
		String action = element.attributeValue("action");

		int x = action.indexOf(StringPool.POUND);

		if (x == -1) {
			_seleniumBuilderFileUtil.throwValidationException(
				1006, fileName, element, "action");
		}

		String actionName = action.substring(0, x);

		if (!_isActionName(actionName)) {
			_seleniumBuilderFileUtil.throwValidationException(
				1011, fileName, element, "action", actionName);
		}

		String actionCommand = action.substring(x + 1);

		if (!_isFunctionName(actionCommand)) {
			_seleniumBuilderFileUtil.throwValidationException(
				1012, fileName, element, "action", actionCommand);
		}

		_validateLocatorKeyElement(fileName, actionName, element);
	}

	private void _validateFunctionElement(String fileName, Element element) {
		String function = element.attributeValue("function");

		int x = function.indexOf(StringPool.POUND);

		if (x == -1) {
			_seleniumBuilderFileUtil.throwValidationException(
				1006, fileName, element, "function");
		}

		String functionName = function.substring(0, x);

		if (!_isFunctionName(functionName)) {
			_seleniumBuilderFileUtil.throwValidationException(
				1011, fileName, element, "function", functionName);
		}

		String functionCommand = function.substring(x + 1);

		if (!_isFunctionCommand(functionName, functionCommand)) {
			_seleniumBuilderFileUtil.throwValidationException(
				1012, fileName, element, "function", functionCommand);
		}
	}

	private void _validateLocatorKeyElement(
		String fileName, String actionName, Element element) {

		String comparator = element.attributeValue("comparator");

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (attributeName.startsWith("locator-key")) {
				String attributeValue = attribute.getValue();

				if (!_isValidLocatorKey(
						actionName, comparator, attributeValue)) {

					_seleniumBuilderFileUtil.throwValidationException(
						1010, fileName, element, attributeValue);
				}
			}
		}
	}

	private void _validateMacroElement(String fileName, Element element) {
		String macro = element.attributeValue("macro");

		int x = macro.indexOf(StringPool.POUND);

		if (x == -1) {
			_seleniumBuilderFileUtil.throwValidationException(
				1006, fileName, element, "macro");
		}

		String macroName = macro.substring(0, x);

		if (!_isMacroName(macroName)) {
			_seleniumBuilderFileUtil.throwValidationException(
				1011, fileName, element, "macro", macroName);
		}

		String macroCommand = macro.substring(x + 1);

		if (!_isMacroCommand(macroName, macroCommand)) {
			_seleniumBuilderFileUtil.throwValidationException(
				1012, fileName, element, "macro", macroCommand);
		}
	}

	private void _validateSeleniumElement(String fileName, Element element) {
		String selenium = element.attributeValue("selenium");

		if (!_isSeleniumCommand(selenium)) {
			_seleniumBuilderFileUtil.throwValidationException(
				1012, fileName, element, "selenium", selenium);
		}
	}

	private void _validateTestCaseElement(String fileName, Element element) {
		String testCase = element.attributeValue("test-case");

		if (!_isTestCaseName(testCase)) {
			_seleniumBuilderFileUtil.throwValidationException(
				1011, fileName, element, "test-case", testCase);
		}
	}

	private void _validateTestSuiteElement(String fileName, Element element) {
		String testSuite = element.attributeValue("test-suite");

		if (!_isTestSuiteName(testSuite)) {
			_seleniumBuilderFileUtil.throwValidationException(
				1011, fileName, element, "test-suite", testSuite);
		}
	}

	private Map<String, String> _actionClassNames =
		new HashMap<String, String>();
	private Map<String, String> _actionFileNames =
		new HashMap<String, String>();
	private Map<String, String> _actionJavaFileNames =
		new HashMap<String, String>();
	private Set<String> _actionNames = new HashSet<String>();
	private Map<String, String> _actionPackageNames =
		new HashMap<String, String>();
	private Map<String, Element> _actionRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _actionSimpleClassNames =
		new HashMap<String, String>();
	private String _baseDir;
	private Map<String, String> _functionClassNames =
		new HashMap<String, String>();
	private Map<String, String> _functionFileNames =
		new HashMap<String, String>();
	private Map<String, String> _functionJavaFileNames =
		new HashMap<String, String>();
	private Map<String, Integer> _functionLocatorCounts =
		new HashMap<String, Integer>();
	private Set<String> _functionNames = new HashSet<String>();
	private Map<String, String> _functionPackageNames =
		new HashMap<String, String>();
	private Map<String, String> _functionReturnTypes =
		new HashMap<String, String>();
	private Map<String, Element> _functionRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _functionSimpleClassNames =
		new HashMap<String, String>();
	private Map<String, String> _macroClassNames =
		new HashMap<String, String>();
	private Map<String, String> _macroFileNames = new HashMap<String, String>();
	private Map<String, String> _macroJavaFileNames =
		new HashMap<String, String>();
	private Set<String> _macroNames = new HashSet<String>();
	private Map<String, String> _macroPackageNames =
		new HashMap<String, String>();
	private Map<String, Element> _macroRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _macroSimpleClassNames =
		new HashMap<String, String>();
	private Map<String, String> _pathClassNames = new HashMap<String, String>();
	private Map<String, String> _pathFileNames = new HashMap<String, String>();
	private Map<String, String> _pathJavaFileNames =
		new HashMap<String, String>();
	private Set<String> _pathNames = new HashSet<String>();
	private Map<String, String> _pathPackageNames =
		new HashMap<String, String>();
	private Map<String, Element> _pathRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _pathSimpleClassNames =
		new HashMap<String, String>();
	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;
	private Map<String, Integer> _seleniumParameterCounts =
		new HashMap<String, Integer>();
	private Map<String, String> _testCaseClassNames =
		new HashMap<String, String>();
	private Map<String, String> _testCaseFileNames =
		new HashMap<String, String>();
	private Map<String, String> _testCaseHTMLFileNames =
		new HashMap<String, String>();
	private Map<String, String> _testCaseJavaFileNames =
		new HashMap<String, String>();
	private Set<String> _testCaseNames = new HashSet<String>();
	private Map<String, String> _testCasePackageNames =
		new HashMap<String, String>();
	private Map<String, Element> _testCaseRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _testCaseSimpleClassNames =
		new HashMap<String, String>();
	private Map<String, String> _testSuiteClassNames =
		new HashMap<String, String>();
	private Map<String, String> _testSuiteFileNames =
		new HashMap<String, String>();
	private Map<String, String> _testSuiteHTMLFileNames =
		new HashMap<String, String>();
	private Map<String, String> _testSuiteJavaFileNames =
		new HashMap<String, String>();
	private Set<String> _testSuiteNames = new HashSet<String>();
	private Map<String, String> _testSuitePackageNames =
		new HashMap<String, String>();
	private Map<String, Element> _testSuiteRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _testSuiteSimpleClassNames =
		new HashMap<String, String>();

}