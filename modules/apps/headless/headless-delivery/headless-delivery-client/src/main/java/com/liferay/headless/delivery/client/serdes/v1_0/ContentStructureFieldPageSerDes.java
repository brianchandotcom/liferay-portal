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

import com.liferay.headless.delivery.client.dto.v1_0.ContentStructureField;
import com.liferay.headless.delivery.client.dto.v1_0.ContentStructureFieldPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentStructureFieldPageSerDes {

	public static ContentStructureFieldPage toDTO(String json) {
		ContentStructureFieldPageJSONParser
			contentStructureFieldPageJSONParser =
				new ContentStructureFieldPageJSONParser();

		return contentStructureFieldPageJSONParser.parseToDTO(json);
	}

	public static ContentStructureFieldPage[] toDTOs(String json) {
		ContentStructureFieldPageJSONParser
			contentStructureFieldPageJSONParser =
				new ContentStructureFieldPageJSONParser();

		return contentStructureFieldPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ContentStructureFieldPage contentStructureFieldPage) {

		if (contentStructureFieldPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (contentStructureFieldPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentStructureFieldPage.getItems().length;
				 i++) {

				sb.append(
					ContentStructureFieldSerDes.toJSON(
						contentStructureFieldPage.getItems()[i]));

				if ((i + 1) < contentStructureFieldPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (contentStructureFieldPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureFieldPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (contentStructureFieldPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureFieldPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (contentStructureFieldPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureFieldPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (contentStructureFieldPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureFieldPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentStructureFieldPageJSONParser
		extends BaseJSONParser<ContentStructureFieldPage> {

		protected ContentStructureFieldPage createDTO() {
			return new ContentStructureFieldPage();
		}

		protected ContentStructureFieldPage[] createDTOArray(int size) {
			return new ContentStructureFieldPage[size];
		}

		protected void setField(
			ContentStructureFieldPage contentStructureFieldPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					contentStructureFieldPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ContentStructureFieldSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ContentStructureField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					contentStructureFieldPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					contentStructureFieldPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					contentStructureFieldPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					contentStructureFieldPage.setTotalCount(
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