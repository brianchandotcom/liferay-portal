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

import com.liferay.headless.admin.user.client.dto.v1_0.HoursAvailable;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class HoursAvailableSerDes {

	public static HoursAvailable toDTO(String json) {
		HoursAvailableJSONParser hoursAvailableJSONParser =
			new HoursAvailableJSONParser();

		return hoursAvailableJSONParser.parseToDTO(json);
	}

	public static HoursAvailable[] toDTOs(String json) {
		HoursAvailableJSONParser hoursAvailableJSONParser =
			new HoursAvailableJSONParser();

		return hoursAvailableJSONParser.parseToDTOs(json);
	}

	public static String toJSON(HoursAvailable hoursAvailable) {
		if (hoursAvailable == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"closes\": ");

		if (hoursAvailable.getCloses() == null) {
			sb.append("null");
		}
		else {
			sb.append(hoursAvailable.getCloses());
		}

		sb.append(", ");

		sb.append("\"dayOfWeek\": ");

		if (hoursAvailable.getDayOfWeek() == null) {
			sb.append("null");
		}
		else {
			sb.append(hoursAvailable.getDayOfWeek());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (hoursAvailable.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(hoursAvailable.getId());
		}

		sb.append(", ");

		sb.append("\"opens\": ");

		if (hoursAvailable.getOpens() == null) {
			sb.append("null");
		}
		else {
			sb.append(hoursAvailable.getOpens());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<HoursAvailable> hoursAvailables) {
		if (hoursAvailables == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (HoursAvailable hoursAvailable : hoursAvailables) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(hoursAvailable));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class HoursAvailableJSONParser
		extends BaseJSONParser<HoursAvailable> {

		protected HoursAvailable createDTO() {
			return new HoursAvailable();
		}

		protected HoursAvailable[] createDTOArray(int size) {
			return new HoursAvailable[size];
		}

		protected void setField(
			HoursAvailable hoursAvailable, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "closes")) {
				if (jsonParserFieldValue != null) {
					hoursAvailable.setCloses((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dayOfWeek")) {
				if (jsonParserFieldValue != null) {
					hoursAvailable.setDayOfWeek((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					hoursAvailable.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "opens")) {
				if (jsonParserFieldValue != null) {
					hoursAvailable.setOpens((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}