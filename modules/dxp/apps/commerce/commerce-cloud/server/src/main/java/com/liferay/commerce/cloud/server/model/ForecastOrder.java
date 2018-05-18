/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cloud.server.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class ForecastOrder implements JsonSerializable {

	public ForecastOrder(JsonObject jsonObject) {
		ForecastOrderConverter.fromJson(jsonObject, this);
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

	public List<ForecastOrderItem> getOrderItems() {
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

	public void setOrderItems(List<ForecastOrderItem> orderItems) {
		_orderItems = orderItems;
	}

	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();

		ForecastOrderConverter.toJson(this, jsonObject);

		return jsonObject;
	}

	private long _companyId;
	private long _createTime;
	private long _customerId;
	private long _orderId;
	private List<ForecastOrderItem> _orderItems;

}