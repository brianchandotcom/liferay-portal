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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.util.JsonHelper;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(enabled = false, immediate = true, service = JsonHelper.class)
public class JsonHelperImpl implements JsonHelper {

	public JsonHelperImpl() {
	}

	@Override
	public JSONArray getValueAsJSONArray(String key, JSONObject jsonObject) {
		JSONArray valueJSONArray = jsonObject.getJSONArray(key);

		if (valueJSONArray != null) {
			return valueJSONArray;
		}

		valueJSONArray = _jsonFactory.createJSONArray();

		String valueString = jsonObject.getString(key);

		if (valueString == null) {
			return valueJSONArray;
		}

		valueJSONArray.put(valueString);

		return valueJSONArray;
	}

	@Override
	public JSONArray toJSONArray(Map<String, List<String>> keyValues) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (Map.Entry<String, List<String>> keyValuesEntry :
				keyValues.entrySet()) {

			JSONObject arrayEntryJSONObject = _jsonFactory.createJSONObject();

			arrayEntryJSONObject.put("key", keyValuesEntry.getKey());

			JSONArray valuesJSONArray = _jsonFactory.createJSONArray();

			List<String> values = keyValuesEntry.getValue();

			for (String value : values) {
				valuesJSONArray.put(value);
			}

			arrayEntryJSONObject.put("value", valuesJSONArray);

			jsonArray.put(arrayEntryJSONObject);
		}

		return jsonArray;
	}

	@Reference
	private JSONFactory _jsonFactory;

}