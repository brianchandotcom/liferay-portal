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

import com.liferay.headless.delivery.client.dto.v1_0.DropZoneDefinition;
import com.liferay.headless.delivery.client.dto.v1_0.Fragment;
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
public class DropZoneDefinitionSerDes {

	public static DropZoneDefinition toDTO(String json) {
		DropZoneDefinitionJSONParser dropZoneDefinitionJSONParser =
			new DropZoneDefinitionJSONParser();

		return dropZoneDefinitionJSONParser.parseToDTO(json);
	}

	public static DropZoneDefinition[] toDTOs(String json) {
		DropZoneDefinitionJSONParser dropZoneDefinitionJSONParser =
			new DropZoneDefinitionJSONParser();

		return dropZoneDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DropZoneDefinition dropZoneDefinition) {
		if (dropZoneDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dropZoneDefinition.getAllowNewFragments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"allowNewFragments\": ");

			sb.append(dropZoneDefinition.getAllowNewFragments());
		}

		if (dropZoneDefinition.getFragments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragments\": ");

			sb.append("[");

			for (int i = 0; i < dropZoneDefinition.getFragments().length; i++) {
				sb.append(String.valueOf(dropZoneDefinition.getFragments()[i]));

				if ((i + 1) < dropZoneDefinition.getFragments().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DropZoneDefinitionJSONParser dropZoneDefinitionJSONParser =
			new DropZoneDefinitionJSONParser();

		return dropZoneDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DropZoneDefinition dropZoneDefinition) {

		if (dropZoneDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dropZoneDefinition.getAllowNewFragments() == null) {
			map.put("allowNewFragments", null);
		}
		else {
			map.put(
				"allowNewFragments",
				String.valueOf(dropZoneDefinition.getAllowNewFragments()));
		}

		if (dropZoneDefinition.getFragments() == null) {
			map.put("fragments", null);
		}
		else {
			map.put(
				"fragments", String.valueOf(dropZoneDefinition.getFragments()));
		}

		return map;
	}

	public static class DropZoneDefinitionJSONParser
		extends BaseJSONParser<DropZoneDefinition> {

		@Override
		protected DropZoneDefinition createDTO() {
			return new DropZoneDefinition();
		}

		@Override
		protected DropZoneDefinition[] createDTOArray(int size) {
			return new DropZoneDefinition[size];
		}

		@Override
		protected void setField(
			DropZoneDefinition dropZoneDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "allowNewFragments")) {
				if (jsonParserFieldValue != null) {
					dropZoneDefinition.setAllowNewFragments(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragments")) {
				if (jsonParserFieldValue != null) {
					dropZoneDefinition.setFragments(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FragmentSerDes.toDTO((String)object)
						).toArray(
							size -> new Fragment[size]
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