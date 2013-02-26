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

				_actionNames.add(actionName);

				_actionRootElements.put(actionName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".function")) {
				String functionName = _getName(fileName);

				_functionClassNames.put(functionName, _getClassName(fileName));

				_functionFileNames.put(functionName, fileName);

				if (_functionNames.contains(functionName)) {
					throw new Exception(
						"Duplicate name " + functionName + " at " + fileName);
				}

				_functionNames.add(functionName);

				_functionRootElements.put(
					functionName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".macro")) {
				String macroName = _getName(fileName);

				_macroClassNames.put(macroName, _getClassName(fileName));

				_macroFileNames.put(macroName, fileName);

				if (_macroNames.contains(macroName)) {
					throw new Exception(
						"Duplicate name " + macroName + " at " + fileName);
				}

				_macroNames.add(macroName);

				_macroRootElements.put(macroName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".path")) {
				String pathName = _getName(fileName);

				_actionClassNames.put(
					pathName, _getClassName(fileName, "Action"));

				_pathClassNames.put(pathName, _getClassName(fileName));

				_pathFileNames.put(pathName, fileName);

				if (_pathNames.contains(pathName)) {
					throw new Exception(
						"Duplicate name " + pathName + " at " + fileName);
				}

				_pathNames.add(pathName);

				_pathRootElements.put(pathName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".testcase")) {
				String testCaseName = _getName(fileName);

				_testCaseClassNames.put(testCaseName, _getClassName(fileName));

				_testCaseFileNames.put(testCaseName, fileName);

				if (_testCaseNames.contains(testCaseName)) {
					throw new Exception(
						"Duplicate name " + testCaseName + " at " + fileName);
				}

				_testCaseNames.add(testCaseName);

				_testCaseRootElements.put(
					testCaseName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".testsuite")) {
				String testSuiteName = _getName(fileName);

				_testSuiteClassNames.put(
					testSuiteName, _getClassName(fileName));

				_testSuiteFileNames.put(testSuiteName, fileName);

				if (_testSuiteNames.contains(testSuiteName)) {
					throw new Exception(
						"Duplicate name " + testSuiteName + " at " + fileName);
				}

				_testSuiteNames.add(testSuiteName);

				_testSuiteRootElements.put(
					testSuiteName, _getRootElement(fileName));
			}
			else {
				throw new IllegalArgumentException("Invalid file " + fileName);
			}
		}
	}

	public String getActionClassName(String actionName) {
		return _actionClassNames.get(actionName);
	}

	public Map<String, String> getActionClassNames() {
		return _actionClassNames;
	}

	public String getActionFileName(String actionName) {
		return _actionFileNames.get(actionName);
	}

	public Map<String, String> getActionFileNames() {
		return _actionFileNames;
	}

	public Set<String> getActionNames() {
		return _actionNames;
	}

	public Element getActionRootElement(String actionName) {
		return _actionRootElements.get(actionName);
	}

	public Map<String, Element> getActionRootElements() {
		return _actionRootElements;
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public String getFunctionClassName(String functionName) {
		return _functionClassNames.get(functionName);
	}

	public Map<String, String> getFunctionClassNames() {
		return _functionClassNames;
	}

	public String getFunctionFileName(String functionName) {
		return _functionFileNames.get(functionName);
	}

	public Map<String, String> getFunctionFileNames() {
		return _functionFileNames;
	}

	public Set<String> getFunctionNames() {
		return _functionNames;
	}

	public Element getFunctionRootElement(String functionName) {
		return _functionRootElements.get(functionName);
	}

	public Map<String, Element> getFunctionRootElements() {
		return _functionRootElements;
	}

	public String getMacroClassName(String macroName) {
		return _macroClassNames.get(macroName);
	}

	public Map<String, String> getMacroClassNames() {
		return _macroClassNames;
	}

	public String getMacroFileName(String macroName) {
		return _macroFileNames.get(macroName);
	}

	public Map<String, String> getMacroFileNames() {
		return _macroFileNames;
	}

	public Set<String> getMacroNames() {
		return _macroNames;
	}

	public Element getMacroRootElement(String macroName) {
		return _macroRootElements.get(macroName);
	}

	public Map<String, Element> getMacroRootElements() {
		return _macroRootElements;
	}

	public String getPathClassName(String pathName) {
		return _pathClassNames.get(pathName);
	}

	public Map<String, String> getPathClassNames() {
		return _pathClassNames;
	}

	public String getPathFileName(String pathName) {
		return _pathFileNames.get(pathName);
	}

	public Map<String, String> getPathFileNames() {
		return _pathFileNames;
	}

	public Set<String> getPathNames() {
		return _pathNames;
	}

	public Element getPathRootElement(String pathName) {
		return _pathRootElements.get(pathName);
	}

	public Map<String, Element> getPathRootElements() {
		return _pathRootElements;
	}

	public String getTestCaseClassName(String testCaseName) {
		return _testCaseClassNames.get(testCaseName);
	}

	public Map<String, String> getTestCaseClassNames() {
		return _testCaseClassNames;
	}

	public String getTestCaseFileName(String testCaseName) {
		return _testCaseFileNames.get(testCaseName);
	}

	public Map<String, String> getTestCaseFileNames() {
		return _testCaseFileNames;
	}

	public Set<String> getTestCaseNames() {
		return _testCaseNames;
	}

	public Element getTestCaseRootElement(String testCaseName) {
		return _testCaseRootElements.get(testCaseName);
	}

	public Map<String, Element> getTestCaseRootElements() {
		return _testCaseRootElements;
	}

	public String getTestSuiteClassName(String testSuiteName) {
		return _testSuiteClassNames.get(testSuiteName);
	}

	public Map<String, String> getTestSuiteClassNames() {
		return _testSuiteClassNames;
	}

	public String getTestSuiteFileName(String testSuiteName) {
		return _testSuiteFileNames.get(testSuiteName);
	}

	public Map<String, String> getTestSuiteFileNames() {
		return _testSuiteFileNames;
	}

	public Set<String> getTestSuiteNames() {
		return _testSuiteNames;
	}

	public Element getTestSuiteRootElement(String testSuiteName) {
		return _testSuiteRootElements.get(testSuiteName);
	}

	public Map<String, Element> getTestSuiteRootElements() {
		return _testSuiteRootElements;
	}

	private String _getClassName(String fileName) {
		return _seleniumBuilderFileUtil.getClassName(fileName);
	}

	private String _getClassName(String fileName, String classSuffix) {
		return _seleniumBuilderFileUtil.getClassName(fileName, classSuffix);
	}

	private String _getName(String fileName) {
		return _seleniumBuilderFileUtil.getName(fileName);
	}

	private Element _getRootElement(String fileName) throws Exception {
		return _seleniumBuilderFileUtil.getRootElement(fileName);
	}

	private String _normalizeFileName(String fileName) {
		return _seleniumBuilderFileUtil.normalizeFileName(fileName);
	}

	private Map<String, String> _actionClassNames =
		new HashMap<String, String>();
	private Map<String, String> _actionFileNames =
		new HashMap<String, String>();
	private Set<String> _actionNames = new HashSet<String>();
	private Map<String, Element> _actionRootElements =
		new HashMap<String, Element>();
	private String _baseDir;
	private Map<String, String> _functionClassNames =
		new HashMap<String, String>();
	private Map<String, String> _functionFileNames =
		new HashMap<String, String>();
	private Set<String> _functionNames = new HashSet<String>();
	private Map<String, Element> _functionRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _macroClassNames =
		new HashMap<String, String>();
	private Map<String, String> _macroFileNames = new HashMap<String, String>();
	private Set<String> _macroNames = new HashSet<String>();
	private Map<String, Element> _macroRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _pathClassNames = new HashMap<String, String>();
	private Map<String, String> _pathFileNames = new HashMap<String, String>();
	private Set<String> _pathNames = new HashSet<String>();
	private Map<String, Element> _pathRootElements =
		new HashMap<String, Element>();
	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;
	private Map<String, String> _testCaseClassNames =
		new HashMap<String, String>();
	private Map<String, String> _testCaseFileNames =
		new HashMap<String, String>();
	private Set<String> _testCaseNames = new HashSet<String>();
	private Map<String, Element> _testCaseRootElements =
		new HashMap<String, Element>();
	private Map<String, String> _testSuiteClassNames =
		new HashMap<String, String>();
	private Map<String, String> _testSuiteFileNames =
		new HashMap<String, String>();
	private Set<String> _testSuiteNames = new HashSet<String>();
	private Map<String, Element> _testSuiteRootElements =
		new HashMap<String, Element>();

}