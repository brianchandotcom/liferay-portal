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

	public static SpiraCustomList.Value createSpiraCustomListValue(
		SpiraProject spiraProject, SpiraCustomList spiraCustomList,
		String value) {

		Value spiraCustomListValue =
			spiraCustomList.getSpiraCustomListValueByName(value);

		if (spiraCustomListValue != null) {
			return spiraCustomListValue;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"custom_list_id", String.valueOf(spiraCustomList.getID()));
		urlPathReplacements.put(
			"project_template_id",
			String.valueOf(spiraProject.getProjectTemplateID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("Active", true);
		requestJSONObject.put("CustomPropertyListId", spiraCustomList.getID());
		requestJSONObject.put("Name", value);
		requestJSONObject.put("ProjectId", spiraProject.getID());

		try {
			spiraCustomListValue = new SpiraCustomList.Value(
				SpiraRestAPIUtil.requestJSONObject(
					"project-templates/{project_template_id}/custom-lists" +
						"/{custom_list_id}/values",
					null, urlPathReplacements, HttpRequestMethod.POST,
					requestJSONObject.toString()),
				spiraProject, spiraCustomList);

			spiraCustomList._addSpiraCustomListValue(spiraCustomListValue);

			return spiraCustomListValue;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public SpiraCustomList.Value getSpiraCustomListValueByName(String name) {
		for (SpiraCustomList.Value value : getSpiraCustomListValues()) {
			if (name.equals(value.getName())) {
				return value;
			}
		}

		return null;
	}

	public List<SpiraCustomList.Value> getSpiraCustomListValues() {
		if (_spiraCustomListValues != null) {
			return _spiraCustomListValues;
		}

		_spiraCustomListValues = new ArrayList<>();

		SpiraProject spiraProject = getSpiraProject();

		List<SpiraCustomProperty> spiraCustomProperties =
			SpiraCustomProperty.getSpiraCustomProperties(
				spiraProject, _spiraArtifactClass,
				new SearchQuery.SearchParameter("Name", getName()));

		if (spiraCustomProperties.isEmpty()) {
			return _spiraCustomListValues;
		}

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

				_spiraCustomListValues.add(
					new Value(valueJSONObject, spiraProject, this));
			}
		}

		return _spiraCustomListValues;
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

	public static class Value extends BaseSpiraArtifact {

		public SpiraCustomList getSpiraCustomList() {
			return _spiraCustomList;
		}

		@Override
		public String getURL() {
			SpiraCustomList spiraCustomList = getSpiraCustomList();

			return spiraCustomList.getURL();
		}

		protected Value(
			JSONObject jsonObject, SpiraProject spiraProject,
			SpiraCustomList spiraCustomList) {

			super(jsonObject);

			jsonObject.put("ProjectId", spiraProject.getID());

			_spiraCustomList = spiraCustomList;
		}

		protected static final String ID_KEY = "CustomPropertyValueId";

		private final SpiraCustomList _spiraCustomList;

	}

	protected static List<SpiraCustomList> getSpiraCustomLists(
		final SpiraProject spiraProject,
		final Class<? extends SpiraArtifact> spiraArtifactClass,
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
					return new SpiraCustomList(
						jsonObject, spiraProject, spiraArtifactClass);
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

	private SpiraCustomList(
		JSONObject jsonObject, SpiraProject spiraProject,
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		super(jsonObject);

		jsonObject.put("ProjectId", spiraProject.getID());

		_spiraArtifactClass = spiraArtifactClass;

		cacheSpiraArtifact(SpiraCustomList.class, this);
	}

	private void _addSpiraCustomListValue(
		SpiraCustomList.Value spiraCustomListValue) {

		List<SpiraCustomList.Value> spiraCustomListValues =
			getSpiraCustomListValues();

		spiraCustomListValues.add(spiraCustomListValue);
	}

	private final Class<? extends SpiraArtifact> _spiraArtifactClass;
	private List<SpiraCustomList.Value> _spiraCustomListValues;

}