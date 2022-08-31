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

import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.WarehouseOrderType;
import com.liferay.headless.commerce.admin.inventory.client.json.BaseJSONParser;

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
public class WarehouseOrderTypeSerDes {

	public static WarehouseOrderType toDTO(String json) {
		WarehouseOrderTypeJSONParser warehouseOrderTypeJSONParser =
			new WarehouseOrderTypeJSONParser();

		return warehouseOrderTypeJSONParser.parseToDTO(json);
	}

	public static WarehouseOrderType[] toDTOs(String json) {
		WarehouseOrderTypeJSONParser warehouseOrderTypeJSONParser =
			new WarehouseOrderTypeJSONParser();

		return warehouseOrderTypeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WarehouseOrderType warehouseOrderType) {
		if (warehouseOrderType == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (warehouseOrderType.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(warehouseOrderType.getActions()));
		}

		if (warehouseOrderType.getOrderType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderType\": ");

			sb.append(String.valueOf(warehouseOrderType.getOrderType()));
		}

		if (warehouseOrderType.getOrderTypeExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderTypeExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					warehouseOrderType.getOrderTypeExternalReferenceCode()));

			sb.append("\"");
		}

		if (warehouseOrderType.getOrderTypeId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderTypeId\": ");

			sb.append(warehouseOrderType.getOrderTypeId());
		}

		if (warehouseOrderType.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(warehouseOrderType.getPriority());
		}

		if (warehouseOrderType.getWarehouseExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"warehouseExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					warehouseOrderType.getWarehouseExternalReferenceCode()));

			sb.append("\"");
		}

		if (warehouseOrderType.getWarehouseId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"warehouseId\": ");

			sb.append(warehouseOrderType.getWarehouseId());
		}

		if (warehouseOrderType.getWarehouseOrderTypeId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"warehouseOrderTypeId\": ");

			sb.append(warehouseOrderType.getWarehouseOrderTypeId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WarehouseOrderTypeJSONParser warehouseOrderTypeJSONParser =
			new WarehouseOrderTypeJSONParser();

		return warehouseOrderTypeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		WarehouseOrderType warehouseOrderType) {

		if (warehouseOrderType == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (warehouseOrderType.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(warehouseOrderType.getActions()));
		}

		if (warehouseOrderType.getOrderType() == null) {
			map.put("orderType", null);
		}
		else {
			map.put(
				"orderType", String.valueOf(warehouseOrderType.getOrderType()));
		}

		if (warehouseOrderType.getOrderTypeExternalReferenceCode() == null) {
			map.put("orderTypeExternalReferenceCode", null);
		}
		else {
			map.put(
				"orderTypeExternalReferenceCode",
				String.valueOf(
					warehouseOrderType.getOrderTypeExternalReferenceCode()));
		}

		if (warehouseOrderType.getOrderTypeId() == null) {
			map.put("orderTypeId", null);
		}
		else {
			map.put(
				"orderTypeId",
				String.valueOf(warehouseOrderType.getOrderTypeId()));
		}

		if (warehouseOrderType.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put(
				"priority", String.valueOf(warehouseOrderType.getPriority()));
		}

		if (warehouseOrderType.getWarehouseExternalReferenceCode() == null) {
			map.put("warehouseExternalReferenceCode", null);
		}
		else {
			map.put(
				"warehouseExternalReferenceCode",
				String.valueOf(
					warehouseOrderType.getWarehouseExternalReferenceCode()));
		}

		if (warehouseOrderType.getWarehouseId() == null) {
			map.put("warehouseId", null);
		}
		else {
			map.put(
				"warehouseId",
				String.valueOf(warehouseOrderType.getWarehouseId()));
		}

		if (warehouseOrderType.getWarehouseOrderTypeId() == null) {
			map.put("warehouseOrderTypeId", null);
		}
		else {
			map.put(
				"warehouseOrderTypeId",
				String.valueOf(warehouseOrderType.getWarehouseOrderTypeId()));
		}

		return map;
	}

	public static class WarehouseOrderTypeJSONParser
		extends BaseJSONParser<WarehouseOrderType> {

		@Override
		protected WarehouseOrderType createDTO() {
			return new WarehouseOrderType();
		}

		@Override
		protected WarehouseOrderType[] createDTOArray(int size) {
			return new WarehouseOrderType[size];
		}

		@Override
		protected void setField(
			WarehouseOrderType warehouseOrderType, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					warehouseOrderType.setActions(
						(Map)WarehouseOrderTypeSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderType")) {
				if (jsonParserFieldValue != null) {
					warehouseOrderType.setOrderType(
						OrderTypeSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"orderTypeExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					warehouseOrderType.setOrderTypeExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderTypeId")) {
				if (jsonParserFieldValue != null) {
					warehouseOrderType.setOrderTypeId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					warehouseOrderType.setPriority(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"warehouseExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					warehouseOrderType.setWarehouseExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "warehouseId")) {
				if (jsonParserFieldValue != null) {
					warehouseOrderType.setWarehouseId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "warehouseOrderTypeId")) {

				if (jsonParserFieldValue != null) {
					warehouseOrderType.setWarehouseOrderTypeId(
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