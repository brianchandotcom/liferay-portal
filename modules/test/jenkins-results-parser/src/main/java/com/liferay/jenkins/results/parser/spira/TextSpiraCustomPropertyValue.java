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
public class TextSpiraCustomPropertyValue extends SpiraCustomPropertyValue {

	public String getStringValue() {
		return jsonObject.optString("StringValue");
	}

	@Override
	public String getValue() {
		return getStringValue();
	}

	protected TextSpiraCustomPropertyValue(
		JSONObject jsonObject, SpiraCustomProperty spiraCustomProperty) {

		super(jsonObject, spiraCustomProperty);
	}

	protected TextSpiraCustomPropertyValue(
		String stringValue, SpiraCustomProperty spiraCustomProperty) {

		super(new JSONObject(), spiraCustomProperty);

		jsonObject.put("Definition", getDefinitionJSONObject());
		jsonObject.put("StringValue", stringValue);
	}

	@Override
	protected JSONObject getFilterJSONObject() {
		JSONObject filterJSONObject = new JSONObject();

		filterJSONObject.put("StringValue", getStringValue());

		return filterJSONObject;
	}

	@Override
	protected boolean matchesJSONObject(JSONObject customPropertyJSONObject) {
		String stringValue = customPropertyJSONObject.optString("StringValue");

		if ((stringValue == null) || (stringValue == JSONObject.NULL)) {
			return false;
		}

		if (stringValue.equals(getStringValue())) {
			return true;
		}

		return false;
	}

}