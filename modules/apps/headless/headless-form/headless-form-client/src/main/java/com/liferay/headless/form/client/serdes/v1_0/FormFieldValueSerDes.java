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

import com.liferay.headless.form.client.dto.v1_0.FormFieldValue;
import com.liferay.headless.form.client.json.BaseJSONParser;

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
public class FormFieldValueSerDes {

	public static FormFieldValue toDTO(String json) {
		FormFieldValueJSONParser formFieldValueJSONParser =
			new FormFieldValueJSONParser();

		return formFieldValueJSONParser.parseToDTO(json);
	}

	public static FormFieldValue[] toDTOs(String json) {
		FormFieldValueJSONParser formFieldValueJSONParser =
			new FormFieldValueJSONParser();

		return formFieldValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormFieldValue formFieldValue) {
		if (formFieldValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formFieldValue.getFormDocument() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formDocument\": ");

			sb.append(String.valueOf(formFieldValue.getFormDocument()));
		}

		if (formFieldValue.getFormDocumentId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formDocumentId\": ");

			sb.append(formFieldValue.getFormDocumentId());
		}

		if (formFieldValue.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(formFieldValue.getId());
		}

		if (formFieldValue.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(formFieldValue.getName()));

			sb.append("\"");
		}

		if (formFieldValue.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(formFieldValue.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormFieldValueJSONParser formFieldValueJSONParser =
			new FormFieldValueJSONParser();

		return formFieldValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormFieldValue formFieldValue) {
		if (formFieldValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (formFieldValue.getFormDocument() == null) {
			map.put("formDocument", null);
		}
		else {
			map.put(
				"formDocument",
				String.valueOf(formFieldValue.getFormDocument()));
		}

		if (formFieldValue.getFormDocumentId() == null) {
			map.put("formDocumentId", null);
		}
		else {
			map.put(
				"formDocumentId",
				String.valueOf(formFieldValue.getFormDocumentId()));
		}

		if (formFieldValue.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formFieldValue.getId()));
		}

		if (formFieldValue.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(formFieldValue.getName()));
		}

		if (formFieldValue.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(formFieldValue.getValue()));
		}

		return map;
	}

	public static class FormFieldValueJSONParser
		extends BaseJSONParser<FormFieldValue> {

		@Override
		protected FormFieldValue createDTO() {
			return new FormFieldValue();
		}

		@Override
		protected FormFieldValue[] createDTOArray(int size) {
			return new FormFieldValue[size];
		}

		@Override
		protected void setField(
			FormFieldValue formFieldValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "formDocument")) {
				if (jsonParserFieldValue != null) {
					formFieldValue.setFormDocument(
						FormDocumentSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formDocumentId")) {
				if (jsonParserFieldValue != null) {
					formFieldValue.setFormDocumentId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formFieldValue.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					formFieldValue.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					formFieldValue.setValue((String)jsonParserFieldValue);
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