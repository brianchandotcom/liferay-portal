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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraTestCaseComponent extends BaseSpiraArtifact {

	public static SpiraTestCaseComponent createSpiraTestCaseComponent(
		SpiraProject spiraProject, String componentName) {

		List<SpiraTestCaseComponent> spiraTestCaseComponents =
			getSpiraTestCaseComponents(
				spiraProject,
				new SearchQuery.SearchParameter("Name", componentName));

		if (!spiraTestCaseComponents.isEmpty()) {
			return spiraTestCaseComponents.get(0);
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("IsActive", true);
		requestJSONObject.put("IsDeleted", false);
		requestJSONObject.put("Name", componentName);
		requestJSONObject.put("ProjectId", spiraProject.getID());

		try {
			return new SpiraTestCaseComponent(
				SpiraRestAPIUtil.requestJSONObject(
					"projects/{project_id}/components", null,
					urlPathReplacements,
					JenkinsResultsParserUtil.HttpRequestMethod.POST,
					requestJSONObject.toString()));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, "/", String.valueOf(spiraProject.getID()),
			"/Administration/Components.aspx");
	}

	protected static List<SpiraTestCaseComponent> getSpiraTestCaseComponents(
		final SpiraProject spiraProject,
		SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraTestCaseComponent.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraTestCaseComponents(spiraProject);
				}

			},
			new Function<JSONObject, SpiraTestCaseComponent>() {

				@Override
				public SpiraTestCaseComponent apply(JSONObject jsonObject) {
					return new SpiraTestCaseComponent(jsonObject);
				}

			},
			searchParameters);
	}

	protected static final String ARTIFACT_TYPE_NAME = "component";

	protected static final String KEY_ID = "ComponentId";

	private static List<JSONObject> _requestSpiraTestCaseComponents(
		SpiraProject spiraProject) {

		List<JSONObject> spiraTestCaseTypes = new ArrayList<>();

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("active_only", "true");
		urlParameters.put("include_deleted", "false");

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/components", urlParameters,
				urlPathReplacements,
				JenkinsResultsParserUtil.HttpRequestMethod.GET, null);

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.KEY_ID, spiraProject.getID());

				spiraTestCaseTypes.add(responseJSONObject);
			}

			return spiraTestCaseTypes;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraTestCaseComponent(JSONObject jsonObject) {
		super(jsonObject);

		cacheSpiraArtifact(SpiraTestCaseComponent.class, this);
	}

}