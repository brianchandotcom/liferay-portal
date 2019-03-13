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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionUtil {

	public static Object getDataDefinitionFieldValue(
		DataDefinitionField dataDefinitionField, Map<String, Object> values) {

		if (dataDefinitionField.getLocalizable()) {
			return (Map<String, Object>)values.get(
				dataDefinitionField.getName());
		}
		else if (dataDefinitionField.getRepeatable()) {
			return (Object[])values.get(dataDefinitionField.getName());
		}

		return values.get(dataDefinitionField.getName());
	}

	public static DataDefinition toDataDefinition(String json)
		throws Exception {

		return new DataDefinition() {
			{
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

				dataDefinitionFields = _toDataDefinitionFields(
					jsonObject.getJSONArray("fields"));
			}
		};
	}

	public static String toJSON(DataDefinition dataDefinition)
		throws Exception {

		return JSONUtil.put(
			"fields", _toJSONArray(dataDefinition.getDataDefinitionFields())
		).toString();
	}

	private static void _put(
		JSONObject jsonObject, String key, LocalizedValue[] localizedValues) {

		JSONObject localziedValueJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (LocalizedValue localizedValue : localizedValues) {
			localziedValueJSONObject.put(
				localizedValue.getKey(), localizedValue.getValue());
		}

		jsonObject.put(key, localziedValueJSONObject);
	}

	private static DataDefinitionField _toDataDefinitionField(
			JSONObject jsonObject)
		throws Exception {

		return new DataDefinitionField() {
			{
				defaultValue = jsonObject.getString("defaultValue");

				if (!jsonObject.has("type")) {
					throw new Exception("Type is required");
				}

				fieldType = jsonObject.getString("type");

				indexable = jsonObject.getBoolean("indexable", true);

				if (!jsonObject.has("label")) {
					throw new Exception("Label is required");
				}

				label = _toLocalizedValues(jsonObject.getJSONObject("label"));

				localizable = jsonObject.getBoolean("localizable", false);

				if (!jsonObject.has("name")) {
					throw new Exception("Name is required");
				}

				name = jsonObject.getString("name");

				repeatable = jsonObject.getBoolean("repeatable", false);

				if (!jsonObject.has("tip")) {
					throw new Exception("Tip is required");
				}

				tip = _toLocalizedValues(jsonObject.getJSONObject("tip"));
			}
		};
	}

	private static DataDefinitionField[] _toDataDefinitionFields(
			JSONArray jsonArray)
		throws Exception {

		List<DataDefinitionField> dataDefinitionFields = new ArrayList<>(
			jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			DataDefinitionField dataDefinitionField = _toDataDefinitionField(
				jsonArray.getJSONObject(i));

			dataDefinitionFields.add(dataDefinitionField);
		}

		return dataDefinitionFields.toArray(
			new DataDefinitionField[dataDefinitionFields.size()]);
	}

	private static JSONArray _toJSONArray(
			DataDefinitionField[] dataDefinitionFields)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DataDefinitionField dataDefinitionField : dataDefinitionFields) {
			jsonArray.put(_toJSONObject(dataDefinitionField));
		}

		return jsonArray;
	}

	private static JSONObject _toJSONObject(
			DataDefinitionField dataDefinitionField)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Object defaultValue = dataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}

		jsonObject.put("indexable", dataDefinitionField.getIndexable());

		LocalizedValue[] label = dataDefinitionField.getLabel();

		if (label.length > 0) {
			_put(jsonObject, "label", label);
		}

		jsonObject.put("localizable", dataDefinitionField.getLocalizable());

		String name = dataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new Exception("Name is required");
		}

		jsonObject.put("name", name);

		jsonObject.put("repeatable", dataDefinitionField.getRepeatable());

		LocalizedValue[] tip = dataDefinitionField.getTip();

		if (tip.length > 0) {
			_put(jsonObject, "tip", tip);
		}

		String type = dataDefinitionField.getFieldType();

		if ((type == null) || type.isEmpty()) {
			throw new Exception("Type is required");
		}

		jsonObject.put("type", type);

		return jsonObject;
	}

	private static LocalizedValue[] _toLocalizedValues(JSONObject jsonObject) {
		List<LocalizedValue> localizedValues = new ArrayList<>();

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			LocalizedValue localizedValue = new LocalizedValue();

			String key = keys.next();

			localizedValue.setKey(key);
			localizedValue.setValue(jsonObject.getString(key));

			localizedValues.add(localizedValue);
		}

		return localizedValues.toArray(
			new LocalizedValue[localizedValues.size()]);
	}

}