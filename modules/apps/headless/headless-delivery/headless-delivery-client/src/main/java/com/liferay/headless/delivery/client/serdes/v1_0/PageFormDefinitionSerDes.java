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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.client.dto.v1_0.PageFormDefinition;
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
public class PageFormDefinitionSerDes {

	public static PageFormDefinition toDTO(String json) {
		PageFormDefinitionJSONParser pageFormDefinitionJSONParser =
			new PageFormDefinitionJSONParser();

		return pageFormDefinitionJSONParser.parseToDTO(json);
	}

	public static PageFormDefinition[] toDTOs(String json) {
		PageFormDefinitionJSONParser pageFormDefinitionJSONParser =
			new PageFormDefinitionJSONParser();

		return pageFormDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageFormDefinition pageFormDefinition) {
		if (pageFormDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageFormDefinition.getFormConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formConfig\": ");

			sb.append(String.valueOf(pageFormDefinition.getFormConfig()));
		}

		if (pageFormDefinition.getFragmentStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentStyle\": ");

			sb.append(String.valueOf(pageFormDefinition.getFragmentStyle()));
		}

		if (pageFormDefinition.getFragmentViewports() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentViewports\": ");

			sb.append("[");

			for (int i = 0;
				 i < pageFormDefinition.getFragmentViewports().length; i++) {

				sb.append(
					String.valueOf(
						pageFormDefinition.getFragmentViewports()[i]));

				if ((i + 1) <
						pageFormDefinition.getFragmentViewports().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageFormDefinition.getIndexed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexed\": ");

			sb.append(pageFormDefinition.getIndexed());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageFormDefinitionJSONParser pageFormDefinitionJSONParser =
			new PageFormDefinitionJSONParser();

		return pageFormDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageFormDefinition pageFormDefinition) {

		if (pageFormDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageFormDefinition.getFormConfig() == null) {
			map.put("formConfig", null);
		}
		else {
			map.put(
				"formConfig",
				String.valueOf(pageFormDefinition.getFormConfig()));
		}

		if (pageFormDefinition.getFragmentStyle() == null) {
			map.put("fragmentStyle", null);
		}
		else {
			map.put(
				"fragmentStyle",
				String.valueOf(pageFormDefinition.getFragmentStyle()));
		}

		if (pageFormDefinition.getFragmentViewports() == null) {
			map.put("fragmentViewports", null);
		}
		else {
			map.put(
				"fragmentViewports",
				String.valueOf(pageFormDefinition.getFragmentViewports()));
		}

		if (pageFormDefinition.getIndexed() == null) {
			map.put("indexed", null);
		}
		else {
			map.put("indexed", String.valueOf(pageFormDefinition.getIndexed()));
		}

		return map;
	}

	public static class PageFormDefinitionJSONParser
		extends BaseJSONParser<PageFormDefinition> {

		@Override
		protected PageFormDefinition createDTO() {
			return new PageFormDefinition();
		}

		@Override
		protected PageFormDefinition[] createDTOArray(int size) {
			return new PageFormDefinition[size];
		}

		@Override
		protected void setField(
			PageFormDefinition pageFormDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "formConfig")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setFormConfig(
						FormConfigSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentStyle")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setFragmentStyle(
						FragmentStyleSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentViewports")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setFragmentViewports(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FragmentViewportSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new FragmentViewport[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexed")) {
				if (jsonParserFieldValue != null) {
					pageFormDefinition.setIndexed(
						(Boolean)jsonParserFieldValue);
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