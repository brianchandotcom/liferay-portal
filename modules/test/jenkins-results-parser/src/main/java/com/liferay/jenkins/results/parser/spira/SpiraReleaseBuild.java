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
public class SpiraReleaseBuild extends BaseSpiraArtifact {

	public static SpiraReleaseBuild createSpiraReleaseBuild(
		SpiraProject spiraProject, SpiraRelease spiraRelease,
		String releaseBuildName) {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"release_id", String.valueOf(spiraRelease.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(SpiraProject.ID_KEY, spiraProject.getID());
		requestJSONObject.put(SpiraRelease.ID_KEY, spiraRelease.getID());
		requestJSONObject.put("BuildStatusId", STATUS_SUCCEEDED);
		requestJSONObject.put("Name", releaseBuildName);

		try {
			JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
				"projects/{project_id}/releases/{release_id}/builds", null,
				urlPathReplacements, HttpRequestMethod.POST,
				requestJSONObject.toString());

			return spiraRelease.getSpiraReleaseBuildByID(
				responseJSONObject.getInt(ID_KEY));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected static List<SpiraReleaseBuild> getSpiraReleaseBuilds(
		SpiraProject spiraProject, SpiraRelease spiraRelease,
		SearchResult.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraReleaseBuild.class,
			() -> _requestSpiraReleaseBuilds(
				spiraProject, spiraRelease, searchParameters),
			T -> new SpiraReleaseBuild(T), searchParameters);
	}

	protected static final String ID_KEY = "BuildId";

	protected static final int STATUS_ABORTED = 4;

	protected static final int STATUS_FAILED = 1;

	protected static final int STATUS_SUCCEEDED = 2;

	protected static final int STATUS_UNSTABLED = 3;

	private static List<JSONObject> _requestSpiraReleaseBuilds(
		SpiraProject spiraProject, SpiraRelease spiraRelease,
		SearchResult.SearchParameter... searchParameters) {

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(15000));
		urlParameters.put("sort_by", "BuildId DESC");
		urlParameters.put("starting_row", String.valueOf(1));

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"release_id", String.valueOf(spiraRelease.getID()));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchResult.SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		List<JSONObject> spiraReleaseBuilds = new ArrayList<>();

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/releases/{release_id}/builds",
				urlParameters, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			for (int i = 0; i < responseJSONArray.length(); i++) {
				spiraReleaseBuilds.add(responseJSONArray.getJSONObject(i));
			}

			return spiraReleaseBuilds;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraReleaseBuild(JSONObject jsonObject) {
		super(jsonObject);
	}

}