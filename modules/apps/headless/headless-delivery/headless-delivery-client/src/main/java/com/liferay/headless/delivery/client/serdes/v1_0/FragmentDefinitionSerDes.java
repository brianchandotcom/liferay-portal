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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentContentField;
import com.liferay.headless.delivery.client.dto.v1_0.FragmentDefinition;
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
public class FragmentDefinitionSerDes {

	public static FragmentDefinition toDTO(String json) {
		FragmentDefinitionJSONParser fragmentDefinitionJSONParser =
			new FragmentDefinitionJSONParser();

		return fragmentDefinitionJSONParser.parseToDTO(json);
	}

	public static FragmentDefinition[] toDTOs(String json) {
		FragmentDefinitionJSONParser fragmentDefinitionJSONParser =
			new FragmentDefinitionJSONParser();

		return fragmentDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentDefinition fragmentDefinition) {
		if (fragmentDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentDefinition.getFragmentCollectionName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentCollectionName\": ");

			sb.append("\"");

			sb.append(_escape(fragmentDefinition.getFragmentCollectionName()));

			sb.append("\"");
		}

		if (fragmentDefinition.getFragmentConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentConfig\": ");

			sb.append(_toJSON(fragmentDefinition.getFragmentConfig()));
		}

		if (fragmentDefinition.getFragmentContentFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentContentFields\": ");

			sb.append("[");

			for (int i = 0;
				 i < fragmentDefinition.getFragmentContentFields().length;
				 i++) {

				sb.append(
					String.valueOf(
						fragmentDefinition.getFragmentContentFields()[i]));

				if ((i + 1) <
						fragmentDefinition.getFragmentContentFields().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (fragmentDefinition.getFragmentName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentName\": ");

			sb.append("\"");

			sb.append(_escape(fragmentDefinition.getFragmentName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentDefinitionJSONParser fragmentDefinitionJSONParser =
			new FragmentDefinitionJSONParser();

		return fragmentDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentDefinition fragmentDefinition) {

		if (fragmentDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentDefinition.getFragmentCollectionName() == null) {
			map.put("fragmentCollectionName", null);
		}
		else {
			map.put(
				"fragmentCollectionName",
				String.valueOf(fragmentDefinition.getFragmentCollectionName()));
		}

		if (fragmentDefinition.getFragmentConfig() == null) {
			map.put("fragmentConfig", null);
		}
		else {
			map.put(
				"fragmentConfig",
				String.valueOf(fragmentDefinition.getFragmentConfig()));
		}

		if (fragmentDefinition.getFragmentContentFields() == null) {
			map.put("fragmentContentFields", null);
		}
		else {
			map.put(
				"fragmentContentFields",
				String.valueOf(fragmentDefinition.getFragmentContentFields()));
		}

		if (fragmentDefinition.getFragmentName() == null) {
			map.put("fragmentName", null);
		}
		else {
			map.put(
				"fragmentName",
				String.valueOf(fragmentDefinition.getFragmentName()));
		}

		return map;
	}

	public static class FragmentDefinitionJSONParser
		extends BaseJSONParser<FragmentDefinition> {

		@Override
		protected FragmentDefinition createDTO() {
			return new FragmentDefinition();
		}

		@Override
		protected FragmentDefinition[] createDTOArray(int size) {
			return new FragmentDefinition[size];
		}

		@Override
		protected void setField(
			FragmentDefinition fragmentDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "fragmentCollectionName")) {
				if (jsonParserFieldValue != null) {
					fragmentDefinition.setFragmentCollectionName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentConfig")) {
				if (jsonParserFieldValue != null) {
					fragmentDefinition.setFragmentConfig(
						(Map)FragmentDefinitionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "fragmentContentFields")) {

				if (jsonParserFieldValue != null) {
					fragmentDefinition.setFragmentContentFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FragmentContentFieldSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new FragmentContentField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentName")) {
				if (jsonParserFieldValue != null) {
					fragmentDefinition.setFragmentName(
						(String)jsonParserFieldValue);
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