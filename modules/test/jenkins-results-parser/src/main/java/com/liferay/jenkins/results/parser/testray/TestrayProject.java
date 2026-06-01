/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

	public TestrayCase getTestrayCase(
		String testCaseName, TestrayCaseType testrayCaseType) {

		String filterString = JenkinsResultsParserUtil.combine(
			"name eq '", testCaseName, "' and ",
			"r_caseTypeToCases_c_caseTypeId eq '",
			String.valueOf(testrayCaseType.getID()), "' and ",
			"r_projectToCases_c_projectId eq '", String.valueOf(getID()), "'");

		try {
			Set<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"cases", TestrayCase.FIELD_NAMES, filterString, null, 1, 1);

			if (entityJSONObjects.isEmpty()) {
				return null;
			}

			for (JSONObject entityJSONObject : entityJSONObjects) {
				return TestrayFactory.newTestrayCase(this, entityJSONObject);
			}

			return null;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayCase getTestrayCaseByName(String testCaseName) {
		_initTestrayCases();

		return _testrayCases.get(testCaseName);
	}

	public long getTestrayCaseIDByName(String testCaseName) {
		_initTestrayCaseIDs();

		Long testrayCaseID = _testrayCaseIDs.get(testCaseName);

		if (testrayCaseID == null) {
			return 0L;
		}

		return testrayCaseID;
	}

	public List<TestrayCase> getTestrayCases() {
		_initTestrayCases();

		return new ArrayList<>(_testrayCases.values());
	}

	public TestrayComponent getTestrayComponentByID(long componentID) {
		synchronized (_testrayComponentsID) {
			TestrayComponent testrayComponent = _testrayComponentsID.get(
				componentID);

			if (testrayComponent != null) {
				return testrayComponent;
			}

			String filterString = JenkinsResultsParserUtil.combine(
				"id eq '", String.valueOf(componentID),
				"' and r_projectToComponents_c_projectId eq '",
				String.valueOf(getID()), "'");

			try {
				Set<JSONObject> entityJSONObjects =
					_testrayServer.requestGraphQL(
						"components", TestrayComponent.FIELD_NAMES,
						filterString, null, 1, 1);

				for (JSONObject entityJSONObject : entityJSONObjects) {
					testrayComponent = TestrayFactory.newTestrayComponent(
						this, entityJSONObject);

					_testrayComponentsID.put(
						testrayComponent.getID(), testrayComponent);
					_testrayComponentsName.put(
						testrayComponent.getName(), testrayComponent);

					return testrayComponent;
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			return null;
		}
	}

	public TestrayComponent getTestrayComponentByName(String componentName) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(componentName)) {
			return null;
		}

		synchronized (_testrayComponentsID) {
			TestrayComponent testrayComponent = _testrayComponentsName.get(
				componentName);

			if (testrayComponent != null) {
				return testrayComponent;
			}

			String filterString = JenkinsResultsParserUtil.combine(
				"name eq '", componentName,
				"' and r_projectToComponents_c_projectId eq '",
				String.valueOf(getID()), "'");

			try {
				Set<JSONObject> entityJSONObjects =
					_testrayServer.requestGraphQL(
						"components", TestrayComponent.FIELD_NAMES,
						filterString, null, 1, 1);

				for (JSONObject entityJSONObject : entityJSONObjects) {
					testrayComponent = TestrayFactory.newTestrayComponent(
						this, entityJSONObject);

					_testrayComponentsID.put(
						testrayComponent.getID(), testrayComponent);
					_testrayComponentsName.put(componentName, testrayComponent);

					return testrayComponent;
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			return null;
		}
	}

	public TestrayProductVersion getTestrayProductVersionByID(
		long productVersionID) {

		String filterString = JenkinsResultsParserUtil.combine(
			"id eq '", String.valueOf(productVersionID), "' and ",
			"r_projectToProductVersions_c_projectId eq '",
			String.valueOf(getID()), "'");

		try {
			Set<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"productVersions", TestrayProductVersion.FIELD_NAMES,
				filterString, null, 1, 1);

			if (entityJSONObjects.isEmpty()) {
				return null;
			}

			Iterator<JSONObject> iterator = entityJSONObjects.iterator();

			return TestrayFactory.newTestrayProductVersion(
				this, iterator.next());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayProductVersion getTestrayProductVersionByName(
		String productVersionName) {

		String filterString = JenkinsResultsParserUtil.combine(
			"name eq '", productVersionName, "' and ",
			"r_projectToProductVersions_c_projectId eq '",
			String.valueOf(getID()), "'");

		try {
			Set<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"productVersions", TestrayProductVersion.FIELD_NAMES,
				filterString, null, 1, 1);

			if (entityJSONObjects.isEmpty()) {
				return null;
			}

			Iterator<JSONObject> iterator = entityJSONObjects.iterator();

			return TestrayFactory.newTestrayProductVersion(
				this, iterator.next());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayRoutine getTestrayRoutineByID(long routineID) {
		TestrayRoutine testrayRoutine = _testrayServer.getTestrayRoutineByID(
			routineID);

		if (testrayRoutine != null) {
			return testrayRoutine;
		}

		String filterString = JenkinsResultsParserUtil.combine(
			"id eq '", String.valueOf(routineID), "'");

		try {
			Set<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"routines", TestrayRoutine.FIELD_NAMES, filterString, null, 1,
				1);

			if (entityJSONObjects.isEmpty()) {
				return null;
			}

			Iterator<JSONObject> iterator = entityJSONObjects.iterator();

			return TestrayFactory.newTestrayRoutine(this, iterator.next());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayRoutine getTestrayRoutineByName(String routineName) {
		String filterString = JenkinsResultsParserUtil.combine(
			"name eq '", routineName, "' and ",
			"r_routineToProjects_c_projectId eq '", String.valueOf(getID()),
			"'");

		try {
			Set<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"routines", TestrayRoutine.FIELD_NAMES, filterString, null, 1,
				1);

			if (entityJSONObjects.isEmpty()) {
				return null;
			}

			Iterator<JSONObject> iterator = entityJSONObjects.iterator();

			return TestrayFactory.newTestrayRoutine(this, iterator.next());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

	public TestrayTeam getTestrayTeamByID(long componentID) {
		for (TestrayTeam testrayTeam : getTestrayTeams()) {
			if (componentID == testrayTeam.getID()) {
				return testrayTeam;
			}
		}

		return null;
	}

	public TestrayTeam getTestrayTeamByName(String teamName) {
		for (TestrayTeam testrayTeam : getTestrayTeams()) {
			if (Objects.equals(teamName, testrayTeam.getName())) {
				return testrayTeam;
			}
		}

		return null;
	}

	public synchronized List<TestrayTeam> getTestrayTeams() {
		if (_testrayTeams != null) {
			return _testrayTeams;
		}

		_testrayTeams = new ArrayList<>();

		String filterString = JenkinsResultsParserUtil.combine(
			"r_projectToTeams_c_projectId eq '", String.valueOf(getID()), "'");

		try {
			Set<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"teams", TestrayTeam.FIELD_NAMES, filterString, null);

			for (JSONObject entityJSONObject : entityJSONObjects) {
				_testrayTeams.add(
					TestrayFactory.newTestrayTeam(this, entityJSONObject));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _testrayTeams;
	}

	public URL getURL() {
		try {
			return new URL(
				JenkinsResultsParserUtil.combine(
					String.valueOf(_testrayServer.getURL()), "/#/project/",
					String.valueOf(getID()), "/routines"));
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

	private synchronized void _initTestrayCaseIDs() {
		if (_testrayCases != null) {
			return;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Gathering test case IDs for project ", getName(), " at ",
				JenkinsResultsParserUtil.toDateString(new Date(start))));

		_testrayCases = new HashMap<>();

		String filterString = JenkinsResultsParserUtil.combine(
			"r_projectToCases_c_projectId eq '", String.valueOf(getID()), "'");

		try {
			Set<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"cases", TestrayCase.FIELD_NAMES_CASE_IDS, filterString, null);

			for (JSONObject entityJSONObject : entityJSONObjects) {
				_testrayCaseIDs.put(
					entityJSONObject.getString("name"),
					entityJSONObject.getLong("id"));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
		finally {
			long duration =
				JenkinsResultsParserUtil.getCurrentTimeMillis() - start;

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Gathered test case IDs for project ", getName(), " in ",
					JenkinsResultsParserUtil.toDurationString(duration)));
		}
	}

	private synchronized void _initTestrayCases() {
		if (_testrayCases != null) {
			return;
		}

		long start = JenkinsResultsParserUtil.getCurrentTimeMillis();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Gathering test cases for project ", getName(), " at ",
				JenkinsResultsParserUtil.toDateString(new Date(start))));

		_testrayCases = new HashMap<>();

		String filterString = JenkinsResultsParserUtil.combine(
			"r_projectToCases_c_projectId eq '", String.valueOf(getID()), "'");

		try {
			Set<JSONObject> entityJSONObjects = _testrayServer.requestGraphQL(
				"cases", TestrayCase.FIELD_NAMES, filterString, null, 0, 50);

			for (JSONObject entityJSONObject : entityJSONObjects) {
				TestrayCase testrayCase = TestrayFactory.newTestrayCase(
					this, entityJSONObject);

				_testrayCases.put(testrayCase.getName(), testrayCase);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
		finally {
			long duration =
				JenkinsResultsParserUtil.getCurrentTimeMillis() - start;

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Gathered test cases for project ", getName(), " in ",
					JenkinsResultsParserUtil.toDurationString(duration)));
		}
	}

	private final JSONObject _jsonObject;
	private final Map<String, Long> _testrayCaseIDs = new HashMap<>();
	private Map<String, TestrayCase> _testrayCases;
	private final Map<Long, TestrayComponent> _testrayComponentsID =
		new HashMap<>();
	private final Map<String, TestrayComponent> _testrayComponentsName =
		new HashMap<>();
	private final TestrayServer _testrayServer;
	private List<TestrayTeam> _testrayTeams;

}