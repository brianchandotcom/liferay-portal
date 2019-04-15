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
import com.liferay.headless.delivery.client.dto.v1_0.ParentKnowledgeBaseFolderLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ParentKnowledgeBaseFolderLiferayPageSerDes {

	public static ParentKnowledgeBaseFolderLiferayPage toDTO(String json) {
		ParentKnowledgeBaseFolderLiferayPageJSONParser
			parentKnowledgeBaseFolderLiferayPageJSONParser =
				new ParentKnowledgeBaseFolderLiferayPageJSONParser();

		return parentKnowledgeBaseFolderLiferayPageJSONParser.parseToDTO(json);
	}

	public static ParentKnowledgeBaseFolderLiferayPage[] toDTOs(String json) {
		ParentKnowledgeBaseFolderLiferayPageJSONParser
			parentKnowledgeBaseFolderLiferayPageJSONParser =
				new ParentKnowledgeBaseFolderLiferayPageJSONParser();

		return parentKnowledgeBaseFolderLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ParentKnowledgeBaseFolderLiferayPage
			parentKnowledgeBaseFolderLiferayPage) {

		if (parentKnowledgeBaseFolderLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (parentKnowledgeBaseFolderLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < parentKnowledgeBaseFolderLiferayPage.getItems().length;
				 i++) {

				sb.append(
					ParentKnowledgeBaseFolderSerDes.toJSON(
						parentKnowledgeBaseFolderLiferayPage.getItems()[i]));

				if ((i + 1) <
						parentKnowledgeBaseFolderLiferayPage.
							getItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (parentKnowledgeBaseFolderLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentKnowledgeBaseFolderLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (parentKnowledgeBaseFolderLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentKnowledgeBaseFolderLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (parentKnowledgeBaseFolderLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentKnowledgeBaseFolderLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (parentKnowledgeBaseFolderLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentKnowledgeBaseFolderLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ParentKnowledgeBaseFolderLiferayPageJSONParser
		extends BaseJSONParser<ParentKnowledgeBaseFolderLiferayPage> {

		protected ParentKnowledgeBaseFolderLiferayPage createDTO() {
			return new ParentKnowledgeBaseFolderLiferayPage();
		}

		protected ParentKnowledgeBaseFolderLiferayPage[] createDTOArray(
			int size) {

			return new ParentKnowledgeBaseFolderLiferayPage[size];
		}

		protected void setField(
			ParentKnowledgeBaseFolderLiferayPage
				parentKnowledgeBaseFolderLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolderLiferayPage.setItems(
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
					parentKnowledgeBaseFolderLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolderLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolderLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolderLiferayPage.setTotalCount(
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