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

package com.liferay.data.engine.rest.internal.io;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jeyvison Nascimento
 */
public class DataDefinitionJSONDeserializer {

	public DataDefinitionJSONDeserializer(JSONFactory jsonFactory) {
		this.jsonFactory = jsonFactory;
	}

	public DataDefinition deserialize(String content) throws Exception {
		DataDefinition dataDefinition = new DataDefinition();

		JSONObject jsonObject = jsonFactory.createJSONObject(content);

		dataDefinition.setDataDefinitionFields(
			getDataDefinitionFields(jsonObject.getJSONArray("fields")));

		return dataDefinition;
	}

	protected DataDefinitionField getDataDefinitionField(JSONObject jsonObject)
		throws Exception {

		List<LocalizedValue> labels = new ArrayList<>();

		if (jsonObject.has("label")) {
			JSONObject labelJSONObject = jsonObject.getJSONObject("label");

			if (labelJSONObject == null) {
				throw new Exception(
					"Label property must contain localized values");
			}

			Iterator<String> keys = labelJSONObject.keys();

			while (keys.hasNext()) {
				String key = keys.next();

				LocalizedValue localizedValue = new LocalizedValue();

				localizedValue.setKey(key);
				localizedValue.setValue(labelJSONObject.getString(key));

				labels.add(localizedValue);
			}
		}

		if (!jsonObject.has("name")) {
			throw new Exception("Name property is required");
		}

		if (!jsonObject.has("type")) {
			throw new Exception("Type property is required");
		}

		List<LocalizedValue> tips = new ArrayList<>();

		if (jsonObject.has("tip")) {
			JSONObject tipJSONObject = jsonObject.getJSONObject("tip");

			if (tipJSONObject == null) {
				throw new Exception(
					"Tip property must contain localized values");
			}

			Iterator<String> keys = tipJSONObject.keys();

			while (keys.hasNext()) {
				String key = keys.next();

				LocalizedValue localizedValue = new LocalizedValue();

				localizedValue.setKey(key);
				localizedValue.setValue(tipJSONObject.getString(key));

				tips.add(localizedValue);
			}
		}

		DataDefinitionField dataDefinitionField = new DataDefinitionField();

		dataDefinitionField.setDefaultValue(
			jsonObject.getString("defaultValue"));
		dataDefinitionField.setIndexable(
			jsonObject.getBoolean("indexable", true));
		dataDefinitionField.setLabel(labels.toArray(new LocalizedValue[0]));
		dataDefinitionField.setLocalizable(
			jsonObject.getBoolean("localizable", false));
		dataDefinitionField.setName(jsonObject.getString("name"));
		dataDefinitionField.setRepeatable(
			jsonObject.getBoolean("repeatable", false));
		dataDefinitionField.setTip(tips.toArray(new LocalizedValue[0]));
		dataDefinitionField.setFieldType(jsonObject.getString("type"));

		return dataDefinitionField;
	}

	protected DataDefinitionField[] getDataDefinitionFields(JSONArray jsonArray)
		throws Exception {

		List<DataDefinitionField> dataDefinitionFields = new ArrayList<>(
			jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			DataDefinitionField dataDefinitionField = getDataDefinitionField(
				jsonArray.getJSONObject(i));

			dataDefinitionFields.add(dataDefinitionField);
		}

		return dataDefinitionFields.toArray(new DataDefinitionField[0]);
	}

	protected JSONFactory jsonFactory;

}