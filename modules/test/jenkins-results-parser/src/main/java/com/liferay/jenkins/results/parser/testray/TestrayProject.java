/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayProject {

	public static final String[] FIELD_NAMES = {
		"dateCreated", "dateModified", "description", "id", "name"
	};

	public TestrayProductVersion createTestrayProductVersion(
		String testrayProductVersionName) {

		TestrayProductVersion testrayProductVersion =
			getTestrayProductVersionByName(testrayProductVersionName);

		if (testrayProductVersion != null) {
			return testrayProductVersion;
		}

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"name", testrayProductVersionName
		).put(
			"r_projectToProductVersions_c_projectId", getID()
		);

		try {
			return TestrayFactory.newTestrayProductVersion(
				this,
				new JSONObject(
					_testrayServer.requestPost(
						"/o/c/productversions", requestJSONObject.toString())));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayRoutine createTestrayRoutine(String testrayRoutineName) {
		TestrayRoutine testrayRoutine = getTestrayRoutineByName(
			testrayRoutineName);

		if (testrayRoutine != null) {
			return testrayRoutine;
		}

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"name", testrayRoutineName
		).put(
			"r_routineToProjects_c_projectId", getID()
		);

		try {
			return TestrayFactory.newTestrayRoutine(
				this,
				new JSONObject(
					_testrayServer.requestPost(
						"/o/c/routines", requestJSONObject.toString())));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
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

	public TestrayProductVersion getTestrayProductVersionByID(
		long productVersionID) {

		String filter = JenkinsResultsParserUtil.combine(
			"id eq '", String.valueOf(productVersionID), "' and ",
			"r_projectToProductVersions_c_projectId eq '",
			String.valueOf(getID()), "'");

		try {
			List<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"productversions", TestrayProductVersion.FIELD_NAMES, filter, 1,
				1);

			if (entityJSONObjects.isEmpty()) {
				return null;
			}

			return TestrayFactory.newTestrayProductVersion(
				this, entityJSONObjects.get(0));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayProductVersion getTestrayProductVersionByName(
		String productVersionName) {

		String filter = JenkinsResultsParserUtil.combine(
			"name eq '", productVersionName, "' and ",
			"r_projectToProductVersions_c_projectId eq '",
			String.valueOf(getID()), "'");

		try {
			List<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"productversions", TestrayProductVersion.FIELD_NAMES, filter, 1,
				1);

			if (entityJSONObjects.isEmpty()) {
				return null;
			}

			return TestrayFactory.newTestrayProductVersion(
				this, entityJSONObjects.get(0));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayRoutine getTestrayRoutineByID(long routineID) {
		String filter = JenkinsResultsParserUtil.combine(
			"id eq '", String.valueOf(routineID), "'");

		try {
			List<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"routines", TestrayRoutine.FIELD_NAMES, filter, 1, 1);

			if (entityJSONObjects.isEmpty()) {
				return null;
			}

			return TestrayFactory.newTestrayRoutine(
				this, entityJSONObjects.get(0));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayRoutine getTestrayRoutineByName(String routineName) {
		String filter = JenkinsResultsParserUtil.combine(
			"name eq '", routineName, "' and ",
			"r_routineToProjects_c_projectId eq '", String.valueOf(getID()),
			"'");

		try {
			List<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"routines", TestrayRoutine.FIELD_NAMES, filter, 1, 1);

			if (entityJSONObjects.isEmpty()) {
				return null;
			}

			return TestrayFactory.newTestrayRoutine(
				this, entityJSONObjects.get(0));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

	public URL getURL() {
		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					String.valueOf(_testrayServer.getURL()), "/#/project/",
					String.valueOf(getID())));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	protected TestrayProject(
		TestrayServer testrayServer, JSONObject jsonObject) {

		_testrayServer = testrayServer;
		_jsonObject = jsonObject;
	}

	private final JSONObject _jsonObject;
	private final TestrayServer _testrayServer;

}