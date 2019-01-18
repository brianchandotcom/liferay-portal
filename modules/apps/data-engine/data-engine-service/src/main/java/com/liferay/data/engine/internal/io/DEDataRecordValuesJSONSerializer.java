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

import com.liferay.data.engine.io.DEDataRecordValuesResponseBuilder;
import com.liferay.data.engine.io.DEDataRecordValuesSerializer;
import com.liferay.data.engine.io.DEDataRecordValuesSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataRecordValuesSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.record.values.serializer.type=json",
	service = DEDataRecordValuesSerializer.class
)
public class DEDataRecordValuesJSONSerializer
	implements DEDataRecordValuesSerializer {

	@Override
	public DEDataRecordValuesSerializerApplyResponse apply(
		DEDataRecordValuesSerializerApplyRequest
			deDataRecordValuesSerializerApplyRequest) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		addDEDataDefinitionFieldValue(
			jsonObject, deDataRecordValuesSerializerApplyRequest);

		return DEDataRecordValuesResponseBuilder.serializeBuilder(
			jsonObject.toJSONString()
		).build();
	}

	protected void addDEDataDefinitionFieldValue(
		JSONObject jsonObject,
		DEDataRecordValuesSerializerApplyRequest
			deDataRecordValuesSerializerApplyRequest) {

		DEDataDefinition deDataDefinition =
			deDataRecordValuesSerializerApplyRequest.getDEDataDefinition();

		Map<String, DEDataDefinitionField> deDataDefinitionFields =
			getDEDataDefinitionFieldsMap(deDataDefinition);

		Map<String, Object> values =
			deDataRecordValuesSerializerApplyRequest.getValues();

		for (Map.Entry<String, DEDataDefinitionField> entry :
				deDataDefinitionFields.entrySet()) {

			if (!values.containsKey(entry.getKey())) {
				continue;
			}

			DEDataDefinitionField deDataDefinitionField = entry.getValue();

			if (deDataDefinitionField.isLocalizable()) {
				JSONObject localizedJSONObject = jsonFactory.createJSONObject();

				addLocalizedValues(
					localizedJSONObject,
					(Map<String, Object>)values.get(entry.getKey()));

				jsonObject.put(entry.getKey(), localizedJSONObject);
			}
			else if (deDataDefinitionField.isRepeatable()) {
				JSONArray jsonArray = jsonFactory.createJSONArray();

				addValues(jsonArray, (Object[])values.get(entry.getKey()));

				jsonObject.put(entry.getKey(), jsonArray);
			}
			else {
				jsonObject.put(entry.getKey(), values.get(entry.getKey()));
			}
		}
	}

	protected void addLocalizedValues(
		JSONObject jsonObject, Map<String, Object> values) {

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			jsonObject.put(
				entry.getKey(),
				GetterUtil.get(entry.getValue(), StringPool.BLANK));
		}
	}

	protected void addValues(JSONArray jsonArray, Object[] values) {
		for (Object value : values) {
			jsonArray.put(value);
		}
	}

	protected Map<String, DEDataDefinitionField> getDEDataDefinitionFieldsMap(
		DEDataDefinition deDataDefinition) {

		List<DEDataDefinitionField> deDataDefinitionFields =
			deDataDefinition.getDEDataDefinitionFields();

		Stream<DEDataDefinitionField> stream = deDataDefinitionFields.stream();

		return stream.collect(
			Collectors.toMap(field -> field.getName(), Function.identity()));
	}

	@Reference
	protected JSONFactory jsonFactory;

}