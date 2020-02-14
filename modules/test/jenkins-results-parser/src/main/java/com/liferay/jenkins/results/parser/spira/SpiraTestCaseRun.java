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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestCaseRun extends BaseSpiraArtifact {

	public static List<SpiraTestCaseRun> recordSpiraTestCaseRuns(
			SpiraProject spiraProject, JSONObject... requestJSONObjects)
		throws IOException {

		String urlPath = "projects/{project_id}/test-runs/record-multiple";

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONArray requestJSONArray = new JSONArray();

		for (JSONObject requestJSONObject : requestJSONObjects) {
			requestJSONArray.put(requestJSONObject);
		}

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
			requestJSONArray.toString());

		List<SpiraTestCaseRun> spiraTestCaseRuns = new ArrayList<>();

		for (int i = 0; i < responseJSONArray.length(); i++) {
			JSONObject responseJSONObject = responseJSONArray.getJSONObject(i);

			responseJSONObject.put("ProjectId", spiraProject.getID());

			spiraTestCaseRuns.add(new SpiraTestCaseRun(responseJSONObject));
		}

		return spiraTestCaseRuns;
	}

	public static SpiraTestCaseRun recordTestSpiraTestCaseRun(
			SpiraProject spiraProject, SpiraTestCaseObject spiraTestCase)
		throws IOException {

		Calendar calendar = Calendar.getInstance();

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("ExecutionStatusId", STATUS_PASSED);
		requestJSONObject.put("RunnerMessage", spiraTestCase.getPath());
		requestJSONObject.put("RunnerName", "Liferay CI");
		requestJSONObject.put("RunnerStackTrace", "");
		requestJSONObject.put("RunnerTestName", spiraTestCase.getName());
		requestJSONObject.put(
			"StartDate", PathSpiraArtifact.toDateString(calendar));
		requestJSONObject.put("TestCaseId", spiraTestCase.getID());
		requestJSONObject.put("TestRunFormatId", RUNNER_FORMAT_PLAIN);

		List<SpiraTestCaseRun> spiraTestCaseRuns = recordSpiraTestCaseRuns(
			spiraProject, requestJSONObject);

		return spiraTestCaseRuns.get(0);
	}

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

	protected static final int RUNNER_FORMAT_HTML = 2;

	protected static final int RUNNER_FORMAT_PLAIN = 1;

	protected static final int STATUS_BLOCKED = 5;

	protected static final int STATUS_CAUTION = 6;

	protected static final int STATUS_FAILED = 1;

	protected static final int STATUS_NOT_APPLICABLE = 4;

	protected static final int STATUS_NOT_RUN = 3;

	protected static final int STATUS_PASSED = 2;

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