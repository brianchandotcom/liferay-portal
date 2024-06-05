/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.testray.TestrayS3Object;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class URLTopLevelBuildReport extends BaseTopLevelBuildReport {

	@Override
	public JSONObject getBuildReportJSONObject() {
		if (buildReportJSONObject != null) {
			return buildReportJSONObject;
		}

		TestrayS3Object buildReportTestrayS3Object =
			getBuildReportTestrayS3Object();

		if (buildReportTestrayS3Object != null) {
			buildReportJSONObject = new JSONObject(
				buildReportTestrayS3Object.getValue());
		}

		if (buildReportJSONObject == null) {
			buildReportJSONObject = getJSONObjectFromURL(
				getBuildReportJSONUserContentURL());
		}

		if (buildReportJSONObject == null) {
			buildReportJSONObject = getJSONObjectFromURL(
				getBuildReportJSONTestrayURL());
		}

		return buildReportJSONObject;
	}

	protected URLTopLevelBuildReport(
		JSONObject buildJSONObject, JobReport jobReport) {

		super(buildJSONObject, jobReport);
	}

	protected URLTopLevelBuildReport(URL buildURL) {
		super(buildURL);
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
					"https://storage.cloud.google.com/testray-results/",
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

	protected JSONObject getJSONObjectFromURL(URL url) {
		if (!JenkinsResultsParserUtil.exists(url)) {
			return null;
		}

		String urlString = String.valueOf(url);

		if (!urlString.endsWith(".gz")) {
			try {
				return JenkinsResultsParserUtil.toJSONObject(urlString);
			}
			catch (IOException ioException) {
				return null;
			}
		}

		File file = new File(
			System.getenv("WORKSPACE"),
			JenkinsResultsParserUtil.getDistinctTimeStamp() + ".gz");

		try {
			JenkinsResultsParserUtil.toFile(url, file);

			String fileContent = JenkinsResultsParserUtil.read(file);

			if (JenkinsResultsParserUtil.isNullOrEmpty(fileContent)) {
				return null;
			}

			return new JSONObject(fileContent);
		}
		catch (Exception exception) {
			return null;
		}
		finally {
			if (file.exists()) {
				JenkinsResultsParserUtil.delete(file);
			}
		}
	}

	private File _jenkinsConsoleLocalFile;

}