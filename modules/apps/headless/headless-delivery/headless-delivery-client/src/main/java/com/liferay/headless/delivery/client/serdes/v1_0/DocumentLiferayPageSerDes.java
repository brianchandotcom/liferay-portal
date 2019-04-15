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
import com.liferay.headless.delivery.client.dto.v1_0.DocumentLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class DocumentLiferayPageSerDes {

	public static DocumentLiferayPage toDTO(String json) {
		DocumentLiferayPageJSONParser documentLiferayPageJSONParser =
			new DocumentLiferayPageJSONParser();

		return documentLiferayPageJSONParser.parseToDTO(json);
	}

	public static DocumentLiferayPage[] toDTOs(String json) {
		DocumentLiferayPageJSONParser documentLiferayPageJSONParser =
			new DocumentLiferayPageJSONParser();

		return documentLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DocumentLiferayPage documentLiferayPage) {
		if (documentLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (documentLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < documentLiferayPage.getItems().length; i++) {
				sb.append(
					DocumentSerDes.toJSON(documentLiferayPage.getItems()[i]));

				if ((i + 1) < documentLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (documentLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (documentLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (documentLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (documentLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class DocumentLiferayPageJSONParser
		extends BaseJSONParser<DocumentLiferayPage> {

		protected DocumentLiferayPage createDTO() {
			return new DocumentLiferayPage();
		}

		protected DocumentLiferayPage[] createDTOArray(int size) {
			return new DocumentLiferayPage[size];
		}

		protected void setField(
			DocumentLiferayPage documentLiferayPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					documentLiferayPage.setItems(
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
					documentLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					documentLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					documentLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					documentLiferayPage.setTotalCount(
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