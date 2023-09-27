/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.PaymentMethod;
import com.liferay.headless.commerce.delivery.cart.client.json.BaseJSONParser;

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
public class PaymentMethodSerDes {

	public static PaymentMethod toDTO(String json) {
		PaymentMethodJSONParser paymentMethodJSONParser =
			new PaymentMethodJSONParser();

		return paymentMethodJSONParser.parseToDTO(json);
	}

	public static PaymentMethod[] toDTOs(String json) {
		PaymentMethodJSONParser paymentMethodJSONParser =
			new PaymentMethodJSONParser();

		return paymentMethodJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PaymentMethod paymentMethod) {
		if (paymentMethod == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (paymentMethod.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(paymentMethod.getDescription()));

			sb.append("\"");
		}

		if (paymentMethod.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(paymentMethod.getKey()));

			sb.append("\"");
		}

		if (paymentMethod.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(paymentMethod.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PaymentMethodJSONParser paymentMethodJSONParser =
			new PaymentMethodJSONParser();

		return paymentMethodJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PaymentMethod paymentMethod) {
		if (paymentMethod == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (paymentMethod.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(paymentMethod.getDescription()));
		}

		if (paymentMethod.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(paymentMethod.getKey()));
		}

		if (paymentMethod.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(paymentMethod.getName()));
		}

		return map;
	}

	public static class PaymentMethodJSONParser
		extends BaseJSONParser<PaymentMethod> {

		@Override
		protected PaymentMethod createDTO() {
			return new PaymentMethod();
		}

		@Override
		protected PaymentMethod[] createDTOArray(int size) {
			return new PaymentMethod[size];
		}

		@Override
		protected void setField(
			PaymentMethod paymentMethod, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					paymentMethod.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					paymentMethod.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					paymentMethod.setName((String)jsonParserFieldValue);
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