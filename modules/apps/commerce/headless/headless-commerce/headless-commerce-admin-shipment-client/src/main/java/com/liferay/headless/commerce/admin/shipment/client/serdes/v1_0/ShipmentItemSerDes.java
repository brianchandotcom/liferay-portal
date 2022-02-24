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

package com.liferay.headless.commerce.admin.shipment.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.shipment.client.dto.v1_0.ShipmentItem;
import com.liferay.headless.commerce.admin.shipment.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class ShipmentItemSerDes {

	public static ShipmentItem toDTO(String json) {
		ShipmentItemJSONParser shipmentItemJSONParser =
			new ShipmentItemJSONParser();

		return shipmentItemJSONParser.parseToDTO(json);
	}

	public static ShipmentItem[] toDTOs(String json) {
		ShipmentItemJSONParser shipmentItemJSONParser =
			new ShipmentItemJSONParser();

		return shipmentItemJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ShipmentItem shipmentItem) {
		if (shipmentItem == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (shipmentItem.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(shipmentItem.getActions()));
		}

		if (shipmentItem.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(shipmentItem.getCreateDate()));

			sb.append("\"");
		}

		if (shipmentItem.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(shipmentItem.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (shipmentItem.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(shipmentItem.getId());
		}

		if (shipmentItem.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(shipmentItem.getModifiedDate()));

			sb.append("\"");
		}

		if (shipmentItem.getOrderItemId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderItemId\": ");

			sb.append(shipmentItem.getOrderItemId());
		}

		if (shipmentItem.getQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append(shipmentItem.getQuantity());
		}

		if (shipmentItem.getShipmentExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shipmentExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(shipmentItem.getShipmentExternalReferenceCode()));

			sb.append("\"");
		}

		if (shipmentItem.getShipmentId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shipmentId\": ");

			sb.append(shipmentItem.getShipmentId());
		}

		if (shipmentItem.getUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(shipmentItem.getUserName()));

			sb.append("\"");
		}

		if (shipmentItem.getWarehouseId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"warehouseId\": ");

			sb.append(shipmentItem.getWarehouseId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ShipmentItemJSONParser shipmentItemJSONParser =
			new ShipmentItemJSONParser();

		return shipmentItemJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ShipmentItem shipmentItem) {
		if (shipmentItem == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (shipmentItem.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(shipmentItem.getActions()));
		}

		if (shipmentItem.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(shipmentItem.getCreateDate()));
		}

		if (shipmentItem.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(shipmentItem.getExternalReferenceCode()));
		}

		if (shipmentItem.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(shipmentItem.getId()));
		}

		if (shipmentItem.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(shipmentItem.getModifiedDate()));
		}

		if (shipmentItem.getOrderItemId() == null) {
			map.put("orderItemId", null);
		}
		else {
			map.put(
				"orderItemId", String.valueOf(shipmentItem.getOrderItemId()));
		}

		if (shipmentItem.getQuantity() == null) {
			map.put("quantity", null);
		}
		else {
			map.put("quantity", String.valueOf(shipmentItem.getQuantity()));
		}

		if (shipmentItem.getShipmentExternalReferenceCode() == null) {
			map.put("shipmentExternalReferenceCode", null);
		}
		else {
			map.put(
				"shipmentExternalReferenceCode",
				String.valueOf(
					shipmentItem.getShipmentExternalReferenceCode()));
		}

		if (shipmentItem.getShipmentId() == null) {
			map.put("shipmentId", null);
		}
		else {
			map.put("shipmentId", String.valueOf(shipmentItem.getShipmentId()));
		}

		if (shipmentItem.getUserName() == null) {
			map.put("userName", null);
		}
		else {
			map.put("userName", String.valueOf(shipmentItem.getUserName()));
		}

		if (shipmentItem.getWarehouseId() == null) {
			map.put("warehouseId", null);
		}
		else {
			map.put(
				"warehouseId", String.valueOf(shipmentItem.getWarehouseId()));
		}

		return map;
	}

	public static class ShipmentItemJSONParser
		extends BaseJSONParser<ShipmentItem> {

		@Override
		protected ShipmentItem createDTO() {
			return new ShipmentItem();
		}

		@Override
		protected ShipmentItem[] createDTOArray(int size) {
			return new ShipmentItem[size];
		}

		@Override
		protected void setField(
			ShipmentItem shipmentItem, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					shipmentItem.setActions(
						(Map)ShipmentItemSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					shipmentItem.setCreateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					shipmentItem.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					shipmentItem.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					shipmentItem.setModifiedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderItemId")) {
				if (jsonParserFieldValue != null) {
					shipmentItem.setOrderItemId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "quantity")) {
				if (jsonParserFieldValue != null) {
					shipmentItem.setQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "shipmentExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					shipmentItem.setShipmentExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shipmentId")) {
				if (jsonParserFieldValue != null) {
					shipmentItem.setShipmentId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userName")) {
				if (jsonParserFieldValue != null) {
					shipmentItem.setUserName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "warehouseId")) {
				if (jsonParserFieldValue != null) {
					shipmentItem.setWarehouseId(
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