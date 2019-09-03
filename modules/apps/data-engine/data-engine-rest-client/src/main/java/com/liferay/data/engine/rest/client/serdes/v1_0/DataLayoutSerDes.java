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

package com.liferay.data.engine.rest.client.serdes.v1_0;

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutPage;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class DataLayoutSerDes {

	public static DataLayout toDTO(String json) {
		DataLayoutJSONParser dataLayoutJSONParser = new DataLayoutJSONParser();

		return dataLayoutJSONParser.parseToDTO(json);
	}

	public static DataLayout[] toDTOs(String json) {
		DataLayoutJSONParser dataLayoutJSONParser = new DataLayoutJSONParser();

		return dataLayoutJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataLayout dataLayout) {
		if (dataLayout == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (dataLayout.getDataDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionId\": ");

			sb.append(dataLayout.getDataDefinitionId());
		}

		if (dataLayout.getDataLayoutKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayoutKey\": ");

			sb.append("\"");

			sb.append(_escape(dataLayout.getDataLayoutKey()));

			sb.append("\"");
		}

		if (dataLayout.getDataLayoutPages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayoutPages\": ");

			sb.append("[");

			for (int i = 0; i < dataLayout.getDataLayoutPages().length; i++) {
				sb.append(String.valueOf(dataLayout.getDataLayoutPages()[i]));

				if ((i + 1) < dataLayout.getDataLayoutPages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataLayout.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(dataLayout.getDateCreated()));

			sb.append("\"");
		}

		if (dataLayout.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(dataLayout.getDateModified()));

			sb.append("\"");
		}

		if (dataLayout.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append(_toJSON(dataLayout.getDescription()));
		}

		if (dataLayout.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(dataLayout.getId());
		}

		if (dataLayout.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(dataLayout.getName()));
		}

		if (dataLayout.getPaginationMode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paginationMode\": ");

			sb.append("\"");

			sb.append(_escape(dataLayout.getPaginationMode()));

			sb.append("\"");
		}

		if (dataLayout.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(dataLayout.getSiteId());
		}

		if (dataLayout.getUserId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(dataLayout.getUserId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataLayoutJSONParser dataLayoutJSONParser = new DataLayoutJSONParser();

		return dataLayoutJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DataLayout dataLayout) {
		if (dataLayout == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (dataLayout.getDataDefinitionId() == null) {
			map.put("dataDefinitionId", null);
		}
		else {
			map.put(
				"dataDefinitionId",
				String.valueOf(dataLayout.getDataDefinitionId()));
		}

		if (dataLayout.getDataLayoutKey() == null) {
			map.put("dataLayoutKey", null);
		}
		else {
			map.put(
				"dataLayoutKey", String.valueOf(dataLayout.getDataLayoutKey()));
		}

		if (dataLayout.getDataLayoutPages() == null) {
			map.put("dataLayoutPages", null);
		}
		else {
			map.put(
				"dataLayoutPages",
				String.valueOf(dataLayout.getDataLayoutPages()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(dataLayout.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(dataLayout.getDateModified()));

		if (dataLayout.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(dataLayout.getDescription()));
		}

		if (dataLayout.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(dataLayout.getId()));
		}

		if (dataLayout.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(dataLayout.getName()));
		}

		if (dataLayout.getPaginationMode() == null) {
			map.put("paginationMode", null);
		}
		else {
			map.put(
				"paginationMode",
				String.valueOf(dataLayout.getPaginationMode()));
		}

		if (dataLayout.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(dataLayout.getSiteId()));
		}

		if (dataLayout.getUserId() == null) {
			map.put("userId", null);
		}
		else {
			map.put("userId", String.valueOf(dataLayout.getUserId()));
		}

		return map;
	}

	public static class DataLayoutJSONParser
		extends BaseJSONParser<DataLayout> {

		@Override
		protected DataLayout createDTO() {
			return new DataLayout();
		}

		@Override
		protected DataLayout[] createDTOArray(int size) {
			return new DataLayout[size];
		}

		@Override
		protected void setField(
			DataLayout dataLayout, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataDefinitionId")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setDataDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataLayoutKey")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setDataLayoutKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataLayoutPages")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setDataLayoutPages(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DataLayoutPageSerDes.toDTO((String)object)
						).toArray(
							size -> new DataLayoutPage[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setDescription(
						(Map)DataLayoutSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setName(
						(Map)DataLayoutSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paginationMode")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setPaginationMode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userId")) {
				if (jsonParserFieldValue != null) {
					dataLayout.setUserId(
						Long.valueOf((String)jsonParserFieldValue));
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

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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