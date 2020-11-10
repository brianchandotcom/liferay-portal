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

	public SpiraCustomProperty getSpiraCustomProperty() {
		return _spiraCustomProperty;
	}

	@Override
	public String getURL() {
		SpiraCustomProperty spiraCustomProperty = getSpiraCustomProperty();

		return spiraCustomProperty.getURL();
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

	protected static final String KEY_ID = "CustomPropertyValueId";

	private final SpiraCustomProperty _spiraCustomProperty;

}