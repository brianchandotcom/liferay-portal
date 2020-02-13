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
public class SpiraTestCaseRun extends BaseSpiraArtifact {

	@Override
	public int getID() {
		return jsonObject.getInt("TestRunId");
	}

	protected static List<SpiraTestCaseRun> getSpiraTestCaseRuns(
			SpiraProject spiraProject, SpiraTestCaseObject spiraTestCase,
			SearchParameter... searchParameters)
		throws IOException {

		List<SpiraTestCaseRun> spiraTestCaseRuns = new ArrayList<>();

		for (SpiraTestCaseRun spiraTestCaseRun : _spiraTestCaseRuns.values()) {
			if (spiraTestCaseRun.matches(searchParameters)) {
				spiraTestCaseRuns.add(spiraTestCaseRun);
			}
		}

		if (!spiraTestCaseRuns.isEmpty()) {
			return spiraTestCaseRuns;
		}

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(1000));
		urlParameters.put("sort_direction", "DESC");
		urlParameters.put("sort_field", "TestRunId");
		urlParameters.put("starting_row", String.valueOf(1));

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"test_case_id", String.valueOf(spiraTestCase.getID()));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			"projects/{project_id}/test-cases/{test_case_id}/test-runs/search",
			urlParameters, urlPathReplacements, HttpRequestMethod.POST,
			requestJSONArray.toString());

		for (int i = 0; i < responseJSONArray.length(); i++) {
			JSONObject responseJSONObject = responseJSONArray.getJSONObject(i);

			responseJSONObject.put("ProjectId", spiraProject.getID());

			SpiraTestCaseRun spiraTestCaseRun = new SpiraTestCaseRun(
				responseJSONObject);

			_spiraTestCaseRuns.put(
				_createSpiraTestCaseRunKey(
					spiraProject.getID(), spiraTestCase.getID(),
					spiraTestCaseRun.getID()),
				spiraTestCaseRun);

			if (spiraTestCaseRun.matches(searchParameters)) {
				spiraTestCaseRuns.add(spiraTestCaseRun);
			}
		}

		return spiraTestCaseRuns;
	}

	private static String _createSpiraTestCaseRunKey(
		int projectID, int testCaseID, int testCaseRunID) {

		return projectID + "-" + testCaseID + "-" + testCaseRunID;
	}

	private SpiraTestCaseRun(JSONObject jsonObject) {
		super(jsonObject);
	}

	private static final Map<String, SpiraTestCaseRun> _spiraTestCaseRuns =
		new HashMap<>();

}