/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class Testray1TestrayProject extends TestrayProject {

	@Override
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

		TestrayServer testrayServer = getTestrayServer();

		try {
			JSONObject jsonObject = new JSONObject(
				testrayServer.requestPost(
					"/home/-/testray/product_versions/add.json",
					sb.toString()));

			if (jsonObject.has("data")) {
				TestrayProductVersion newTestrayProductVersion =
					TestrayFactory.newTestrayProductVersion(
						this, jsonObject.getJSONObject("data"));

				_testrayProductVersionsByID.put(
					newTestrayProductVersion.getID(), newTestrayProductVersion);
				_testrayProductVersionsByName.put(
					testrayProductVersionName, newTestrayProductVersion);

				return newTestrayProductVersion;
			}

			throw new RuntimeException("Unable to create a product version");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
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

		TestrayServer testrayServer = getTestrayServer();

		try {
			JSONObject jsonObject = new JSONObject(
				testrayServer.requestPost(
					"/home/-/testray/routines/add.json", sb.toString()));

			if (jsonObject.has("data")) {
				TestrayRoutine newTestrayRoutine =
					TestrayFactory.newTestrayRoutine(
						this, jsonObject.getJSONObject("data"));

				_testrayRoutinesByID.put(getID(), newTestrayRoutine);
				_testrayRoutinesByName.put(
					testrayRoutineName, newTestrayRoutine);

				return newTestrayRoutine;
			}

			throw new RuntimeException("Unable to create a routine");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getDescription() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("description");
	}

	@Override
	public long getID() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getLong("testrayProjectId");
	}

	@Override
	public String getName() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("name");
	}

	@Override
	public TestrayProductVersion getTestrayProductVersionByID(
		long productVersionID) {

		_initTestrayProductVersions();

		return _testrayProductVersionsByID.get(productVersionID);
	}

	@Override
	public TestrayProductVersion getTestrayProductVersionByName(
		String productVersionName) {

		_initTestrayProductVersions();

		return _testrayProductVersionsByName.get(productVersionName);
	}

	@Override
	public TestrayRoutine getTestrayRoutineByID(long routineID) {
		_initTestrayRoutines();

		return _testrayRoutinesByID.get(routineID);
	}

	@Override
	public TestrayRoutine getTestrayRoutineByName(String routineName) {
		_initTestrayRoutines();

		return _testrayRoutinesByName.get(routineName);
	}

	@Override
	public URL getURL() {
		return _url;
	}

	protected Testray1TestrayProject(
		TestrayServer testrayServer, JSONObject jsonObject) {

		super(testrayServer, jsonObject);

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
				String productVersionAPIURLPath =
					JenkinsResultsParserUtil.combine(
						"/home/-/testray/product_versions/index.json?cur=",
						String.valueOf(current), "&delta=",
						String.valueOf(_DELTA), "&testrayProjectId=",
						String.valueOf(getID()));

				JSONObject jsonObject = new JSONObject(
					testrayServer.requestGet(productVersionAPIURLPath));

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayProductVersion testrayProductVersion =
						TestrayFactory.newTestrayProductVersion(
							this, dataJSONObject);

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
				String routineAPIURLPath = JenkinsResultsParserUtil.combine(
					"/home/-/testray/routines.json?cur=",
					String.valueOf(current), "&delta=", String.valueOf(_DELTA),
					"&orderByCol=testrayRoutineId&testrayProjectId=",
					String.valueOf(getID()));

				JSONObject jsonObject = new JSONObject(
					testrayServer.requestGet(routineAPIURLPath));

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayRoutine testrayRoutine =
						TestrayFactory.newTestrayRoutine(this, dataJSONObject);

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

	private Map<Long, TestrayProductVersion> _testrayProductVersionsByID;
	private Map<String, TestrayProductVersion> _testrayProductVersionsByName;
	private Map<Long, TestrayRoutine> _testrayRoutinesByID;
	private Map<String, TestrayRoutine> _testrayRoutinesByName;
	private final URL _url;

}