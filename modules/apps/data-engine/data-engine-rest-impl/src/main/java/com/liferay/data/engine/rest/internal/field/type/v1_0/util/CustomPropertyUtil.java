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

import com.liferay.data.engine.rest.dto.v1_0.CustomProperty;
import com.liferay.data.engine.rest.internal.field.type.v1_0.DataFieldOption;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Marcela Cunha
 */
public class CustomPropertyUtil {

	public static CustomProperty[] add(
		CustomProperty[] customProperties, String key, Object value) {

		CustomProperty customProperty = new CustomProperty();

		customProperty.setKey(key);
		customProperty.setValue(value);

		if (customProperties == null) {
			customProperties = new CustomProperty[] {customProperty};

			return customProperties;
		}

		return ArrayUtil.append(customProperties, customProperty);
	}

	public static Boolean getBoolean(
		CustomProperty[] customProperties, String key, boolean defaultValue) {

		if (ArrayUtil.isEmpty(customProperties)) {
			return defaultValue;
		}

		for (CustomProperty customProperty : customProperties) {
			if (StringUtils.equals(key, customProperty.getKey())) {
				return GetterUtil.getBoolean(customProperty.getValue());
			}
		}

		return defaultValue;
	}

	public static List<DataFieldOption> getDataFieldOptions(
		CustomProperty[] customProperties, String key) {

		if (ArrayUtil.isEmpty(customProperties)) {
			return Collections.emptyList();
		}

		for (CustomProperty customProperty : customProperties) {
			if (StringUtils.equals(key, customProperty.getKey())) {
				return (List<DataFieldOption>)customProperty.getValue();
			}
		}

		return Collections.emptyList();
	}

	public static Long getLong(CustomProperty[] customProperties, String key) {
		if (ArrayUtil.isEmpty(customProperties)) {
			return Long.valueOf(0);
		}

		for (CustomProperty customProperty : customProperties) {
			if (StringUtils.equals(key, customProperty.getKey())) {
				return GetterUtil.getLong(customProperty.getValue());
			}
		}

		return Long.valueOf(0);
	}

	public static <K, V> Map<K, V> getMap(
		CustomProperty[] customProperties, String key) {

		if (ArrayUtil.isEmpty(customProperties)) {
			return Collections.emptyMap();
		}

		for (CustomProperty customProperty : customProperties) {
			if (StringUtils.equals(key, customProperty.getKey())) {
				return (Map<K, V>)customProperty.getValue();
			}
		}

		return Collections.emptyMap();
	}

	public static String getString(
		CustomProperty[] customProperties, String key) {

		return getString(customProperties, key, StringPool.BLANK);
	}

	public static String getString(
		CustomProperty[] customProperties, String key, String defaultValue) {

		if (ArrayUtil.isEmpty(customProperties)) {
			return defaultValue;
		}

		for (CustomProperty customProperty : customProperties) {
			if (StringUtils.equals(key, customProperty.getKey())) {
				return GetterUtil.getString(customProperty.getValue());
			}
		}

		return defaultValue;
	}

	public static List<String> getValues(
		CustomProperty[] customProperties, String key) {

		String json = getString(customProperties, key, "[]");

		JSONArray jsonArray = null;

		try {
			jsonArray = JSONFactoryUtil.createJSONArray(json);
		}
		catch (JSONException jsone) {
			jsonArray = JSONFactoryUtil.createJSONArray();
		}

		return JSONUtil.toStringList(jsonArray);
	}

	public static JSONObject toJSONObject(Map<String, String> values) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (values.isEmpty()) {
			return jsonObject;
		}

		for (Map.Entry<String, String> entry : values.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

	public static Map<String, String> toMap(JSONObject jsonObject) {
		Map<String, String> values = new HashMap<>();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			values.put(key, jsonObject.getString(key));
		}

		return values;
	}

}