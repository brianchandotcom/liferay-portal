/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.model;

import java.math.BigDecimal;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderItem {

	public OrderItem(
		long cpInstanceId, String discount, String[] errorMessages,
		String formattedQuantity, String formattedSubscriptionPeriod,
		String name, String options, long orderId, long orderItemId,
		List<OrderItem> orderItems, long parentOrderItemId, String price,
		String promoPrice, BigDecimal shippedQuantity, String sku,
		String thumbnail, String total, String unitOfMeasureKey) {

		_cpInstanceId = cpInstanceId;
		_discount = discount;
		_errorMessages = errorMessages;
		_formattedQuantity = formattedQuantity;
		_formattedSubscriptionPeriod = formattedSubscriptionPeriod;
		_name = name;
		_options = options;
		_orderId = orderId;
		_orderItemId = orderItemId;
		_orderItems = orderItems;
		_parentOrderItemId = parentOrderItemId;
		_price = price;
		_promoPrice = promoPrice;
		_shippedQuantity = shippedQuantity;
		_sku = sku;
		_thumbnail = thumbnail;
		_total = total;
		_unitOfMeasureKey = unitOfMeasureKey;
	}

	public long getCPInstanceId() {
		return _cpInstanceId;
	}

	public String getDiscount() {
		return _discount;
	}

	public String[] getErrorMessages() {
		return _errorMessages;
	}

	public String getFormattedQuantity() {
		return _formattedQuantity;
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

	public BigDecimal getShippedQuantity() {
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

	public String getUnitOfMeasureKey() {
		return _unitOfMeasureKey;
	}

	private final long _cpInstanceId;
	private final String _discount;
	private final String[] _errorMessages;
	private final String _formattedQuantity;
	private final String _formattedSubscriptionPeriod;
	private final String _name;
	private final String _options;
	private final long _orderId;
	private final long _orderItemId;
	private final List<OrderItem> _orderItems;
	private final long _parentOrderItemId;
	private final String _price;
	private final String _promoPrice;
	private final BigDecimal _shippedQuantity;
	private final String _sku;
	private final String _thumbnail;
	private final String _total;
	private final String _unitOfMeasureKey;

}