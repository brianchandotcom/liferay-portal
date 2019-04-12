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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.Location;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class LocationSerDes {

	public static Location toDTO(String json) {
		LocationJSONParser locationJSONParser = new LocationJSONParser();

		return locationJSONParser.parseToDTO(json);
	}

	public static Location[] toDTOs(String json) {
		LocationJSONParser locationJSONParser = new LocationJSONParser();

		return locationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Location location) {
		if (location == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"addressCountry\": ");

		if (location.getAddressCountry() == null) {
			sb.append("null");
		}
		else {
			sb.append(location.getAddressCountry());
		}

		sb.append(", ");

		sb.append("\"addressRegion\": ");

		if (location.getAddressRegion() == null) {
			sb.append("null");
		}
		else {
			sb.append(location.getAddressRegion());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (location.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(location.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<Location> locations) {
		if (locations == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Location location : locations) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(location));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class LocationJSONParser extends BaseJSONParser<Location> {

		protected Location createDTO() {
			return new Location();
		}

		protected Location[] createDTOArray(int size) {
			return new Location[size];
		}

		protected void setField(
			Location location, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "addressCountry")) {
				if (jsonParserFieldValue != null) {
					location.setAddressCountry((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "addressRegion")) {
				if (jsonParserFieldValue != null) {
					location.setAddressRegion((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					location.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}