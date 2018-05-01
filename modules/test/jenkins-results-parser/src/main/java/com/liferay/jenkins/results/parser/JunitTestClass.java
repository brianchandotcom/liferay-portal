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

import java.util.ArrayList;
import java.util.List;
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
			_initTestMethods();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public File getSrcFile() {
		return _srcFile;
	}

	private void _initTestMethods() throws IOException {
		String content = JenkinsResultsParserUtil.read(_srcFile);

		Matcher matcher = _pattern.matcher(content);

		while (matcher.find()) {
			String annotations = matcher.group("annotations");
			String methodName = matcher.group("methodName");

			if (annotations.contains("@Test")) {
				addTestMethod(methodName);
			}
		}
	}

	private static Pattern _pattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\t(?<annotations>(@[\\s\\S]+?))public\\s+void\\s+",
			"(?<methodName>[^\\(\\s]+)"));

	private final GitWorkingDirectory _gitWorkingDirectory;
	private final File _srcFile;

}