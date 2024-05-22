/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayProject {

	public TestrayProductVersion createTestrayProductVersion(
		String testrayProductVersionName) {

		return null;
	}

	public TestrayRoutine createTestrayRoutine(String testrayRoutineName) {
		return null;
	}

	public String getDescription() {
		return null;
	}

	public long getID() {
		return -1;
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		return null;
	}

	public TestrayProductVersion getTestrayProductVersionByID(
		long productVersionID) {

		return null;
	}

	public TestrayProductVersion getTestrayProductVersionByName(
		String productVersionName) {

		return null;
	}

	public TestrayRoutine getTestrayRoutineByID(long routineID) {
		return null;
	}

	public TestrayRoutine getTestrayRoutineByName(String routineName) {
		return null;
	}

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

	public URL getURL() {
		return null;
	}

	protected TestrayProject(
		TestrayServer testrayServer, JSONObject jsonObject) {

		_testrayServer = testrayServer;
		_jsonObject = jsonObject;
	}

	private final JSONObject _jsonObject;
	private final TestrayServer _testrayServer;

}