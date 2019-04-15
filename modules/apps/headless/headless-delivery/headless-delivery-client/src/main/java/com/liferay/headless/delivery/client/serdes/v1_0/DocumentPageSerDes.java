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

import com.liferay.headless.delivery.client.dto.v1_0.Document;
import com.liferay.headless.delivery.client.dto.v1_0.DocumentPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class DocumentPageSerDes {

	public static DocumentPage toDTO(String json) {
		DocumentPageJSONParser documentPageJSONParser =
			new DocumentPageJSONParser();

		return documentPageJSONParser.parseToDTO(json);
	}

	public static DocumentPage[] toDTOs(String json) {
		DocumentPageJSONParser documentPageJSONParser =
			new DocumentPageJSONParser();

		return documentPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DocumentPage documentPage) {
		if (documentPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (documentPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < documentPage.getItems().length; i++) {
				sb.append(DocumentSerDes.toJSON(documentPage.getItems()[i]));

				if ((i + 1) < documentPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (documentPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (documentPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (documentPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (documentPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class DocumentPageJSONParser
		extends BaseJSONParser<DocumentPage> {

		protected DocumentPage createDTO() {
			return new DocumentPage();
		}

		protected DocumentPage[] createDTOArray(int size) {
			return new DocumentPage[size];
		}

		protected void setField(
			DocumentPage documentPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					documentPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DocumentSerDes.toDTO((String)object)
						).toArray(
							size -> new Document[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					documentPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					documentPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					documentPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					documentPage.setTotalCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}