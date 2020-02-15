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
public class SpiraTestSetFolder extends IndentLevelSpiraArtifact {

	@Override
	public int getID() {
		return jsonObject.getInt("TestSetFolderId");
	}

	protected static List<SpiraTestSetFolder> getSpiraTestSetFolders(
			SpiraProject spiraProject, SearchParameter... searchParameters)
		throws IOException {

		List<SpiraTestSetFolder> spiraTestSetFolders = new ArrayList<>();

		for (SpiraTestSetFolder spiraTestSetFolder :
				_spiraTestSetFolders.values()) {

			if (spiraTestSetFolder.matches(searchParameters)) {
				spiraTestSetFolders.add(spiraTestSetFolder);
			}
		}

		if (!spiraTestSetFolders.isEmpty()) {
			return spiraTestSetFolders;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			"projects/{project_id}/test-set-folders", null, urlPathReplacements,
			HttpRequestMethod.GET, null);

		for (int i = 0; i < responseJSONArray.length(); i++) {
			JSONObject responseJSONObject = responseJSONArray.getJSONObject(i);

			responseJSONObject.put("ProjectId", spiraProject.getID());

			SpiraTestSetFolder spiraTestSetFolder = new SpiraTestSetFolder(
				responseJSONObject);

			_spiraTestSetFolders.put(
				_createSpiraTestSetFolderKey(
					spiraProject.getID(), spiraTestSetFolder.getID()),
				spiraTestSetFolder);

			if (spiraTestSetFolder.matches(searchParameters)) {
				spiraTestSetFolders.add(spiraTestSetFolder);
			}
		}

		return spiraTestSetFolders;
	}

	@Override
	protected PathSpiraArtifact getSpiraArtifactByIndentLevel(
		String indentLevel) {

		SpiraProject spiraProject = getSpiraProject();

		try {
			return spiraProject.getSpiraTestSetFolderByIndentLevel(indentLevel);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static String _createSpiraTestSetFolderKey(
		Integer projectID, Integer testSetFolderID) {

		return projectID + "-" + testSetFolderID;
	}

	private SpiraTestSetFolder(JSONObject jsonObject) {
		super(jsonObject);
	}

	private static final Map<String, SpiraTestSetFolder> _spiraTestSetFolders =
		new HashMap<>();

}