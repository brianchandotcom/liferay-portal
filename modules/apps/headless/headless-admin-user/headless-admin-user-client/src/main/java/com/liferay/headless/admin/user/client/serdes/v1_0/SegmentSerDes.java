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

import com.liferay.headless.admin.user.client.dto.v1_0.Segment;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SegmentSerDes {

	public static Segment toDTO(String json) {
		SegmentJSONParser segmentJSONParser = new SegmentJSONParser();

		return segmentJSONParser.parseToDTO(json);
	}

	public static Segment[] toDTOs(String json) {
		SegmentJSONParser segmentJSONParser = new SegmentJSONParser();

		return segmentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Segment segment) {
		if (segment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (segment.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\":");

			sb.append(segment.getActive());
		}

		if (segment.getCriteria() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"criteria\":");

			sb.append("\"");

			sb.append(segment.getCriteria());

			sb.append("\"");
		}

		if (segment.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(segment.getDateCreated()));

			sb.append("\"");
		}

		if (segment.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(segment.getDateModified()));

			sb.append("\"");
		}

		if (segment.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(segment.getId());
		}

		if (segment.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(segment.getName());

			sb.append("\"");
		}

		if (segment.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\":");

			sb.append(segment.getSiteId());
		}

		if (segment.getSource() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"source\":");

			sb.append("\"");

			sb.append(segment.getSource());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Segment segment) {
		if (segment == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (segment.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(segment.getActive()));
		}

		if (segment.getCriteria() == null) {
			map.put("criteria", null);
		}
		else {
			map.put("criteria", String.valueOf(segment.getCriteria()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(segment.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(segment.getDateModified()));

		if (segment.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(segment.getId()));
		}

		if (segment.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(segment.getName()));
		}

		if (segment.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(segment.getSiteId()));
		}

		if (segment.getSource() == null) {
			map.put("source", null);
		}
		else {
			map.put("source", String.valueOf(segment.getSource()));
		}

		return map;
	}

	private static class SegmentJSONParser extends BaseJSONParser<Segment> {

		@Override
		protected Segment createDTO() {
			return new Segment();
		}

		@Override
		protected Segment[] createDTOArray(int size) {
			return new Segment[size];
		}

		@Override
		protected void setField(
			Segment segment, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					segment.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "criteria")) {
				if (jsonParserFieldValue != null) {
					segment.setCriteria((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					segment.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					segment.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					segment.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					segment.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					segment.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "source")) {
				if (jsonParserFieldValue != null) {
					segment.setSource((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}