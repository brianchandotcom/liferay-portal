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

import java.util.HashMap;
import java.util.Map;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (location.getAddressCountry() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"addressCountry\":");

			sb.append("\"");

			sb.append(location.getAddressCountry());

			sb.append("\"");
		}

		if (location.getAddressRegion() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"addressRegion\":");

			sb.append("\"");

			sb.append(location.getAddressRegion());

			sb.append("\"");
		}

		if (location.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(location.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Location location) {
		if (location == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (location.getAddressCountry() == null) {
			map.put("addressCountry", null);
		}
		else {
			map.put(
				"addressCountry", String.valueOf(location.getAddressCountry()));
		}

		if (location.getAddressRegion() == null) {
			map.put("addressRegion", null);
		}
		else {
			map.put(
				"addressRegion", String.valueOf(location.getAddressRegion()));
		}

		if (location.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(location.getId()));
		}

		return map;
	}

	private static class LocationJSONParser extends BaseJSONParser<Location> {

		@Override
		protected Location createDTO() {
			return new Location();
		}

		@Override
		protected Location[] createDTOArray(int size) {
			return new Location[size];
		}

		@Override
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