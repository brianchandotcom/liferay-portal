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

import com.liferay.headless.delivery.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.delivery.client.dto.v1_0.TaxonomyCategoryLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class TaxonomyCategoryLiferayPageSerDes {

	public static TaxonomyCategoryLiferayPage toDTO(String json) {
		TaxonomyCategoryLiferayPageJSONParser
			taxonomyCategoryLiferayPageJSONParser =
				new TaxonomyCategoryLiferayPageJSONParser();

		return taxonomyCategoryLiferayPageJSONParser.parseToDTO(json);
	}

	public static TaxonomyCategoryLiferayPage[] toDTOs(String json) {
		TaxonomyCategoryLiferayPageJSONParser
			taxonomyCategoryLiferayPageJSONParser =
				new TaxonomyCategoryLiferayPageJSONParser();

		return taxonomyCategoryLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		TaxonomyCategoryLiferayPage taxonomyCategoryLiferayPage) {

		if (taxonomyCategoryLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (taxonomyCategoryLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < taxonomyCategoryLiferayPage.getItems().length;
				 i++) {

				sb.append(
					TaxonomyCategorySerDes.toJSON(
						taxonomyCategoryLiferayPage.getItems()[i]));

				if ((i + 1) < taxonomyCategoryLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (taxonomyCategoryLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategoryLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (taxonomyCategoryLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategoryLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (taxonomyCategoryLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategoryLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (taxonomyCategoryLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategoryLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class TaxonomyCategoryLiferayPageJSONParser
		extends BaseJSONParser<TaxonomyCategoryLiferayPage> {

		protected TaxonomyCategoryLiferayPage createDTO() {
			return new TaxonomyCategoryLiferayPage();
		}

		protected TaxonomyCategoryLiferayPage[] createDTOArray(int size) {
			return new TaxonomyCategoryLiferayPage[size];
		}

		protected void setField(
			TaxonomyCategoryLiferayPage taxonomyCategoryLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TaxonomyCategorySerDes.toDTO(
								(String)object)
						).toArray(
							size -> new TaxonomyCategory[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryLiferayPage.setTotalCount(
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