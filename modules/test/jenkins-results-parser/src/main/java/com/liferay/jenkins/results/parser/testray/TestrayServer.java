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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayServer {

	public TestrayServer(String urlString) {
		try {
			_url = new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(
				"Invalid Testray Server URL " + urlString,
				malformedURLException);
		}
	}

	public TestrayProject getTestrayProjectByID(int projectID) {
		_initTestrayProjects();

		return _testrayProjectsByID.get(projectID);
	}

	public TestrayProject getTestrayProjectByName(String projectName) {
		_initTestrayProjects();

		return _testrayProjectsByName.get(projectName);
	}

	public List<TestrayProject> getTestrayProjects() {
		_initTestrayProjects();

		return new ArrayList<>(_testrayProjectsByName.values());
	}

	public URL getURL() {
		return _url;
	}

	public void setAdminUserName(String adminUserName) {
		_adminUserName = adminUserName;
	}

	public void setAdminUserPassword(String adminUserPassword) {
		_adminUserPassword = adminUserPassword;
	}

	private JenkinsResultsParserUtil.HTTPAuthorization _getHTTPAuthorization() {
		if (JenkinsResultsParserUtil.isNullOrEmpty(_adminUserPassword) ||
			JenkinsResultsParserUtil.isNullOrEmpty(_adminUserName)) {

			return null;
		}

		return new JenkinsResultsParserUtil.BasicHTTPAuthorization(
			_adminUserPassword, _adminUserName);
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
				String projectAPIURL = JenkinsResultsParserUtil.combine(
					String.valueOf(getURL()),
					"/home/-/testray/projects.json?cur=",
					String.valueOf(current), "&delta=", String.valueOf(_DELTA),
					"&orderByCol=testrayProjectId");

				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					projectAPIURL, null, _getHTTPAuthorization());

				JSONArray dataJSONArray = jsonObject.getJSONArray("data");

				if (dataJSONArray.length() == 0) {
					break;
				}

				for (int i = 0; i < dataJSONArray.length(); i++) {
					JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);

					TestrayProject testrayProject = new TestrayProject(
						this, dataJSONObject);

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

	private static final int _DELTA = 25;

	private String _adminUserName;
	private String _adminUserPassword;
	private Map<Integer, TestrayProject> _testrayProjectsByID;
	private Map<String, TestrayProject> _testrayProjectsByName;
	private final URL _url;

}