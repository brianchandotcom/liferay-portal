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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.WidgetInstance;
import com.liferay.headless.delivery.client.dto.v1_0.WidgetPermission;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WidgetInstanceSerDes {

	public static WidgetInstance toDTO(String json) {
		WidgetInstanceJSONParser widgetInstanceJSONParser =
			new WidgetInstanceJSONParser();

		return widgetInstanceJSONParser.parseToDTO(json);
	}

	public static WidgetInstance[] toDTOs(String json) {
		WidgetInstanceJSONParser widgetInstanceJSONParser =
			new WidgetInstanceJSONParser();

		return widgetInstanceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WidgetInstance widgetInstance) {
		if (widgetInstance == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (widgetInstance.getWidgetConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"widgetConfig\": ");

			sb.append(_toJSON(widgetInstance.getWidgetConfig()));
		}

		if (widgetInstance.getWidgetInstanceId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"widgetInstanceId\": ");

			sb.append("\"");

			sb.append(_escape(widgetInstance.getWidgetInstanceId()));

			sb.append("\"");
		}

		if (widgetInstance.getWidgetName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"widgetName\": ");

			sb.append("\"");

			sb.append(_escape(widgetInstance.getWidgetName()));

			sb.append("\"");
		}

		if (widgetInstance.getWidgetPermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"widgetPermissions\": ");

			sb.append("[");

			for (int i = 0; i < widgetInstance.getWidgetPermissions().length;
				 i++) {

				sb.append(
					String.valueOf(widgetInstance.getWidgetPermissions()[i]));

				if ((i + 1) < widgetInstance.getWidgetPermissions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WidgetInstanceJSONParser widgetInstanceJSONParser =
			new WidgetInstanceJSONParser();

		return widgetInstanceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(WidgetInstance widgetInstance) {
		if (widgetInstance == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (widgetInstance.getWidgetConfig() == null) {
			map.put("widgetConfig", null);
		}
		else {
			map.put(
				"widgetConfig",
				String.valueOf(widgetInstance.getWidgetConfig()));
		}

		if (widgetInstance.getWidgetInstanceId() == null) {
			map.put("widgetInstanceId", null);
		}
		else {
			map.put(
				"widgetInstanceId",
				String.valueOf(widgetInstance.getWidgetInstanceId()));
		}

		if (widgetInstance.getWidgetName() == null) {
			map.put("widgetName", null);
		}
		else {
			map.put(
				"widgetName", String.valueOf(widgetInstance.getWidgetName()));
		}

		if (widgetInstance.getWidgetPermissions() == null) {
			map.put("widgetPermissions", null);
		}
		else {
			map.put(
				"widgetPermissions",
				String.valueOf(widgetInstance.getWidgetPermissions()));
		}

		return map;
	}

	public static class WidgetInstanceJSONParser
		extends BaseJSONParser<WidgetInstance> {

		@Override
		protected WidgetInstance createDTO() {
			return new WidgetInstance();
		}

		@Override
		protected WidgetInstance[] createDTOArray(int size) {
			return new WidgetInstance[size];
		}

		@Override
		protected void setField(
			WidgetInstance widgetInstance, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "widgetConfig")) {
				if (jsonParserFieldValue != null) {
					widgetInstance.setWidgetConfig(
						(Map)WidgetInstanceSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "widgetInstanceId")) {
				if (jsonParserFieldValue != null) {
					widgetInstance.setWidgetInstanceId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "widgetName")) {
				if (jsonParserFieldValue != null) {
					widgetInstance.setWidgetName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "widgetPermissions")) {
				if (jsonParserFieldValue != null) {
					widgetInstance.setWidgetPermissions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> WidgetPermissionSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new WidgetPermission[size]
						));
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}