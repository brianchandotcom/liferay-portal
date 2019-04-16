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

import com.liferay.headless.admin.user.client.dto.v1_0.SegmentUser;
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
public class SegmentUserSerDes {

	public static SegmentUser toDTO(String json) {
		SegmentUserJSONParser segmentUserJSONParser =
			new SegmentUserJSONParser();

		return segmentUserJSONParser.parseToDTO(json);
	}

	public static SegmentUser[] toDTOs(String json) {
		SegmentUserJSONParser segmentUserJSONParser =
			new SegmentUserJSONParser();

		return segmentUserJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SegmentUser segmentUser) {
		if (segmentUser == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"emailAddress\": ");

		if (segmentUser.getEmailAddress() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(segmentUser.getEmailAddress());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (segmentUser.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(segmentUser.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (segmentUser.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(segmentUser.getName());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(SegmentUser segmentUser) {
		if (segmentUser == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put("emailAddress", String.valueOf(segmentUser.getEmailAddress()));

		map.put("id", String.valueOf(segmentUser.getId()));

		map.put("name", String.valueOf(segmentUser.getName()));

		return map;
	}

	private static class SegmentUserJSONParser
		extends BaseJSONParser<SegmentUser> {

		@Override
		protected SegmentUser createDTO() {
			return new SegmentUser();
		}

		@Override
		protected SegmentUser[] createDTOArray(int size) {
			return new SegmentUser[size];
		}

		@Override
		protected void setField(
			SegmentUser segmentUser, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "emailAddress")) {
				if (jsonParserFieldValue != null) {
					segmentUser.setEmailAddress((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					segmentUser.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					segmentUser.setName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}