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

package com.liferay.cloud.aws.lambda.commerce.order.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;

import java.util.Collections;

/**
 * @author Eduardo García
 */
public class LambdaCommerceOrderDemoRequestHandler
	implements RequestHandler
		<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(
		APIGatewayProxyRequestEvent event, Context context) {

		LambdaLogger logger = context.getLogger();

		String body = event.getBody();

		logger.log("Received event body: " + _gson.toJson(body));

		JsonObject jsonObject = _gson.fromJson(body, JsonObject.class);

		JsonElement commerceOrderId = jsonObject.get("commerceOrderId");

		String responseMessage = null;
		int statusCode;

		if (commerceOrderId != null) {
			try {
				String host = System.getenv("LIFERAY_HOST");
				String login = System.getenv("LIFERAY_LOGIN");
				String password = System.getenv("LIFERAY_PASSWORD");
				int port = Integer.parseInt(System.getenv("LIFERAY_PORT"));

				int orderStatus = _getOrderStatus(
					host, port, login, password, commerceOrderId.getAsLong());

				responseMessage = "Order status: " + orderStatus;

				statusCode = 200;
			}
			catch (Exception exception) {
				responseMessage = "Error: " + exception.getMessage();

				statusCode = 500;

				exception.printStackTrace();
			}
		}
		else {
			responseMessage = "Missing commerceOrderId";

			statusCode = 400;
		}

		logger.log(responseMessage);

		return _buildAPIGatewayProxyResponseEvent(statusCode, responseMessage);
	}

	private APIGatewayProxyResponseEvent _buildAPIGatewayProxyResponseEvent(
		int statusCode, String responseMessage) {

		APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent =
			new APIGatewayProxyResponseEvent();

		apiGatewayProxyResponseEvent.setIsBase64Encoded(false);
		apiGatewayProxyResponseEvent.setStatusCode(statusCode);
		apiGatewayProxyResponseEvent.setHeaders(
			Collections.singletonMap(
				"timeStamp", String.valueOf(System.currentTimeMillis())));
		apiGatewayProxyResponseEvent.setBody(
			_gson.toJson(
				Collections.singletonMap("responseMessage", responseMessage)));

		return apiGatewayProxyResponseEvent;
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

	private final Gson _gson = new GsonBuilder().setPrettyPrinting(
	).create();

}