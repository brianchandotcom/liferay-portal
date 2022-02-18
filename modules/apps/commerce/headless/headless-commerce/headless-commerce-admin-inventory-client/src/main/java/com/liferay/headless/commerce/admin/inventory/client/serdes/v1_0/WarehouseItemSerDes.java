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

package com.liferay.headless.commerce.admin.inventory.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.WarehouseItem;
import com.liferay.headless.commerce.admin.inventory.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class WarehouseItemSerDes {

	public static WarehouseItem toDTO(String json) {
		WarehouseItemJSONParser warehouseItemJSONParser =
			new WarehouseItemJSONParser();

		return warehouseItemJSONParser.parseToDTO(json);
	}

	public static WarehouseItem[] toDTOs(String json) {
		WarehouseItemJSONParser warehouseItemJSONParser =
			new WarehouseItemJSONParser();

		return warehouseItemJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WarehouseItem warehouseItem) {
		if (warehouseItem == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (warehouseItem.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(warehouseItem.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (warehouseItem.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(warehouseItem.getId());
		}

		if (warehouseItem.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					warehouseItem.getModifiedDate()));

			sb.append("\"");
		}

		if (warehouseItem.getQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append(warehouseItem.getQuantity());
		}

		if (warehouseItem.getReservedQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reservedQuantity\": ");

			sb.append(warehouseItem.getReservedQuantity());
		}

		if (warehouseItem.getSku() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append("\"");

			sb.append(_escape(warehouseItem.getSku()));

			sb.append("\"");
		}

		if (warehouseItem.getWarehouseExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"warehouseExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(warehouseItem.getWarehouseExternalReferenceCode()));

			sb.append("\"");
		}

		if (warehouseItem.getWarehouseId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"warehouseId\": ");

			sb.append(warehouseItem.getWarehouseId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WarehouseItemJSONParser warehouseItemJSONParser =
			new WarehouseItemJSONParser();

		return warehouseItemJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(WarehouseItem warehouseItem) {
		if (warehouseItem == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (warehouseItem.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(warehouseItem.getExternalReferenceCode()));
		}

		if (warehouseItem.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(warehouseItem.getId()));
		}

		if (warehouseItem.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(
					warehouseItem.getModifiedDate()));
		}

		if (warehouseItem.getQuantity() == null) {
			map.put("quantity", null);
		}
		else {
			map.put("quantity", String.valueOf(warehouseItem.getQuantity()));
		}

		if (warehouseItem.getReservedQuantity() == null) {
			map.put("reservedQuantity", null);
		}
		else {
			map.put(
				"reservedQuantity",
				String.valueOf(warehouseItem.getReservedQuantity()));
		}

		if (warehouseItem.getSku() == null) {
			map.put("sku", null);
		}
		else {
			map.put("sku", String.valueOf(warehouseItem.getSku()));
		}

		if (warehouseItem.getWarehouseExternalReferenceCode() == null) {
			map.put("warehouseExternalReferenceCode", null);
		}
		else {
			map.put(
				"warehouseExternalReferenceCode",
				String.valueOf(
					warehouseItem.getWarehouseExternalReferenceCode()));
		}

		if (warehouseItem.getWarehouseId() == null) {
			map.put("warehouseId", null);
		}
		else {
			map.put(
				"warehouseId", String.valueOf(warehouseItem.getWarehouseId()));
		}

		return map;
	}

	public static class WarehouseItemJSONParser
		extends BaseJSONParser<WarehouseItem> {

		@Override
		protected WarehouseItem createDTO() {
			return new WarehouseItem();
		}

		@Override
		protected WarehouseItem[] createDTOArray(int size) {
			return new WarehouseItem[size];
		}

		@Override
		protected void setField(
			WarehouseItem warehouseItem, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "externalReferenceCode")) {
				if (jsonParserFieldValue != null) {
					warehouseItem.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					warehouseItem.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					warehouseItem.setModifiedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "quantity")) {
				if (jsonParserFieldValue != null) {
					warehouseItem.setQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "reservedQuantity")) {
				if (jsonParserFieldValue != null) {
					warehouseItem.setReservedQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sku")) {
				if (jsonParserFieldValue != null) {
					warehouseItem.setSku((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"warehouseExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					warehouseItem.setWarehouseExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "warehouseId")) {
				if (jsonParserFieldValue != null) {
					warehouseItem.setWarehouseId(
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