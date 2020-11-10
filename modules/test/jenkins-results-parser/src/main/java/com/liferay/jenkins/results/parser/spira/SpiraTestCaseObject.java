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

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang.StringEscapeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestCaseObject extends PathSpiraArtifact {

	public static void clearCachedSpiraTestCaseObjects() {
		clearCachedSpiraArtifacts(SpiraTestCaseObject.class);
	}

	public static SpiraTestCaseObject createSpiraTestCaseObject(
		SpiraProject spiraProject, String testCaseName, String testCaseFilePath,
		SpiraTestCaseType spiraTestCaseType) {

		return _createSpiraTestCaseObject(
			spiraProject, testCaseName, testCaseFilePath, spiraTestCaseType,
			null, true);
	}

	public static SpiraTestCaseObject createSpiraTestCaseObject(
		SpiraProject spiraProject, String testCaseName, String testCaseFilePath,
		SpiraTestCaseType spiraTestCaseType, Integer parentTestCaseFolderID) {

		return _createSpiraTestCaseObject(
			spiraProject, testCaseName, testCaseFilePath, spiraTestCaseType,
			parentTestCaseFolderID, true);
	}

	public static SpiraTestCaseObject createSpiraTestCaseObjectByPath(
		SpiraProject spiraProject, String testCasePath, String testCaseFilePath,
		SpiraTestCaseType spiraTestCaseType) {

		List<SpiraTestCaseObject> spiraTestCaseObjects =
			spiraProject.getSpiraTestCaseObjectsByPath(testCasePath);

		if (!spiraTestCaseObjects.isEmpty()) {
			return spiraTestCaseObjects.get(0);
		}

		String testCaseName = getPathName(testCasePath);
		String parentTestCaseFolderPath = getParentPath(testCasePath);

		if (parentTestCaseFolderPath.isEmpty()) {
			return createSpiraTestCaseObject(
				spiraProject, testCaseName, testCaseFilePath,
				spiraTestCaseType);
		}

		SpiraTestCaseFolder parentSpiraTestCaseFolder =
			SpiraTestCaseFolder.createSpiraTestCaseFolderByPath(
				spiraProject, parentTestCaseFolderPath);

		return _createSpiraTestCaseObject(
			spiraProject, testCaseName, testCaseFilePath, spiraTestCaseType,
			parentSpiraTestCaseFolder.getID(), false);
	}

	public static void deleteSpiraTestCaseObjectByID(
		SpiraProject spiraProject, int testCaseID) {

		List<SpiraTestCaseObject> spiraTestCaseObjects =
			getSpiraTestCaseObjects(
				spiraProject,
				new SearchQuery.SearchParameter(KEY_ID, testCaseID));

		if (spiraTestCaseObjects.isEmpty()) {
			return;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put("test_case_id", String.valueOf(testCaseID));

		try {
			SpiraRestAPIUtil.request(
				"projects/{project_id}/test-cases/{test_case_id}", null,
				urlPathReplacements, HttpRequestMethod.DELETE, null);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		removeCachedSpiraArtifacts(
			SpiraTestCaseObject.class, spiraTestCaseObjects);
	}

	public static void deleteSpiraTestCaseObjectsByPath(
		SpiraProject spiraProject, String testCasePath) {

		List<SpiraTestCaseObject> spiraTestCaseObjects =
			spiraProject.getSpiraTestCaseObjectsByPath(testCasePath);

		for (SpiraTestCaseObject spiraTestCaseObject : spiraTestCaseObjects) {
			deleteSpiraTestCaseObjectByID(
				spiraProject, spiraTestCaseObject.getID());
		}
	}

	public String getFilePath() {
		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				getSpiraProject(), SpiraTestCaseObject.class,
				_CUSTOM_FIELD_FILE_PATH_KEY, SpiraCustomProperty.Type.TEXT);

		JSONArray customPropertiesJSONArray = jsonObject.getJSONArray(
			"CustomProperties");

		for (int i = 0; i < customPropertiesJSONArray.length(); i++) {
			JSONObject customPropertyJSONObject =
				customPropertiesJSONArray.getJSONObject(i);

			int propertyNumber = customPropertyJSONObject.getInt(
				"PropertyNumber");

			if (propertyNumber == spiraCustomProperty.getPropertyNumber()) {
				return customPropertyJSONObject.optString("StringValue");
			}
		}

		return null;
	}

	public SpiraTestCaseFolder getParentSpiraTestCaseFolder() {
		if (_parentSpiraTestCaseFolder != null) {
			return _parentSpiraTestCaseFolder;
		}

		Object testCaseFolderID = jsonObject.get(SpiraTestCaseFolder.KEY_ID);

		if (testCaseFolderID == JSONObject.NULL) {
			return null;
		}

		if (!(testCaseFolderID instanceof Integer)) {
			return null;
		}

		SpiraProject spiraProject = getSpiraProject();

		_parentSpiraTestCaseFolder = spiraProject.getSpiraTestCaseFolderByID(
			(Integer)testCaseFolderID);

		return _parentSpiraTestCaseFolder;
	}

	public SpiraTestCaseRun getSpiraTestCaseRunByID(int testCaseRunID) {
		List<SpiraTestCaseRun> spiraTestCaseRuns =
			SpiraTestCaseRun.getSpiraTestCaseRuns(
				getSpiraProject(), this,
				new SearchQuery.SearchParameter(
					SpiraTestCaseRun.KEY_ID, testCaseRunID));

		if (spiraTestCaseRuns.size() > 1) {
			throw new RuntimeException(
				"Duplicate test case run ID " + testCaseRunID);
		}

		if (spiraTestCaseRuns.isEmpty()) {
			throw new RuntimeException(
				"Missing test case run ID " + testCaseRunID);
		}

		return spiraTestCaseRuns.get(0);
	}

	public List<SpiraTestCaseRun> getSpiraTestCaseRuns() {
		return SpiraTestCaseRun.getSpiraTestCaseRuns(getSpiraProject(), this);
	}

	public SpiraTestCaseType getSpiraTestCaseType() {
		SpiraProject spiraProject = getSpiraProject();

		return spiraProject.getSpiraTestCaseTypeByID(
			jsonObject.getInt("TestCaseTypeId"));
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, String.valueOf(spiraProject.getID()), "/TestCase/",
			String.valueOf(getID()), ".aspx");
	}

	public static enum Status {

		APPROVED(4), DRAFT(1), OBSOLETE(9), READY_FOR_REVIEW(2),
		READY_FOR_TEST(5), REJECTED(3), TESTED(7), VERIFIED(8);

		public Integer getID() {
			return _id;
		}

		private Status(Integer id) {
			_id = id;
		}

		private final Integer _id;

	}

	protected static List<SpiraTestCaseObject> getSpiraTestCaseObjects(
		final SpiraProject spiraProject, final long numberOfRows,
		final SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraTestCaseObject.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraTestCases(
						spiraProject, numberOfRows, searchParameters);
				}

			},
			new Function<JSONObject, SpiraTestCaseObject>() {

				@Override
				public SpiraTestCaseObject apply(JSONObject jsonObject) {
					return new SpiraTestCaseObject(jsonObject);
				}

			},
			searchParameters);
	}

	protected static List<SpiraTestCaseObject> getSpiraTestCaseObjects(
		final SpiraProject spiraProject,
		final SearchQuery.SearchParameter... searchParameters) {

		return getSpiraTestCaseObjects(spiraProject, 35000, searchParameters);
	}

	@Override
	protected PathSpiraArtifact getParentSpiraArtifact() {
		return getParentSpiraTestCaseFolder();
	}

	protected static final Integer ARTIFACT_TYPE_ID = 2;

	protected static final String ARTIFACT_TYPE_NAME = "testcase";

	protected static final String KEY_ID = "TestCaseId";

	private static SpiraTestCaseObject _createSpiraTestCaseObject(
		SpiraProject spiraProject, String testCaseName, String testCaseFilePath,
		SpiraTestCaseType spiraTestCaseType, Integer parentTestCaseFolderID,
		boolean checkCache) {

		SpiraCustomProperty filePathSpiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraProject, SpiraTestCaseObject.class,
				_CUSTOM_FIELD_FILE_PATH_KEY, SpiraCustomProperty.Type.TEXT);

		if (checkCache) {
			List<SearchQuery.SearchParameter> searchParameterList =
				new ArrayList<>();

			searchParameterList.add(
				new SearchQuery.SearchParameter("Name", testCaseName));

			if ((parentTestCaseFolderID != null) &&
				(parentTestCaseFolderID != 0)) {

				searchParameterList.add(
					new SearchQuery.SearchParameter(
						"TestCaseFolderId", parentTestCaseFolderID));
			}

			if ((testCaseFilePath != null) && !testCaseFilePath.isEmpty()) {
				SpiraCustomPropertyValue spiraCustomPropertyValue =
					SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
						filePathSpiraCustomProperty, testCaseFilePath);

				searchParameterList.add(
					new SearchQuery.SearchParameter(spiraCustomPropertyValue));
			}

			List<SpiraTestCaseObject> spiraTestCaseObjects =
				getSpiraTestCaseObjects(
					spiraProject,
					searchParameterList.toArray(
						new SearchQuery.SearchParameter[0]));

			if (!spiraTestCaseObjects.isEmpty()) {
				return spiraTestCaseObjects.get(0);
			}
		}

		String urlPath = "projects/{project_id}/test-cases";

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"Name", StringEscapeUtils.unescapeJava(testCaseName));
		requestJSONObject.put("TestCaseStatusId", Status.DRAFT.getID());

		if ((parentTestCaseFolderID != null) && (parentTestCaseFolderID != 0)) {
			requestJSONObject.put(
				SpiraTestCaseFolder.KEY_ID, parentTestCaseFolderID);
		}

		if (spiraTestCaseType != null) {
			requestJSONObject.put("TestCaseTypeId", spiraTestCaseType.getID());
		}

		JSONArray customPropertiesJSONArray = new JSONArray();

		if ((testCaseFilePath != null) && !testCaseFilePath.isEmpty()) {
			JSONObject filePathJSONObject = new JSONObject();

			filePathJSONObject.put(
				"PropertyNumber",
				filePathSpiraCustomProperty.getPropertyNumber());
			filePathJSONObject.put("StringValue", testCaseFilePath);

			customPropertiesJSONArray.put(filePathJSONObject);
		}

		SpiraCustomProperty executionTypeSpiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraProject, SpiraTestCaseObject.class,
				_CUSTOM_FIELD_EXECUTION_TYPE_KEY,
				SpiraCustomProperty.Type.LIST);

		JSONObject executionTypeJSONObject = new JSONObject();

		executionTypeJSONObject.put(
			"PropertyNumber",
			executionTypeSpiraCustomProperty.getPropertyNumber());

		SpiraCustomPropertyValue spiraCustomPropertyValue =
			SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
				executionTypeSpiraCustomProperty, "Automatic");

		executionTypeJSONObject.put(
			"IntegerValue", spiraCustomPropertyValue.getID());

		customPropertiesJSONArray.put(executionTypeJSONObject);

		requestJSONObject.put("CustomProperties", customPropertiesJSONArray);

		try {
			JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
				urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONObject.toString());

			return spiraProject.getSpiraTestCaseObjectByID(
				responseJSONObject.getInt(KEY_ID));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static List<JSONObject> _requestSpiraTestCases(
		SpiraProject spiraProject, long numberOfRows,
		SearchQuery.SearchParameter... searchParameters) {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(numberOfRows));
		urlParameters.put("starting_row", String.valueOf(1));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchQuery.SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/test-cases/search", urlParameters,
				urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			List<JSONObject> spiraTestCases = new ArrayList<>();

			for (int i = 0; i < responseJSONArray.length(); i++) {
				spiraTestCases.add(responseJSONArray.getJSONObject(i));
			}

			return spiraTestCases;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraTestCaseObject(JSONObject jsonObject) {
		super(jsonObject);

		cacheSpiraArtifact(SpiraTestCaseObject.class, this);
	}

	private static final String _CUSTOM_FIELD_EXECUTION_TYPE_KEY =
		"Execution Type";

	private static final String _CUSTOM_FIELD_FILE_PATH_KEY = "File Path";

	private SpiraTestCaseFolder _parentSpiraTestCaseFolder;

}