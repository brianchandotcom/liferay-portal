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

import com.liferay.search.experiences.rest.client.dto.v1_0.SearchParameter;
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
public class SearchParameterSerDes {

	public static SearchParameter toDTO(String json) {
		SearchParameterJSONParser searchParameterJSONParser =
			new SearchParameterJSONParser();

		return searchParameterJSONParser.parseToDTO(json);
	}

	public static SearchParameter[] toDTOs(String json) {
		SearchParameterJSONParser searchParameterJSONParser =
			new SearchParameterJSONParser();

		return searchParameterJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SearchParameter searchParameter) {
		if (searchParameter == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (searchParameter.getDateFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateFormat\": ");

			sb.append("\"");

			sb.append(_escape(searchParameter.getDateFormat()));

			sb.append("\"");
		}

		if (searchParameter.getDefaultValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueDouble\": ");

			sb.append(searchParameter.getDefaultValueDouble());
		}

		if (searchParameter.getDefaultValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueFloat\": ");

			sb.append(searchParameter.getDefaultValueFloat());
		}

		if (searchParameter.getDefaultValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueInteger\": ");

			sb.append(searchParameter.getDefaultValueInteger());
		}

		if (searchParameter.getDefaultValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueLong\": ");

			sb.append(searchParameter.getDefaultValueLong());
		}

		if (searchParameter.getDefaultValueString() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValueString\": ");

			sb.append("\"");

			sb.append(_escape(searchParameter.getDefaultValueString()));

			sb.append("\"");
		}

		if (searchParameter.getDefaultValuesIntegerArray() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValuesIntegerArray\": ");

			sb.append("[");

			for (int i = 0;
				 i < searchParameter.getDefaultValuesIntegerArray().length;
				 i++) {

				sb.append(searchParameter.getDefaultValuesIntegerArray()[i]);

				if ((i + 1) <
						searchParameter.getDefaultValuesIntegerArray().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (searchParameter.getDefaultValuesLongArray() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValuesLongArray\": ");

			sb.append("[");

			for (int i = 0;
				 i < searchParameter.getDefaultValuesLongArray().length; i++) {

				sb.append(searchParameter.getDefaultValuesLongArray()[i]);

				if ((i + 1) <
						searchParameter.getDefaultValuesLongArray().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (searchParameter.getDefaultValuesStringArray() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultValuesStringArray\": ");

			sb.append("[");

			for (int i = 0;
				 i < searchParameter.getDefaultValuesStringArray().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(searchParameter.getDefaultValuesStringArray()[i]));

				sb.append("\"");

				if ((i + 1) <
						searchParameter.getDefaultValuesStringArray().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (searchParameter.getMaxValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueDouble\": ");

			sb.append(searchParameter.getMaxValueDouble());
		}

		if (searchParameter.getMaxValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueFloat\": ");

			sb.append(searchParameter.getMaxValueFloat());
		}

		if (searchParameter.getMaxValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueInteger\": ");

			sb.append(searchParameter.getMaxValueInteger());
		}

		if (searchParameter.getMaxValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxValueLong\": ");

			sb.append(searchParameter.getMaxValueLong());
		}

		if (searchParameter.getMinValueDouble() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueDouble\": ");

			sb.append(searchParameter.getMinValueDouble());
		}

		if (searchParameter.getMinValueFloat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueFloat\": ");

			sb.append(searchParameter.getMinValueFloat());
		}

		if (searchParameter.getMinValueInteger() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueInteger\": ");

			sb.append(searchParameter.getMinValueInteger());
		}

		if (searchParameter.getMinValueLong() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minValueLong\": ");

			sb.append(searchParameter.getMinValueLong());
		}

		if (searchParameter.getParameterType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameterType\": ");

			sb.append("\"");

			sb.append(searchParameter.getParameterType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SearchParameterJSONParser searchParameterJSONParser =
			new SearchParameterJSONParser();

		return searchParameterJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SearchParameter searchParameter) {
		if (searchParameter == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (searchParameter.getDateFormat() == null) {
			map.put("dateFormat", null);
		}
		else {
			map.put(
				"dateFormat", String.valueOf(searchParameter.getDateFormat()));
		}

		if (searchParameter.getDefaultValueDouble() == null) {
			map.put("defaultValueDouble", null);
		}
		else {
			map.put(
				"defaultValueDouble",
				String.valueOf(searchParameter.getDefaultValueDouble()));
		}

		if (searchParameter.getDefaultValueFloat() == null) {
			map.put("defaultValueFloat", null);
		}
		else {
			map.put(
				"defaultValueFloat",
				String.valueOf(searchParameter.getDefaultValueFloat()));
		}

		if (searchParameter.getDefaultValueInteger() == null) {
			map.put("defaultValueInteger", null);
		}
		else {
			map.put(
				"defaultValueInteger",
				String.valueOf(searchParameter.getDefaultValueInteger()));
		}

		if (searchParameter.getDefaultValueLong() == null) {
			map.put("defaultValueLong", null);
		}
		else {
			map.put(
				"defaultValueLong",
				String.valueOf(searchParameter.getDefaultValueLong()));
		}

		if (searchParameter.getDefaultValueString() == null) {
			map.put("defaultValueString", null);
		}
		else {
			map.put(
				"defaultValueString",
				String.valueOf(searchParameter.getDefaultValueString()));
		}

		if (searchParameter.getDefaultValuesIntegerArray() == null) {
			map.put("defaultValuesIntegerArray", null);
		}
		else {
			map.put(
				"defaultValuesIntegerArray",
				String.valueOf(searchParameter.getDefaultValuesIntegerArray()));
		}

		if (searchParameter.getDefaultValuesLongArray() == null) {
			map.put("defaultValuesLongArray", null);
		}
		else {
			map.put(
				"defaultValuesLongArray",
				String.valueOf(searchParameter.getDefaultValuesLongArray()));
		}

		if (searchParameter.getDefaultValuesStringArray() == null) {
			map.put("defaultValuesStringArray", null);
		}
		else {
			map.put(
				"defaultValuesStringArray",
				String.valueOf(searchParameter.getDefaultValuesStringArray()));
		}

		if (searchParameter.getMaxValueDouble() == null) {
			map.put("maxValueDouble", null);
		}
		else {
			map.put(
				"maxValueDouble",
				String.valueOf(searchParameter.getMaxValueDouble()));
		}

		if (searchParameter.getMaxValueFloat() == null) {
			map.put("maxValueFloat", null);
		}
		else {
			map.put(
				"maxValueFloat",
				String.valueOf(searchParameter.getMaxValueFloat()));
		}

		if (searchParameter.getMaxValueInteger() == null) {
			map.put("maxValueInteger", null);
		}
		else {
			map.put(
				"maxValueInteger",
				String.valueOf(searchParameter.getMaxValueInteger()));
		}

		if (searchParameter.getMaxValueLong() == null) {
			map.put("maxValueLong", null);
		}
		else {
			map.put(
				"maxValueLong",
				String.valueOf(searchParameter.getMaxValueLong()));
		}

		if (searchParameter.getMinValueDouble() == null) {
			map.put("minValueDouble", null);
		}
		else {
			map.put(
				"minValueDouble",
				String.valueOf(searchParameter.getMinValueDouble()));
		}

		if (searchParameter.getMinValueFloat() == null) {
			map.put("minValueFloat", null);
		}
		else {
			map.put(
				"minValueFloat",
				String.valueOf(searchParameter.getMinValueFloat()));
		}

		if (searchParameter.getMinValueInteger() == null) {
			map.put("minValueInteger", null);
		}
		else {
			map.put(
				"minValueInteger",
				String.valueOf(searchParameter.getMinValueInteger()));
		}

		if (searchParameter.getMinValueLong() == null) {
			map.put("minValueLong", null);
		}
		else {
			map.put(
				"minValueLong",
				String.valueOf(searchParameter.getMinValueLong()));
		}

		if (searchParameter.getParameterType() == null) {
			map.put("parameterType", null);
		}
		else {
			map.put(
				"parameterType",
				String.valueOf(searchParameter.getParameterType()));
		}

		return map;
	}

	public static class SearchParameterJSONParser
		extends BaseJSONParser<SearchParameter> {

		@Override
		protected SearchParameter createDTO() {
			return new SearchParameter();
		}

		@Override
		protected SearchParameter[] createDTOArray(int size) {
			return new SearchParameter[size];
		}

		@Override
		protected void setField(
			SearchParameter searchParameter, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateFormat")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setDateFormat((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValueDouble")) {

				if (jsonParserFieldValue != null) {
					searchParameter.setDefaultValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultValueFloat")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setDefaultValueFloat(
						(Float)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValueInteger")) {

				if (jsonParserFieldValue != null) {
					searchParameter.setDefaultValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultValueLong")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setDefaultValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValueString")) {

				if (jsonParserFieldValue != null) {
					searchParameter.setDefaultValueString(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValuesIntegerArray")) {

				if (jsonParserFieldValue != null) {
					searchParameter.setDefaultValuesIntegerArray(
						toIntegers((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValuesLongArray")) {

				if (jsonParserFieldValue != null) {
					searchParameter.setDefaultValuesLongArray(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultValuesStringArray")) {

				if (jsonParserFieldValue != null) {
					searchParameter.setDefaultValuesStringArray(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueDouble")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setMaxValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueFloat")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setMaxValueFloat(
						(Float)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueInteger")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setMaxValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxValueLong")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setMaxValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueDouble")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setMinValueDouble(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueFloat")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setMinValueFloat(
						(Float)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueInteger")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setMinValueInteger(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minValueLong")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setMinValueLong(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parameterType")) {
				if (jsonParserFieldValue != null) {
					searchParameter.setParameterType(
						SearchParameter.ParameterType.create(
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