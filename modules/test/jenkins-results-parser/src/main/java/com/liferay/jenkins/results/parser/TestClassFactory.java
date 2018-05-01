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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class TestClassFactory {

	public static JunitTestClass newJunitTestClass(
		GitWorkingDirectory gitWorkingDirectory, File file, File srcFile) {

		if (_junitTestClass.containsKey(file)) {
			return _junitTestClass.get(file);
		}

		JunitTestClass junitTestClass = new JunitTestClass(
			gitWorkingDirectory, file, srcFile);

		_junitTestClass.put(file, junitTestClass);

		return junitTestClass;
	}

	public static JunitTestClass newJunitTestClass(
		GitWorkingDirectory gitWorkingDirectory, String fullClassName) {

		String filePath = fullClassName.substring(
			0, fullClassName.lastIndexOf("."));

		filePath = filePath.replace(".", "/");

		String simpleClassName = fullClassName.substring(
			fullClassName.lastIndexOf(".") + 1);

		File file = new File(filePath, simpleClassName + ".class");

		if (_junitTestClass.containsKey(file)) {
			return _junitTestClass.get(file);
		}

		String srcFileName = simpleClassName + ".java";

		List<File> srcFiles = JenkinsResultsParserUtil.findFiles(
			gitWorkingDirectory.getWorkingDirectory(), srcFileName);

		File matchingSrcFile = null;

		for (File srcFile : srcFiles) {
			String srcFilePath = srcFile.toString();

			if (srcFilePath.contains(filePath)) {
				matchingSrcFile = srcFile;

				break;
			}
		}

		if (matchingSrcFile == null) {
			throw new RuntimeException("No matching files found.");
		}

		if (srcFiles.size() > 1) {
		}

		return newJunitTestClass(gitWorkingDirectory, file, matchingSrcFile);
	}

	private static final Map<File, JunitTestClass> _junitTestClass =
		new HashMap<>();

}