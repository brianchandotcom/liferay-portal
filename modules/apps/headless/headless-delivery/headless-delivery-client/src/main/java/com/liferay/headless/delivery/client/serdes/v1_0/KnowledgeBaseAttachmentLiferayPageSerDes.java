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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseAttachmentLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class KnowledgeBaseAttachmentLiferayPageSerDes {

	public static KnowledgeBaseAttachmentLiferayPage toDTO(String json) {
		KnowledgeBaseAttachmentLiferayPageJSONParser
			knowledgeBaseAttachmentLiferayPageJSONParser =
				new KnowledgeBaseAttachmentLiferayPageJSONParser();

		return knowledgeBaseAttachmentLiferayPageJSONParser.parseToDTO(json);
	}

	public static KnowledgeBaseAttachmentLiferayPage[] toDTOs(String json) {
		KnowledgeBaseAttachmentLiferayPageJSONParser
			knowledgeBaseAttachmentLiferayPageJSONParser =
				new KnowledgeBaseAttachmentLiferayPageJSONParser();

		return knowledgeBaseAttachmentLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		KnowledgeBaseAttachmentLiferayPage knowledgeBaseAttachmentLiferayPage) {

		if (knowledgeBaseAttachmentLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (knowledgeBaseAttachmentLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < knowledgeBaseAttachmentLiferayPage.getItems().length;
				 i++) {

				sb.append(
					KnowledgeBaseAttachmentSerDes.toJSON(
						knowledgeBaseAttachmentLiferayPage.getItems()[i]));

				if ((i + 1) <
						knowledgeBaseAttachmentLiferayPage.getItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (knowledgeBaseAttachmentLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseAttachmentLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (knowledgeBaseAttachmentLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseAttachmentLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (knowledgeBaseAttachmentLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseAttachmentLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (knowledgeBaseAttachmentLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseAttachmentLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class KnowledgeBaseAttachmentLiferayPageJSONParser
		extends BaseJSONParser<KnowledgeBaseAttachmentLiferayPage> {

		protected KnowledgeBaseAttachmentLiferayPage createDTO() {
			return new KnowledgeBaseAttachmentLiferayPage();
		}

		protected KnowledgeBaseAttachmentLiferayPage[] createDTOArray(
			int size) {

			return new KnowledgeBaseAttachmentLiferayPage[size];
		}

		protected void setField(
			KnowledgeBaseAttachmentLiferayPage
				knowledgeBaseAttachmentLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachmentLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> KnowledgeBaseAttachmentSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new KnowledgeBaseAttachment[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachmentLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachmentLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachmentLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachmentLiferayPage.setTotalCount(
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