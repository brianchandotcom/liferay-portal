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

package com.liferay.commerce.order.content.web.internal.model;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderItem {

	public OrderItem(
		long orderItemId, long orderId, String sku, String name, String options,
		List<OrderItem> orderItems, long parentOrderItemId, String price,
		String promoPrice, String discount, int quantity, String total,
		String thumbnail, int shippedQuantity, String[] errorMessages,
		String formattedSubscriptionPeriod) {

		_orderItemId = orderItemId;
		_orderId = orderId;
		_sku = sku;
		_name = name;
		_options = options;
		_orderItems = orderItems;
		_parentOrderItemId = parentOrderItemId;
		_price = price;
		_promoPrice = promoPrice;
		_discount = discount;
		_quantity = quantity;
		_total = total;
		_thumbnail = thumbnail;
		_shippedQuantity = shippedQuantity;
		_errorMessages = errorMessages;
		_formattedSubscriptionPeriod = formattedSubscriptionPeriod;
	}

	public String getDiscount() {
		return _discount;
	}

	public String[] getErrorMessages() {
		return _errorMessages;
	}

	public String getFormattedSubscriptionPeriod() {
		return _formattedSubscriptionPeriod;
	}

	public String getName() {
		return _name;
	}

	public String getOptions() {
		return _options;
	}

	public long getOrderId() {
		return _orderId;
	}

	public long getOrderItemId() {
		return _orderItemId;
	}

	public List<OrderItem> getOrderItems() {
		return _orderItems;
	}

	public long getParentOrderItemId() {
		return _parentOrderItemId;
	}

	public String getPrice() {
		return _price;
	}

	public String getPromoPrice() {
		return _promoPrice;
	}

	public int getQuantity() {
		return _quantity;
	}

	public int getShippedQuantity() {
		return _shippedQuantity;
	}

	public String getSku() {
		return _sku;
	}

	public String getThumbnail() {
		return _thumbnail;
	}

	public String getTotal() {
		return _total;
	}

	private final String _discount;
	private final String[] _errorMessages;
	private final String _formattedSubscriptionPeriod;
	private final String _name;
	private final String _options;
	private final long _orderId;
	private final long _orderItemId;
	private final List<OrderItem> _orderItems;
	private final long _parentOrderItemId;
	private final String _price;
	private final String _promoPrice;
	private final int _quantity;
	private final int _shippedQuantity;
	private final String _sku;
	private final String _thumbnail;
	private final String _total;

}