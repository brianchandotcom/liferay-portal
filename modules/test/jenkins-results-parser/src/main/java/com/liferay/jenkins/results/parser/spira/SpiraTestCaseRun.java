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

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestCaseRun extends BaseSpiraArtifact {

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

			SpiraTestCaseObject spiraTestCase = result.getSpiraTestCase();

			requestJSONObject.put(
				SpiraTestCaseObject.ID_KEY, spiraTestCase.getID());

			requestJSONObject.put(
				"CustomProperties", result.getCustomPropertyValuesJSONArray());
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
				requestJSONObject.put(SpiraRelease.ID_KEY, releaseID);
			}

			if (releaseBuildID != null) {
				requestJSONObject.put(SpiraReleaseBuild.ID_KEY, releaseBuildID);
			}

			if (testSetID != null) {
				requestJSONObject.put(SpiraTestSet.ID_KEY, testSetID);

				SpiraTestSet.SpiraTestSetTestCase spiraTestSetTestCase =
					spiraTestSet.assignSpiraTestCase(spiraTestCase);

				requestJSONObject.put(
					SpiraTestSet.SpiraTestSetTestCase.ID_KEY,
					spiraTestSetTestCase.getID());
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
							result.initSpiraTestCase();
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

	public static class Result {

		public Result(
			SpiraTestCaseObject spiraTestCase, RunnerFormat runnerFormat,
			String runnerStackTrace, Status status,
			List<SpiraCustomProperty.Value> spiraCustomPropertyValues) {

			_spiraTestCase = spiraTestCase;
			_runnerFormat = runnerFormat;
			_description = runnerStackTrace;
			_status = status;
			_spiraCustomPropertyValues = spiraCustomPropertyValues;
		}

		public Result(
			Supplier<SpiraTestCaseObject> testCaseSupplier,
			RunnerFormat runnerFormat, String runnerStackTrace, Status status,
			List<SpiraCustomProperty.Value> spiraCustomPropertyValues) {

			_spiraTestCaseSupplier = testCaseSupplier;
			_runnerFormat = runnerFormat;
			_description = runnerStackTrace;
			_status = status;
			_spiraCustomPropertyValues = spiraCustomPropertyValues;
		}

		public JSONArray getCustomPropertyValuesJSONArray() {
			JSONArray jsonArray = new JSONArray();

			if (_spiraCustomPropertyValues == null) {
				return jsonArray;
			}

			for (SpiraCustomProperty.Value spiraCustomPropertyValue :
					_spiraCustomPropertyValues) {

				SpiraCustomProperty spiraCustomProperty =
					spiraCustomPropertyValue.getSpiraCustomProperty();

				JSONObject customListValuesJSONObject = new JSONObject();

				int propertyNumber = spiraCustomProperty.getPropertyNumber();

				customListValuesJSONObject.put(
					"PropertyNumber", propertyNumber);

				SpiraCustomProperty.Type type = spiraCustomProperty.getType();

				if (type == SpiraCustomProperty.Type.LIST) {
					SpiraCustomList spiraCustomList =
						SpiraCustomList.getSpiraCustomListByName(
							getSpiraProject(), SpiraTestCaseRun.class,
							spiraCustomProperty.getName());

					SpiraCustomList.Value spiraCustomListValue =
						spiraCustomList.getSpiraCustomListValueByName(
							spiraCustomPropertyValue.getValue());

					customListValuesJSONObject.put(
						"IntegerValue", spiraCustomListValue.getID());

					jsonArray.put(customListValuesJSONObject);
				}
				else if (type == SpiraCustomProperty.Type.MULTILIST) {
					JSONArray integerListValueJSONArray = null;

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);

						int jsonObjectPropertyNumber = jsonObject.optInt(
							"PropertyNumber", -1);

						if (jsonObjectPropertyNumber == -1) {
							continue;
						}

						if (propertyNumber != jsonObjectPropertyNumber) {
							continue;
						}

						integerListValueJSONArray = jsonObject.getJSONArray(
							"IntegerListValue");

						break;
					}

					if (integerListValueJSONArray == null) {
						integerListValueJSONArray = new JSONArray();

						jsonArray.put(customListValuesJSONObject);
					}

					SpiraCustomList spiraCustomList =
						SpiraCustomList.getSpiraCustomListByName(
							getSpiraProject(), SpiraTestCaseRun.class,
							spiraCustomProperty.getName());

					SpiraCustomList.Value spiraCustomListValue =
						spiraCustomList.getSpiraCustomListValueByName(
							spiraCustomPropertyValue.getValue());

					integerListValueJSONArray.put(spiraCustomListValue.getID());

					customListValuesJSONObject.put(
						"IntegerListValue", integerListValueJSONArray);
				}
				else if (type == SpiraCustomProperty.Type.TEXT) {
					customListValuesJSONObject.put(
						"StringValue", spiraCustomPropertyValue.getValue());

					jsonArray.put(customListValuesJSONObject);
				}
				else {
					throw new RuntimeException(
						"Unsupported custom property " + type);
				}
			}

			System.out.println(jsonArray);

			return jsonArray;
		}

		public String getDescription() {
			return _description;
		}

		public Integer getRunnerFormatID() {
			return _runnerFormat.getID();
		}

		public SpiraProject getSpiraProject() {
			SpiraTestCaseObject spiraTestCase = getSpiraTestCase();

			return spiraTestCase.getSpiraProject();
		}

		public SpiraTestCaseObject getSpiraTestCase() {
			initSpiraTestCase();

			return _spiraTestCase;
		}

		public Integer getStatusID() {
			return _status.getID();
		}

		public void initSpiraTestCase() {
			if (_spiraTestCase != null) {
				return;
			}

			if (_spiraTestCaseSupplier == null) {
				return;
			}

			_spiraTestCase = _spiraTestCaseSupplier.get();
		}

		private final String _description;
		private final RunnerFormat _runnerFormat;
		private final List<SpiraCustomProperty.Value>
			_spiraCustomPropertyValues;
		private SpiraTestCaseObject _spiraTestCase;
		private Supplier<SpiraTestCaseObject> _spiraTestCaseSupplier;
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

		cacheSpiraArtifact(SpiraTestCaseRun.class, this);
	}

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