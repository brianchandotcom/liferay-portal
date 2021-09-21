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

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.Cardinality;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class CardinalitySerDes {

	public static Cardinality toDTO(String json) {
		CardinalityJSONParser cardinalityJSONParser =
			new CardinalityJSONParser();

		return cardinalityJSONParser.parseToDTO(json);
	}

	public static Cardinality[] toDTOs(String json) {
		CardinalityJSONParser cardinalityJSONParser =
			new CardinalityJSONParser();

		return cardinalityJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Cardinality cardinality) {
		if (cardinality == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (cardinality.getField() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"field\": ");

			sb.append("\"");

			sb.append(_escape(cardinality.getField()));

			sb.append("\"");
		}

		if (cardinality.getPrecision_threshold() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"precision_threshold\": ");

			sb.append(cardinality.getPrecision_threshold());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CardinalityJSONParser cardinalityJSONParser =
			new CardinalityJSONParser();

		return cardinalityJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Cardinality cardinality) {
		if (cardinality == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (cardinality.getField() == null) {
			map.put("field", null);
		}
		else {
			map.put("field", String.valueOf(cardinality.getField()));
		}

		if (cardinality.getPrecision_threshold() == null) {
			map.put("precision_threshold", null);
		}
		else {
			map.put(
				"precision_threshold",
				String.valueOf(cardinality.getPrecision_threshold()));
		}

		return map;
	}

	public static class CardinalityJSONParser
		extends BaseJSONParser<Cardinality> {

		@Override
		protected Cardinality createDTO() {
			return new Cardinality();
		}

		@Override
		protected Cardinality[] createDTOArray(int size) {
			return new Cardinality[size];
		}

		@Override
		protected void setField(
			Cardinality cardinality, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "field")) {
				if (jsonParserFieldValue != null) {
					cardinality.setField((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "precision_threshold")) {

				if (jsonParserFieldValue != null) {
					cardinality.setPrecision_threshold(
						Integer.valueOf((String)jsonParserFieldValue));
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