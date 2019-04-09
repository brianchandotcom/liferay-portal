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

import com.liferay.headless.foundation.client.dto.v1_0.SitesBrief;
import com.liferay.headless.foundation.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SitesBriefSerDes {

	public static SitesBrief toDTO(String json) {
		SitesBriefJSONParser sitesBriefJSONParser = new SitesBriefJSONParser();

		return sitesBriefJSONParser.parseToDTO(json);
	}

	public static SitesBrief[] toDTOs(String json) {
		SitesBriefJSONParser sitesBriefJSONParser = new SitesBriefJSONParser();

		return sitesBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SitesBrief sitesBrief) {
		if (sitesBrief == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		sb.append(sitesBrief.getId());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(sitesBrief.getName());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<SitesBrief> sitesBriefs) {
		if (sitesBriefs == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (SitesBrief sitesBrief : sitesBriefs) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(sitesBrief));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class SitesBriefJSONParser
		extends BaseJSONParser<SitesBrief> {

		protected SitesBrief createDTO() {
			return new SitesBrief();
		}

		protected SitesBrief[] createDTOArray(int size) {
			return new SitesBrief[size];
		}

		protected void setField(
			SitesBrief sitesBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					sitesBrief.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					sitesBrief.setName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}