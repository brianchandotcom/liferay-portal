/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Testray1TestrayRoutine extends TestrayRoutine {

	@Override
	public TestrayBuild createTestrayBuild(
		TestrayProductVersion testrayProductVersion, String buildName) {

		return createTestrayBuild(
			testrayProductVersion, buildName, null, null, null);
	}

	@Override
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

		TestrayServer testrayServer = getTestrayServer();

		try {
			JSONObject jsonObject = new JSONObject(
				testrayServer.requestPost(
					"/web/guest/home/-/testray/builds/add.json",
					sb.toString()));

			if (jsonObject.has("data")) {
				return TestrayFactory.newTestrayBuild(
					this, jsonObject.getJSONObject("data"));
			}

			String message = jsonObject.optString("message", "");

			if (!message.equals("The build name already exists.")) {
				throw new RuntimeException("Unable to create a Testray build");
			}
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}

		return getTestrayBuildByName(buildName);
	}

	@Override
	public long getID() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getLong("testrayRoutineId");
	}

	@Override
	public String getName() {
		JSONObject jsonObject = getJSONObject();

		return jsonObject.getString("name");
	}

	@Override
	public TestrayBuild getTestrayBuildByID(long buildID) {
		if (_testrayBuildsByID.containsKey(buildID)) {
			return _testrayBuildsByID.get(buildID);
		}

		TestrayServer testrayServer = getTestrayServer();

		String buildAPIURLPath = JenkinsResultsParserUtil.combine(
			"/web/guest/home/-/testray/builds/view.json?id=",
			String.valueOf(buildID));

		try {
			JSONObject jsonObject = new JSONObject(
				testrayServer.requestGet(buildAPIURLPath));

			if (!jsonObject.has("data")) {
				return null;
			}

			JSONObject dataJSONObject = jsonObject.getJSONObject("data");

			TestrayBuild testrayBuild = TestrayFactory.newTestrayBuild(
				this, dataJSONObject);

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

		TestrayServer testrayServer = getTestrayServer();

		while (true) {
			try {
				String buildAPIURLPath = JenkinsResultsParserUtil.combine(
					"/home/-/testray/builds.json?cur=", String.valueOf(current),
					"&delta=", String.valueOf(_DELTA), sb.toString(),
					"&orderByCol=testrayBuildId&testrayRoutineId=",
					String.valueOf(getID()));

				JSONObject jsonObject = new JSONObject(
					testrayServer.requestGet(buildAPIURLPath));

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayBuild testrayBuild = TestrayFactory.newTestrayBuild(
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

		TestrayServer testrayServer = getTestrayServer();

		while ((current * _DELTA) <= maxSize) {
			try {
				String buildAPIURLPath = JenkinsResultsParserUtil.combine(
					"/home/-/testray/builds.json?cur=", String.valueOf(current),
					"&delta=", String.valueOf(_DELTA), sb.toString(),
					"&orderByCol=testrayBuildId&testrayRoutineId=",
					String.valueOf(getID()));

				JSONObject jsonObject = new JSONObject(
					testrayServer.requestGet(buildAPIURLPath));

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayBuild testrayBuild = TestrayFactory.newTestrayBuild(
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

	public URL getURL() {
		TestrayServer testrayServer = getTestrayServer();

		String urlString = JenkinsResultsParserUtil.combine(
			String.valueOf(testrayServer.getURL()),
			"/home/-/testray/builds?testrayRoutineId=",
			String.valueOf(getID()));

		try {
			return new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(
				"Invalid Testray project URL " + urlString,
				malformedURLException);
		}
	}

	protected Testray1TestrayRoutine(
		TestrayProject testrayProject, JSONObject jsonObject) {

		super(testrayProject, jsonObject);
	}

	protected Testray1TestrayRoutine(URL testrayRoutineURL) {
		super(testrayRoutineURL);

		Matcher matcher = _testrayRoutineURLPattern.matcher(
			testrayRoutineURL.toString());

		if (!matcher.find()) {
			throw new RuntimeException(
				"Invalid Routine URL " + testrayRoutineURL);
		}

		TestrayServer testrayServer = TestrayFactory.newTestrayServer(
			matcher.group("serverURL"));

		setTestrayServer(testrayServer);

		try {
			String urlPath = JenkinsResultsParserUtil.combine(
				"/home/-/testray/routines/", matcher.group("routineID"),
				".json");

			JSONObject responseJSONObject = new JSONObject(
				testrayServer.requestGet(urlPath));

			JSONObject jsonObject = responseJSONObject.getJSONObject("data");

			setJSONObject(jsonObject);

			setTestrayProject(
				testrayServer.getTestrayProjectByID(
					Long.parseLong(jsonObject.getString("testrayProjectId"))));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _addToTestrayBuildMaps(TestrayBuild testrayBuild) {
		_testrayBuildsByID.put(testrayBuild.getID(), testrayBuild);
		_testrayBuildsByName.put(testrayBuild.getName(), testrayBuild);
	}

	private static final int _DELTA = 200;

	private static final Log _log = LogFactory.getLog(TestrayRoutine.class);

	private static final Pattern _testrayRoutineURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"(?<serverURL>https://[^/]+)/home/-/testray/builds\\?",
			"testrayRoutineId=(?<routineID>\\d+)"));

	private final Map<Long, TestrayBuild> _testrayBuildsByID = new TreeMap<>(
		Collections.reverseOrder());
	private final Map<String, TestrayBuild> _testrayBuildsByName =
		new HashMap<>();

}