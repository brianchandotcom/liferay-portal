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
import java.util.Collections;
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
public class SpiraCustomList extends BaseSpiraArtifact {

	protected static SpiraCustomList createSpiraCustomListByName(
		final SpiraProject spiraProject, String spiraCustomListName) {

		List<SpiraCustomList> spiraCustomLists = getSpiraCustomLists(
			spiraProject,
			new SearchQuery.SearchParameter("Name", spiraCustomListName));

		if (!spiraCustomLists.isEmpty()) {
			return spiraCustomLists.get(0);
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		Integer projectTemplateID = spiraProject.getProjectTemplateID();

		urlPathReplacements.put(
			"project_template_id", String.valueOf(projectTemplateID));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("Active", true);
		requestJSONObject.put("Name", spiraCustomListName);
		requestJSONObject.put("ProjectTemplateId", projectTemplateID);
		requestJSONObject.put("SortedOnValue", true);

		try {
			SpiraCustomList spiraCustomList = new SpiraCustomList(
				SpiraRestAPIUtil.requestJSONObject(
					"project-templates/{project_template_id}/custom-lists",
					null, urlPathReplacements, HttpRequestMethod.POST,
					requestJSONObject.toString()));

			cachedSpiraArtifacts(Collections.singletonList(spiraCustomList));

			return spiraCustomList;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected static List<SpiraCustomList> getSpiraCustomLists(
		final SpiraProject spiraProject,
		SearchQuery.SearchParameter... searchParameters) {

		SearchQuery.SearchParameter[] customSearchParameters =
			new SearchQuery.SearchParameter[searchParameters.length + 1];

		customSearchParameters[0] = new SearchQuery.SearchParameter(
			SpiraProject.ID_KEY, spiraProject.getID());

		for (int i = 0; i < searchParameters.length; i++) {
			customSearchParameters[i + 1] = searchParameters[i];
		}

		return getSpiraArtifacts(
			SpiraCustomList.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraCustomLists(spiraProject);
				}

			},
			new Function<JSONObject, SpiraCustomList>() {

				@Override
				public SpiraCustomList apply(JSONObject jsonObject) {
					return new SpiraCustomList(jsonObject);
				}

			},
			customSearchParameters);
	}

	protected static final String ARTIFACT_TYPE_NAME = "custompropertylist";

	protected static final String ID_KEY = "CustomPropertyListId";

	private static List<JSONObject> _requestSpiraCustomLists(
		SpiraProject spiraProject) {

		List<JSONObject> spiraCustomLists = new ArrayList<>();

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_template_id",
			String.valueOf(spiraProject.getProjectTemplateID()));

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"project-templates/{project_template_id}/custom-lists", null,
				urlPathReplacements, HttpRequestMethod.GET, null);

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.ID_KEY, spiraProject.getID());

				spiraCustomLists.add(responseJSONObject);
			}

			return spiraCustomLists;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraCustomList(JSONObject jsonObject) {
		super(jsonObject);
	}

}