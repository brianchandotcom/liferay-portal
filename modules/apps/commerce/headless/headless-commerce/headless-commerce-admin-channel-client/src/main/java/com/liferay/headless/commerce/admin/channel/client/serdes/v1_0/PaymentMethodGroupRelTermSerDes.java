/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.PaymentMethodGroupRelTerm;
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
public class PaymentMethodGroupRelTermSerDes {

	public static PaymentMethodGroupRelTerm toDTO(String json) {
		PaymentMethodGroupRelTermJSONParser
			paymentMethodGroupRelTermJSONParser =
				new PaymentMethodGroupRelTermJSONParser();

		return paymentMethodGroupRelTermJSONParser.parseToDTO(json);
	}

	public static PaymentMethodGroupRelTerm[] toDTOs(String json) {
		PaymentMethodGroupRelTermJSONParser
			paymentMethodGroupRelTermJSONParser =
				new PaymentMethodGroupRelTermJSONParser();

		return paymentMethodGroupRelTermJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		PaymentMethodGroupRelTerm paymentMethodGroupRelTerm) {

		if (paymentMethodGroupRelTerm == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (paymentMethodGroupRelTerm.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(paymentMethodGroupRelTerm.getActions()));
		}

		if (paymentMethodGroupRelTerm.getPaymentMethodGroupRelId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentMethodGroupRelId\": ");

			sb.append(paymentMethodGroupRelTerm.getPaymentMethodGroupRelId());
		}

		if (paymentMethodGroupRelTerm.getPaymentMethodGroupRelTermId() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentMethodGroupRelTermId\": ");

			sb.append(
				paymentMethodGroupRelTerm.getPaymentMethodGroupRelTermId());
		}

		if (paymentMethodGroupRelTerm.getTerm() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"term\": ");

			sb.append(String.valueOf(paymentMethodGroupRelTerm.getTerm()));
		}

		if (paymentMethodGroupRelTerm.getTermExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"termExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					paymentMethodGroupRelTerm.getTermExternalReferenceCode()));

			sb.append("\"");
		}

		if (paymentMethodGroupRelTerm.getTermId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"termId\": ");

			sb.append(paymentMethodGroupRelTerm.getTermId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PaymentMethodGroupRelTermJSONParser
			paymentMethodGroupRelTermJSONParser =
				new PaymentMethodGroupRelTermJSONParser();

		return paymentMethodGroupRelTermJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PaymentMethodGroupRelTerm paymentMethodGroupRelTerm) {

		if (paymentMethodGroupRelTerm == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (paymentMethodGroupRelTerm.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put(
				"actions",
				String.valueOf(paymentMethodGroupRelTerm.getActions()));
		}

		if (paymentMethodGroupRelTerm.getPaymentMethodGroupRelId() == null) {
			map.put("paymentMethodGroupRelId", null);
		}
		else {
			map.put(
				"paymentMethodGroupRelId",
				String.valueOf(
					paymentMethodGroupRelTerm.getPaymentMethodGroupRelId()));
		}

		if (paymentMethodGroupRelTerm.getPaymentMethodGroupRelTermId() ==
				null) {

			map.put("paymentMethodGroupRelTermId", null);
		}
		else {
			map.put(
				"paymentMethodGroupRelTermId",
				String.valueOf(
					paymentMethodGroupRelTerm.
						getPaymentMethodGroupRelTermId()));
		}

		if (paymentMethodGroupRelTerm.getTerm() == null) {
			map.put("term", null);
		}
		else {
			map.put(
				"term", String.valueOf(paymentMethodGroupRelTerm.getTerm()));
		}

		if (paymentMethodGroupRelTerm.getTermExternalReferenceCode() == null) {
			map.put("termExternalReferenceCode", null);
		}
		else {
			map.put(
				"termExternalReferenceCode",
				String.valueOf(
					paymentMethodGroupRelTerm.getTermExternalReferenceCode()));
		}

		if (paymentMethodGroupRelTerm.getTermId() == null) {
			map.put("termId", null);
		}
		else {
			map.put(
				"termId",
				String.valueOf(paymentMethodGroupRelTerm.getTermId()));
		}

		return map;
	}

	public static class PaymentMethodGroupRelTermJSONParser
		extends BaseJSONParser<PaymentMethodGroupRelTerm> {

		@Override
		protected PaymentMethodGroupRelTerm createDTO() {
			return new PaymentMethodGroupRelTerm();
		}

		@Override
		protected PaymentMethodGroupRelTerm[] createDTOArray(int size) {
			return new PaymentMethodGroupRelTerm[size];
		}

		@Override
		protected void setField(
			PaymentMethodGroupRelTerm paymentMethodGroupRelTerm,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					paymentMethodGroupRelTerm.setActions(
						(Map)PaymentMethodGroupRelTermSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "paymentMethodGroupRelId")) {

				if (jsonParserFieldValue != null) {
					paymentMethodGroupRelTerm.setPaymentMethodGroupRelId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "paymentMethodGroupRelTermId")) {

				if (jsonParserFieldValue != null) {
					paymentMethodGroupRelTerm.setPaymentMethodGroupRelTermId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "term")) {
				if (jsonParserFieldValue != null) {
					paymentMethodGroupRelTerm.setTerm(
						TermSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "termExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					paymentMethodGroupRelTerm.setTermExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "termId")) {
				if (jsonParserFieldValue != null) {
					paymentMethodGroupRelTerm.setTermId(
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