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

package com.liferay.headless.foundation.client.serdes.v1_0;

import com.liferay.headless.foundation.client.dto.v1_0.RolesBrief;
import com.liferay.headless.foundation.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RolesBriefSerDes {

	public static RolesBrief toDTO(String json) {
		RolesBriefJSONParser rolesBriefJSONParser = new RolesBriefJSONParser();

		return rolesBriefJSONParser.parseToDTO(json);
	}

	public static RolesBrief[] toDTOs(String json) {
		RolesBriefJSONParser rolesBriefJSONParser = new RolesBriefJSONParser();

		return rolesBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RolesBrief rolesBrief) {
		if (rolesBrief == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		sb.append(rolesBrief.getId());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(rolesBrief.getName());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<RolesBrief> rolesBriefs) {
		if (rolesBriefs == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (RolesBrief rolesBrief : rolesBriefs) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(rolesBrief));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class RolesBriefJSONParser
		extends BaseJSONParser<RolesBrief> {

		protected RolesBrief createDTO() {
			return new RolesBrief();
		}

		protected RolesBrief[] createDTOArray(int size) {
			return new RolesBrief[size];
		}

		protected void setField(
			RolesBrief rolesBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					rolesBrief.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					rolesBrief.setName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}