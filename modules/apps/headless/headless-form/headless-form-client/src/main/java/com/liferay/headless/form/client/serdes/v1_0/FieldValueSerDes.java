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

package com.liferay.headless.form.client.serdes.v1_0;

import com.liferay.headless.form.client.dto.v1_0.FieldValue;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FieldValueSerDes {

	public static FieldValue toDTO(String json) {
		FieldValueJSONParser fieldValueJSONParser = new FieldValueJSONParser();

		return fieldValueJSONParser.parseToDTO(json);
	}

	public static FieldValue[] toDTOs(String json) {
		FieldValueJSONParser fieldValueJSONParser = new FieldValueJSONParser();

		return fieldValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FieldValue fieldValue) {
		if (fieldValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fieldValue.getFormDocument() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formDocument\": ");

			sb.append(String.valueOf(fieldValue.getFormDocument()));
		}

		if (fieldValue.getFormDocumentId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formDocumentId\": ");

			sb.append(fieldValue.getFormDocumentId());
		}

		if (fieldValue.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(fieldValue.getId());
		}

		if (fieldValue.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(fieldValue.getName()));

			sb.append("\"");
		}

		if (fieldValue.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(fieldValue.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FieldValueJSONParser fieldValueJSONParser = new FieldValueJSONParser();

		return fieldValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FieldValue fieldValue) {
		if (fieldValue == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (fieldValue.getFormDocument() == null) {
			map.put("formDocument", null);
		}
		else {
			map.put(
				"formDocument", String.valueOf(fieldValue.getFormDocument()));
		}

		if (fieldValue.getFormDocumentId() == null) {
			map.put("formDocumentId", null);
		}
		else {
			map.put(
				"formDocumentId",
				String.valueOf(fieldValue.getFormDocumentId()));
		}

		if (fieldValue.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(fieldValue.getId()));
		}

		if (fieldValue.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(fieldValue.getName()));
		}

		if (fieldValue.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(fieldValue.getValue()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class FieldValueJSONParser
		extends BaseJSONParser<FieldValue> {

		@Override
		protected FieldValue createDTO() {
			return new FieldValue();
		}

		@Override
		protected FieldValue[] createDTOArray(int size) {
			return new FieldValue[size];
		}

		@Override
		protected void setField(
			FieldValue fieldValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "formDocument")) {
				if (jsonParserFieldValue != null) {
					fieldValue.setFormDocument(
						FormDocumentSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formDocumentId")) {
				if (jsonParserFieldValue != null) {
					fieldValue.setFormDocumentId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					fieldValue.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					fieldValue.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					fieldValue.setValue((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}