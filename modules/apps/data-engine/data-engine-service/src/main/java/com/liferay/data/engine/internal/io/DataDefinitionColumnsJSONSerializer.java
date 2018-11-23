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

import com.liferay.data.engine.exception.DataDefinitionColumnsSerializerException;
import com.liferay.data.engine.io.DataDefinitionColumnsSerializer;
import com.liferay.data.engine.io.DataDefinitionColumnsSerializerApplyRequest;
import com.liferay.data.engine.io.DataDefinitionColumnsSerializerApplyResponse;
import com.liferay.data.engine.model.DataDefinitionColumn;
import com.liferay.data.engine.model.DataDefinitionColumnType;
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
	service = DataDefinitionColumnsSerializer.class
)
public class DataDefinitionColumnsJSONSerializer
	implements DataDefinitionColumnsSerializer {

	@Override
	public DataDefinitionColumnsSerializerApplyResponse apply(
			DataDefinitionColumnsSerializerApplyRequest
				dataDefinitionColumnsSerializerApplyRequest)
		throws DataDefinitionColumnsSerializerException {

		List<DataDefinitionColumn> dataDefinitionColumns =
			dataDefinitionColumnsSerializerApplyRequest.
				getDataDefinitionColumns();

		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (DataDefinitionColumn dataDefinitionColumn :
				dataDefinitionColumns) {

			jsonArray.put(mapColumn(dataDefinitionColumn));
		}

		return DataDefinitionColumnsSerializerApplyResponse.Builder.of(
			jsonArray.toJSONString());
	}

	protected JSONObject mapColumn(DataDefinitionColumn dataDefinitionColumn)
		throws DataDefinitionColumnsSerializerException {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		Object defaultValue = dataDefinitionColumn.getDefaultValue();

		if (defaultValue != null) {
			jsonObject.put("defaultValue", defaultValue);
		}

		jsonObject.put("indexable", dataDefinitionColumn.isIndexable());

		Map<String, String> label = dataDefinitionColumn.getLabel();

		if (!label.isEmpty()) {
			setProperty("label", jsonObject, label);
		}

		jsonObject.put("localizable", dataDefinitionColumn.isLocalizable());

		String name = dataDefinitionColumn.getName();

		if (Validator.isNull(name)) {
			throw new DataDefinitionColumnsSerializerException(
				"Name property is required");
		}
		else {
			jsonObject.put("name", name);
		}

		jsonObject.put("repeatable", dataDefinitionColumn.isRepeatable());
		jsonObject.put("required", dataDefinitionColumn.isRequired());

		Map<String, String> tip = dataDefinitionColumn.getTip();

		if (!tip.isEmpty()) {
			setProperty("tip", jsonObject, tip);
		}

		DataDefinitionColumnType dataDefinitionColumnType =
			dataDefinitionColumn.getType();

		if (dataDefinitionColumnType == null) {
			throw new DataDefinitionColumnsSerializerException(
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