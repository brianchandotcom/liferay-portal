/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import java.net.URL;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayRoutine {

	public TestrayBuild createTestrayBuild(
		TestrayProductVersion testrayProductVersion, String buildName) {

		return createTestrayBuild(
			testrayProductVersion, buildName, null, null, null);
	}

	public TestrayBuild createTestrayBuild(
		TestrayProductVersion testrayProductVersion, String buildName,
		Date buildDate, String buildDescription, String buildSHA) {

		return null;
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

	public TestrayBuild getTestrayBuildByID(long buildID) {
		return null;
	}

	public TestrayBuild getTestrayBuildByName(
		String buildName, String... names) {

		return null;
	}

	public List<TestrayBuild> getTestrayBuilds() {
		return null;
	}

	public List<TestrayBuild> getTestrayBuilds(
		int maxSize, String... nameFilters) {

		return null;
	}

	public TestrayProject getTestrayProject() {
		return _testrayProject;
	}

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

	public URL getURL() {
		return _url;
	}

	public void setJSONObject(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	protected TestrayRoutine(
		TestrayProject testrayProject, JSONObject jsonObject) {

		_testrayProject = testrayProject;

		_testrayServer = testrayProject.getTestrayServer();

		_jsonObject = jsonObject;
	}

	protected TestrayRoutine(URL url) {
		_url = url;
	}

	protected void setTestrayProject(TestrayProject testrayProject) {
		_testrayProject = testrayProject;
	}

	protected void setTestrayServer(TestrayServer testrayServer) {
		_testrayServer = testrayServer;
	}

	private JSONObject _jsonObject;
	private TestrayProject _testrayProject;
	private TestrayServer _testrayServer;
	private URL _url;

}