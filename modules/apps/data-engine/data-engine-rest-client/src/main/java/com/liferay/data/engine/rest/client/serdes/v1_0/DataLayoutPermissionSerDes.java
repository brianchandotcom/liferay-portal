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

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutPermission;
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
public class DataLayoutPermissionSerDes {

	public static DataLayoutPermission toDTO(String json) {
		DataLayoutPermissionJSONParser dataLayoutPermissionJSONParser =
			new DataLayoutPermissionJSONParser();

		return dataLayoutPermissionJSONParser.parseToDTO(json);
	}

	public static DataLayoutPermission[] toDTOs(String json) {
		DataLayoutPermissionJSONParser dataLayoutPermissionJSONParser =
			new DataLayoutPermissionJSONParser();

		return dataLayoutPermissionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataLayoutPermission dataLayoutPermission) {
		if (dataLayoutPermission == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataLayoutPermission.getAddDataLayout() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addDataLayout\": ");

			sb.append(dataLayoutPermission.getAddDataLayout());
		}

		if (dataLayoutPermission.getDefinePermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"definePermissions\": ");

			sb.append(dataLayoutPermission.getDefinePermissions());
		}

		if (dataLayoutPermission.getDelete() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"delete\": ");

			sb.append(dataLayoutPermission.getDelete());
		}

		if (dataLayoutPermission.getRoleNames() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleNames\": ");

			sb.append("[");

			for (int i = 0; i < dataLayoutPermission.getRoleNames().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(dataLayoutPermission.getRoleNames()[i]));

				sb.append("\"");

				if ((i + 1) < dataLayoutPermission.getRoleNames().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataLayoutPermission.getUpdate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"update\": ");

			sb.append(dataLayoutPermission.getUpdate());
		}

		if (dataLayoutPermission.getView() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"view\": ");

			sb.append(dataLayoutPermission.getView());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataLayoutPermissionJSONParser dataLayoutPermissionJSONParser =
			new DataLayoutPermissionJSONParser();

		return dataLayoutPermissionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DataLayoutPermission dataLayoutPermission) {

		if (dataLayoutPermission == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataLayoutPermission.getAddDataLayout() == null) {
			map.put("addDataLayout", null);
		}
		else {
			map.put(
				"addDataLayout",
				String.valueOf(dataLayoutPermission.getAddDataLayout()));
		}

		if (dataLayoutPermission.getDefinePermissions() == null) {
			map.put("definePermissions", null);
		}
		else {
			map.put(
				"definePermissions",
				String.valueOf(dataLayoutPermission.getDefinePermissions()));
		}

		if (dataLayoutPermission.getDelete() == null) {
			map.put("delete", null);
		}
		else {
			map.put("delete", String.valueOf(dataLayoutPermission.getDelete()));
		}

		if (dataLayoutPermission.getRoleNames() == null) {
			map.put("roleNames", null);
		}
		else {
			map.put(
				"roleNames",
				String.valueOf(dataLayoutPermission.getRoleNames()));
		}

		if (dataLayoutPermission.getUpdate() == null) {
			map.put("update", null);
		}
		else {
			map.put("update", String.valueOf(dataLayoutPermission.getUpdate()));
		}

		if (dataLayoutPermission.getView() == null) {
			map.put("view", null);
		}
		else {
			map.put("view", String.valueOf(dataLayoutPermission.getView()));
		}

		return map;
	}

	public static class DataLayoutPermissionJSONParser
		extends BaseJSONParser<DataLayoutPermission> {

		@Override
		protected DataLayoutPermission createDTO() {
			return new DataLayoutPermission();
		}

		@Override
		protected DataLayoutPermission[] createDTOArray(int size) {
			return new DataLayoutPermission[size];
		}

		@Override
		protected void setField(
			DataLayoutPermission dataLayoutPermission,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "addDataLayout")) {
				if (jsonParserFieldValue != null) {
					dataLayoutPermission.setAddDataLayout(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "definePermissions")) {
				if (jsonParserFieldValue != null) {
					dataLayoutPermission.setDefinePermissions(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "delete")) {
				if (jsonParserFieldValue != null) {
					dataLayoutPermission.setDelete(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleNames")) {
				if (jsonParserFieldValue != null) {
					dataLayoutPermission.setRoleNames(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "update")) {
				if (jsonParserFieldValue != null) {
					dataLayoutPermission.setUpdate(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "view")) {
				if (jsonParserFieldValue != null) {
					dataLayoutPermission.setView((Boolean)jsonParserFieldValue);
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