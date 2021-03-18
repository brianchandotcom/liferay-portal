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

	public TestrayProductVersion createTestrayProductVersion(
		String testrayProductVersionName) {

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayProductVersionName)) {
			throw new RuntimeException(
				"Please set a Testray product version name");
		}

		TestrayProductVersion testrayProductVersion =
			getTestrayProductVersionByName(testrayProductVersionName);

		if (testrayProductVersion != null) {
			return testrayProductVersion;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("name=");
		sb.append(testrayProductVersionName);
		sb.append("&testrayProjectId=");
		sb.append(getID());

		String productVersionAddURL = JenkinsResultsParserUtil.combine(
			String.valueOf(_testrayServer.getURL()),
			"/home/-/testray/product_versions/add.json");

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				productVersionAddURL, sb.toString());

			if (jsonObject.has("data")) {
				return new TestrayProductVersion(
					this, jsonObject.getJSONObject("data"));
			}

			throw new RuntimeException("Failed to create a product version");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayRoutine createTestrayRoutine(String testrayRoutineName) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {
			throw new RuntimeException("Please set a Testray routine name");
		}

		TestrayRoutine testrayRoutine = getTestrayRoutineByName(
			testrayRoutineName);

		if (testrayRoutine != null) {
			return testrayRoutine;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("name=");
		sb.append(testrayRoutineName);
		sb.append("&testrayProjectId=");
		sb.append(getID());

		String routineAddURL = JenkinsResultsParserUtil.combine(
			String.valueOf(_testrayServer.getURL()),
			"/home/-/testray/routines/add.json");

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				routineAddURL, sb.toString());

			if (jsonObject.has("data")) {
				return new TestrayRoutine(
					this, jsonObject.getJSONObject("data"));
			}

			throw new RuntimeException("Failed to create a routine");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
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

		int current = 1;

		while (true) {
			try {
				String productVersionAPIURL = JenkinsResultsParserUtil.combine(
					String.valueOf(testrayServer.getURL()),
					"/home/-/testray/product_versions/index.json?cur=",
					String.valueOf(current), "&delta=", String.valueOf(_DELTA),
					"&testrayProjectId=", String.valueOf(getID()));

				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					productVersionAPIURL, true);

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

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
			finally {
				current++;
			}
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