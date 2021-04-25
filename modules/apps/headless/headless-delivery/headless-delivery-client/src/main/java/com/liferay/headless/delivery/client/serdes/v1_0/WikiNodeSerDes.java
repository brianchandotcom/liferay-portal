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

import com.liferay.headless.delivery.client.dto.v1_0.WikiNode;
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
public class WikiNodeSerDes {

	public static WikiNode toDTO(String json) {
		WikiNodeJSONParser wikiNodeJSONParser = new WikiNodeJSONParser();

		return wikiNodeJSONParser.parseToDTO(json);
	}

	public static WikiNode[] toDTOs(String json) {
		WikiNodeJSONParser wikiNodeJSONParser = new WikiNodeJSONParser();

		return wikiNodeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WikiNode wikiNode) {
		if (wikiNode == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (wikiNode.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(wikiNode.getActions()));
		}

		if (wikiNode.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(wikiNode.getCreator()));
		}

		if (wikiNode.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(wikiNode.getDateCreated()));

			sb.append("\"");
		}

		if (wikiNode.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(wikiNode.getDateModified()));

			sb.append("\"");
		}

		if (wikiNode.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(wikiNode.getDescription()));

			sb.append("\"");
		}

		if (wikiNode.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(wikiNode.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (wikiNode.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(wikiNode.getId());
		}

		if (wikiNode.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(wikiNode.getName()));

			sb.append("\"");
		}

		if (wikiNode.getNumberOfWikiPages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfWikiPages\": ");

			sb.append(wikiNode.getNumberOfWikiPages());
		}

		if (wikiNode.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(wikiNode.getSiteId());
		}

		if (wikiNode.getSubscribed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscribed\": ");

			sb.append(wikiNode.getSubscribed());
		}

		if (wikiNode.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\": ");

			sb.append("\"");

			sb.append(wikiNode.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WikiNodeJSONParser wikiNodeJSONParser = new WikiNodeJSONParser();

		return wikiNodeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(WikiNode wikiNode) {
		if (wikiNode == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (wikiNode.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(wikiNode.getActions()));
		}

		if (wikiNode.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(wikiNode.getCreator()));
		}

		if (wikiNode.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(wikiNode.getDateCreated()));
		}

		if (wikiNode.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(wikiNode.getDateModified()));
		}

		if (wikiNode.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(wikiNode.getDescription()));
		}

		if (wikiNode.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(wikiNode.getExternalReferenceCode()));
		}

		if (wikiNode.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(wikiNode.getId()));
		}

		if (wikiNode.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(wikiNode.getName()));
		}

		if (wikiNode.getNumberOfWikiPages() == null) {
			map.put("numberOfWikiPages", null);
		}
		else {
			map.put(
				"numberOfWikiPages",
				String.valueOf(wikiNode.getNumberOfWikiPages()));
		}

		if (wikiNode.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(wikiNode.getSiteId()));
		}

		if (wikiNode.getSubscribed() == null) {
			map.put("subscribed", null);
		}
		else {
			map.put("subscribed", String.valueOf(wikiNode.getSubscribed()));
		}

		if (wikiNode.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put("viewableBy", String.valueOf(wikiNode.getViewableBy()));
		}

		return map;
	}

	public static class WikiNodeJSONParser extends BaseJSONParser<WikiNode> {

		@Override
		protected WikiNode createDTO() {
			return new WikiNode();
		}

		@Override
		protected WikiNode[] createDTOArray(int size) {
			return new WikiNode[size];
		}

		@Override
		protected void setField(
			WikiNode wikiNode, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setActions(
						(Map)WikiNodeSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					wikiNode.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfWikiPages")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setNumberOfWikiPages(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subscribed")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setSubscribed((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					wikiNode.setViewableBy(
						WikiNode.ViewableBy.create(
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