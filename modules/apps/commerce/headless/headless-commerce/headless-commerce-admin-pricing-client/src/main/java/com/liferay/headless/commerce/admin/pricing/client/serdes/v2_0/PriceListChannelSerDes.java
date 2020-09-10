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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListChannel;
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
public class PriceListChannelSerDes {

	public static PriceListChannel toDTO(String json) {
		PriceListChannelJSONParser priceListChannelJSONParser =
			new PriceListChannelJSONParser();

		return priceListChannelJSONParser.parseToDTO(json);
	}

	public static PriceListChannel[] toDTOs(String json) {
		PriceListChannelJSONParser priceListChannelJSONParser =
			new PriceListChannelJSONParser();

		return priceListChannelJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PriceListChannel priceListChannel) {
		if (priceListChannel == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (priceListChannel.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(priceListChannel.getActions()));
		}

		if (priceListChannel.getChannel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channel\": ");

			sb.append(String.valueOf(priceListChannel.getChannel()));
		}

		if (priceListChannel.getChannelExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(priceListChannel.getChannelExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceListChannel.getChannelId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelId\": ");

			sb.append(priceListChannel.getChannelId());
		}

		if (priceListChannel.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(priceListChannel.getId());
		}

		if (priceListChannel.getOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"order\": ");

			sb.append(priceListChannel.getOrder());
		}

		if (priceListChannel.getPriceListExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(priceListChannel.getPriceListExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceListChannel.getPriceListId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListId\": ");

			sb.append(priceListChannel.getPriceListId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PriceListChannelJSONParser priceListChannelJSONParser =
			new PriceListChannelJSONParser();

		return priceListChannelJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PriceListChannel priceListChannel) {
		if (priceListChannel == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (priceListChannel.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(priceListChannel.getActions()));
		}

		if (priceListChannel.getChannel() == null) {
			map.put("channel", null);
		}
		else {
			map.put("channel", String.valueOf(priceListChannel.getChannel()));
		}

		if (priceListChannel.getChannelExternalReferenceCode() == null) {
			map.put("channelExternalReferenceCode", null);
		}
		else {
			map.put(
				"channelExternalReferenceCode",
				String.valueOf(
					priceListChannel.getChannelExternalReferenceCode()));
		}

		if (priceListChannel.getChannelId() == null) {
			map.put("channelId", null);
		}
		else {
			map.put(
				"channelId", String.valueOf(priceListChannel.getChannelId()));
		}

		if (priceListChannel.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(priceListChannel.getId()));
		}

		if (priceListChannel.getOrder() == null) {
			map.put("order", null);
		}
		else {
			map.put("order", String.valueOf(priceListChannel.getOrder()));
		}

		if (priceListChannel.getPriceListExternalReferenceCode() == null) {
			map.put("priceListExternalReferenceCode", null);
		}
		else {
			map.put(
				"priceListExternalReferenceCode",
				String.valueOf(
					priceListChannel.getPriceListExternalReferenceCode()));
		}

		if (priceListChannel.getPriceListId() == null) {
			map.put("priceListId", null);
		}
		else {
			map.put(
				"priceListId",
				String.valueOf(priceListChannel.getPriceListId()));
		}

		return map;
	}

	public static class PriceListChannelJSONParser
		extends BaseJSONParser<PriceListChannel> {

		@Override
		protected PriceListChannel createDTO() {
			return new PriceListChannel();
		}

		@Override
		protected PriceListChannel[] createDTOArray(int size) {
			return new PriceListChannel[size];
		}

		@Override
		protected void setField(
			PriceListChannel priceListChannel, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					priceListChannel.setActions(
						(Map)PriceListChannelSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channel")) {
				if (jsonParserFieldValue != null) {
					priceListChannel.setChannel(
						ChannelSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "channelExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceListChannel.setChannelExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelId")) {
				if (jsonParserFieldValue != null) {
					priceListChannel.setChannelId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					priceListChannel.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "order")) {
				if (jsonParserFieldValue != null) {
					priceListChannel.setOrder(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"priceListExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceListChannel.setPriceListExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceListId")) {
				if (jsonParserFieldValue != null) {
					priceListChannel.setPriceListId(
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