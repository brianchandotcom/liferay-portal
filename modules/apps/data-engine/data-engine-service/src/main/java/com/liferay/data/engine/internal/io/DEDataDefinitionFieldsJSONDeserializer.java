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

import com.liferay.data.engine.exception.DEDataDefinitionFieldsDeserializerException;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializer;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinitionField;
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
	service = DEDataDefinitionFieldsDeserializer.class
)
public class DEDataDefinitionFieldsJSONDeserializer
	implements DEDataDefinitionFieldsDeserializer {

	@Override
	public DEDataDefinitionFieldsDeserializerApplyResponse apply(
			DEDataDefinitionFieldsDeserializerApplyRequest
				deDataDefinitionFieldsDeserializerApplyRequest)
		throws DEDataDefinitionFieldsDeserializerException {

		List<DEDataDefinitionField> deDataDefinitionFields = new ArrayList<>();

		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(
				deDataDefinitionFieldsDeserializerApplyRequest.getContent());

			for (int i = 0; i < jsonArray.length(); i++) {
				DEDataDefinitionField deDataDefinitionField =
					createDataDefinitionField(jsonArray.getJSONObject(i));

				deDataDefinitionFields.add(deDataDefinitionField);
			}

			return DEDataDefinitionFieldsDeserializerApplyResponse.Builder.of(
				deDataDefinitionFields);
		}
		catch (JSONException e)
		{
			throw new DEDataDefinitionFieldsDeserializerException(
				"Invalid JSON format");
		}
		catch (DEDataDefinitionFieldsDeserializerException deddfde) {
			throw deddfde;
		}
	}

	protected DEDataDefinitionField createDataDefinitionField(
			JSONObject jsonObject)
		throws DEDataDefinitionFieldsDeserializerException {

		Map<String, String> labels = new TreeMap<>();

		if (jsonObject.has("label")) {
			JSONObject labelJSONObject = jsonObject.getJSONObject("label");

			if (labelJSONObject == null) {
				throw new DEDataDefinitionFieldsDeserializerException(
					"Label property must contain localized values");
			}

			Iterator<String> keys = labelJSONObject.keys();

			while (keys.hasNext()) {
				String key = keys.next();

				labels.put(key, labelJSONObject.getString(key));
			}
		}

		if (!jsonObject.has("name")) {
			throw new DEDataDefinitionFieldsDeserializerException(
				"Name property is required");
		}

		if (!jsonObject.has("type")) {
			throw new DEDataDefinitionFieldsDeserializerException(
				"Type property is required");
		}

		Map<String, String> tips = new TreeMap<>();

		if (jsonObject.has("tip")) {
			JSONObject tipJSONObject = jsonObject.getJSONObject("tip");

			if (tipJSONObject == null) {
				throw new DEDataDefinitionFieldsDeserializerException(
					"Tip property must contain localized values");
			}

			Iterator<String> keys = tipJSONObject.keys();

			while (keys.hasNext()) {
				String key = keys.next();

				tips.put(key, tipJSONObject.getString(key));
			}
		}

		return DEDataDefinitionField.Builder.newBuilder(
			jsonObject.getString("name"), jsonObject.getString("type")
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