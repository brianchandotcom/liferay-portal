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

import java.util.Objects;

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

		sb.append("\"data\": ");

		if (value.getData() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(value.getData());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"document\": ");

		sb.append(ContentDocumentSerDes.toJSON(value.getDocument()));
		sb.append(", ");

		sb.append("\"geo\": ");

		sb.append(GeoSerDes.toJSON(value.getGeo()));
		sb.append(", ");

		sb.append("\"image\": ");

		sb.append(ContentDocumentSerDes.toJSON(value.getImage()));
		sb.append(", ");

		sb.append("\"link\": ");

		if (value.getLink() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(value.getLink());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"structuredContentLink\": ");

		sb.append(
			StructuredContentLinkSerDes.toJSON(
				value.getStructuredContentLink()));

		sb.append("}");

		return sb.toString();
	}

	private static class ValueJSONParser extends BaseJSONParser<Value> {

		protected Value createDTO() {
			return new Value();
		}

		protected Value[] createDTOArray(int size) {
			return new Value[size];
		}

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

}