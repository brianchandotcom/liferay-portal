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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashSet;
import java.util.Set;

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

		for (String fileName : fileNames) {
			fileName = _normalizeFileName(fileName);

			if (fileName.endsWith(".action")) {
				_actionFileNames.add(fileName);
			}
			else if (fileName.endsWith(".function")) {
				_functionFileNames.add(fileName);
			}
			else if (fileName.endsWith(".macro")) {
				_macroFileNames.add(fileName);
			}
			else if (fileName.endsWith(".path")) {
				_pathFileNames.add(fileName);
			}
			else if (fileName.endsWith(".testcase")) {
				_testCaseFileNames.add(fileName);
			}
			else if (fileName.endsWith(".testsuite")) {
				_testSuiteFileNames.add(fileName);
			}
			else {
				throw new IllegalArgumentException("Invalid file " + fileName);
			}
		}
	}

	public Set<String> getActionFileNames() {
		return _actionFileNames;
	}

	public String getBaseDir() {
		return _baseDir;
	}

	public Set<String> getFunctionFileNames() {
		return _functionFileNames;
	}

	public Set<String> getMacroFileNames() {
		return _macroFileNames;
	}

	public Set<String> getPathFileNames() {
		return _pathFileNames;
	}

	public Set<String> getTestCaseFileNames() {
		return _testCaseFileNames;
	}

	public Set<String> getTestSuiteFileNames() {
		return _testSuiteFileNames;
	}

	private String _normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	private Set<String> _actionFileNames = new HashSet<String>();
	private String _baseDir;
	private Set<String> _functionFileNames = new HashSet<String>();
	private Set<String> _macroFileNames = new HashSet<String>();
	private Set<String> _pathFileNames = new HashSet<String>();
	private Set<String> _testCaseFileNames = new HashSet<String>();
	private Set<String> _testSuiteFileNames = new HashSet<String>();

}