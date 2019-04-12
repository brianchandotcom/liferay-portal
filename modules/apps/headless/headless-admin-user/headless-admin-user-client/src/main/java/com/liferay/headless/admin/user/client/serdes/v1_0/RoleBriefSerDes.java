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

import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RoleBriefSerDes {

	public static RoleBrief toDTO(String json) {
		RoleBriefJSONParser roleBriefJSONParser = new RoleBriefJSONParser();

		return roleBriefJSONParser.parseToDTO(json);
	}

	public static RoleBrief[] toDTOs(String json) {
		RoleBriefJSONParser roleBriefJSONParser = new RoleBriefJSONParser();

		return roleBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RoleBrief roleBrief) {
		if (roleBrief == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		if (roleBrief.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(roleBrief.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (roleBrief.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append(roleBrief.getName());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<RoleBrief> roleBriefs) {
		if (roleBriefs == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (RoleBrief roleBrief : roleBriefs) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(roleBrief));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class RoleBriefJSONParser extends BaseJSONParser<RoleBrief> {

		protected RoleBrief createDTO() {
			return new RoleBrief();
		}

		protected RoleBrief[] createDTOArray(int size) {
			return new RoleBrief[size];
		}

		protected void setField(
			RoleBrief roleBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					roleBrief.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					roleBrief.setName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}