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

import com.liferay.headless.delivery.client.dto.v1_0.PageTemplateDefinition;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageTemplateDefinitionSerDes {

	public static PageTemplateDefinition toDTO(String json) {
		PageTemplateDefinitionJSONParser pageTemplateDefinitionJSONParser =
			new PageTemplateDefinitionJSONParser();

		return pageTemplateDefinitionJSONParser.parseToDTO(json);
	}

	public static PageTemplateDefinition[] toDTOs(String json) {
		PageTemplateDefinitionJSONParser pageTemplateDefinitionJSONParser =
			new PageTemplateDefinitionJSONParser();

		return pageTemplateDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageTemplateDefinition pageTemplateDefinition) {
		if (pageTemplateDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (pageTemplateDefinition.getCollectionName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionName\": ");

			sb.append("\"");

			sb.append(_escape(pageTemplateDefinition.getCollectionName()));

			sb.append("\"");
		}

		if (pageTemplateDefinition.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(pageTemplateDefinition.getCreator()));
		}

		if (pageTemplateDefinition.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					pageTemplateDefinition.getDateCreated()));

			sb.append("\"");
		}

		if (pageTemplateDefinition.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					pageTemplateDefinition.getDateModified()));

			sb.append("\"");
		}

		if (pageTemplateDefinition.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(pageTemplateDefinition.getId());
		}

		if (pageTemplateDefinition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(pageTemplateDefinition.getName()));

			sb.append("\"");
		}

		if (pageTemplateDefinition.getPageDefinition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageDefinition\": ");

			sb.append(
				String.valueOf(pageTemplateDefinition.getPageDefinition()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageTemplateDefinitionJSONParser pageTemplateDefinitionJSONParser =
			new PageTemplateDefinitionJSONParser();

		return pageTemplateDefinitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PageTemplateDefinition pageTemplateDefinition) {

		if (pageTemplateDefinition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (pageTemplateDefinition.getCollectionName() == null) {
			map.put("collectionName", null);
		}
		else {
			map.put(
				"collectionName",
				String.valueOf(pageTemplateDefinition.getCollectionName()));
		}

		if (pageTemplateDefinition.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put(
				"creator", String.valueOf(pageTemplateDefinition.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				pageTemplateDefinition.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				pageTemplateDefinition.getDateModified()));

		if (pageTemplateDefinition.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(pageTemplateDefinition.getId()));
		}

		if (pageTemplateDefinition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(pageTemplateDefinition.getName()));
		}

		if (pageTemplateDefinition.getPageDefinition() == null) {
			map.put("pageDefinition", null);
		}
		else {
			map.put(
				"pageDefinition",
				String.valueOf(pageTemplateDefinition.getPageDefinition()));
		}

		return map;
	}

	public static class PageTemplateDefinitionJSONParser
		extends BaseJSONParser<PageTemplateDefinition> {

		@Override
		protected PageTemplateDefinition createDTO() {
			return new PageTemplateDefinition();
		}

		@Override
		protected PageTemplateDefinition[] createDTOArray(int size) {
			return new PageTemplateDefinition[size];
		}

		@Override
		protected void setField(
			PageTemplateDefinition pageTemplateDefinition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "collectionName")) {
				if (jsonParserFieldValue != null) {
					pageTemplateDefinition.setCollectionName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					pageTemplateDefinition.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					pageTemplateDefinition.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					pageTemplateDefinition.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					pageTemplateDefinition.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					pageTemplateDefinition.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageDefinition")) {
				if (jsonParserFieldValue != null) {
					pageTemplateDefinition.setPageDefinition(
						PageDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
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