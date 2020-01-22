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

import com.liferay.headless.form.client.dto.v1_0.Validation;
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
public class ValidationSerDes {

	public static Validation toDTO(String json) {
		ValidationJSONParser validationJSONParser = new ValidationJSONParser();

		return validationJSONParser.parseToDTO(json);
	}

	public static Validation[] toDTOs(String json) {
		ValidationJSONParser validationJSONParser = new ValidationJSONParser();

		return validationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Validation validation) {
		if (validation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (validation.getErrorMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessage\": ");

			sb.append("\"");

			sb.append(_escape(validation.getErrorMessage()));

			sb.append("\"");
		}

		if (validation.getErrorMessage_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessage_i18n\": ");

			sb.append(_toJSON(validation.getErrorMessage_i18n()));
		}

		if (validation.getExpression() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expression\": ");

			sb.append("\"");

			sb.append(_escape(validation.getExpression()));

			sb.append("\"");
		}

		if (validation.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(validation.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ValidationJSONParser validationJSONParser = new ValidationJSONParser();

		return validationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Validation validation) {
		if (validation == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (validation.getErrorMessage() == null) {
			map.put("errorMessage", null);
		}
		else {
			map.put(
				"errorMessage", String.valueOf(validation.getErrorMessage()));
		}

		if (validation.getErrorMessage_i18n() == null) {
			map.put("errorMessage_i18n", null);
		}
		else {
			map.put(
				"errorMessage_i18n",
				String.valueOf(validation.getErrorMessage_i18n()));
		}

		if (validation.getExpression() == null) {
			map.put("expression", null);
		}
		else {
			map.put("expression", String.valueOf(validation.getExpression()));
		}

		if (validation.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(validation.getId()));
		}

		return map;
	}

	public static class ValidationJSONParser
		extends BaseJSONParser<Validation> {

		@Override
		protected Validation createDTO() {
			return new Validation();
		}

		@Override
		protected Validation[] createDTOArray(int size) {
			return new Validation[size];
		}

		@Override
		protected void setField(
			Validation validation, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "errorMessage")) {
				if (jsonParserFieldValue != null) {
					validation.setErrorMessage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "errorMessage_i18n")) {
				if (jsonParserFieldValue != null) {
					validation.setErrorMessage_i18n(
						(Map)ValidationSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expression")) {
				if (jsonParserFieldValue != null) {
					validation.setExpression((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					validation.setId(
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