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
public class IntegerSpiraCustomPropertyValue extends SpiraCustomPropertyValue {

	public int getIntegerValue() {
		return jsonObject.optInt("IntegerValue");
	}

	@Override
	public String getValue() {
		return String.valueOf(getIntegerValue());
	}

	protected IntegerSpiraCustomPropertyValue(
		Integer integerValue, SpiraCustomProperty spiraCustomProperty) {

		super(new JSONObject(), spiraCustomProperty);

		jsonObject.put("Definition", getDefinitionJSONObject());
		jsonObject.put("IntegerValue", integerValue);
	}

	protected IntegerSpiraCustomPropertyValue(
		JSONObject jsonObject, SpiraCustomProperty spiraCustomProperty) {

		super(jsonObject, spiraCustomProperty);
	}

	@Override
	protected JSONObject getCustomPropertyJSONObject() {
		JSONObject customPropertyJSONObject = new JSONObject();

		customPropertyJSONObject.put("IntegerValue", getIntegerValue());
		customPropertyJSONObject.put("PropertyNumber", getPropertyNumber());

		return customPropertyJSONObject;
	}

	@Override
	protected JSONObject getFilterJSONObject() {
		JSONObject filterJSONObject = new JSONObject();

		filterJSONObject.put("IntegerValue", getIntegerValue());

		return filterJSONObject;
	}

	@Override
	protected boolean matchesJSONObject(JSONObject customPropertyJSONObject) {
		if (customPropertyJSONObject.optInt("IntegerValue") ==
				getIntegerValue()) {

			return true;
		}

		return false;
	}

}