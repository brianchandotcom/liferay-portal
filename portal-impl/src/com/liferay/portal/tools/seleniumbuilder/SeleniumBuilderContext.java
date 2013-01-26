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

import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilderContext {

	public SeleniumBuilderContext(String basedir) throws Exception {
		_basedir = basedir;

		_fileNameSet = _initFileNameSet();

		_fileNameSetAction = _initFileNameSetAction();
		_fileNameSetFunction = _initFileNameSetFunction();
		_fileNameSetMacro = _initFileNameSetMacro();
		_fileNameSetPath = _initFileNameSetPath();
		_fileNameSetTestCase = _initFileNameSetTestCase();
		_fileNameSetTestSuite = _initFileNameSetTestSuite();
	}

	public String getBasedir() {
		return _basedir;
	}

	public Set<String> getFileNameSet() {
		return _fileNameSet;
	}

	public Set<String> getFileNameSetAction() {
		return _fileNameSetAction;
	}

	public Set<String> getFileNameSetFunction() {
		return _fileNameSetFunction;
	}

	public Set<String> getFileNameSetMacro() {
		return _fileNameSetMacro;
	}

	public Set<String> getFileNameSetPath() {
		return _fileNameSetPath;
	}

	public Set<String> getFileNameSetTestCase() {
		return _fileNameSetTestCase;
	}

	public Set<String> getFileNameSetTestSuite() {
		return _fileNameSetTestSuite;
	}

	private Set<String> _initFileNameSet() throws Exception {
		Set<String> treeSet = new TreeSet<String>();

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_basedir);
		directoryScanner.setIncludes(
			new String[] {
				"**\\portalweb\\**\\*.action", "**\\portalweb\\**\\*.function",
				"**\\portalweb\\**\\*.macro", "**\\portalweb\\**\\*.path",
				"**\\portalweb\\**\\*.testcase",
				"**\\portalweb\\**\\*.testsuite"
			});

		directoryScanner.scan();

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = _normalizeFileName(fileName);

			treeSet.add(fileName);
		}

		return treeSet;
	}

	private Set<String> _initFileNameSetAction() {
		return _initFileNameSetType(".action");
	}

	private Set<String> _initFileNameSetFunction() {
		return _initFileNameSetType(".function");
	}

	private Set<String> _initFileNameSetMacro() {
		return _initFileNameSetType(".macro");
	}

	private Set<String> _initFileNameSetPath() {
		return _initFileNameSetType(".path");
	}

	private Set<String> _initFileNameSetTestCase() {
		return _initFileNameSetType(".testcase");
	}

	private Set<String> _initFileNameSetTestSuite() {
		return _initFileNameSetType(".testsuite");
	}

	private Set<String> _initFileNameSetType(String suffix) {
		Set<String> treeSet = new TreeSet<String>();

		for (String fileName : _fileNameSet) {
			if (fileName.endsWith(suffix)) {
				treeSet.add(fileName);
			}
		}

		return treeSet;
	}

	private String _normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	private String _basedir;
	private Set<String> _fileNameSet;
	private Set<String> _fileNameSetAction;
	private Set<String> _fileNameSetFunction;
	private Set<String> _fileNameSetMacro;
	private Set<String> _fileNameSetPath;
	private Set<String> _fileNameSetTestCase;
	private Set<String> _fileNameSetTestSuite;

}