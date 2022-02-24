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

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderType;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderTypeChannel;
import com.liferay.headless.commerce.admin.order.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class OrderTypeSerDes {

	public static OrderType toDTO(String json) {
		OrderTypeJSONParser orderTypeJSONParser = new OrderTypeJSONParser();

		return orderTypeJSONParser.parseToDTO(json);
	}

	public static OrderType[] toDTOs(String json) {
		OrderTypeJSONParser orderTypeJSONParser = new OrderTypeJSONParser();

		return orderTypeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(OrderType orderType) {
		if (orderType == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (orderType.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(orderType.getActions()));
		}

		if (orderType.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(orderType.getActive());
		}

		if (orderType.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(orderType.getCustomFields()));
		}

		if (orderType.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append(_toJSON(orderType.getDescription()));
		}

		if (orderType.getDisplayDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(orderType.getDisplayDate()));

			sb.append("\"");
		}

		if (orderType.getDisplayOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayOrder\": ");

			sb.append(orderType.getDisplayOrder());
		}

		if (orderType.getExpirationDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(orderType.getExpirationDate()));

			sb.append("\"");
		}

		if (orderType.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(orderType.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (orderType.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(orderType.getId());
		}

		if (orderType.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(orderType.getName()));
		}

		if (orderType.getNeverExpire() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(orderType.getNeverExpire());
		}

		if (orderType.getOrderTypeChannels() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderTypeChannels\": ");

			sb.append("[");

			for (int i = 0; i < orderType.getOrderTypeChannels().length; i++) {
				sb.append(String.valueOf(orderType.getOrderTypeChannels()[i]));

				if ((i + 1) < orderType.getOrderTypeChannels().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (orderType.getWorkflowStatusInfo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowStatusInfo\": ");

			sb.append(String.valueOf(orderType.getWorkflowStatusInfo()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OrderTypeJSONParser orderTypeJSONParser = new OrderTypeJSONParser();

		return orderTypeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(OrderType orderType) {
		if (orderType == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (orderType.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(orderType.getActions()));
		}

		if (orderType.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(orderType.getActive()));
		}

		if (orderType.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields", String.valueOf(orderType.getCustomFields()));
		}

		if (orderType.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(orderType.getDescription()));
		}

		if (orderType.getDisplayDate() == null) {
			map.put("displayDate", null);
		}
		else {
			map.put(
				"displayDate",
				liferayToJSONDateFormat.format(orderType.getDisplayDate()));
		}

		if (orderType.getDisplayOrder() == null) {
			map.put("displayOrder", null);
		}
		else {
			map.put(
				"displayOrder", String.valueOf(orderType.getDisplayOrder()));
		}

		if (orderType.getExpirationDate() == null) {
			map.put("expirationDate", null);
		}
		else {
			map.put(
				"expirationDate",
				liferayToJSONDateFormat.format(orderType.getExpirationDate()));
		}

		if (orderType.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(orderType.getExternalReferenceCode()));
		}

		if (orderType.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(orderType.getId()));
		}

		if (orderType.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(orderType.getName()));
		}

		if (orderType.getNeverExpire() == null) {
			map.put("neverExpire", null);
		}
		else {
			map.put("neverExpire", String.valueOf(orderType.getNeverExpire()));
		}

		if (orderType.getOrderTypeChannels() == null) {
			map.put("orderTypeChannels", null);
		}
		else {
			map.put(
				"orderTypeChannels",
				String.valueOf(orderType.getOrderTypeChannels()));
		}

		if (orderType.getWorkflowStatusInfo() == null) {
			map.put("workflowStatusInfo", null);
		}
		else {
			map.put(
				"workflowStatusInfo",
				String.valueOf(orderType.getWorkflowStatusInfo()));
		}

		return map;
	}

	public static class OrderTypeJSONParser extends BaseJSONParser<OrderType> {

		@Override
		protected OrderType createDTO() {
			return new OrderType();
		}

		@Override
		protected OrderType[] createDTOArray(int size) {
			return new OrderType[size];
		}

		@Override
		protected void setField(
			OrderType orderType, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					orderType.setActions(
						(Map)OrderTypeSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					orderType.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					orderType.setCustomFields(
						(Map)OrderTypeSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					orderType.setDescription(
						(Map)OrderTypeSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayDate")) {
				if (jsonParserFieldValue != null) {
					orderType.setDisplayDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayOrder")) {
				if (jsonParserFieldValue != null) {
					orderType.setDisplayOrder(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expirationDate")) {
				if (jsonParserFieldValue != null) {
					orderType.setExpirationDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					orderType.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					orderType.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					orderType.setName(
						(Map)OrderTypeSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "neverExpire")) {
				if (jsonParserFieldValue != null) {
					orderType.setNeverExpire((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderTypeChannels")) {
				if (jsonParserFieldValue != null) {
					orderType.setOrderTypeChannels(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OrderTypeChannelSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new OrderTypeChannel[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "workflowStatusInfo")) {

				if (jsonParserFieldValue != null) {
					orderType.setWorkflowStatusInfo(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
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