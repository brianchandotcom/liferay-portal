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
import com.liferay.headless.delivery.client.dto.v1_0.ContentStructurePage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentStructurePageSerDes {

	public static ContentStructurePage toDTO(String json) {
		ContentStructurePageJSONParser contentStructurePageJSONParser =
			new ContentStructurePageJSONParser();

		return contentStructurePageJSONParser.parseToDTO(json);
	}

	public static ContentStructurePage[] toDTOs(String json) {
		ContentStructurePageJSONParser contentStructurePageJSONParser =
			new ContentStructurePageJSONParser();

		return contentStructurePageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentStructurePage contentStructurePage) {
		if (contentStructurePage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (contentStructurePage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentStructurePage.getItems().length; i++) {
				sb.append(
					ContentStructureSerDes.toJSON(
						contentStructurePage.getItems()[i]));

				if ((i + 1) < contentStructurePage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (contentStructurePage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructurePage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (contentStructurePage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructurePage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (contentStructurePage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructurePage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (contentStructurePage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructurePage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentStructurePageJSONParser
		extends BaseJSONParser<ContentStructurePage> {

		protected ContentStructurePage createDTO() {
			return new ContentStructurePage();
		}

		protected ContentStructurePage[] createDTOArray(int size) {
			return new ContentStructurePage[size];
		}

		protected void setField(
			ContentStructurePage contentStructurePage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					contentStructurePage.setItems(
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
					contentStructurePage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					contentStructurePage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					contentStructurePage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					contentStructurePage.setTotalCount(
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