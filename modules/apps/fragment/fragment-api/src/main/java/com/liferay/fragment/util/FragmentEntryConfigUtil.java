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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryConfigUtil {

	public static final String CONTEXT_OBJECT_SUFFIX = "Object";

	public static JSONObject getConfigurationDefaultValuesJSONObject(
		String configuration) {

		List<FragmentConfigurationField> configurationFields =
			getFragmentConfigurationFields(configuration);

		JSONObject defaultValuesJSONObject = JSONFactoryUtil.createJSONObject();

		for (FragmentConfigurationField configurationField :
				configurationFields) {

			defaultValuesJSONObject.put(
				configurationField.getName(),
				getFieldValue(configurationField, null));
		}

		return defaultValuesJSONObject;
	}

	public static Map<String, Object> getContextObjects(
		JSONObject configurationValuesJSONObject, String configuration) {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			getFragmentConfigurationFields(configuration);

		HashMap<String, Object> contextObjects = new HashMap<>();

		for (FragmentConfigurationField fragmentConfigurationField :
				fragmentConfigurationFields) {

			String name = fragmentConfigurationField.getName();

			String string = configurationValuesJSONObject.getString(name);

			Object contextObject = _getContextObject(
				fragmentConfigurationField.getType(), string);

			if (contextObject != null) {
				contextObjects.put(name + CONTEXT_OBJECT_SUFFIX, contextObject);
			}
		}

		return Collections.unmodifiableMap(contextObjects);
	}

	public static Object getFieldValue(
		FragmentConfigurationField configurationField, String value) {

		String dataType = configurationField.getDataType();

		value = GetterUtil.getString(
			value, configurationField.getDefaultValue());

		if (StringUtil.equalsIgnoreCase(
				configurationField.getType(), "checkbox")) {

			return _getFieldValue("bool", value);
		}
		else if (StringUtil.equalsIgnoreCase(
					configurationField.getType(), "colorPalette")) {

			return _getFieldValue("object", value);
		}
		else if (StringUtil.equalsIgnoreCase(
					configurationField.getType(), "itemSelector")) {

			return _getAssetJSONObject(value);
		}
		else if (StringUtil.equalsIgnoreCase(
					configurationField.getType(), "select")) {

			if (Validator.isNull(dataType)) {
				_log.error(
					configurationField.getName() + "field has a null dataType");

				return null;
			}

			return _getFieldValue(dataType, value);
		}

		return _getFieldValue("string", value);
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

                configurationFields.add(
                    new FragmentConfigurationField(
                        configurationFieldSetFieldsJSONArray.getJSONObject(j)));
			}
		}

		return Collections.unmodifiableList(configurationFields);
	}

	private static Object _getAsset(String value) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			String className = GetterUtil.getString(
				jsonObject.getString("className"));

			long classPK = GetterUtil.getLong(jsonObject.getString("classPK"));

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				className, classPK);

			if (assetEntry != null) {
				AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

				return assetRenderer.getAssetObject();
			}
		}
		catch (Exception e) {
			_log.error("Unable to obtain asset: " + value, e);
		}

		return null;
	}

	private static JSONObject _getAssetJSONObject(String value) {
		try {
			JSONObject configurationValue = JSONFactoryUtil.createJSONObject(
				value);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(_getAsset(value)));

			jsonObject.put(
				"className",
				GetterUtil.getString(configurationValue.getString("className"))
			).put(
				"classPK",
				GetterUtil.getLong(configurationValue.getString("classPK"))
			);

			return jsonObject;
		}
		catch (JSONException jsone) {
			_log.error("Unable to serialize asset to json: " + value, jsone);
		}

		return null;
	}

	private static Object _getContextObject(String type, String value) {
		if (StringUtil.equalsIgnoreCase(type, "itemSelector")) {
			return _getAsset(value);
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