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

import com.liferay.headless.delivery.client.dto.v1_0.ParentKnowledgeBaseFolder;
import com.liferay.headless.delivery.client.dto.v1_0.ParentKnowledgeBaseFolderPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ParentKnowledgeBaseFolderPageSerDes {

	public static ParentKnowledgeBaseFolderPage toDTO(String json) {
		ParentKnowledgeBaseFolderPageJSONParser
			parentKnowledgeBaseFolderPageJSONParser =
				new ParentKnowledgeBaseFolderPageJSONParser();

		return parentKnowledgeBaseFolderPageJSONParser.parseToDTO(json);
	}

	public static ParentKnowledgeBaseFolderPage[] toDTOs(String json) {
		ParentKnowledgeBaseFolderPageJSONParser
			parentKnowledgeBaseFolderPageJSONParser =
				new ParentKnowledgeBaseFolderPageJSONParser();

		return parentKnowledgeBaseFolderPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ParentKnowledgeBaseFolderPage parentKnowledgeBaseFolderPage) {

		if (parentKnowledgeBaseFolderPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (parentKnowledgeBaseFolderPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < parentKnowledgeBaseFolderPage.getItems().length;
				 i++) {

				sb.append(
					ParentKnowledgeBaseFolderSerDes.toJSON(
						parentKnowledgeBaseFolderPage.getItems()[i]));

				if ((i + 1) < parentKnowledgeBaseFolderPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (parentKnowledgeBaseFolderPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentKnowledgeBaseFolderPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (parentKnowledgeBaseFolderPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentKnowledgeBaseFolderPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (parentKnowledgeBaseFolderPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentKnowledgeBaseFolderPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (parentKnowledgeBaseFolderPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentKnowledgeBaseFolderPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ParentKnowledgeBaseFolderPageJSONParser
		extends BaseJSONParser<ParentKnowledgeBaseFolderPage> {

		protected ParentKnowledgeBaseFolderPage createDTO() {
			return new ParentKnowledgeBaseFolderPage();
		}

		protected ParentKnowledgeBaseFolderPage[] createDTOArray(int size) {
			return new ParentKnowledgeBaseFolderPage[size];
		}

		protected void setField(
			ParentKnowledgeBaseFolderPage parentKnowledgeBaseFolderPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolderPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ParentKnowledgeBaseFolderSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ParentKnowledgeBaseFolder[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolderPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolderPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolderPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolderPage.setTotalCount(
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