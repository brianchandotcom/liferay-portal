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

import com.liferay.headless.delivery.client.dto.v1_0.ContentDocument;
import com.liferay.headless.delivery.client.dto.v1_0.ContentDocumentPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentDocumentPageSerDes {

	public static ContentDocumentPage toDTO(String json) {
		ContentDocumentPageJSONParser contentDocumentPageJSONParser =
			new ContentDocumentPageJSONParser();

		return contentDocumentPageJSONParser.parseToDTO(json);
	}

	public static ContentDocumentPage[] toDTOs(String json) {
		ContentDocumentPageJSONParser contentDocumentPageJSONParser =
			new ContentDocumentPageJSONParser();

		return contentDocumentPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentDocumentPage contentDocumentPage) {
		if (contentDocumentPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (contentDocumentPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentDocumentPage.getItems().length; i++) {
				sb.append(
					ContentDocumentSerDes.toJSON(
						contentDocumentPage.getItems()[i]));

				if ((i + 1) < contentDocumentPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (contentDocumentPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocumentPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (contentDocumentPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocumentPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (contentDocumentPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocumentPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (contentDocumentPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocumentPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentDocumentPageJSONParser
		extends BaseJSONParser<ContentDocumentPage> {

		protected ContentDocumentPage createDTO() {
			return new ContentDocumentPage();
		}

		protected ContentDocumentPage[] createDTOArray(int size) {
			return new ContentDocumentPage[size];
		}

		protected void setField(
			ContentDocumentPage contentDocumentPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					contentDocumentPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ContentDocumentSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ContentDocument[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					contentDocumentPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					contentDocumentPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					contentDocumentPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					contentDocumentPage.setTotalCount(
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