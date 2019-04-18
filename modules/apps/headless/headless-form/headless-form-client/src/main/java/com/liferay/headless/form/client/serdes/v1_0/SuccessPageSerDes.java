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

package com.liferay.headless.form.client.serdes.v1_0;

import com.liferay.headless.form.client.dto.v1_0.SuccessPage;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SuccessPageSerDes {

	public static SuccessPage toDTO(String json) {
		SuccessPageJSONParser successPageJSONParser =
			new SuccessPageJSONParser();

		return successPageJSONParser.parseToDTO(json);
	}

	public static SuccessPage[] toDTOs(String json) {
		SuccessPageJSONParser successPageJSONParser =
			new SuccessPageJSONParser();

		return successPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SuccessPage successPage) {
		if (successPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (successPage.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\":");

			sb.append("\"");

			sb.append(_escape(successPage.getDescription()));

			sb.append("\"");
		}

		if (successPage.getHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline\":");

			sb.append("\"");

			sb.append(_escape(successPage.getHeadline()));

			sb.append("\"");
		}

		if (successPage.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(successPage.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(SuccessPage successPage) {
		if (successPage == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (successPage.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(successPage.getDescription()));
		}

		if (successPage.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put("headline", String.valueOf(successPage.getHeadline()));
		}

		if (successPage.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(successPage.getId()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class SuccessPageJSONParser
		extends BaseJSONParser<SuccessPage> {

		@Override
		protected SuccessPage createDTO() {
			return new SuccessPage();
		}

		@Override
		protected SuccessPage[] createDTOArray(int size) {
			return new SuccessPage[size];
		}

		@Override
		protected void setField(
			SuccessPage successPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					successPage.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					successPage.setHeadline((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					successPage.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}