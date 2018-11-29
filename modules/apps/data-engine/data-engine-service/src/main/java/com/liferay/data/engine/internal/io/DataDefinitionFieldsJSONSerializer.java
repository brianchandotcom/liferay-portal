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

import com.liferay.data.engine.exception.DataDefinitionFieldsSerializerException;
import com.liferay.data.engine.io.DataDefinitionFieldsSerializer;
import com.liferay.data.engine.io.DataDefinitionFieldsSerializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionFieldsSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumnType;
import com.liferay.data.engine.model.DataDefinitionField;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.definition.serializer.type=json",
	service = DataDefinitionFieldsSerializer.class
)
public class DataDefinitionFieldsJSONSerializer
	implements DataDefinitionFieldsSerializer {

	@Override
	public DataDefinitionFieldsSerializerApplyResponse apply(
			DataDefinitionFieldsSerializerApplyRequest
				dataDefinitionFieldsSerializerApplyRequest)
		throws DataDefinitionFieldsSerializerException {

		List<DataDefinitionField> dataDefinitionFields =
			dataDefinitionFieldsSerializerApplyRequest.
				getDataDefinitionFields();

		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (DataDefinitionField dataDefinitionField : dataDefinitionFields) {
			jsonArray.put(mapField(dataDefinitionField));
		}

		return DataDefinitionFieldsSerializerApplyResponse.Builder.of(
			jsonArray.toJSONString());
	}

	protected JSONObject mapField(DataDefinitionField dataDefinitionField)
		throws DataDefinitionFieldsSerializerException {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		Object defaultValue = dataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}

		jsonObject.put("indexable", dataDefinitionField.isIndexable());

		Map<String, String> label = dataDefinitionField.getLabel();

		if (!label.isEmpty()) {
			setProperty("label", jsonObject, label);
		}

		jsonObject.put("localizable", dataDefinitionField.isLocalizable());

		String name = dataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new DataDefinitionFieldsSerializerException(
				"Name property is required");
		}
		else {
			jsonObject.put("name", name);
		}

		jsonObject.put("repeatable", dataDefinitionField.isRepeatable());
		jsonObject.put("required", dataDefinitionField.isRequired());

		Map<String, String> tip = dataDefinitionField.getTip();

		if (!tip.isEmpty()) {
			setProperty("tip", jsonObject, tip);
		}

		DataDefinitionColumnType dataDefinitionColumnType =
			dataDefinitionField.getType();

		if (dataDefinitionColumnType == null) {
			throw new DataDefinitionFieldsSerializerException(
				"Type property is required");
		}
		else {
			jsonObject.put("type", dataDefinitionColumnType.getValue());
		}

		return jsonObject;
	}

	protected void setProperty(
		String property, JSONObject jsonObject, Map<String, String> map) {

		JSONObject languageJSONObject = jsonFactory.createJSONObject();

		Set<Map.Entry<String, String>> entries = map.entrySet();

		Stream<Map.Entry<String, String>> stream = entries.stream();

		stream.forEach(
			entry -> languageJSONObject.put(entry.getKey(), entry.getValue()));

		jsonObject.put(property, languageJSONObject);
	}

	@Reference
	protected JSONFactory jsonFactory;

}