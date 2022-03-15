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

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.type.constants.CommerceInventoryAuditTypeConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = "destination.name=" + DestinationNames.COMMERCE_ORDER_STATUS,
	service = MessageListener.class
)
public class CommerceOrderStatusMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(message.getPayload()));

		long commerceOrderId = jsonObject.getLong("commerceOrderId");

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);

		if (commerceOrder == null) {
			return;
		}

		int orderStatus = jsonObject.getInt("orderStatus");

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			if (CommerceOrderConstants.ORDER_STATUS_CANCELLED == orderStatus) {
				User currentUser = _userService.getCurrentUser();

				_commerceInventoryBookedQuantityLocalService.
					restockCommerceInventoryBookedQuantity(
						currentUser.getUserId(),
						commerceOrderItem.getBookedQuantityId(),
						HashMapBuilder.put(
							CommerceInventoryAuditTypeConstants.ORDER_ID,
							String.valueOf(
								commerceOrderItem.getCommerceOrderId())
						).put(
							CommerceInventoryAuditTypeConstants.ORDER_ITEM_ID,
							String.valueOf(
								commerceOrderItem.getCommerceOrderItemId())
						).build());
			}
		}
	}

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private UserService _userService;

}