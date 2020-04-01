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
import java.util.function.Function;
import java.util.function.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestCaseRun extends BaseSpiraArtifact {

	public static List<SpiraTestCaseRun> recordTestSpiraTestCaseRuns(
		SpiraProject spiraProject, SpiraRelease spiraRelease,
		SpiraReleaseBuild spiraReleaseBuild, List<Result> results) {

		Integer releaseID = null;

		if (spiraRelease != null) {
			releaseID = spiraRelease.getID();
		}

		Integer releaseBuildID = null;

		if (spiraReleaseBuild != null) {
			releaseBuildID = spiraReleaseBuild.getID();
		}

		Calendar calendar = Calendar.getInstance();

		JSONArray requestJSONArray = new JSONArray();

		for (Result result : results) {
			JSONObject requestJSONObject = new JSONObject();

			SpiraTestCaseObject spiraTestCase = result.getSpiraTestCase();

			requestJSONObject.put(
				SpiraTestCaseObject.ID_KEY, spiraTestCase.getID());
			requestJSONObject.put(
				"CustomProperties", result.getCustomListValuesJSONArray());
			requestJSONObject.put("ExecutionStatusId", result.getStatusID());
			requestJSONObject.put("RunnerMessage", spiraTestCase.getName());
			requestJSONObject.put("RunnerName", "Liferay CI");
			requestJSONObject.put("RunnerStackTrace", result.getDescription());
			requestJSONObject.put("RunnerTestName", spiraTestCase.getName());
			requestJSONObject.put(
				"StartDate", PathSpiraArtifact.toDateString(calendar));
			requestJSONObject.put(
				"TestRunFormatId", result.getRunnerFormatID());

			if (releaseID != null) {
				requestJSONObject.put("ReleaseId", releaseID);
			}

			if (releaseBuildID != null) {
				requestJSONObject.put("BuildId", releaseBuildID);
			}

			requestJSONArray.put(requestJSONObject);
		}

		String urlPath = "projects/{project_id}/test-runs/record-multiple";

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			List<SpiraTestCaseRun> spiraTestCaseRuns = new ArrayList<>();

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.ID_KEY, spiraProject.getID());

				spiraTestCaseRuns.add(new SpiraTestCaseRun(responseJSONObject));
			}

			return spiraTestCaseRuns;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static class Result {

		public Result(
			SpiraTestCaseObject spiraTestCase, RunnerFormat runnerFormat,
			String runnerStackTrace, Status status,
			List<SpiraCustomList.Value> spiraCustomListValues) {

			_spiraTestCase = spiraTestCase;
			_runnerFormat = runnerFormat;
			_description = runnerStackTrace;
			_status = status;
			_spiraCustomListValues = spiraCustomListValues;
		}

		public JSONArray getCustomListValuesJSONArray() {
			JSONArray customListValuesJSONArray = new JSONArray();

			if (_spiraCustomListValues == null) {
				return customListValuesJSONArray;
			}

			for (SpiraCustomList.Value spiraCustomListValue :
					_spiraCustomListValues) {

				SpiraCustomProperty spiraCustomProperty =
					spiraCustomListValue.getSpiraCustomProperty();

				JSONArray integerListValueJSONArray = new JSONArray();

				integerListValueJSONArray.put(spiraCustomListValue.getID());

				JSONObject customListValuesJSONObject = new JSONObject();

				customListValuesJSONObject.put(
					"IntegerListValue", integerListValueJSONArray);
				customListValuesJSONObject.put(
					"PropertyNumber", spiraCustomProperty.getPropertyNumber());

				customListValuesJSONArray.put(customListValuesJSONObject);
			}

			return customListValuesJSONArray;
		}

		public String getDescription() {
			return _description;
		}

		public Integer getRunnerFormatID() {
			return _runnerFormat.getID();
		}

		public SpiraTestCaseObject getSpiraTestCase() {
			return _spiraTestCase;
		}

		public Integer getStatusID() {
			return _status.getID();
		}

		private final String _description;
		private final RunnerFormat _runnerFormat;
		private final List<SpiraCustomList.Value> _spiraCustomListValues;
		private final SpiraTestCaseObject _spiraTestCase;
		private final Status _status;

	}

	public static enum RunnerFormat {

		HTML(2), PLAIN(1);

		public Integer getID() {
			return _id;
		}

		private RunnerFormat(Integer id) {
			_id = id;
		}

		private final Integer _id;

	}

	public static enum Status {

		BLOCKED(5), CAUTION(6), FAILED(1), NOT_APPLICABLE(4), NOT_RUN(3),
		PASSED(2);

		public Integer getID() {
			return _id;
		}

		private Status(Integer id) {
			_id = id;
		}

		private final Integer _id;

	}

	protected static List<SpiraTestCaseRun> getSpiraTestCaseRuns(
		final SpiraProject spiraProject,
		final SpiraTestCaseObject spiraTestCase,
		final SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraTestCaseRun.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraTestCaseRuns(
						spiraProject, spiraTestCase, searchParameters);
				}

			},
			new Function<JSONObject, SpiraTestCaseRun>() {

				@Override
				public SpiraTestCaseRun apply(JSONObject jsonObject) {
					return new SpiraTestCaseRun(jsonObject);
				}

			},
			searchParameters);
	}

	protected static List<SpiraTestCaseRun> recordSpiraTestCaseRuns(
		SpiraProject spiraProject, JSONObject... requestJSONObjects) {

		String urlPath = "projects/{project_id}/test-runs/record-multiple";

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONArray requestJSONArray = new JSONArray();

		for (JSONObject requestJSONObject : requestJSONObjects) {
			requestJSONArray.put(requestJSONObject);
		}

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			List<SpiraTestCaseRun> spiraTestCaseRuns = new ArrayList<>();

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.ID_KEY, spiraProject.getID());

				spiraTestCaseRuns.add(new SpiraTestCaseRun(responseJSONObject));
			}

			return spiraTestCaseRuns;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected static final Integer ARTIFACT_TYPE_ID = 5;

	protected static final String ARTIFACT_TYPE_NAME = "testrun";

	protected static final String ID_KEY = "TestRunId";

	private static List<JSONObject> _requestSpiraTestCaseRuns(
		SpiraProject spiraProject, SpiraTestCaseObject spiraTestCase,
		SearchQuery.SearchParameter... searchParameters) {

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(1000));
		urlParameters.put("sort_direction", "DESC");
		urlParameters.put("sort_field", ID_KEY);
		urlParameters.put("starting_row", String.valueOf(1));

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"test_case_id", String.valueOf(spiraTestCase.getID()));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchQuery.SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/test-cases/{test_case_id}/test-runs" +
					"/search",
				urlParameters, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			List<JSONObject> spiraTestCaseRuns = new ArrayList<>();

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.ID_KEY, spiraProject.getID());

				spiraTestCaseRuns.add(responseJSONObject);
			}

			return spiraTestCaseRuns;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraTestCaseRun(JSONObject jsonObject) {
		super(jsonObject);
	}

}