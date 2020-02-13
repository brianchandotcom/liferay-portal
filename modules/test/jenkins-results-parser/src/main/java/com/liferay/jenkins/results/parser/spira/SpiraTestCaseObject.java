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
public class SpiraTestCaseObject extends PathSpiraArtifact {

	@Override
	public int getID() {
		return jsonObject.getInt("TestCaseId");
	}

	public SpiraTestCaseFolder getParentSpiraTestCaseFolder() {
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

	protected static List<SpiraTestCaseObject> getSpiraTestCases(
			SpiraProject spiraProject, SearchParameter... searchParameters)
		throws IOException {

		List<SpiraTestCaseObject> spiraTestCases = new ArrayList<>();

		for (SpiraTestCaseObject spiraTestCase : _spiraTestCases.values()) {
			if (spiraTestCase.matches(searchParameters)) {
				spiraTestCases.add(spiraTestCase);
			}
		}

		if (!spiraTestCases.isEmpty()) {
			return spiraTestCases;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(15000));
		urlParameters.put("starting_row", String.valueOf(1));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			"projects/{project_id}/test-cases/search", urlParameters,
			urlPathReplacements, HttpRequestMethod.POST,
			requestJSONArray.toString());

		for (int i = 0; i < responseJSONArray.length(); i++) {
			SpiraTestCaseObject spiraTestCase = new SpiraTestCaseObject(
				responseJSONArray.getJSONObject(i));

			_spiraTestCases.put(
				_createSpiraTestCaseKey(
					spiraProject.getID(), spiraTestCase.getID()),
				spiraTestCase);

			if (spiraTestCase.matches(searchParameters)) {
				spiraTestCases.add(spiraTestCase);
			}
		}

		return spiraTestCases;
	}

	@Override
	protected PathSpiraArtifact getParentSpiraArtifact() {
		if (_parentSpiraArtifact != null) {
			return _parentSpiraArtifact;
		}

		Object testCaseFolderID = jsonObject.get("TestCaseFolderId");

		if (testCaseFolderID == JSONObject.NULL) {
			return null;
		}

		if (!(testCaseFolderID instanceof Integer)) {
			return null;
		}

		SpiraProject spiraProject = getSpiraProject();

		try {
			_parentSpiraArtifact = spiraProject.getSpiraTestCaseFolderByID(
				(Integer)testCaseFolderID);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _parentSpiraArtifact;
	}

	private static String _createSpiraTestCaseKey(
		Integer projectID, Integer testCaseID) {

		return projectID + "-" + testCaseID;
	}

	private SpiraTestCaseObject(JSONObject jsonObject) {
		super(jsonObject);
	}

	private static final Map<String, SpiraTestCaseObject> _spiraTestCases =
		new HashMap<>();

	private PathSpiraArtifact _parentSpiraArtifact;

}