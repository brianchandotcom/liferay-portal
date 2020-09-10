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

package com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountAccount;
import com.liferay.headless.commerce.admin.pricing.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class DiscountAccountSerDes {

	public static DiscountAccount toDTO(String json) {
		DiscountAccountJSONParser discountAccountJSONParser =
			new DiscountAccountJSONParser();

		return discountAccountJSONParser.parseToDTO(json);
	}

	public static DiscountAccount[] toDTOs(String json) {
		DiscountAccountJSONParser discountAccountJSONParser =
			new DiscountAccountJSONParser();

		return discountAccountJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DiscountAccount discountAccount) {
		if (discountAccount == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (discountAccount.getAccount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"account\": ");

			sb.append(String.valueOf(discountAccount.getAccount()));
		}

		if (discountAccount.getAccountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(discountAccount.getAccountExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountAccount.getAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(discountAccount.getAccountId());
		}

		if (discountAccount.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(discountAccount.getActions()));
		}

		if (discountAccount.getDiscountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(discountAccount.getDiscountExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountAccount.getDiscountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountId\": ");

			sb.append(discountAccount.getDiscountId());
		}

		if (discountAccount.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(discountAccount.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DiscountAccountJSONParser discountAccountJSONParser =
			new DiscountAccountJSONParser();

		return discountAccountJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DiscountAccount discountAccount) {
		if (discountAccount == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (discountAccount.getAccount() == null) {
			map.put("account", null);
		}
		else {
			map.put("account", String.valueOf(discountAccount.getAccount()));
		}

		if (discountAccount.getAccountExternalReferenceCode() == null) {
			map.put("accountExternalReferenceCode", null);
		}
		else {
			map.put(
				"accountExternalReferenceCode",
				String.valueOf(
					discountAccount.getAccountExternalReferenceCode()));
		}

		if (discountAccount.getAccountId() == null) {
			map.put("accountId", null);
		}
		else {
			map.put(
				"accountId", String.valueOf(discountAccount.getAccountId()));
		}

		if (discountAccount.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(discountAccount.getActions()));
		}

		if (discountAccount.getDiscountExternalReferenceCode() == null) {
			map.put("discountExternalReferenceCode", null);
		}
		else {
			map.put(
				"discountExternalReferenceCode",
				String.valueOf(
					discountAccount.getDiscountExternalReferenceCode()));
		}

		if (discountAccount.getDiscountId() == null) {
			map.put("discountId", null);
		}
		else {
			map.put(
				"discountId", String.valueOf(discountAccount.getDiscountId()));
		}

		if (discountAccount.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(discountAccount.getId()));
		}

		return map;
	}

	public static class DiscountAccountJSONParser
		extends BaseJSONParser<DiscountAccount> {

		@Override
		protected DiscountAccount createDTO() {
			return new DiscountAccount();
		}

		@Override
		protected DiscountAccount[] createDTOArray(int size) {
			return new DiscountAccount[size];
		}

		@Override
		protected void setField(
			DiscountAccount discountAccount, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "account")) {
				if (jsonParserFieldValue != null) {
					discountAccount.setAccount(
						AccountSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "accountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountAccount.setAccountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountId")) {
				if (jsonParserFieldValue != null) {
					discountAccount.setAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					discountAccount.setActions(
						(Map)DiscountAccountSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "discountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountAccount.setDiscountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountId")) {
				if (jsonParserFieldValue != null) {
					discountAccount.setDiscountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					discountAccount.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			sb.append("\":");

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
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}