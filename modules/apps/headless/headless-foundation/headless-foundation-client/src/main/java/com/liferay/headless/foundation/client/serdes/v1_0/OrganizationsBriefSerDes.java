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

import com.liferay.headless.foundation.client.dto.v1_0.OrganizationsBrief;
import com.liferay.headless.foundation.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class OrganizationsBriefSerDes {

	public static OrganizationsBrief toDTO(String json) {
		OrganizationsBriefJSONParser organizationsBriefJSONParser =
			new OrganizationsBriefJSONParser();

		return organizationsBriefJSONParser.parseToDTO(json);
	}

	public static OrganizationsBrief[] toDTOs(String json) {
		OrganizationsBriefJSONParser organizationsBriefJSONParser =
			new OrganizationsBriefJSONParser();

		return organizationsBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(OrganizationsBrief organizationsBrief) {
		if (organizationsBrief == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		sb.append(organizationsBrief.getId());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(organizationsBrief.getName());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<OrganizationsBrief> organizationsBriefs) {

		if (organizationsBriefs == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (OrganizationsBrief organizationsBrief : organizationsBriefs) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(organizationsBrief));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class OrganizationsBriefJSONParser
		extends BaseJSONParser<OrganizationsBrief> {

		protected OrganizationsBrief createDTO() {
			return new OrganizationsBrief();
		}

		protected OrganizationsBrief[] createDTOArray(int size) {
			return new OrganizationsBrief[size];
		}

		protected void setField(
			OrganizationsBrief organizationsBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					organizationsBrief.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					organizationsBrief.setName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}