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

import com.liferay.data.engine.exception.DEDataDefinitionFieldsSerializerException;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializer;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinitionField;
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
	service = DEDataDefinitionFieldsSerializer.class
)
public class DEDataDefinitionFieldsJSONSerializer
	implements DEDataDefinitionFieldsSerializer {

	@Override
	public DEDataDefinitionFieldsSerializerApplyResponse apply(
			DEDataDefinitionFieldsSerializerApplyRequest
				deDataDefinitionFieldsSerializerApplyRequest)
		throws DEDataDefinitionFieldsSerializerException {

		List<DEDataDefinitionField> deDataDefinitionFields =
			deDataDefinitionFieldsSerializerApplyRequest.
				getDEDataDefinitionFields();

		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (DEDataDefinitionField deDataDefinitionField :
				deDataDefinitionFields) {

			jsonArray.put(mapField(deDataDefinitionField));
		}

		return DEDataDefinitionFieldsSerializerApplyResponse.Builder.of(
			jsonArray.toJSONString());
	}

	protected JSONObject mapField(DEDataDefinitionField deDataDefinitionField)
		throws DEDataDefinitionFieldsSerializerException {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		Object defaultValue = deDataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}

		jsonObject.put("indexable", deDataDefinitionField.isIndexable());

		Map<String, String> label = deDataDefinitionField.getLabel();

		if (!label.isEmpty()) {
			setProperty("label", jsonObject, label);
		}

		jsonObject.put("localizable", deDataDefinitionField.isLocalizable());

		String name = deDataDefinitionField.getName();

		if (Validator.isNull(name)) {
			throw new DEDataDefinitionFieldsSerializerException(
				"Name property is required");
		}
		else {
			jsonObject.put("name", name);
		}

		jsonObject.put("repeatable", deDataDefinitionField.isRepeatable());
		jsonObject.put("required", deDataDefinitionField.isRequired());

		Map<String, String> tip = deDataDefinitionField.getTip();

		if (!tip.isEmpty()) {
			setProperty("tip", jsonObject, tip);
		}

		String type = deDataDefinitionField.getType();

		if ((type == null) || type.isEmpty()) {
			throw new DEDataDefinitionFieldsSerializerException(
				"Type property is required");
		}
		else {
			jsonObject.put("type", type);
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