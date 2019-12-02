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

import com.liferay.data.engine.rest.client.dto.v1_0.DataModelPermission;
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
public class DataModelPermissionSerDes {

	public static DataModelPermission toDTO(String json) {
		DataModelPermissionJSONParser dataModelPermissionJSONParser =
			new DataModelPermissionJSONParser();

		return dataModelPermissionJSONParser.parseToDTO(json);
	}

	public static DataModelPermission[] toDTOs(String json) {
		DataModelPermissionJSONParser dataModelPermissionJSONParser =
			new DataModelPermissionJSONParser();

		return dataModelPermissionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataModelPermission dataModelPermission) {
		if (dataModelPermission == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataModelPermission.getActionIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actionIds\": ");

			sb.append("[");

			for (int i = 0; i < dataModelPermission.getActionIds().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(dataModelPermission.getActionIds()[i]));

				sb.append("\"");

				if ((i + 1) < dataModelPermission.getActionIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataModelPermission.getRoleName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleName\": ");

			sb.append("\"");

			sb.append(_escape(dataModelPermission.getRoleName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataModelPermissionJSONParser dataModelPermissionJSONParser =
			new DataModelPermissionJSONParser();

		return dataModelPermissionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DataModelPermission dataModelPermission) {

		if (dataModelPermission == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataModelPermission.getActionIds() == null) {
			map.put("actionIds", null);
		}
		else {
			map.put(
				"actionIds",
				String.valueOf(dataModelPermission.getActionIds()));
		}

		if (dataModelPermission.getRoleName() == null) {
			map.put("roleName", null);
		}
		else {
			map.put(
				"roleName", String.valueOf(dataModelPermission.getRoleName()));
		}

		return map;
	}

	public static class DataModelPermissionJSONParser
		extends BaseJSONParser<DataModelPermission> {

		@Override
		protected DataModelPermission createDTO() {
			return new DataModelPermission();
		}

		@Override
		protected DataModelPermission[] createDTOArray(int size) {
			return new DataModelPermission[size];
		}

		@Override
		protected void setField(
			DataModelPermission dataModelPermission, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actionIds")) {
				if (jsonParserFieldValue != null) {
					dataModelPermission.setActionIds(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleName")) {
				if (jsonParserFieldValue != null) {
					dataModelPermission.setRoleName(
						(String)jsonParserFieldValue);
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