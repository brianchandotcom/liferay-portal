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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestSet extends PathSpiraArtifact {

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

	@Override
	protected PathSpiraArtifact getParentSpiraArtifact() {
		return getParentSpiraTestSetFolder();
	}

	protected static final String ARTIFACT_TYPE_NAME = "testset";

	protected static final String ID_KEY = "TestSetId";

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

		System.out.println(requestJSONArray);

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
	}

	private SpiraTestSetFolder _parentSpiraTestSetFolder;

}