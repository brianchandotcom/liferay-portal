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
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseAttachmentPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class KnowledgeBaseAttachmentPageSerDes {

	public static KnowledgeBaseAttachmentPage toDTO(String json) {
		KnowledgeBaseAttachmentPageJSONParser
			knowledgeBaseAttachmentPageJSONParser =
				new KnowledgeBaseAttachmentPageJSONParser();

		return knowledgeBaseAttachmentPageJSONParser.parseToDTO(json);
	}

	public static KnowledgeBaseAttachmentPage[] toDTOs(String json) {
		KnowledgeBaseAttachmentPageJSONParser
			knowledgeBaseAttachmentPageJSONParser =
				new KnowledgeBaseAttachmentPageJSONParser();

		return knowledgeBaseAttachmentPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		KnowledgeBaseAttachmentPage knowledgeBaseAttachmentPage) {

		if (knowledgeBaseAttachmentPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (knowledgeBaseAttachmentPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < knowledgeBaseAttachmentPage.getItems().length;
				 i++) {

				sb.append(
					KnowledgeBaseAttachmentSerDes.toJSON(
						knowledgeBaseAttachmentPage.getItems()[i]));

				if ((i + 1) < knowledgeBaseAttachmentPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (knowledgeBaseAttachmentPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseAttachmentPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (knowledgeBaseAttachmentPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseAttachmentPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (knowledgeBaseAttachmentPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseAttachmentPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (knowledgeBaseAttachmentPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseAttachmentPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class KnowledgeBaseAttachmentPageJSONParser
		extends BaseJSONParser<KnowledgeBaseAttachmentPage> {

		protected KnowledgeBaseAttachmentPage createDTO() {
			return new KnowledgeBaseAttachmentPage();
		}

		protected KnowledgeBaseAttachmentPage[] createDTOArray(int size) {
			return new KnowledgeBaseAttachmentPage[size];
		}

		protected void setField(
			KnowledgeBaseAttachmentPage knowledgeBaseAttachmentPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachmentPage.setItems(
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
					knowledgeBaseAttachmentPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachmentPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachmentPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachmentPage.setTotalCount(
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