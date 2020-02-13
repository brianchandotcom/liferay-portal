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
public class SpiraTestCaseFolder extends IndentLevelSpiraArtifact {

	public static SpiraTestCaseFolder createSpiraTestCaseFolder(
			SpiraProject spiraProject, String testCaseFolderName)
		throws IOException {

		return createSpiraTestCaseFolder(
			spiraProject, testCaseFolderName, null);
	}

	public static SpiraTestCaseFolder createSpiraTestCaseFolder(
			SpiraProject spiraProject, String testCaseFolderName,
			Integer parentTestCaseFolderID)
		throws IOException {

		String testCaseFolderPath = "/" + testCaseFolderName;

		if (parentTestCaseFolderID != null) {
			SpiraTestCaseFolder parentSpiraTestCaseFolder =
				spiraProject.getSpiraTestCaseFolderByID(parentTestCaseFolderID);

			if (parentSpiraTestCaseFolder != null) {
				testCaseFolderPath =
					parentSpiraTestCaseFolder.getPath() + "/" +
						testCaseFolderName;
			}
		}

		List<SpiraTestCaseFolder> spiraTestCaseFolders =
			spiraProject.getSpiraTestCaseFoldersByPath(testCaseFolderPath);

		if (!spiraTestCaseFolders.isEmpty()) {
			return spiraTestCaseFolders.get(0);
		}

		String urlPath = "projects/{project_id}/test-folders";

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"Name", StringEscapeUtils.unescapeJava(testCaseFolderName));
		requestJSONObject.put("ParentTestCaseFolderId", parentTestCaseFolderID);

		JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
			urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
			requestJSONObject.toString());

		return spiraProject.getSpiraTestCaseFolderByID(
			responseJSONObject.getInt("TestCaseFolderId"));
	}

	public static SpiraTestCaseFolder createSpiraTestCaseFolderByPath(
			SpiraProject spiraProject, String testCaseFolderPath)
		throws IOException {

		List<SpiraTestCaseFolder> spiraTestCaseFolders =
			spiraProject.getSpiraTestCaseFoldersByPath(testCaseFolderPath);

		if (!spiraTestCaseFolders.isEmpty()) {
			return spiraTestCaseFolders.get(0);
		}

		String testCaseFolderName = getPathName(testCaseFolderPath);
		String parentTestCaseFolderPath = getParentPath(testCaseFolderPath);

		if (parentTestCaseFolderPath.isEmpty()) {
			return createSpiraTestCaseFolder(spiraProject, testCaseFolderName);
		}

		SpiraTestCaseFolder parentSpiraTestCaseFolder =
			createSpiraTestCaseFolderByPath(
				spiraProject, parentTestCaseFolderPath);

		return createSpiraTestCaseFolder(
			spiraProject, testCaseFolderName,
			parentSpiraTestCaseFolder.getID());
	}

	public static void deleteSpiraTestCaseFolderByID(
			SpiraProject spiraProject, int testCaseFolderID)
		throws IOException {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"test_case_folder_id", String.valueOf(testCaseFolderID));

		SpiraRestAPIUtil.request(
			"projects/{project_id}/test-folders/{test_case_folder_id}", null,
			urlPathReplacements, HttpRequestMethod.DELETE, null);

		_spiraTestCaseFolders.remove(
			_createSpiraTestCaseFolderKey(
				spiraProject.getID(), testCaseFolderID));
	}

	public static void deleteSpiraTestCaseFoldersByPath(
			SpiraProject spiraProject, String testCaseFolderPath)
		throws IOException {

		List<SpiraTestCaseFolder> spiraTestCaseFolders =
			spiraProject.getSpiraTestCaseFoldersByPath(testCaseFolderPath);

		for (SpiraTestCaseFolder spiraTestCaseFolder : spiraTestCaseFolders) {
			deleteSpiraTestCaseFolderByID(
				spiraProject, spiraTestCaseFolder.getID());
		}
	}

	@Override
	public int getID() {
		return jsonObject.getInt("TestCaseFolderId");
	}

	public SpiraTestCaseFolder getParentTestCaseFolder() {
		PathSpiraArtifact parentSpiraArtifact = getParentSpiraArtifact();

		if (parentSpiraArtifact == null) {
			return null;
		}

		if (!(parentSpiraArtifact instanceof SpiraTestCaseFolder)) {
			throw new RuntimeException(
				"Invalid parent object " + parentSpiraArtifact);
		}

		return (SpiraTestCaseFolder)parentSpiraArtifact;
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
			JSONObject responseJSONObject = responseJSONArray.getJSONObject(i);

			responseJSONObject.put("ProjectId", spiraProject.getID());

			SpiraTestCaseFolder spiraTestCaseFolder = new SpiraTestCaseFolder(
				responseJSONObject);

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

	@Override
	protected PathSpiraArtifact getSpiraArtifactByIndentLevel(
		String indentLevel) {

		SpiraProject spiraProject = getSpiraProject();

		try {
			return spiraProject.getSpiraTestCaseFolderByIndentLevel(
				indentLevel);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static String _createSpiraTestCaseFolderKey(
		Integer projectID, Integer testCaseFolderID) {

		return projectID + "-" + testCaseFolderID;
	}

	private SpiraTestCaseFolder(JSONObject jsonObject) {
		super(jsonObject);
	}

	private static final Map<String, SpiraTestCaseFolder>
		_spiraTestCaseFolders = new HashMap<>();

}