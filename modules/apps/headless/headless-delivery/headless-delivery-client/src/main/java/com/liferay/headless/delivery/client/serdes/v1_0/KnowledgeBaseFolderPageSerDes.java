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
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolderPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class KnowledgeBaseFolderPageSerDes {

	public static KnowledgeBaseFolderPage toDTO(String json) {
		KnowledgeBaseFolderPageJSONParser knowledgeBaseFolderPageJSONParser =
			new KnowledgeBaseFolderPageJSONParser();

		return knowledgeBaseFolderPageJSONParser.parseToDTO(json);
	}

	public static KnowledgeBaseFolderPage[] toDTOs(String json) {
		KnowledgeBaseFolderPageJSONParser knowledgeBaseFolderPageJSONParser =
			new KnowledgeBaseFolderPageJSONParser();

		return knowledgeBaseFolderPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		KnowledgeBaseFolderPage knowledgeBaseFolderPage) {

		if (knowledgeBaseFolderPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (knowledgeBaseFolderPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < knowledgeBaseFolderPage.getItems().length;
				 i++) {

				sb.append(
					KnowledgeBaseFolderSerDes.toJSON(
						knowledgeBaseFolderPage.getItems()[i]));

				if ((i + 1) < knowledgeBaseFolderPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (knowledgeBaseFolderPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolderPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (knowledgeBaseFolderPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolderPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (knowledgeBaseFolderPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolderPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (knowledgeBaseFolderPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolderPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class KnowledgeBaseFolderPageJSONParser
		extends BaseJSONParser<KnowledgeBaseFolderPage> {

		protected KnowledgeBaseFolderPage createDTO() {
			return new KnowledgeBaseFolderPage();
		}

		protected KnowledgeBaseFolderPage[] createDTOArray(int size) {
			return new KnowledgeBaseFolderPage[size];
		}

		protected void setField(
			KnowledgeBaseFolderPage knowledgeBaseFolderPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolderPage.setItems(
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
					knowledgeBaseFolderPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolderPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolderPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolderPage.setTotalCount(
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