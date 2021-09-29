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

import com.liferay.headless.admin.user.client.dto.v1_0.AccountBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class AccountBriefSerDes {

	public static AccountBrief toDTO(String json) {
		AccountBriefJSONParser accountBriefJSONParser =
			new AccountBriefJSONParser();

		return accountBriefJSONParser.parseToDTO(json);
	}

	public static AccountBrief[] toDTOs(String json) {
		AccountBriefJSONParser accountBriefJSONParser =
			new AccountBriefJSONParser();

		return accountBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AccountBrief accountBrief) {
		if (accountBrief == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (accountBrief.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(accountBrief.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (accountBrief.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(accountBrief.getId());
		}

		if (accountBrief.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(accountBrief.getName()));

			sb.append("\"");
		}

		if (accountBrief.getRoleBriefs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleBriefs\": ");

			sb.append("[");

			for (int i = 0; i < accountBrief.getRoleBriefs().length; i++) {
				sb.append(String.valueOf(accountBrief.getRoleBriefs()[i]));

				if ((i + 1) < accountBrief.getRoleBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AccountBriefJSONParser accountBriefJSONParser =
			new AccountBriefJSONParser();

		return accountBriefJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AccountBrief accountBrief) {
		if (accountBrief == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (accountBrief.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(accountBrief.getExternalReferenceCode()));
		}

		if (accountBrief.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(accountBrief.getId()));
		}

		if (accountBrief.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(accountBrief.getName()));
		}

		if (accountBrief.getRoleBriefs() == null) {
			map.put("roleBriefs", null);
		}
		else {
			map.put("roleBriefs", String.valueOf(accountBrief.getRoleBriefs()));
		}

		return map;
	}

	public static class AccountBriefJSONParser
		extends BaseJSONParser<AccountBrief> {

		@Override
		protected AccountBrief createDTO() {
			return new AccountBrief();
		}

		@Override
		protected AccountBrief[] createDTOArray(int size) {
			return new AccountBrief[size];
		}

		@Override
		protected void setField(
			AccountBrief accountBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "externalReferenceCode")) {
				if (jsonParserFieldValue != null) {
					accountBrief.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					accountBrief.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					accountBrief.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleBriefs")) {
				if (jsonParserFieldValue != null) {
					accountBrief.setRoleBriefs(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RoleBriefSerDes.toDTO((String)object)
						).toArray(
							size -> new RoleBrief[size]
						));
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}