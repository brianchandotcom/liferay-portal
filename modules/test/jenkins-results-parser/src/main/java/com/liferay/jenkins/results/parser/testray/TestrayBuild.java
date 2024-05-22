/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.TopLevelBuildReport;

import java.net.URL;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayBuild implements Comparable<TestrayBuild> {

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
		return null;
	}

	public List<TestrayCaseResult> getTestrayCaseResults(
		TestrayCaseType testrayCaseType, TestrayRun testrayRun) {

		return null;
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
		return null;
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
	private final TestrayServer _testrayServer;

}