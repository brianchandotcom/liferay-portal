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

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		if (organizationBrief.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(organizationBrief.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (organizationBrief.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append(organizationBrief.getName());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<OrganizationBrief> organizationBriefs) {

		if (organizationBriefs == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (OrganizationBrief organizationBrief : organizationBriefs) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(organizationBrief));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class OrganizationBriefJSONParser
		extends BaseJSONParser<OrganizationBrief> {

		protected OrganizationBrief createDTO() {
			return new OrganizationBrief();
		}

		protected OrganizationBrief[] createDTOArray(int size) {
			return new OrganizationBrief[size];
		}

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