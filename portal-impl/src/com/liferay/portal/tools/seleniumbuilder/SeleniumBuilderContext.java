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

import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
			fileName = _normalizeFileName(fileName);

			if (fileName.endsWith(".action")) {
				String actionName = _getName(fileName);

				_actionFileNames.put(actionName, fileName);

				if (_actionNames.contains(actionName)) {
					throw new Exception(
						"Duplicate name " + actionName + " at " + fileName);
				}

				_actionRootElements.put(actionName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".function")) {
				String functionName = _getName(fileName);

				_functionClassNames.put(functionName, _getClassName(fileName));

				_functionFileNames.put(functionName, fileName);

				_functionJavaFileNames.put(
					functionName, _getJavaFileName(fileName));

				if (_functionNames.contains(functionName)) {
					throw new Exception(
						"Duplicate name " + functionName + " at " + fileName);
				}

				_functionNames.add(functionName);

				_functionPackageNames.put(
					functionName, _getPackageName(fileName));

				_functionReturnTypes.put(
					functionName, _getReturnType(functionName));

				Element rootElement = _getRootElement(fileName);

				_functionRootElements.put(functionName, rootElement);

				_functionSimpleClassNames.put(
					functionName, _getSimpleClassName(fileName));

				_functionTargetCounts.put(
					functionName, _getTargetCount(rootElement));
			}
			else if (fileName.endsWith(".macro")) {
				String macroName = _getName(fileName);

				_macroClassNames.put(macroName, _getClassName(fileName));

				_macroFileNames.put(macroName, fileName);

				_macroJavaFileNames.put(macroName, _getJavaFileName(fileName));

				if (_macroNames.contains(macroName)) {
					throw new Exception(
						"Duplicate name " + macroName + " at " + fileName);
				}

				_macroNames.add(macroName);

				_macroPackageNames.put(macroName, _getPackageName(fileName));

				_macroSimpleClassNames.put(
					macroName, _getSimpleClassName(fileName));

				_macroRootElements.put(macroName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".path")) {
				String pathName = _getName(fileName);

				_actionClassNames.put(
					pathName, _getClassName(fileName, "Action"));

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
					throw new Exception(
						"Duplicate name " + pathName + " at " + fileName);
				}

				_pathNames.add(pathName);

				_pathPackageNames.put(pathName, _getPackageName(fileName));

				_pathRootElements.put(pathName, _getRootElement(fileName));

				_pathSimpleClassNames.put(
					pathName, _getSimpleClassName(fileName));
			}
			else if (fileName.endsWith(".testcase")) {
				String testCaseName = _getName(fileName);

				_testCaseClassNames.put(testCaseName, _getClassName(fileName));

				_testCaseFileNames.put(testCaseName, fileName);

				_testCaseJavaFileNames.put(
					testCaseName, _getJavaFileName(fileName));

				if (_testCaseNames.contains(testCaseName)) {
					throw new Exception(
						"Duplicate name " + testCaseName + " at " + fileName);
				}

				_testCaseNames.add(testCaseName);

				_testCasePackageNames.put(
					testCaseName, _getPackageName(fileName));

				_testCaseRootElements.put(
					testCaseName, _getRootElement(fileName));

				_testCaseSimpleClassNames.put(
					testCaseName, _getSimpleClassName(fileName));
			}
			else if (fileName.endsWith(".testsuite")) {
				String testSuiteName = _getName(fileName);

				_testSuiteClassNames.put(
					testSuiteName, _getClassName(fileName));

				_testSuiteFileNames.put(testSuiteName, fileName);

				_testSuiteJavaFileNames.put(
					testSuiteName, _getJavaFileName(fileName));

				if (_testSuiteNames.contains(testSuiteName)) {
					throw new Exception(
						"Duplicate name " + testSuiteName + " at " + fileName);
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

	public int getFunctionTargetCount(String functionName) {
		return _functionTargetCounts.get(functionName);
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

	public String getTestCaseClassName(String testCaseName) {
		return _testCaseClassNames.get(testCaseName);
	}

	public String getTestCaseFileName(String testCaseName) {
		return _testCaseFileNames.get(testCaseName);
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

	private String _getClassName(String fileName) {
		return _seleniumBuilderFileUtil.getClassName(fileName);
	}

	private String _getClassName(String fileName, String classSuffix) {
		return _seleniumBuilderFileUtil.getClassName(fileName, classSuffix);
	}

	private String _getJavaFileName(String fileName) {
		return _seleniumBuilderFileUtil.getJavaFileName(fileName);
	}

	private String _getJavaFileName(String fileName, String classSuffix) {
		return _seleniumBuilderFileUtil.getJavaFileName(fileName, classSuffix);
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

	private int _getTargetCount(Element rootElement) throws Exception {
		return _seleniumBuilderFileUtil.getTargetCount(rootElement);
	}

	private String _normalizeFileName(String fileName) {
		return _seleniumBuilderFileUtil.normalizeFileName(fileName);
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
	private Set<String> _functionNames = new HashSet<String>();
	private Map<String, String> _functionPackageNames =
		new HashMap<String, String>();
	private Map<String, String> _functionReturnTypes =
		new HashMap<String, String>();
	private Map<String, Element> _functionRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _functionSimpleClassNames =
		new HashMap<String, String>();
	private Map<String, Integer> _functionTargetCounts =
		new HashMap<String, Integer>();
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
	private Map<String, String> _testCaseClassNames =
		new HashMap<String, String>();
	private Map<String, String> _testCaseFileNames =
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