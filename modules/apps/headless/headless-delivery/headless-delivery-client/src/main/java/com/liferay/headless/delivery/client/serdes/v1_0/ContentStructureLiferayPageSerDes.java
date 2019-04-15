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

import com.liferay.headless.delivery.client.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.client.dto.v1_0.ContentStructureLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentStructureLiferayPageSerDes {

	public static ContentStructureLiferayPage toDTO(String json) {
		ContentStructureLiferayPageJSONParser
			contentStructureLiferayPageJSONParser =
				new ContentStructureLiferayPageJSONParser();

		return contentStructureLiferayPageJSONParser.parseToDTO(json);
	}

	public static ContentStructureLiferayPage[] toDTOs(String json) {
		ContentStructureLiferayPageJSONParser
			contentStructureLiferayPageJSONParser =
				new ContentStructureLiferayPageJSONParser();

		return contentStructureLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ContentStructureLiferayPage contentStructureLiferayPage) {

		if (contentStructureLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (contentStructureLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentStructureLiferayPage.getItems().length;
				 i++) {

				sb.append(
					ContentStructureSerDes.toJSON(
						contentStructureLiferayPage.getItems()[i]));

				if ((i + 1) < contentStructureLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (contentStructureLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (contentStructureLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (contentStructureLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (contentStructureLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructureLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentStructureLiferayPageJSONParser
		extends BaseJSONParser<ContentStructureLiferayPage> {

		protected ContentStructureLiferayPage createDTO() {
			return new ContentStructureLiferayPage();
		}

		protected ContentStructureLiferayPage[] createDTOArray(int size) {
			return new ContentStructureLiferayPage[size];
		}

		protected void setField(
			ContentStructureLiferayPage contentStructureLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					contentStructureLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ContentStructureSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ContentStructure[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					contentStructureLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					contentStructureLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					contentStructureLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					contentStructureLiferayPage.setTotalCount(
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