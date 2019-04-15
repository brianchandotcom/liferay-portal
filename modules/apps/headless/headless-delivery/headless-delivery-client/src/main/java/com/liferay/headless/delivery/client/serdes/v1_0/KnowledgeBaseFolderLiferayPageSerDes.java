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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolderLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class KnowledgeBaseFolderLiferayPageSerDes {

	public static KnowledgeBaseFolderLiferayPage toDTO(String json) {
		KnowledgeBaseFolderLiferayPageJSONParser
			knowledgeBaseFolderLiferayPageJSONParser =
				new KnowledgeBaseFolderLiferayPageJSONParser();

		return knowledgeBaseFolderLiferayPageJSONParser.parseToDTO(json);
	}

	public static KnowledgeBaseFolderLiferayPage[] toDTOs(String json) {
		KnowledgeBaseFolderLiferayPageJSONParser
			knowledgeBaseFolderLiferayPageJSONParser =
				new KnowledgeBaseFolderLiferayPageJSONParser();

		return knowledgeBaseFolderLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		KnowledgeBaseFolderLiferayPage knowledgeBaseFolderLiferayPage) {

		if (knowledgeBaseFolderLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (knowledgeBaseFolderLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < knowledgeBaseFolderLiferayPage.getItems().length; i++) {

				sb.append(
					KnowledgeBaseFolderSerDes.toJSON(
						knowledgeBaseFolderLiferayPage.getItems()[i]));

				if ((i + 1) <
						knowledgeBaseFolderLiferayPage.getItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (knowledgeBaseFolderLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolderLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (knowledgeBaseFolderLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolderLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (knowledgeBaseFolderLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolderLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (knowledgeBaseFolderLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolderLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class KnowledgeBaseFolderLiferayPageJSONParser
		extends BaseJSONParser<KnowledgeBaseFolderLiferayPage> {

		protected KnowledgeBaseFolderLiferayPage createDTO() {
			return new KnowledgeBaseFolderLiferayPage();
		}

		protected KnowledgeBaseFolderLiferayPage[] createDTOArray(int size) {
			return new KnowledgeBaseFolderLiferayPage[size];
		}

		protected void setField(
			KnowledgeBaseFolderLiferayPage knowledgeBaseFolderLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolderLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> KnowledgeBaseFolderSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new KnowledgeBaseFolder[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolderLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolderLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolderLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolderLiferayPage.setTotalCount(
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