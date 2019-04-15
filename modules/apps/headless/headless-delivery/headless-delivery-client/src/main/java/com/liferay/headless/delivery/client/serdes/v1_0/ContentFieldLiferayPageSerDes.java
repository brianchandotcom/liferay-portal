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
import com.liferay.headless.delivery.client.dto.v1_0.ContentFieldLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentFieldLiferayPageSerDes {

	public static ContentFieldLiferayPage toDTO(String json) {
		ContentFieldLiferayPageJSONParser contentFieldLiferayPageJSONParser =
			new ContentFieldLiferayPageJSONParser();

		return contentFieldLiferayPageJSONParser.parseToDTO(json);
	}

	public static ContentFieldLiferayPage[] toDTOs(String json) {
		ContentFieldLiferayPageJSONParser contentFieldLiferayPageJSONParser =
			new ContentFieldLiferayPageJSONParser();

		return contentFieldLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ContentFieldLiferayPage contentFieldLiferayPage) {

		if (contentFieldLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (contentFieldLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentFieldLiferayPage.getItems().length;
				 i++) {

				sb.append(
					ContentFieldSerDes.toJSON(
						contentFieldLiferayPage.getItems()[i]));

				if ((i + 1) < contentFieldLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (contentFieldLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentFieldLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (contentFieldLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentFieldLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (contentFieldLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentFieldLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (contentFieldLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentFieldLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentFieldLiferayPageJSONParser
		extends BaseJSONParser<ContentFieldLiferayPage> {

		protected ContentFieldLiferayPage createDTO() {
			return new ContentFieldLiferayPage();
		}

		protected ContentFieldLiferayPage[] createDTOArray(int size) {
			return new ContentFieldLiferayPage[size];
		}

		protected void setField(
			ContentFieldLiferayPage contentFieldLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					contentFieldLiferayPage.setItems(
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
					contentFieldLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					contentFieldLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					contentFieldLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					contentFieldLiferayPage.setTotalCount(
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