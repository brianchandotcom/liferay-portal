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

/**
 * @author Andrea Di Giorgi
 */
@DataObject(generateConverter = true, publicConverter = false)
public class ForecastOrderItem implements JsonSerializable {

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