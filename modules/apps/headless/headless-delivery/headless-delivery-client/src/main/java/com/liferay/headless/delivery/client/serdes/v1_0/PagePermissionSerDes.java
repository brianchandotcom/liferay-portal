/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.PagePermission;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class PagePermissionSerDes {

	public static PagePermission toDTO(String json) {
		PagePermissionJSONParser pagePermissionJSONParser =
			new PagePermissionJSONParser();

		return pagePermissionJSONParser.parseToDTO(json);
	}

	public static PagePermission[] toDTOs(String json) {
		PagePermissionJSONParser pagePermissionJSONParser =
			new PagePermissionJSONParser();

		return pagePermissionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PagePermission pagePermission) {
		if (pagePermission == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pagePermission.getActionKeys() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actionKeys\": ");

			sb.append("[");

			for (int i = 0; i < pagePermission.getActionKeys().length; i++) {
				sb.append("\"");

				sb.append(_escape(pagePermission.getActionKeys()[i]));

				sb.append("\"");

				if ((i + 1) < pagePermission.getActionKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pagePermission.getRoleKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleKey\": ");

			sb.append("\"");

			sb.append(_escape(pagePermission.getRoleKey()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PagePermissionJSONParser pagePermissionJSONParser =
			new PagePermissionJSONParser();

		return pagePermissionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PagePermission pagePermission) {
		if (pagePermission == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pagePermission.getActionKeys() == null) {
			map.put("actionKeys", null);
		}
		else {
			map.put(
				"actionKeys", String.valueOf(pagePermission.getActionKeys()));
		}

		if (pagePermission.getRoleKey() == null) {
			map.put("roleKey", null);
		}
		else {
			map.put("roleKey", String.valueOf(pagePermission.getRoleKey()));
		}

		return map;
	}

	public static class PagePermissionJSONParser
		extends BaseJSONParser<PagePermission> {

		@Override
		protected PagePermission createDTO() {
			return new PagePermission();
		}

		@Override
		protected PagePermission[] createDTOArray(int size) {
			return new PagePermission[size];
		}

		@Override
		protected void setField(
			PagePermission pagePermission, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actionKeys")) {
				if (jsonParserFieldValue != null) {
					pagePermission.setActionKeys(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleKey")) {
				if (jsonParserFieldValue != null) {
					pagePermission.setRoleKey((String)jsonParserFieldValue);
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