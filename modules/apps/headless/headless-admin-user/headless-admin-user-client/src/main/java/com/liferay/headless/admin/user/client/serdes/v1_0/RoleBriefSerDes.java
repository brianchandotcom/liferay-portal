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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

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
public class RoleBriefSerDes {

	public static RoleBrief toDTO(String json) {
		RoleBriefJSONParser roleBriefJSONParser = new RoleBriefJSONParser();

		return roleBriefJSONParser.parseToDTO(json);
	}

	public static RoleBrief[] toDTOs(String json) {
		RoleBriefJSONParser roleBriefJSONParser = new RoleBriefJSONParser();

		return roleBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RoleBrief roleBrief) {
		if (roleBrief == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (roleBrief.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(roleBrief.getId());
		}

		if (roleBrief.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(roleBrief.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RoleBriefJSONParser roleBriefJSONParser = new RoleBriefJSONParser();

		return roleBriefJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(RoleBrief roleBrief) {
		if (roleBrief == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (roleBrief.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(roleBrief.getId()));
		}

		if (roleBrief.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(roleBrief.getName()));
		}

		return map;
	}

	public static class RoleBriefJSONParser extends BaseJSONParser<RoleBrief> {

		@Override
		protected RoleBrief createDTO() {
			return new RoleBrief();
		}

		@Override
		protected RoleBrief[] createDTOArray(int size) {
			return new RoleBrief[size];
		}

		@Override
		protected void setField(
			RoleBrief roleBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					roleBrief.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					roleBrief.setName((String)jsonParserFieldValue);
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