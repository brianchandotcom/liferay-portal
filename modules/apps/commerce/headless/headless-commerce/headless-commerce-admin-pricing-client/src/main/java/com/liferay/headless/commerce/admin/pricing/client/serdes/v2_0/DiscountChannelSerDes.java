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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountChannel;
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
public class DiscountChannelSerDes {

	public static DiscountChannel toDTO(String json) {
		DiscountChannelJSONParser discountChannelJSONParser =
			new DiscountChannelJSONParser();

		return discountChannelJSONParser.parseToDTO(json);
	}

	public static DiscountChannel[] toDTOs(String json) {
		DiscountChannelJSONParser discountChannelJSONParser =
			new DiscountChannelJSONParser();

		return discountChannelJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DiscountChannel discountChannel) {
		if (discountChannel == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (discountChannel.getChannelExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(discountChannel.getChannelExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountChannel.getChannelId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelId\": ");

			sb.append(discountChannel.getChannelId());
		}

		if (discountChannel.getChannelName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelName\": ");

			sb.append("\"");

			sb.append(_escape(discountChannel.getChannelName()));

			sb.append("\"");
		}

		if (discountChannel.getDiscountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(discountChannel.getDiscountExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountChannel.getDiscountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountId\": ");

			sb.append(discountChannel.getDiscountId());
		}

		if (discountChannel.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(discountChannel.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DiscountChannelJSONParser discountChannelJSONParser =
			new DiscountChannelJSONParser();

		return discountChannelJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DiscountChannel discountChannel) {
		if (discountChannel == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (discountChannel.getChannelExternalReferenceCode() == null) {
			map.put("channelExternalReferenceCode", null);
		}
		else {
			map.put(
				"channelExternalReferenceCode",
				String.valueOf(
					discountChannel.getChannelExternalReferenceCode()));
		}

		if (discountChannel.getChannelId() == null) {
			map.put("channelId", null);
		}
		else {
			map.put(
				"channelId", String.valueOf(discountChannel.getChannelId()));
		}

		if (discountChannel.getChannelName() == null) {
			map.put("channelName", null);
		}
		else {
			map.put(
				"channelName",
				String.valueOf(discountChannel.getChannelName()));
		}

		if (discountChannel.getDiscountExternalReferenceCode() == null) {
			map.put("discountExternalReferenceCode", null);
		}
		else {
			map.put(
				"discountExternalReferenceCode",
				String.valueOf(
					discountChannel.getDiscountExternalReferenceCode()));
		}

		if (discountChannel.getDiscountId() == null) {
			map.put("discountId", null);
		}
		else {
			map.put(
				"discountId", String.valueOf(discountChannel.getDiscountId()));
		}

		if (discountChannel.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(discountChannel.getId()));
		}

		return map;
	}

	public static class DiscountChannelJSONParser
		extends BaseJSONParser<DiscountChannel> {

		@Override
		protected DiscountChannel createDTO() {
			return new DiscountChannel();
		}

		@Override
		protected DiscountChannel[] createDTOArray(int size) {
			return new DiscountChannel[size];
		}

		@Override
		protected void setField(
			DiscountChannel discountChannel, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "channelExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountChannel.setChannelExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelId")) {
				if (jsonParserFieldValue != null) {
					discountChannel.setChannelId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelName")) {
				if (jsonParserFieldValue != null) {
					discountChannel.setChannelName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "discountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountChannel.setDiscountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountId")) {
				if (jsonParserFieldValue != null) {
					discountChannel.setDiscountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					discountChannel.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
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