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

import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolderPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class StructuredContentFolderPageSerDes {

	public static StructuredContentFolderPage toDTO(String json) {
		StructuredContentFolderPageJSONParser
			structuredContentFolderPageJSONParser =
				new StructuredContentFolderPageJSONParser();

		return structuredContentFolderPageJSONParser.parseToDTO(json);
	}

	public static StructuredContentFolderPage[] toDTOs(String json) {
		StructuredContentFolderPageJSONParser
			structuredContentFolderPageJSONParser =
				new StructuredContentFolderPageJSONParser();

		return structuredContentFolderPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		StructuredContentFolderPage structuredContentFolderPage) {

		if (structuredContentFolderPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (structuredContentFolderPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < structuredContentFolderPage.getItems().length;
				 i++) {

				sb.append(
					StructuredContentFolderSerDes.toJSON(
						structuredContentFolderPage.getItems()[i]));

				if ((i + 1) < structuredContentFolderPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (structuredContentFolderPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentFolderPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (structuredContentFolderPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentFolderPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (structuredContentFolderPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentFolderPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (structuredContentFolderPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentFolderPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class StructuredContentFolderPageJSONParser
		extends BaseJSONParser<StructuredContentFolderPage> {

		protected StructuredContentFolderPage createDTO() {
			return new StructuredContentFolderPage();
		}

		protected StructuredContentFolderPage[] createDTOArray(int size) {
			return new StructuredContentFolderPage[size];
		}

		protected void setField(
			StructuredContentFolderPage structuredContentFolderPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolderPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> StructuredContentFolderSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new StructuredContentFolder[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolderPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolderPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolderPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolderPage.setTotalCount(
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