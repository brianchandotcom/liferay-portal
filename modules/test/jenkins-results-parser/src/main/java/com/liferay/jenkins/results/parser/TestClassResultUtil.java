/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Yoo
 */
public class TestClassResultUtil {

	public static File getProjectTestResultDir(
		String projectName, File baseDir) {

		Matcher matcher = _projectNamePattern.matcher(projectName);

		if (matcher.find()) {
			String testProjectName = matcher.group(1);

			testProjectName = testProjectName.replaceAll(":", "/");

			return new File(baseDir, "modules/" + testProjectName);
		}

		return null;
	}

	public static String getTestResultFileName(File file) {
		Matcher matcher = _testResultFileNamePattern.matcher(
			file.getAbsolutePath());

		if (matcher.find()) {
			return matcher.group(0);
		}

		return null;
	}

	public static void moveTestResultFiles(
			File testProjectResultsDir, File baseDir)
		throws IOException {

		List<File> fileList = JenkinsResultsParserUtil.getIncludedFiles(
			testProjectResultsDir, null, _INCLUDED_FILES_GLOBS);

		File testResultsDir = new File(baseDir, "modules/test-results");

		for (File resultFile : fileList) {
			String testResultFileName = getTestResultFileName(resultFile);

			File destinationFile = new File(testResultsDir, testResultFileName);

			JenkinsResultsParserUtil.move(resultFile, destinationFile);

			if (!resultFile.exists()) {
				System.out.println("Deleted result file:" + resultFile);
			}
		}
	}

	private static final String[] _INCLUDED_FILES_GLOBS = {"**/TEST-**.xml"};

	private static final Pattern _projectNamePattern = Pattern.compile(
		"(:.*)(:test|testIntegration)");
	private static final Pattern _testResultFileNamePattern = Pattern.compile(
		"TEST-.*.xml");

}