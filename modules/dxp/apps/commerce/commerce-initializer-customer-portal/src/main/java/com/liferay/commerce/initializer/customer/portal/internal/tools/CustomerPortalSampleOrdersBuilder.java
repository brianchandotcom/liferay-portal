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

package com.liferay.commerce.initializer.customer.portal.internal.tools;

import com.liferay.commerce.initializer.customer.portal.internal.CustomerPortalGroupInitializer;
import com.liferay.commerce.initializer.customer.portal.internal.tools.util.CustomerPortalToolsUtil;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.IOException;

import java.math.BigDecimal;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Andrea Di Giorgi
 */
public class CustomerPortalSampleOrdersBuilder {

	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			new CustomerPortalSampleOrdersBuilder(args[0]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public CustomerPortalSampleOrdersBuilder(String outputDirName)
		throws IOException {

		Path outputDirPath = Paths.get(outputDirName);

		String productsJSON = CustomerPortalToolsUtil.read(
			CustomerPortalGroupInitializer.DEPENDENCY_PATH + "products.json");

		JSONArray productsJSONArray = new JSONArray(productsJSON);

		for (int organizationId = 1;
			organizationId <=
				CustomerPortalGroupInitializer.ACCOUNT_ORGANIZATIONS_COUNT;
			organizationId++) {

			_writeOrders(outputDirPath, organizationId, productsJSONArray);
		}
	}

	private JSONObject _getOrder(
		int organizationId, JSONArray productsJSONArray) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("organizationId", organizationId);

		BigDecimal total = BigDecimal.ZERO;

		JSONArray itemsJSONArray = new JSONArray();

		int itemsCount = RandomUtil.nextInt(_MAX_ORDER_ITEMS_COUNT);

		for (int i = 0; i < itemsCount; i++) {
			JSONObject itemJSONObject = new JSONObject();

			JSONObject productJSONObject = productsJSONArray.getJSONObject(
				RandomUtil.nextInt(productsJSONArray.length()));

			String sku = productJSONObject.getString("Sku");
			BigDecimal unitPrice = BigDecimal.valueOf(
				productJSONObject.optDouble("Price", 0));

			int quantity = RandomUtil.nextInt(_MAX_QUANTITY);

			BigDecimal price = unitPrice.multiply(BigDecimal.valueOf(quantity));

			total = total.add(price);

			itemJSONObject.put("price", price);

			itemJSONObject.put("quantity", quantity);
			itemJSONObject.put("sku", sku);

			itemsJSONArray.put(itemJSONObject);
		}

		jsonObject.put("items", itemsJSONArray);

		jsonObject.put("time", Math.negateExact((long)(Math.random() * _TIME)));
		jsonObject.put("total", total);

		return jsonObject;
	}

	private void _writeOrders(
			Path outputDirPath, int organizationId, JSONArray productsJSONArray)
		throws IOException {

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < _ORDERS_COUNT; i++) {
			JSONObject jsonObject = _getOrder(
				organizationId, productsJSONArray);

			jsonArray.put(jsonObject);
		}

		Path path = outputDirPath.resolve(
			"organization_" + organizationId + ".json");

		CustomerPortalToolsUtil.write(path, jsonArray);
	}

	private static final int _MAX_ORDER_ITEMS_COUNT = 20;

	private static final int _MAX_QUANTITY = 100;

	private static final int _ORDERS_COUNT = 5000;

	private static final long _TIME = 6 * Time.MONTH;

}