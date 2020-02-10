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
public class SpiraTestCaseFolder {

	public int getID() {
		return _jsonObject.getInt("TestCaseFolderId");
	}

	public String getName() {
		return _jsonObject.getString("Name");
	}

	public JSONObject toJSONObject() {
		return _jsonObject;
	}

	@Override
	public String toString() {
		return _jsonObject.toString();
	}

	protected static List<SpiraTestCaseFolder> getSpiraTestCaseFolders(
			SpiraProject spiraProject, SearchParameter... searchParameters)
		throws IOException {

		List<SpiraTestCaseFolder> spiraTestCaseFolders = new ArrayList<>();

		for (SpiraTestCaseFolder spiraTestCaseFolder :
				_spiraTestCaseFolders.values()) {

			if (spiraTestCaseFolder.matches(searchParameters)) {
				spiraTestCaseFolders.add(spiraTestCaseFolder);
			}
		}

		if (!spiraTestCaseFolders.isEmpty()) {
			return spiraTestCaseFolders;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			"projects/{project_id}/test-folders", null, urlPathReplacements,
			HttpRequestMethod.GET, null);

		for (int i = 0; i < responseJSONArray.length(); i++) {
			SpiraTestCaseFolder spiraTestCaseFolder = new SpiraTestCaseFolder(
				responseJSONArray.getJSONObject(i));

			_spiraTestCaseFolders.put(
				_createSpiraTestCaseFolderKey(
					spiraProject.getID(), spiraTestCaseFolder.getID()),
				spiraTestCaseFolder);

			if (spiraTestCaseFolder.matches(searchParameters)) {
				spiraTestCaseFolders.add(spiraTestCaseFolder);
			}
		}

		return spiraTestCaseFolders;
	}

	protected SpiraTestCaseFolder(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	protected boolean matches(SearchParameter... searchParameters) {
		return SearchParameter.matches(toJSONObject(), searchParameters);
	}

	private static String _createSpiraTestCaseFolderKey(
		Integer projectID, Integer testCaseFolderID) {

		return projectID + "-" + testCaseFolderID;
	}

	private static final Map<String, SpiraTestCaseFolder>
		_spiraTestCaseFolders = new HashMap<>();

	private final JSONObject _jsonObject;

}