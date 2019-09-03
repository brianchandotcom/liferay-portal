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

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.Status;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class StatusSerDes {

	public static Status toDTO(String json) {
		StatusJSONParser statusJSONParser = new StatusJSONParser();

		return statusJSONParser.parseToDTO(json);
	}

	public static Status[] toDTOs(String json) {
		StatusJSONParser statusJSONParser = new StatusJSONParser();

		return statusJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Status status) {
		if (status == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (status.getActionInProgress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actionInProgress\": ");

			sb.append(status.getActionInProgress());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		StatusJSONParser statusJSONParser = new StatusJSONParser();

		return statusJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Status status) {
		if (status == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (status.getActionInProgress() == null) {
			map.put("actionInProgress", null);
		}
		else {
			map.put(
				"actionInProgress",
				String.valueOf(status.getActionInProgress()));
		}

		return map;
	}

	public static class StatusJSONParser extends BaseJSONParser<Status> {

		@Override
		protected Status createDTO() {
			return new Status();
		}

		@Override
		protected Status[] createDTOArray(int size) {
			return new Status[size];
		}

		@Override
		protected void setField(
			Status status, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actionInProgress")) {
				if (jsonParserFieldValue != null) {
					status.setActionInProgress((Boolean)jsonParserFieldValue);
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