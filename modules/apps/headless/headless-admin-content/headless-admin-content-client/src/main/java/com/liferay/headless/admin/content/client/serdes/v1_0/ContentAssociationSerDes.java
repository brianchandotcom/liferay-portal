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

package com.liferay.headless.admin.content.client.serdes.v1_0;

import com.liferay.headless.admin.content.client.dto.v1_0.ContentAssociation;
import com.liferay.headless.admin.content.client.json.BaseJSONParser;

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
public class ContentAssociationSerDes {

	public static ContentAssociation toDTO(String json) {
		ContentAssociationJSONParser contentAssociationJSONParser =
			new ContentAssociationJSONParser();

		return contentAssociationJSONParser.parseToDTO(json);
	}

	public static ContentAssociation[] toDTOs(String json) {
		ContentAssociationJSONParser contentAssociationJSONParser =
			new ContentAssociationJSONParser();

		return contentAssociationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentAssociation contentAssociation) {
		if (contentAssociation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contentAssociation.getContentSubtype() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentSubtype\": ");

			sb.append("\"");

			sb.append(_escape(contentAssociation.getContentSubtype()));

			sb.append("\"");
		}

		if (contentAssociation.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append("\"");

			sb.append(_escape(contentAssociation.getContentType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContentAssociationJSONParser contentAssociationJSONParser =
			new ContentAssociationJSONParser();

		return contentAssociationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ContentAssociation contentAssociation) {

		if (contentAssociation == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (contentAssociation.getContentSubtype() == null) {
			map.put("contentSubtype", null);
		}
		else {
			map.put(
				"contentSubtype",
				String.valueOf(contentAssociation.getContentSubtype()));
		}

		if (contentAssociation.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put(
				"contentType",
				String.valueOf(contentAssociation.getContentType()));
		}

		return map;
	}

	public static class ContentAssociationJSONParser
		extends BaseJSONParser<ContentAssociation> {

		@Override
		protected ContentAssociation createDTO() {
			return new ContentAssociation();
		}

		@Override
		protected ContentAssociation[] createDTOArray(int size) {
			return new ContentAssociation[size];
		}

		@Override
		protected void setField(
			ContentAssociation contentAssociation, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentSubtype")) {
				if (jsonParserFieldValue != null) {
					contentAssociation.setContentSubtype(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					contentAssociation.setContentType(
						(String)jsonParserFieldValue);
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