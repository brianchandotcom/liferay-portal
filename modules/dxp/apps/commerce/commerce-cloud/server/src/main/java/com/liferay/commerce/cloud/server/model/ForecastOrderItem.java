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

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class ForecastOrderItem implements JsonSerializable {

	public ForecastOrderItem() {
	}

	public ForecastOrderItem(JsonObject jsonObject) {
		ForecastOrderItemConverter.fromJson(jsonObject, this);
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

	public void setPrice(String price) {
		_price = price;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	public void setSku(String sku) {
		_sku = sku;
	}

	@Override
	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();

		ForecastOrderItemConverter.toJson(this, jsonObject);

		return jsonObject;
	}

	private String _price;
	private int _quantity;
	private String _sku;

}