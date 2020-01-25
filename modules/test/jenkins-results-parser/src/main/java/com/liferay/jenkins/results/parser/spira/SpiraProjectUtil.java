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

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraProjectUtil {

	public static SpiraProject getSpiraProjectById(int projectID) {
		if (_spiraProjectByID.containsKey(projectID)) {
			return _spiraProjectByID.get(projectID);
		}

		Map<String, String> urlReplacements = new HashMap<>();

		urlReplacements.put("project_id", String.valueOf(projectID));

		try {
			return _newSpiraProject(
				SpiraRestAPIUtil.requestJSONObject(
					"projects/{project_id}", urlReplacements,
					JenkinsResultsParserUtil.HttpRequestMethod.GET, null));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static SpiraProject _newSpiraProject(JSONObject jsonObject) {
		int projectID = jsonObject.getInt("ProjectId");

		if (_spiraProjectByID.containsKey(projectID)) {
			return _spiraProjectByID.get(projectID);
		}

		SpiraProject spiraProject = new SpiraProject(jsonObject);

		_spiraProjectByID.put(spiraProject.getID(), spiraProject);

		return spiraProject;
	}

	private static final Map<Integer, SpiraProject> _spiraProjectByID =
		new HashMap<>();

}