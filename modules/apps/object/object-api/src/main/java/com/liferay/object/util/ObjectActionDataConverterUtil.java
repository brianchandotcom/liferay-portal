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

package com.liferay.object.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class ObjectActionDataConverterUtil {

	public static UnicodeProperties convertObjectActionParameters(
		Map<String, ?> parameters) {

		return UnicodePropertiesBuilder.create(
			_toStringMap(parameters), true
		).build();
	}

	public static Map<String, Object> convertParametersUnicodeProperties(
		UnicodeProperties parametersUnicodeProperties) {

		Map<String, Object> parameters = new HashMap<>();

		for (Map.Entry<String, String> entry :
				parametersUnicodeProperties.entrySet()) {

			Object value = entry.getValue();

			if (Objects.equals(entry.getKey(), "objectDefinitionId")) {
				value = GetterUtil.getLong(value);
			}
			else if (Objects.equals(entry.getKey(), "predefinedValues")) {
				value = JSONFactoryUtil.looseDeserialize((String)value);
			}
			else if (Objects.equals(entry.getKey(), "relateEntries")) {
				value = GetterUtil.getBoolean(value);
			}

			parameters.put(entry.getKey(), value);
		}

		return parameters;
	}

	public static Map<String, Object> convertPayloadJSONObject(
		DTOConverterRegistry dtoConverterRegistry,
		ObjectDefinition objectDefinition, JSONObject payloadJSONObject) {

		if (objectDefinition.isSystem()) {
			return _convertSystemObjectPayloadJSONObject(
				dtoConverterRegistry, objectDefinition, payloadJSONObject);
		}

		return _convertObjectEntryPayloadJSONObject(payloadJSONObject);
	}

	private static Map<String, Object> _convertObjectEntryPayloadJSONObject(
		JSONObject payloadJSONObject) {

		Map<String, Object> map = new HashMap<>(
			(Map)payloadJSONObject.get("objectEntry"));

		Object values = map.get("values");

		if (values != null) {
			map.putAll((Map<String, Object>)values);

			map.remove("values");
		}

		map.put("currentUserId", payloadJSONObject.getLong("userId"));

		return map;
	}

	private static Map<String, Object> _convertSystemObjectPayloadJSONObject(
		DTOConverterRegistry dtoConverterRegistry,
		ObjectDefinition objectDefinition, JSONObject payloadJSONObject) {

		DTOConverter<?, ?> dtoConverter = dtoConverterRegistry.getDTOConverter(
			objectDefinition.getClassName());

		JSONObject modelDTOJSONObject = payloadJSONObject.getJSONObject(
			"modelDTO" + dtoConverter.getContentType());

		modelDTOJSONObject.put(
			"companyId", payloadJSONObject.get("companyId")
		).put(
			"currentUserId", payloadJSONObject.get("userId")
		).put(
			"objectDefinitionId", payloadJSONObject.get("objectDefinitionId")
		);

		return modelDTOJSONObject.toMap();
	}

	private static Map<String, String> _toStringMap(Map<String, ?> map) {
		Map<String, String> stringMap = new HashMap<>();

		for (Map.Entry<String, ?> entry : map.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof ArrayList) {
				value = JSONFactoryUtil.looseSerialize(value);
			}

			stringMap.put(entry.getKey(), value.toString());
		}

		return stringMap;
	}

}