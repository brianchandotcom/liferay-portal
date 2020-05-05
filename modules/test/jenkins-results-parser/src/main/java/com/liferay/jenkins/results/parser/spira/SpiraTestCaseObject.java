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
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang.StringEscapeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestCaseObject extends PathSpiraArtifact {

	public static SpiraTestCaseObject createSpiraTestCase(
		SpiraProject spiraProject, String testCaseName, String testCaseFilePath,
		SpiraTestCaseType spiraTestCaseType) {

		return _createSpiraTestCase(
			spiraProject, testCaseName, testCaseFilePath, spiraTestCaseType,
			null, true);
	}

	public static SpiraTestCaseObject createSpiraTestCase(
		SpiraProject spiraProject, String testCaseName, String testCaseFilePath,
		SpiraTestCaseType spiraTestCaseType, Integer parentTestCaseFolderID) {

		return _createSpiraTestCase(
			spiraProject, testCaseName, testCaseFilePath, spiraTestCaseType,
			parentTestCaseFolderID, true);
	}

	public static SpiraTestCaseObject createSpiraTestCaseByPath(
		SpiraProject spiraProject, String testCasePath, String testCaseFilePath,
		SpiraTestCaseType spiraTestCaseType) {

		List<SpiraTestCaseObject> spiraTestCases =
			spiraProject.getSpiraTestCasesByPath(testCasePath);

		if (!spiraTestCases.isEmpty()) {
			return spiraTestCases.get(0);
		}

		String testCaseName = getPathName(testCasePath);
		String parentTestCaseFolderPath = getParentPath(testCasePath);

		if (parentTestCaseFolderPath.isEmpty()) {
			return createSpiraTestCase(
				spiraProject, testCaseName, testCaseFilePath,
				spiraTestCaseType);
		}

		SpiraTestCaseFolder parentSpiraTestCaseFolder =
			SpiraTestCaseFolder.createSpiraTestCaseFolderByPath(
				spiraProject, parentTestCaseFolderPath);

		return _createSpiraTestCase(
			spiraProject, testCaseName, testCaseFilePath, spiraTestCaseType,
			parentSpiraTestCaseFolder.getID(), false);
	}

	public static void deleteSpiraTestCaseByID(
		SpiraProject spiraProject, int testCaseID) {

		List<SpiraTestCaseObject> spiraTestCases = getSpiraTestCases(
			spiraProject, new SearchQuery.SearchParameter(ID_KEY, testCaseID));

		if (spiraTestCases.isEmpty()) {
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

		removeCachedSpiraArtifacts(SpiraTestCaseObject.class, spiraTestCases);
	}

	public static void deleteSpiraTestCasesByPath(
		SpiraProject spiraProject, String testCasePath) {

		List<SpiraTestCaseObject> spiraTestCases =
			spiraProject.getSpiraTestCasesByPath(testCasePath);

		for (SpiraTestCaseObject spiraTestCase : spiraTestCases) {
			deleteSpiraTestCaseByID(spiraProject, spiraTestCase.getID());
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

		Object testCaseFolderID = jsonObject.get(SpiraTestCaseFolder.ID_KEY);

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

	public List<SpiraTestCaseRun> getSpiraTestCaseRuns() {
		return SpiraTestCaseRun.getSpiraTestCaseRuns(getSpiraProject(), this);
	}

	public SpiraTestCaseType getSpiraTestCaseType() {
		SpiraProject spiraProject = getSpiraProject();

		return spiraProject.getSpiraTestCaseTypeByID(
			jsonObject.getInt("TestCaseTypeId"));
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

	protected static List<SpiraTestCaseObject> getSpiraTestCases(
		final SpiraProject spiraProject,
		final SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraTestCaseObject.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraTestCases(
						spiraProject, searchParameters);
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

	@Override
	protected PathSpiraArtifact getParentSpiraArtifact() {
		return getParentSpiraTestCaseFolder();
	}

	protected static final Integer ARTIFACT_TYPE_ID = 2;

	protected static final String ARTIFACT_TYPE_NAME = "testcase";

	protected static final String ID_KEY = "TestCaseId";

	private static SpiraTestCaseObject _createSpiraTestCase(
		SpiraProject spiraProject, String testCaseName, String testCaseFilePath,
		SpiraTestCaseType spiraTestCaseType, Integer parentTestCaseFolderID,
		boolean checkCache) {

		SpiraCustomProperty spiraCustomProperty =
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
				SpiraCustomProperty.Value spiraCustomValue =
					SpiraCustomProperty.createSpiraCustomPropertyValue(
						spiraCustomProperty, testCaseFilePath);

				searchParameterList.add(
					new SearchQuery.SearchParameter(spiraCustomValue));
			}

			List<SpiraTestCaseObject> spiraTestCases = getSpiraTestCases(
				spiraProject,
				searchParameterList.toArray(
					new SearchQuery.SearchParameter[0]));

			if (!spiraTestCases.isEmpty()) {
				return spiraTestCases.get(0);
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
				SpiraTestCaseFolder.ID_KEY, parentTestCaseFolderID);
		}

		if (spiraTestCaseType != null) {
			requestJSONObject.put("TestCaseTypeId", spiraTestCaseType.getID());
		}

		if ((testCaseFilePath != null) && !testCaseFilePath.isEmpty()) {
			JSONArray customPropertiesJSONArray = new JSONArray();

			JSONObject filePathJSONObject = new JSONObject();

			filePathJSONObject.put(
				"PropertyNumber", spiraCustomProperty.getPropertyNumber());
			filePathJSONObject.put("StringValue", testCaseFilePath);

			customPropertiesJSONArray.put(filePathJSONObject);

			requestJSONObject.put(
				"CustomProperties", customPropertiesJSONArray);
		}

		try {
			JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
				urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONObject.toString());

			return spiraProject.getSpiraTestCaseByID(
				responseJSONObject.getInt(ID_KEY));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static List<JSONObject> _requestSpiraTestCases(
		SpiraProject spiraProject,
		SearchQuery.SearchParameter... searchParameters) {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(15000));
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

	private static final String _CUSTOM_FIELD_FILE_PATH_KEY = "File Path";

	private SpiraTestCaseFolder _parentSpiraTestCaseFolder;

}