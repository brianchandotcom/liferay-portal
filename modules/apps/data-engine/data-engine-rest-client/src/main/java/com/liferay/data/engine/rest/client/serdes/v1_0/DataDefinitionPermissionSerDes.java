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

package com.liferay.data.engine.rest.client.serdes.v1_0;

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionPermission;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionPermissionSerDes {

	public static DataDefinitionPermission toDTO(String json) {
		DataDefinitionPermissionJSONParser dataDefinitionPermissionJSONParser =
			new DataDefinitionPermissionJSONParser();

		return dataDefinitionPermissionJSONParser.parseToDTO(json);
	}

	public static DataDefinitionPermission[] toDTOs(String json) {
		DataDefinitionPermissionJSONParser dataDefinitionPermissionJSONParser =
			new DataDefinitionPermissionJSONParser();

		return dataDefinitionPermissionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		DataDefinitionPermission dataDefinitionPermission) {

		if (dataDefinitionPermission == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataDefinitionPermission.getAddDataDefinition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addDataDefinition\": ");

			sb.append(dataDefinitionPermission.getAddDataDefinition());
		}

		if (dataDefinitionPermission.getDefinePermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"definePermissions\": ");

			sb.append(dataDefinitionPermission.getDefinePermissions());
		}

		if (dataDefinitionPermission.getDelete() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"delete\": ");

			sb.append(dataDefinitionPermission.getDelete());
		}

		if (dataDefinitionPermission.getRoleNames() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleNames\": ");

			sb.append("[");

			for (int i = 0; i < dataDefinitionPermission.getRoleNames().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(dataDefinitionPermission.getRoleNames()[i]));

				sb.append("\"");

				if ((i + 1) < dataDefinitionPermission.getRoleNames().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataDefinitionPermission.getUpdate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"update\": ");

			sb.append(dataDefinitionPermission.getUpdate());
		}

		if (dataDefinitionPermission.getView() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"view\": ");

			sb.append(dataDefinitionPermission.getView());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataDefinitionPermissionJSONParser dataDefinitionPermissionJSONParser =
			new DataDefinitionPermissionJSONParser();

		return dataDefinitionPermissionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DataDefinitionPermission dataDefinitionPermission) {

		if (dataDefinitionPermission == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataDefinitionPermission.getAddDataDefinition() == null) {
			map.put("addDataDefinition", null);
		}
		else {
			map.put(
				"addDataDefinition",
				String.valueOf(
					dataDefinitionPermission.getAddDataDefinition()));
		}

		if (dataDefinitionPermission.getDefinePermissions() == null) {
			map.put("definePermissions", null);
		}
		else {
			map.put(
				"definePermissions",
				String.valueOf(
					dataDefinitionPermission.getDefinePermissions()));
		}

		if (dataDefinitionPermission.getDelete() == null) {
			map.put("delete", null);
		}
		else {
			map.put(
				"delete", String.valueOf(dataDefinitionPermission.getDelete()));
		}

		if (dataDefinitionPermission.getRoleNames() == null) {
			map.put("roleNames", null);
		}
		else {
			map.put(
				"roleNames",
				String.valueOf(dataDefinitionPermission.getRoleNames()));
		}

		if (dataDefinitionPermission.getUpdate() == null) {
			map.put("update", null);
		}
		else {
			map.put(
				"update", String.valueOf(dataDefinitionPermission.getUpdate()));
		}

		if (dataDefinitionPermission.getView() == null) {
			map.put("view", null);
		}
		else {
			map.put("view", String.valueOf(dataDefinitionPermission.getView()));
		}

		return map;
	}

	public static class DataDefinitionPermissionJSONParser
		extends BaseJSONParser<DataDefinitionPermission> {

		@Override
		protected DataDefinitionPermission createDTO() {
			return new DataDefinitionPermission();
		}

		@Override
		protected DataDefinitionPermission[] createDTOArray(int size) {
			return new DataDefinitionPermission[size];
		}

		@Override
		protected void setField(
			DataDefinitionPermission dataDefinitionPermission,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "addDataDefinition")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionPermission.setAddDataDefinition(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "definePermissions")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionPermission.setDefinePermissions(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "delete")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionPermission.setDelete(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleNames")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionPermission.setRoleNames(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "update")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionPermission.setUpdate(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "view")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionPermission.setView(
						(Boolean)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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
			sb.append("\":");

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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}