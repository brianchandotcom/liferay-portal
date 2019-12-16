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

package com.liferay.data.engine.rest.internal.field.type.v1_0.util;

import com.liferay.data.engine.rest.internal.field.type.v1_0.DataFieldOption;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class DataFieldOptionUtil {

	public static List<DataFieldOption> getLocalizedDataFieldOptions(
		Map<String, Object> customProperties, String key, String languageId) {

		if (MapUtil.isEmpty(customProperties) ||
			!customProperties.containsKey(key)) {

			return Collections.emptyList();
		}

		Map<String, List<DataFieldOption>> localizedDataFieldOptions =
			(Map<String, List<DataFieldOption>>)customProperties.get(key);

		return (List<DataFieldOption>)GetterUtil.getObject(
			localizedDataFieldOptions.get(languageId), Collections.emptyList());
	}

	public static JSONObject toJSONObject(
		Map<String, Object> customProperties, String key) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (MapUtil.isEmpty(customProperties) ||
			!customProperties.containsKey(key)) {

			return jsonObject;
		}

		Map<String, List<Map<String, String>>> customPropertyOptions =
			(Map<String, List<Map<String, String>>>)customProperties.get(key);

		for (Map.Entry<String, List<Map<String, String>>> entry :
				customPropertyOptions.entrySet()) {

			List<Map<String, String>> options = entry.getValue();

			JSONArray dataFieldOptionsJSONArray =
				JSONFactoryUtil.createJSONArray();

			for (Map<String, String> option : options) {
				JSONObject optionJSONObject =
					JSONFactoryUtil.createJSONObject();

				dataFieldOptionsJSONArray.put(
					optionJSONObject.put(
						"label", MapUtil.getString(option, "label")
					).put(
						"value", MapUtil.getString(option, "value")
					));
			}

			jsonObject.put(entry.getKey(), dataFieldOptionsJSONArray);
		}

		return jsonObject;
	}

	public static Map<String, List<DataFieldOption>>
		toLocalizedDataFieldOptions(JSONObject jsonObject) {

		Map<String, List<DataFieldOption>> localizedDataFieldOptions =
			new HashMap<>();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			List<DataFieldOption> dataFieldOptions = new ArrayList<>();

			JSONArray jsonArray = jsonObject.getJSONArray(key);

			for (int i = 0; i < jsonArray.length(); i++) {
				dataFieldOptions.add(
					_toDataFieldOption(jsonArray.getJSONObject(i)));
			}

			localizedDataFieldOptions.put(key, dataFieldOptions);
		}

		return localizedDataFieldOptions;
	}

	private static DataFieldOption _toDataFieldOption(JSONObject jsonObject) {
		return new DataFieldOption(
			jsonObject.getString("label"), jsonObject.getString("value"));
	}

}