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

import com.liferay.search.experiences.rest.client.dto.v1_0.Parameter;
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
public class ParameterSerDes {

	public static Parameter toDTO(String json) {
		ParameterJSONParser parameterJSONParser = new ParameterJSONParser();

		return parameterJSONParser.parseToDTO(json);
	}

	public static Parameter[] toDTOs(String json) {
		ParameterJSONParser parameterJSONParser = new ParameterJSONParser();

		return parameterJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Parameter parameter) {
		if (parameter == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (parameter.getDateFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateFormat\": ");

			sb.append("\"");

			sb.append(_escape(parameter.getDateFormat()));

			sb.append("\"");
		}

		if (parameter.getDefaultValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueDouble\": ");

			sb.append(parameter.getDefaultValueDouble());
		}

		if (parameter.getDefaultValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueFloat\": ");

			sb.append(parameter.getDefaultValueFloat());
		}

		if (parameter.getDefaultValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueInteger\": ");

			sb.append(parameter.getDefaultValueInteger());
		}

		if (parameter.getDefaultValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueLong\": ");

			sb.append(parameter.getDefaultValueLong());
		}

		if (parameter.getDefaultValueString() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueString\": ");

			sb.append("\"");

			sb.append(_escape(parameter.getDefaultValueString()));

			sb.append("\"");
		}

		if (parameter.getDefaultValuesIntegerArray() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValuesIntegerArray\": ");

			sb.append("[");

			for (int i = 0; i < parameter.getDefaultValuesIntegerArray().length;
				 i++) {

				sb.append(parameter.getDefaultValuesIntegerArray()[i]);

				if ((i + 1) < parameter.getDefaultValuesIntegerArray().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (parameter.getDefaultValuesLongArray() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValuesLongArray\": ");

			sb.append("[");

			for (int i = 0; i < parameter.getDefaultValuesLongArray().length;
				 i++) {

				sb.append(parameter.getDefaultValuesLongArray()[i]);

				if ((i + 1) < parameter.getDefaultValuesLongArray().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (parameter.getDefaultValuesStringArray() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValuesStringArray\": ");

			sb.append("[");

			for (int i = 0; i < parameter.getDefaultValuesStringArray().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(parameter.getDefaultValuesStringArray()[i]));

				sb.append("\"");

				if ((i + 1) < parameter.getDefaultValuesStringArray().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (parameter.getMaxValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueDouble\": ");

			sb.append(parameter.getMaxValueDouble());
		}

		if (parameter.getMaxValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueFloat\": ");

			sb.append(parameter.getMaxValueFloat());
		}

		if (parameter.getMaxValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueInteger\": ");

			sb.append(parameter.getMaxValueInteger());
		}

		if (parameter.getMaxValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueLong\": ");

			sb.append(parameter.getMaxValueLong());
		}

		if (parameter.getMinValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueDouble\": ");

			sb.append(parameter.getMinValueDouble());
		}

		if (parameter.getMinValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueFloat\": ");

			sb.append(parameter.getMinValueFloat());
		}

		if (parameter.getMinValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueInteger\": ");

			sb.append(parameter.getMinValueInteger());
		}

		if (parameter.getMinValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueLong\": ");

			sb.append(parameter.getMinValueLong());
		}

		if (parameter.getParameterType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameterType\": ");

			sb.append("\"");

			sb.append(parameter.getParameterType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ParameterJSONParser parameterJSONParser = new ParameterJSONParser();

		return parameterJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Parameter parameter) {
		if (parameter == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (parameter.getDateFormat() == null) {
			map.put("dateFormat", null);
		}
		else {
			map.put("dateFormat", String.valueOf(parameter.getDateFormat()));
		}

		if (parameter.getDefaultValueDouble() == null) {
			map.put("defaultValueDouble", null);
		}
		else {
			map.put(
				"defaultValueDouble",
				String.valueOf(parameter.getDefaultValueDouble()));
		}

		if (parameter.getDefaultValueFloat() == null) {
			map.put("defaultValueFloat", null);
		}
		else {
			map.put(
				"defaultValueFloat",
				String.valueOf(parameter.getDefaultValueFloat()));
		}

		if (parameter.getDefaultValueInteger() == null) {
			map.put("defaultValueInteger", null);
		}
		else {
			map.put(
				"defaultValueInteger",
				String.valueOf(parameter.getDefaultValueInteger()));
		}

		if (parameter.getDefaultValueLong() == null) {
			map.put("defaultValueLong", null);
		}
		else {
			map.put(
				"defaultValueLong",
				String.valueOf(parameter.getDefaultValueLong()));
		}

		if (parameter.getDefaultValueString() == null) {
			map.put("defaultValueString", null);
		}
		else {
			map.put(
				"defaultValueString",
				String.valueOf(parameter.getDefaultValueString()));
		}

		if (parameter.getDefaultValuesIntegerArray() == null) {
			map.put("defaultValuesIntegerArray", null);
		}
		else {
			map.put(
				"defaultValuesIntegerArray",
				String.valueOf(parameter.getDefaultValuesIntegerArray()));
		}

		if (parameter.getDefaultValuesLongArray() == null) {
			map.put("defaultValuesLongArray", null);
		}
		else {
			map.put(
				"defaultValuesLongArray",
				String.valueOf(parameter.getDefaultValuesLongArray()));
		}

		if (parameter.getDefaultValuesStringArray() == null) {
			map.put("defaultValuesStringArray", null);
		}
		else {
			map.put(
				"defaultValuesStringArray",
				String.valueOf(parameter.getDefaultValuesStringArray()));
		}

		if (parameter.getMaxValueDouble() == null) {
			map.put("maxValueDouble", null);
		}
		else {
			map.put(
				"maxValueDouble",
				String.valueOf(parameter.getMaxValueDouble()));
		}

		if (parameter.getMaxValueFloat() == null) {
			map.put("maxValueFloat", null);
		}
		else {
			map.put(
				"maxValueFloat", String.valueOf(parameter.getMaxValueFloat()));
		}

		if (parameter.getMaxValueInteger() == null) {
			map.put("maxValueInteger", null);
		}
		else {
			map.put(
				"maxValueInteger",
				String.valueOf(parameter.getMaxValueInteger()));
		}

		if (parameter.getMaxValueLong() == null) {
			map.put("maxValueLong", null);
		}
		else {
			map.put(
				"maxValueLong", String.valueOf(parameter.getMaxValueLong()));
		}

		if (parameter.getMinValueDouble() == null) {
			map.put("minValueDouble", null);
		}
		else {
			map.put(
				"minValueDouble",
				String.valueOf(parameter.getMinValueDouble()));
		}

		if (parameter.getMinValueFloat() == null) {
			map.put("minValueFloat", null);
		}
		else {
			map.put(
				"minValueFloat", String.valueOf(parameter.getMinValueFloat()));
		}

		if (parameter.getMinValueInteger() == null) {
			map.put("minValueInteger", null);
		}
		else {
			map.put(
				"minValueInteger",
				String.valueOf(parameter.getMinValueInteger()));
		}

		if (parameter.getMinValueLong() == null) {
			map.put("minValueLong", null);
		}
		else {
			map.put(
				"minValueLong", String.valueOf(parameter.getMinValueLong()));
		}

		if (parameter.getParameterType() == null) {
			map.put("parameterType", null);
		}
		else {
			map.put(
				"parameterType", String.valueOf(parameter.getParameterType()));
		}

		return map;
	}

	public static class ParameterJSONParser extends BaseJSONParser<Parameter> {

		@Override
		protected Parameter createDTO() {
			return new Parameter();
		}

		@Override
		protected Parameter[] createDTOArray(int size) {
			return new Parameter[size];
		}

		@Override
		protected void setField(
			Parameter parameter, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateFormat")) {
				if (jsonParserFieldValue != null) {
					parameter.setDateFormat((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValueDouble")) {

				if (jsonParserFieldValue != null) {
					parameter.setDefaultValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultValueFloat")) {
				if (jsonParserFieldValue != null) {
					parameter.setDefaultValueFloat(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValueInteger")) {

				if (jsonParserFieldValue != null) {
					parameter.setDefaultValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultValueLong")) {
				if (jsonParserFieldValue != null) {
					parameter.setDefaultValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValueString")) {

				if (jsonParserFieldValue != null) {
					parameter.setDefaultValueString(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValuesIntegerArray")) {

				if (jsonParserFieldValue != null) {
					parameter.setDefaultValuesIntegerArray(
						toIntegers((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValuesLongArray")) {

				if (jsonParserFieldValue != null) {
					parameter.setDefaultValuesLongArray(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValuesStringArray")) {

				if (jsonParserFieldValue != null) {
					parameter.setDefaultValuesStringArray(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueDouble")) {
				if (jsonParserFieldValue != null) {
					parameter.setMaxValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueFloat")) {
				if (jsonParserFieldValue != null) {
					parameter.setMaxValueFloat(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueInteger")) {
				if (jsonParserFieldValue != null) {
					parameter.setMaxValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueLong")) {
				if (jsonParserFieldValue != null) {
					parameter.setMaxValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueDouble")) {
				if (jsonParserFieldValue != null) {
					parameter.setMinValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueFloat")) {
				if (jsonParserFieldValue != null) {
					parameter.setMinValueFloat(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueInteger")) {
				if (jsonParserFieldValue != null) {
					parameter.setMinValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueLong")) {
				if (jsonParserFieldValue != null) {
					parameter.setMinValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parameterType")) {
				if (jsonParserFieldValue != null) {
					parameter.setParameterType(
						Parameter.ParameterType.create(
							(String)jsonParserFieldValue));
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