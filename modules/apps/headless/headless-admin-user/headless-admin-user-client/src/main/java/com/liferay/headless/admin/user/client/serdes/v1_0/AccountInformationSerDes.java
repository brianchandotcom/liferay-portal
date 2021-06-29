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

import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.AccountInformation;
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
public class AccountInformationSerDes {

	public static AccountInformation toDTO(String json) {
		AccountInformationJSONParser accountInformationJSONParser =
			new AccountInformationJSONParser();

		return accountInformationJSONParser.parseToDTO(json);
	}

	public static AccountInformation[] toDTOs(String json) {
		AccountInformationJSONParser accountInformationJSONParser =
			new AccountInformationJSONParser();

		return accountInformationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AccountInformation accountInformation) {
		if (accountInformation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (accountInformation.getAccounts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accounts\": ");

			sb.append("[");

			for (int i = 0; i < accountInformation.getAccounts().length; i++) {
				sb.append(accountInformation.getAccounts()[i]);

				if ((i + 1) < accountInformation.getAccounts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (accountInformation.getCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"count\": ");

			sb.append(accountInformation.getCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AccountInformationJSONParser accountInformationJSONParser =
			new AccountInformationJSONParser();

		return accountInformationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AccountInformation accountInformation) {

		if (accountInformation == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (accountInformation.getAccounts() == null) {
			map.put("accounts", null);
		}
		else {
			map.put(
				"accounts", String.valueOf(accountInformation.getAccounts()));
		}

		if (accountInformation.getCount() == null) {
			map.put("count", null);
		}
		else {
			map.put("count", String.valueOf(accountInformation.getCount()));
		}

		return map;
	}

	public static class AccountInformationJSONParser
		extends BaseJSONParser<AccountInformation> {

		@Override
		protected AccountInformation createDTO() {
			return new AccountInformation();
		}

		@Override
		protected AccountInformation[] createDTOArray(int size) {
			return new AccountInformation[size];
		}

		@Override
		protected void setField(
			AccountInformation accountInformation, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accounts")) {
				if (jsonParserFieldValue != null) {
					accountInformation.setAccounts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AccountSerDes.toDTO((String)object)
						).toArray(
							size -> new Account[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "count")) {
				if (jsonParserFieldValue != null) {
					accountInformation.setCount(
						Integer.valueOf((String)jsonParserFieldValue));
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