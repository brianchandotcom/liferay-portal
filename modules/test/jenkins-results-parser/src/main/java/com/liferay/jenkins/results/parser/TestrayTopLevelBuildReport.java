/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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