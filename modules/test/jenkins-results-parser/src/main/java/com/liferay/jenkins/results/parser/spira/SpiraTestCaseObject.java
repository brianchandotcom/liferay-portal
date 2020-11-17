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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

	public static SpiraTestCaseObject createSpiraTestCaseObjectByPath(
		SpiraProject spiraProject, String testCasePath,
		SpiraTestCaseType spiraTestCaseType,
		List<SpiraCustomPropertyValue> spiraCustomPropertyValues) {

		Set<SpiraCustomPropertyValue> spiraCustomPropertyValueSet =
			new HashSet<>();

		if (spiraCustomPropertyValues != null) {
			spiraCustomPropertyValueSet.addAll(spiraCustomPropertyValues);
		}

		spiraCustomPropertyValueSet.add(
			_getExecutionTypeSpiraCustomPropertyValue(spiraProject));

		List<SpiraTestCaseObject> spiraTestCaseObjects =
			_getSpiraTestCaseObjects(
				spiraProject, testCasePath, spiraTestCaseType,
				new ArrayList<>(spiraCustomPropertyValueSet));

		if (!spiraTestCaseObjects.isEmpty()) {
			return spiraTestCaseObjects.get(0);
		}

		String urlPath = "projects/{project_id}/test-cases";

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"Name", StringEscapeUtils.unescapeJava(getPathName(testCasePath)));
		requestJSONObject.put("TestCaseStatusId", Status.DRAFT.getID());

		SpiraTestCaseFolder parentSpiraTestCaseFolder = null;

		String parentTestCaseFolderPath = getParentPath(testCasePath);

		if ((parentTestCaseFolderPath != null) &&
			!parentTestCaseFolderPath.isEmpty()) {

			parentSpiraTestCaseFolder =
				SpiraTestCaseFolder.createSpiraTestCaseFolderByPath(
					spiraProject, parentTestCaseFolderPath);
		}

		if (parentSpiraTestCaseFolder != null) {
			requestJSONObject.put(
				SpiraTestCaseFolder.KEY_ID, parentSpiraTestCaseFolder.getID());
		}

		if (spiraTestCaseType != null) {
			requestJSONObject.put("TestCaseTypeId", spiraTestCaseType.getID());
		}

		JSONArray customPropertiesJSONArray = new JSONArray();

		for (SpiraCustomPropertyValue spiraCustomPropertyValue :
				spiraCustomPropertyValueSet) {

			customPropertiesJSONArray.put(
				spiraCustomPropertyValue.getCustomPropertyJSONObject());
		}

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

	public static SpiraTestCaseObject createSpiraTestCaseObjectByPath(
		SpiraProject spiraProject, String testCasePath, String testCaseFilePath,
		SpiraTestCaseType spiraTestCaseType) {

		List<SpiraCustomPropertyValue> spiraCustomPropertyValues =
			new ArrayList<>();

		if ((testCaseFilePath != null) && !testCaseFilePath.isEmpty()) {
			SpiraCustomProperty filePathSpiraCustomProperty =
				SpiraCustomProperty.createSpiraCustomProperty(
					spiraProject, SpiraTestCaseObject.class,
					_CUSTOM_FIELD_FILE_PATH_KEY, SpiraCustomProperty.Type.TEXT);

			spiraCustomPropertyValues.add(
				SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					filePathSpiraCustomProperty, testCaseFilePath));
		}

		return createSpiraTestCaseObjectByPath(
			spiraProject, testCasePath, spiraTestCaseType,
			spiraCustomPropertyValues);
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
			getSpiraTestCaseObjects(
				spiraProject,
				new SearchQuery.SearchParameter("Path", testCasePath));

		for (SpiraTestCaseObject spiraTestCaseObject : spiraTestCaseObjects) {
			deleteSpiraTestCaseObjectByID(
				spiraProject, spiraTestCaseObject.getID());
		}
	}

	public String getFilePath() {
		SpiraCustomPropertyValue spiraCustomPropertyValue =
			getSpiraCustomPropertyValue(_CUSTOM_FIELD_FILE_PATH_KEY);

		if (spiraCustomPropertyValue != null) {
			return spiraCustomPropertyValue.getValueString();
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

	public SpiraTestCaseProductVersion getSpiraTestCaseProductVersion() {
		if (_spiraTestCaseProductVersion != null) {
			return _spiraTestCaseProductVersion;
		}

		SpiraCustomPropertyValue spiraCustomPropertyValue =
			getSpiraCustomPropertyValue(
				SpiraTestCaseProductVersion.CUSTOM_PROPERTY_NAME);

		if (spiraCustomPropertyValue == null) {
			return null;
		}

		if (spiraCustomPropertyValue instanceof SpiraTestCaseProductVersion) {
			_spiraTestCaseProductVersion =
				(SpiraTestCaseProductVersion)spiraCustomPropertyValue;

			return _spiraTestCaseProductVersion;
		}

		return null;
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

	private static SpiraCustomPropertyValue
		_getExecutionTypeSpiraCustomPropertyValue(SpiraProject spiraProject) {

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				spiraProject, SpiraTestCaseObject.class,
				_CUSTOM_FIELD_EXECUTION_TYPE_KEY,
				SpiraCustomProperty.Type.LIST);

		return SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
			spiraCustomProperty, "Automatic");
	}

	private static List<SpiraTestCaseObject> _getSpiraTestCaseObjects(
		SpiraProject spiraProject, String testCasePath,
		SpiraTestCaseType spiraTestCaseType,
		List<SpiraCustomPropertyValue> spiraCustomPropertyValues) {

		List<SearchQuery.SearchParameter> searchParameters = new ArrayList<>();

		searchParameters.add(
			new SearchQuery.SearchParameter("Path", testCasePath));

		String parentTestCaseFolderPath = getParentPath(testCasePath);

		SpiraTestCaseFolder parentSpiraTestCaseFolder = null;

		if ((parentTestCaseFolderPath != null) &&
			!parentTestCaseFolderPath.isEmpty()) {

			parentSpiraTestCaseFolder =
				SpiraTestCaseFolder.createSpiraTestCaseFolderByPath(
					spiraProject, parentTestCaseFolderPath);

			searchParameters.add(
				new SearchQuery.SearchParameter(
					"TestCaseFolderId", parentSpiraTestCaseFolder.getID()));
		}

		if (spiraTestCaseType != null) {
			searchParameters.add(
				new SearchQuery.SearchParameter(
					"TestCaseTypeId", spiraTestCaseType.getID()));
		}

		Set<SpiraCustomPropertyValue> spiraCustomPropertyValueSet =
			new HashSet<>();

		if (spiraCustomPropertyValues != null) {
			spiraCustomPropertyValueSet.addAll(spiraCustomPropertyValues);
		}

		spiraCustomPropertyValueSet.add(
			_getExecutionTypeSpiraCustomPropertyValue(spiraProject));

		for (SpiraCustomPropertyValue spiraCustomPropertyValue :
				spiraCustomPropertyValueSet) {

			searchParameters.add(
				new SearchQuery.SearchParameter(spiraCustomPropertyValue));
		}

		return getSpiraTestCaseObjects(
			spiraProject,
			searchParameters.toArray(new SearchQuery.SearchParameter[0]));
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

		List<String> searchParameterNames = new ArrayList<>();

		for (SearchQuery.SearchParameter searchParameter : searchParameters) {
			String searchParameterName = searchParameter.getName();

			if (searchParameterNames.contains(searchParameterName)) {
				continue;
			}

			searchParameterNames.add(searchParameterName);

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
	private SpiraTestCaseProductVersion _spiraTestCaseProductVersion;

}