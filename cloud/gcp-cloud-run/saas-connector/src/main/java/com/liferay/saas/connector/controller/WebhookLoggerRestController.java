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

package com.liferay.saas.connector.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Zoltán Takács
 * @author Jürgen Kappler
 */
@BasePathAwareController
@Slf4j
public class WebhookLoggerRestController {

	@GetMapping("{value}")
	public ResponseEntity<String> getValue(
		@PathVariable(required = false) String value) {
		return ResponseEntity.ok(value);
	}

	@PostMapping("/test/post")
	public ResponseEntity<String> postValue(@RequestBody ObjectNode value) {
		JsonNode commerceOrder = value.get("commerceOrder");

		Order order = Order.toDTO(commerceOrder.toString());

		OrderItem[] orderItems = order.getOrderItems();

		if (orderItems.length > 0) {
			OrderItem orderItem = orderItems[0];

			log.info(orderItem.getOptions());
		} else {
			log.info("No Order Item in the Order");
		}

		return ResponseEntity.ok("Order status is: " + order.getOrderStatus());
	}
}
