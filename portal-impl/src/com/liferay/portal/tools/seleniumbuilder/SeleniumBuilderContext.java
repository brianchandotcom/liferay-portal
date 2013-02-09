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

				_actionRootElements.put(fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".function")) {
				_functionClassNames.add(_getClassName(fileName));

				String functionName = _getName(fileName);

				_functionFileNames.put(functionName, fileName);

				if (_functionNames.contains(functionName)) {
					throw new Exception(
						"Duplicate name " + functionName + " at " + fileName);
				}

				_functionNames.add(functionName);

				_functionRootElements.put(fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".macro")) {
				_macroClassNames.add(_getClassName(fileName));

				String macroName = _getName(fileName);

				_macroFileNames.put(macroName, fileName);

				if (_macroNames.contains(macroName)) {
					throw new Exception(
						"Duplicate name " + macroName + " at " + fileName);
				}

				_macroNames.add(macroName);

				_macroRootElements.put(fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".path")) {
				_actionClassNames.add(_getClassName(fileName, "Action"));

				_pathClassNames.add(_getClassName(fileName));

				String pathName = _getName(fileName);

				_pathFileNames.put(pathName, fileName);

				if (_pathNames.contains(pathName)) {
					throw new Exception(
						"Duplicate name " + pathName + " at " + fileName);
				}

				_pathNames.add(pathName);

				_pathRootElements.put(fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".testcase")) {
				_testCaseClassNames.add(_getClassName(fileName));

				String testCaseName = _getName(fileName);

				_testCaseFileNames.put(testCaseName, fileName);

				if (_testCaseNames.contains(testCaseName)) {
					throw new Exception(
						"Duplicate name " + testCaseName + " at " + fileName);
				}

				_testCaseNames.add(testCaseName);

				_testCaseRootElements.put(fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".testsuite")) {
				_testSuiteClassNames.add(_getClassName(fileName));

				String testSuiteName = _getName(fileName);

				_testSuiteFileNames.put(testSuiteName, fileName);

				if (_testSuiteNames.contains(testSuiteName)) {
					throw new Exception(
						"Duplicate name " + testSuiteName + " at " + fileName);
				}

				_testSuiteNames.add(testSuiteName);

				_testSuiteRootElements.put(fileName, _getRootElement(fileName));
			}
			else {
				throw new IllegalArgumentException("Invalid file " + fileName);
			}
		}
	}

	public Set<String> getActionClassNames() {
		return _actionClassNames;
	}

	public Map<String, String> getActionFileNames() {
		return _actionFileNames;
	}

	public Set<String> getActionNames() {
		return _actionNames;
	}

	public Element getActionRootElement(String fileName) {
		return _actionRootElements.get(fileName);
	}

	public Map<String, Element> getActionRootElements() {
		return _actionRootElements;
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public Set<String> getFunctionClassNames() {
		return _functionClassNames;
	}

	public Map<String, String> getFunctionFileNames() {
		return _functionFileNames;
	}

	public Set<String> getFunctionNames() {
		return _functionNames;
	}

	public Element getFunctionRootElement(String fileName) {
		return _functionRootElements.get(fileName);
	}

	public Map<String, Element> getFunctionRootElements() {
		return _functionRootElements;
	}

	public Set<String> getMacroClassNames() {
		return _macroClassNames;
	}

	public Map<String, String> getMacroFileNames() {
		return _macroFileNames;
	}

	public Set<String> getMacroNames() {
		return _macroNames;
	}

	public Element getMacroRootElement(String fileName) {
		return _macroRootElements.get(fileName);
	}

	public Map<String, Element> getMacroRootElements() {
		return _macroRootElements;
	}

	public Set<String> getPathClassNames() {
		return _pathClassNames;
	}

	public Map<String, String> getPathFileNames() {
		return _pathFileNames;
	}

	public Set<String> getPathNames() {
		return _pathNames;
	}

	public Element getPathRootElement(String fileName) {
		return _pathRootElements.get(fileName);
	}

	public Map<String, Element> getPathRootElements() {
		return _pathRootElements;
	}

	public Set<String> getTestCaseClassNames() {
		return _testCaseClassNames;
	}

	public Map<String, String> getTestCaseFileNames() {
		return _testCaseFileNames;
	}

	public Set<String> getTestCaseNames() {
		return _testCaseNames;
	}

	public Element getTestCaseRootElement(String fileName) {
		return _testCaseRootElements.get(fileName);
	}

	public Map<String, Element> getTestCaseRootElements() {
		return _testCaseRootElements;
	}

	public Set<String> getTestSuiteClassNames() {
		return _testSuiteClassNames;
	}

	public Map<String, String> getTestSuiteFileNames() {
		return _testSuiteFileNames;
	}

	public Set<String> getTestSuiteNames() {
		return _testSuiteNames;
	}

	public Element getTestSuiteRootElement(String fileName) {
		return _testSuiteRootElements.get(fileName);
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

	private Set<String> _actionClassNames = new HashSet<String>();
	private Map<String, String> _actionFileNames =
		new HashMap<String, String>();
	private Set<String> _actionNames = new HashSet<String>();
	private Map<String, Element> _actionRootElements =
		new HashMap<String, Element>();
	private String _baseDir;
	private Set<String> _functionClassNames = new HashSet<String>();
	private Map<String, String> _functionFileNames =
		new HashMap<String, String>();
	private Set<String> _functionNames = new HashSet<String>();
	private Map<String, Element> _functionRootElements =
		new HashMap<String, Element>();
	private Set<String> _macroClassNames = new HashSet<String>();
	private Map<String, String> _macroFileNames = new HashMap<String, String>();
	private Set<String> _macroNames = new HashSet<String>();
	private Map<String, Element> _macroRootElements =
		new HashMap<String, Element>();
	private Set<String> _pathClassNames = new HashSet<String>();
	private Map<String, String> _pathFileNames = new HashMap<String, String>();
	private Set<String> _pathNames = new HashSet<String>();
	private Map<String, Element> _pathRootElements =
		new HashMap<String, Element>();
	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;
	private Set<String> _testCaseClassNames = new HashSet<String>();
	private Map<String, String> _testCaseFileNames =
		new HashMap<String, String>();
	private Set<String> _testCaseNames = new HashSet<String>();
	private Map<String, Element> _testCaseRootElements =
		new HashMap<String, Element>();
	private Set<String> _testSuiteClassNames = new HashSet<String>();
	private Map<String, String> _testSuiteFileNames =
		new HashMap<String, String>();
	private Set<String> _testSuiteNames = new HashSet<String>();
	private Map<String, Element> _testSuiteRootElements =
		new HashMap<String, Element>();

}