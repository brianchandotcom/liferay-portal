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
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class GeoSerDes {

	public static Geo toDTO(String json) {
		GeoJSONParser geoJSONParser = new GeoJSONParser();

		return geoJSONParser.parseToDTO(json);
	}

	public static Geo[] toDTOs(String json) {
		GeoJSONParser geoJSONParser = new GeoJSONParser();

		return geoJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Geo geo) {
		if (geo == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"latitude\": ");

		if (geo.getLatitude() == null) {
			sb.append("null");
		}
		else {
			sb.append(geo.getLatitude());
		}

		sb.append(", ");

		sb.append("\"longitude\": ");

		if (geo.getLongitude() == null) {
			sb.append("null");
		}
		else {
			sb.append(geo.getLongitude());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<Geo> geos) {
		if (geos == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Geo geo : geos) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(geo));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class GeoJSONParser extends BaseJSONParser<Geo> {

		protected Geo createDTO() {
			return new Geo();
		}

		protected Geo[] createDTOArray(int size) {
			return new Geo[size];
		}

		protected void setField(
			Geo geo, String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "latitude")) {
				if (jsonParserFieldValue != null) {
					geo.setLatitude((Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "longitude")) {
				if (jsonParserFieldValue != null) {
					geo.setLongitude((Number)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}