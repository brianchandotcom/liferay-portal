/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.order.client.dto.v1_0.PlacedOrderComment;
import com.liferay.headless.commerce.delivery.order.client.json.BaseJSONParser;

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
public class PlacedOrderCommentSerDes {

	public static PlacedOrderComment toDTO(String json) {
		PlacedOrderCommentJSONParser placedOrderCommentJSONParser =
			new PlacedOrderCommentJSONParser();

		return placedOrderCommentJSONParser.parseToDTO(json);
	}

	public static PlacedOrderComment[] toDTOs(String json) {
		PlacedOrderCommentJSONParser placedOrderCommentJSONParser =
			new PlacedOrderCommentJSONParser();

		return placedOrderCommentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PlacedOrderComment placedOrderComment) {
		if (placedOrderComment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (placedOrderComment.getAuthor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"author\": ");

			sb.append("\"");

			sb.append(_escape(placedOrderComment.getAuthor()));

			sb.append("\"");
		}

		if (placedOrderComment.getContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"content\": ");

			sb.append("\"");

			sb.append(_escape(placedOrderComment.getContent()));

			sb.append("\"");
		}

		if (placedOrderComment.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(placedOrderComment.getId());
		}

		if (placedOrderComment.getOrderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderId\": ");

			sb.append(placedOrderComment.getOrderId());
		}

		if (placedOrderComment.getRestricted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"restricted\": ");

			sb.append(placedOrderComment.getRestricted());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PlacedOrderCommentJSONParser placedOrderCommentJSONParser =
			new PlacedOrderCommentJSONParser();

		return placedOrderCommentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PlacedOrderComment placedOrderComment) {

		if (placedOrderComment == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (placedOrderComment.getAuthor() == null) {
			map.put("author", null);
		}
		else {
			map.put("author", String.valueOf(placedOrderComment.getAuthor()));
		}

		if (placedOrderComment.getContent() == null) {
			map.put("content", null);
		}
		else {
			map.put("content", String.valueOf(placedOrderComment.getContent()));
		}

		if (placedOrderComment.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(placedOrderComment.getId()));
		}

		if (placedOrderComment.getOrderId() == null) {
			map.put("orderId", null);
		}
		else {
			map.put("orderId", String.valueOf(placedOrderComment.getOrderId()));
		}

		if (placedOrderComment.getRestricted() == null) {
			map.put("restricted", null);
		}
		else {
			map.put(
				"restricted",
				String.valueOf(placedOrderComment.getRestricted()));
		}

		return map;
	}

	public static class PlacedOrderCommentJSONParser
		extends BaseJSONParser<PlacedOrderComment> {

		@Override
		protected PlacedOrderComment createDTO() {
			return new PlacedOrderComment();
		}

		@Override
		protected PlacedOrderComment[] createDTOArray(int size) {
			return new PlacedOrderComment[size];
		}

		@Override
		protected void setField(
			PlacedOrderComment placedOrderComment, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "author")) {
				if (jsonParserFieldValue != null) {
					placedOrderComment.setAuthor((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "content")) {
				if (jsonParserFieldValue != null) {
					placedOrderComment.setContent((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					placedOrderComment.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderId")) {
				if (jsonParserFieldValue != null) {
					placedOrderComment.setOrderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "restricted")) {
				if (jsonParserFieldValue != null) {
					placedOrderComment.setRestricted(
						(Boolean)jsonParserFieldValue);
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