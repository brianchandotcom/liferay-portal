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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListAccount;
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
public class PriceListAccountSerDes {

	public static PriceListAccount toDTO(String json) {
		PriceListAccountJSONParser priceListAccountJSONParser =
			new PriceListAccountJSONParser();

		return priceListAccountJSONParser.parseToDTO(json);
	}

	public static PriceListAccount[] toDTOs(String json) {
		PriceListAccountJSONParser priceListAccountJSONParser =
			new PriceListAccountJSONParser();

		return priceListAccountJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PriceListAccount priceListAccount) {
		if (priceListAccount == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (priceListAccount.getAccount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"account\": ");

			sb.append(String.valueOf(priceListAccount.getAccount()));
		}

		if (priceListAccount.getAccountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(priceListAccount.getAccountExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceListAccount.getAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(priceListAccount.getAccountId());
		}

		if (priceListAccount.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(priceListAccount.getActions()));
		}

		if (priceListAccount.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(priceListAccount.getId());
		}

		if (priceListAccount.getOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"order\": ");

			sb.append(priceListAccount.getOrder());
		}

		if (priceListAccount.getPriceListExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(priceListAccount.getPriceListExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceListAccount.getPriceListId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListId\": ");

			sb.append(priceListAccount.getPriceListId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PriceListAccountJSONParser priceListAccountJSONParser =
			new PriceListAccountJSONParser();

		return priceListAccountJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PriceListAccount priceListAccount) {
		if (priceListAccount == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (priceListAccount.getAccount() == null) {
			map.put("account", null);
		}
		else {
			map.put("account", String.valueOf(priceListAccount.getAccount()));
		}

		if (priceListAccount.getAccountExternalReferenceCode() == null) {
			map.put("accountExternalReferenceCode", null);
		}
		else {
			map.put(
				"accountExternalReferenceCode",
				String.valueOf(
					priceListAccount.getAccountExternalReferenceCode()));
		}

		if (priceListAccount.getAccountId() == null) {
			map.put("accountId", null);
		}
		else {
			map.put(
				"accountId", String.valueOf(priceListAccount.getAccountId()));
		}

		if (priceListAccount.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(priceListAccount.getActions()));
		}

		if (priceListAccount.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(priceListAccount.getId()));
		}

		if (priceListAccount.getOrder() == null) {
			map.put("order", null);
		}
		else {
			map.put("order", String.valueOf(priceListAccount.getOrder()));
		}

		if (priceListAccount.getPriceListExternalReferenceCode() == null) {
			map.put("priceListExternalReferenceCode", null);
		}
		else {
			map.put(
				"priceListExternalReferenceCode",
				String.valueOf(
					priceListAccount.getPriceListExternalReferenceCode()));
		}

		if (priceListAccount.getPriceListId() == null) {
			map.put("priceListId", null);
		}
		else {
			map.put(
				"priceListId",
				String.valueOf(priceListAccount.getPriceListId()));
		}

		return map;
	}

	public static class PriceListAccountJSONParser
		extends BaseJSONParser<PriceListAccount> {

		@Override
		protected PriceListAccount createDTO() {
			return new PriceListAccount();
		}

		@Override
		protected PriceListAccount[] createDTOArray(int size) {
			return new PriceListAccount[size];
		}

		@Override
		protected void setField(
			PriceListAccount priceListAccount, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "account")) {
				if (jsonParserFieldValue != null) {
					priceListAccount.setAccount(
						AccountSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "accountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceListAccount.setAccountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountId")) {
				if (jsonParserFieldValue != null) {
					priceListAccount.setAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					priceListAccount.setActions(
						(Map)PriceListAccountSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					priceListAccount.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "order")) {
				if (jsonParserFieldValue != null) {
					priceListAccount.setOrder(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"priceListExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceListAccount.setPriceListExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceListId")) {
				if (jsonParserFieldValue != null) {
					priceListAccount.setPriceListId(
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