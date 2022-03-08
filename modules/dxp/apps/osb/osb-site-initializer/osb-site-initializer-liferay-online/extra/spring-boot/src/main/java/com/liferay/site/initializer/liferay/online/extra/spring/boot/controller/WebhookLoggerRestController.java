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

package com.liferay.site.initializer.liferay.online.extra.spring.boot.controller;

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
		}
		else {
			log.info("No Order Item in the Order");
		}

		return ResponseEntity.ok("Order status is: " + order.getOrderStatus());
	}

}