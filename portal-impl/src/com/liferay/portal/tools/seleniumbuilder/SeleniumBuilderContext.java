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
				_actionFileNames.add(fileName);

				_actionRootElementMap.put(fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".function")) {
				_functionFileNames.add(fileName);

				_functionRootElementMap.put(
					fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".macro")) {
				_macroFileNames.add(fileName);

				_macroRootElementMap.put(fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".path")) {
				_pathFileNames.add(fileName);

				_pathRootElementMap.put(fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".testcase")) {
				_testCaseFileNames.add(fileName);

				_testCaseRootElementMap.put(
					fileName, _getRootElement(fileName));
			}
			else if (fileName.endsWith(".testsuite")) {
				_testSuiteFileNames.add(fileName);

				_testSuiteRootElementMap.put(
					fileName, _getRootElement(fileName));
			}
			else {
				throw new IllegalArgumentException("Invalid file " + fileName);
			}
		}
	}

	public Set<String> getActionFileNames() {
		return _actionFileNames;
	}

	public Map<String, Element> getActionRootElementMap() {
		return _actionRootElementMap;
	}

	public Set<String> getFunctionFileNames() {
		return _functionFileNames;
	}

	public Map<String, Element> getFunctionRootElementMap() {
		return _functionRootElementMap;
	}

	public Set<String> getMacroFileNames() {
		return _macroFileNames;
	}

	public Map<String, Element> getMacroRootElementMap() {
		return _macroRootElementMap;
	}

	public Set<String> getPathFileNames() {
		return _pathFileNames;
	}

	public Map<String, Element> getPathRootElementMap() {
		return _pathRootElementMap;
	}

	public Set<String> getTestCaseFileNames() {
		return _testCaseFileNames;
	}

	public Map<String, Element> getTestCaseRootElementMap() {
		return _testCaseRootElementMap;
	}

	public Set<String> getTestSuiteFileNames() {
		return _testSuiteFileNames;
	}

	public Map<String, Element> getTestSuiteRootElementMap() {
		return _testSuiteRootElementMap;
	}

	private Element _getRootElement(String fileName) throws Exception {
		return _seleniumBuilderFileUtil.getRootElement(fileName);
	}

	private String _normalizeFileName(String fileName) {
		return _seleniumBuilderFileUtil.normalizeFileName(fileName);
	}

	private Set<String> _actionFileNames = new HashSet<String>();
	private Map<String, Element> _actionRootElementMap =
		new HashMap<String, Element>();
	private String _baseDir;
	private Set<String> _functionFileNames = new HashSet<String>();
	private Map<String, Element> _functionRootElementMap =
		new HashMap<String, Element>();
	private Set<String> _macroFileNames = new HashSet<String>();
	private Map<String, Element> _macroRootElementMap =
		new HashMap<String, Element>();
	private Set<String> _pathFileNames = new HashSet<String>();
	private Map<String, Element> _pathRootElementMap =
		new HashMap<String, Element>();
	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;
	private Set<String> _testCaseFileNames = new HashSet<String>();
	private Map<String, Element> _testCaseRootElementMap =
		new HashMap<String, Element>();
	private Set<String> _testSuiteFileNames = new HashSet<String>();
	private Map<String, Element> _testSuiteRootElementMap =
		new HashMap<String, Element>();

}