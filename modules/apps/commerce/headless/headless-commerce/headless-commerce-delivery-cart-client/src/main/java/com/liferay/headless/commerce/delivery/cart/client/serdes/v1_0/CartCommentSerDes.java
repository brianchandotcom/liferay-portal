/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.cart.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CartComment;
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
public class CartCommentSerDes {

	public static CartComment toDTO(String json) {
		CartCommentJSONParser cartCommentJSONParser =
			new CartCommentJSONParser();

		return cartCommentJSONParser.parseToDTO(json);
	}

	public static CartComment[] toDTOs(String json) {
		CartCommentJSONParser cartCommentJSONParser =
			new CartCommentJSONParser();

		return cartCommentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CartComment cartComment) {
		if (cartComment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (cartComment.getAuthor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"author\": ");

			sb.append("\"");

			sb.append(_escape(cartComment.getAuthor()));

			sb.append("\"");
		}

		if (cartComment.getContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"content\": ");

			sb.append("\"");

			sb.append(_escape(cartComment.getContent()));

			sb.append("\"");
		}

		if (cartComment.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(cartComment.getId());
		}

		if (cartComment.getOrderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderId\": ");

			sb.append(cartComment.getOrderId());
		}

		if (cartComment.getRestricted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"restricted\": ");

			sb.append(cartComment.getRestricted());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CartCommentJSONParser cartCommentJSONParser =
			new CartCommentJSONParser();

		return cartCommentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CartComment cartComment) {
		if (cartComment == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (cartComment.getAuthor() == null) {
			map.put("author", null);
		}
		else {
			map.put("author", String.valueOf(cartComment.getAuthor()));
		}

		if (cartComment.getContent() == null) {
			map.put("content", null);
		}
		else {
			map.put("content", String.valueOf(cartComment.getContent()));
		}

		if (cartComment.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(cartComment.getId()));
		}

		if (cartComment.getOrderId() == null) {
			map.put("orderId", null);
		}
		else {
			map.put("orderId", String.valueOf(cartComment.getOrderId()));
		}

		if (cartComment.getRestricted() == null) {
			map.put("restricted", null);
		}
		else {
			map.put("restricted", String.valueOf(cartComment.getRestricted()));
		}

		return map;
	}

	public static class CartCommentJSONParser
		extends BaseJSONParser<CartComment> {

		@Override
		protected CartComment createDTO() {
			return new CartComment();
		}

		@Override
		protected CartComment[] createDTOArray(int size) {
			return new CartComment[size];
		}

		@Override
		protected void setField(
			CartComment cartComment, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "author")) {
				if (jsonParserFieldValue != null) {
					cartComment.setAuthor((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "content")) {
				if (jsonParserFieldValue != null) {
					cartComment.setContent((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					cartComment.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderId")) {
				if (jsonParserFieldValue != null) {
					cartComment.setOrderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "restricted")) {
				if (jsonParserFieldValue != null) {
					cartComment.setRestricted((Boolean)jsonParserFieldValue);
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