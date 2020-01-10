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

package com.liferay.app.builder.rest.client.serdes.v1_0;

import com.liferay.app.builder.rest.client.dto.v1_0.AppModelPermission;
import com.liferay.app.builder.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class AppModelPermissionSerDes {

	public static AppModelPermission toDTO(String json) {
		AppModelPermissionJSONParser appModelPermissionJSONParser =
			new AppModelPermissionJSONParser();

		return appModelPermissionJSONParser.parseToDTO(json);
	}

	public static AppModelPermission[] toDTOs(String json) {
		AppModelPermissionJSONParser appModelPermissionJSONParser =
			new AppModelPermissionJSONParser();

		return appModelPermissionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AppModelPermission appModelPermission) {
		if (appModelPermission == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appModelPermission.getActionIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actionIds\": ");

			sb.append("[");

			for (int i = 0; i < appModelPermission.getActionIds().length; i++) {
				sb.append("\"");

				sb.append(_escape(appModelPermission.getActionIds()[i]));

				sb.append("\"");

				if ((i + 1) < appModelPermission.getActionIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (appModelPermission.getRoleName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleName\": ");

			sb.append("\"");

			sb.append(_escape(appModelPermission.getRoleName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppModelPermissionJSONParser appModelPermissionJSONParser =
			new AppModelPermissionJSONParser();

		return appModelPermissionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AppModelPermission appModelPermission) {

		if (appModelPermission == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appModelPermission.getActionIds() == null) {
			map.put("actionIds", null);
		}
		else {
			map.put(
				"actionIds", String.valueOf(appModelPermission.getActionIds()));
		}

		if (appModelPermission.getRoleName() == null) {
			map.put("roleName", null);
		}
		else {
			map.put(
				"roleName", String.valueOf(appModelPermission.getRoleName()));
		}

		return map;
	}

	public static class AppModelPermissionJSONParser
		extends BaseJSONParser<AppModelPermission> {

		@Override
		protected AppModelPermission createDTO() {
			return new AppModelPermission();
		}

		@Override
		protected AppModelPermission[] createDTOArray(int size) {
			return new AppModelPermission[size];
		}

		@Override
		protected void setField(
			AppModelPermission appModelPermission, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actionIds")) {
				if (jsonParserFieldValue != null) {
					appModelPermission.setActionIds(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleName")) {
				if (jsonParserFieldValue != null) {
					appModelPermission.setRoleName(
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