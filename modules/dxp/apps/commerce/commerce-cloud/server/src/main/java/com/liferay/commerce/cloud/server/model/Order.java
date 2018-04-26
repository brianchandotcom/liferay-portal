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

package com.liferay.commerce.cloud.server.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class Order implements JsonSerializable {

	public Order() {
	}

	public Order(JsonObject jsonObject) {
		OrderConverter.fromJson(jsonObject, this);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getCreateTime() {
		return _createTime;
	}

	public long getCustomerId() {
		return _customerId;
	}

	public long getOrderId() {
		return _orderId;
	}

	public List<OrderItem> getOrderItems() {
		return _orderItems;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreateTime(long createTime) {
		_createTime = createTime;
	}

	public void setCustomerId(long customerId) {
		_customerId = customerId;
	}

	public void setOrderId(long orderId) {
		_orderId = orderId;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		_orderItems = orderItems;
	}

	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();

		OrderConverter.toJson(this, jsonObject);

		return jsonObject;
	}

	private long _companyId;
	private long _createTime;
	private long _customerId;
	private long _orderId;
	private List<OrderItem> _orderItems;

}