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

	public static SpiraCustomList createSpiraCustomListByName(
		SpiraProject spiraProject,
		Class<? extends SpiraArtifact> spiraArtifactClass,
		String customListName) {

		List<SpiraCustomList> spiraCustomLists = getSpiraCustomLists(
			spiraProject, spiraArtifactClass,
			new SearchQuery.SearchParameter("Name", customListName));

		if (!spiraCustomLists.isEmpty()) {
			return spiraCustomLists.get(0);
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		Integer projectTemplateID = spiraProject.getProjectTemplateID();

		urlPathReplacements.put(
			"project_template_id", String.valueOf(projectTemplateID));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("Active", true);
		requestJSONObject.put("Name", customListName);
		requestJSONObject.put("ProjectTemplateId", projectTemplateID);
		requestJSONObject.put("SortedOnValue", true);

		try {
			return new SpiraCustomList(
				SpiraRestAPIUtil.requestJSONObject(
					"project-templates/{project_template_id}/custom-lists",
					null, urlPathReplacements, HttpRequestMethod.POST,
					requestJSONObject.toString()),
				spiraProject, spiraArtifactClass);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public SpiraCustomListValue getSpiraCustomListValueByID(int id) {
		for (SpiraCustomListValue spiraCustomListValue :
				getSpiraCustomListValues()) {

			if (id == spiraCustomListValue.getID()) {
				return spiraCustomListValue;
			}
		}

		return null;
	}

	public SpiraCustomListValue getSpiraCustomListValueByName(String name) {
		for (SpiraCustomListValue spiraCustomListValue :
				getSpiraCustomListValues()) {

			if (name.equals(spiraCustomListValue.getName())) {
				return spiraCustomListValue;
			}
		}

		return null;
	}

	public List<SpiraCustomListValue> getSpiraCustomListValues() {
		SpiraProject spiraProject = getSpiraProject();

		List<SpiraCustomProperty> spiraCustomProperties =
			SpiraCustomProperty.getSpiraCustomProperties(
				spiraProject, _spiraArtifactClass,
				new SearchQuery.SearchParameter("Name", getName()));

		if (spiraCustomProperties.isEmpty()) {
			return new ArrayList<>();
		}

		List<SpiraCustomListValue> spiraCustomListValues = new ArrayList<>();

		SpiraCustomProperty spiraCustomProperty = spiraCustomProperties.get(0);

		JSONObject spiraCustomPropertyJSONObject =
			spiraCustomProperty.toJSONObject();

		JSONObject customListJSONObject =
			spiraCustomPropertyJSONObject.optJSONObject("CustomList");

		if (customListJSONObject != JSONObject.NULL) {
			JSONArray valuesJSONArray = customListJSONObject.getJSONArray(
				"Values");

			for (int i = 0; i < valuesJSONArray.length(); i++) {
				JSONObject valueJSONObject = valuesJSONArray.getJSONObject(i);

				spiraCustomListValues.add(
					new SpiraCustomListValue(
						valueJSONObject, spiraProject, this));
			}
		}

		return spiraCustomListValues;
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, "/pt/",
			String.valueOf(spiraProject.getProjectTemplateID()),
			"/Administration/CustomListDetails.aspx?customPropertyListId=",
			String.valueOf(getID()));
	}

	protected static List<SpiraCustomList> getSpiraCustomLists(
		final SpiraProject spiraProject,
		final Class<? extends SpiraArtifact> spiraArtifactClass,
		SearchQuery.SearchParameter... searchParameters) {

		SearchQuery.SearchParameter[] customSearchParameters =
			new SearchQuery.SearchParameter[searchParameters.length + 1];

		customSearchParameters[0] = new SearchQuery.SearchParameter(
			SpiraProject.KEY_ID, spiraProject.getID());

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
					return new SpiraCustomList(
						jsonObject, spiraProject, spiraArtifactClass);
				}

			},
			customSearchParameters);
	}

	protected void addSpiraCustomListValue(
		SpiraCustomListValue spiraCustomListValue) {

		List<SpiraCustomListValue> spiraCustomListValues =
			getSpiraCustomListValues();

		spiraCustomListValues.add(spiraCustomListValue);
	}

	protected static final String ARTIFACT_TYPE_NAME = "custompropertylist";

	protected static final String KEY_ID = "CustomPropertyListId";

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
					SpiraProject.KEY_ID, spiraProject.getID());

				spiraCustomLists.add(responseJSONObject);
			}

			return spiraCustomLists;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraCustomList(
		JSONObject jsonObject, SpiraProject spiraProject,
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		super(jsonObject);

		jsonObject.put("ProjectId", spiraProject.getID());

		_spiraArtifactClass = spiraArtifactClass;

		cacheSpiraArtifact(SpiraCustomList.class, this);
	}

	private final Class<? extends SpiraArtifact> _spiraArtifactClass;

}