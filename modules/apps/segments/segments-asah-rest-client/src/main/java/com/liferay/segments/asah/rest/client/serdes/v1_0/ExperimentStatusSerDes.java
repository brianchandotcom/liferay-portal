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

package com.liferay.segments.asah.rest.client.serdes.v1_0;

import com.liferay.segments.asah.rest.client.dto.v1_0.ExperimentStatus;
import com.liferay.segments.asah.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ExperimentStatusSerDes {

	public static ExperimentStatus toDTO(String json) {
		ExperimentStatusJSONParser experimentStatusJSONParser =
			new ExperimentStatusJSONParser();

		return experimentStatusJSONParser.parseToDTO(json);
	}

	public static ExperimentStatus[] toDTOs(String json) {
		ExperimentStatusJSONParser experimentStatusJSONParser =
			new ExperimentStatusJSONParser();

		return experimentStatusJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ExperimentStatus experimentStatus) {
		if (experimentStatus == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (experimentStatus.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(experimentStatus.getStatus()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ExperimentStatusJSONParser experimentStatusJSONParser =
			new ExperimentStatusJSONParser();

		return experimentStatusJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ExperimentStatus experimentStatus) {
		if (experimentStatus == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (experimentStatus.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(experimentStatus.getStatus()));
		}

		return map;
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

			Class valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
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
				sb.append(entry.getValue());
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ExperimentStatusJSONParser
		extends BaseJSONParser<ExperimentStatus> {

		@Override
		protected ExperimentStatus createDTO() {
			return new ExperimentStatus();
		}

		@Override
		protected ExperimentStatus[] createDTOArray(int size) {
			return new ExperimentStatus[size];
		}

		@Override
		protected void setField(
			ExperimentStatus experimentStatus, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					experimentStatus.setStatus((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}