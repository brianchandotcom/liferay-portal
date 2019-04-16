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
import com.liferay.headless.form.client.dto.v1_0.FormPage;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormPageSerDes {

	public static FormPage toDTO(String json) {
		FormPageJSONParser formPageJSONParser = new FormPageJSONParser();

		return formPageJSONParser.parseToDTO(json);
	}

	public static FormPage[] toDTOs(String json) {
		FormPageJSONParser formPageJSONParser = new FormPageJSONParser();

		return formPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormPage formPage) {
		if (formPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formPage.getFields() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"fields\":");

			sb.append("[");

			for (int i = 0; i < formPage.getFields().length; i++) {
				sb.append(FieldSerDes.toJSON(formPage.getFields()[i]));

				if ((i + 1) < formPage.getFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formPage.getHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"headline\":");

			sb.append("\"");

			sb.append(formPage.getHeadline());

			sb.append("\"");
		}

		if (formPage.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(formPage.getId());
		}

		if (formPage.getText() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"text\":");

			sb.append("\"");

			sb.append(formPage.getText());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(FormPage formPage) {
		if (formPage == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (formPage.getFields() == null) {
			map.put("fields", null);
		}
		else {
			map.put("fields", String.valueOf(formPage.getFields()));
		}

		if (formPage.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put("headline", String.valueOf(formPage.getHeadline()));
		}

		if (formPage.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formPage.getId()));
		}

		if (formPage.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put("text", String.valueOf(formPage.getText()));
		}

		return map;
	}

	private static class FormPageJSONParser extends BaseJSONParser<FormPage> {

		@Override
		protected FormPage createDTO() {
			return new FormPage();
		}

		@Override
		protected FormPage[] createDTOArray(int size) {
			return new FormPage[size];
		}

		@Override
		protected void setField(
			FormPage formPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "fields")) {
				if (jsonParserFieldValue != null) {
					formPage.setFields(
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
					formPage.setHeadline((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formPage.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					formPage.setText((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}