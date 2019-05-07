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

import com.liferay.headless.form.client.dto.v1_0.Field;
import com.liferay.headless.form.client.dto.v1_0.FormLayoutPage;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormLayoutPageSerDes {

	public static FormLayoutPage toDTO(String json) {
		FormLayoutPageJSONParser formLayoutPageJSONParser =
			new FormLayoutPageJSONParser();

		return formLayoutPageJSONParser.parseToDTO(json);
	}

	public static FormLayoutPage[] toDTOs(String json) {
		FormLayoutPageJSONParser formLayoutPageJSONParser =
			new FormLayoutPageJSONParser();

		return formLayoutPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormLayoutPage formLayoutPage) {
		if (formLayoutPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formLayoutPage.getFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fields\": ");

			sb.append("[");

			for (int i = 0; i < formLayoutPage.getFields().length; i++) {
				sb.append(String.valueOf(formLayoutPage.getFields()[i]));

				if ((i + 1) < formLayoutPage.getFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formLayoutPage.getHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline\": ");

			sb.append("\"");

			sb.append(_escape(formLayoutPage.getHeadline()));

			sb.append("\"");
		}

		if (formLayoutPage.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(formLayoutPage.getId());
		}

		if (formLayoutPage.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			sb.append("\"");

			sb.append(_escape(formLayoutPage.getText()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormLayoutPageJSONParser formLayoutPageJSONParser =
			new FormLayoutPageJSONParser();

		return formLayoutPageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormLayoutPage formLayoutPage) {
		if (formLayoutPage == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (formLayoutPage.getFields() == null) {
			map.put("fields", null);
		}
		else {
			map.put("fields", String.valueOf(formLayoutPage.getFields()));
		}

		if (formLayoutPage.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put("headline", String.valueOf(formLayoutPage.getHeadline()));
		}

		if (formLayoutPage.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formLayoutPage.getId()));
		}

		if (formLayoutPage.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put("text", String.valueOf(formLayoutPage.getText()));
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

	private static class FormLayoutPageJSONParser
		extends BaseJSONParser<FormLayoutPage> {

		@Override
		protected FormLayoutPage createDTO() {
			return new FormLayoutPage();
		}

		@Override
		protected FormLayoutPage[] createDTOArray(int size) {
			return new FormLayoutPage[size];
		}

		@Override
		protected void setField(
			FormLayoutPage formLayoutPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "fields")) {
				if (jsonParserFieldValue != null) {
					formLayoutPage.setFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FieldSerDes.toDTO((String)object)
						).toArray(
							size -> new Field[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					formLayoutPage.setHeadline((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formLayoutPage.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					formLayoutPage.setText((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}