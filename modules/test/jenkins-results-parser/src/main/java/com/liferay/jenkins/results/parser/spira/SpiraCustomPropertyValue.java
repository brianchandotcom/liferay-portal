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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraCustomPropertyValue extends BaseSpiraArtifact {

	public static SpiraCustomPropertyValue createSpiraCustomPropertyValue(
		SpiraCustomProperty spiraCustomProperty, String value) {

		SpiraCustomPropertyValue spiraCustomPropertyValue =
			spiraCustomProperty.getSpiraCustomPropertyValue(value);

		if (spiraCustomPropertyValue != null) {
			return spiraCustomPropertyValue;
		}

		SpiraCustomListValue spiraCustomListValue =
			SpiraCustomListValue.createSpiraCustomListValue(
				spiraCustomProperty.getSpiraProject(),
				spiraCustomProperty.getSpiraCustomList(), value);

		spiraCustomPropertyValue = new SpiraCustomPropertyValue(
			spiraCustomListValue, spiraCustomProperty);

		spiraCustomProperty.addSpiraCustomPropertyValue(
			spiraCustomPropertyValue);

		return spiraCustomPropertyValue;
	}

	public static List<SpiraCustomPropertyValue> getSpiraCustomPropertyValues(
		SpiraCustomProperty spiraCustomProperty, JSONObject valueJSONObject) {

		SpiraCustomProperty.Type spiraCustomPropertyType =
			spiraCustomProperty.getType();

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.LIST) {
			Object integerValue = valueJSONObject.get("IntegerValue");

			if (!(integerValue instanceof Integer)) {
				return null;
			}

			SpiraCustomList spiraCustomList = _getSpiraCustomList(
				spiraCustomProperty, valueJSONObject);

			SpiraCustomListValue spiraCustomListValue =
				spiraCustomList.getSpiraCustomListValueByID(
					(Integer)integerValue);

			return Arrays.asList(
				new SpiraCustomPropertyValue(
					spiraCustomListValue, spiraCustomProperty));
		}

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.MULTILIST) {
			Object integerListValue = valueJSONObject.get("IntegerListValue");

			if (!(integerListValue instanceof JSONArray)) {
				return null;
			}

			JSONArray integerListValueJSONArray = (JSONArray)integerListValue;

			List<SpiraCustomPropertyValue> spiraCustomPropertyValues =
				new ArrayList<>();

			for (int i = 0; i < integerListValueJSONArray.length(); i++) {
				int integerValue = integerListValueJSONArray.getInt(i);

				if (integerValue <= 0) {
					continue;
				}

				SpiraCustomList spiraCustomList = _getSpiraCustomList(
					spiraCustomProperty, valueJSONObject);

				SpiraCustomListValue spiraCustomListValue =
					spiraCustomList.getSpiraCustomListValueByID(integerValue);

				spiraCustomPropertyValues.add(
					new SpiraCustomPropertyValue(
						spiraCustomListValue, spiraCustomProperty));
			}

			return spiraCustomPropertyValues;
		}

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.TEXT) {
			Object stringValue = valueJSONObject.get("StringValue");

			if (!(stringValue instanceof String)) {
				return null;
			}

			return Arrays.asList(
				new SpiraCustomPropertyValue(
					(String)stringValue, spiraCustomProperty));
		}

		return null;
	}

	public SpiraCustomProperty getSpiraCustomProperty() {
		return _spiraCustomProperty;
	}

	@Override
	public String getURL() {
		SpiraCustomProperty spiraCustomProperty = getSpiraCustomProperty();

		return spiraCustomProperty.getURL();
	}

	protected SpiraCustomPropertyValue(
		JSONObject jsonObject, SpiraCustomProperty spiraCustomProperty) {

		super(jsonObject);

		_spiraCustomProperty = spiraCustomProperty;
	}

	protected SpiraCustomPropertyValue(
		SpiraCustomListValue spiraCustomListValue,
		SpiraCustomProperty spiraCustomProperty) {

		super(spiraCustomListValue.toJSONObject());

		_spiraCustomProperty = spiraCustomProperty;
	}

	protected SpiraCustomPropertyValue(
		String propertyValue, SpiraCustomProperty spiraCustomProperty) {

		super(new JSONObject());

		jsonObject.put("Name", propertyValue);

		_spiraCustomProperty = spiraCustomProperty;
	}

	protected JSONObject getDefinitionJSONObject() {
		SpiraProject spiraProject = getSpiraProject();

		SpiraCustomProperty spiraCustomProperty = getSpiraCustomProperty();

		SpiraCustomProperty.Type spiraCustomPropertyType =
			spiraCustomProperty.getType();

		JSONObject definitionJSONObject = new JSONObject();

		definitionJSONObject.put(
			"ArtifactTypeId", spiraCustomProperty.getArtifactTypeId());
		definitionJSONObject.put(
			"CustomPropertyId", spiraCustomProperty.getID());
		definitionJSONObject.put(
			"CustomPropertyTypeId", spiraCustomPropertyType.getID());
		definitionJSONObject.put(
			"CustomPropertyTypeName", spiraCustomPropertyType.getName());
		definitionJSONObject.put("Name", spiraCustomProperty.getName());
		definitionJSONObject.put(
			"ProjectTemplateId", spiraProject.getProjectTemplateID());
		definitionJSONObject.put(
			"PropertyNumber", spiraCustomProperty.getPropertyNumber());

		return definitionJSONObject;
	}

	protected static final String KEY_ID = "CustomPropertyValueId";

	private static SpiraCustomList _getSpiraCustomList(
		SpiraCustomProperty spiraCustomProperty, JSONObject valueJSONObject) {

		JSONObject definitionJSONObject = valueJSONObject.getJSONObject(
			"Definition");

		List<SpiraCustomList> spiraCustomLists =
			SpiraCustomList.getSpiraCustomLists(
				spiraCustomProperty.getSpiraProject(),
				spiraCustomProperty.getSpiraArtifactClass(),
				new SearchQuery.SearchParameter(
					"Name", definitionJSONObject.getString("Name")));

		return spiraCustomLists.get(0);
	}

	private final SpiraCustomProperty _spiraCustomProperty;

}