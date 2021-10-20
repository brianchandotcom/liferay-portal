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

import com.liferay.search.experiences.rest.client.dto.v1_0.Contains;
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
public class ContainsSerDes {

	public static Contains toDTO(String json) {
		ContainsJSONParser containsJSONParser = new ContainsJSONParser();

		return containsJSONParser.parseToDTO(json);
	}

	public static Contains[] toDTOs(String json) {
		ContainsJSONParser containsJSONParser = new ContainsJSONParser();

		return containsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Contains contains) {
		if (contains == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contains.getParameterName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameterName\": ");

			sb.append("\"");

			sb.append(_escape(contains.getParameterName()));

			sb.append("\"");
		}

		if (contains.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(contains.getValue()));

			sb.append("\"");
		}

		if (contains.getValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"values\": ");

			sb.append("[");

			for (int i = 0; i < contains.getValues().length; i++) {
				sb.append("\"");

				sb.append(_escape(contains.getValues()[i]));

				sb.append("\"");

				if ((i + 1) < contains.getValues().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContainsJSONParser containsJSONParser = new ContainsJSONParser();

		return containsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Contains contains) {
		if (contains == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (contains.getParameterName() == null) {
			map.put("parameterName", null);
		}
		else {
			map.put(
				"parameterName", String.valueOf(contains.getParameterName()));
		}

		if (contains.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(contains.getValue()));
		}

		if (contains.getValues() == null) {
			map.put("values", null);
		}
		else {
			map.put("values", String.valueOf(contains.getValues()));
		}

		return map;
	}

	public static class ContainsJSONParser extends BaseJSONParser<Contains> {

		@Override
		protected Contains createDTO() {
			return new Contains();
		}

		@Override
		protected Contains[] createDTOArray(int size) {
			return new Contains[size];
		}

		@Override
		protected void setField(
			Contains contains, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "parameterName")) {
				if (jsonParserFieldValue != null) {
					contains.setParameterName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					contains.setValue((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "values")) {
				if (jsonParserFieldValue != null) {
					contains.setValues((Object[])jsonParserFieldValue);
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