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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;
import com.liferay.jenkins.results.parser.ParallelExecutor;
import com.liferay.jenkins.results.parser.spira.result.SpiraTestResult;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.function.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestCaseRun extends BaseSpiraArtifact {

	public static final int NUMBER_OF_ROWS_MAX_DEFAULT = 1000;

	public static void clearCachedSpiraTestCaseRuns() {
		clearCachedSpiraArtifacts(SpiraTestCaseRun.class);
	}

	public static void deleteSpiraTestCaseRuns(
		List<SpiraTestCaseRun> spiraTestCaseRuns) {

		for (SpiraTestCaseRun spiraTestCaseRun : spiraTestCaseRuns) {
			Map<String, String> urlPathReplacements = new HashMap<>();

			SpiraProject spiraProject = spiraTestCaseRun.getSpiraProject();

			urlPathReplacements.put(
				"project_id", String.valueOf(spiraProject.getID()));

			urlPathReplacements.put(
				"test_run_id", String.valueOf(spiraTestCaseRun.getID()));

			try {
				SpiraRestAPIUtil.request(
					"projects/{project_id}/test-runs/{test_run_id}", null,
					urlPathReplacements, HttpRequestMethod.DELETE, null);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
	}

	public static List<SpiraTestCaseRun> recordSpiraTestCaseRuns(
		SpiraProject spiraProject, SpiraRelease spiraRelease,
		SpiraReleaseBuild spiraReleaseBuild, SpiraTestSet spiraTestSet,
		List<Result> results) {

		Integer releaseID = null;

		if (spiraRelease != null) {
			releaseID = spiraRelease.getID();
		}

		Integer releaseBuildID = null;

		if (spiraReleaseBuild != null) {
			releaseBuildID = spiraReleaseBuild.getID();
		}

		Integer testSetID = null;

		if (spiraTestSet != null) {
			testSetID = spiraTestSet.getID();
		}

		Calendar calendar = Calendar.getInstance();

		JSONArray requestJSONArray = new JSONArray();

		for (Result result : results) {
			JSONObject requestJSONObject = new JSONObject();

			SpiraAutomationHost spiraAutomationHost =
				result.getSpiraAutomationHost();

			requestJSONObject.put(
				SpiraAutomationHost.KEY_ID, spiraAutomationHost.getID());

			SpiraTestCaseObject spiraTestCaseObject =
				result.getSpiraTestCaseObject();

			requestJSONObject.put(
				SpiraTestCaseObject.KEY_ID, spiraTestCaseObject.getID());

			requestJSONObject.put(
				"CustomProperties", result.getCustomPropertyValuesJSONArray());
			requestJSONObject.put("ExecutionStatusId", result.getStatusID());
			requestJSONObject.put(
				"RunnerMessage", spiraTestCaseObject.getName());
			requestJSONObject.put("RunnerName", "Liferay CI");
			requestJSONObject.put("RunnerStackTrace", result.getDescription());
			requestJSONObject.put(
				"RunnerTestName", spiraTestCaseObject.getName());
			requestJSONObject.put("StartDate", toDateString(calendar));
			requestJSONObject.put(
				"TestRunFormatId", result.getRunnerFormatID());

			if (releaseID != null) {
				requestJSONObject.put(SpiraRelease.KEY_ID, releaseID);
			}

			if (releaseBuildID != null) {
				requestJSONObject.put(SpiraReleaseBuild.KEY_ID, releaseBuildID);
			}

			if (testSetID != null) {
				requestJSONObject.put(SpiraTestSet.KEY_ID, testSetID);

				SpiraTestSet.SpiraTestSetTestCase spiraTestSetTestCase =
					spiraTestSet.assignSpiraTestCaseObject(spiraTestCaseObject);

				requestJSONObject.put(
					SpiraTestSet.SpiraTestSetTestCase.KEY_ID,
					spiraTestSetTestCase.getID());
			}

			requestJSONArray.put(requestJSONObject);
		}

		return _recordSpiraTestCaseRuns(spiraProject, requestJSONArray);
	}

	public static List<SpiraTestCaseRun> recordSpiraTestCaseRuns(
		final SpiraProject spiraProject, final SpiraRelease spiraRelease,
		final SpiraReleaseBuild spiraReleaseBuild,
		final SpiraTestSet spiraTestSet, List<Result> results,
		Integer resultGroupSize, Integer threadCount) {

		if (results.size() < resultGroupSize) {
			return recordSpiraTestCaseRuns(
				spiraProject, spiraRelease, spiraReleaseBuild, spiraTestSet,
				results);
		}

		List<Callable<List<SpiraTestCaseRun>>> callables = new ArrayList<>();

		int resultCount = results.size();

		int resultGroupCount = resultCount / resultGroupSize;

		if ((resultCount % resultGroupSize) > 0) {
			resultGroupCount++;
		}

		for (int i = 0; i < resultGroupCount; i++) {
			int resultGroupStart = i * resultGroupSize;

			int resultGroupEnd = ((i + 1) * resultGroupSize) - 1;

			if (resultGroupEnd > resultCount) {
				resultGroupEnd = resultCount;
			}

			final List<Result> resultGroup = results.subList(
				resultGroupStart, resultGroupEnd);

			Callable<List<SpiraTestCaseRun>> callable =
				new IndexedCallable<List<SpiraTestCaseRun>>(i) {

					@Override
					public List<SpiraTestCaseRun> safeCall() {
						long start = System.currentTimeMillis();

						String startString =
							JenkinsResultsParserUtil.toDateString(
								new Date(start), "America/Los_Angeles");

						print("Starting at " + startString);

						for (Result result : resultGroup) {
							result.initSpiraTestCaseObject();
						}

						List<SpiraTestCaseRun> spiraTestCaseRuns =
							recordSpiraTestCaseRuns(
								spiraProject, spiraRelease, spiraReleaseBuild,
								spiraTestSet, resultGroup);

						String durationString =
							JenkinsResultsParserUtil.toDurationString(
								System.currentTimeMillis() - start);

						print("Completed in " + durationString);

						return spiraTestCaseRuns;
					}

				};

			callables.add(callable);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("Recording results for ");
		sb.append(resultGroupCount);
		sb.append(" groups of ");
		sb.append(resultGroupSize);
		sb.append(" in ");
		sb.append(threadCount);
		sb.append(" threads.");

		System.out.println(sb.toString());

		ParallelExecutor<List<SpiraTestCaseRun>> parallelExecutor =
			new ParallelExecutor<>(
				callables,
				JenkinsResultsParserUtil.getNewThreadPoolExecutor(
					threadCount, true));

		List<SpiraTestCaseRun> spiraTestCaseRuns = new ArrayList<>();

		for (List<SpiraTestCaseRun> spiraTestCaseRunList :
				parallelExecutor.execute()) {

			spiraTestCaseRuns.addAll(spiraTestCaseRunList);
		}

		return spiraTestCaseRuns;
	}

	public static List<SpiraTestCaseRun> recordSpiraTestCaseRuns(
		SpiraProject spiraProject, SpiraTestResult... spiraTestResults) {

		List<Callable<JSONObject>> callableList = new ArrayList<>();

		for (final SpiraTestResult spiraTestResult : spiraTestResults) {
			callableList.add(
				new Callable<JSONObject>() {

					@Override
					public JSONObject call() {
						long start = System.currentTimeMillis();

						JSONObject requestJSONObject =
							spiraTestResult.getRequestJSONObject();

						SpiraTestCaseObject spiraTestCaseObject =
							spiraTestResult.getSpiraTestCaseObject();

						System.out.println(
							JenkinsResultsParserUtil.combine(
								spiraTestCaseObject.getPath(), " ",
								spiraTestCaseObject.getURL(), " in ",
								JenkinsResultsParserUtil.toDurationString(
									System.currentTimeMillis() - start)));

						return requestJSONObject;
					}

				});
		}

		if (callableList.isEmpty()) {
			return new ArrayList<>();
		}

		JSONArray requestJSONArray = new JSONArray();

		Callable<JSONObject> callable = callableList.get(0);

		try {
			requestJSONArray.put(callable.call());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		if (callableList.size() > 1) {
			List<Callable<JSONObject>> callableSubList = callableList.subList(
				1, callableList.size());

			ThreadPoolExecutor threadPoolExecutor =
				JenkinsResultsParserUtil.getNewThreadPoolExecutor(
					callableSubList.size(), true);

			ParallelExecutor<JSONObject> parallelExecutor =
				new ParallelExecutor<>(callableSubList, threadPoolExecutor);

			for (JSONObject requestJSONObject : parallelExecutor.execute()) {
				requestJSONArray.put(requestJSONObject);
			}
		}

		return _recordSpiraTestCaseRuns(spiraProject, requestJSONArray);
	}

	public JSONObject getAutomatedJSONObject() {
		if (_automatedJSONObject != null) {
			return _automatedJSONObject;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		SpiraProject spiraProject = getSpiraProject();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		urlPathReplacements.put("test_run_id", String.valueOf(getID()));

		try {
			_automatedJSONObject = SpiraRestAPIUtil.requestJSONObject(
				"projects/{project_id}/test-runs/{test_run_id}/automated", null,
				urlPathReplacements, HttpRequestMethod.GET, null);

			return _automatedJSONObject;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getName() {
		if (jsonObject.has("Name")) {
			return jsonObject.getString("Name");
		}

		return jsonObject.getString("RunnerTestName");
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, String.valueOf(spiraProject.getID()), "/TestRun/",
			String.valueOf(getID()), ".aspx");
	}

	public static class Result implements Serializable {

		public Result(
			SpiraTestCaseObject spiraTestCaseObject,
			SpiraAutomationHost spiraAutomationHost, RunnerFormat runnerFormat,
			String runnerStackTrace, long duration, Status status,
			List<SpiraCustomPropertyValue> spiraCustomPropertyValues) {

			_spiraTestCaseObject = spiraTestCaseObject;
			_spiraAutomationHost = spiraAutomationHost;
			_runnerFormat = runnerFormat;
			_duration = duration;
			_status = status;
			_spiraCustomPropertyValues = spiraCustomPropertyValues;

			_description = runnerStackTrace;
		}

		public Result(
			Supplier<SpiraTestCaseObject> spiraTestCaseObjectSupplier,
			SpiraAutomationHost spiraAutomationHost, RunnerFormat runnerFormat,
			String runnerStackTrace, long duration, Status status,
			List<SpiraCustomPropertyValue> spiraCustomPropertyValues) {

			_spiraTestCaseObjectSupplier = spiraTestCaseObjectSupplier;
			_spiraAutomationHost = spiraAutomationHost;
			_runnerFormat = runnerFormat;
			_duration = duration;
			_status = status;
			_spiraCustomPropertyValues = spiraCustomPropertyValues;

			_description = runnerStackTrace;
		}

		public JSONArray getCustomPropertyValuesJSONArray() {
			JSONArray customPropertyValuesJSONArray = new JSONArray();

			if (_spiraCustomPropertyValues == null) {
				return customPropertyValuesJSONArray;
			}

			for (SpiraCustomPropertyValue spiraCustomPropertyValue :
					_spiraCustomPropertyValues) {

				if (spiraCustomPropertyValue instanceof
						MultiListSpiraCustomPropertyValue) {

					_putMultiListSpiraCustomPropertyValue(
						customPropertyValuesJSONArray,
						(MultiListSpiraCustomPropertyValue)
							spiraCustomPropertyValue);

					continue;
				}

				customPropertyValuesJSONArray.put(
					spiraCustomPropertyValue.getCustomPropertyJSONObject());
			}

			customPropertyValuesJSONArray.put(_getDurationJSONObject());
			customPropertyValuesJSONArray.put(_getDurationStringJSONObject());

			return customPropertyValuesJSONArray;
		}

		public String getDescription() {
			return _description;
		}

		public Integer getRunnerFormatID() {
			return _runnerFormat.getID();
		}

		public SpiraAutomationHost getSpiraAutomationHost() {
			return _spiraAutomationHost;
		}

		public SpiraProject getSpiraProject() {
			SpiraTestCaseObject spiraTestCaseObject = getSpiraTestCaseObject();

			return spiraTestCaseObject.getSpiraProject();
		}

		public SpiraTestCaseObject getSpiraTestCaseObject() {
			initSpiraTestCaseObject();

			return _spiraTestCaseObject;
		}

		public Integer getStatusID() {
			return _status.getID();
		}

		public void initSpiraTestCaseObject() {
			if (_spiraTestCaseObject != null) {
				return;
			}

			if (_spiraTestCaseObjectSupplier == null) {
				return;
			}

			_spiraTestCaseObject = _spiraTestCaseObjectSupplier.get();
		}

		private JSONObject _getDurationJSONObject() {
			SpiraCustomProperty spiraCustomProperty =
				SpiraCustomProperty.createSpiraCustomProperty(
					getSpiraProject(), SpiraTestCaseRun.class, "Duration",
					SpiraCustomProperty.Type.INTEGER);

			Integer duration = _duration.intValue();

			if (_duration > Integer.MAX_VALUE) {
				duration = Integer.MAX_VALUE;
			}

			SpiraCustomPropertyValue spiraCustomPropertyValue =
				SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					spiraCustomProperty, String.valueOf(duration));

			return spiraCustomPropertyValue.getCustomPropertyJSONObject();
		}

		private JSONObject _getDurationStringJSONObject() {
			SpiraCustomProperty spiraCustomProperty =
				SpiraCustomProperty.createSpiraCustomProperty(
					getSpiraProject(), SpiraTestCaseRun.class,
					"Duration String", SpiraCustomProperty.Type.TEXT);

			SpiraCustomPropertyValue spiraCustomPropertyValue =
				SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					spiraCustomProperty,
					JenkinsResultsParserUtil.toDurationString(_duration));

			return spiraCustomPropertyValue.getCustomPropertyJSONObject();
		}

		private void _putMultiListSpiraCustomPropertyValue(
			JSONArray customPropertyValuesJSONArray,
			MultiListSpiraCustomPropertyValue
				multiListSpiraCustomPropertyValue) {

			JSONObject customPropertyValueJSONObject = null;

			int propertyNumber =
				multiListSpiraCustomPropertyValue.getPropertyNumber();

			for (int i = 0; i < customPropertyValuesJSONArray.length(); i++) {
				JSONObject jsonObject =
					customPropertyValuesJSONArray.getJSONObject(i);

				if (propertyNumber != jsonObject.optInt("PropertyNumber")) {
					continue;
				}

				customPropertyValueJSONObject = jsonObject;

				break;
			}

			if (customPropertyValueJSONObject == null) {
				customPropertyValueJSONObject = new JSONObject();

				customPropertyValueJSONObject.put(
					"PropertyNumber", propertyNumber);

				customPropertyValuesJSONArray.put(
					customPropertyValueJSONObject);
			}

			JSONArray integerListValueJSONArray =
				customPropertyValueJSONObject.optJSONArray("IntegerListValue");

			if ((integerListValueJSONArray == null) ||
				(integerListValueJSONArray == JSONObject.NULL)) {

				integerListValueJSONArray = new JSONArray();
			}

			for (SpiraCustomListValue spiraCustomListValue :
					multiListSpiraCustomPropertyValue.getValue()) {

				integerListValueJSONArray.put(spiraCustomListValue.getID());
			}

			customPropertyValueJSONObject.put(
				"IntegerListValue", integerListValueJSONArray);
		}

		private final String _description;
		private final Long _duration;
		private final RunnerFormat _runnerFormat;
		private final SpiraAutomationHost _spiraAutomationHost;
		private final List<SpiraCustomPropertyValue> _spiraCustomPropertyValues;
		private SpiraTestCaseObject _spiraTestCaseObject;
		private transient Supplier<SpiraTestCaseObject>
			_spiraTestCaseObjectSupplier;
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

		public static String getStatusName(int statusID) {
			for (Status status : values()) {
				if (statusID == status.getID()) {
					return status.name();
				}
			}

			throw new IllegalArgumentException(
				"No enum is defined for status ID: " + statusID);
		}

		public Integer getID() {
			return _id;
		}

		private Status(Integer id) {
			_id = id;
		}

		private final Integer _id;

	}

	protected static List<SpiraTestCaseRun> getSpiraTestCaseRuns(
		final int numberOfRows, final SpiraProject spiraProject,
		final SearchQuery.SearchParameter... searchParameters) {

		return getSpiraTestCaseRuns(
			numberOfRows, spiraProject, null, searchParameters);
	}

	protected static List<SpiraTestCaseRun> getSpiraTestCaseRuns(
		final int numberOfRows, final SpiraProject spiraProject,
		final SpiraTestCaseObject spiraTestCase,
		final SearchQuery.SearchParameter... searchParameters) {

		List<SearchQuery.SearchParameter> searchParameterList =
			new ArrayList<>();

		Collections.addAll(searchParameterList, searchParameters);

		if (spiraTestCase != null) {
			searchParameterList.add(
				new SearchQuery.SearchParameter(
					SpiraTestCaseObject.KEY_ID, spiraTestCase.getID()));
		}

		return getSpiraArtifacts(
			SpiraTestCaseRun.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraTestCaseRuns(
						numberOfRows, spiraProject, spiraTestCase,
						searchParameters);
				}

			},
			new Function<JSONObject, SpiraTestCaseRun>() {

				@Override
				public SpiraTestCaseRun apply(JSONObject jsonObject) {
					return new SpiraTestCaseRun(spiraProject, jsonObject);
				}

			},
			searchParameterList.toArray(new SearchQuery.SearchParameter[0]));
	}

	protected static final Integer ARTIFACT_TYPE_ID = 5;

	protected static final String ARTIFACT_TYPE_NAME = "testrun";

	protected static final String KEY_ID = "TestRunId";

	private static List<SpiraTestCaseRun> _recordSpiraTestCaseRuns(
		SpiraProject spiraProject, JSONArray requestJSONArray) {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/test-runs/record-multiple", null,
				urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			List<SpiraTestCaseRun> spiraTestCaseRuns = new ArrayList<>();

			for (int i = 0; i < responseJSONArray.length(); i++) {
				spiraTestCaseRuns.add(
					new SpiraTestCaseRun(
						spiraProject, responseJSONArray.getJSONObject(i)));
			}

			return spiraTestCaseRuns;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static List<JSONObject> _requestSpiraTestCaseRuns(
		int numberOfRows, SpiraProject spiraProject,
		SpiraTestCaseObject spiraTestCase,
		SearchQuery.SearchParameter... searchParameters) {

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(numberOfRows));
		urlParameters.put("sort_direction", "DESC");
		urlParameters.put("sort_field", KEY_ID);
		urlParameters.put("starting_row", String.valueOf(1));

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		if (spiraTestCase != null) {
			urlPathReplacements.put(
				"test_case_id", String.valueOf(spiraTestCase.getID()));
		}

		JSONArray requestJSONArray = new JSONArray();

		for (SearchQuery.SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		try {
			String baseRequestURL = "projects/{project_id}/test-runs/search";

			if (spiraTestCase != null) {
				baseRequestURL =
					"projects/{project_id}/test-cases/{test_case_id}" +
						"/test-runs/search";
			}

			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				baseRequestURL, urlParameters, urlPathReplacements,
				HttpRequestMethod.POST, requestJSONArray.toString());

			List<JSONObject> spiraTestCaseRuns = new ArrayList<>();

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.KEY_ID, spiraProject.getID());

				spiraTestCaseRuns.add(responseJSONObject);
			}

			return spiraTestCaseRuns;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraTestCaseRun(SpiraProject spiraProject, JSONObject jsonObject) {
		super(jsonObject);

		jsonObject.put(spiraProject.getKeyID(), spiraProject.getID());

		cacheSpiraArtifact(SpiraTestCaseRun.class, this);
	}

	private JSONObject _automatedJSONObject;

	private abstract static class IndexedCallable<T> implements Callable<T> {

		@Override
		public final T call() {
			try {
				return safeCall();
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}

			return null;
		}

		public abstract T safeCall();

		protected IndexedCallable(Integer index) {
			_index = index;
		}

		protected Integer getIndex() {
			return _index;
		}

		protected void print(String s) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"[thread_" + getIndex(), "] ", s));
		}

		private final Integer _index;

	}

}