/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.subscription.web.internal.model;

/**
 * @author Luca Pellizzon
 */
public class OrderItem {

	public OrderItem(
		long orderItemId, long orderId, String sku, String name, String price,
		String subscriptionDuration, String subscriptionPeriod, String discount,
		int quantity, String total) {

		_orderItemId = orderItemId;
		_orderId = orderId;
		_sku = sku;
		_name = name;
		_price = price;
		_subscriptionDuration = subscriptionDuration;
		_subscriptionPeriod = subscriptionPeriod;
		_discount = discount;
		_quantity = quantity;
		_total = total;
	}

	public String getDiscount() {
		return _discount;
	}

	public String getName() {
		return _name;
	}

	public long getOrderId() {
		return _orderId;
	}

	public long getOrderItemId() {
		return _orderItemId;
	}

	public String getPrice() {
		return _price;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String getSku() {
		return _sku;
	}

	public String getSubscriptionDuration() {
		return _subscriptionDuration;
	}

	public String getSubscriptionPeriod() {
		return _subscriptionPeriod;
	}

	public String getTotal() {
		return _total;
	}

	private final String _discount;
	private final String _name;
	private final long _orderId;
	private final long _orderItemId;
	private final String _price;
	private final int _quantity;
	private final String _sku;
	private final String _subscriptionDuration;
	private final String _subscriptionPeriod;
	private final String _total;

}