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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayProject {

	public TestrayProject(TestrayServer testrayServer, JSONObject jsonObject) {
		_testrayServer = testrayServer;
		_jsonObject = jsonObject;

		String urlString = JenkinsResultsParserUtil.combine(
			String.valueOf(testrayServer.getURL()),
			"/home/-/testray/routines?testrayProjectId=",
			String.valueOf(getID()));

		try {
			_url = new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(
				"Invalid Testray Project URL " + urlString,
				malformedURLException);
		}
	}

	public String getDescription() {
		return _jsonObject.getString("description");
	}

	public int getID() {
		return _jsonObject.getInt("testrayProjectId");
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public TestrayProductVersion getTestrayProductVersionByID(
		int productVersionID) {

		_initTestrayProductVersions();

		return _testrayProductVersionsByID.get(productVersionID);
	}

	public TestrayProductVersion getTestrayProductVersionByName(
		String productVersionName) {

		_initTestrayProductVersions();

		return _testrayProductVersionsByName.get(productVersionName);
	}

	public TestrayRoutine getTestrayRoutineByID(int routineID) {
		_initTestrayRoutines();

		return _testrayRoutinesByID.get(routineID);
	}

	public TestrayRoutine getTestrayRoutineByName(String routineName) {
		_initTestrayRoutines();

		return _testrayRoutinesByName.get(routineName);
	}

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

	public URL getURL() {
		return _url;
	}

	private synchronized void _initTestrayProductVersions() {
		if ((_testrayProductVersionsByID != null) &&
			(_testrayProductVersionsByName != null)) {

			return;
		}

		_testrayProductVersionsByID = new HashMap<>();
		_testrayProductVersionsByName = new HashMap<>();

		TestrayServer testrayServer = getTestrayServer();

		try {
			String productVersionAPIURL = JenkinsResultsParserUtil.combine(
				String.valueOf(testrayServer.getURL()),
				"/home/-/testray/product_versions/index.json?",
				"testrayProjectId=", String.valueOf(getID()));

			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				productVersionAPIURL, true);

			JSONArray dataJSONArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < dataJSONArray.length(); i++) {
				JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

				TestrayProductVersion testrayProductVersion =
					new TestrayProductVersion(this, dataJSONObject);

				_testrayProductVersionsByID.put(
					testrayProductVersion.getID(), testrayProductVersion);
				_testrayProductVersionsByName.put(
					testrayProductVersion.getName(), testrayProductVersion);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private synchronized void _initTestrayRoutines() {
		if ((_testrayRoutinesByID != null) &&
			(_testrayRoutinesByName != null)) {

			return;
		}

		_testrayRoutinesByID = new HashMap<>();
		_testrayRoutinesByName = new HashMap<>();

		int current = 1;

		TestrayServer testrayServer = getTestrayServer();

		while (true) {
			try {
				String routineAPIURL = JenkinsResultsParserUtil.combine(
					String.valueOf(testrayServer.getURL()),
					"/home/-/testray/routines.json?cur=",
					String.valueOf(current), "&delta=", String.valueOf(_DELTA),
					"&orderByCol=testrayRoutineId&testrayProjectId=",
					String.valueOf(getID()));

				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					routineAPIURL, true);

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayRoutine testrayRoutine = new TestrayRoutine(
						this, dataJSONObject);

					_testrayRoutinesByID.put(
						testrayRoutine.getID(), testrayRoutine);
					_testrayRoutinesByName.put(
						testrayRoutine.getName(), testrayRoutine);
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

	private static final int _DELTA = 25;

	private final JSONObject _jsonObject;
	private Map<Integer, TestrayProductVersion> _testrayProductVersionsByID;
	private Map<String, TestrayProductVersion> _testrayProductVersionsByName;
	private Map<Integer, TestrayRoutine> _testrayRoutinesByID;
	private Map<String, TestrayRoutine> _testrayRoutinesByName;
	private final TestrayServer _testrayServer;
	private final URL _url;

}