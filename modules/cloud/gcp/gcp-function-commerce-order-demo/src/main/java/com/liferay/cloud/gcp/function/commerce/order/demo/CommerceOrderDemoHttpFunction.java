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

package com.liferay.cloud.gcp.function.commerce.order.demo;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.logging.Logger;

public class CommerceOrderDemoHttpFunction implements HttpFunction {

	@Override
	public void service(HttpRequest httpRequest, HttpResponse httpResponse)
		throws IOException {

		String responseMessage = null;
		int statusCode = 200;

		try {
			JsonElement requestParsed = _gson.fromJson(
				httpRequest.getReader(), JsonElement.class);
			JsonObject requestJsonObject = null;

			if ((requestParsed != null) && requestParsed.isJsonObject()) {
				requestJsonObject = requestParsed.getAsJsonObject();
			}

			JsonElement commerceOrderId = null;

			if ((requestJsonObject != null) &&
				requestJsonObject.has("commerceOrderId")) {

				commerceOrderId = requestJsonObject.get("commerceOrderId");
			}

			if (commerceOrderId != null) {
				String host = System.getenv("LIFERAY_HOST");
				String login = System.getenv("LIFERAY_LOGIN");
				String password = System.getenv("LIFERAY_PASSWORD");
				int port = Integer.parseInt(System.getenv("LIFERAY_PORT"));

				int orderStatus = _getOrderStatus(
					host, port, login, password, commerceOrderId.getAsLong());

				responseMessage = "Order status: " + orderStatus;
			}
			else {
				responseMessage = "Missing commerceOrderId";

				statusCode = 400;
			}
		}
		catch (Exception exception) {
			_logger.severe(
				"Error processing request: " + exception.getMessage());

			responseMessage = "Error: " + exception.getMessage();

			statusCode = 500;

			exception.printStackTrace();
		}

		_logger.info(responseMessage);

		httpResponse.setStatusCode(statusCode);

		PrintWriter writer = new PrintWriter(httpResponse.getWriter());

		writer.printf(responseMessage);
	}

	private int _getOrderStatus(
			String host, int port, String login, String password, long orderId)
		throws Exception {

		OrderResource orderResource = OrderResource.builder(
		).authentication(
			login, password
		).endpoint(
			host, port, "https"
		).build();

		Order order = orderResource.getOrder(orderId);

		return order.getOrderStatus();
	}

	private static final Logger _logger = Logger.getLogger(
		CommerceOrderDemoHttpFunction.class.getName());

	private final Gson _gson = new GsonBuilder().setPrettyPrinting(
	).create();

}