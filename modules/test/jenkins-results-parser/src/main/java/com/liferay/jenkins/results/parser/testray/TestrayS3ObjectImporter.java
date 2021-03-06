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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.BuildDatabase;
import com.liferay.jenkins.results.parser.BuildDatabaseUtil;
import com.liferay.jenkins.results.parser.JenkinsConsoleTextLoader;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class TestrayS3ObjectImporter {

	public TestrayS3ObjectImporter(Build build) {
		if (build == null) {
			throw new RuntimeException("Please set a build");
		}

		_build = build;

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase(
			_build);

		_startProperties = buildDatabase.getProperties(
			_build.getJobVariant() + "/start.properties");
	}

	public void record() {
		JenkinsResultsParserUtil.delete(_getTestrayLogsDir());

		_recordJenkinsConsole();
	}

	public void upload() {
		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		testrayS3Bucket.createTestrayS3Objects(_getTestrayLogsDir());
	}

	private File _convertToGzipFile(File file) {
		File gzipFile = new File(file.getParent(), file.getName() + ".gz");

		JenkinsResultsParserUtil.gzip(file, gzipFile);

		JenkinsResultsParserUtil.delete(file);

		return gzipFile;
	}

	private File _getTestrayLogsBuildDir() {
		StringBuilder sb = new StringBuilder();

		Date topLevelStartDate = new Date(
			Long.parseLong(
				_startProperties.getProperty("TOP_LEVEL_START_TIME")));

		sb.append(
			JenkinsResultsParserUtil.toDateString(
				topLevelStartDate, "yyyy-MM", "America/Los_Angeles"));

		sb.append("/");
		sb.append(_startProperties.getProperty("TOP_LEVEL_MASTER_HOSTNAME"));
		sb.append("/");
		sb.append(_startProperties.getProperty("TOP_LEVEL_JOB_NAME"));
		sb.append("/");
		sb.append(_startProperties.getProperty("TOP_LEVEL_BUILD_NUMBER"));
		sb.append("/");
		sb.append(_build.getJobVariant());
		sb.append("/");

		sb.append(
			JenkinsResultsParserUtil.getAxisVariable(_build.getBuildURL()));

		return new File(_getTestrayLogsDir(), sb.toString());
	}

	private File _getTestrayLogsDir() {
		String workspace = System.getenv("WORKSPACE");

		if (JenkinsResultsParserUtil.isNullOrEmpty(workspace)) {
			throw new RuntimeException("Please set WORKSPACE");
		}

		return new File(workspace, "testray/logs");
	}

	private void _recordJenkinsConsole() {
		JenkinsConsoleTextLoader jenkinsConsoleTextLoader =
			new JenkinsConsoleTextLoader(_build.getBuildURL());

		File jenkinsConsoleFile = new File(
			_getTestrayLogsBuildDir(), "jenkins-console.txt");

		try {
			JenkinsResultsParserUtil.write(
				jenkinsConsoleFile, jenkinsConsoleTextLoader.getConsoleText());

			_convertToGzipFile(jenkinsConsoleFile);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final Build _build;
	private final Properties _startProperties;

}