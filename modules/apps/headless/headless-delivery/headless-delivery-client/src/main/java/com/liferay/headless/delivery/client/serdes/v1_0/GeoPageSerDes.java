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

import com.liferay.headless.delivery.client.dto.v1_0.Geo;
import com.liferay.headless.delivery.client.dto.v1_0.GeoPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class GeoPageSerDes {

	public static GeoPage toDTO(String json) {
		GeoPageJSONParser geoPageJSONParser = new GeoPageJSONParser();

		return geoPageJSONParser.parseToDTO(json);
	}

	public static GeoPage[] toDTOs(String json) {
		GeoPageJSONParser geoPageJSONParser = new GeoPageJSONParser();

		return geoPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(GeoPage geoPage) {
		if (geoPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (geoPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < geoPage.getItems().length; i++) {
				sb.append(GeoSerDes.toJSON(geoPage.getItems()[i]));

				if ((i + 1) < geoPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (geoPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(geoPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (geoPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(geoPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (geoPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(geoPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (geoPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(geoPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class GeoPageJSONParser extends BaseJSONParser<GeoPage> {

		protected GeoPage createDTO() {
			return new GeoPage();
		}

		protected GeoPage[] createDTOArray(int size) {
			return new GeoPage[size];
		}

		protected void setField(
			GeoPage geoPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					geoPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> GeoSerDes.toDTO((String)object)
						).toArray(
							size -> new Geo[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					geoPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					geoPage.setPage(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					geoPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					geoPage.setTotalCount(
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