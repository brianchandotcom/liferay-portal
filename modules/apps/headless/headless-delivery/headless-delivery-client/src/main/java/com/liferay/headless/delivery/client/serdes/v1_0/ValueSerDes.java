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

import com.liferay.headless.delivery.client.dto.v1_0.Value;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class ValueSerDes {

	public static Value toDTO(String json) {
		ValueJSONParser valueJSONParser = new ValueJSONParser();

		return valueJSONParser.parseToDTO(json);
	}

	public static Value[] toDTOs(String json) {
		ValueJSONParser valueJSONParser = new ValueJSONParser();

		return valueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Value value) {
		if (value == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (value.getData() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"data\": ");

			sb.append("\"");

			sb.append(_escape(value.getData()));

			sb.append("\"");
		}

		if (value.getDocument() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"document\": ");

			sb.append(String.valueOf(value.getDocument()));
		}

		if (value.getGeo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"geo\": ");

			sb.append(String.valueOf(value.getGeo()));
		}

		if (value.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append(String.valueOf(value.getImage()));
		}

		if (value.getLink() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"link\": ");

			sb.append("\"");

			sb.append(_escape(value.getLink()));

			sb.append("\"");
		}

		if (value.getStructuredContentLink() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"structuredContentLink\": ");

			sb.append(String.valueOf(value.getStructuredContentLink()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ValueJSONParser valueJSONParser = new ValueJSONParser();

		return valueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Value value) {
		if (value == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (value.getData() == null) {
			map.put("data", null);
		}
		else {
			map.put("data", String.valueOf(value.getData()));
		}

		if (value.getDocument() == null) {
			map.put("document", null);
		}
		else {
			map.put("document", String.valueOf(value.getDocument()));
		}

		if (value.getGeo() == null) {
			map.put("geo", null);
		}
		else {
			map.put("geo", String.valueOf(value.getGeo()));
		}

		if (value.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(value.getImage()));
		}

		if (value.getLink() == null) {
			map.put("link", null);
		}
		else {
			map.put("link", String.valueOf(value.getLink()));
		}

		if (value.getStructuredContentLink() == null) {
			map.put("structuredContentLink", null);
		}
		else {
			map.put(
				"structuredContentLink",
				String.valueOf(value.getStructuredContentLink()));
		}

		return map;
	}

	public static class ValueJSONParser extends BaseJSONParser<Value> {

		@Override
		protected Value createDTO() {
			return new Value();
		}

		@Override
		protected Value[] createDTOArray(int size) {
			return new Value[size];
		}

		@Override
		protected void setField(
			Value value, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "data")) {
				if (jsonParserFieldValue != null) {
					value.setData((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "document")) {
				if (jsonParserFieldValue != null) {
					value.setDocument(
						ContentDocumentSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "geo")) {
				if (jsonParserFieldValue != null) {
					value.setGeo(GeoSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					value.setImage(
						ContentDocumentSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "link")) {
				if (jsonParserFieldValue != null) {
					value.setLink((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "structuredContentLink")) {

				if (jsonParserFieldValue != null) {
					value.setStructuredContentLink(
						StructuredContentLinkSerDes.toDTO(
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