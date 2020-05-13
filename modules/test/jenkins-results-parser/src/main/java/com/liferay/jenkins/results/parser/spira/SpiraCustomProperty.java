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
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraCustomProperty extends BaseSpiraArtifact {

	public static SpiraCustomProperty createSpiraCustomProperty(
		final SpiraProject spiraProject,
		final Class<? extends SpiraArtifact> spiraArtifactClass,
		String customPropertyName, Type type) {

		List<SpiraCustomProperty> spiraCustomProperties =
			getSpiraCustomProperties(
				spiraProject, spiraArtifactClass,
				new SearchQuery.SearchParameter("Name", customPropertyName));

		if (!spiraCustomProperties.isEmpty()) {
			return spiraCustomProperties.get(0);
		}

		SpiraCustomList spiraCustomList =
			SpiraCustomList.createSpiraCustomListByName(
				spiraProject, spiraArtifactClass, customPropertyName);

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put(
			"custom_list_id", String.valueOf(spiraCustomList.getID()));

		Map<String, String> urlPathReplacements = new HashMap<>();

		Integer projectTemplateID = spiraProject.getProjectTemplateID();

		urlPathReplacements.put(
			"project_template_id", String.valueOf(projectTemplateID));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"ArtifactTypeId", getArtifactTypeID(spiraArtifactClass));
		requestJSONObject.put("CustomPropertyTypeId", type.getID());
		requestJSONObject.put("Name", customPropertyName);
		requestJSONObject.put("ProjectTemplateId", projectTemplateID);

		spiraCustomProperties = getSpiraCustomProperties(
			spiraProject, spiraArtifactClass);

		Integer positionNumber = _getNextPositionNumber(spiraCustomProperties);

		if (positionNumber == null) {
			throw new RuntimeException("Could not find valid position number");
		}

		requestJSONObject.put("PropertyNumber", positionNumber);

		try {
			SpiraRestAPIUtil.requestJSONObject(
				"project-templates/{project_template_id}/custom-properties",
				urlParameters, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONObject.toString());

			SpiraCustomProperty spiraCustomProperty =
				spiraProject.getSpiraCustomPropertyByName(
					spiraArtifactClass, customPropertyName);

			SearchQuery.clearSearchQueries(SpiraCustomProperty.class);

			return spiraCustomProperty;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static SpiraCustomProperty.Value createSpiraCustomPropertyValue(
		SpiraCustomProperty spiraCustomProperty, String value) {

		SpiraCustomProperty.Value spiraCustomPropertyValue =
			spiraCustomProperty.getSpiraCustomPropertyValue(value);

		if (spiraCustomPropertyValue != null) {
			return spiraCustomPropertyValue;
		}

		SpiraCustomList.Value spiraCustomListValue =
			SpiraCustomList.createSpiraCustomListValue(
				spiraCustomProperty.getSpiraProject(),
				spiraCustomProperty.getSpiraCustomList(), value);

		spiraCustomPropertyValue = new SpiraCustomProperty.Value(
			spiraCustomListValue, spiraCustomProperty);

		spiraCustomProperty._addSpiraCustomPropertyValue(
			spiraCustomPropertyValue);

		return spiraCustomPropertyValue;
	}

	public static void deleteSpiraCustomPropertyByName(
		final SpiraProject spiraProject,
		final Class<? extends SpiraArtifact> spiraArtifactClass,
		String customPropertyName) {

		List<SpiraCustomProperty> spiraCustomProperties =
			getSpiraCustomProperties(
				spiraProject, spiraArtifactClass,
				new SearchQuery.SearchParameter("Name", customPropertyName));

		if (spiraCustomProperties.isEmpty()) {
			return;
		}

		for (SpiraCustomProperty spiraCustomProperty : spiraCustomProperties) {
			Map<String, String> urlPathReplacements = new HashMap<>();

			urlPathReplacements.put(
				"custom_property_id",
				String.valueOf(spiraCustomProperty.getID()));
			urlPathReplacements.put(
				"project_template_id",
				String.valueOf(spiraProject.getProjectTemplateID()));

			try {
				SpiraRestAPIUtil.request(
					"project-templates/{project_template_id}" +
						"/custom-properties/{custom_property_id}",
					null, urlPathReplacements, HttpRequestMethod.DELETE, null);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
	}

	public int getArtifactTypeId() {
		return jsonObject.getInt("ArtifactTypeId");
	}

	public int getPropertyNumber() {
		return jsonObject.getInt("PropertyNumber");
	}

	public SpiraCustomList getSpiraCustomList() {
		if (!_hasCustomList()) {
			return null;
		}

		JSONObject customListJSONObject = jsonObject.getJSONObject(
			"CustomList");

		return SpiraCustomList.createSpiraCustomListByName(
			getSpiraProject(), _spiraArtifactClass,
			customListJSONObject.getString("Name"));
	}

	public SpiraCustomProperty.Value getSpiraCustomPropertyValue(String value) {
		if (!_hasCustomList()) {
			return new SpiraCustomProperty.Value(value, this);
		}

		for (SpiraCustomProperty.Value spiraCustomPropertyValue :
				getSpiraCustomPropertyValues()) {

			if (!Objects.equals(value, spiraCustomPropertyValue.getName())) {
				continue;
			}

			return spiraCustomPropertyValue;
		}

		return null;
	}

	public List<SpiraCustomProperty.Value> getSpiraCustomPropertyValues() {
		if (!_hasCustomList()) {
			return null;
		}

		if (_spiraCustomPropertyValues != null) {
			return _spiraCustomPropertyValues;
		}

		_spiraCustomPropertyValues = new ArrayList<>();

		SpiraCustomList spiraCustomList = getSpiraCustomList();

		for (SpiraCustomList.Value spiraCustomListValue :
				spiraCustomList.getSpiraCustomListValues()) {

			_spiraCustomPropertyValues.add(
				new SpiraCustomProperty.Value(spiraCustomListValue, this));
		}

		return _spiraCustomPropertyValues;
	}

	public SpiraCustomProperty.Type getType() {
		return Type.get(jsonObject.getInt("CustomPropertyTypeId"));
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, "/pt/",
			String.valueOf(spiraProject.getProjectTemplateID()),
			"/Administration/CustomProperties.aspx?artifactTypeId=",
			String.valueOf(getArtifactTypeId()));
	}

	public static enum Type {

		BOOLEAN(4), DATE(5), DECIMAL(3), INTEGER(2), LIST(6), MULTILIST(7),
		TEXT(1), USER(8);

		public static Type get(Integer id) {
			return _types.get(id);
		}

		public Integer getID() {
			return _id;
		}

		private Type(Integer id) {
			_id = id;
		}

		private static Map<Integer, Type> _types = new HashMap<>();

		static {
			for (Type type : values()) {
				_types.put(type.getID(), type);
			}
		}

		private final Integer _id;

	}

	public static class Value extends BaseSpiraArtifact {

		public SpiraCustomProperty getSpiraCustomProperty() {
			return _spiraCustomProperty;
		}

		@Override
		public String getURL() {
			SpiraCustomProperty spiraCustomProperty = getSpiraCustomProperty();

			return spiraCustomProperty.getURL();
		}

		protected Value(
			SpiraCustomList.Value spiraCustomValue,
			SpiraCustomProperty spiraCustomProperty) {

			super(spiraCustomValue.toJSONObject());

			_spiraCustomProperty = spiraCustomProperty;
		}

		protected Value(String name, SpiraCustomProperty spiraCustomProperty) {
			super(new JSONObject("{\"Name\":\"" + name + "\"}"));

			_spiraCustomProperty = spiraCustomProperty;
		}

		protected static final String ID_KEY = "CustomPropertyValueId";

		private final SpiraCustomProperty _spiraCustomProperty;

	}

	protected static List<SpiraCustomProperty> getSpiraCustomProperties(
		final SpiraProject spiraProject,
		final Class<? extends SpiraArtifact> spiraArtifactClass,
		SearchQuery.SearchParameter... searchParameters) {

		SearchQuery.SearchParameter[] customSearchParameters =
			new SearchQuery.SearchParameter[searchParameters.length + 2];

		customSearchParameters[0] = new SearchQuery.SearchParameter(
			SpiraProject.ID_KEY, spiraProject.getID());
		customSearchParameters[1] = new SearchQuery.SearchParameter(
			"ArtifactTypeName", getArtifactTypeName(spiraArtifactClass));

		for (int i = 0; i < searchParameters.length; i++) {
			customSearchParameters[i + 2] = searchParameters[i];
		}

		return getSpiraArtifacts(
			SpiraCustomProperty.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraCustomProperties(
						spiraProject, spiraArtifactClass);
				}

			},
			new Function<JSONObject, SpiraCustomProperty>() {

				@Override
				public SpiraCustomProperty apply(JSONObject jsonObject) {
					return new SpiraCustomProperty(
						jsonObject, spiraProject, spiraArtifactClass);
				}

			},
			customSearchParameters);
	}

	protected void addSpiraCustomListValue(
		SpiraCustomList.Value spiraCustomListValue) {

		JSONObject customListJSONObject = jsonObject.optJSONObject(
			"CustomList");

		if (customListJSONObject == null) {
			customListJSONObject = new JSONObject();

			customListJSONObject.put("Values", new JSONArray());

			jsonObject.put("CustomList", customListJSONObject);
		}

		JSONArray valuesJSONArray = customListJSONObject.getJSONArray("Values");

		valuesJSONArray.put(spiraCustomListValue.toJSONObject());
	}

	protected static final String ARTIFACT_TYPE_NAME = "customproperty";

	protected static final String ID_KEY = "CustomPropertyId";

	private static Integer _getNextPositionNumber(
		List<SpiraCustomProperty> spiraCustomProperties) {

		for (int i = 1; i <= spiraCustomProperties.size(); i++) {
			SpiraCustomProperty positionedSpiraCustomProperty = null;

			for (SpiraCustomProperty spiraCustomProperty :
					spiraCustomProperties) {

				if (spiraCustomProperty.getPropertyNumber() != i) {
					continue;
				}

				positionedSpiraCustomProperty = spiraCustomProperty;

				break;
			}

			if (positionedSpiraCustomProperty == null) {
				return i;
			}
		}

		return spiraCustomProperties.size() + 1;
	}

	private static List<JSONObject> _requestSpiraCustomProperties(
		SpiraProject spiraProject,
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		List<JSONObject> spiraCustomProperties = new ArrayList<>();

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"artifact_type_name", getArtifactTypeName(spiraArtifactClass));
		urlPathReplacements.put(
			"project_template_id",
			String.valueOf(spiraProject.getProjectTemplateID()));

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"project-templates/{project_template_id}/custom-properties" +
					"/{artifact_type_name}",
				null, urlPathReplacements, HttpRequestMethod.GET, null);

			for (int i = 0; i < responseJSONArray.length(); i++) {
				JSONObject responseJSONObject = responseJSONArray.getJSONObject(
					i);

				responseJSONObject.put(
					SpiraProject.ID_KEY, spiraProject.getID());

				spiraCustomProperties.add(responseJSONObject);
			}

			return spiraCustomProperties;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraCustomProperty(
		JSONObject jsonObject, SpiraProject spiraProject,
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		super(jsonObject);

		_spiraArtifactClass = spiraArtifactClass;

		jsonObject.put(
			"ArtifactTypeName", getArtifactTypeName(spiraArtifactClass));
		jsonObject.put("ProjectId", spiraProject.getID());

		cacheSpiraArtifact(SpiraCustomProperty.class, this);
	}

	private void _addSpiraCustomPropertyValue(
		SpiraCustomProperty.Value spiraCustomPropertyValue) {

		List<SpiraCustomProperty.Value> spiraCustomPropertyValues =
			getSpiraCustomPropertyValues();

		spiraCustomPropertyValues.add(spiraCustomPropertyValue);
	}

	private boolean _hasCustomList() {
		if ((getType() != Type.LIST) && (getType() != Type.MULTILIST)) {
			return false;
		}

		if (jsonObject.optJSONObject("CustomList") == null) {
			return false;
		}

		return true;
	}

	private final Class<? extends SpiraArtifact> _spiraArtifactClass;
	private List<SpiraCustomProperty.Value> _spiraCustomPropertyValues;

}