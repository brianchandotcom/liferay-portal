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

import com.liferay.headless.admin.user.client.dto.v1_0.OrganizationBrief;
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
public class OrganizationBriefSerDes {

	public static OrganizationBrief toDTO(String json) {
		OrganizationBriefJSONParser organizationBriefJSONParser =
			new OrganizationBriefJSONParser();

		return organizationBriefJSONParser.parseToDTO(json);
	}

	public static OrganizationBrief[] toDTOs(String json) {
		OrganizationBriefJSONParser organizationBriefJSONParser =
			new OrganizationBriefJSONParser();

		return organizationBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(OrganizationBrief organizationBrief) {
		if (organizationBrief == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (organizationBrief.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(organizationBrief.getId());
		}

		if (organizationBrief.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(organizationBrief.getName());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		OrganizationBrief organizationBrief) {

		if (organizationBrief == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (organizationBrief.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(organizationBrief.getId()));
		}

		if (organizationBrief.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(organizationBrief.getName()));
		}

		return map;
	}

	private static class OrganizationBriefJSONParser
		extends BaseJSONParser<OrganizationBrief> {

		@Override
		protected OrganizationBrief createDTO() {
			return new OrganizationBrief();
		}

		@Override
		protected OrganizationBrief[] createDTOArray(int size) {
			return new OrganizationBrief[size];
		}

		@Override
		protected void setField(
			OrganizationBrief organizationBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					organizationBrief.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					organizationBrief.setName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}