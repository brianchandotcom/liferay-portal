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

package com.liferay.commerce.cloud.server.eleflow.util;

import com.liferay.commerce.cloud.server.eleflow.model.EleflowOrder;
import com.liferay.commerce.cloud.server.eleflow.model.EleflowOrderItems;
import com.liferay.commerce.cloud.server.model.Order;
import com.liferay.commerce.cloud.server.model.OrderItem;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowOrderEncoder implements Function<Order, EleflowOrder> {

	@Override
	public EleflowOrder apply(Order order) {
		EleflowOrder eleflowOrder = new EleflowOrder();

		eleflowOrder.setCompanyId(String.valueOf(order.getCompanyId()));
		eleflowOrder.setCreateDate(
			EleflowUtil.getOffsetDateTime(order.getCreateTime()));
		eleflowOrder.setCustomerId(String.valueOf(order.getCustomerId()));
		eleflowOrder.setItems(_encodeOrderItems(order));
		eleflowOrder.setOrderId(String.valueOf(order.getOrderId()));

		return eleflowOrder;
	}

	private List<EleflowOrderItems> _encodeOrderItems(Order order) {
		List<OrderItem> orderItems = order.getOrderItems();

		Stream<OrderItem> stream = orderItems.stream();

		return stream.map(
			_eleflowOrderItemEncoder
		).collect(
			Collectors.toList()
		);
	}

	private static final Function<OrderItem, EleflowOrderItems>
		_eleflowOrderItemEncoder = new EleflowOrderItemEncoder();

}