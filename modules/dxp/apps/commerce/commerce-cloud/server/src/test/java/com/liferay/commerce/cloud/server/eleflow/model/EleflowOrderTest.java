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

package com.liferay.commerce.cloud.server.eleflow.model;

import com.liferay.commerce.cloud.server.util.JsonUtil;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.math.BigDecimal;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowOrderTest {

	@BeforeClass
	public static void setUpClass() {
		JsonUtil.init();
	}

	@Test
	public void testToJson() {
		EleflowOrder eleflowOrder = new EleflowOrder();

		eleflowOrder.addItemsItem(
			_createEleflowOrderItems("15.0123456789", 123, "SKU #1"));
		eleflowOrder.addItemsItem(
			_createEleflowOrderItems("1000", Integer.MAX_VALUE, "SKU #2"));

		eleflowOrder.setCompanyId("Company ID");
		eleflowOrder.setCreateDate(
			OffsetDateTime.of(
				2018, 03, 26, 23, 54, 40, 100000, ZoneOffset.UTC));
		eleflowOrder.setCustomerId("Customer ID");
		eleflowOrder.setOrderId("Order ID");

		String json = Json.encode(eleflowOrder);

		JsonObject jsonObject = new JsonObject(json);

		JsonArray itemsJsonArray = jsonObject.getJsonArray("items");

		Assert.assertEquals(2, itemsJsonArray.size());

		_assertEleflowOrderItems(
			itemsJsonArray.getJsonObject(0), "15.0123456789", 123, "SKU #1");
		_assertEleflowOrderItems(
			itemsJsonArray.getJsonObject(1), "1000", Integer.MAX_VALUE,
			"SKU #2");

		Assert.assertEquals("Company ID", jsonObject.getString("companyId"));
		Assert.assertEquals("Customer ID", jsonObject.getString("customerId"));
		Assert.assertEquals("Order ID", jsonObject.getString("orderId"));
		Assert.assertEquals(
			"2018-03-26T23:54:40.0001Z", jsonObject.getString("createDate"));
	}

	private static void _assertEleflowOrderItems(
		JsonObject jsonObject, String expectedPrice, int expectedQuantity,
		String expectedSKU) {

		Assert.assertEquals(
			Double.parseDouble(expectedPrice), jsonObject.getDouble("price"),
			0.0000000001);
		Assert.assertEquals(
			expectedQuantity, (int)jsonObject.getInteger("quantity"));
		Assert.assertEquals(expectedSKU, jsonObject.getString("sku"));
	}

	private static EleflowOrderItems _createEleflowOrderItems(
		String price, int quantity, String sku) {

		EleflowOrderItems eleflowOrderItems = new EleflowOrderItems();

		eleflowOrderItems.setPrice(new BigDecimal(price));
		eleflowOrderItems.setQuantity(BigDecimal.valueOf(quantity));
		eleflowOrderItems.setSku(sku);

		return eleflowOrderItems;
	}

}