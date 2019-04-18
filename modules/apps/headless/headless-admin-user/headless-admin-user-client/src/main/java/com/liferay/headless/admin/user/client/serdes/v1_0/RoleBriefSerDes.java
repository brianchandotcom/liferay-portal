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

import java.util.HashMap;
import java.util.Map;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (roleBrief.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(roleBrief.getId());
		}

		if (roleBrief.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(_escape(roleBrief.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(RoleBrief roleBrief) {
		if (roleBrief == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (roleBrief.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(roleBrief.getId()));
		}

		if (roleBrief.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(roleBrief.getName()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class RoleBriefJSONParser extends BaseJSONParser<RoleBrief> {

		@Override
		protected RoleBrief createDTO() {
			return new RoleBrief();
		}

		@Override
		protected RoleBrief[] createDTOArray(int size) {
			return new RoleBrief[size];
		}

		@Override
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