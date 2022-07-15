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

package com.liferay.headless.commerce.delivery.order.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.order.client.dto.v1_0.PlacedOrderItem;
import com.liferay.headless.commerce.delivery.order.client.dto.v1_0.PlacedOrderItemShipment;
import com.liferay.headless.commerce.delivery.order.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class PlacedOrderItemSerDes {

	public static PlacedOrderItem toDTO(String json) {
		PlacedOrderItemJSONParser placedOrderItemJSONParser =
			new PlacedOrderItemJSONParser();

		return placedOrderItemJSONParser.parseToDTO(json);
	}

	public static PlacedOrderItem[] toDTOs(String json) {
		PlacedOrderItemJSONParser placedOrderItemJSONParser =
			new PlacedOrderItemJSONParser();

		return placedOrderItemJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PlacedOrderItem placedOrderItem) {
		if (placedOrderItem == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (placedOrderItem.getAdaptiveMediaImageHTMLTag() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"adaptiveMediaImageHTMLTag\": ");

			sb.append("\"");

			sb.append(_escape(placedOrderItem.getAdaptiveMediaImageHTMLTag()));

			sb.append("\"");
		}

		if (placedOrderItem.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(placedOrderItem.getCustomFields()));
		}

		if (placedOrderItem.getErrorMessages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessages\": ");

			sb.append("[");

			for (int i = 0; i < placedOrderItem.getErrorMessages().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(placedOrderItem.getErrorMessages()[i]));

				sb.append("\"");

				if ((i + 1) < placedOrderItem.getErrorMessages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (placedOrderItem.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(placedOrderItem.getId());
		}

		if (placedOrderItem.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(placedOrderItem.getName()));

			sb.append("\"");
		}

		if (placedOrderItem.getOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"options\": ");

			sb.append("\"");

			sb.append(_escape(placedOrderItem.getOptions()));

			sb.append("\"");
		}

		if (placedOrderItem.getParentOrderItemId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentOrderItemId\": ");

			sb.append(placedOrderItem.getParentOrderItemId());
		}

		if (placedOrderItem.getPlacedOrderItemShipments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"placedOrderItemShipments\": ");

			sb.append("[");

			for (int i = 0;
				 i < placedOrderItem.getPlacedOrderItemShipments().length;
				 i++) {

				sb.append(
					String.valueOf(
						placedOrderItem.getPlacedOrderItemShipments()[i]));

				if ((i + 1) <
						placedOrderItem.getPlacedOrderItemShipments().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (placedOrderItem.getPlacedOrderItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"placedOrderItems\": ");

			sb.append("[");

			for (int i = 0; i < placedOrderItem.getPlacedOrderItems().length;
				 i++) {

				sb.append(
					String.valueOf(placedOrderItem.getPlacedOrderItems()[i]));

				if ((i + 1) < placedOrderItem.getPlacedOrderItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (placedOrderItem.getPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(String.valueOf(placedOrderItem.getPrice()));
		}

		if (placedOrderItem.getProductId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(placedOrderItem.getProductId());
		}

		if (placedOrderItem.getProductURLs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productURLs\": ");

			sb.append(_toJSON(placedOrderItem.getProductURLs()));
		}

		if (placedOrderItem.getQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append(placedOrderItem.getQuantity());
		}

		if (placedOrderItem.getSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"settings\": ");

			sb.append(String.valueOf(placedOrderItem.getSettings()));
		}

		if (placedOrderItem.getSku() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append("\"");

			sb.append(_escape(placedOrderItem.getSku()));

			sb.append("\"");
		}

		if (placedOrderItem.getSkuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuId\": ");

			sb.append(placedOrderItem.getSkuId());
		}

		if (placedOrderItem.getSubscription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscription\": ");

			sb.append(placedOrderItem.getSubscription());
		}

		if (placedOrderItem.getThumbnail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"thumbnail\": ");

			sb.append("\"");

			sb.append(_escape(placedOrderItem.getThumbnail()));

			sb.append("\"");
		}

		if (placedOrderItem.getValid() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"valid\": ");

			sb.append(placedOrderItem.getValid());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PlacedOrderItemJSONParser placedOrderItemJSONParser =
			new PlacedOrderItemJSONParser();

		return placedOrderItemJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PlacedOrderItem placedOrderItem) {
		if (placedOrderItem == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (placedOrderItem.getAdaptiveMediaImageHTMLTag() == null) {
			map.put("adaptiveMediaImageHTMLTag", null);
		}
		else {
			map.put(
				"adaptiveMediaImageHTMLTag",
				String.valueOf(placedOrderItem.getAdaptiveMediaImageHTMLTag()));
		}

		if (placedOrderItem.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields",
				String.valueOf(placedOrderItem.getCustomFields()));
		}

		if (placedOrderItem.getErrorMessages() == null) {
			map.put("errorMessages", null);
		}
		else {
			map.put(
				"errorMessages",
				String.valueOf(placedOrderItem.getErrorMessages()));
		}

		if (placedOrderItem.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(placedOrderItem.getId()));
		}

		if (placedOrderItem.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(placedOrderItem.getName()));
		}

		if (placedOrderItem.getOptions() == null) {
			map.put("options", null);
		}
		else {
			map.put("options", String.valueOf(placedOrderItem.getOptions()));
		}

		if (placedOrderItem.getParentOrderItemId() == null) {
			map.put("parentOrderItemId", null);
		}
		else {
			map.put(
				"parentOrderItemId",
				String.valueOf(placedOrderItem.getParentOrderItemId()));
		}

		if (placedOrderItem.getPlacedOrderItemShipments() == null) {
			map.put("placedOrderItemShipments", null);
		}
		else {
			map.put(
				"placedOrderItemShipments",
				String.valueOf(placedOrderItem.getPlacedOrderItemShipments()));
		}

		if (placedOrderItem.getPlacedOrderItems() == null) {
			map.put("placedOrderItems", null);
		}
		else {
			map.put(
				"placedOrderItems",
				String.valueOf(placedOrderItem.getPlacedOrderItems()));
		}

		if (placedOrderItem.getPrice() == null) {
			map.put("price", null);
		}
		else {
			map.put("price", String.valueOf(placedOrderItem.getPrice()));
		}

		if (placedOrderItem.getProductId() == null) {
			map.put("productId", null);
		}
		else {
			map.put(
				"productId", String.valueOf(placedOrderItem.getProductId()));
		}

		if (placedOrderItem.getProductURLs() == null) {
			map.put("productURLs", null);
		}
		else {
			map.put(
				"productURLs",
				String.valueOf(placedOrderItem.getProductURLs()));
		}

		if (placedOrderItem.getQuantity() == null) {
			map.put("quantity", null);
		}
		else {
			map.put("quantity", String.valueOf(placedOrderItem.getQuantity()));
		}

		if (placedOrderItem.getSettings() == null) {
			map.put("settings", null);
		}
		else {
			map.put("settings", String.valueOf(placedOrderItem.getSettings()));
		}

		if (placedOrderItem.getSku() == null) {
			map.put("sku", null);
		}
		else {
			map.put("sku", String.valueOf(placedOrderItem.getSku()));
		}

		if (placedOrderItem.getSkuId() == null) {
			map.put("skuId", null);
		}
		else {
			map.put("skuId", String.valueOf(placedOrderItem.getSkuId()));
		}

		if (placedOrderItem.getSubscription() == null) {
			map.put("subscription", null);
		}
		else {
			map.put(
				"subscription",
				String.valueOf(placedOrderItem.getSubscription()));
		}

		if (placedOrderItem.getThumbnail() == null) {
			map.put("thumbnail", null);
		}
		else {
			map.put(
				"thumbnail", String.valueOf(placedOrderItem.getThumbnail()));
		}

		if (placedOrderItem.getValid() == null) {
			map.put("valid", null);
		}
		else {
			map.put("valid", String.valueOf(placedOrderItem.getValid()));
		}

		return map;
	}

	public static class PlacedOrderItemJSONParser
		extends BaseJSONParser<PlacedOrderItem> {

		@Override
		protected PlacedOrderItem createDTO() {
			return new PlacedOrderItem();
		}

		@Override
		protected PlacedOrderItem[] createDTOArray(int size) {
			return new PlacedOrderItem[size];
		}

		@Override
		protected void setField(
			PlacedOrderItem placedOrderItem, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "adaptiveMediaImageHTMLTag")) {

				if (jsonParserFieldValue != null) {
					placedOrderItem.setAdaptiveMediaImageHTMLTag(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setCustomFields(
						(Map)PlacedOrderItemSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "errorMessages")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setErrorMessages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "options")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setOptions((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parentOrderItemId")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setParentOrderItemId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "placedOrderItemShipments")) {

				if (jsonParserFieldValue != null) {
					placedOrderItem.setPlacedOrderItemShipments(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PlacedOrderItemShipmentSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new PlacedOrderItemShipment[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "placedOrderItems")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setPlacedOrderItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PlacedOrderItemSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new PlacedOrderItem[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "price")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setPrice(
						PriceSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productId")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productURLs")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setProductURLs(
						(Map)PlacedOrderItemSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "quantity")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "settings")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setSettings(
						SettingsSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sku")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setSku((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuId")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setSkuId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subscription")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setSubscription(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "thumbnail")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setThumbnail((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "valid")) {
				if (jsonParserFieldValue != null) {
					placedOrderItem.setValid((Boolean)jsonParserFieldValue);
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