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
import com.liferay.headless.delivery.client.dto.v1_0.TaxonomyCategoryPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class TaxonomyCategoryPageSerDes {

	public static TaxonomyCategoryPage toDTO(String json) {
		TaxonomyCategoryPageJSONParser taxonomyCategoryPageJSONParser =
			new TaxonomyCategoryPageJSONParser();

		return taxonomyCategoryPageJSONParser.parseToDTO(json);
	}

	public static TaxonomyCategoryPage[] toDTOs(String json) {
		TaxonomyCategoryPageJSONParser taxonomyCategoryPageJSONParser =
			new TaxonomyCategoryPageJSONParser();

		return taxonomyCategoryPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TaxonomyCategoryPage taxonomyCategoryPage) {
		if (taxonomyCategoryPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (taxonomyCategoryPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < taxonomyCategoryPage.getItems().length; i++) {
				sb.append(
					TaxonomyCategorySerDes.toJSON(
						taxonomyCategoryPage.getItems()[i]));

				if ((i + 1) < taxonomyCategoryPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (taxonomyCategoryPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategoryPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (taxonomyCategoryPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategoryPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (taxonomyCategoryPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategoryPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (taxonomyCategoryPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyCategoryPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class TaxonomyCategoryPageJSONParser
		extends BaseJSONParser<TaxonomyCategoryPage> {

		protected TaxonomyCategoryPage createDTO() {
			return new TaxonomyCategoryPage();
		}

		protected TaxonomyCategoryPage[] createDTOArray(int size) {
			return new TaxonomyCategoryPage[size];
		}

		protected void setField(
			TaxonomyCategoryPage taxonomyCategoryPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryPage.setItems(
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
					taxonomyCategoryPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategoryPage.setTotalCount(
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