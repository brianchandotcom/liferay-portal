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

package com.liferay.commerce.product.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
public class DDMFormValuesUtil {

	public static boolean equals(String json1, String json2)
		throws PortalException {

		JSONArray jsonArray1 = JSONFactoryUtil.createJSONArray(json1);
		JSONArray jsonArray2 = JSONFactoryUtil.createJSONArray(json2);

		if (jsonArray1.length() != jsonArray2.length()) {
			return false;
		}

		Map<String, String> map1 = _toMap(jsonArray1);
		Map<String, String> map2 = _toMap(jsonArray2);

		return map1.equals(map2);
	}

	public static JSONArray toJSONArray(Map<String, List<String>> keyValues) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Map.Entry<String, List<String>> keyValuesEntry :
				keyValues.entrySet()) {

			String key = keyValuesEntry.getKey();

			JSONObject arrayEntryJSONObject =
				JSONFactoryUtil.createJSONObject();

			arrayEntryJSONObject.put("key", key);

			List<String> values = keyValuesEntry.getValue();

			JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray();

			for (String value : values) {
				valuesJSONArray.put(value);
			}

			arrayEntryJSONObject.put("value", valuesJSONArray);

			jsonArray.put(arrayEntryJSONObject);
		}

		return jsonArray;
	}

	private static Map<String, String> _toMap(JSONArray jsonArray) {
		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString("key");
			String value = jsonObject.getString("value");

			map.put(key, value);
		}

		return map;
	}

}