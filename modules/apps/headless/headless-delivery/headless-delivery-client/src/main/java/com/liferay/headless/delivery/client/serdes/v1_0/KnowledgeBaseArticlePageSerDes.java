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
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticlePage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class KnowledgeBaseArticlePageSerDes {

	public static KnowledgeBaseArticlePage toDTO(String json) {
		KnowledgeBaseArticlePageJSONParser knowledgeBaseArticlePageJSONParser =
			new KnowledgeBaseArticlePageJSONParser();

		return knowledgeBaseArticlePageJSONParser.parseToDTO(json);
	}

	public static KnowledgeBaseArticlePage[] toDTOs(String json) {
		KnowledgeBaseArticlePageJSONParser knowledgeBaseArticlePageJSONParser =
			new KnowledgeBaseArticlePageJSONParser();

		return knowledgeBaseArticlePageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		KnowledgeBaseArticlePage knowledgeBaseArticlePage) {

		if (knowledgeBaseArticlePage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (knowledgeBaseArticlePage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < knowledgeBaseArticlePage.getItems().length;
				 i++) {

				sb.append(
					KnowledgeBaseArticleSerDes.toJSON(
						knowledgeBaseArticlePage.getItems()[i]));

				if ((i + 1) < knowledgeBaseArticlePage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (knowledgeBaseArticlePage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseArticlePage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (knowledgeBaseArticlePage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseArticlePage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (knowledgeBaseArticlePage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseArticlePage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (knowledgeBaseArticlePage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseArticlePage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class KnowledgeBaseArticlePageJSONParser
		extends BaseJSONParser<KnowledgeBaseArticlePage> {

		protected KnowledgeBaseArticlePage createDTO() {
			return new KnowledgeBaseArticlePage();
		}

		protected KnowledgeBaseArticlePage[] createDTOArray(int size) {
			return new KnowledgeBaseArticlePage[size];
		}

		protected void setField(
			KnowledgeBaseArticlePage knowledgeBaseArticlePage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticlePage.setItems(
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
					knowledgeBaseArticlePage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticlePage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticlePage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticlePage.setTotalCount(
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