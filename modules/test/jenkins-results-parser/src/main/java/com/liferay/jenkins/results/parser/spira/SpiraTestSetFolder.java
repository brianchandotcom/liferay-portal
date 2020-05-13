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
public class SpiraTestSetFolder extends PathSpiraArtifact {

	public static SpiraTestSetFolder createSpiraTestSetFolder(
		SpiraProject spiraProject, String testSetFolderName) {

		return createSpiraTestSetFolder(spiraProject, testSetFolderName, null);
	}

	public static SpiraTestSetFolder createSpiraTestSetFolder(
		SpiraProject spiraProject, String testSetFolderName,
		Integer parentTestSetFolderID) {

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

		try {
			JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
				urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONObject.toString());

			return spiraProject.getSpiraTestSetFolderByID(
				responseJSONObject.getInt(ID_KEY));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static SpiraTestSetFolder createSpiraTestSetFolderByPath(
		SpiraProject spiraProject, String testSetFolderPath) {

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
		SpiraProject spiraProject, int testSetFolderID) {

		List<SpiraTestSetFolder> spiraTestSetFolders = getSpiraTestSetFolders(
			spiraProject,
			new SearchQuery.SearchParameter(ID_KEY, testSetFolderID));

		if (spiraTestSetFolders.isEmpty()) {
			return;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"test_set_folder_id", String.valueOf(testSetFolderID));

		try {
			SpiraRestAPIUtil.request(
				"projects/{project_id}/test-set-folders/{test_set_folder_id}",
				null, urlPathReplacements, HttpRequestMethod.DELETE, null);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		removeCachedSpiraArtifacts(
			SpiraTestSetFolder.class, spiraTestSetFolders);
	}

	public static void deleteSpiraTestSetFoldersByPath(
		SpiraProject spiraProject, String testSetFolderPath) {

		List<SpiraTestSetFolder> spiraTestSetFolders =
			spiraProject.getSpiraTestSetFoldersByPath(testSetFolderPath);

		for (SpiraTestSetFolder spiraTestSetFolder : spiraTestSetFolders) {
			deleteSpiraTestSetFolderByID(
				spiraProject, spiraTestSetFolder.getID());
		}
	}

	public SpiraTestSetFolder getParentSpiraTestSetFolder() {
		if (_parentSpiraTestSetFolder != null) {
			return _parentSpiraTestSetFolder;
		}

		Object parentTestSetFolderID = jsonObject.get("ParentTestSetFolderId");

		if (parentTestSetFolderID == JSONObject.NULL) {
			return null;
		}

		if (!(parentTestSetFolderID instanceof Integer)) {
			return null;
		}

		SpiraProject spiraProject = getSpiraProject();

		_parentSpiraTestSetFolder = spiraProject.getSpiraTestSetFolderByID(
			(Integer)parentTestSetFolderID);

		return _parentSpiraTestSetFolder;
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, String.valueOf(spiraProject.getID()),
			"/TestSet/List/", String.valueOf(getID()), ".aspx");
	}

	protected static List<SpiraTestSetFolder> getSpiraTestSetFolders(
		final SpiraProject spiraProject,
		SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraTestSetFolder.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraTestSetFolders(spiraProject);
				}

			},
			new Function<JSONObject, SpiraTestSetFolder>() {

				@Override
				public SpiraTestSetFolder apply(JSONObject jsonObject) {
					return new SpiraTestSetFolder(jsonObject);
				}

			},
			searchParameters);
	}

	@Override
	protected PathSpiraArtifact getParentSpiraArtifact() {
		return getParentSpiraTestSetFolder();
	}

	protected static final String ARTIFACT_TYPE_NAME = "testsetfolder";

	protected static final String ID_KEY = "TestSetFolderId";

	private static List<JSONObject> _requestSpiraTestSetFolders(
		SpiraProject spiraProject) {

		List<JSONObject> spiraTestSetFolders = new ArrayList<>();

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/test-set-folders", null,
				urlPathReplacements, HttpRequestMethod.GET, null);

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

	private SpiraTestSetFolder(JSONObject jsonObject) {
		super(jsonObject);

		cacheSpiraArtifact(SpiraTestSetFolder.class, this);
	}

	private SpiraTestSetFolder _parentSpiraTestSetFolder;

}