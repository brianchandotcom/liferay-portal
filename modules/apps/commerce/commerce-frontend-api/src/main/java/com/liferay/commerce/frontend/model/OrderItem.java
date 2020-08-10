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

package com.liferay.commerce.frontend.model;

/**
 * @author Alec Sloan
 */
public class OrderItem {

	public OrderItem(
		int available, Icon icon, long orderId, long orderItemId, int quantity,
		String sku) {

		_available = available;
		_icon = icon;
		_orderId = orderId;
		_orderItemId = orderItemId;
		_quantity = quantity;
		_sku = sku;
	}

	public int getAvailable() {
		return _available;
	}

	public Icon getIcon() {
		return _icon;
	}

	public long getOrderId() {
		return _orderId;
	}

	public long getOrderItemId() {
		return _orderItemId;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String getSku() {
		return _sku;
	}

	private final int _available;
	private final Icon _icon;
	private final long _orderId;
	private final long _orderItemId;
	private final int _quantity;
	private final String _sku;

}