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

	public TestrayServer getTestrayServer() {
		return _testrayServer;
	}

	public URL getURL() {
		return _url;
	}

	private final JSONObject _jsonObject;
	private final TestrayServer _testrayServer;
	private final URL _url;

}