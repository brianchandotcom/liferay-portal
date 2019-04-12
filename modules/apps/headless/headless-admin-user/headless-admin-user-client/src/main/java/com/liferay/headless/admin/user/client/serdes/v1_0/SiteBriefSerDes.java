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

import com.liferay.headless.admin.user.client.dto.v1_0.SiteBrief;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SiteBriefSerDes {

	public static SiteBrief toDTO(String json) {
		SiteBriefJSONParser siteBriefJSONParser = new SiteBriefJSONParser();

		return siteBriefJSONParser.parseToDTO(json);
	}

	public static SiteBrief[] toDTOs(String json) {
		SiteBriefJSONParser siteBriefJSONParser = new SiteBriefJSONParser();

		return siteBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SiteBrief siteBrief) {
		if (siteBrief == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		if (siteBrief.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(siteBrief.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (siteBrief.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append(siteBrief.getName());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<SiteBrief> siteBriefs) {
		if (siteBriefs == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (SiteBrief siteBrief : siteBriefs) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(siteBrief));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class SiteBriefJSONParser extends BaseJSONParser<SiteBrief> {

		protected SiteBrief createDTO() {
			return new SiteBrief();
		}

		protected SiteBrief[] createDTOArray(int size) {
			return new SiteBrief[size];
		}

		protected void setField(
			SiteBrief siteBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					siteBrief.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					siteBrief.setName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}