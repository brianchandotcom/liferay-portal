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

import com.liferay.jenkins.results.parser.BuildReportFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuildReport;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayBuild implements Comparable<TestrayBuild> {

	public TestrayBuild(TestrayRoutine testrayRoutine, JSONObject jsonObject) {
		_testrayRoutine = testrayRoutine;
		_jsonObject = jsonObject;

		_testrayProject = _testrayRoutine.getTestrayProject();
		_testrayServer = _testrayRoutine.getTestrayServer();

		_testrayProductVersion = _testrayProject.getTestrayProductVersionByID(
			_jsonObject.getInt("testrayProductVersionId"));
	}

	@Override
	public int compareTo(TestrayBuild testrayBuild) {
		if (testrayBuild == null) {
			throw new NullPointerException("Testray build is null");
		}

		Integer id = testrayBuild.getID();

		return id.compareTo(getID());
	}

	public String getDescription() {
		return _jsonObject.getString("description");
	}

	public int getID() {
		return _jsonObject.getInt("testrayBuildId");
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public String getPortalSHA() {
		Matcher matcher = _portalSHAPattern.matcher(
			_jsonObject.optString("description"));

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("portalSHA");
	}

	public String getStartYearMonth() {
		Matcher matcher = _getTestrayAttachmentURLMatcher();

		if (matcher == null) {
			return null;
		}

		return matcher.group("startYearMonth");
	}

	public List<TestrayCaseResult> getTestrayCaseResults() {
		return getTestrayCaseResults(null);
	}

	public List<TestrayCaseResult> getTestrayCaseResults(String nameFilter) {
		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		String urlString = String.valueOf(getURL());

		String caseResultsAPIURLString = urlString.replace(
			"runs", "case_results.json");

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				caseResultsAPIURLString, _testrayServer.getHTTPAuthorization());

			JSONArray dataJSONArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < dataJSONArray.length(); i++) {
				JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

				TestrayCaseResult testrayCaseResult = new TestrayCaseResult(
					this, dataJSONObject);

				testrayCaseResults.add(testrayCaseResult);
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}

		return testrayCaseResults;
	}

	public TestrayProductVersion getTestrayProductVersion() {
		return _testrayProductVersion;
	}

	public TestrayProject getTestrayProject() {
		return _testrayProject;
	}

	public TestrayRoutine getTestrayRoutine() {
		return _testrayRoutine;
	}

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

	public TopLevelBuildReport getTopLevelBuildReport() {
		if (_topLevelBuildReport != null) {
			return _topLevelBuildReport;
		}

		URL topLevelBuildReportURL = getTopLevelBuildReportURL();

		if ((topLevelBuildReportURL == null) ||
			!JenkinsResultsParserUtil.exists(topLevelBuildReportURL)) {

			return null;
		}

		_topLevelBuildReport = BuildReportFactory.newTopLevelBuildReport(this);

		return _topLevelBuildReport;
	}

	public URL getTopLevelBuildReportURL() {
		Matcher matcher = _getTestrayAttachmentURLMatcher();

		if (matcher == null) {
			return null;
		}

		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					"https://", matcher.group("topLevelMasterHostname"),
					".liferay.com/userContent/jobs/",
					matcher.group("topLevelJobName"), "/builds/",
					matcher.group("topLevelBuildNumber"),
					"/build-report.json.gz"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public URL getTopLevelBuildURL() {
		Matcher matcher = _getTestrayAttachmentURLMatcher();

		if (matcher == null) {
			return null;
		}

		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					"https://", matcher.group("topLevelMasterHostname"),
					".liferay.com/job/", matcher.group("topLevelJobName"), "/",
					matcher.group("topLevelBuildNumber"), "/"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public TestrayCaseResult getTopLevelTestrayCaseResult() {
		List<TestrayCaseResult> testrayCaseResults = getTestrayCaseResults(
			"Top");

		for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
			if (!Objects.equals(
					testrayCaseResult.getName(), "Top Level Build")) {

				continue;
			}

			return testrayCaseResult;
		}

		return null;
	}

	public URL getURL() {
		try {
			return new URL(_jsonObject.getString("htmlURL"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	protected TestrayBuild(URL testrayBuildURL) {
		Matcher matcher = _testrayBuildURLPattern.matcher(
			testrayBuildURL.toString());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid Build URL " + testrayBuildURL);
		}

		String serverURL = matcher.group("serverURL");

		_testrayServer = TestrayFactory.newTestrayServer(
			matcher.group("serverURL"));

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					serverURL, "/home/-/testray/builds/",
					matcher.group("buildID"), ".json"),
				_testrayServer.getHTTPAuthorization());

			_jsonObject = jsonObject.getJSONObject("data");

			_testrayRoutine = TestrayFactory.newTestrayRoutine(
				JenkinsResultsParserUtil.combine(
					String.valueOf(_testrayServer.getURL()),
					"/home/-/testray/builds?testrayRoutineId=",
					_jsonObject.getString("testrayRoutineId")));

			_testrayProject = _testrayRoutine.getTestrayProject();

			_testrayProductVersion =
				_testrayProject.getTestrayProductVersionByID(
					_jsonObject.getInt("testrayProductVersionId"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private Matcher _getTestrayAttachmentURLMatcher() {
		if (_testrayAttachmentURLMatcher != null) {
			return _testrayAttachmentURLMatcher;
		}

		TestrayCaseResult topLevelTestrayCaseResult =
			getTopLevelTestrayCaseResult();

		if (topLevelTestrayCaseResult != null) {
			for (TestrayAttachment testrayAttachment :
					topLevelTestrayCaseResult.getTestrayAttachments()) {

				Matcher testrayAttachmentURLMatcher =
					_testrayAttachmentURLPattern.matcher(
						String.valueOf(testrayAttachment.getURL()));

				if (testrayAttachmentURLMatcher.find()) {
					_testrayAttachmentURLMatcher = testrayAttachmentURLMatcher;

					return _testrayAttachmentURLMatcher;
				}
			}
		}

		List<TestrayCaseResult> testrayCaseResults = getTestrayCaseResults();

		if (testrayCaseResults.isEmpty()) {
			return null;
		}

		int count = 0;

		for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
			count++;

			if (count >= 5) {
				break;
			}

			for (TestrayAttachment testrayAttachment :
					testrayCaseResult.getTestrayAttachments()) {

				Matcher testrayAttachmentURLMatcher =
					_testrayAttachmentURLPattern.matcher(
						String.valueOf(testrayAttachment.getURL()));

				if (testrayAttachmentURLMatcher.find()) {
					_testrayAttachmentURLMatcher = testrayAttachmentURLMatcher;

					return _testrayAttachmentURLMatcher;
				}
			}
		}

		return null;
	}

	private static final Pattern _portalSHAPattern = Pattern.compile(
		"Portal SHA: (?<portalSHA>[^;]+);");
	private static final Pattern _testrayAttachmentURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://testray.liferay.com/reports/production/logs/",
			"(?<startYearMonth>\\d{4}-\\d{2})/",
			"(?<topLevelMasterHostname>test-\\d+-\\d+)/",
			"(?<topLevelJobName>[^/]+)/(?<topLevelBuildNumber>\\d+)/.*"));
	private static final Pattern _testrayBuildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"(?<serverURL>https://[^/]+)/home/-/testray/runs\\?",
			"testrayBuildId=(?<buildID>\\d+)"));

	private final JSONObject _jsonObject;
	private Matcher _testrayAttachmentURLMatcher;
	private final TestrayProductVersion _testrayProductVersion;
	private final TestrayProject _testrayProject;
	private final TestrayRoutine _testrayRoutine;
	private final TestrayServer _testrayServer;
	private TopLevelBuildReport _topLevelBuildReport;

}