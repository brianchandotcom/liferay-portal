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

package com.liferay.headless.commerce.admin.order.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.AccountGroup;
import com.liferay.headless.commerce.admin.order.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class AccountGroupSerDes {

	public static AccountGroup toDTO(String json) {
		AccountGroupJSONParser accountGroupJSONParser =
			new AccountGroupJSONParser();

		return accountGroupJSONParser.parseToDTO(json);
	}

	public static AccountGroup[] toDTOs(String json) {
		AccountGroupJSONParser accountGroupJSONParser =
			new AccountGroupJSONParser();

		return accountGroupJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AccountGroup accountGroup) {
		if (accountGroup == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (accountGroup.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(accountGroup.getId());
		}

		if (accountGroup.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(accountGroup.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AccountGroupJSONParser accountGroupJSONParser =
			new AccountGroupJSONParser();

		return accountGroupJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AccountGroup accountGroup) {
		if (accountGroup == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (accountGroup.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(accountGroup.getId()));
		}

		if (accountGroup.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(accountGroup.getName()));
		}

		return map;
	}

	public static class AccountGroupJSONParser
		extends BaseJSONParser<AccountGroup> {

		@Override
		protected AccountGroup createDTO() {
			return new AccountGroup();
		}

		@Override
		protected AccountGroup[] createDTOArray(int size) {
			return new AccountGroup[size];
		}

		@Override
		protected void setField(
			AccountGroup accountGroup, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					accountGroup.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					accountGroup.setName((String)jsonParserFieldValue);
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