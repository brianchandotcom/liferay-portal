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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class SpiraCustomPropertyValue<T> extends BaseSpiraArtifact {

	public static SpiraCustomPropertyValue<?> createSpiraCustomPropertyValue(
		SpiraCustomProperty spiraCustomProperty, String value) {

		if (value == null) {
			throw new RuntimeException("Invalid value " + value);
		}

		SpiraCustomProperty.Type spiraCustomPropertyType =
			spiraCustomProperty.getType();

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.INTEGER) {
			if (!value.matches("\\d+")) {
				throw new RuntimeException("Invalid integer value " + value);
			}

			return new IntegerSpiraCustomPropertyValue(
				Integer.valueOf(value), spiraCustomProperty);
		}

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.LIST) {
			return new ListSpiraCustomPropertyValue(
				SpiraCustomListValue.createSpiraCustomListValue(
					spiraCustomProperty.getSpiraProject(),
					spiraCustomProperty.getSpiraCustomList(), value),
				spiraCustomProperty);
		}

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.MULTILIST) {
			return new MultiListSpiraCustomPropertyValue(
				SpiraCustomListValue.createSpiraCustomListValue(
					spiraCustomProperty.getSpiraProject(),
					spiraCustomProperty.getSpiraCustomList(), value),
				spiraCustomProperty);
		}

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.TEXT) {
			return new TextSpiraCustomPropertyValue(value, spiraCustomProperty);
		}

		throw new RuntimeException(
			"Unsupported custom property type " + spiraCustomPropertyType);
	}

	public static SpiraCustomPropertyValue getSpiraCustomPropertyValue(
		SpiraCustomProperty spiraCustomProperty, JSONObject valueJSONObject) {

		SpiraCustomProperty.Type spiraCustomPropertyType =
			spiraCustomProperty.getType();

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.INTEGER) {
			return new IntegerSpiraCustomPropertyValue(
				valueJSONObject, spiraCustomProperty);
		}

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.LIST) {
			return new ListSpiraCustomPropertyValue(
				valueJSONObject, spiraCustomProperty);
		}

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.MULTILIST) {
			return new MultiListSpiraCustomPropertyValue(
				valueJSONObject, spiraCustomProperty);
		}

		if (spiraCustomPropertyType == SpiraCustomProperty.Type.TEXT) {
			return new TextSpiraCustomPropertyValue(
				valueJSONObject, spiraCustomProperty);
		}

		return null;
	}

	public int getPropertyNumber() {
		SpiraCustomProperty spiraCustomProperty = getSpiraCustomProperty();

		return spiraCustomProperty.getPropertyNumber();
	}

	public SpiraCustomProperty getSpiraCustomProperty() {
		return _spiraCustomProperty;
	}

	public abstract SpiraCustomProperty.Type getSpiraCustomPropertyType();

	@Override
	public SpiraProject getSpiraProject() {
		SpiraCustomProperty spiraCustomProperty = getSpiraCustomProperty();

		return spiraCustomProperty.getSpiraProject();
	}

	@Override
	public String getURL() {
		SpiraCustomProperty spiraCustomProperty = getSpiraCustomProperty();

		return spiraCustomProperty.getURL();
	}

	public abstract T getValue();

	public String getValueString() {
		return String.valueOf(getValue());
	}

	protected SpiraCustomPropertyValue(
		JSONObject jsonObject, SpiraCustomProperty spiraCustomProperty) {

		super(jsonObject);

		_spiraCustomProperty = spiraCustomProperty;
	}

	protected abstract JSONObject getCustomPropertyJSONObject();

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

	protected abstract JSONObject getFilterJSONObject();

	protected abstract boolean matchesJSONObject(
		JSONObject customPropertyJSONObject);

	protected static final String KEY_ID = "CustomPropertyValueId";

	private final SpiraCustomProperty _spiraCustomProperty;

}