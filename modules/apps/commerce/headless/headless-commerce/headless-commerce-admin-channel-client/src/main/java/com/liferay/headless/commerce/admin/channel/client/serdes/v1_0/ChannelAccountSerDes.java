/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.ChannelAccount;
import com.liferay.headless.commerce.admin.channel.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class ChannelAccountSerDes {

	public static ChannelAccount toDTO(String json) {
		ChannelAccountJSONParser channelAccountJSONParser =
			new ChannelAccountJSONParser();

		return channelAccountJSONParser.parseToDTO(json);
	}

	public static ChannelAccount[] toDTOs(String json) {
		ChannelAccountJSONParser channelAccountJSONParser =
			new ChannelAccountJSONParser();

		return channelAccountJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ChannelAccount channelAccount) {
		if (channelAccount == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (channelAccount.getAccount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"account\": ");

			sb.append(String.valueOf(channelAccount.getAccount()));
		}

		if (channelAccount.getAccountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(channelAccount.getAccountExternalReferenceCode()));

			sb.append("\"");
		}

		if (channelAccount.getAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(channelAccount.getAccountId());
		}

		if (channelAccount.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(channelAccount.getActions()));
		}

		if (channelAccount.getChannelAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelAccountId\": ");

			sb.append(channelAccount.getChannelAccountId());
		}

		if (channelAccount.getChannelExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(channelAccount.getChannelExternalReferenceCode()));

			sb.append("\"");
		}

		if (channelAccount.getChannelId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelId\": ");

			sb.append(channelAccount.getChannelId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ChannelAccountJSONParser channelAccountJSONParser =
			new ChannelAccountJSONParser();

		return channelAccountJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ChannelAccount channelAccount) {
		if (channelAccount == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (channelAccount.getAccount() == null) {
			map.put("account", null);
		}
		else {
			map.put("account", String.valueOf(channelAccount.getAccount()));
		}

		if (channelAccount.getAccountExternalReferenceCode() == null) {
			map.put("accountExternalReferenceCode", null);
		}
		else {
			map.put(
				"accountExternalReferenceCode",
				String.valueOf(
					channelAccount.getAccountExternalReferenceCode()));
		}

		if (channelAccount.getAccountId() == null) {
			map.put("accountId", null);
		}
		else {
			map.put("accountId", String.valueOf(channelAccount.getAccountId()));
		}

		if (channelAccount.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(channelAccount.getActions()));
		}

		if (channelAccount.getChannelAccountId() == null) {
			map.put("channelAccountId", null);
		}
		else {
			map.put(
				"channelAccountId",
				String.valueOf(channelAccount.getChannelAccountId()));
		}

		if (channelAccount.getChannelExternalReferenceCode() == null) {
			map.put("channelExternalReferenceCode", null);
		}
		else {
			map.put(
				"channelExternalReferenceCode",
				String.valueOf(
					channelAccount.getChannelExternalReferenceCode()));
		}

		if (channelAccount.getChannelId() == null) {
			map.put("channelId", null);
		}
		else {
			map.put("channelId", String.valueOf(channelAccount.getChannelId()));
		}

		return map;
	}

	public static class ChannelAccountJSONParser
		extends BaseJSONParser<ChannelAccount> {

		@Override
		protected ChannelAccount createDTO() {
			return new ChannelAccount();
		}

		@Override
		protected ChannelAccount[] createDTOArray(int size) {
			return new ChannelAccount[size];
		}

		@Override
		protected void setField(
			ChannelAccount channelAccount, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "account")) {
				if (jsonParserFieldValue != null) {
					channelAccount.setAccount(
						AccountSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "accountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					channelAccount.setAccountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountId")) {
				if (jsonParserFieldValue != null) {
					channelAccount.setAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					channelAccount.setActions(
						(Map)ChannelAccountSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelAccountId")) {
				if (jsonParserFieldValue != null) {
					channelAccount.setChannelAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "channelExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					channelAccount.setChannelExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelId")) {
				if (jsonParserFieldValue != null) {
					channelAccount.setChannelId(
						Long.valueOf((String)jsonParserFieldValue));
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