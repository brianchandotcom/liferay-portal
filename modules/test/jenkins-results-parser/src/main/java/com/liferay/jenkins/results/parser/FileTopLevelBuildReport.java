/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class FileTopLevelBuildReport extends BaseTopLevelBuildReport {

	public static FileTopLevelBuildReport getInstance(File jenkinsConsoleFile) {
		if ((jenkinsConsoleFile == null) || !jenkinsConsoleFile.exists()) {
			return null;
		}

		return new FileTopLevelBuildReport(jenkinsConsoleFile);
	}

	@Override
	public JSONObject getBuildReportJSONObject() {
		if (buildReportJSONObject != null) {
			return buildReportJSONObject;
		}

		File parentFile = _jenkinsConsoleFile.getParentFile();

		buildReportJSONObject = _getJSONObjectFromFile(
			new File(parentFile, "build-report.json.gz"));

		if (buildReportJSONObject == null) {
			buildReportJSONObject = getBuildReportJSONObject(
				_getJSONObjectFromFile(
					new File(parentFile, "build-result.json.gz")));
		}

		return buildReportJSONObject;
	}

	protected FileTopLevelBuildReport(File jenkinsConsoleFile) {
		super(_getBuildURL(jenkinsConsoleFile));

		if (!jenkinsConsoleFile.exists()) {
			throw new RuntimeException("Invalid file " + jenkinsConsoleFile);
		}

		_jenkinsConsoleFile = jenkinsConsoleFile;
	}

	@Override
	protected File getJenkinsConsoleLocalFile() {
		if (_jenkinsConsoleLocalFile != null) {
			return _jenkinsConsoleLocalFile;
		}

		if ((_jenkinsConsoleFile == null) || !_jenkinsConsoleFile.exists()) {
			return null;
		}

		String timeStamp = JenkinsResultsParserUtil.getDistinctTimeStamp();

		File jenkinsConsoleLocalFile = new File(
			System.getenv("WORKSPACE"), timeStamp);
		File jenkinsConsoleLocalGzipFile = new File(
			System.getenv("WORKSPACE"), timeStamp + ".gz");

		try {
			JenkinsResultsParserUtil.copy(
				_jenkinsConsoleFile, jenkinsConsoleLocalGzipFile);

			JenkinsResultsParserUtil.unGzip(
				jenkinsConsoleLocalGzipFile, jenkinsConsoleLocalFile);

			_jenkinsConsoleLocalFile = jenkinsConsoleLocalFile;

			return _jenkinsConsoleLocalFile;
		}
		catch (Exception exception) {
			return null;
		}
		finally {
			if (jenkinsConsoleLocalGzipFile.exists()) {
				JenkinsResultsParserUtil.delete(jenkinsConsoleLocalGzipFile);
			}
		}
	}

	@Override
	protected String getStartYearMonth() {
		Matcher matcher = _jenkinsConsoleFilePattern.matcher(
			JenkinsResultsParserUtil.getCanonicalPath(_jenkinsConsoleFile));

		if (!matcher.find()) {
			throw new RuntimeException("Invalid file " + _jenkinsConsoleFile);
		}

		return matcher.group("startYearMonth");
	}

	private static URL _getBuildURL(File jenkinsConsoleFile) {
		if (!jenkinsConsoleFile.exists()) {
			throw new RuntimeException("Invalid file " + jenkinsConsoleFile);
		}

		Matcher matcher = _jenkinsConsoleFilePattern.matcher(
			JenkinsResultsParserUtil.getCanonicalPath(jenkinsConsoleFile));

		if (!matcher.find()) {
			throw new RuntimeException("Invalid file " + jenkinsConsoleFile);
		}

		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					"https://", matcher.group("masterHostname"),
					".liferay.com/job/", matcher.group("jobName"), "/",
					matcher.group("buildNumber")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private JSONObject _getJSONObjectFromFile(File file) {
		if ((file == null) || !file.exists()) {
			return null;
		}

		try {
			String fileContent = JenkinsResultsParserUtil.read(file);

			if (JenkinsResultsParserUtil.isNullOrEmpty(fileContent)) {
				return null;
			}

			return new JSONObject(fileContent);
		}
		catch (Exception exception) {
			return null;
		}
	}

	private static final Pattern _jenkinsConsoleFilePattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"/mnt/mfs-hdd1-10.0.10/jenkins/testray-results/production/logs/",
			"(?<startYearMonth>\\d{4}-\\d{2})/",
			"(?<masterHostname>test-\\d+-\\d+)/(?<jobName>[^/]+)/",
			"(?<buildNumber>\\d+)/jenkins-console.txt.gz"));

	private final File _jenkinsConsoleFile;
	private File _jenkinsConsoleLocalFile;

}