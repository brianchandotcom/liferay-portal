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

import com.liferay.jenkins.results.parser.testray.TestrayBuild;

import java.io.File;
import java.io.IOException;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class TestrayTopLevelBuildReport extends URLTopLevelBuildReport {

	protected TestrayTopLevelBuildReport(TestrayBuild testrayBuild) {
		super(testrayBuild.getTopLevelBuildURL());

		_startYearMonth = testrayBuild.getStartYearMonth();
	}

	@Override
	protected File getJenkinsConsoleLocalFile() {
		if (_jenkinsConsoleLocalFile != null) {
			return _jenkinsConsoleLocalFile;
		}

		JobReport jobReport = getJobReport();

		JenkinsMaster jenkinsMaster = jobReport.getJenkinsMaster();

		try {
			URL jenkinsConsoleURL = new URL(
				JenkinsResultsParserUtil.combine(
					"https://testray.liferay.com/reports/production/logs/",
					getStartYearMonth(), "/", jenkinsMaster.getName(), "/",
					jobReport.getJobName(), "/",
					String.valueOf(getBuildNumber()),
					"/jenkins-console.txt.gz"));

			File jenkinsConsoleLocalGzipFile = new File(
				System.getenv("WORKSPACE"),
				JenkinsResultsParserUtil.getDistinctTimeStamp() + ".gz");

			JenkinsResultsParserUtil.toFile(
				jenkinsConsoleURL, jenkinsConsoleLocalGzipFile);

			File jenkinsConsoleLocalFile = new File(
				System.getenv("WORKSPACE"),
				JenkinsResultsParserUtil.getDistinctTimeStamp());

			JenkinsResultsParserUtil.unGzip(
				jenkinsConsoleLocalGzipFile, jenkinsConsoleLocalFile);

			_jenkinsConsoleLocalFile = jenkinsConsoleLocalFile;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _jenkinsConsoleLocalFile;
	}

	@Override
	protected String getStartYearMonth() {
		return _startYearMonth;
	}

	private File _jenkinsConsoleLocalFile;
	private final String _startYearMonth;

}