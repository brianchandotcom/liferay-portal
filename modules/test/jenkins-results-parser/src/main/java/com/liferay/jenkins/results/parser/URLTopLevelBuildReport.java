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

		buildReportJSONObject = _getJSONObjectFromURL(
			getBuildReportJSONUserContentURL());

		if (buildReportJSONObject == null) {
			buildReportJSONObject = _getJSONObjectFromURL(
				getBuildReportJSONTestrayURL());
		}

		if (buildReportJSONObject == null) {
			TestrayS3Object buildReportTestrayS3Object =
				getBuildReportTestrayS3Object();

			if (buildReportTestrayS3Object != null) {
				buildReportJSONObject = new JSONObject(
					buildReportTestrayS3Object.getValue());
			}
		}

		if (buildReportJSONObject == null) {
			buildReportJSONObject = getBuildReportJSONObject(
				_getJSONObjectFromURL(getBuildResultJSONTestrayURL()));
		}

		if (buildReportJSONObject == null) {
			TestrayS3Object buildResultTestrayS3Object =
				getBuildResultTestrayS3Object();

			if (buildResultTestrayS3Object != null) {
				buildReportJSONObject = getBuildReportJSONObject(
					new JSONObject(buildResultTestrayS3Object.getValue()));
			}
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

	private JSONObject _getJSONObjectFromURL(URL url) {
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