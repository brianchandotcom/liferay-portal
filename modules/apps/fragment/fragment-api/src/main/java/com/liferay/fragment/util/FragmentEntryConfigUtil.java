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

/**
 * @author Rubén Pulido
 */
public class FragmentEntryConfigUtil {

	public static JSONObject getConfigurationDefaultValuesJSONObject(
		String configuration) {

		JSONObject defaultValuesJSONObject = JSONFactoryUtil.createJSONObject();

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

		JSONArray fieldSetsJSONArray = configurationJSONObject.getJSONArray(
			"fieldSets");

		if (fieldSetsJSONArray == null) {
			return null;
		}

		for (int i = 0; i < fieldSetsJSONArray.length(); i++) {
			JSONObject configurationFieldSetJSONObject =
				fieldSetsJSONArray.getJSONObject(i);

			JSONObject defaultValuesFieldSetJSONObject =
				JSONFactoryUtil.createJSONObject();

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

				defaultValuesFieldSetJSONObject.put(
					configurationFieldSetFieldJSONObject.getString("name"),
					fieldDefaultValue);
			}

			defaultValuesJSONObject.put(
				configurationFieldSetJSONObject.getString("name"),
				defaultValuesFieldSetJSONObject);
		}

		return defaultValuesJSONObject;
	}

	private static Object _getFieldValue(String dataType, String value) {
		if (dataType.equals("double")) {
			return GetterUtil.getDouble(value);
		}
		else if (dataType.equals("int")) {
			return GetterUtil.getInteger(value);
		}
		else if (dataType.equals("string")) {
			return value;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryConfigUtil.class);

}