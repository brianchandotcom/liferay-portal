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

package com.liferay.jenkins.results.parser.spira;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTest {

	public int getID() {
		return _jsonObject.getInt("TestCaseId");
	}

	public String getName() {
		return _jsonObject.getString("Name");
	}

	public JSONObject toJSONObject() {
		return _jsonObject;
	}

	@Override
	public String toString() {
		return _jsonObject.toString();
	}

	protected SpiraTest(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	protected static List<SpiraTest> getSpiraTests(
			SpiraProject spiraProject, SearchParameter... searchParameters)
		throws IOException {

		List<SpiraTest> spiraTests = new ArrayList<>();

		for (SpiraTest spiraTest : _spiraTests.values()) {
			if (spiraTest.matches(searchParameters)) {
				spiraTests.add(spiraTest);
			}
		}

		if (!spiraTests.isEmpty()) {
			return spiraTests;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(_NUMBER_OF_ROWS));
		urlParameters.put("starting_row", String.valueOf(_STARTING_ROW));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			"projects/{project_id}/test-cases/search", urlParameters,
			urlPathReplacements, HttpRequestMethod.POST,
			requestJSONArray.toString());

		for (int i = 0; i < responseJSONArray.length(); i++) {
			SpiraTest spiraTest = new SpiraTest(
				responseJSONArray.getJSONObject(i));

			_spiraTests.put(
				_createSpiraTestKey(spiraProject.getID(), spiraTest.getID()),
				spiraTest);

			if (spiraTest.matches(searchParameters)) {
				spiraTests.add(spiraTest);
			}
		}

		return spiraTests;
	}

	protected boolean matches(SearchParameter... searchParameters) {
		return SearchParameter.matches(toJSONObject(), searchParameters);
	}

	private static String _createSpiraTestKey(
		Integer projectID, Integer testID) {

		return projectID + "-" + testID;
	}

	private static final int _NUMBER_OF_ROWS = 15000;

	private static final int _STARTING_ROW = 1;

	private static final Map<String, SpiraTest> _spiraTests = new HashMap<>();

	private final JSONObject _jsonObject;

}