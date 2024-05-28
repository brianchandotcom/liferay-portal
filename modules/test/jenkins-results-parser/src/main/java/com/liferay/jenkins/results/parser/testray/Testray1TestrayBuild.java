/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class Testray1TestrayBuild extends TestrayBuild {

	@Override
	public String getDescription() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("description");
	}

	@Override
	public long getID() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getLong("testrayBuildId");
	}

	@Override
	public String getName() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("name");
	}

	@Override
	public JSONObject getRunsJSONObject() {
		if (_runsJSONObject != null) {
			return _runsJSONObject;
		}

		TestrayServer testrayServer = getTestrayServer();

		try {
			_runsJSONObject = new JSONObject(
				testrayServer.requestGet(
					JenkinsResultsParserUtil.combine(
						"/home/-/testray/runs.json?delta=200&testrayBuildId=",
						String.valueOf(getID()))));
		}
		catch (IOException ioException) {
		}

		return _runsJSONObject;
	}

	@Override
	public String getStartYearMonth() {
		Matcher matcher = _getTestrayAttachmentURLMatcher();

		if (matcher == null) {
			return null;
		}

		return matcher.group("startYearMonth");
	}

	@Override
	public List<TestrayCaseResult> getTestrayCaseResults() {
		return getTestrayCaseResults(null, null);
	}

	@Override
	public List<TestrayCaseResult> getTestrayCaseResults(
		TestrayCaseType testrayCaseType, TestrayRun testrayRun) {

		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		TestrayServer testrayServer = getTestrayServer();

		StringBuilder sb = new StringBuilder();

		sb.append("/home/-/testray/case_results.json?delta=");
		sb.append(_PAGE_DELTA);
		sb.append("&orderByCol=status_sortable");
		sb.append("&orderByType=asc");
		sb.append("&resetCur=false");
		sb.append("&testrayBuildId=");
		sb.append(getID());

		if (testrayCaseType != null) {
			sb.append("&testrayCaseTypeId=");
			sb.append(testrayCaseType.getID());
		}

		if (testrayRun != null) {
			sb.append("&testrayRunId=");
			sb.append(testrayRun.getID());
		}

		long previousTestrayCaseResultID = -1;

		for (int page = 1; page < _PAGE_COUNT; page++) {
			try {
				String testrayCaseResultsURLPath = sb + "&cur=" + page;

				System.out.println(
					testrayServer.getURL() + testrayCaseResultsURLPath);

				JSONObject jsonObject = new JSONObject(
					testrayServer.requestGet(testrayCaseResultsURLPath));

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.isEmpty()) {
					break;
				}

				JSONObject firstDataJSONObject = dataJSONArray.getJSONObject(0);

				if (Objects.equals(
						firstDataJSONObject.optLong("testrayCaseResultId"),
						previousTestrayCaseResultID)) {

					break;
				}

				previousTestrayCaseResultID = firstDataJSONObject.getLong(
					"testrayCaseResultId");

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayCaseResult testrayCaseResult =
						TestrayFactory.newTestrayCaseResult(
							this, dataJSONObject);

					testrayCaseResults.add(testrayCaseResult);
				}

				if (dataJSONArray.length() < _PAGE_DELTA) {
					break;
				}
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		return testrayCaseResults;
	}

	@Override
	public TestrayProductVersion getTestrayProductVersion() {
		return _testrayProductVersion;
	}

	@Override
	public synchronized List<TestrayRun> getTestrayRuns() {
		if (_testrayRuns != null) {
			return _testrayRuns;
		}

		_testrayRuns = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		sb.append("/home/-/testray/runs.json?testrayBuildId=");
		sb.append(getID());

		TestrayServer testrayServer = getTestrayServer();

		try {
			JSONObject responseJSONObject = new JSONObject(
				testrayServer.requestGet(sb.toString()));

			JSONArray testrayRunJSONArray = responseJSONObject.getJSONArray(
				"data");

			for (int i = 0; i < testrayRunJSONArray.length(); i++) {
				JSONObject testrayRunJSONObject =
					testrayRunJSONArray.getJSONObject(i);

				_testrayRuns.add(
					TestrayFactory.newTestrayRun(this, testrayRunJSONObject));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _testrayRuns;
	}

	@Override
	public TopLevelBuildReport getTopLevelBuildReport() {
		if (_topLevelBuildReport != null) {
			return _topLevelBuildReport;
		}

		URL topLevelBuildReportURL = getTopLevelBuildReportURL();

		if (topLevelBuildReportURL == null) {
			return null;
		}

		_topLevelBuildReport = BuildReportFactory.newTopLevelBuildReport(this);

		return _topLevelBuildReport;
	}

	@Override
	public URL getTopLevelBuildReportURL() {
		Matcher matcher = _getTestrayAttachmentURLMatcher();

		if (matcher == null) {
			return null;
		}

		try {
			URL url = new URL(
				JenkinsResultsParserUtil.combine(
					"http://", matcher.group("topLevelMasterHostname"),
					"/userContent/jobs/", matcher.group("topLevelJobName"),
					"/builds/", matcher.group("topLevelBuildNumber"),
					"/build-report.json.gz"));

			if (JenkinsResultsParserUtil.exists(url)) {
				return url;
			}

			url = new URL(matcher.group());

			if (JenkinsResultsParserUtil.exists(url)) {
				return url;
			}

			return null;
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	@Override
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

	@Override
	public TestrayCaseResult getTopLevelTestrayCaseResult() {
		TestrayServer testrayServer = getTestrayServer();

		List<TestrayCaseResult> testrayCaseResults = getTestrayCaseResults(
			testrayServer.getTestrayCaseType("Batch"), null);

		for (TestrayCaseResult testrayCaseResult : testrayCaseResults) {
			if (!Objects.equals(
					testrayCaseResult.getName(), "Top Level Build")) {

				continue;
			}

			return testrayCaseResult;
		}

		return null;
	}

	@Override
	public URL getURL() {
		JSONObject jsonObject = getJSONObject();

		try {
			return new URL(jsonObject.getString("htmlURL"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	protected Testray1TestrayBuild(
		TestrayRoutine testrayRoutine, JSONObject jsonObject) {

		super(testrayRoutine, jsonObject);

		TestrayProject testrayProject = getTestrayProject();

		_testrayProductVersion = testrayProject.getTestrayProductVersionByID(
			jsonObject.getLong("testrayProductVersionId"));
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

	private static final int _PAGE_COUNT = 100;

	private static final int _PAGE_DELTA = 200;

	private static final Pattern _testrayAttachmentURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://testray.liferay.com/reports/production/logs/",
			"(?<startYearMonth>\\d{4}-\\d{2})/",
			"(?<topLevelMasterHostname>test-\\d+-\\d+)/",
			"(?<topLevelJobName>[^/]+)/(?<topLevelBuildNumber>\\d+)/.*"));

	private JSONObject _runsJSONObject;
	private Matcher _testrayAttachmentURLMatcher;
	private final TestrayProductVersion _testrayProductVersion;
	private List<TestrayRun> _testrayRuns;
	private TopLevelBuildReport _topLevelBuildReport;

}