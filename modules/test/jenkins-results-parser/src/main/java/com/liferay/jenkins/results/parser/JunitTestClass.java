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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class JunitTestClass extends TestClass {

	public JunitTestClass(
		GitWorkingDirectory gitWorkingDirectory, File file, File srcFile) {

		super(file);

		String srcFileName = srcFile.getName();

		if (!srcFileName.endsWith(".java")) {
			throw new RuntimeException("Invalid Junit Test Class");
		}

		_gitWorkingDirectory = gitWorkingDirectory;
		_srcFile = srcFile;

		try {
			_srcFileContent = JenkinsResultsParserUtil.read(_srcFile);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		_className = _getClassName();
		_packageName = _getPackageName();

		try {
			_initTestMethods();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public File getSrcFile() {
		return _srcFile;
	}

	private String _getClassName() {
		String srcFileName = _srcFile.getName();

		return srcFileName.substring(0, srcFileName.lastIndexOf("."));
	}

	private String _getPackageName() {
		Matcher packageNameMatcher = _packageNamePattern.matcher(
			_srcFileContent);

		if (!packageNameMatcher.find()) {
			throw new RuntimeException("No package found in " + _srcFile);
		}

		return packageNameMatcher.group("packageName");
	}

	private String _getParentClassName() {
		Pattern classHeaderPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"public\\s+(abstract\\s+)?class\\s+", _className,
				"(\\<[^\\<]+\\>)?(?<classHeaderEntities>[^\\{]+)\\{"));

		Matcher classHeaderMatcher = classHeaderPattern.matcher(
			_srcFileContent);

		if (!classHeaderMatcher.find()) {
			throw new RuntimeException("No class header found in " + _srcFile);
		}

		String classHeaderEntities = classHeaderMatcher.group(
			"classHeaderEntities");

		Pattern parentClassPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"extends\\s+(?<parentClassName>[^\\s\\<]+)"));

		Matcher parentClassMatcher = parentClassPattern.matcher(
			classHeaderEntities);

		if (parentClassMatcher.find()) {
			return parentClassMatcher.group("parentClassName");
		}

		return null;
	}

	private String _getParentFullClassName() {
		String parentClassName = _getParentClassName();

		if (parentClassName == null) {
			return null;
		}

		if (parentClassName.contains(".") &&
			parentClassName.matches("[a-z].*")) {

			return parentClassName;
		}

		String parentPackageName = _getParentPackageName(parentClassName);

		if (parentPackageName == null) {
			return null;
		}

		return parentPackageName + "." + parentClassName;
	}

	private String _getParentPackageName(String parentClassName) {
		Pattern parentImportClassPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"import\\s+(?<parentPackageName>[^;]+)\\.", parentClassName,
				";"));

		Matcher parentImportClassMatcher = parentImportClassPattern.matcher(
			_srcFileContent);

		if (parentImportClassMatcher.find()) {
			String parentPackageName = parentImportClassMatcher.group(
				"parentPackageName");

			if (!parentPackageName.startsWith("com.liferay")) {
				return null;
			}

			return parentPackageName;
		}

		return _packageName;
	}

	private void _initTestMethods() throws IOException {
		Matcher methodHeaderMatcher = _methodHeaderPattern.matcher(
			_srcFileContent);

		while (methodHeaderMatcher.find()) {
			String annotations = methodHeaderMatcher.group("annotations");
			String methodName = methodHeaderMatcher.group("methodName");

			if (annotations.contains("@Test")) {
				addTestMethod(methodName);
			}
		}

		String parentFullClassName = _getParentFullClassName();

		if (parentFullClassName == null) {
			return;
		}

		JunitTestClass parentJunitTestClass =
			TestClassFactory.newJunitTestClass(
				_gitWorkingDirectory, parentFullClassName);

		for (TestMethod testMethod : parentJunitTestClass.getTestMethods()) {
			addTestMethod(testMethod);
		}
	}

	private static Pattern _methodHeaderPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\t(?<annotations>(@[\\s\\S]+?))public\\s+void\\s+",
			"(?<methodName>[^\\(\\s]+)"));
	private static Pattern _packageNamePattern = Pattern.compile(
		"package (?<packageName>[^;]+);");

	private final String _className;
	private final GitWorkingDirectory _gitWorkingDirectory;
	private final String _packageName;
	private final File _srcFile;
	private final String _srcFileContent;

}