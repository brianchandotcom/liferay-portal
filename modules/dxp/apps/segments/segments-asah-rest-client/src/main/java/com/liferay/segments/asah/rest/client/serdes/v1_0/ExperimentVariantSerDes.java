/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.rest.client.serdes.v1_0;

import com.liferay.segments.asah.rest.client.dto.v1_0.ExperimentVariant;
import com.liferay.segments.asah.rest.client.json.BaseJSONParser;

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
public class ExperimentVariantSerDes {

	public static ExperimentVariant toDTO(String json) {
		ExperimentVariantJSONParser experimentVariantJSONParser =
			new ExperimentVariantJSONParser();

		return experimentVariantJSONParser.parseToDTO(json);
	}

	public static ExperimentVariant[] toDTOs(String json) {
		ExperimentVariantJSONParser experimentVariantJSONParser =
			new ExperimentVariantJSONParser();

		return experimentVariantJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ExperimentVariant experimentVariant) {
		if (experimentVariant == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (experimentVariant.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(experimentVariant.getId()));

			sb.append("\"");
		}

		if (experimentVariant.getTrafficSplit() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"trafficSplit\": ");

			sb.append(experimentVariant.getTrafficSplit());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ExperimentVariantJSONParser experimentVariantJSONParser =
			new ExperimentVariantJSONParser();

		return experimentVariantJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ExperimentVariant experimentVariant) {

		if (experimentVariant == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (experimentVariant.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(experimentVariant.getId()));
		}

		if (experimentVariant.getTrafficSplit() == null) {
			map.put("trafficSplit", null);
		}
		else {
			map.put(
				"trafficSplit",
				String.valueOf(experimentVariant.getTrafficSplit()));
		}

		return map;
	}

	public static class ExperimentVariantJSONParser
		extends BaseJSONParser<ExperimentVariant> {

		@Override
		protected ExperimentVariant createDTO() {
			return new ExperimentVariant();
		}

		@Override
		protected ExperimentVariant[] createDTOArray(int size) {
			return new ExperimentVariant[size];
		}

		@Override
		protected void setField(
			ExperimentVariant experimentVariant, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					experimentVariant.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "trafficSplit")) {
				if (jsonParserFieldValue != null) {
					experimentVariant.setTrafficSplit(
						Double.valueOf((String)jsonParserFieldValue));
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