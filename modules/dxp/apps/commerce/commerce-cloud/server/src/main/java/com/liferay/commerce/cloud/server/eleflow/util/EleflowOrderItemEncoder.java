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

import com.liferay.commerce.cloud.server.eleflow.model.EleflowOrderItems;
import com.liferay.commerce.cloud.server.model.OrderItem;

import java.math.BigDecimal;

import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowOrderItemEncoder
	implements Function<OrderItem, EleflowOrderItems> {

	@Override
	public EleflowOrderItems apply(OrderItem orderItem) {
		EleflowOrderItems eleflowOrderItems = new EleflowOrderItems();

		eleflowOrderItems.setPrice(new BigDecimal(orderItem.getPrice()));
		eleflowOrderItems.setQuantity(
			BigDecimal.valueOf(orderItem.getQuantity()));
		eleflowOrderItems.setSku(orderItem.getSku());

		return eleflowOrderItems;
	}

}