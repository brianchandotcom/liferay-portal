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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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

				Object fieldDefaultValue = _getFieldValue(
					configurationFieldSetFieldJSONObject.getString("dataType"),
					configurationFieldSetFieldJSONObject.getString(
						"defaultValue"));

				defaultValuesJSONObject.put(
					configurationFieldSetFieldJSONObject.getString("name"),
					fieldDefaultValue);
			}
		}

		return defaultValuesJSONObject;
	}

	public static Object getFieldValue(
		String configuration, String fieldName, String value) {

		String dataType = _getFieldDataType(configuration, fieldName);

		if (Validator.isNull(dataType)) {
			return value;
		}

		return _getFieldValue(dataType, value);
	}

	private static String _getFieldDataType(
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

					return configurationFieldSetFieldJSONObject.getString(
						"dataType");
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