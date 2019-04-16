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

import com.liferay.headless.admin.user.client.dto.v1_0.Creator;
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
public class CreatorSerDes {

	public static Creator toDTO(String json) {
		CreatorJSONParser creatorJSONParser = new CreatorJSONParser();

		return creatorJSONParser.parseToDTO(json);
	}

	public static Creator[] toDTOs(String json) {
		CreatorJSONParser creatorJSONParser = new CreatorJSONParser();

		return creatorJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Creator creator) {
		if (creator == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"additionalName\": ");

		if (creator.getAdditionalName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(creator.getAdditionalName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"familyName\": ");

		if (creator.getFamilyName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(creator.getFamilyName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"givenName\": ");

		if (creator.getGivenName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(creator.getGivenName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (creator.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(creator.getId());
		}

		sb.append(", ");

		sb.append("\"image\": ");

		if (creator.getImage() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(creator.getImage());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (creator.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(creator.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"profileURL\": ");

		if (creator.getProfileURL() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(creator.getProfileURL());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Creator creator) {
		if (creator == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (creator.getAdditionalName() == null) {
			map.put("additionalName", null);
		}
		else {
			map.put(
				"additionalName", String.valueOf(creator.getAdditionalName()));
		}

		if (creator.getFamilyName() == null) {
			map.put("familyName", null);
		}
		else {
			map.put("familyName", String.valueOf(creator.getFamilyName()));
		}

		if (creator.getGivenName() == null) {
			map.put("givenName", null);
		}
		else {
			map.put("givenName", String.valueOf(creator.getGivenName()));
		}

		if (creator.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(creator.getId()));
		}

		if (creator.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(creator.getImage()));
		}

		if (creator.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(creator.getName()));
		}

		if (creator.getProfileURL() == null) {
			map.put("profileURL", null);
		}
		else {
			map.put("profileURL", String.valueOf(creator.getProfileURL()));
		}

		return map;
	}

	private static class CreatorJSONParser extends BaseJSONParser<Creator> {

		@Override
		protected Creator createDTO() {
			return new Creator();
		}

		@Override
		protected Creator[] createDTOArray(int size) {
			return new Creator[size];
		}

		@Override
		protected void setField(
			Creator creator, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "additionalName")) {
				if (jsonParserFieldValue != null) {
					creator.setAdditionalName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "familyName")) {
				if (jsonParserFieldValue != null) {
					creator.setFamilyName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "givenName")) {
				if (jsonParserFieldValue != null) {
					creator.setGivenName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					creator.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					creator.setImage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					creator.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "profileURL")) {
				if (jsonParserFieldValue != null) {
					creator.setProfileURL((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}