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

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleAccount;
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
public class OrderRuleAccountSerDes {

	public static OrderRuleAccount toDTO(String json) {
		OrderRuleAccountJSONParser orderRuleAccountJSONParser =
			new OrderRuleAccountJSONParser();

		return orderRuleAccountJSONParser.parseToDTO(json);
	}

	public static OrderRuleAccount[] toDTOs(String json) {
		OrderRuleAccountJSONParser orderRuleAccountJSONParser =
			new OrderRuleAccountJSONParser();

		return orderRuleAccountJSONParser.parseToDTOs(json);
	}

	public static String toJSON(OrderRuleAccount orderRuleAccount) {
		if (orderRuleAccount == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (orderRuleAccount.getAccount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"account\": ");

			sb.append(String.valueOf(orderRuleAccount.getAccount()));
		}

		if (orderRuleAccount.getAccountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(orderRuleAccount.getAccountExternalReferenceCode()));

			sb.append("\"");
		}

		if (orderRuleAccount.getAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(orderRuleAccount.getAccountId());
		}

		if (orderRuleAccount.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(orderRuleAccount.getActions()));
		}

		if (orderRuleAccount.getOrderRuleAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderRuleAccountId\": ");

			sb.append(orderRuleAccount.getOrderRuleAccountId());
		}

		if (orderRuleAccount.getOrderRuleExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderRuleExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(orderRuleAccount.getOrderRuleExternalReferenceCode()));

			sb.append("\"");
		}

		if (orderRuleAccount.getOrderRuleId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderRuleId\": ");

			sb.append(orderRuleAccount.getOrderRuleId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OrderRuleAccountJSONParser orderRuleAccountJSONParser =
			new OrderRuleAccountJSONParser();

		return orderRuleAccountJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(OrderRuleAccount orderRuleAccount) {
		if (orderRuleAccount == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (orderRuleAccount.getAccount() == null) {
			map.put("account", null);
		}
		else {
			map.put("account", String.valueOf(orderRuleAccount.getAccount()));
		}

		if (orderRuleAccount.getAccountExternalReferenceCode() == null) {
			map.put("accountExternalReferenceCode", null);
		}
		else {
			map.put(
				"accountExternalReferenceCode",
				String.valueOf(
					orderRuleAccount.getAccountExternalReferenceCode()));
		}

		if (orderRuleAccount.getAccountId() == null) {
			map.put("accountId", null);
		}
		else {
			map.put(
				"accountId", String.valueOf(orderRuleAccount.getAccountId()));
		}

		if (orderRuleAccount.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(orderRuleAccount.getActions()));
		}

		if (orderRuleAccount.getOrderRuleAccountId() == null) {
			map.put("orderRuleAccountId", null);
		}
		else {
			map.put(
				"orderRuleAccountId",
				String.valueOf(orderRuleAccount.getOrderRuleAccountId()));
		}

		if (orderRuleAccount.getOrderRuleExternalReferenceCode() == null) {
			map.put("orderRuleExternalReferenceCode", null);
		}
		else {
			map.put(
				"orderRuleExternalReferenceCode",
				String.valueOf(
					orderRuleAccount.getOrderRuleExternalReferenceCode()));
		}

		if (orderRuleAccount.getOrderRuleId() == null) {
			map.put("orderRuleId", null);
		}
		else {
			map.put(
				"orderRuleId",
				String.valueOf(orderRuleAccount.getOrderRuleId()));
		}

		return map;
	}

	public static class OrderRuleAccountJSONParser
		extends BaseJSONParser<OrderRuleAccount> {

		@Override
		protected OrderRuleAccount createDTO() {
			return new OrderRuleAccount();
		}

		@Override
		protected OrderRuleAccount[] createDTOArray(int size) {
			return new OrderRuleAccount[size];
		}

		@Override
		protected void setField(
			OrderRuleAccount orderRuleAccount, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "account")) {
				if (jsonParserFieldValue != null) {
					orderRuleAccount.setAccount(
						AccountSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "accountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					orderRuleAccount.setAccountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountId")) {
				if (jsonParserFieldValue != null) {
					orderRuleAccount.setAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					orderRuleAccount.setActions(
						(Map)OrderRuleAccountSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "orderRuleAccountId")) {

				if (jsonParserFieldValue != null) {
					orderRuleAccount.setOrderRuleAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"orderRuleExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					orderRuleAccount.setOrderRuleExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderRuleId")) {
				if (jsonParserFieldValue != null) {
					orderRuleAccount.setOrderRuleId(
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