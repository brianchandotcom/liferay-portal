/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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
public class TestrayBuild implements Comparable<TestrayBuild> {

	public static final String[] FIELD_NAMES = {
		"dateCreated", "dateModified", "description", "dueDate",
		"githubCompareURLs", "gitHash", "id", "name", "productVersionToBuilds"
	};

	public int compareTo(TestrayBuild testrayBuild) {
		if (testrayBuild == null) {
			throw new NullPointerException("Testray build is null");
		}

		Long id = testrayBuild.getID();

		return id.compareTo(getID());
	}

	public String getDescription() {
		return _jsonObject.optString("description");
	}

	public long getID() {
		return _jsonObject.getLong("id");
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public String getPortalBranch() {
		Matcher matcher = _portalBranchPattern.matcher(getDescription());

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("portalBranch");
	}

	public String getPortalSHA() {
		Matcher matcher = _portalSHAPattern.matcher(getDescription());

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
		return getTestrayCaseResults(null, null);
	}

	public List<TestrayCaseResult> getTestrayCaseResults(
		TestrayCaseType testrayCaseType, TestrayRun testrayRun) {

		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		if ((testrayRun != null) && (testrayRun.getID() > 0)) {
			sb.append("r_runToCaseResult_c_runId eq '");
			sb.append(testrayRun.getID());
			sb.append("' and ");
		}

		sb.append("r_buildToCaseResult_c_buildId eq '");
		sb.append(getID());
		sb.append("'");

		try {
			List<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"caseResults", TestrayCaseResult.FIELD_NAMES, sb.toString());

			for (JSONObject entityJSONObject : entityJSONObjects) {
				TestrayCaseResult testrayCaseResult =
					TestrayFactory.newTestrayCaseResult(this, entityJSONObject);

				TestrayCase testrayCase = testrayCaseResult.getTestrayCase();

				if (testrayCaseType != null) {
					if (Objects.equals(
							testrayCaseType.getID(),
							testrayCase.getTestrayCaseTypeID())) {

						testrayCaseResults.add(testrayCaseResult);
					}
				}
				else {
					testrayCaseResults.add(testrayCaseResult);
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return testrayCaseResults;
	}

	public TestrayProductVersion getTestrayProductVersion() {
		if (_testrayProductVersion != null) {
			return _testrayProductVersion;
		}

		JSONObject productVersionJSONObject = _jsonObject.getJSONObject(
			"productVersionToBuilds");

		_testrayProductVersion = _testrayProject.getTestrayProductVersionByID(
			productVersionJSONObject.getLong("id"));

		return _testrayProductVersion;
	}

	public TestrayProject getTestrayProject() {
		return _testrayProject;
	}

	public TestrayRoutine getTestrayRoutine() {
		return _testrayRoutine;
	}

	public synchronized List<TestrayRun> getTestrayRuns() {
		if (_testrayRuns != null) {
			return _testrayRuns;
		}

		_testrayRuns = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		sb.append("/o/c/builds/");
		sb.append(getID());
		sb.append("/buildToRuns?pageSize=100");

		try {
			JSONObject responseJSONObject = new JSONObject(
				_testrayServer.requestGet(sb.toString()));

			JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

				_testrayRuns.add(
					TestrayFactory.newTestrayRun(this, itemJSONObject));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _testrayRuns;
	}

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

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
		TestrayServer testrayServer = getTestrayServer();

		List<TestrayCaseResult> testrayCaseResults = getTestrayCaseResults(
			testrayServer.getTestrayCaseTypeByName("Batch"), null);

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
			return new URL(
				JenkinsResultsParserUtil.combine(
					String.valueOf(_testrayRoutine.getURL()), "/build/",
					String.valueOf(getID())));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	protected TestrayBuild(
		TestrayRoutine testrayRoutine, JSONObject jsonObject) {

		_testrayRoutine = testrayRoutine;

		_testrayProject = testrayRoutine.getTestrayProject();
		_testrayServer = testrayRoutine.getTestrayServer();

		_jsonObject = jsonObject;
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

	private static final Pattern _portalBranchPattern = Pattern.compile(
		"Portal Branch: (?<portalBranch>[^;]+);");
	private static final Pattern _portalSHAPattern = Pattern.compile(
		"Portal SHA: (?<portalSHA>[^;]+);");
	private static final Pattern _testrayAttachmentURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://testray.liferay.com/reports/production/logs/",
			"(?<startYearMonth>\\d{4}-\\d{2})/",
			"(?<topLevelMasterHostname>test-\\d+-\\d+)/",
			"(?<topLevelJobName>[^/]+)/(?<topLevelBuildNumber>\\d+)/.*"));

	private final JSONObject _jsonObject;
	private Matcher _testrayAttachmentURLMatcher;
	private TestrayProductVersion _testrayProductVersion;
	private final TestrayProject _testrayProject;
	private final TestrayRoutine _testrayRoutine;
	private List<TestrayRun> _testrayRuns;
	private final TestrayServer _testrayServer;
	private TopLevelBuildReport _topLevelBuildReport;

}