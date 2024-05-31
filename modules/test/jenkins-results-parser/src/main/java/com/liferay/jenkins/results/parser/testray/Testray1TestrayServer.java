/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

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
public class Testray1TestrayServer extends TestrayServer {

	@Override
	public TestrayBuild getTestrayBuildByID(long buildID) {
		String buildAPIURLPath = JenkinsResultsParserUtil.combine(
			"/web/guest/home/-/testray/builds/", String.valueOf(buildID),
			".json");

		try {
			JSONObject jsonObject = new JSONObject(requestGet(buildAPIURLPath));

			if (!jsonObject.has("data")) {
				return null;
			}

			JSONObject dataJSONObject = jsonObject.getJSONObject("data");

			return TestrayFactory.newTestrayBuild(this, dataJSONObject);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public TestrayCaseType getTestrayCaseTypeByID(long testrayCaseTypeID) {
		if (_testrayCaseTypes != null) {
			for (TestrayCaseType testrayCaseType : _testrayCaseTypes.values()) {
				if (Objects.equals(
						testrayCaseTypeID, testrayCaseType.getID())) {

					return testrayCaseType;
				}
			}
		}

		_testrayCaseTypes = new HashMap<>();

		TestrayCaseType testrayCaseType = null;

		try {
			JSONObject jsonObject = new JSONObject(
				requestGet("/home/-/testray/case_types.json"));

			JSONArray dataJSONArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < dataJSONArray.length(); i++) {
				JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

				testrayCaseType = TestrayFactory.newTestrayCaseType(
					this, dataJSONObject);

				_testrayCaseTypes.put(
					testrayCaseType.getName(), testrayCaseType);

				break;
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return testrayCaseType;
	}

	@Override
	public TestrayCaseType getTestrayCaseTypeByName(
		String testrayCaseTypeName) {

		if (_testrayCaseTypes != null) {
			return _testrayCaseTypes.get(testrayCaseTypeName);
		}

		_testrayCaseTypes = new HashMap<>();

		try {
			JSONObject jsonObject = new JSONObject(
				requestGet("/home/-/testray/case_types.json"));

			JSONArray dataJSONArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < dataJSONArray.length(); i++) {
				JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

				_testrayCaseTypes.put(
					dataJSONObject.getString("name"),
					TestrayFactory.newTestrayCaseType(this, dataJSONObject));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _testrayCaseTypes.get(testrayCaseTypeName);
	}

	@Override
	public TestrayProject getTestrayProjectByID(long projectID) {
		_initTestrayProjects();

		return _testrayProjectsByID.get(projectID);
	}

	@Override
	public TestrayProject getTestrayProjectByName(String projectName) {
		_initTestrayProjects();

		return _testrayProjectsByName.get(projectName);
	}

	@Override
	public List<TestrayProject> getTestrayProjects() {
		_initTestrayProjects();

		return new ArrayList<>(_testrayProjectsByName.values());
	}

	@Override
	public TestrayRoutine getTestrayRoutineByID(long routineId) {
		String routineAPIURLPath = JenkinsResultsParserUtil.combine(
			"/web/guest/home/-/testray/routines/", String.valueOf(routineId),
			".json");

		try {
			JSONObject jsonObject = new JSONObject(
				requestGet(routineAPIURLPath));

			if (!jsonObject.has("data")) {
				return null;
			}

			JSONObject dataJSONObject = jsonObject.getJSONObject("data");

			return TestrayFactory.newTestrayRoutine(this, dataJSONObject);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected Testray1TestrayServer(String urlString) {
		super(urlString);
	}

	private synchronized void _initTestrayProjects() {
		if ((_testrayProjectsByID != null) &&
			(_testrayProjectsByName != null)) {

			return;
		}

		_testrayProjectsByID = new HashMap<>();
		_testrayProjectsByName = new HashMap<>();

		int current = 1;

		while (true) {
			try {
				String projectAPIURLPath = JenkinsResultsParserUtil.combine(
					"/home/-/testray/projects.json?cur=",
					String.valueOf(current), "&delta=", String.valueOf(_DELTA),
					"&orderByCol=testrayProjectId");

				JSONObject jsonObject = new JSONObject(
					requestGet(projectAPIURLPath));

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayProject testrayProject =
						TestrayFactory.newTestrayProject(this, dataJSONObject);

					_testrayProjectsByID.put(
						testrayProject.getID(), testrayProject);
					_testrayProjectsByName.put(
						testrayProject.getName(), testrayProject);
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
			finally {
				current++;
			}
		}
	}

	private static final int _DELTA = 50;

	private Map<String, TestrayCaseType> _testrayCaseTypes;
	private Map<Long, TestrayProject> _testrayProjectsByID;
	private Map<String, TestrayProject> _testrayProjectsByName;

}