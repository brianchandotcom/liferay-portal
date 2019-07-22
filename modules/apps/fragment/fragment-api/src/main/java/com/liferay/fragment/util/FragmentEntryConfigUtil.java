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

package com.liferay.fragment.util;

import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryConfigUtil {

	public static JSONObject getConfigurationDefaultValuesJSONObject(
		String configuration) {

		JSONObject defaultValuesJSONObject = JSONFactoryUtil.createJSONObject();

		JSONArray fieldSetsJSONArray = _getFieldSetsJSONArray(configuration);

		if (fieldSetsJSONArray == null) {
			return null;
		}

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject configurationFieldSetJSONObject =
				fieldSetsJSONArray.getJSONObject(i);

			JSONArray configurationFieldSetFieldsJSONArray =
				configurationFieldSetJSONObject.getJSONArray("fields");

			for (int j = 0; j < configurationFieldSetFieldsJSONArray.length();
				 j++) {

				JSONObject configurationFieldSetFieldJSONObject =
					configurationFieldSetFieldsJSONArray.getJSONObject(j);

				defaultValuesJSONObject.put(
					configurationFieldSetFieldJSONObject.getString("name"),
					_getFieldValue(configurationFieldSetFieldJSONObject, null));
			}
		}

		return defaultValuesJSONObject;
	}

	public static Object getFieldValue(
		String configuration, String fieldName, String value) {

		JSONObject configurationFieldSetFieldJSONObject =
			_getConfigurationFieldSetFieldJSONObject(configuration, fieldName);

		if (configurationFieldSetFieldJSONObject == null) {
			return value;
		}

		return _getFieldValue(configurationFieldSetFieldJSONObject, value);
	}
	
	public static List<FragmentConfigurationField>
		getFragmentConfigurationFields(String configuration) {

		List<FragmentConfigurationField> configurationFields =
			new ArrayList<>();

		JSONArray fieldSetsJSONArray = _getFieldSetsJSONArray(configuration);

		if (fieldSetsJSONArray == null) {
			return Collections.unmodifiableList(configurationFields);
		}

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject configurationFieldSetJSONObject =
				fieldSetsJSONArray.getJSONObject(i);

			JSONArray configurationFieldSetFieldsJSONArray =
				configurationFieldSetJSONObject.getJSONArray("fields");

			for (int j = 0; j < configurationFieldSetFieldsJSONArray.length();
				 j++) {

				JSONObject configurationFieldSetFieldJSONObject =
					configurationFieldSetFieldsJSONArray.getJSONObject(j);

				String type = configurationFieldSetFieldJSONObject.getString(
					"type");
				String name = configurationFieldSetFieldJSONObject.getString(
					"name");
				String dataType =
					configurationFieldSetFieldJSONObject.getString("dataType");
				String defaultValue =
					configurationFieldSetFieldJSONObject.getString(
						"defaultValue");

				FragmentConfigurationField configurationField =
					new FragmentConfigurationField(
						name, dataType, defaultValue, type);

				configurationFields.add(configurationField);
			}
		}

		return Collections.unmodifiableList(configurationFields);
	}

	private static JSONObject _getConfigurationFieldSetFieldJSONObject(
		String configuration, String fieldName) {

		JSONArray fieldSetsJSONArray = _getFieldSetsJSONArray(configuration);

		if (fieldSetsJSONArray == null) {
			return null;
		}

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject configurationFieldSetJSONObject =
				fieldSetsJSONArray.getJSONObject(i);

			JSONArray configurationFieldSetFieldsJSONArray =
				configurationFieldSetJSONObject.getJSONArray("fields");

			for (int j = 0; j < configurationFieldSetFieldsJSONArray.length();
				 j++) {

				JSONObject configurationFieldSetFieldJSONObject =
					configurationFieldSetFieldsJSONArray.getJSONObject(j);

				if (Objects.equals(
						fieldName,
						configurationFieldSetFieldJSONObject.get("name"))) {

					return configurationFieldSetFieldJSONObject;
				}
			}
		}

		return null;
	}

	private static JSONArray _getFieldSetsJSONArray(String configuration) {
		JSONObject configurationJSONObject = null;

		try {
			configurationJSONObject = JSONFactoryUtil.createJSONObject(
				configuration);
		}
		catch (JSONException jsone) {
			_log.error(
				"Unable to parse configuration JSON object: " + configuration,
				jsone);

			return null;
		}

		return configurationJSONObject.getJSONArray("fieldSets");
	}

	private static Object _getFieldValue(
		JSONObject configurationFieldSetFieldJSONObject, String value) {

		String dataType = configurationFieldSetFieldJSONObject.getString(
			"dataType");

		value = GetterUtil.getString(
			value,
			configurationFieldSetFieldJSONObject.getString("defaultValue"));

		if (Validator.isNotNull(dataType)) {
			return _getFieldValue(dataType, value);
		}

		if (StringUtil.equalsIgnoreCase(
				configurationFieldSetFieldJSONObject.getString("type"),
				"checkbox")) {

			return _getFieldValue("bool", value);
		}
		else if (StringUtil.equalsIgnoreCase(
					configurationFieldSetFieldJSONObject.getString("type"),
					"colorPalette")) {

			return _getFieldValue("object", value);
		}

		return _getFieldValue("string", value);
	}

	private static Object _getFieldValue(String dataType, String value) {
		if (StringUtil.equalsIgnoreCase(dataType, "bool")) {
			return GetterUtil.getBoolean(value);
		}
		else if (StringUtil.equalsIgnoreCase(dataType, "double")) {
			return GetterUtil.getDouble(value);
		}
		else if (StringUtil.equalsIgnoreCase(dataType, "int")) {
			return GetterUtil.getInteger(value);
		}
		else if (StringUtil.equalsIgnoreCase(dataType, "object")) {
			try {
				return JSONFactoryUtil.createJSONObject(value);
			}
			catch (JSONException jsone) {
				_log.error(
					"Unable to parse configuration JSON object: " + value,
					jsone);
			}
		}
		else if (StringUtil.equalsIgnoreCase(dataType, "string")) {
			return value;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryConfigUtil.class);

}