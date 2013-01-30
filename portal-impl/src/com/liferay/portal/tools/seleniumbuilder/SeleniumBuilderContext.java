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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
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

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_baseDir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\*.action", "**\\*.function", "**\\*.macro", "**\\*.path",
				"**\\*.testcase", "**\\*.testsuite"
			});

		directoryScanner.scan();

		String[] fileNames = directoryScanner.getIncludedFiles();

		_seleniumBuilderFileUtil = new SeleniumBuilderFileUtil(_baseDir);

		for (String fileName : fileNames) {
			fileName = _seleniumBuilderFileUtil.normalizeFileName(fileName);

			if (fileName.endsWith(".action")) {
				_actionFileNames.add(fileName);

				Element root =
					_seleniumBuilderFileUtil.getRootElementByFileName(fileName);

				_actionRootElements.put(fileName, root);
			}
			else if (fileName.endsWith(".function")) {
				_functionFileNames.add(fileName);

				String className = StringUtil.replace(
					fileName, ".functions", "Function");

				className = StringUtil.replace(
					className, StringPool.SLASH, StringPool.PERIOD);

				_functionClassNames.add(className);

				Element root =
					_seleniumBuilderFileUtil.getRootElementByFileName(fileName);

				_functionRootElements.put(fileName, root);

				Set<String> functionCommands = new HashSet<String>();

				List<Element> functiondefs = root.elements("functiondef");

				for (Element functiondef : functiondefs) {
					functionCommands.add(functiondef.attributeValue("command"));
				}

				_functionCommandSets.put(fileName, functionCommands);

				String functionParams = root.attributeValue("params");

				int functionParam = GetterUtil.getInteger(functionParams);

				if (functionParam == 0) {
					_functionParams.put(fileName, 1);
				}
				else {
					_functionParams.put(fileName, functionParam);
				}

				String functionReturn = root.attributeValue("return");

				if (functionReturn == null) {
					_functionReturnTypes.put(fileName, "void");
				}
				else {
					_functionReturnTypes.put(fileName, functionReturn);
				}
			}
			else if (fileName.endsWith(".macro")) {
				_macroFileNames.add(fileName);

				String className = StringUtil.replace(
					fileName, ".macro", "Macro");

				className = StringUtil.replace(
					className, StringPool.SLASH, StringPool.PERIOD);

				_macroClassNames.add(className);

				Element root =
					_seleniumBuilderFileUtil.getRootElementByFileName(fileName);

				_macroRootElements.put(fileName, root);

				Set<String> macroCommands = new HashSet<String>();

				List<Element> macrodefs = root.elements("macrodef");

				for (Element macrodef : macrodefs) {
					macroCommands.add(macrodef.attributeValue("command"));
				}

				_macroCommandNames.put(fileName, macroCommands);
			}
			else if (fileName.endsWith(".path")) {
				_pathFileNames.add(fileName);

				String actionClassName = StringUtil.replace(
					fileName, ".path", "Action");

				actionClassName = StringUtil.replace(
					actionClassName, StringPool.SLASH, StringPool.PERIOD);

				_actionClassNames.add(actionClassName);

				String pathClassName = StringUtil.replace(
					fileName, ".path", "Path");

				pathClassName = StringUtil.replace(
					pathClassName, StringPool.SLASH, StringPool.PERIOD);

				_pathClassNames.add(pathClassName);

				Element root =
					_seleniumBuilderFileUtil.getRootElementByFileName(fileName);

				_pathRootElements.put(fileName, root);
			}
			else if (fileName.endsWith(".testcase")) {
				_testCaseFileNames.add(fileName);

				Element root =
					_seleniumBuilderFileUtil.getRootElementByFileName(fileName);

				_testCaseRootElements.put(fileName, root);
			}
			else if (fileName.endsWith(".testsuite")) {
				_testSuiteFileNames.add(fileName);

				Element root =
					_seleniumBuilderFileUtil.getRootElementByFileName(fileName);

				_testSuiteRootElements.put(fileName, root);
			}
			else {
				throw new IllegalArgumentException("Invalid file " + fileName);
			}
		}

		_seleniumParams = _putSeleniumParams(
			_seleniumParams, "com/liferay/portalweb/portal/util/" +
				"liferayselenium/SeleniumWrapper.java");

		_seleniumParams = _putSeleniumParams(
			_seleniumParams, "com/liferay/portalweb/portal/util/" +
				"liferayselenium/LiferaySelenium.java");

		_seleniumParams.put("isNotChecked", 1);
		_seleniumParams.put("isNotText", 2);
		_seleniumParams.put("isNotVisible", 1);
		_seleniumParams.put("open", 1);
	}

	public Set<String> getActionClassNames() {
		return _actionClassNames;
	}

	public Set<String> getActionFileNames() {
		return _actionFileNames;
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

	public Map<String, Set<String>> getFunctionCommandSets() {
		return _functionCommandSets;
	}

	public Set<String> getFunctionFileNames() {
		return _functionFileNames;
	}

	public Map<String, Integer> getFunctionParams() {
		return _functionParams;
	}

	public Map<String, String> getFunctionReturnTypes() {
		return _functionReturnTypes;
	}

	public Map<String, Element> getFunctionRootElements() {
		return _functionRootElements;
	}

	public Set<String> getMacroClassNames() {
		return _macroClassNames;
	}

	public Map<String, Set<String>> getMacroCommandNames() {
		return _macroCommandNames;
	}

	public Set<String> getMacroFileNames() {
		return _macroFileNames;
	}

	public Map<String, Element> getMacroRootElements() {
		return _macroRootElements;
	}

	public Set<String> getPathClassNames() {
		return _pathClassNames;
	}

	public Set<String> getPathFileNames() {
		return _pathFileNames;
	}

	public Map<String, Element> getPathRootElements() {
		return _pathRootElements;
	}

	public Map<String, Integer> getSeleniumParams() {
		return _seleniumParams;
	}

	public Set<String> getTestCaseFileNames() {
		return _testCaseFileNames;
	}

	public Map<String, Element> getTestCaseRootElements() {
		return _testCaseRootElements;
	}

	public Map<String, Element> getTestCaseSuiteRootElements() {
		return _testSuiteRootElements;
	}

	public Set<String> getTestSuiteFileNames() {
		return _testSuiteFileNames;
	}

	private Map<String, Integer> _putSeleniumParams(
		Map<String, Integer> map, String file) throws Exception {

		String content = _seleniumBuilderFileUtil.getNormalizedContent(file);

		Pattern pattern = Pattern.compile(
			"public (boolean|String|void) [A-Za-z0-9]*\\(.*?\\)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String methodDeclaraction = matcher.group();

			int x = methodDeclaraction.indexOf("(");
			int y = methodDeclaraction.indexOf(")");

			String methodName = null;

			if (methodDeclaraction.startsWith("public boolean")) {
				methodName = methodDeclaraction.substring(15, x);
			}
			else if (methodDeclaraction.startsWith("public String")) {
				methodName = methodDeclaraction.substring(14, x);
			}
			else if (methodDeclaraction.startsWith("public void")) {
				methodName = methodDeclaraction.substring(12, x);
			}

			String methodParams = methodDeclaraction.substring(x + 1, y);

			if (methodParams.equals("")) {
				map.put(methodName, 0);
			}
			else {
				int methodParamsCount = StringUtil.count(methodParams, ",") + 1;

				map.put(methodName, methodParamsCount);
			}
		}

		return map;
	}

	private Set<String> _actionClassNames = new HashSet<String>();
	private Set<String> _actionFileNames = new HashSet<String>();
	private Map<String, Element> _actionRootElements =
		new HashMap<String, Element>();
	private String _baseDir;
	private Set<String> _functionClassNames = new HashSet<String>();
	private Map<String, Set<String>> _functionCommandSets =
		new HashMap<String, Set<String>>();
	private Set<String> _functionFileNames = new HashSet<String>();
	private Map<String, Integer> _functionParams =
		new HashMap<String, Integer>();
	private Map<String, String> _functionReturnTypes =
		new HashMap<String, String>();
	private Map<String, Element> _functionRootElements =
		new HashMap<String, Element>();
	private Set<String> _macroClassNames = new HashSet<String>();
	private Map<String, Set<String>> _macroCommandNames =
		new HashMap<String, Set<String>>();
	private Set<String> _macroFileNames = new HashSet<String>();
	private Map<String, Element> _macroRootElements =
		new HashMap<String, Element>();
	private Set<String> _pathClassNames = new HashSet<String>();
	private Set<String> _pathFileNames = new HashSet<String>();
	private Map<String, Element> _pathRootElements =
		new HashMap<String, Element>();
	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;
	private Map<String, Integer> _seleniumParams =
		new HashMap<String, Integer>();
	private Set<String> _testCaseFileNames = new HashSet<String>();
	private Map<String, Element> _testCaseRootElements =
		new HashMap<String, Element>();
	private Set<String> _testSuiteFileNames = new HashSet<String>();
	private Map<String, Element> _testSuiteRootElements =
		new HashMap<String, Element>();

}