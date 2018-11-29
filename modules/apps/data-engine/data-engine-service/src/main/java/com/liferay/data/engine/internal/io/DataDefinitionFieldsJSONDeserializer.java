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

package com.liferay.data.engine.internal.io;

import com.liferay.data.engine.exception.DataDefinitionFieldsDeserializerException;
import com.liferay.data.engine.io.DataDefinitionFieldsDeserializer;
import com.liferay.data.engine.io.DataDefinitionFieldsDeserializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionFieldsDeserializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.data.engine.model.DataDefinitionField;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.definition.deserializer.type=json",
	service = DataDefinitionFieldsDeserializer.class
)
public class DataDefinitionFieldsJSONDeserializer
	implements DataDefinitionFieldsDeserializer {

	@Override
	public DataDefinitionFieldsDeserializerApplyResponse apply(
			DataDefinitionFieldsDeserializerApplyRequest
				dataDefinitionFieldsDeserializerApplyRequest)
		throws DataDefinitionFieldsDeserializerException {

		List<DataDefinitionField> dataDefinitionFields = new ArrayList<>();

		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(
				dataDefinitionFieldsDeserializerApplyRequest.getContent());

			for (int i = 0; i < jsonArray.length(); i++) {
				DataDefinitionField dataDefinitionField =
					createDataDefinitionField(jsonArray.getJSONObject(i));

				dataDefinitionFields.add(dataDefinitionField);
			}

			return DataDefinitionFieldsDeserializerApplyResponse.Builder.of(
				dataDefinitionFields);
		}
		catch (JSONException e)
		{
			throw new DataDefinitionFieldsDeserializerException(
				"Invalid JSON format");
		}
		catch (DataDefinitionFieldsDeserializerException ddfde) {
			throw ddfde;
		}
	}

	protected DataDefinitionField createDataDefinitionField(
			JSONObject jsonObject)
		throws DataDefinitionFieldsDeserializerException {

		Map<String, String> labels = new TreeMap<>();

		if (jsonObject.has("label")) {
			JSONObject labelJSONObject = jsonObject.getJSONObject("label");

			if (labelJSONObject == null) {
				throw new DataDefinitionFieldsDeserializerException(
					"Label property must contain localized values");
			}

			Iterator<String> keys = labelJSONObject.keys();

			while (keys.hasNext()) {
				String key = keys.next();

				labels.put(key, labelJSONObject.getString(key));
			}
		}

		if (!jsonObject.has("name")) {
			throw new DataDefinitionFieldsDeserializerException(
				"Name property is required");
		}

		if (!jsonObject.has("type")) {
			throw new DataDefinitionFieldsDeserializerException(
				"Type property is required");
		}

		Map<String, String> tips = new TreeMap<>();

		if (jsonObject.has("tip")) {
			JSONObject tipJSONObject = jsonObject.getJSONObject("tip");

			if (tipJSONObject == null) {
				throw new DataDefinitionFieldsDeserializerException(
					"Tip property must contain localized values");
			}

			Iterator<String> keys = tipJSONObject.keys();

			while (keys.hasNext()) {
				String key = keys.next();

				tips.put(key, tipJSONObject.getString(key));
			}
		}

		return DataDefinitionField.Builder.newBuilder(
			jsonObject.getString("name"),
			DataDefinitionColumnType.parse(jsonObject.getString("type"))
		).defaultValue(
			jsonObject.get("defaultValue")
		).indexable(
			jsonObject.getBoolean("indexable", true)
		).label(
			labels
		).localizable(
			jsonObject.getBoolean("localizable", false)
		).repeatable(
			jsonObject.getBoolean("repeatable", false)
		).required(
			jsonObject.getBoolean("required", false)
		).tip(
			tips
		).build();
	}

	@Reference
	protected JSONFactory jsonFactory;

}