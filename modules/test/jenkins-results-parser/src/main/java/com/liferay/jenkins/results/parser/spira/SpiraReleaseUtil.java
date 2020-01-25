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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraReleaseUtil {

	public static SpiraRelease getSpiraReleaseById(int projectId, int releaseID)
		throws IOException {

		SpiraProject spiraProject = SpiraProjectUtil.getSpiraProjectById(
			projectId);

		SpiraRelease spiraRelease = spiraProject.getSpiraReleaseByID(releaseID);

		if (spiraRelease != null) {
			return spiraRelease;
		}

		Map<String, String> urlReplacements = new HashMap<>();

		urlReplacements.put("project_id", String.valueOf(projectId));
		urlReplacements.put("release_id", String.valueOf(releaseID));

		return _newSpiraRelease(
			SpiraRestAPIUtil.requestJSONObject(
				"projects/{project_id}/releases/{release_id}", urlReplacements,
				HttpRequestMethod.GET, null));
	}

	private static SpiraRelease _newSpiraRelease(JSONObject jsonObject) {
		Integer projectID = jsonObject.getInt("ProjectId");

		SpiraProject spiraProject = SpiraProjectUtil.getSpiraProjectById(
			projectID);

		SpiraRelease spiraRelease = spiraProject.getSpiraReleaseByID(
			jsonObject.getInt("ReleaseId"));

		if (spiraRelease == null) {
			spiraRelease = new SpiraRelease(jsonObject);
		}

		spiraProject.addSpiraRelease(spiraRelease);

		return spiraRelease;
	}

}