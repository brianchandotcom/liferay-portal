/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.testray.TestrayS3Object;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
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

		buildReportJSONObject = getJSONObjectFromURL(
			getBuildReportJSONUserContentURL());

		if (buildReportJSONObject == null) {
			buildReportJSONObject = getJSONObjectFromURL(
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
				getJSONObjectFromURL(getBuildResultJSONTestrayURL()));
		}

		if (buildReportJSONObject == null) {
			TestrayS3Object buildResultTestrayS3Object =
				getBuildResultTestrayS3Object();

			if (buildResultTestrayS3Object != null) {
				buildReportJSONObject = getBuildReportJSONObject(
					new JSONObject(buildResultTestrayS3Object.getValue()));
			}
		}

		_addTestResultsFromBuildResults();

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

	private void _addTestResultsFromBuildResults() {
		JSONObject buildReportJSONObject = getBuildReportJSONObject();

		if (buildReportJSONObject == null) {
			return;
		}

		Map<String, List<JSONObject>> testResultsJSONObjectsMap =
			_getTestResultsJSONObjectsMap();

		if (testResultsJSONObjectsMap.isEmpty()) {
			return;
		}

		JSONArray batchesJSONArray = buildReportJSONObject.getJSONArray(
			"batches");

		for (int i = 0; i < batchesJSONArray.length(); i++) {
			JSONObject batchJSONObject = batchesJSONArray.getJSONObject(i);

			List<JSONObject> testResultsJSONObjects =
				testResultsJSONObjectsMap.get(
					batchJSONObject.getString("batchName"));

			JSONArray buildsJSONArray = batchJSONObject.getJSONArray("builds");

			for (int j = 0; j < buildsJSONArray.length(); j++) {
				JSONObject buildJSONObject = buildsJSONArray.getJSONObject(j);

				for (JSONObject testResultJSONObject : testResultsJSONObjects) {
					if (!Objects.equals(
							buildJSONObject.getString("buildURL"),
							testResultJSONObject.getString("buildURL"))) {

						continue;
					}

					JSONArray testResultsJSONArray =
						buildJSONObject.getJSONArray("testResults");

					if (testResultsJSONArray == null) {
						testResultsJSONArray = new JSONArray();
					}

					JSONObject jsonObject = new JSONObject();

					jsonObject.put(
						"duration", testResultJSONObject.get("duration")
					).put(
						"errorDetails", testResultJSONObject.opt("errorDetails")
					).put(
						"name", testResultJSONObject.get("name")
					).put(
						"status", testResultJSONObject.get("status")
					);

					testResultsJSONArray.put(jsonObject);
				}
			}
		}
	}

	private Map<String, List<JSONObject>> _getTestResultsJSONObjectsMap() {
		Map<String, List<JSONObject>> testResultsJSONObjectsMap =
			new HashMap<>();

		JSONObject buildResultJSONObject = getJSONObjectFromURL(
			getBuildResultJSONUserContentURL());

		if (buildResultJSONObject == null) {
			return testResultsJSONObjectsMap;
		}

		JSONArray batchResultsJSONArray = buildResultJSONObject.getJSONArray(
			"batchResults");

		for (int i = 0; i < batchResultsJSONArray.length(); i++) {
			JSONObject batchResultJSONObject =
				batchResultsJSONArray.getJSONObject(i);

			String batchName = batchResultJSONObject.getString("jobVariant");

			batchName = batchName.replaceAll("([^/]+)/.*", "$1");

			List<JSONObject> testResultsJSONObjects =
				testResultsJSONObjectsMap.get(batchName);

			if (testResultsJSONObjects == null) {
				testResultsJSONObjects = new ArrayList<>();
			}

			JSONArray testResultsJSONArray = batchResultJSONObject.getJSONArray(
				"testResults");

			for (int j = 0; j < testResultsJSONArray.length(); j++) {
				testResultsJSONObjects.add(
					testResultsJSONArray.getJSONObject(j));
			}

			testResultsJSONObjectsMap.put(batchName, testResultsJSONObjects);
		}

		return testResultsJSONObjectsMap;
	}

	private File _jenkinsConsoleLocalFile;

}