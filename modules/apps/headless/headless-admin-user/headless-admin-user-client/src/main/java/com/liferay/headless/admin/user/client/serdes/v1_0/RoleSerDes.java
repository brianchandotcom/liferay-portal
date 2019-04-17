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

import com.liferay.headless.admin.user.client.dto.v1_0.Role;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RoleSerDes {

	public static Role toDTO(String json) {
		RoleJSONParser roleJSONParser = new RoleJSONParser();

		return roleJSONParser.parseToDTO(json);
	}

	public static Role[] toDTOs(String json) {
		RoleJSONParser roleJSONParser = new RoleJSONParser();

		return roleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Role role) {
		if (role == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (role.getAvailableLanguages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availableLanguages\":");

			sb.append("[");

			for (int i = 0; i < role.getAvailableLanguages().length; i++) {
				sb.append("\"");

				sb.append(role.getAvailableLanguages()[i]);

				sb.append("\"");

				if ((i + 1) < role.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (role.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\":");

			sb.append(CreatorSerDes.toJSON(role.getCreator()));
		}

		if (role.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(role.getDateCreated()));

			sb.append("\"");
		}

		if (role.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(role.getDateModified()));

			sb.append("\"");
		}

		if (role.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\":");

			sb.append("\"");

			sb.append(role.getDescription());

			sb.append("\"");
		}

		if (role.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(role.getId());
		}

		if (role.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(role.getName());

			sb.append("\"");
		}

		if (role.getRoleType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleType\":");

			sb.append("\"");

			sb.append(role.getRoleType());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Role role) {
		if (role == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (role.getAvailableLanguages() == null) {
			map.put("availableLanguages", null);
		}
		else {
			map.put(
				"availableLanguages",
				String.valueOf(role.getAvailableLanguages()));
		}

		if (role.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", CreatorSerDes.toJSON(role.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(role.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(role.getDateModified()));

		if (role.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(role.getDescription()));
		}

		if (role.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(role.getId()));
		}

		if (role.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(role.getName()));
		}

		if (role.getRoleType() == null) {
			map.put("roleType", null);
		}
		else {
			map.put("roleType", String.valueOf(role.getRoleType()));
		}

		return map;
	}

	private static class RoleJSONParser extends BaseJSONParser<Role> {

		@Override
		protected Role createDTO() {
			return new Role();
		}

		@Override
		protected Role[] createDTOArray(int size) {
			return new Role[size];
		}

		@Override
		protected void setField(
			Role role, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "availableLanguages")) {
				if (jsonParserFieldValue != null) {
					role.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					role.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					role.setDateCreated(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					role.setDateModified(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					role.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					role.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					role.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleType")) {
				if (jsonParserFieldValue != null) {
					role.setRoleType((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}