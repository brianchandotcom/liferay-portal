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

package com.liferay.change.tracking.rest.client.serdes.v1_0;

import com.liferay.change.tracking.rest.client.dto.v1_0.Publication;
import com.liferay.change.tracking.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author David Truong
 * @generated
 */
@Generated("")
public class PublicationSerDes {

	public static Publication toDTO(String json) {
		PublicationJSONParser publicationJSONParser =
			new PublicationJSONParser();

		return publicationJSONParser.parseToDTO(json);
	}

	public static Publication[] toDTOs(String json) {
		PublicationJSONParser publicationJSONParser =
			new PublicationJSONParser();

		return publicationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Publication publication) {
		if (publication == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (publication.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(publication.getActions()));
		}

		if (publication.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(publication.getDateCreated()));

			sb.append("\"");
		}

		if (publication.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(publication.getDateModified()));

			sb.append("\"");
		}

		if (publication.getDateScheduled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateScheduled\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(publication.getDateScheduled()));

			sb.append("\"");
		}

		if (publication.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(publication.getDescription()));

			sb.append("\"");
		}

		if (publication.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(publication.getId());
		}

		if (publication.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(publication.getName()));

			sb.append("\"");
		}

		if (publication.getOwnerName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ownerName\": ");

			sb.append("\"");

			sb.append(_escape(publication.getOwnerName()));

			sb.append("\"");
		}

		if (publication.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(String.valueOf(publication.getStatus()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PublicationJSONParser publicationJSONParser =
			new PublicationJSONParser();

		return publicationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Publication publication) {
		if (publication == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (publication.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(publication.getActions()));
		}

		if (publication.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(publication.getDateCreated()));
		}

		if (publication.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(publication.getDateModified()));
		}

		if (publication.getDateScheduled() == null) {
			map.put("dateScheduled", null);
		}
		else {
			map.put(
				"dateScheduled",
				liferayToJSONDateFormat.format(publication.getDateScheduled()));
		}

		if (publication.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(publication.getDescription()));
		}

		if (publication.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(publication.getId()));
		}

		if (publication.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(publication.getName()));
		}

		if (publication.getOwnerName() == null) {
			map.put("ownerName", null);
		}
		else {
			map.put("ownerName", String.valueOf(publication.getOwnerName()));
		}

		if (publication.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(publication.getStatus()));
		}

		return map;
	}

	public static class PublicationJSONParser
		extends BaseJSONParser<Publication> {

		@Override
		protected Publication createDTO() {
			return new Publication();
		}

		@Override
		protected Publication[] createDTOArray(int size) {
			return new Publication[size];
		}

		@Override
		protected void setField(
			Publication publication, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					publication.setActions(
						(Map)PublicationSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					publication.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					publication.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateScheduled")) {
				if (jsonParserFieldValue != null) {
					publication.setDateScheduled(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					publication.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					publication.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					publication.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ownerName")) {
				if (jsonParserFieldValue != null) {
					publication.setOwnerName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					publication.setStatus(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
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