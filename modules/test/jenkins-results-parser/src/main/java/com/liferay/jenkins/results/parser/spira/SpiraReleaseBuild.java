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
			String releaseBuildName)
		throws IOException {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"release_id", String.valueOf(spiraRelease.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("BuildStatusId", STATUS_SUCCEEDED);
		requestJSONObject.put("Name", releaseBuildName);
		requestJSONObject.put("ProjectId", spiraProject.getID());
		requestJSONObject.put("ReleaseId", spiraRelease.getID());

		JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
			"projects/{project_id}/releases/{release_id}/builds", null,
			urlPathReplacements, HttpRequestMethod.POST,
			requestJSONObject.toString());

		SpiraReleaseBuild spiraReleaseBuild =
			spiraRelease.getSpiraReleaseBuildByID(
				responseJSONObject.getInt("BuildId"));

		_spiraReleaseBuilds.put(
			_createSpiraReleaseBuildKey(
				spiraProject.getID(), spiraRelease.getID(),
				spiraReleaseBuild.getID()),
			spiraReleaseBuild);

		return spiraReleaseBuild;
	}

	@Override
	public int getID() {
		return jsonObject.getInt("BuildId");
	}

	protected static List<SpiraReleaseBuild> getSpiraReleaseBuilds(
			SpiraProject spiraProject, SpiraRelease spiraRelease,
			SearchParameter... searchParameters)
		throws IOException {

		List<SpiraReleaseBuild> spiraReleaseBuilds = new ArrayList<>();

		for (SpiraReleaseBuild spiraReleaseBuild :
				_spiraReleaseBuilds.values()) {

			if (spiraReleaseBuild.matches(searchParameters)) {
				spiraReleaseBuilds.add(spiraReleaseBuild);
			}
		}

		if (!spiraReleaseBuilds.isEmpty()) {
			return spiraReleaseBuilds;
		}

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

		for (SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			"projects/{project_id}/releases/{release_id}/builds", urlParameters,
			urlPathReplacements, HttpRequestMethod.POST,
			requestJSONArray.toString());

		for (int i = 0; i < responseJSONArray.length(); i++) {
			SpiraReleaseBuild spiraReleaseBuild = new SpiraReleaseBuild(
				responseJSONArray.getJSONObject(i));

			_spiraReleaseBuilds.put(
				_createSpiraReleaseBuildKey(
					spiraProject.getID(), spiraRelease.getID(),
					spiraReleaseBuild.getID()),
				spiraReleaseBuild);

			if (spiraReleaseBuild.matches(searchParameters)) {
				spiraReleaseBuilds.add(spiraReleaseBuild);
			}
		}

		return spiraReleaseBuilds;
	}

	protected static final int STATUS_ABORTED = 4;

	protected static final int STATUS_FAILED = 1;

	protected static final int STATUS_SUCCEEDED = 2;

	protected static final int STATUS_UNSTABLED = 3;

	private static String _createSpiraReleaseBuildKey(
		int projectID, int releaseID, int releaseBuildID) {

		return projectID + "-" + releaseID + "-" + releaseBuildID;
	}

	private SpiraReleaseBuild(JSONObject jsonObject) {
		super(jsonObject);
	}

	private static final Map<String, SpiraReleaseBuild> _spiraReleaseBuilds =
		new HashMap<>();

}