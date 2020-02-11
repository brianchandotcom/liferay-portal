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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentField;
import com.liferay.headless.delivery.client.dto.v1_0.FragmentInstanceDefinition;
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
public class FragmentInstanceDefinitionSerDes {

	public static FragmentInstanceDefinition toDTO(String json) {
		FragmentInstanceDefinitionJSONParser
			fragmentInstanceDefinitionJSONParser =
				new FragmentInstanceDefinitionJSONParser();

		return fragmentInstanceDefinitionJSONParser.parseToDTO(json);
	}

	public static FragmentInstanceDefinition[] toDTOs(String json) {
		FragmentInstanceDefinitionJSONParser
			fragmentInstanceDefinitionJSONParser =
				new FragmentInstanceDefinitionJSONParser();

		return fragmentInstanceDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		FragmentInstanceDefinition fragmentInstanceDefinition) {

		if (fragmentInstanceDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentInstanceDefinition.getFragment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragment\": ");

			sb.append(String.valueOf(fragmentInstanceDefinition.getFragment()));
		}

		if (fragmentInstanceDefinition.getFragmentConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentConfig\": ");

			sb.append(_toJSON(fragmentInstanceDefinition.getFragmentConfig()));
		}

		if (fragmentInstanceDefinition.getFragmentFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentFields\": ");

			sb.append("[");

			for (int i = 0;
				 i < fragmentInstanceDefinition.getFragmentFields().length;
				 i++) {

				sb.append(
					String.valueOf(
						fragmentInstanceDefinition.getFragmentFields()[i]));

				if ((i + 1) <
						fragmentInstanceDefinition.getFragmentFields().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentInstanceDefinitionJSONParser
			fragmentInstanceDefinitionJSONParser =
				new FragmentInstanceDefinitionJSONParser();

		return fragmentInstanceDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentInstanceDefinition fragmentInstanceDefinition) {

		if (fragmentInstanceDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentInstanceDefinition.getFragment() == null) {
			map.put("fragment", null);
		}
		else {
			map.put(
				"fragment",
				String.valueOf(fragmentInstanceDefinition.getFragment()));
		}

		if (fragmentInstanceDefinition.getFragmentConfig() == null) {
			map.put("fragmentConfig", null);
		}
		else {
			map.put(
				"fragmentConfig",
				String.valueOf(fragmentInstanceDefinition.getFragmentConfig()));
		}

		if (fragmentInstanceDefinition.getFragmentFields() == null) {
			map.put("fragmentFields", null);
		}
		else {
			map.put(
				"fragmentFields",
				String.valueOf(fragmentInstanceDefinition.getFragmentFields()));
		}

		return map;
	}

	public static class FragmentInstanceDefinitionJSONParser
		extends BaseJSONParser<FragmentInstanceDefinition> {

		@Override
		protected FragmentInstanceDefinition createDTO() {
			return new FragmentInstanceDefinition();
		}

		@Override
		protected FragmentInstanceDefinition[] createDTOArray(int size) {
			return new FragmentInstanceDefinition[size];
		}

		@Override
		protected void setField(
			FragmentInstanceDefinition fragmentInstanceDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "fragment")) {
				if (jsonParserFieldValue != null) {
					fragmentInstanceDefinition.setFragment(
						FragmentSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentConfig")) {
				if (jsonParserFieldValue != null) {
					fragmentInstanceDefinition.setFragmentConfig(
						(Map)FragmentInstanceDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentFields")) {
				if (jsonParserFieldValue != null) {
					fragmentInstanceDefinition.setFragmentFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FragmentFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new FragmentField[size]
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