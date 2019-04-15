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

		sb.append("\"dataType\": ");

		if (contentField.getDataType() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentField.getDataType());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"inputControl\": ");

		if (contentField.getInputControl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentField.getInputControl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"label\": ");

		if (contentField.getLabel() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentField.getLabel());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (contentField.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentField.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"nestedFields\": ");

		if (contentField.getNestedFields() == null) {
			sb.append("null");
		}
		else {
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

		sb.append(", ");

		sb.append("\"repeatable\": ");

		if (contentField.getRepeatable() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentField.getRepeatable());
		}

		sb.append(", ");

		sb.append("\"value\": ");

		sb.append(ValueSerDes.toJSON(contentField.getValue()));

		sb.append("}");

		return sb.toString();
	}

	private static class ContentFieldJSONParser
		extends BaseJSONParser<ContentField> {

		protected ContentField createDTO() {
			return new ContentField();
		}

		protected ContentField[] createDTOArray(int size) {
			return new ContentField[size];
		}

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