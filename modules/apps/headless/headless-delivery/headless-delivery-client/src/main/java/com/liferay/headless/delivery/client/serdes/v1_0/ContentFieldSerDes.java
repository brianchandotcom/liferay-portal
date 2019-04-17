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

import com.liferay.headless.delivery.client.dto.v1_0.ContentField;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class ContentFieldSerDes {

	public static ContentField toDTO(String json) {
		ContentFieldJSONParser contentFieldJSONParser =
			new ContentFieldJSONParser();

		return contentFieldJSONParser.parseToDTO(json);
	}

	public static ContentField[] toDTOs(String json) {
		ContentFieldJSONParser contentFieldJSONParser =
			new ContentFieldJSONParser();

		return contentFieldJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentField contentField) {
		if (contentField == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contentField.getDataType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataType\":");

			sb.append("\"");

			sb.append(contentField.getDataType());

			sb.append("\"");
		}

		if (contentField.getInputControl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inputControl\":");

			sb.append("\"");

			sb.append(contentField.getInputControl());

			sb.append("\"");
		}

		if (contentField.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\":");

			sb.append("\"");

			sb.append(contentField.getLabel());

			sb.append("\"");
		}

		if (contentField.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(contentField.getName());

			sb.append("\"");
		}

		if (contentField.getNestedFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nestedFields\":");

			sb.append("[");

			for (int i = 0; i < contentField.getNestedFields().length; i++) {
				sb.append(
					ContentFieldSerDes.toJSON(
						contentField.getNestedFields()[i]));

				if ((i + 1) < contentField.getNestedFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contentField.getRepeatable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"repeatable\":");

			sb.append(contentField.getRepeatable());
		}

		if (contentField.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\":");

			sb.append(ValueSerDes.toJSON(contentField.getValue()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(ContentField contentField) {
		if (contentField == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (contentField.getDataType() == null) {
			map.put("dataType", null);
		}
		else {
			map.put("dataType", String.valueOf(contentField.getDataType()));
		}

		if (contentField.getInputControl() == null) {
			map.put("inputControl", null);
		}
		else {
			map.put(
				"inputControl", String.valueOf(contentField.getInputControl()));
		}

		if (contentField.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(contentField.getLabel()));
		}

		if (contentField.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(contentField.getName()));
		}

		if (contentField.getNestedFields() == null) {
			map.put("nestedFields", null);
		}
		else {
			map.put(
				"nestedFields", String.valueOf(contentField.getNestedFields()));
		}

		if (contentField.getRepeatable() == null) {
			map.put("repeatable", null);
		}
		else {
			map.put("repeatable", String.valueOf(contentField.getRepeatable()));
		}

		if (contentField.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", ValueSerDes.toJSON(contentField.getValue()));
		}

		return map;
	}

	private static class ContentFieldJSONParser
		extends BaseJSONParser<ContentField> {

		@Override
		protected ContentField createDTO() {
			return new ContentField();
		}

		@Override
		protected ContentField[] createDTOArray(int size) {
			return new ContentField[size];
		}

		@Override
		protected void setField(
			ContentField contentField, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataType")) {
				if (jsonParserFieldValue != null) {
					contentField.setDataType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inputControl")) {
				if (jsonParserFieldValue != null) {
					contentField.setInputControl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					contentField.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					contentField.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nestedFields")) {
				if (jsonParserFieldValue != null) {
					contentField.setNestedFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ContentFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new ContentField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "repeatable")) {
				if (jsonParserFieldValue != null) {
					contentField.setRepeatable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					contentField.setValue(
						ValueSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}