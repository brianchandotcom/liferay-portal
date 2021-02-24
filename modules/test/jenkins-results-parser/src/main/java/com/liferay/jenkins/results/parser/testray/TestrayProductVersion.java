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

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayProductVersion {

	public TestrayProductVersion(
		TestrayProject testrayProject, JSONObject jsonObject) {

		_testrayProject = testrayProject;
		_jsonObject = jsonObject;

		_testrayServer = _testrayProject.getTestrayServer();

		String urlString = JenkinsResultsParserUtil.combine(
			String.valueOf(_testrayServer.getURL()),
			"/home/-/testray/product_versions?testrayProjectId=",
			String.valueOf(_testrayProject.getID()));

		try {
			_url = new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	public int getID() {
		return _jsonObject.getInt("testrayProductVersionId");
	}

	public String getName() {
		return _jsonObject.getString("name");
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

	private final JSONObject _jsonObject;
	private final TestrayProject _testrayProject;
	private final TestrayServer _testrayServer;
	private final URL _url;

}