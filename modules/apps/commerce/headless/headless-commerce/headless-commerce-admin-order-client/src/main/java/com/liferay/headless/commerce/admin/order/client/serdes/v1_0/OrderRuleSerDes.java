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

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleAccount;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleChannel;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleOrderType;
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
public class OrderRuleSerDes {

	public static OrderRule toDTO(String json) {
		OrderRuleJSONParser orderRuleJSONParser = new OrderRuleJSONParser();

		return orderRuleJSONParser.parseToDTO(json);
	}

	public static OrderRule[] toDTOs(String json) {
		OrderRuleJSONParser orderRuleJSONParser = new OrderRuleJSONParser();

		return orderRuleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(OrderRule orderRule) {
		if (orderRule == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (orderRule.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(orderRule.getActions()));
		}

		if (orderRule.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(orderRule.getActive());
		}

		if (orderRule.getAuthor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"author\": ");

			sb.append("\"");

			sb.append(_escape(orderRule.getAuthor()));

			sb.append("\"");
		}

		if (orderRule.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(orderRule.getCreateDate()));

			sb.append("\"");
		}

		if (orderRule.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(orderRule.getDescription()));

			sb.append("\"");
		}

		if (orderRule.getDisplayDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(orderRule.getDisplayDate()));

			sb.append("\"");
		}

		if (orderRule.getExpirationDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(orderRule.getExpirationDate()));

			sb.append("\"");
		}

		if (orderRule.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(orderRule.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (orderRule.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(orderRule.getId());
		}

		if (orderRule.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(orderRule.getName()));

			sb.append("\"");
		}

		if (orderRule.getNeverExpire() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(orderRule.getNeverExpire());
		}

		if (orderRule.getOrderRuleAccount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderRuleAccount\": ");

			sb.append("[");

			for (int i = 0; i < orderRule.getOrderRuleAccount().length; i++) {
				sb.append(String.valueOf(orderRule.getOrderRuleAccount()[i]));

				if ((i + 1) < orderRule.getOrderRuleAccount().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (orderRule.getOrderRuleAccountGroup() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderRuleAccountGroup\": ");

			sb.append("[");

			for (int i = 0; i < orderRule.getOrderRuleAccountGroup().length;
				 i++) {

				sb.append(
					String.valueOf(orderRule.getOrderRuleAccountGroup()[i]));

				if ((i + 1) < orderRule.getOrderRuleAccountGroup().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (orderRule.getOrderRuleChannel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderRuleChannel\": ");

			sb.append("[");

			for (int i = 0; i < orderRule.getOrderRuleChannel().length; i++) {
				sb.append(String.valueOf(orderRule.getOrderRuleChannel()[i]));

				if ((i + 1) < orderRule.getOrderRuleChannel().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (orderRule.getOrderRuleOrderType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderRuleOrderType\": ");

			sb.append("[");

			for (int i = 0; i < orderRule.getOrderRuleOrderType().length; i++) {
				sb.append(String.valueOf(orderRule.getOrderRuleOrderType()[i]));

				if ((i + 1) < orderRule.getOrderRuleOrderType().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (orderRule.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(orderRule.getPriority());
		}

		if (orderRule.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(orderRule.getType()));

			sb.append("\"");
		}

		if (orderRule.getTypeSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"typeSettings\": ");

			sb.append("\"");

			sb.append(_escape(orderRule.getTypeSettings()));

			sb.append("\"");
		}

		if (orderRule.getWorkflowStatusInfo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowStatusInfo\": ");

			sb.append(String.valueOf(orderRule.getWorkflowStatusInfo()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OrderRuleJSONParser orderRuleJSONParser = new OrderRuleJSONParser();

		return orderRuleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(OrderRule orderRule) {
		if (orderRule == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (orderRule.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(orderRule.getActions()));
		}

		if (orderRule.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(orderRule.getActive()));
		}

		if (orderRule.getAuthor() == null) {
			map.put("author", null);
		}
		else {
			map.put("author", String.valueOf(orderRule.getAuthor()));
		}

		if (orderRule.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(orderRule.getCreateDate()));
		}

		if (orderRule.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(orderRule.getDescription()));
		}

		if (orderRule.getDisplayDate() == null) {
			map.put("displayDate", null);
		}
		else {
			map.put(
				"displayDate",
				liferayToJSONDateFormat.format(orderRule.getDisplayDate()));
		}

		if (orderRule.getExpirationDate() == null) {
			map.put("expirationDate", null);
		}
		else {
			map.put(
				"expirationDate",
				liferayToJSONDateFormat.format(orderRule.getExpirationDate()));
		}

		if (orderRule.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(orderRule.getExternalReferenceCode()));
		}

		if (orderRule.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(orderRule.getId()));
		}

		if (orderRule.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(orderRule.getName()));
		}

		if (orderRule.getNeverExpire() == null) {
			map.put("neverExpire", null);
		}
		else {
			map.put("neverExpire", String.valueOf(orderRule.getNeverExpire()));
		}

		if (orderRule.getOrderRuleAccount() == null) {
			map.put("orderRuleAccount", null);
		}
		else {
			map.put(
				"orderRuleAccount",
				String.valueOf(orderRule.getOrderRuleAccount()));
		}

		if (orderRule.getOrderRuleAccountGroup() == null) {
			map.put("orderRuleAccountGroup", null);
		}
		else {
			map.put(
				"orderRuleAccountGroup",
				String.valueOf(orderRule.getOrderRuleAccountGroup()));
		}

		if (orderRule.getOrderRuleChannel() == null) {
			map.put("orderRuleChannel", null);
		}
		else {
			map.put(
				"orderRuleChannel",
				String.valueOf(orderRule.getOrderRuleChannel()));
		}

		if (orderRule.getOrderRuleOrderType() == null) {
			map.put("orderRuleOrderType", null);
		}
		else {
			map.put(
				"orderRuleOrderType",
				String.valueOf(orderRule.getOrderRuleOrderType()));
		}

		if (orderRule.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(orderRule.getPriority()));
		}

		if (orderRule.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(orderRule.getType()));
		}

		if (orderRule.getTypeSettings() == null) {
			map.put("typeSettings", null);
		}
		else {
			map.put(
				"typeSettings", String.valueOf(orderRule.getTypeSettings()));
		}

		if (orderRule.getWorkflowStatusInfo() == null) {
			map.put("workflowStatusInfo", null);
		}
		else {
			map.put(
				"workflowStatusInfo",
				String.valueOf(orderRule.getWorkflowStatusInfo()));
		}

		return map;
	}

	public static class OrderRuleJSONParser extends BaseJSONParser<OrderRule> {

		@Override
		protected OrderRule createDTO() {
			return new OrderRule();
		}

		@Override
		protected OrderRule[] createDTOArray(int size) {
			return new OrderRule[size];
		}

		@Override
		protected void setField(
			OrderRule orderRule, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					orderRule.setActions(
						(Map)OrderRuleSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					orderRule.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "author")) {
				if (jsonParserFieldValue != null) {
					orderRule.setAuthor((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					orderRule.setCreateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					orderRule.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayDate")) {
				if (jsonParserFieldValue != null) {
					orderRule.setDisplayDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expirationDate")) {
				if (jsonParserFieldValue != null) {
					orderRule.setExpirationDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					orderRule.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					orderRule.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					orderRule.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "neverExpire")) {
				if (jsonParserFieldValue != null) {
					orderRule.setNeverExpire((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderRuleAccount")) {
				if (jsonParserFieldValue != null) {
					orderRule.setOrderRuleAccount(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OrderRuleAccountSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new OrderRuleAccount[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "orderRuleAccountGroup")) {

				if (jsonParserFieldValue != null) {
					orderRule.setOrderRuleAccountGroup(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OrderRuleAccountGroupSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new OrderRuleAccountGroup[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderRuleChannel")) {
				if (jsonParserFieldValue != null) {
					orderRule.setOrderRuleChannel(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OrderRuleChannelSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new OrderRuleChannel[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "orderRuleOrderType")) {

				if (jsonParserFieldValue != null) {
					orderRule.setOrderRuleOrderType(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OrderRuleOrderTypeSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new OrderRuleOrderType[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					orderRule.setPriority(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					orderRule.setType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "typeSettings")) {
				if (jsonParserFieldValue != null) {
					orderRule.setTypeSettings((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "workflowStatusInfo")) {

				if (jsonParserFieldValue != null) {
					orderRule.setWorkflowStatusInfo(
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