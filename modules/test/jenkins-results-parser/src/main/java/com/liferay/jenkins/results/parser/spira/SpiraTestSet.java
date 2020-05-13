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
import java.util.Collections;
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
public class SpiraTestSet extends PathSpiraArtifact {

	public static SpiraTestSet createSpiraTestSet(
		SpiraProject spiraProject, String testSetName,
		String testSetDescription) {

		return _createSpiraTestSet(
			spiraProject, testSetName, testSetDescription, null);
	}

	public static SpiraTestSet createSpiraTestSet(
		SpiraProject spiraProject, String testSetName,
		String testSetDescription, Integer parentTestSetFolderID) {

		return _createSpiraTestSet(
			spiraProject, testSetName, testSetDescription,
			parentTestSetFolderID);
	}

	public static SpiraTestSet createSpiraTestSetByPath(
		SpiraProject spiraProject, String testSetPath,
		String testSetDescription) {

		List<SpiraTestSet> spiraTestSets = spiraProject.getSpiraTestSetsByPath(
			testSetPath);

		if (!spiraTestSets.isEmpty()) {
			return spiraTestSets.get(0);
		}

		String testSetName = getPathName(testSetPath);
		String parentTestSetFolderPath = getParentPath(testSetPath);

		if (parentTestSetFolderPath.isEmpty()) {
			return createSpiraTestSet(
				spiraProject, testSetName, testSetDescription);
		}

		SpiraTestSetFolder parentSpiraTestSetFolder =
			SpiraTestSetFolder.createSpiraTestSetFolderByPath(
				spiraProject, parentTestSetFolderPath);

		return createSpiraTestSet(
			spiraProject, testSetName, testSetDescription,
			parentSpiraTestSetFolder.getID());
	}

	public SpiraTestSetFolder getParentSpiraTestSetFolder() {
		if (_parentSpiraTestSetFolder != null) {
			return _parentSpiraTestSetFolder;
		}

		Object testSetFolderID = jsonObject.get(SpiraTestSetFolder.ID_KEY);

		if (testSetFolderID == JSONObject.NULL) {
			return null;
		}

		if (!(testSetFolderID instanceof Integer)) {
			return null;
		}

		SpiraProject spiraProject = getSpiraProject();

		_parentSpiraTestSetFolder = spiraProject.getSpiraTestSetFolderByID(
			(Integer)testSetFolderID);

		return _parentSpiraTestSetFolder;
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, String.valueOf(spiraProject.getID()), "/TestSet/",
			String.valueOf(getID()), ".aspx");
	}

	public static enum Status {

		BLOCKED(4), COMPLETED(3), DEFERRED(5), IN_PROGRESS(2), NOT_STARTED(1);

		public Integer getID() {
			return _id;
		}

		private Status(Integer id) {
			_id = id;
		}

		private final Integer _id;

	}

	public static enum TestRunType {

		AUTOMATED(2), MANUAL(1);

		public Integer getID() {
			return _id;
		}

		private TestRunType(Integer id) {
			_id = id;
		}

		private final Integer _id;

	}

	protected static List<SpiraTestSet> getSpiraTestSets(
		final SpiraProject spiraProject,
		final SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraTestSet.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraTestSets(
						spiraProject, searchParameters);
				}

			},
			new Function<JSONObject, SpiraTestSet>() {

				@Override
				public SpiraTestSet apply(JSONObject jsonObject) {
					return new SpiraTestSet(jsonObject);
				}

			},
			searchParameters);
	}

	protected SpiraTestSetTestCase assignSpiraTestCase(
		SpiraTestCaseObject spiraTestCase) {

		if (_spiraTestSetTestCases.containsKey(spiraTestCase.getID())) {
			return _spiraTestSetTestCases.get(spiraTestCase.getID());
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		SpiraProject spiraProject = getSpiraProject();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		urlPathReplacements.put(
			"test_case_id", String.valueOf(spiraTestCase.getID()));

		urlPathReplacements.put("test_set_id", String.valueOf(getID()));

		JSONArray requestJSONArray = new JSONArray();

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/test-sets/{test_set_id}" +
					"/test-case-mapping/{test_case_id}",
				null, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			for (int i = 0; i < responseJSONArray.length(); i++) {
				SpiraTestSetTestCase spiraTestSetTestCase =
					new SpiraTestSetTestCase(
						responseJSONArray.getJSONObject(i));

				_spiraTestSetTestCases.put(
					spiraTestSetTestCase.getTestCaseID(), spiraTestSetTestCase);

				return spiraTestSetTestCase;
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return null;
	}

	@Override
	protected PathSpiraArtifact getParentSpiraArtifact() {
		return getParentSpiraTestSetFolder();
	}

	protected static final String ARTIFACT_TYPE_NAME = "testset";

	protected static final String ID_KEY = "TestSetId";

	protected static class SpiraTestSetTestCase extends BaseSpiraArtifact {

		@Override
		public String getURL() {
			SpiraTestCaseObject spiraTestCase = getSpiraTestCase();

			return spiraTestCase.getURL();
		}

		protected SpiraTestCaseObject getSpiraTestCase() {
			SpiraProject spiraProject = getSpiraProject();

			return spiraProject.getSpiraTestCaseByID(getTestCaseID());
		}

		protected Integer getTestCaseID() {
			return jsonObject.getInt(SpiraTestCaseObject.ID_KEY);
		}

		protected static final String ID_KEY = "TestSetTestCaseId";

		private SpiraTestSetTestCase(JSONObject jsonObject) {
			super(jsonObject);
		}

	}

	private static SpiraTestSet _createSpiraTestSet(
		SpiraProject spiraProject, String testSetName,
		String testSetDescription, Integer parentTestSetFolderID) {

		List<SpiraTestSet> spiraTestSets = getSpiraTestSets(
			spiraProject, new SearchQuery.SearchParameter("Name", testSetName),
			new SearchQuery.SearchParameter(
				"TestCaseFolderId", parentTestSetFolderID));

		if (!spiraTestSets.isEmpty()) {
			return spiraTestSets.get(0);
		}

		String urlPath = "projects/{project_id}/test-sets";

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"Name", StringEscapeUtils.unescapeJava(testSetName));
		requestJSONObject.put("TestRunTypeId", TestRunType.AUTOMATED.getID());
		requestJSONObject.put("TestSetStatusId", Status.IN_PROGRESS.getID());

		if ((parentTestSetFolderID != null) && (parentTestSetFolderID != 0)) {
			requestJSONObject.put(
				SpiraTestSetFolder.ID_KEY, parentTestSetFolderID);
		}

		if (testSetDescription == null) {
			testSetDescription = "";
		}

		requestJSONObject.put(
			"Description", StringEscapeUtils.unescapeJava(testSetDescription));

		try {
			JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
				urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONObject.toString());

			return spiraProject.getSpiraTestSetByID(
				responseJSONObject.getInt(ID_KEY));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static List<JSONObject> _requestSpiraTestSets(
		SpiraProject spiraProject,
		SearchQuery.SearchParameter... searchParameters) {

		List<JSONObject> spiraTestSetFolders = new ArrayList<>();

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(15000));
		urlParameters.put("release_id", null);
		urlParameters.put("sort_direction", "ASC");
		urlParameters.put("sort_field", ID_KEY);
		urlParameters.put("starting_row", String.valueOf(1));

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchQuery.SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/test-sets/search", urlParameters,
				urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.ID_KEY, spiraProject.getID());

				spiraTestSetFolders.add(responseJSONObject);
			}

			return spiraTestSetFolders;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraTestSet(JSONObject jsonObject) {
		super(jsonObject);

		Map<String, String> urlPathReplacements = new HashMap<>();

		SpiraProject spiraProject = getSpiraProject();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		urlPathReplacements.put("test_set_id", String.valueOf(getID()));

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/test-sets/{test_set_id}/test-cases",
				null, urlPathReplacements, HttpRequestMethod.GET, null);

			for (int i = 0; i < responseJSONArray.length(); i++) {
				SpiraTestSetTestCase spiraTestSetTestCase =
					new SpiraTestSetTestCase(
						responseJSONArray.getJSONObject(i));

				_spiraTestSetTestCases.put(
					spiraTestSetTestCase.getTestCaseID(), spiraTestSetTestCase);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		cacheSpiraArtifact(SpiraTestSet.class, this);
	}

	private SpiraTestSetFolder _parentSpiraTestSetFolder;
	private Map<Integer, SpiraTestSetTestCase> _spiraTestSetTestCases =
		Collections.synchronizedMap(
			new HashMap<Integer, SpiraTestSetTestCase>());

}