/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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