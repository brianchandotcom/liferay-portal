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

package com.liferay.headless.commerce.delivery.cart.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.client.json.BaseJSONParser;

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
public class CartItemSerDes {

	public static CartItem toDTO(String json) {
		CartItemJSONParser cartItemJSONParser = new CartItemJSONParser();

		return cartItemJSONParser.parseToDTO(json);
	}

	public static CartItem[] toDTOs(String json) {
		CartItemJSONParser cartItemJSONParser = new CartItemJSONParser();

		return cartItemJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CartItem cartItem) {
		if (cartItem == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (cartItem.getAdaptiveMediaImageHTMLTag() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"adaptiveMediaImageHTMLTag\": ");

			sb.append("\"");

			sb.append(_escape(cartItem.getAdaptiveMediaImageHTMLTag()));

			sb.append("\"");
		}

		if (cartItem.getCartItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cartItems\": ");

			sb.append("[");

			for (int i = 0; i < cartItem.getCartItems().length; i++) {
				sb.append(String.valueOf(cartItem.getCartItems()[i]));

				if ((i + 1) < cartItem.getCartItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (cartItem.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(cartItem.getCustomFields()));
		}

		if (cartItem.getErrorMessages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessages\": ");

			sb.append("[");

			for (int i = 0; i < cartItem.getErrorMessages().length; i++) {
				sb.append("\"");

				sb.append(_escape(cartItem.getErrorMessages()[i]));

				sb.append("\"");

				if ((i + 1) < cartItem.getErrorMessages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (cartItem.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(cartItem.getId());
		}

		if (cartItem.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(cartItem.getName()));

			sb.append("\"");
		}

		if (cartItem.getOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"options\": ");

			sb.append("\"");

			sb.append(_escape(cartItem.getOptions()));

			sb.append("\"");
		}

		if (cartItem.getParentCartItemId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentCartItemId\": ");

			sb.append(cartItem.getParentCartItemId());
		}

		if (cartItem.getPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(String.valueOf(cartItem.getPrice()));
		}

		if (cartItem.getProductId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(cartItem.getProductId());
		}

		if (cartItem.getProductURLs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productURLs\": ");

			sb.append(_toJSON(cartItem.getProductURLs()));
		}

		if (cartItem.getQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append(cartItem.getQuantity());
		}

		if (cartItem.getSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"settings\": ");

			sb.append(String.valueOf(cartItem.getSettings()));
		}

		if (cartItem.getSku() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append("\"");

			sb.append(_escape(cartItem.getSku()));

			sb.append("\"");
		}

		if (cartItem.getSkuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuId\": ");

			sb.append(cartItem.getSkuId());
		}

		if (cartItem.getSubscription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscription\": ");

			sb.append(cartItem.getSubscription());
		}

		if (cartItem.getThumbnail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"thumbnail\": ");

			sb.append("\"");

			sb.append(_escape(cartItem.getThumbnail()));

			sb.append("\"");
		}

		if (cartItem.getValid() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"valid\": ");

			sb.append(cartItem.getValid());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CartItemJSONParser cartItemJSONParser = new CartItemJSONParser();

		return cartItemJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CartItem cartItem) {
		if (cartItem == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (cartItem.getAdaptiveMediaImageHTMLTag() == null) {
			map.put("adaptiveMediaImageHTMLTag", null);
		}
		else {
			map.put(
				"adaptiveMediaImageHTMLTag",
				String.valueOf(cartItem.getAdaptiveMediaImageHTMLTag()));
		}

		if (cartItem.getCartItems() == null) {
			map.put("cartItems", null);
		}
		else {
			map.put("cartItems", String.valueOf(cartItem.getCartItems()));
		}

		if (cartItem.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put("customFields", String.valueOf(cartItem.getCustomFields()));
		}

		if (cartItem.getErrorMessages() == null) {
			map.put("errorMessages", null);
		}
		else {
			map.put(
				"errorMessages", String.valueOf(cartItem.getErrorMessages()));
		}

		if (cartItem.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(cartItem.getId()));
		}

		if (cartItem.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(cartItem.getName()));
		}

		if (cartItem.getOptions() == null) {
			map.put("options", null);
		}
		else {
			map.put("options", String.valueOf(cartItem.getOptions()));
		}

		if (cartItem.getParentCartItemId() == null) {
			map.put("parentCartItemId", null);
		}
		else {
			map.put(
				"parentCartItemId",
				String.valueOf(cartItem.getParentCartItemId()));
		}

		if (cartItem.getPrice() == null) {
			map.put("price", null);
		}
		else {
			map.put("price", String.valueOf(cartItem.getPrice()));
		}

		if (cartItem.getProductId() == null) {
			map.put("productId", null);
		}
		else {
			map.put("productId", String.valueOf(cartItem.getProductId()));
		}

		if (cartItem.getProductURLs() == null) {
			map.put("productURLs", null);
		}
		else {
			map.put("productURLs", String.valueOf(cartItem.getProductURLs()));
		}

		if (cartItem.getQuantity() == null) {
			map.put("quantity", null);
		}
		else {
			map.put("quantity", String.valueOf(cartItem.getQuantity()));
		}

		if (cartItem.getSettings() == null) {
			map.put("settings", null);
		}
		else {
			map.put("settings", String.valueOf(cartItem.getSettings()));
		}

		if (cartItem.getSku() == null) {
			map.put("sku", null);
		}
		else {
			map.put("sku", String.valueOf(cartItem.getSku()));
		}

		if (cartItem.getSkuId() == null) {
			map.put("skuId", null);
		}
		else {
			map.put("skuId", String.valueOf(cartItem.getSkuId()));
		}

		if (cartItem.getSubscription() == null) {
			map.put("subscription", null);
		}
		else {
			map.put("subscription", String.valueOf(cartItem.getSubscription()));
		}

		if (cartItem.getThumbnail() == null) {
			map.put("thumbnail", null);
		}
		else {
			map.put("thumbnail", String.valueOf(cartItem.getThumbnail()));
		}

		if (cartItem.getValid() == null) {
			map.put("valid", null);
		}
		else {
			map.put("valid", String.valueOf(cartItem.getValid()));
		}

		return map;
	}

	public static class CartItemJSONParser extends BaseJSONParser<CartItem> {

		@Override
		protected CartItem createDTO() {
			return new CartItem();
		}

		@Override
		protected CartItem[] createDTOArray(int size) {
			return new CartItem[size];
		}

		@Override
		protected void setField(
			CartItem cartItem, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "adaptiveMediaImageHTMLTag")) {

				if (jsonParserFieldValue != null) {
					cartItem.setAdaptiveMediaImageHTMLTag(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "cartItems")) {
				if (jsonParserFieldValue != null) {
					cartItem.setCartItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CartItemSerDes.toDTO((String)object)
						).toArray(
							size -> new CartItem[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					cartItem.setCustomFields(
						(Map)CartItemSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "errorMessages")) {
				if (jsonParserFieldValue != null) {
					cartItem.setErrorMessages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					cartItem.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					cartItem.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "options")) {
				if (jsonParserFieldValue != null) {
					cartItem.setOptions((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parentCartItemId")) {
				if (jsonParserFieldValue != null) {
					cartItem.setParentCartItemId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "price")) {
				if (jsonParserFieldValue != null) {
					cartItem.setPrice(
						PriceSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productId")) {
				if (jsonParserFieldValue != null) {
					cartItem.setProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productURLs")) {
				if (jsonParserFieldValue != null) {
					cartItem.setProductURLs(
						(Map)CartItemSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "quantity")) {
				if (jsonParserFieldValue != null) {
					cartItem.setQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "settings")) {
				if (jsonParserFieldValue != null) {
					cartItem.setSettings(
						SettingsSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sku")) {
				if (jsonParserFieldValue != null) {
					cartItem.setSku((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuId")) {
				if (jsonParserFieldValue != null) {
					cartItem.setSkuId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subscription")) {
				if (jsonParserFieldValue != null) {
					cartItem.setSubscription((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "thumbnail")) {
				if (jsonParserFieldValue != null) {
					cartItem.setThumbnail((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "valid")) {
				if (jsonParserFieldValue != null) {
					cartItem.setValid((Boolean)jsonParserFieldValue);
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