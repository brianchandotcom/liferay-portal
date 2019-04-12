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

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"email\": ");

		if (segmentUser.getEmail() == null) {
			sb.append("null");
		}
		else {
			sb.append(segmentUser.getEmail());
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
			sb.append(segmentUser.getName());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<SegmentUser> segmentUsers) {
		if (segmentUsers == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (SegmentUser segmentUser : segmentUsers) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(segmentUser));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class SegmentUserJSONParser
		extends BaseJSONParser<SegmentUser> {

		protected SegmentUser createDTO() {
			return new SegmentUser();
		}

		protected SegmentUser[] createDTOArray(int size) {
			return new SegmentUser[size];
		}

		protected void setField(
			SegmentUser segmentUser, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					segmentUser.setEmail((String)jsonParserFieldValue);
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