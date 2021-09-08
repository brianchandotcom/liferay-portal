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

package com.liferay.commerce.internal.messaging;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = "destination.name=" + DestinationNames.COMMERCE_SHIPMENT_STATUS,
	service = MessageListener.class
)
public class CommerceShipmentStatusMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(message.getPayload()));

		long commerceShipmentId = jsonObject.getLong("commerceShipmentId");

		List<CommerceOrder> shippedCommerceOrders =
			_commerceOrderLocalService.
				getShippedCommerceOrdersByCommerceShipmentId(
					commerceShipmentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceOrder commerceOrder : shippedCommerceOrders) {
			_commerceOrderEngine.checkCommerceOrderShipmentStatus(
				commerceOrder);
		}
	}

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}