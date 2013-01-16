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
import com.liferay.portal.util.InitUtil;

import jargs.gnu.CmdLineParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new SeleniumBuilder(args);
	}

	public SeleniumBuilder(String[] args) throws Exception {
		CmdLineParser cmdLineParser = new CmdLineParser();

		CmdLineParser.Option basedirOption = cmdLineParser.addStringOption(
			"basedir");
		CmdLineParser.Option typeOption = cmdLineParser.addStringOption("type");

		cmdLineParser.parse(args);

		String type = (String)cmdLineParser.getOptionValue(typeOption);

		List<String> fileTypes = Arrays.asList(type.split(","));

		_basedir = (String)cmdLineParser.getOptionValue(basedirOption);

		_seleniumFileUtil = new SeleniumFileUtil(_basedir);

		_fileNameSet = _initFileNameSet();
		_seleniumParameterNumberMap = _initSeleniumParameterNumberMap();

		_classNameSetAvailable = _initClassNameSetAvailable();
		_fileNameSetActions = _initFileNameSetActions();
		_fileNameSetFunctions = _initFileNameSetFunctions();
		_fileNameSetMacros = _initFileNameSetMacros();
		_fileNameSetPaths = _initFileNameSetPaths();
		_fileNameSetTestCases = _initFileNameSetTestCases();
		_fileNameSetTestSuites = _initFileNameSetTestSuites();

		_directoryNameSet = _initDirectoryNameSet();
		_functionParameterNumberMap = _initFunctionParameterNumberMap();
		_functionReturnTypeMap = _initFunctionReturnTypeMap();

		_context = _initContext();

		BaseXMLToJavaBuilder baseXMLToJavaBuilder = new BaseXMLToJavaBuilder(
			_context);
		ActionsXMLToJavaBuilder actionsXMLToJavaBuilder =
			new ActionsXMLToJavaBuilder(_context);
		FunctionsXMLToJavaBuilder functionsXMLToJavaBuilder =
			new FunctionsXMLToJavaBuilder(_context);
		MacrosXMLToJavaBuilder macrosXMLToJavaBuilder =
			new MacrosXMLToJavaBuilder(_context);
		PathsXMLToJavaBuilder pathsXMLToJavaBuilder = new PathsXMLToJavaBuilder(
			_context);
		TestCasesXMLToJavaBuilder testCasesXMLToJavaBuilder =
			new TestCasesXMLToJavaBuilder(_context);
		TestPlansJavaBuilder testPlansJavaBuilder = new TestPlansJavaBuilder(
			_context);
		TestSuitesXMLToJavaBuilder testSuitesXMLToJavaBuilder =
			new TestSuitesXMLToJavaBuilder(_context);

		for (String fileName : _fileNameSet) {
			if (fileName.endsWith(".functions") &&
				fileTypes.contains("functions")) {

				functionsXMLToJavaBuilder.generateFunctions(fileName);
			}
			else if (fileName.endsWith(".macros") &&
					 fileTypes.contains("macros")) {

				macrosXMLToJavaBuilder.generateMacros(fileName);
			}
			else if (fileName.endsWith(".paths")) {
				if (fileTypes.contains("actions")) {
					actionsXMLToJavaBuilder.generateActions(fileName);
				}

				if (fileTypes.contains("paths")) {
					pathsXMLToJavaBuilder.generatePaths(fileName);
				}
			}
			else if (fileName.endsWith(".testcase") &&
					 fileTypes.contains("testcases")) {

				testCasesXMLToJavaBuilder.generateTestCase(fileName);
			}
			else if (fileName.endsWith(".testsuite") &&
					 fileTypes.contains("testsuites")) {

				testSuitesXMLToJavaBuilder.generateTestSuite(fileName);
			}
		}

		for (String directoryName : _directoryNameSet) {
			if (fileTypes.contains("testplans")) {
				testPlansJavaBuilder.generateTestPlan(directoryName);
			}
		}
	}

	private Set<String> _initClassNameSetAvailable() throws Exception {
		Set<String> classNameSet = new TreeSet<String>();

		for (String fileName : _fileNameSet) {
			if (fileName.endsWith(".functions")) {
				String className = StringUtil.replace(
					fileName, ".functions", "Functions");

				className = StringUtil.replace(
					className, StringPool.SLASH, StringPool.PERIOD);

				classNameSet.add(className);
			}
			else if (fileName.endsWith(".macros")) {
				String className = StringUtil.replace(
					fileName, ".macros", "Macros");

				className = StringUtil.replace(
					className, StringPool.SLASH, StringPool.PERIOD);

				classNameSet.add(className);

			}
			else if (fileName.endsWith(".paths")) {
				String className = StringUtil.replace(
					fileName, ".paths", "Actions");

				className = StringUtil.replace(
					className, StringPool.SLASH, StringPool.PERIOD);

				className = StringUtil.replace(
					className, ".paths.", ".actions.");

				classNameSet.add(className);
			}
		}

		return classNameSet;
	}

	private Map<String, Object> _initContext() throws Exception {
		Map<String, Object> hashMap = new HashMap<String, Object>();

		hashMap.put("basedir", _basedir);
		hashMap.put("classNameSetAvailable", _classNameSetAvailable);
		hashMap.put("directoryNameSet", _directoryNameSet);
		hashMap.put("fileNameSet", _fileNameSet);
		hashMap.put("fileNameSetActions", _fileNameSetActions);
		hashMap.put("fileNameSetFunctions", _fileNameSetFunctions);
		hashMap.put("fileNameSetMacros", _fileNameSetMacros);
		hashMap.put("fileNameSetPaths", _fileNameSetPaths);
		hashMap.put("fileNameSetTestCases", _fileNameSetTestCases);
		hashMap.put("fileNameSetTestSuite", _fileNameSetTestSuites);
		hashMap.put("functionParameterNumberMap", _functionParameterNumberMap);
		hashMap.put("functionReturnTypeMap", _functionReturnTypeMap);
		hashMap.put("seleniumParameterNumberMap", _seleniumParameterNumberMap);

		return hashMap;
	}

	private Set<String> _initDirectoryNameSet() throws Exception {
		Set<String> treeSet = new TreeSet<String>();

		for (String fileName : _fileNameSetTestCases) {
			int x = fileName.lastIndexOf("/");

			treeSet.add(fileName.substring(0, x));
		}

		return treeSet;
	}

	private Set<String> _initFileNameSet() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\**\\*.actions",
				"**\\portalweb\\**\\*.functions", "**\\portalweb\\**\\*.macros",
				"**\\portalweb\\**\\*.paths", "**\\portalweb\\**\\*.testcase",
				"**\\portalweb\\**\\*.testsuite"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = _seleniumFileUtil.normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		return fileNames;
	}

	private Set<String> _initFileNameSetActions() throws Exception {
		return _initFileNameSetType(".actions");
	}

	private Set<String> _initFileNameSetFunctions() throws Exception {
		return _initFileNameSetType(".functions");
	}

	private Set<String> _initFileNameSetMacros() throws Exception {
		return _initFileNameSetType(".macros");
	}

	private Set<String> _initFileNameSetPaths() throws Exception {
		return _initFileNameSetType(".paths");
	}

	private Set<String> _initFileNameSetTestCases() throws Exception {
		return _initFileNameSetType(".testcase");
	}

	private Set<String> _initFileNameSetTestSuites() throws Exception {
		return _initFileNameSetType(".testsuite");
	}

	private Set<String> _initFileNameSetType(String suffix) throws Exception {
		Set<String> fileNameTypeSet = new TreeSet<String>();

		for (String fileName : _fileNameSet) {
			if (fileName.endsWith(suffix)) {
				fileNameTypeSet.add(fileName);
			}
		}

		return fileNameTypeSet;
	}

	private Map<String, Integer> _initFunctionParameterNumberMap()
		throws Exception {

		Map<String, Integer> hashMap = new HashMap<String, Integer>();

		for (String fileName : _fileNameSetFunctions) {
			Element functions = _seleniumFileUtil.getRootElementByFileName(
				fileName);

			String functionsObject = functions.attributeValue("object");
			String functionsParams = functions.attributeValue("params");

			int functionsParamsInteger = GetterUtil.getInteger(functionsParams);

			if (functionsParamsInteger == 0) {
				hashMap.put(functionsObject, 1);
			}
			else {
				hashMap.put(functionsObject, functionsParamsInteger);
			}
		}

		return hashMap;
	}

	private Map<String, String> _initFunctionReturnTypeMap() throws Exception {
		Map<String, String> hashMap = new HashMap<String, String>();

		for (String fileName : _fileNameSetFunctions) {
			Element functions = _seleniumFileUtil.getRootElementByFileName(
				fileName);

			String functionsObject = functions.attributeValue("object");
			String functionsReturn = functions.attributeValue("return");

			if (functionsReturn == null) {
				hashMap.put(functionsObject, "void");
			}
			else {
				hashMap.put(functionsObject, functionsReturn);
			}
		}

		return hashMap;
	}

	private Map<String, Integer> _initSeleniumParameterNumberMap()
		throws Exception {

		Map<String, Integer> hashMap = new HashMap<String, Integer>();

		hashMap = _putSeleniumParameterNumberMapFile(
			hashMap, "com/liferay/portalweb/portal/util/liferayselenium/" +
				"SeleniumWrapper.java");

		hashMap = _putSeleniumParameterNumberMapFile(
			hashMap, "com/liferay/portalweb/portal/util/liferayselenium/" +
				"LiferaySelenium.java");

		hashMap.put("open", 1);
		hashMap.put("isNotChecked", 1);
		hashMap.put("isNotText", 1);
		hashMap.put("isNotVisible", 1);

		return hashMap;
	}

	private Map<String, Integer> _putSeleniumParameterNumberMapFile(
		Map<String, Integer> hashMap, String file) throws Exception {

		String content = _seleniumFileUtil.getNormalizedContent(file);

		Pattern pattern = Pattern.compile(
			"public (boolean|String|void) [A-Za-z0-9_]*\\(.*?\\)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String methodDeclaraction = matcher.group();

			int x = methodDeclaraction.indexOf("(");
			int y = methodDeclaraction.indexOf(")");

			String name = null;

			if (methodDeclaraction.startsWith("public boolean")) {
				name = methodDeclaraction.substring(15, x);
			}
			else if (methodDeclaraction.startsWith("public String")) {
				name = methodDeclaraction.substring(14, x);
			}
			else if (methodDeclaraction.startsWith("public void")) {
				name = methodDeclaraction.substring(12, x);
			}

			String params = methodDeclaraction.substring(x + 1, y);

			String[] paramsArray = params.split(",");

			int numParams;

			if (params.equals("")) {
				numParams = 0;
			}
			else {
				numParams = paramsArray.length;
			}

			hashMap.put(name, numParams);
		}

		return hashMap;
	}

	private String _basedir;
	private Set<String> _classNameSetAvailable;
	private Map<String, Object> _context;
	private Set<String> _directoryNameSet;
	private Set<String> _fileNameSet;
	private Set<String> _fileNameSetActions;
	private Set<String> _fileNameSetFunctions;
	private Set<String> _fileNameSetMacros;
	private Set<String> _fileNameSetPaths;
	private Set<String> _fileNameSetTestCases;
	private Set<String> _fileNameSetTestSuites;
	private Map<String, Integer> _functionParameterNumberMap;
	private Map<String, String> _functionReturnTypeMap;
	private SeleniumFileUtil _seleniumFileUtil;
	private Map<String, Integer> _seleniumParameterNumberMap;

}