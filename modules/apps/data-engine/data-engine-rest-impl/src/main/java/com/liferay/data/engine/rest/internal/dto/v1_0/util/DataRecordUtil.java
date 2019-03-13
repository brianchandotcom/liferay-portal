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
import com.liferay.data.engine.rest.dto.v1_0.DataRecordValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataRecordUtil {

	public static DataRecordValue[] toDataRecordValues(
			DataDefinition dataDefinition, String json)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		List<DataRecordValue> dataRecordValues = new ArrayList<>();

		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			String dataDefinitionColumnName = dataDefinitionField.getName();

			if (!jsonObject.has(dataDefinitionColumnName)) {
				continue;
			}

			DataRecordValue dataRecordValue = new DataRecordValue();

			if (dataDefinitionField.getLocalizable()) {
				JSONObject localizedJSONObject = jsonObject.getJSONObject(
					dataDefinitionColumnName);

				Map<String, Object> localizedValues = new HashMap<>();

				Iterator<String> keys = localizedJSONObject.keys();

				while (keys.hasNext()) {
					String languageId = keys.next();

					localizedValues.put(
						languageId, localizedJSONObject.get(languageId));
				}

				dataRecordValue.setKey(dataDefinitionColumnName);
				dataRecordValue.setValue(localizedValues);

				dataRecordValues.add(dataRecordValue);
			}
			else if (dataDefinitionField.getRepeatable()) {
				JSONArray jsonArray = jsonObject.getJSONArray(
					dataDefinitionColumnName);

				Object[] repeatableValues = new Object[jsonArray.length()];

				for (int i = 0; i < jsonArray.length(); i++) {
					repeatableValues[i] = jsonArray.get(i);
				}

				dataRecordValue.setKey(dataDefinitionColumnName);
				dataRecordValue.setValue(repeatableValues);

				dataRecordValues.add(dataRecordValue);
			}
			else {
				dataRecordValue.setKey(dataDefinitionColumnName);
				dataRecordValue.setValue(
					jsonObject.get(dataDefinitionColumnName));

				dataRecordValues.add(dataRecordValue);
			}
		}

		return dataRecordValues.toArray(
			new DataRecordValue[dataRecordValues.size()]);
	}

	public static Map<String, Object> toDataRecordValuesMap(
			DataRecordValue[] dataRecordValues)
		throws PortalException {

		Map<String, Object> dataRecordValuesMap = new HashMap<>();

		for (DataRecordValue dataRecordValue : dataRecordValues) {
			dataRecordValuesMap.put(
				dataRecordValue.getKey(), dataRecordValue.getValue());
		}

		return dataRecordValuesMap;
	}

	public static String toJSONString(
			DataDefinition dataDefinition, DataRecordValue[] dataRecordValues)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		_addDEDataDefinitionFieldValue(
			jsonObject, dataDefinition, dataRecordValues);

		return jsonObject.toJSONString();
	}

	private static void _addDEDataDefinitionFieldValue(
			JSONObject jsonObject, DataDefinition dataDefinition,
			DataRecordValue[] dataRecordValues)
		throws PortalException {

		Map<String, DataDefinitionField> deDataDefinitionFields =
			_getDataDefinitionFieldsMap(dataDefinition);

		Map<String, Object> dataRecordValuesMap = toDataRecordValuesMap(
			dataRecordValues);

		for (Map.Entry<String, DataDefinitionField> entry :
				deDataDefinitionFields.entrySet()) {

			if (!dataRecordValuesMap.containsKey(entry.getKey())) {
				continue;
			}

			DataDefinitionField dataDefinitionField = entry.getValue();

			Object value = DataDefinitionUtil.getDataDefinitionFieldValue(
				dataDefinitionField, toDataRecordValuesMap(dataRecordValues));

			if (dataDefinitionField.getLocalizable()) {
				JSONObject localizedJSONObject =
					JSONFactoryUtil.createJSONObject();

				_addLocalizedValues(
					localizedJSONObject, (Map<String, Object>)value);

				jsonObject.put(entry.getKey(), localizedJSONObject);
			}
			else if (dataDefinitionField.getRepeatable()) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				_addValues(
					jsonArray,
					(Object[])dataRecordValuesMap.get(entry.getKey()));

				jsonObject.put(entry.getKey(), jsonArray);
			}
			else {
				jsonObject.put(
					entry.getKey(), dataRecordValuesMap.get(entry.getKey()));
			}
		}
	}

	private static void _addLocalizedValues(
		JSONObject jsonObject, Map<String, Object> values) {

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			jsonObject.put(
				entry.getKey(),
				GetterUtil.get(entry.getValue(), StringPool.BLANK));
		}
	}

	private static void _addValues(JSONArray jsonArray, Object[] values) {
		for (Object value : values) {
			jsonArray.put(value);
		}
	}

	private static Map<String, DataDefinitionField> _getDataDefinitionFieldsMap(
		DataDefinition dataDefinition) {

		return Stream.of(
			dataDefinition.getDataDefinitionFields()
		).collect(
			Collectors.toMap(field -> field.getName(), Function.identity())
		);
	}

}