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
import com.liferay.headless.delivery.client.dto.v1_0.ContentDocumentLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentDocumentLiferayPageSerDes {

	public static ContentDocumentLiferayPage toDTO(String json) {
		ContentDocumentLiferayPageJSONParser
			contentDocumentLiferayPageJSONParser =
				new ContentDocumentLiferayPageJSONParser();

		return contentDocumentLiferayPageJSONParser.parseToDTO(json);
	}

	public static ContentDocumentLiferayPage[] toDTOs(String json) {
		ContentDocumentLiferayPageJSONParser
			contentDocumentLiferayPageJSONParser =
				new ContentDocumentLiferayPageJSONParser();

		return contentDocumentLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ContentDocumentLiferayPage contentDocumentLiferayPage) {

		if (contentDocumentLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (contentDocumentLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentDocumentLiferayPage.getItems().length;
				 i++) {

				sb.append(
					ContentDocumentSerDes.toJSON(
						contentDocumentLiferayPage.getItems()[i]));

				if ((i + 1) < contentDocumentLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (contentDocumentLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocumentLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (contentDocumentLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocumentLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (contentDocumentLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocumentLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (contentDocumentLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocumentLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentDocumentLiferayPageJSONParser
		extends BaseJSONParser<ContentDocumentLiferayPage> {

		protected ContentDocumentLiferayPage createDTO() {
			return new ContentDocumentLiferayPage();
		}

		protected ContentDocumentLiferayPage[] createDTOArray(int size) {
			return new ContentDocumentLiferayPage[size];
		}

		protected void setField(
			ContentDocumentLiferayPage contentDocumentLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					contentDocumentLiferayPage.setItems(
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
					contentDocumentLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					contentDocumentLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					contentDocumentLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					contentDocumentLiferayPage.setTotalCount(
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