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

package com.liferay.data.engine.rest.client.serdes.v2_0;

import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinitionFieldLink;
import com.liferay.data.engine.rest.client.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.client.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionFieldLinkSerDes {

	public static DataDefinitionFieldLink toDTO(String json) {
		DataDefinitionFieldLinkJSONParser dataDefinitionFieldLinkJSONParser =
			new DataDefinitionFieldLinkJSONParser();

		return dataDefinitionFieldLinkJSONParser.parseToDTO(json);
	}

	public static DataDefinitionFieldLink[] toDTOs(String json) {
		DataDefinitionFieldLinkJSONParser dataDefinitionFieldLinkJSONParser =
			new DataDefinitionFieldLinkJSONParser();

		return dataDefinitionFieldLinkJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		DataDefinitionFieldLink dataDefinitionFieldLink) {

		if (dataDefinitionFieldLink == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataDefinitionFieldLink.getDataDefinition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinition\": ");

			sb.append(
				String.valueOf(dataDefinitionFieldLink.getDataDefinition()));
		}

		if (dataDefinitionFieldLink.getDataLayouts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayouts\": ");

			sb.append("[");

			for (int i = 0; i < dataDefinitionFieldLink.getDataLayouts().length;
				 i++) {

				sb.append(
					String.valueOf(
						dataDefinitionFieldLink.getDataLayouts()[i]));

				if ((i + 1) < dataDefinitionFieldLink.getDataLayouts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataDefinitionFieldLink.getDataListViews() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataListViews\": ");

			sb.append("[");

			for (int i = 0;
				 i < dataDefinitionFieldLink.getDataListViews().length; i++) {

				sb.append(
					String.valueOf(
						dataDefinitionFieldLink.getDataListViews()[i]));

				if ((i + 1) <
						dataDefinitionFieldLink.getDataListViews().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataDefinitionFieldLinkJSONParser dataDefinitionFieldLinkJSONParser =
			new DataDefinitionFieldLinkJSONParser();

		return dataDefinitionFieldLinkJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DataDefinitionFieldLink dataDefinitionFieldLink) {

		if (dataDefinitionFieldLink == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataDefinitionFieldLink.getDataDefinition() == null) {
			map.put("dataDefinition", null);
		}
		else {
			map.put(
				"dataDefinition",
				String.valueOf(dataDefinitionFieldLink.getDataDefinition()));
		}

		if (dataDefinitionFieldLink.getDataLayouts() == null) {
			map.put("dataLayouts", null);
		}
		else {
			map.put(
				"dataLayouts",
				String.valueOf(dataDefinitionFieldLink.getDataLayouts()));
		}

		if (dataDefinitionFieldLink.getDataListViews() == null) {
			map.put("dataListViews", null);
		}
		else {
			map.put(
				"dataListViews",
				String.valueOf(dataDefinitionFieldLink.getDataListViews()));
		}

		return map;
	}

	public static class DataDefinitionFieldLinkJSONParser
		extends BaseJSONParser<DataDefinitionFieldLink> {

		@Override
		protected DataDefinitionFieldLink createDTO() {
			return new DataDefinitionFieldLink();
		}

		@Override
		protected DataDefinitionFieldLink[] createDTOArray(int size) {
			return new DataDefinitionFieldLink[size];
		}

		@Override
		protected void setField(
			DataDefinitionFieldLink dataDefinitionFieldLink,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataDefinition")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionFieldLink.setDataDefinition(
						DataDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataLayouts")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionFieldLink.setDataLayouts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DataLayoutSerDes.toDTO((String)object)
						).toArray(
							size -> new DataLayout[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataListViews")) {
				if (jsonParserFieldValue != null) {
					dataDefinitionFieldLink.setDataListViews(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DataListViewSerDes.toDTO((String)object)
						).toArray(
							size -> new DataListView[size]
						));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}