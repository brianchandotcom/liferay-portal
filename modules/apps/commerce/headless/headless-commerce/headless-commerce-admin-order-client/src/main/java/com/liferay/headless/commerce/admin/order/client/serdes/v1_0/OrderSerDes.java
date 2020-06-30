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

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.json.BaseJSONParser;

import java.math.BigDecimal;

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
public class OrderSerDes {

	public static Order toDTO(String json) {
		OrderJSONParser orderJSONParser = new OrderJSONParser();

		return orderJSONParser.parseToDTO(json);
	}

	public static Order[] toDTOs(String json) {
		OrderJSONParser orderJSONParser = new OrderJSONParser();

		return orderJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Order order) {
		if (order == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (order.getAccountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(order.getAccountExternalReferenceCode()));

			sb.append("\"");
		}

		if (order.getAccountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountId\": ");

			sb.append(order.getAccountId());
		}

		if (order.getAdvanceStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"advanceStatus\": ");

			sb.append("\"");

			sb.append(_escape(order.getAdvanceStatus()));

			sb.append("\"");
		}

		if (order.getBillingAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"billingAddress\": ");

			sb.append(String.valueOf(order.getBillingAddress()));
		}

		if (order.getBillingAddressId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"billingAddressId\": ");

			sb.append(order.getBillingAddressId());
		}

		if (order.getChannelId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelId\": ");

			sb.append(order.getChannelId());
		}

		if (order.getCouponCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"couponCode\": ");

			sb.append("\"");

			sb.append(_escape(order.getCouponCode()));

			sb.append("\"");
		}

		if (order.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(order.getCreateDate()));

			sb.append("\"");
		}

		if (order.getCurrencyCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"currencyCode\": ");

			sb.append("\"");

			sb.append(_escape(order.getCurrencyCode()));

			sb.append("\"");
		}

		if (order.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(order.getCustomFields()));
		}

		if (order.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(order.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (order.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(order.getId());
		}

		if (order.getLastPriceUpdateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lastPriceUpdateDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(order.getLastPriceUpdateDate()));

			sb.append("\"");
		}

		if (order.getModifiedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(order.getModifiedDate()));

			sb.append("\"");
		}

		if (order.getOrderDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(order.getOrderDate()));

			sb.append("\"");
		}

		if (order.getOrderItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderItems\": ");

			sb.append("[");

			for (int i = 0; i < order.getOrderItems().length; i++) {
				sb.append(String.valueOf(order.getOrderItems()[i]));

				if ((i + 1) < order.getOrderItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (order.getOrderStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"orderStatus\": ");

			sb.append(order.getOrderStatus());
		}

		if (order.getPaymentMethod() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentMethod\": ");

			sb.append("\"");

			sb.append(_escape(order.getPaymentMethod()));

			sb.append("\"");
		}

		if (order.getPaymentStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentStatus\": ");

			sb.append(order.getPaymentStatus());
		}

		if (order.getPrintedNote() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"printedNote\": ");

			sb.append("\"");

			sb.append(_escape(order.getPrintedNote()));

			sb.append("\"");
		}

		if (order.getPurchaseOrderNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"purchaseOrderNumber\": ");

			sb.append("\"");

			sb.append(_escape(order.getPurchaseOrderNumber()));

			sb.append("\"");
		}

		if (order.getRequestedDeliveryDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"requestedDeliveryDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					order.getRequestedDeliveryDate()));

			sb.append("\"");
		}

		if (order.getShippingAddress() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAddress\": ");

			sb.append(String.valueOf(order.getShippingAddress()));
		}

		if (order.getShippingAddressId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAddressId\": ");

			sb.append(order.getShippingAddressId());
		}

		if (order.getShippingAmount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAmount\": ");

			sb.append(order.getShippingAmount());
		}

		if (order.getShippingAmountFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(order.getShippingAmountFormatted()));

			sb.append("\"");
		}

		if (order.getShippingAmountValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingAmountValue\": ");

			sb.append(order.getShippingAmountValue());
		}

		if (order.getShippingDiscountAmount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountAmount\": ");

			sb.append(order.getShippingDiscountAmount());
		}

		if (order.getShippingDiscountAmountFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(order.getShippingDiscountAmountFormatted()));

			sb.append("\"");
		}

		if (order.getShippingDiscountPercentageLevel1() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel1\": ");

			sb.append(order.getShippingDiscountPercentageLevel1());
		}

		if (order.getShippingDiscountPercentageLevel2() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel2\": ");

			sb.append(order.getShippingDiscountPercentageLevel2());
		}

		if (order.getShippingDiscountPercentageLevel3() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel3\": ");

			sb.append(order.getShippingDiscountPercentageLevel3());
		}

		if (order.getShippingDiscountPercentageLevel4() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingDiscountPercentageLevel4\": ");

			sb.append(order.getShippingDiscountPercentageLevel4());
		}

		if (order.getShippingMethod() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingMethod\": ");

			sb.append("\"");

			sb.append(_escape(order.getShippingMethod()));

			sb.append("\"");
		}

		if (order.getShippingOption() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"shippingOption\": ");

			sb.append("\"");

			sb.append(_escape(order.getShippingOption()));

			sb.append("\"");
		}

		if (order.getSubtotal() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotal\": ");

			sb.append(order.getSubtotal());
		}

		if (order.getSubtotalAmount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalAmount\": ");

			sb.append(order.getSubtotalAmount());
		}

		if (order.getSubtotalDiscountAmount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountAmount\": ");

			sb.append(order.getSubtotalDiscountAmount());
		}

		if (order.getSubtotalDiscountAmountFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(order.getSubtotalDiscountAmountFormatted()));

			sb.append("\"");
		}

		if (order.getSubtotalDiscountPercentageLevel1() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel1\": ");

			sb.append(order.getSubtotalDiscountPercentageLevel1());
		}

		if (order.getSubtotalDiscountPercentageLevel2() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel2\": ");

			sb.append(order.getSubtotalDiscountPercentageLevel2());
		}

		if (order.getSubtotalDiscountPercentageLevel3() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel3\": ");

			sb.append(order.getSubtotalDiscountPercentageLevel3());
		}

		if (order.getSubtotalDiscountPercentageLevel4() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalDiscountPercentageLevel4\": ");

			sb.append(order.getSubtotalDiscountPercentageLevel4());
		}

		if (order.getSubtotalFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subtotalFormatted\": ");

			sb.append("\"");

			sb.append(_escape(order.getSubtotalFormatted()));

			sb.append("\"");
		}

		if (order.getTaxAmount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxAmount\": ");

			sb.append(order.getTaxAmount());
		}

		if (order.getTaxAmountFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(order.getTaxAmountFormatted()));

			sb.append("\"");
		}

		if (order.getTotal() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"total\": ");

			sb.append(order.getTotal());
		}

		if (order.getTotalAmount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalAmount\": ");

			sb.append(order.getTotalAmount());
		}

		if (order.getTotalDiscountAmount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountAmount\": ");

			sb.append(order.getTotalDiscountAmount());
		}

		if (order.getTotalDiscountAmountFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountAmountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(order.getTotalDiscountAmountFormatted()));

			sb.append("\"");
		}

		if (order.getTotalDiscountPercentageLevel1() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel1\": ");

			sb.append(order.getTotalDiscountPercentageLevel1());
		}

		if (order.getTotalDiscountPercentageLevel2() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel2\": ");

			sb.append(order.getTotalDiscountPercentageLevel2());
		}

		if (order.getTotalDiscountPercentageLevel3() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel3\": ");

			sb.append(order.getTotalDiscountPercentageLevel3());
		}

		if (order.getTotalDiscountPercentageLevel4() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalDiscountPercentageLevel4\": ");

			sb.append(order.getTotalDiscountPercentageLevel4());
		}

		if (order.getTotalFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalFormatted\": ");

			sb.append("\"");

			sb.append(_escape(order.getTotalFormatted()));

			sb.append("\"");
		}

		if (order.getTransactionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"transactionId\": ");

			sb.append("\"");

			sb.append(_escape(order.getTransactionId()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OrderJSONParser orderJSONParser = new OrderJSONParser();

		return orderJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Order order) {
		if (order == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (order.getAccountExternalReferenceCode() == null) {
			map.put("accountExternalReferenceCode", null);
		}
		else {
			map.put(
				"accountExternalReferenceCode",
				String.valueOf(order.getAccountExternalReferenceCode()));
		}

		if (order.getAccountId() == null) {
			map.put("accountId", null);
		}
		else {
			map.put("accountId", String.valueOf(order.getAccountId()));
		}

		if (order.getAdvanceStatus() == null) {
			map.put("advanceStatus", null);
		}
		else {
			map.put("advanceStatus", String.valueOf(order.getAdvanceStatus()));
		}

		if (order.getBillingAddress() == null) {
			map.put("billingAddress", null);
		}
		else {
			map.put(
				"billingAddress", String.valueOf(order.getBillingAddress()));
		}

		if (order.getBillingAddressId() == null) {
			map.put("billingAddressId", null);
		}
		else {
			map.put(
				"billingAddressId",
				String.valueOf(order.getBillingAddressId()));
		}

		if (order.getChannelId() == null) {
			map.put("channelId", null);
		}
		else {
			map.put("channelId", String.valueOf(order.getChannelId()));
		}

		if (order.getCouponCode() == null) {
			map.put("couponCode", null);
		}
		else {
			map.put("couponCode", String.valueOf(order.getCouponCode()));
		}

		if (order.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(order.getCreateDate()));
		}

		if (order.getCurrencyCode() == null) {
			map.put("currencyCode", null);
		}
		else {
			map.put("currencyCode", String.valueOf(order.getCurrencyCode()));
		}

		if (order.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put("customFields", String.valueOf(order.getCustomFields()));
		}

		if (order.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(order.getExternalReferenceCode()));
		}

		if (order.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(order.getId()));
		}

		if (order.getLastPriceUpdateDate() == null) {
			map.put("lastPriceUpdateDate", null);
		}
		else {
			map.put(
				"lastPriceUpdateDate",
				liferayToJSONDateFormat.format(order.getLastPriceUpdateDate()));
		}

		if (order.getModifiedDate() == null) {
			map.put("modifiedDate", null);
		}
		else {
			map.put(
				"modifiedDate",
				liferayToJSONDateFormat.format(order.getModifiedDate()));
		}

		if (order.getOrderDate() == null) {
			map.put("orderDate", null);
		}
		else {
			map.put(
				"orderDate",
				liferayToJSONDateFormat.format(order.getOrderDate()));
		}

		if (order.getOrderItems() == null) {
			map.put("orderItems", null);
		}
		else {
			map.put("orderItems", String.valueOf(order.getOrderItems()));
		}

		if (order.getOrderStatus() == null) {
			map.put("orderStatus", null);
		}
		else {
			map.put("orderStatus", String.valueOf(order.getOrderStatus()));
		}

		if (order.getPaymentMethod() == null) {
			map.put("paymentMethod", null);
		}
		else {
			map.put("paymentMethod", String.valueOf(order.getPaymentMethod()));
		}

		if (order.getPaymentStatus() == null) {
			map.put("paymentStatus", null);
		}
		else {
			map.put("paymentStatus", String.valueOf(order.getPaymentStatus()));
		}

		if (order.getPrintedNote() == null) {
			map.put("printedNote", null);
		}
		else {
			map.put("printedNote", String.valueOf(order.getPrintedNote()));
		}

		if (order.getPurchaseOrderNumber() == null) {
			map.put("purchaseOrderNumber", null);
		}
		else {
			map.put(
				"purchaseOrderNumber",
				String.valueOf(order.getPurchaseOrderNumber()));
		}

		if (order.getRequestedDeliveryDate() == null) {
			map.put("requestedDeliveryDate", null);
		}
		else {
			map.put(
				"requestedDeliveryDate",
				liferayToJSONDateFormat.format(
					order.getRequestedDeliveryDate()));
		}

		if (order.getShippingAddress() == null) {
			map.put("shippingAddress", null);
		}
		else {
			map.put(
				"shippingAddress", String.valueOf(order.getShippingAddress()));
		}

		if (order.getShippingAddressId() == null) {
			map.put("shippingAddressId", null);
		}
		else {
			map.put(
				"shippingAddressId",
				String.valueOf(order.getShippingAddressId()));
		}

		if (order.getShippingAmount() == null) {
			map.put("shippingAmount", null);
		}
		else {
			map.put(
				"shippingAmount", String.valueOf(order.getShippingAmount()));
		}

		if (order.getShippingAmountFormatted() == null) {
			map.put("shippingAmountFormatted", null);
		}
		else {
			map.put(
				"shippingAmountFormatted",
				String.valueOf(order.getShippingAmountFormatted()));
		}

		if (order.getShippingAmountValue() == null) {
			map.put("shippingAmountValue", null);
		}
		else {
			map.put(
				"shippingAmountValue",
				String.valueOf(order.getShippingAmountValue()));
		}

		if (order.getShippingDiscountAmount() == null) {
			map.put("shippingDiscountAmount", null);
		}
		else {
			map.put(
				"shippingDiscountAmount",
				String.valueOf(order.getShippingDiscountAmount()));
		}

		if (order.getShippingDiscountAmountFormatted() == null) {
			map.put("shippingDiscountAmountFormatted", null);
		}
		else {
			map.put(
				"shippingDiscountAmountFormatted",
				String.valueOf(order.getShippingDiscountAmountFormatted()));
		}

		if (order.getShippingDiscountPercentageLevel1() == null) {
			map.put("shippingDiscountPercentageLevel1", null);
		}
		else {
			map.put(
				"shippingDiscountPercentageLevel1",
				String.valueOf(order.getShippingDiscountPercentageLevel1()));
		}

		if (order.getShippingDiscountPercentageLevel2() == null) {
			map.put("shippingDiscountPercentageLevel2", null);
		}
		else {
			map.put(
				"shippingDiscountPercentageLevel2",
				String.valueOf(order.getShippingDiscountPercentageLevel2()));
		}

		if (order.getShippingDiscountPercentageLevel3() == null) {
			map.put("shippingDiscountPercentageLevel3", null);
		}
		else {
			map.put(
				"shippingDiscountPercentageLevel3",
				String.valueOf(order.getShippingDiscountPercentageLevel3()));
		}

		if (order.getShippingDiscountPercentageLevel4() == null) {
			map.put("shippingDiscountPercentageLevel4", null);
		}
		else {
			map.put(
				"shippingDiscountPercentageLevel4",
				String.valueOf(order.getShippingDiscountPercentageLevel4()));
		}

		if (order.getShippingMethod() == null) {
			map.put("shippingMethod", null);
		}
		else {
			map.put(
				"shippingMethod", String.valueOf(order.getShippingMethod()));
		}

		if (order.getShippingOption() == null) {
			map.put("shippingOption", null);
		}
		else {
			map.put(
				"shippingOption", String.valueOf(order.getShippingOption()));
		}

		if (order.getSubtotal() == null) {
			map.put("subtotal", null);
		}
		else {
			map.put("subtotal", String.valueOf(order.getSubtotal()));
		}

		if (order.getSubtotalAmount() == null) {
			map.put("subtotalAmount", null);
		}
		else {
			map.put(
				"subtotalAmount", String.valueOf(order.getSubtotalAmount()));
		}

		if (order.getSubtotalDiscountAmount() == null) {
			map.put("subtotalDiscountAmount", null);
		}
		else {
			map.put(
				"subtotalDiscountAmount",
				String.valueOf(order.getSubtotalDiscountAmount()));
		}

		if (order.getSubtotalDiscountAmountFormatted() == null) {
			map.put("subtotalDiscountAmountFormatted", null);
		}
		else {
			map.put(
				"subtotalDiscountAmountFormatted",
				String.valueOf(order.getSubtotalDiscountAmountFormatted()));
		}

		if (order.getSubtotalDiscountPercentageLevel1() == null) {
			map.put("subtotalDiscountPercentageLevel1", null);
		}
		else {
			map.put(
				"subtotalDiscountPercentageLevel1",
				String.valueOf(order.getSubtotalDiscountPercentageLevel1()));
		}

		if (order.getSubtotalDiscountPercentageLevel2() == null) {
			map.put("subtotalDiscountPercentageLevel2", null);
		}
		else {
			map.put(
				"subtotalDiscountPercentageLevel2",
				String.valueOf(order.getSubtotalDiscountPercentageLevel2()));
		}

		if (order.getSubtotalDiscountPercentageLevel3() == null) {
			map.put("subtotalDiscountPercentageLevel3", null);
		}
		else {
			map.put(
				"subtotalDiscountPercentageLevel3",
				String.valueOf(order.getSubtotalDiscountPercentageLevel3()));
		}

		if (order.getSubtotalDiscountPercentageLevel4() == null) {
			map.put("subtotalDiscountPercentageLevel4", null);
		}
		else {
			map.put(
				"subtotalDiscountPercentageLevel4",
				String.valueOf(order.getSubtotalDiscountPercentageLevel4()));
		}

		if (order.getSubtotalFormatted() == null) {
			map.put("subtotalFormatted", null);
		}
		else {
			map.put(
				"subtotalFormatted",
				String.valueOf(order.getSubtotalFormatted()));
		}

		if (order.getTaxAmount() == null) {
			map.put("taxAmount", null);
		}
		else {
			map.put("taxAmount", String.valueOf(order.getTaxAmount()));
		}

		if (order.getTaxAmountFormatted() == null) {
			map.put("taxAmountFormatted", null);
		}
		else {
			map.put(
				"taxAmountFormatted",
				String.valueOf(order.getTaxAmountFormatted()));
		}

		if (order.getTotal() == null) {
			map.put("total", null);
		}
		else {
			map.put("total", String.valueOf(order.getTotal()));
		}

		if (order.getTotalAmount() == null) {
			map.put("totalAmount", null);
		}
		else {
			map.put("totalAmount", String.valueOf(order.getTotalAmount()));
		}

		if (order.getTotalDiscountAmount() == null) {
			map.put("totalDiscountAmount", null);
		}
		else {
			map.put(
				"totalDiscountAmount",
				String.valueOf(order.getTotalDiscountAmount()));
		}

		if (order.getTotalDiscountAmountFormatted() == null) {
			map.put("totalDiscountAmountFormatted", null);
		}
		else {
			map.put(
				"totalDiscountAmountFormatted",
				String.valueOf(order.getTotalDiscountAmountFormatted()));
		}

		if (order.getTotalDiscountPercentageLevel1() == null) {
			map.put("totalDiscountPercentageLevel1", null);
		}
		else {
			map.put(
				"totalDiscountPercentageLevel1",
				String.valueOf(order.getTotalDiscountPercentageLevel1()));
		}

		if (order.getTotalDiscountPercentageLevel2() == null) {
			map.put("totalDiscountPercentageLevel2", null);
		}
		else {
			map.put(
				"totalDiscountPercentageLevel2",
				String.valueOf(order.getTotalDiscountPercentageLevel2()));
		}

		if (order.getTotalDiscountPercentageLevel3() == null) {
			map.put("totalDiscountPercentageLevel3", null);
		}
		else {
			map.put(
				"totalDiscountPercentageLevel3",
				String.valueOf(order.getTotalDiscountPercentageLevel3()));
		}

		if (order.getTotalDiscountPercentageLevel4() == null) {
			map.put("totalDiscountPercentageLevel4", null);
		}
		else {
			map.put(
				"totalDiscountPercentageLevel4",
				String.valueOf(order.getTotalDiscountPercentageLevel4()));
		}

		if (order.getTotalFormatted() == null) {
			map.put("totalFormatted", null);
		}
		else {
			map.put(
				"totalFormatted", String.valueOf(order.getTotalFormatted()));
		}

		if (order.getTransactionId() == null) {
			map.put("transactionId", null);
		}
		else {
			map.put("transactionId", String.valueOf(order.getTransactionId()));
		}

		return map;
	}

	public static class OrderJSONParser extends BaseJSONParser<Order> {

		@Override
		protected Order createDTO() {
			return new Order();
		}

		@Override
		protected Order[] createDTOArray(int size) {
			return new Order[size];
		}

		@Override
		protected void setField(
			Order order, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "accountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					order.setAccountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "accountId")) {
				if (jsonParserFieldValue != null) {
					order.setAccountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "advanceStatus")) {
				if (jsonParserFieldValue != null) {
					order.setAdvanceStatus((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "billingAddress")) {
				if (jsonParserFieldValue != null) {
					order.setBillingAddress(
						BillingAddressSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "billingAddressId")) {
				if (jsonParserFieldValue != null) {
					order.setBillingAddressId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "channelId")) {
				if (jsonParserFieldValue != null) {
					order.setChannelId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "couponCode")) {
				if (jsonParserFieldValue != null) {
					order.setCouponCode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					order.setCreateDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "currencyCode")) {
				if (jsonParserFieldValue != null) {
					order.setCurrencyCode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					order.setCustomFields(
						(Map)OrderSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					order.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					order.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "lastPriceUpdateDate")) {

				if (jsonParserFieldValue != null) {
					order.setLastPriceUpdateDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "modifiedDate")) {
				if (jsonParserFieldValue != null) {
					order.setModifiedDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderDate")) {
				if (jsonParserFieldValue != null) {
					order.setOrderDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderItems")) {
				if (jsonParserFieldValue != null) {
					order.setOrderItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OrderItemSerDes.toDTO((String)object)
						).toArray(
							size -> new OrderItem[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "orderStatus")) {
				if (jsonParserFieldValue != null) {
					order.setOrderStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paymentMethod")) {
				if (jsonParserFieldValue != null) {
					order.setPaymentMethod((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paymentStatus")) {
				if (jsonParserFieldValue != null) {
					order.setPaymentStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "printedNote")) {
				if (jsonParserFieldValue != null) {
					order.setPrintedNote((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "purchaseOrderNumber")) {

				if (jsonParserFieldValue != null) {
					order.setPurchaseOrderNumber((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "requestedDeliveryDate")) {

				if (jsonParserFieldValue != null) {
					order.setRequestedDeliveryDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shippingAddress")) {
				if (jsonParserFieldValue != null) {
					order.setShippingAddress(
						ShippingAddressSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shippingAddressId")) {
				if (jsonParserFieldValue != null) {
					order.setShippingAddressId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shippingAmount")) {
				if (jsonParserFieldValue != null) {
					order.setShippingAmount((BigDecimal)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "shippingAmountFormatted")) {

				if (jsonParserFieldValue != null) {
					order.setShippingAmountFormatted(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "shippingAmountValue")) {

				if (jsonParserFieldValue != null) {
					order.setShippingAmountValue(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "shippingDiscountAmount")) {

				if (jsonParserFieldValue != null) {
					order.setShippingDiscountAmount(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"shippingDiscountAmountFormatted")) {

				if (jsonParserFieldValue != null) {
					order.setShippingDiscountAmountFormatted(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"shippingDiscountPercentageLevel1")) {

				if (jsonParserFieldValue != null) {
					order.setShippingDiscountPercentageLevel1(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"shippingDiscountPercentageLevel2")) {

				if (jsonParserFieldValue != null) {
					order.setShippingDiscountPercentageLevel2(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"shippingDiscountPercentageLevel3")) {

				if (jsonParserFieldValue != null) {
					order.setShippingDiscountPercentageLevel3(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"shippingDiscountPercentageLevel4")) {

				if (jsonParserFieldValue != null) {
					order.setShippingDiscountPercentageLevel4(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shippingMethod")) {
				if (jsonParserFieldValue != null) {
					order.setShippingMethod((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "shippingOption")) {
				if (jsonParserFieldValue != null) {
					order.setShippingOption((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subtotal")) {
				if (jsonParserFieldValue != null) {
					order.setSubtotal((BigDecimal)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subtotalAmount")) {
				if (jsonParserFieldValue != null) {
					order.setSubtotalAmount(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "subtotalDiscountAmount")) {

				if (jsonParserFieldValue != null) {
					order.setSubtotalDiscountAmount(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"subtotalDiscountAmountFormatted")) {

				if (jsonParserFieldValue != null) {
					order.setSubtotalDiscountAmountFormatted(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"subtotalDiscountPercentageLevel1")) {

				if (jsonParserFieldValue != null) {
					order.setSubtotalDiscountPercentageLevel1(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"subtotalDiscountPercentageLevel2")) {

				if (jsonParserFieldValue != null) {
					order.setSubtotalDiscountPercentageLevel2(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"subtotalDiscountPercentageLevel3")) {

				if (jsonParserFieldValue != null) {
					order.setSubtotalDiscountPercentageLevel3(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"subtotalDiscountPercentageLevel4")) {

				if (jsonParserFieldValue != null) {
					order.setSubtotalDiscountPercentageLevel4(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subtotalFormatted")) {
				if (jsonParserFieldValue != null) {
					order.setSubtotalFormatted((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "taxAmount")) {
				if (jsonParserFieldValue != null) {
					order.setTaxAmount(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxAmountFormatted")) {

				if (jsonParserFieldValue != null) {
					order.setTaxAmountFormatted((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "total")) {
				if (jsonParserFieldValue != null) {
					order.setTotal((BigDecimal)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalAmount")) {
				if (jsonParserFieldValue != null) {
					order.setTotalAmount(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "totalDiscountAmount")) {

				if (jsonParserFieldValue != null) {
					order.setTotalDiscountAmount(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "totalDiscountAmountFormatted")) {

				if (jsonParserFieldValue != null) {
					order.setTotalDiscountAmountFormatted(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "totalDiscountPercentageLevel1")) {

				if (jsonParserFieldValue != null) {
					order.setTotalDiscountPercentageLevel1(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "totalDiscountPercentageLevel2")) {

				if (jsonParserFieldValue != null) {
					order.setTotalDiscountPercentageLevel2(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "totalDiscountPercentageLevel3")) {

				if (jsonParserFieldValue != null) {
					order.setTotalDiscountPercentageLevel3(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "totalDiscountPercentageLevel4")) {

				if (jsonParserFieldValue != null) {
					order.setTotalDiscountPercentageLevel4(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalFormatted")) {
				if (jsonParserFieldValue != null) {
					order.setTotalFormatted((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "transactionId")) {
				if (jsonParserFieldValue != null) {
					order.setTransactionId((String)jsonParserFieldValue);
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