/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TopLevelBuildReport;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
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
		"githubCompareURLs", "gitHash", "id", "name"
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

	public JSONObject getRunsJSONObject() {
		return null;
	}

	public String getStartYearMonth() {
		return null;
	}

	public List<TestrayCaseResult> getTestrayCaseResults() {
		return getTestrayCaseResults(null, null);
	}

	public List<TestrayCaseResult> getTestrayCaseResults(
		TestrayCaseType testrayCaseType, TestrayRun testrayRun) {

		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		if (testrayCaseType != null) {
			sb.append("r_caseTypeToCases_c_caseTypeId eq '");
			sb.append(testrayCaseType.getID());
			sb.append("' and ");
		}

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
				"caseResults", TestrayCaseResult.FIELD_NAMES, sb.toString(), -1,
				25);

			for (JSONObject entityJSONObject : entityJSONObjects) {
				testrayCaseResults.add(
					TestrayFactory.newTestrayCaseResult(
						this, entityJSONObject));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return testrayCaseResults;
	}

	public TestrayProductVersion getTestrayProductVersion() {
		return null;
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

				System.out.println(itemJSONObject.getString("name"));

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
		return null;
	}

	public URL getTopLevelBuildReportURL() {
		return null;
	}

	public URL getTopLevelBuildURL() {
		return null;
	}

	public TestrayCaseResult getTopLevelTestrayCaseResult() {
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

	private static final Pattern _portalBranchPattern = Pattern.compile(
		"Portal Branch: (?<portalBranch>[^;]+);");
	private static final Pattern _portalSHAPattern = Pattern.compile(
		"Portal SHA: (?<portalSHA>[^;]+);");

	private final JSONObject _jsonObject;
	private final TestrayProject _testrayProject;
	private final TestrayRoutine _testrayRoutine;
	private List<TestrayRun> _testrayRuns;
	private final TestrayServer _testrayServer;

}