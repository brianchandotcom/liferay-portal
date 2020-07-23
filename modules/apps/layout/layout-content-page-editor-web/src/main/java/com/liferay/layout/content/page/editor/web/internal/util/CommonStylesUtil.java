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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Pavel Savinov
 */
public class CommonStylesUtil {

	public static List<String> getAvailableStyleNames() throws Exception {
		if (_availableStyleNames != null) {
			return _availableStyleNames;
		}

		List<String> availableStyleNames = new ArrayList<>();

		JSONArray jsonArray = getCommongStylesJSONArray(null);

		Iterator<JSONObject> iterator = jsonArray.iterator();

		iterator.forEachRemaining(
			jsonObject -> {
				JSONArray stylesJSONArray = jsonObject.getJSONArray("styles");

				Iterator<JSONObject> stylesIterator =
					stylesJSONArray.iterator();

				stylesIterator.forEachRemaining(
					styleJSONObject -> availableStyleNames.add(
						styleJSONObject.getString("name")));
			});

		_availableStyleNames = availableStyleNames;

		return _availableStyleNames;
	}

	public static JSONArray getCommongStylesJSONArray(
			ResourceBundle resourceBundle)
		throws Exception {

		if (_commonStylesJSONArray != null) {
			return _commonStylesJSONArray;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			new String(
				FileUtil.getBytes(
					CommonStylesUtil.class, "common-styles.json")));

		Iterator<JSONObject> iterator = jsonArray.iterator();

		iterator.forEachRemaining(
			jsonObject -> {
				jsonObject.put(
					"label",
					LanguageUtil.get(
						resourceBundle, jsonObject.getString("label")));

				JSONArray stylesJSONArray = jsonObject.getJSONArray("styles");

				Iterator<JSONObject> stylesIterator =
					stylesJSONArray.iterator();

				stylesIterator.forEachRemaining(
					styleJSONObject -> {
						styleJSONObject.put(
							"label",
							LanguageUtil.get(
								resourceBundle,
								styleJSONObject.getString("label")));

						JSONArray validValuesJSONArray =
							styleJSONObject.getJSONArray("validValues");

						if (validValuesJSONArray != null) {
							Iterator<JSONObject> validValuesIterator =
								validValuesJSONArray.iterator();

							validValuesIterator.forEachRemaining(
								validValueJSONObject ->
									validValueJSONObject.put(
										"label",
										LanguageUtil.get(
											resourceBundle,
											validValueJSONObject.getString(
												"label"))));
						}
					});
			});

		if (resourceBundle != null) {
			_commonStylesJSONArray = jsonArray;
		}

		return jsonArray;
	}

	public static Map<String, Object> getDefaultStyleValues() throws Exception {
		if (_defaultValues != null) {
			return _defaultValues;
		}

		Map<String, Object> defaultValues = new HashMap<>();

		JSONArray jsonArray = getCommongStylesJSONArray(null);

		Iterator<JSONObject> iterator = jsonArray.iterator();

		iterator.forEachRemaining(
			jsonObject -> {
				JSONArray stylesJSONArray = jsonObject.getJSONArray("styles");

				Iterator<JSONObject> stylesIterator =
					stylesJSONArray.iterator();

				stylesIterator.forEachRemaining(
					styleJSONObject -> defaultValues.put(
						styleJSONObject.getString("name"),
						styleJSONObject.get("defaultValue")));
			});

		_defaultValues = defaultValues;

		return _defaultValues;
	}

	private static List<String> _availableStyleNames;
	private static JSONArray _commonStylesJSONArray;
	private static Map<String, Object> _defaultValues;

}