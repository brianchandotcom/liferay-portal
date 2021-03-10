/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayBuild {

	public TestrayBuild(TestrayRoutine testrayRoutine, JSONObject jsonObject) {
		_testrayRoutine = testrayRoutine;
		_jsonObject = jsonObject;

		_testrayProject = _testrayRoutine.getTestrayProject();
		_testrayServer = _testrayRoutine.getTestrayServer();

		_testrayProductVersion = _testrayProject.getTestrayProductVersionByID(
			_jsonObject.getInt("testrayProductVersionId"));
	}

	public String getDescription() {
		return _jsonObject.getString("description");
	}

	public int getID() {
		return _jsonObject.getInt("testrayBuildId");
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public List<TestrayCaseResult> getTestrayCaseResults() {
		if (_testrayCaseResults == null) {
			_initTestrayCaseResults();
		}

		return _testrayCaseResults;
	}

	public TestrayProductVersion getTestrayProductVersion() {
		return _testrayProductVersion;
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

	public URL getURL() {
		try {
			return new URL(_jsonObject.getString("htmlURL"));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private void _initTestrayCaseResults() {
		String urlString = String.valueOf(getURL());

		String caseResultsAPIURLString = urlString.replace(
			"runs", "case_results.json");

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				caseResultsAPIURLString);

			JSONArray dataJSONArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < dataJSONArray.length(); i++) {
				JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

				TestrayCaseResult testrayCaseResult = new TestrayCaseResult(
					this, dataJSONObject);

				_testrayCaseResults.add(testrayCaseResult);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final JSONObject _jsonObject;
	private List<TestrayCaseResult> _testrayCaseResults;
	private final TestrayProductVersion _testrayProductVersion;
	private final TestrayProject _testrayProject;
	private final TestrayRoutine _testrayRoutine;
	private final TestrayServer _testrayServer;

}