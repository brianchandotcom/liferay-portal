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

import com.liferay.search.experiences.rest.client.dto.v1_0.ValueDefinition;
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
public class ValueDefinitionSerDes {

	public static ValueDefinition toDTO(String json) {
		ValueDefinitionJSONParser valueDefinitionJSONParser =
			new ValueDefinitionJSONParser();

		return valueDefinitionJSONParser.parseToDTO(json);
	}

	public static ValueDefinition[] toDTOs(String json) {
		ValueDefinitionJSONParser valueDefinitionJSONParser =
			new ValueDefinitionJSONParser();

		return valueDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ValueDefinition valueDefinition) {
		if (valueDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (valueDefinition.getDefaultValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueDouble\": ");

			sb.append(valueDefinition.getDefaultValueDouble());
		}

		if (valueDefinition.getDefaultValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueFloat\": ");

			sb.append(valueDefinition.getDefaultValueFloat());
		}

		if (valueDefinition.getDefaultValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueInteger\": ");

			sb.append(valueDefinition.getDefaultValueInteger());
		}

		if (valueDefinition.getDefaultValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueLong\": ");

			sb.append(valueDefinition.getDefaultValueLong());
		}

		if (valueDefinition.getDefaultValueString() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueString\": ");

			sb.append("\"");

			sb.append(_escape(valueDefinition.getDefaultValueString()));

			sb.append("\"");
		}

		if (valueDefinition.getDefaultValuesIntegerArray() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValuesIntegerArray\": ");

			sb.append("[");

			for (int i = 0;
				 i < valueDefinition.getDefaultValuesIntegerArray().length;
				 i++) {

				sb.append(valueDefinition.getDefaultValuesIntegerArray()[i]);

				if ((i + 1) <
						valueDefinition.getDefaultValuesIntegerArray().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (valueDefinition.getDefaultValuesLongArray() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValuesLongArray\": ");

			sb.append("[");

			for (int i = 0;
				 i < valueDefinition.getDefaultValuesLongArray().length; i++) {

				sb.append(valueDefinition.getDefaultValuesLongArray()[i]);

				if ((i + 1) <
						valueDefinition.getDefaultValuesLongArray().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (valueDefinition.getDefaultValuesStringArray() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValuesStringArray\": ");

			sb.append("[");

			for (int i = 0;
				 i < valueDefinition.getDefaultValuesStringArray().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(valueDefinition.getDefaultValuesStringArray()[i]));

				sb.append("\"");

				if ((i + 1) <
						valueDefinition.getDefaultValuesStringArray().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (valueDefinition.getFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"format\": ");

			sb.append("\"");

			sb.append(_escape(valueDefinition.getFormat()));

			sb.append("\"");
		}

		if (valueDefinition.getMaxValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueDouble\": ");

			sb.append(valueDefinition.getMaxValueDouble());
		}

		if (valueDefinition.getMaxValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueFloat\": ");

			sb.append(valueDefinition.getMaxValueFloat());
		}

		if (valueDefinition.getMaxValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueInteger\": ");

			sb.append(valueDefinition.getMaxValueInteger());
		}

		if (valueDefinition.getMaxValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueLong\": ");

			sb.append(valueDefinition.getMaxValueLong());
		}

		if (valueDefinition.getMinValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueDouble\": ");

			sb.append(valueDefinition.getMinValueDouble());
		}

		if (valueDefinition.getMinValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueFloat\": ");

			sb.append(valueDefinition.getMinValueFloat());
		}

		if (valueDefinition.getMinValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueInteger\": ");

			sb.append(valueDefinition.getMinValueInteger());
		}

		if (valueDefinition.getMinValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueLong\": ");

			sb.append(valueDefinition.getMinValueLong());
		}

		if (valueDefinition.getStepValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"stepValueDouble\": ");

			sb.append(valueDefinition.getStepValueDouble());
		}

		if (valueDefinition.getStepValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"stepValueFloat\": ");

			sb.append(valueDefinition.getStepValueFloat());
		}

		if (valueDefinition.getStepValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"stepValueInteger\": ");

			sb.append(valueDefinition.getStepValueInteger());
		}

		if (valueDefinition.getStepValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"stepValueLong\": ");

			sb.append(valueDefinition.getStepValueLong());
		}

		if (valueDefinition.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(valueDefinition.getType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ValueDefinitionJSONParser valueDefinitionJSONParser =
			new ValueDefinitionJSONParser();

		return valueDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ValueDefinition valueDefinition) {
		if (valueDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (valueDefinition.getDefaultValueDouble() == null) {
			map.put("defaultValueDouble", null);
		}
		else {
			map.put(
				"defaultValueDouble",
				String.valueOf(valueDefinition.getDefaultValueDouble()));
		}

		if (valueDefinition.getDefaultValueFloat() == null) {
			map.put("defaultValueFloat", null);
		}
		else {
			map.put(
				"defaultValueFloat",
				String.valueOf(valueDefinition.getDefaultValueFloat()));
		}

		if (valueDefinition.getDefaultValueInteger() == null) {
			map.put("defaultValueInteger", null);
		}
		else {
			map.put(
				"defaultValueInteger",
				String.valueOf(valueDefinition.getDefaultValueInteger()));
		}

		if (valueDefinition.getDefaultValueLong() == null) {
			map.put("defaultValueLong", null);
		}
		else {
			map.put(
				"defaultValueLong",
				String.valueOf(valueDefinition.getDefaultValueLong()));
		}

		if (valueDefinition.getDefaultValueString() == null) {
			map.put("defaultValueString", null);
		}
		else {
			map.put(
				"defaultValueString",
				String.valueOf(valueDefinition.getDefaultValueString()));
		}

		if (valueDefinition.getDefaultValuesIntegerArray() == null) {
			map.put("defaultValuesIntegerArray", null);
		}
		else {
			map.put(
				"defaultValuesIntegerArray",
				String.valueOf(valueDefinition.getDefaultValuesIntegerArray()));
		}

		if (valueDefinition.getDefaultValuesLongArray() == null) {
			map.put("defaultValuesLongArray", null);
		}
		else {
			map.put(
				"defaultValuesLongArray",
				String.valueOf(valueDefinition.getDefaultValuesLongArray()));
		}

		if (valueDefinition.getDefaultValuesStringArray() == null) {
			map.put("defaultValuesStringArray", null);
		}
		else {
			map.put(
				"defaultValuesStringArray",
				String.valueOf(valueDefinition.getDefaultValuesStringArray()));
		}

		if (valueDefinition.getFormat() == null) {
			map.put("format", null);
		}
		else {
			map.put("format", String.valueOf(valueDefinition.getFormat()));
		}

		if (valueDefinition.getMaxValueDouble() == null) {
			map.put("maxValueDouble", null);
		}
		else {
			map.put(
				"maxValueDouble",
				String.valueOf(valueDefinition.getMaxValueDouble()));
		}

		if (valueDefinition.getMaxValueFloat() == null) {
			map.put("maxValueFloat", null);
		}
		else {
			map.put(
				"maxValueFloat",
				String.valueOf(valueDefinition.getMaxValueFloat()));
		}

		if (valueDefinition.getMaxValueInteger() == null) {
			map.put("maxValueInteger", null);
		}
		else {
			map.put(
				"maxValueInteger",
				String.valueOf(valueDefinition.getMaxValueInteger()));
		}

		if (valueDefinition.getMaxValueLong() == null) {
			map.put("maxValueLong", null);
		}
		else {
			map.put(
				"maxValueLong",
				String.valueOf(valueDefinition.getMaxValueLong()));
		}

		if (valueDefinition.getMinValueDouble() == null) {
			map.put("minValueDouble", null);
		}
		else {
			map.put(
				"minValueDouble",
				String.valueOf(valueDefinition.getMinValueDouble()));
		}

		if (valueDefinition.getMinValueFloat() == null) {
			map.put("minValueFloat", null);
		}
		else {
			map.put(
				"minValueFloat",
				String.valueOf(valueDefinition.getMinValueFloat()));
		}

		if (valueDefinition.getMinValueInteger() == null) {
			map.put("minValueInteger", null);
		}
		else {
			map.put(
				"minValueInteger",
				String.valueOf(valueDefinition.getMinValueInteger()));
		}

		if (valueDefinition.getMinValueLong() == null) {
			map.put("minValueLong", null);
		}
		else {
			map.put(
				"minValueLong",
				String.valueOf(valueDefinition.getMinValueLong()));
		}

		if (valueDefinition.getStepValueDouble() == null) {
			map.put("stepValueDouble", null);
		}
		else {
			map.put(
				"stepValueDouble",
				String.valueOf(valueDefinition.getStepValueDouble()));
		}

		if (valueDefinition.getStepValueFloat() == null) {
			map.put("stepValueFloat", null);
		}
		else {
			map.put(
				"stepValueFloat",
				String.valueOf(valueDefinition.getStepValueFloat()));
		}

		if (valueDefinition.getStepValueInteger() == null) {
			map.put("stepValueInteger", null);
		}
		else {
			map.put(
				"stepValueInteger",
				String.valueOf(valueDefinition.getStepValueInteger()));
		}

		if (valueDefinition.getStepValueLong() == null) {
			map.put("stepValueLong", null);
		}
		else {
			map.put(
				"stepValueLong",
				String.valueOf(valueDefinition.getStepValueLong()));
		}

		if (valueDefinition.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(valueDefinition.getType()));
		}

		return map;
	}

	public static class ValueDefinitionJSONParser
		extends BaseJSONParser<ValueDefinition> {

		@Override
		protected ValueDefinition createDTO() {
			return new ValueDefinition();
		}

		@Override
		protected ValueDefinition[] createDTOArray(int size) {
			return new ValueDefinition[size];
		}

		@Override
		protected void setField(
			ValueDefinition valueDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "defaultValueDouble")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setDefaultValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultValueFloat")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setDefaultValueFloat(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValueInteger")) {

				if (jsonParserFieldValue != null) {
					valueDefinition.setDefaultValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultValueLong")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setDefaultValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValueString")) {

				if (jsonParserFieldValue != null) {
					valueDefinition.setDefaultValueString(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValuesIntegerArray")) {

				if (jsonParserFieldValue != null) {
					valueDefinition.setDefaultValuesIntegerArray(
						toIntegers((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValuesLongArray")) {

				if (jsonParserFieldValue != null) {
					valueDefinition.setDefaultValuesLongArray(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValuesStringArray")) {

				if (jsonParserFieldValue != null) {
					valueDefinition.setDefaultValuesStringArray(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "format")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setFormat((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueDouble")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setMaxValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueFloat")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setMaxValueFloat(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueInteger")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setMaxValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueLong")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setMaxValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueDouble")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setMinValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueFloat")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setMinValueFloat(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueInteger")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setMinValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueLong")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setMinValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "stepValueDouble")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setStepValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "stepValueFloat")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setStepValueFloat(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "stepValueInteger")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setStepValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "stepValueLong")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setStepValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					valueDefinition.setType(
						ValueDefinition.Type.create(
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