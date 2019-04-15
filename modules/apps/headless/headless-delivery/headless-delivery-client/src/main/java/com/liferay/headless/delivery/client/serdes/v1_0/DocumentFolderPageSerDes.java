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

import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolderPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class DocumentFolderPageSerDes {

	public static DocumentFolderPage toDTO(String json) {
		DocumentFolderPageJSONParser documentFolderPageJSONParser =
			new DocumentFolderPageJSONParser();

		return documentFolderPageJSONParser.parseToDTO(json);
	}

	public static DocumentFolderPage[] toDTOs(String json) {
		DocumentFolderPageJSONParser documentFolderPageJSONParser =
			new DocumentFolderPageJSONParser();

		return documentFolderPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DocumentFolderPage documentFolderPage) {
		if (documentFolderPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (documentFolderPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < documentFolderPage.getItems().length; i++) {
				sb.append(
					DocumentFolderSerDes.toJSON(
						documentFolderPage.getItems()[i]));

				if ((i + 1) < documentFolderPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (documentFolderPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentFolderPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (documentFolderPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentFolderPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (documentFolderPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentFolderPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (documentFolderPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentFolderPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class DocumentFolderPageJSONParser
		extends BaseJSONParser<DocumentFolderPage> {

		protected DocumentFolderPage createDTO() {
			return new DocumentFolderPage();
		}

		protected DocumentFolderPage[] createDTOArray(int size) {
			return new DocumentFolderPage[size];
		}

		protected void setField(
			DocumentFolderPage documentFolderPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					documentFolderPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DocumentFolderSerDes.toDTO((String)object)
						).toArray(
							size -> new DocumentFolder[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					documentFolderPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					documentFolderPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					documentFolderPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					documentFolderPage.setTotalCount(
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