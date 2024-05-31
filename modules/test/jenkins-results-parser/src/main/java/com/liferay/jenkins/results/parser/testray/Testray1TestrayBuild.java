/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		if (_testrayProductVersion != null) {
			return _testrayProductVersion;
		}

		JSONObject jsonObject = getJSONObject();

		TestrayProject testrayProject = getTestrayProject();

		_testrayProductVersion = testrayProject.getTestrayProductVersionByID(
			jsonObject.getLong("testrayProductVersionId"));

		return _testrayProductVersion;
	}

	public TestrayProject getTestrayProject() {
		if (_testrayProject != null) {
			return _testrayProject;
		}

		TestrayRoutine testrayRoutine = getTestrayRoutine();

		_testrayProject = testrayRoutine.getTestrayProject();

		return _testrayProject;
	}

	public TestrayRoutine getTestrayRoutine() {
		if (_testrayRoutine != null) {
			return _testrayRoutine;
		}

		JSONObject jsonObject = getJSONObject();

		TestrayServer testrayServer = getTestrayServer();

		_testrayRoutine = testrayServer.getTestrayRoutineByID(
			jsonObject.getLong("testrayRoutineId"));

		return _testrayRoutine;
	}

	@Override
	public synchronized List<TestrayRun> getTestrayRuns() {
		if (_testrayRuns != null) {
			return _testrayRuns;
		}

		_testrayRuns = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		sb.append("/home/-/testray/runs.json?delta=50&testrayBuildId=");
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
	}

	protected Testray1TestrayBuild(
		TestrayServer testrayServer, JSONObject jsonObject) {

		super(testrayServer, jsonObject);
	}

	protected List<TestrayCaseResult> getTestrayCaseResults(int maxCount) {
		List<TestrayCaseResult> testrayCaseResults = new ArrayList<>();

		TestrayServer testrayServer = getTestrayServer();

		try {
			JSONObject jsonObject = new JSONObject(
				testrayServer.requestGet(
					"/home/-/testray/case_results.json?testrayBuildId=" +
						getID()));

			JSONArray dataJSONArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < dataJSONArray.length(); i++) {
				testrayCaseResults.add(
					TestrayFactory.newTestrayCaseResult(
						this, dataJSONArray.getJSONObject(i)));
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return testrayCaseResults;
	}

	private static final int _PAGE_COUNT = 100;

	private static final int _PAGE_DELTA = 200;

	private TestrayProductVersion _testrayProductVersion;
	private TestrayProject _testrayProject;
	private TestrayRoutine _testrayRoutine;
	private List<TestrayRun> _testrayRuns;

}