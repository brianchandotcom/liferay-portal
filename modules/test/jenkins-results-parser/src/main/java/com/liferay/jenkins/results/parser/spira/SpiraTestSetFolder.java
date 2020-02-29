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

import org.apache.commons.lang.StringEscapeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestSetFolder extends IndentLevelSpiraArtifact {

	public static SpiraTestSetFolder createSpiraTestSetFolder(
			SpiraProject spiraProject, String testSetFolderName)
		throws IOException {

		return createSpiraTestSetFolder(spiraProject, testSetFolderName, null);
	}

	public static SpiraTestSetFolder createSpiraTestSetFolder(
			SpiraProject spiraProject, String testSetFolderName,
			Integer parentTestSetFolderID)
		throws IOException {

		String testSetFolderPath = "/" + testSetFolderName;

		if (parentTestSetFolderID != null) {
			SpiraTestSetFolder parentSpiraTestSetFolder =
				spiraProject.getSpiraTestSetFolderByID(parentTestSetFolderID);

			if (parentSpiraTestSetFolder != null) {
				testSetFolderPath =
					parentSpiraTestSetFolder.getPath() + "/" +
						testSetFolderName;
			}
		}

		List<SpiraTestSetFolder> spiraTestSetFolders =
			spiraProject.getSpiraTestSetFoldersByPath(testSetFolderPath);

		if (!spiraTestSetFolders.isEmpty()) {
			return spiraTestSetFolders.get(0);
		}

		String urlPath = "projects/{project_id}/test-set-folders";

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"Name", StringEscapeUtils.unescapeJava(testSetFolderName));
		requestJSONObject.put("ParentTestSetFolderId", parentTestSetFolderID);

		JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
			urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
			requestJSONObject.toString());

		return spiraProject.getSpiraTestSetFolderByID(
			responseJSONObject.getInt(ID_KEY));
	}

	public static SpiraTestSetFolder createSpiraTestSetFolderByPath(
			SpiraProject spiraProject, String testSetFolderPath)
		throws IOException {

		List<SpiraTestSetFolder> spiraTestSetFolders =
			spiraProject.getSpiraTestSetFoldersByPath(testSetFolderPath);

		if (!spiraTestSetFolders.isEmpty()) {
			return spiraTestSetFolders.get(0);
		}

		String testSetFolderName = getPathName(testSetFolderPath);
		String parentTestSetFolderPath = getParentPath(testSetFolderPath);

		if (parentTestSetFolderPath.isEmpty()) {
			return createSpiraTestSetFolder(spiraProject, testSetFolderName);
		}

		SpiraTestSetFolder parentSpiraTestSetFolder =
			createSpiraTestSetFolderByPath(
				spiraProject, parentTestSetFolderPath);

		return createSpiraTestSetFolder(
			spiraProject, testSetFolderName, parentSpiraTestSetFolder.getID());
	}

	public static void deleteSpiraTestSetFolderByID(
			SpiraProject spiraProject, int testSetFolderID)
		throws IOException {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"test_set_folder_id", String.valueOf(testSetFolderID));

		SpiraRestAPIUtil.request(
			"projects/{project_id}/test-set-folders/{test_set_folder_id}", null,
			urlPathReplacements, HttpRequestMethod.DELETE, null);

		_spiraTestSetFolders.remove(
			_createSpiraTestSetFolderKey(
				spiraProject.getID(), testSetFolderID));
	}

	public static void deleteSpiraTestSetFoldersByPath(
			SpiraProject spiraProject, String testSetFolderPath)
		throws IOException {

		List<SpiraTestSetFolder> spiraTestSetFolders =
			spiraProject.getSpiraTestSetFoldersByPath(testSetFolderPath);

		for (SpiraTestSetFolder spiraTestSetFolder : spiraTestSetFolders) {
			deleteSpiraTestSetFolderByID(
				spiraProject, spiraTestSetFolder.getID());
		}
	}

	@Override
	public int getID() {
		return jsonObject.getInt(ID_KEY);
	}

	protected static List<SpiraTestSetFolder> getSpiraTestSetFolders(
			SpiraProject spiraProject, SearchParameter... searchParameters)
		throws IOException {

		List<SpiraTestSetFolder> spiraTestSetFolders = new ArrayList<>();

		if (isPreviousSearch(SpiraTestSetFolder.class, searchParameters)) {
			for (SpiraTestSetFolder spiraTestSetFolder :
					_spiraTestSetFolders.values()) {

				if (spiraTestSetFolder.matches(searchParameters)) {
					spiraTestSetFolders.add(spiraTestSetFolder);
				}
			}

			if (!spiraTestSetFolders.isEmpty()) {
				return spiraTestSetFolders;
			}
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			"projects/{project_id}/test-set-folders", null, urlPathReplacements,
			HttpRequestMethod.GET, null);

		for (int i = 0; i < responseJSONArray.length(); i++) {
			JSONObject responseJSONObject = responseJSONArray.getJSONObject(i);

			responseJSONObject.put(SpiraProject.ID_KEY, spiraProject.getID());

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

		addPreviousSearchParameters(SpiraTestSetFolder.class, searchParameters);

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

	protected static final String ID_KEY = "TestSetFolderId";

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