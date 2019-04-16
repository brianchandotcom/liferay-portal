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
import java.util.Map;
import java.util.Objects;

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

		sb.append("\"document\": ");

		sb.append(FormDocumentSerDes.toJSON(fieldValue.getDocument()));
		sb.append(", ");

		sb.append("\"documentId\": ");

		if (fieldValue.getDocumentId() == null) {
			sb.append("null");
		}
		else {
			sb.append(fieldValue.getDocumentId());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (fieldValue.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(fieldValue.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (fieldValue.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(fieldValue.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"value\": ");

		if (fieldValue.getValue() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(fieldValue.getValue());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(FieldValue fieldValue) {
		if (fieldValue == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put(
			"document", FormDocumentSerDes.toJSON(fieldValue.getDocument()));

		map.put("documentId", String.valueOf(fieldValue.getDocumentId()));

		map.put("id", String.valueOf(fieldValue.getId()));

		map.put("name", String.valueOf(fieldValue.getName()));

		map.put("value", String.valueOf(fieldValue.getValue()));

		return map;
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

			if (Objects.equals(jsonParserFieldName, "document")) {
				if (jsonParserFieldValue != null) {
					fieldValue.setDocument(
						FormDocumentSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "documentId")) {
				if (jsonParserFieldValue != null) {
					fieldValue.setDocumentId(
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