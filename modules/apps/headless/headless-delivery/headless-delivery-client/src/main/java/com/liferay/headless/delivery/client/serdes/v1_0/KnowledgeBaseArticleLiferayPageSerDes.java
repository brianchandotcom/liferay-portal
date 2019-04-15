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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticleLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class KnowledgeBaseArticleLiferayPageSerDes {

	public static KnowledgeBaseArticleLiferayPage toDTO(String json) {
		KnowledgeBaseArticleLiferayPageJSONParser
			knowledgeBaseArticleLiferayPageJSONParser =
				new KnowledgeBaseArticleLiferayPageJSONParser();

		return knowledgeBaseArticleLiferayPageJSONParser.parseToDTO(json);
	}

	public static KnowledgeBaseArticleLiferayPage[] toDTOs(String json) {
		KnowledgeBaseArticleLiferayPageJSONParser
			knowledgeBaseArticleLiferayPageJSONParser =
				new KnowledgeBaseArticleLiferayPageJSONParser();

		return knowledgeBaseArticleLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		KnowledgeBaseArticleLiferayPage knowledgeBaseArticleLiferayPage) {

		if (knowledgeBaseArticleLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (knowledgeBaseArticleLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < knowledgeBaseArticleLiferayPage.getItems().length; i++) {

				sb.append(
					KnowledgeBaseArticleSerDes.toJSON(
						knowledgeBaseArticleLiferayPage.getItems()[i]));

				if ((i + 1) <
						knowledgeBaseArticleLiferayPage.getItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (knowledgeBaseArticleLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseArticleLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (knowledgeBaseArticleLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseArticleLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (knowledgeBaseArticleLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseArticleLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (knowledgeBaseArticleLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseArticleLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class KnowledgeBaseArticleLiferayPageJSONParser
		extends BaseJSONParser<KnowledgeBaseArticleLiferayPage> {

		protected KnowledgeBaseArticleLiferayPage createDTO() {
			return new KnowledgeBaseArticleLiferayPage();
		}

		protected KnowledgeBaseArticleLiferayPage[] createDTOArray(int size) {
			return new KnowledgeBaseArticleLiferayPage[size];
		}

		protected void setField(
			KnowledgeBaseArticleLiferayPage knowledgeBaseArticleLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticleLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> KnowledgeBaseArticleSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new KnowledgeBaseArticle[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticleLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticleLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticleLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticleLiferayPage.setTotalCount(
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