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

import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CartComment;
import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.client.json.BaseJSONParser;

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
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class CartSerDes {

	public static Cart toDTO(String json) {
		CartJSONParser cartJSONParser = new CartJSONParser();

		return cartJSONParser.parseToDTO(json);
	}

	public static Cart[] toDTOs(String json) {
		CartJSONParser cartJSONParser = new CartJSONParser();

		return cartJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Cart cart) {
		if (cart == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (cart.getAccount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"account\": ");

			sb.append("\"");

			sb.append(_escape(cart.getAccount()));

			sb.append("\"");
		}

		if (cart.getAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(cart.getAccountId());
		}

		if (cart.getAuthor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"author\": ");

			sb.append("\"");

			sb.append(_escape(cart.getAuthor()));

			sb.append("\"");
		}

		if (cart.getBillingAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"billingAddress\": ");

			sb.append(String.valueOf(cart.getBillingAddress()));
		}

		if (cart.getBillingAddressId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"billingAddressId\": ");

			sb.append(cart.getBillingAddressId());
		}

		if (cart.getCartItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cartItems\": ");

			sb.append("[");

			for (int i = 0; i < cart.getCartItems().length; i++) {
				sb.append(String.valueOf(cart.getCartItems()[i]));

				if ((i + 1) < cart.getCartItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (cart.getChannelId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelId\": ");

			sb.append(cart.getChannelId());
		}

		if (cart.getCouponCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"couponCode\": ");

			sb.append("\"");

			sb.append(_escape(cart.getCouponCode()));

			sb.append("\"");
		}

		if (cart.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(cart.getCreateDate()));

			sb.append("\"");
		}

		if (cart.getCurrencyCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"currencyCode\": ");

			sb.append("\"");

			sb.append(_escape(cart.getCurrencyCode()));

			sb.append("\"");
		}

		if (cart.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(cart.getCustomFields()));
		}

		if (cart.getErrorMessages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessages\": ");

			sb.append("[");

			for (int i = 0; i < cart.getErrorMessages().length; i++) {
				sb.append("\"");

				sb.append(_escape(cart.getErrorMessages()[i]));

				sb.append("\"");

				if ((i + 1) < cart.getErrorMessages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (cart.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(cart.getId());
		}

		if (cart.getLastPriceUpdateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastPriceUpdateDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(cart.getLastPriceUpdateDate()));

			sb.append("\"");
		}

		if (cart.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(cart.getModifiedDate()));

			sb.append("\"");
		}

		if (cart.getNotes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"notes\": ");

			sb.append("[");

			for (int i = 0; i < cart.getNotes().length; i++) {
				sb.append(String.valueOf(cart.getNotes()[i]));

				if ((i + 1) < cart.getNotes().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (cart.getOrderStatusInfo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderStatusInfo\": ");

			sb.append(String.valueOf(cart.getOrderStatusInfo()));
		}

		if (cart.getOrderTypeExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderTypeExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(cart.getOrderTypeExternalReferenceCode()));

			sb.append("\"");
		}

		if (cart.getOrderTypeId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderTypeId\": ");

			sb.append(cart.getOrderTypeId());
		}

		if (cart.getOrderUUID() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderUUID\": ");

			sb.append("\"");

			sb.append(_escape(cart.getOrderUUID()));

			sb.append("\"");
		}

		if (cart.getPaymentMethod() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentMethod\": ");

			sb.append("\"");

			sb.append(_escape(cart.getPaymentMethod()));

			sb.append("\"");
		}

		if (cart.getPaymentMethodLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentMethodLabel\": ");

			sb.append("\"");

			sb.append(_escape(cart.getPaymentMethodLabel()));

			sb.append("\"");
		}

		if (cart.getPaymentStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentStatus\": ");

			sb.append(cart.getPaymentStatus());
		}

		if (cart.getPaymentStatusInfo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentStatusInfo\": ");

			sb.append(String.valueOf(cart.getPaymentStatusInfo()));
		}

		if (cart.getPaymentStatusLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentStatusLabel\": ");

			sb.append("\"");

			sb.append(_escape(cart.getPaymentStatusLabel()));

			sb.append("\"");
		}

		if (cart.getPrintedNote() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"printedNote\": ");

			sb.append("\"");

			sb.append(_escape(cart.getPrintedNote()));

			sb.append("\"");
		}

		if (cart.getPurchaseOrderNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"purchaseOrderNumber\": ");

			sb.append("\"");

			sb.append(_escape(cart.getPurchaseOrderNumber()));

			sb.append("\"");
		}

		if (cart.getShippingAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAddress\": ");

			sb.append(String.valueOf(cart.getShippingAddress()));
		}

		if (cart.getShippingAddressId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAddressId\": ");

			sb.append(cart.getShippingAddressId());
		}

		if (cart.getShippingMethod() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingMethod\": ");

			sb.append("\"");

			sb.append(_escape(cart.getShippingMethod()));

			sb.append("\"");
		}

		if (cart.getShippingOption() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingOption\": ");

			sb.append("\"");

			sb.append(_escape(cart.getShippingOption()));

			sb.append("\"");
		}

		if (cart.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(cart.getStatus()));

			sb.append("\"");
		}

		if (cart.getSummary() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"summary\": ");

			sb.append(String.valueOf(cart.getSummary()));
		}

		if (cart.getUseAsBilling() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"useAsBilling\": ");

			sb.append(cart.getUseAsBilling());
		}

		if (cart.getValid() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"valid\": ");

			sb.append(cart.getValid());
		}

		if (cart.getWorkflowStatusInfo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowStatusInfo\": ");

			sb.append(String.valueOf(cart.getWorkflowStatusInfo()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CartJSONParser cartJSONParser = new CartJSONParser();

		return cartJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Cart cart) {
		if (cart == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (cart.getAccount() == null) {
			map.put("account", null);
		}
		else {
			map.put("account", String.valueOf(cart.getAccount()));
		}

		if (cart.getAccountId() == null) {
			map.put("accountId", null);
		}
		else {
			map.put("accountId", String.valueOf(cart.getAccountId()));
		}

		if (cart.getAuthor() == null) {
			map.put("author", null);
		}
		else {
			map.put("author", String.valueOf(cart.getAuthor()));
		}

		if (cart.getBillingAddress() == null) {
			map.put("billingAddress", null);
		}
		else {
			map.put("billingAddress", String.valueOf(cart.getBillingAddress()));
		}

		if (cart.getBillingAddressId() == null) {
			map.put("billingAddressId", null);
		}
		else {
			map.put(
				"billingAddressId", String.valueOf(cart.getBillingAddressId()));
		}

		if (cart.getCartItems() == null) {
			map.put("cartItems", null);
		}
		else {
			map.put("cartItems", String.valueOf(cart.getCartItems()));
		}

		if (cart.getChannelId() == null) {
			map.put("channelId", null);
		}
		else {
			map.put("channelId", String.valueOf(cart.getChannelId()));
		}

		if (cart.getCouponCode() == null) {
			map.put("couponCode", null);
		}
		else {
			map.put("couponCode", String.valueOf(cart.getCouponCode()));
		}

		if (cart.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(cart.getCreateDate()));
		}

		if (cart.getCurrencyCode() == null) {
			map.put("currencyCode", null);
		}
		else {
			map.put("currencyCode", String.valueOf(cart.getCurrencyCode()));
		}

		if (cart.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put("customFields", String.valueOf(cart.getCustomFields()));
		}

		if (cart.getErrorMessages() == null) {
			map.put("errorMessages", null);
		}
		else {
			map.put("errorMessages", String.valueOf(cart.getErrorMessages()));
		}

		if (cart.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(cart.getId()));
		}

		if (cart.getLastPriceUpdateDate() == null) {
			map.put("lastPriceUpdateDate", null);
		}
		else {
			map.put(
				"lastPriceUpdateDate",
				liferayToJSONDateFormat.format(cart.getLastPriceUpdateDate()));
		}

		if (cart.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(cart.getModifiedDate()));
		}

		if (cart.getNotes() == null) {
			map.put("notes", null);
		}
		else {
			map.put("notes", String.valueOf(cart.getNotes()));
		}

		if (cart.getOrderStatusInfo() == null) {
			map.put("orderStatusInfo", null);
		}
		else {
			map.put(
				"orderStatusInfo", String.valueOf(cart.getOrderStatusInfo()));
		}

		if (cart.getOrderTypeExternalReferenceCode() == null) {
			map.put("orderTypeExternalReferenceCode", null);
		}
		else {
			map.put(
				"orderTypeExternalReferenceCode",
				String.valueOf(cart.getOrderTypeExternalReferenceCode()));
		}

		if (cart.getOrderTypeId() == null) {
			map.put("orderTypeId", null);
		}
		else {
			map.put("orderTypeId", String.valueOf(cart.getOrderTypeId()));
		}

		if (cart.getOrderUUID() == null) {
			map.put("orderUUID", null);
		}
		else {
			map.put("orderUUID", String.valueOf(cart.getOrderUUID()));
		}

		if (cart.getPaymentMethod() == null) {
			map.put("paymentMethod", null);
		}
		else {
			map.put("paymentMethod", String.valueOf(cart.getPaymentMethod()));
		}

		if (cart.getPaymentMethodLabel() == null) {
			map.put("paymentMethodLabel", null);
		}
		else {
			map.put(
				"paymentMethodLabel",
				String.valueOf(cart.getPaymentMethodLabel()));
		}

		if (cart.getPaymentStatus() == null) {
			map.put("paymentStatus", null);
		}
		else {
			map.put("paymentStatus", String.valueOf(cart.getPaymentStatus()));
		}

		if (cart.getPaymentStatusInfo() == null) {
			map.put("paymentStatusInfo", null);
		}
		else {
			map.put(
				"paymentStatusInfo",
				String.valueOf(cart.getPaymentStatusInfo()));
		}

		if (cart.getPaymentStatusLabel() == null) {
			map.put("paymentStatusLabel", null);
		}
		else {
			map.put(
				"paymentStatusLabel",
				String.valueOf(cart.getPaymentStatusLabel()));
		}

		if (cart.getPrintedNote() == null) {
			map.put("printedNote", null);
		}
		else {
			map.put("printedNote", String.valueOf(cart.getPrintedNote()));
		}

		if (cart.getPurchaseOrderNumber() == null) {
			map.put("purchaseOrderNumber", null);
		}
		else {
			map.put(
				"purchaseOrderNumber",
				String.valueOf(cart.getPurchaseOrderNumber()));
		}

		if (cart.getShippingAddress() == null) {
			map.put("shippingAddress", null);
		}
		else {
			map.put(
				"shippingAddress", String.valueOf(cart.getShippingAddress()));
		}

		if (cart.getShippingAddressId() == null) {
			map.put("shippingAddressId", null);
		}
		else {
			map.put(
				"shippingAddressId",
				String.valueOf(cart.getShippingAddressId()));
		}

		if (cart.getShippingMethod() == null) {
			map.put("shippingMethod", null);
		}
		else {
			map.put("shippingMethod", String.valueOf(cart.getShippingMethod()));
		}

		if (cart.getShippingOption() == null) {
			map.put("shippingOption", null);
		}
		else {
			map.put("shippingOption", String.valueOf(cart.getShippingOption()));
		}

		if (cart.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(cart.getStatus()));
		}

		if (cart.getSummary() == null) {
			map.put("summary", null);
		}
		else {
			map.put("summary", String.valueOf(cart.getSummary()));
		}

		if (cart.getUseAsBilling() == null) {
			map.put("useAsBilling", null);
		}
		else {
			map.put("useAsBilling", String.valueOf(cart.getUseAsBilling()));
		}

		if (cart.getValid() == null) {
			map.put("valid", null);
		}
		else {
			map.put("valid", String.valueOf(cart.getValid()));
		}

		if (cart.getWorkflowStatusInfo() == null) {
			map.put("workflowStatusInfo", null);
		}
		else {
			map.put(
				"workflowStatusInfo",
				String.valueOf(cart.getWorkflowStatusInfo()));
		}

		return map;
	}

	public static class CartJSONParser extends BaseJSONParser<Cart> {

		@Override
		protected Cart createDTO() {
			return new Cart();
		}

		@Override
		protected Cart[] createDTOArray(int size) {
			return new Cart[size];
		}

		@Override
		protected void setField(
			Cart cart, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "account")) {
				if (jsonParserFieldValue != null) {
					cart.setAccount((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountId")) {
				if (jsonParserFieldValue != null) {
					cart.setAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "author")) {
				if (jsonParserFieldValue != null) {
					cart.setAuthor((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "billingAddress")) {
				if (jsonParserFieldValue != null) {
					cart.setBillingAddress(
						AddressSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "billingAddressId")) {
				if (jsonParserFieldValue != null) {
					cart.setBillingAddressId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "cartItems")) {
				if (jsonParserFieldValue != null) {
					cart.setCartItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CartItemSerDes.toDTO((String)object)
						).toArray(
							size -> new CartItem[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelId")) {
				if (jsonParserFieldValue != null) {
					cart.setChannelId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "couponCode")) {
				if (jsonParserFieldValue != null) {
					cart.setCouponCode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					cart.setCreateDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "currencyCode")) {
				if (jsonParserFieldValue != null) {
					cart.setCurrencyCode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					cart.setCustomFields(
						(Map)CartSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "errorMessages")) {
				if (jsonParserFieldValue != null) {
					cart.setErrorMessages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					cart.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "lastPriceUpdateDate")) {

				if (jsonParserFieldValue != null) {
					cart.setLastPriceUpdateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					cart.setModifiedDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "notes")) {
				if (jsonParserFieldValue != null) {
					cart.setNotes(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CartCommentSerDes.toDTO((String)object)
						).toArray(
							size -> new CartComment[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderStatusInfo")) {
				if (jsonParserFieldValue != null) {
					cart.setOrderStatusInfo(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"orderTypeExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					cart.setOrderTypeExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderTypeId")) {
				if (jsonParserFieldValue != null) {
					cart.setOrderTypeId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderUUID")) {
				if (jsonParserFieldValue != null) {
					cart.setOrderUUID((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paymentMethod")) {
				if (jsonParserFieldValue != null) {
					cart.setPaymentMethod((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "paymentMethodLabel")) {

				if (jsonParserFieldValue != null) {
					cart.setPaymentMethodLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paymentStatus")) {
				if (jsonParserFieldValue != null) {
					cart.setPaymentStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paymentStatusInfo")) {
				if (jsonParserFieldValue != null) {
					cart.setPaymentStatusInfo(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "paymentStatusLabel")) {

				if (jsonParserFieldValue != null) {
					cart.setPaymentStatusLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "printedNote")) {
				if (jsonParserFieldValue != null) {
					cart.setPrintedNote((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "purchaseOrderNumber")) {

				if (jsonParserFieldValue != null) {
					cart.setPurchaseOrderNumber((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shippingAddress")) {
				if (jsonParserFieldValue != null) {
					cart.setShippingAddress(
						AddressSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shippingAddressId")) {
				if (jsonParserFieldValue != null) {
					cart.setShippingAddressId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shippingMethod")) {
				if (jsonParserFieldValue != null) {
					cart.setShippingMethod((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shippingOption")) {
				if (jsonParserFieldValue != null) {
					cart.setShippingOption((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					cart.setStatus((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "summary")) {
				if (jsonParserFieldValue != null) {
					cart.setSummary(
						SummarySerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "useAsBilling")) {
				if (jsonParserFieldValue != null) {
					cart.setUseAsBilling((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "valid")) {
				if (jsonParserFieldValue != null) {
					cart.setValid((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "workflowStatusInfo")) {

				if (jsonParserFieldValue != null) {
					cart.setWorkflowStatusInfo(
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