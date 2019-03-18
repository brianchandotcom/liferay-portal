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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Jeyvison Nascimento
 */
public class DataRecordValueUtil {

	public static DataRecordValue[] toDataRecordValues(
			DataDefinition dataDefinition, String json)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		List<DataRecordValue> dataRecordValues = new ArrayList<>();

		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			String name = dataDefinitionField.getName();

			if (!jsonObject.has(name)) {
				continue;
			}

			DataRecordValue dataRecordValue = new DataRecordValue() {
				{
					key = name;

					if (dataDefinitionField.getLocalizable()) {
						JSONObject dataRecordValueJSONObject =
							jsonObject.getJSONObject(name);

						Iterable<String> iterable =
							dataRecordValueJSONObject::keys;

						Map<String, Object> localizedValues = new HashMap<>();

						StreamSupport.stream(
							iterable.spliterator(), false
						).forEach(
							key -> localizedValues.put(
								key, dataRecordValueJSONObject.get(key))
						);

						value = localizedValues;
					}
					else if (dataDefinitionField.getRepeatable()) {
						value = JSONUtil.toObjectArray(
							jsonObject.getJSONArray(name));
					}
					else {
						value = jsonObject.get(name);
					}
				}
			};

			dataRecordValues.add(dataRecordValue);
		}

		return dataRecordValues.toArray(
			new DataRecordValue[dataRecordValues.size()]);
	}

	public static String toJSON(
			DataDefinition dataDefinition, DataRecordValue[] dataRecordValues)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		_addDEDataDefinitionFieldValue(
			jsonObject, dataDefinition, dataRecordValues);

		return jsonObject.toString();
	}

	private static void _addDEDataDefinitionFieldValue(
			JSONObject jsonObject, DataDefinition dataDefinition,
			DataRecordValue[] dataRecordValues)
		throws Exception {

		Map<String, Object> dataRecordValuesMap = _toDataRecordValuesMap(
			dataRecordValues);

		Map<String, DataDefinitionField> dataDefinitionFields = Stream.of(
			dataDefinition.getDataDefinitionFields()
		).collect(
			Collectors.toMap(field -> field.getName(), Function.identity())
		);

		for (Map.Entry<String, DataDefinitionField> entry :
				dataDefinitionFields.entrySet()) {

			if (!dataRecordValuesMap.containsKey(entry.getKey())) {
				continue;
			}

			DataDefinitionField dataDefinitionField = entry.getValue();

			if (dataDefinitionField.getLocalizable()) {
				JSONObject localizedJSONObject =
					JSONFactoryUtil.createJSONObject();

				_addLocalizedValues(
					localizedJSONObject,
					(Map<String, Object>)dataRecordValuesMap.get(
						dataDefinitionField.getName()));

				jsonObject.put(entry.getKey(), localizedJSONObject);
			}
			else if (dataDefinitionField.getRepeatable()) {
				jsonObject.put(
					entry.getKey(),
					JSONUtil.toJSONArray(
						(Object[])dataRecordValuesMap.get(entry.getKey()),
						object -> object));
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

	private static Map<String, Object> _toDataRecordValuesMap(
			DataRecordValue[] dataRecordValues)
		throws Exception {

		Map<String, Object> dataRecordValuesMap = new HashMap<>();

		for (DataRecordValue dataRecordValue : dataRecordValues) {
			dataRecordValuesMap.put(
				dataRecordValue.getKey(), dataRecordValue.getValue());
		}

		return dataRecordValuesMap;
	}

}