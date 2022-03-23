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

package com.liferay.object.admin.rest.client.serdes.v1_0;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectFieldSetting;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ObjectFieldSettingSerDes {

	public static ObjectFieldSetting toDTO(String json) {
		ObjectFieldSettingJSONParser objectFieldSettingJSONParser =
			new ObjectFieldSettingJSONParser();

		return objectFieldSettingJSONParser.parseToDTO(json);
	}

	public static ObjectFieldSetting[] toDTOs(String json) {
		ObjectFieldSettingJSONParser objectFieldSettingJSONParser =
			new ObjectFieldSettingJSONParser();

		return objectFieldSettingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectFieldSetting objectFieldSetting) {
		if (objectFieldSetting == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectFieldSetting.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(objectFieldSetting.getId());
		}

		if (objectFieldSetting.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(objectFieldSetting.getName()));

			sb.append("\"");
		}

		if (objectFieldSetting.getObjectFieldId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectFieldId\": ");

			sb.append(objectFieldSetting.getObjectFieldId());
		}

		if (objectFieldSetting.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(objectFieldSetting.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectFieldSettingJSONParser objectFieldSettingJSONParser =
			new ObjectFieldSettingJSONParser();

		return objectFieldSettingJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ObjectFieldSetting objectFieldSetting) {

		if (objectFieldSetting == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectFieldSetting.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(objectFieldSetting.getId()));
		}

		if (objectFieldSetting.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(objectFieldSetting.getName()));
		}

		if (objectFieldSetting.getObjectFieldId() == null) {
			map.put("objectFieldId", null);
		}
		else {
			map.put(
				"objectFieldId",
				String.valueOf(objectFieldSetting.getObjectFieldId()));
		}

		if (objectFieldSetting.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(objectFieldSetting.getValue()));
		}

		return map;
	}

	public static class ObjectFieldSettingJSONParser
		extends BaseJSONParser<ObjectFieldSetting> {

		@Override
		protected ObjectFieldSetting createDTO() {
			return new ObjectFieldSetting();
		}

		@Override
		protected ObjectFieldSetting[] createDTOArray(int size) {
			return new ObjectFieldSetting[size];
		}

		@Override
		protected void setField(
			ObjectFieldSetting objectFieldSetting, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					objectFieldSetting.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					objectFieldSetting.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectFieldId")) {
				if (jsonParserFieldValue != null) {
					objectFieldSetting.setObjectFieldId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					objectFieldSetting.setValue((String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}