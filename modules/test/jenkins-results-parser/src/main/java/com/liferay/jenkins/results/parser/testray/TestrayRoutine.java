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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayRoutine {

	public TestrayRoutine(
		TestrayProject testrayProject, JSONObject jsonObject) {

		_testrayProject = testrayProject;
		_jsonObject = jsonObject;

		_testrayServer = testrayProject.getTestrayServer();

		String urlString = JenkinsResultsParserUtil.combine(
			String.valueOf(_testrayServer.getURL()),
			"/home/-/testray/builds?testrayRoutineId=",
			String.valueOf(getID()));

		try {
			_url = new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(
				"Invalid Testray project URL " + urlString,
				malformedURLException);
		}
	}

	public TestrayBuild createTestrayBuild(
		TestrayProductVersion testrayProductVersion, String buildName) {

		return createTestrayBuild(
			testrayProductVersion, buildName, null, null, null);
	}

	public TestrayBuild createTestrayBuild(
		TestrayProductVersion testrayProductVersion, String buildName,
		Date buildDate, String buildDescription, String buildSHA) {

		if (testrayProductVersion == null) {
			throw new RuntimeException("Please set a Testray product version");
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(buildName)) {
			throw new RuntimeException("Please set a Testray build name");
		}

		StringBuilder sb = new StringBuilder();

		sb.append("name=");
		sb.append(buildName);
		sb.append("&testrayProductVersionId=");
		sb.append(testrayProductVersion.getID());
		sb.append("&testrayRoutineId=");
		sb.append(getID());

		if (buildDate != null) {
			String buildDateString = JenkinsResultsParserUtil.toDateString(
				buildDate, "MM-dd'T'HH:mm:ss.SSS'Z'", "America/Los_Angeles");

			sb.append("&createDate=");
			sb.append(buildDateString);
			sb.append("&dueDate=");
			sb.append(buildDateString);
			sb.append("&modifiedDate=");
			sb.append(buildDateString);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(buildDescription)) {
			sb.append("&description=");
			sb.append(buildDescription);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(buildSHA)) {
			sb.append("&gitHash=");
			sb.append(buildSHA);
		}

		String buildAddURL = JenkinsResultsParserUtil.combine(
			String.valueOf(_testrayServer.getURL()),
			"/web/guest/home/-/testray/builds/add.json");

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				buildAddURL, 2, 5, sb.toString());

			if (jsonObject.has("data")) {
				return new TestrayBuild(this, jsonObject.getJSONObject("data"));
			}

			String message = jsonObject.optString("message", "");

			if (!message.equals("The build name already exists.")) {
				throw new RuntimeException("Failed to create a Testray build");
			}
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException.getMessage(), ioException);
			}
		}

		return getTestrayBuildByName(buildName);
	}

	public int getID() {
		return _jsonObject.getInt("testrayRoutineId");
	}

	public String getName() {
		return _jsonObject.getString("name");
	}

	public TestrayBuild getTestrayBuildByID(int buildID) {
		if (_testrayBuildsByID.containsKey(buildID)) {
			return _testrayBuildsByID.get(buildID);
		}

		String buildAPIURL = JenkinsResultsParserUtil.combine(
			String.valueOf(_testrayServer.getURL()),
			"/web/guest/home/-/testray/builds/view.json?id=",
			String.valueOf(buildID));

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				buildAPIURL, true);

			if (!jsonObject.has("data")) {
				return null;
			}

			JSONObject dataJSONObject = jsonObject.getJSONObject("data");

			TestrayBuild testrayBuild = new TestrayBuild(this, dataJSONObject);

			_addToTestrayBuildMaps(testrayBuild);

			return testrayBuild;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public TestrayBuild getTestrayBuildByName(
		String buildName, String... names) {

		if (_testrayBuildsByName.containsKey(buildName)) {
			return _testrayBuildsByName.get(buildName);
		}

		int current = 1;

		StringBuilder sb = new StringBuilder();

		for (String name : names) {
			sb.append("&name=");
			sb.append(JenkinsResultsParserUtil.fixURL(name));
		}

		while (true) {
			try {
				String buildAPIURL = JenkinsResultsParserUtil.combine(
					String.valueOf(_testrayServer.getURL()),
					"/home/-/testray/builds.json?cur=", String.valueOf(current),
					"&delta=", String.valueOf(_DELTA), sb.toString(),
					"&orderByCol=testrayBuildId&testrayRoutineId=",
					String.valueOf(getID()));

				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					buildAPIURL, true);

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayBuild testrayBuild = new TestrayBuild(
						this, dataJSONObject);

					_addToTestrayBuildMaps(testrayBuild);

					if (_testrayBuildsByName.containsKey(buildName)) {
						return _testrayBuildsByName.get(buildName);
					}
				}

				current++;
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		return null;
	}

	public List<TestrayBuild> getTestrayBuilds() {
		return getTestrayBuilds(_DELTA);
	}

	public List<TestrayBuild> getTestrayBuilds(int maxSize) {
		return getTestrayBuilds(maxSize, null);
	}

	public List<TestrayBuild> getTestrayBuilds(
		int maxSize, String... nameFilters) {

		int current = 1;

		StringBuilder sb = new StringBuilder();

		if ((nameFilters != null) && (nameFilters.length > 0)) {
			for (String nameFilter : nameFilters) {
				if (JenkinsResultsParserUtil.isNullOrEmpty(nameFilter)) {
					continue;
				}

				sb.append("&name=");

				if (nameFilter.contains("-")) {
					sb.append("%22");
					sb.append(nameFilter);
					sb.append("%22");
				}
				else {
					sb.append(nameFilter);
				}
			}
		}

		while ((current * _DELTA) <= maxSize) {
			try {
				String buildAPIURL = JenkinsResultsParserUtil.combine(
					String.valueOf(_testrayServer.getURL()),
					"/home/-/testray/builds.json?cur=", String.valueOf(current),
					"&delta=", String.valueOf(_DELTA), sb.toString(),
					"&orderByCol=testrayBuildId&testrayRoutineId=",
					String.valueOf(getID()));

				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					buildAPIURL, true);

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayBuild testrayBuild = new TestrayBuild(
						this, dataJSONObject);

					if (_testrayBuildsByID.containsKey(testrayBuild.getID())) {
						break;
					}

					_addToTestrayBuildMaps(testrayBuild);
				}
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
			finally {
				current++;
			}
		}

		List<TestrayBuild> testrayBuilds = new ArrayList<>();

		for (TestrayBuild testrayBuild : _testrayBuildsByID.values()) {
			String testrayBuildName = testrayBuild.getName();

			if ((nameFilters != null) && (nameFilters.length > 0)) {
				boolean matches = true;

				for (String nameFilter : nameFilters) {
					if (JenkinsResultsParserUtil.isNullOrEmpty(nameFilter) ||
						testrayBuildName.contains(nameFilter)) {

						continue;
					}

					matches = false;

					break;
				}

				if (!matches) {
					continue;
				}
			}

			testrayBuilds.add(testrayBuild);
		}

		return testrayBuilds;
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

	private void _addToTestrayBuildMaps(TestrayBuild testrayBuild) {
		_testrayBuildsByID.put(testrayBuild.getID(), testrayBuild);
		_testrayBuildsByName.put(testrayBuild.getName(), testrayBuild);
	}

	private static final int _DELTA = 200;

	private static final Log _log = LogFactory.getLog(TestrayRoutine.class);

	private final JSONObject _jsonObject;
	private final Map<Integer, TestrayBuild> _testrayBuildsByID =
		new HashMap<>();
	private final Map<String, TestrayBuild> _testrayBuildsByName =
		new HashMap<>();
	private final TestrayProject _testrayProject;
	private final TestrayServer _testrayServer;
	private final URL _url;

}