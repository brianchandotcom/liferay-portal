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
import com.liferay.headless.delivery.client.dto.v1_0.ContentFieldPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentFieldPageSerDes {

	public static ContentFieldPage toDTO(String json) {
		ContentFieldPageJSONParser contentFieldPageJSONParser =
			new ContentFieldPageJSONParser();

		return contentFieldPageJSONParser.parseToDTO(json);
	}

	public static ContentFieldPage[] toDTOs(String json) {
		ContentFieldPageJSONParser contentFieldPageJSONParser =
			new ContentFieldPageJSONParser();

		return contentFieldPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentFieldPage contentFieldPage) {
		if (contentFieldPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (contentFieldPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentFieldPage.getItems().length; i++) {
				sb.append(
					ContentFieldSerDes.toJSON(contentFieldPage.getItems()[i]));

				if ((i + 1) < contentFieldPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (contentFieldPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentFieldPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (contentFieldPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentFieldPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (contentFieldPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentFieldPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (contentFieldPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentFieldPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentFieldPageJSONParser
		extends BaseJSONParser<ContentFieldPage> {

		protected ContentFieldPage createDTO() {
			return new ContentFieldPage();
		}

		protected ContentFieldPage[] createDTOArray(int size) {
			return new ContentFieldPage[size];
		}

		protected void setField(
			ContentFieldPage contentFieldPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					contentFieldPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ContentFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new ContentField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					contentFieldPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					contentFieldPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					contentFieldPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					contentFieldPage.setTotalCount(
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