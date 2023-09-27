/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.messaging;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.type.constants.CommerceInventoryAuditTypeConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderLocalService;
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
	property = "destination.name=" + DestinationNames.COMMERCE_ORDER_STATUS,
	service = MessageListener.class
)
public class CommerceOrderStatusMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		JSONObject jsonObject = (JSONObject)message.getPayload();

		long commerceOrderId = jsonObject.getLong("commerceOrderId");

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);

		if (commerceOrder == null) {
			return;
		}

		int orderStatus = jsonObject.getInt("orderStatus");

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			if ((CommerceOrderConstants.ORDER_STATUS_CANCELLED !=
					orderStatus) ||
				(commerceOrderItem.getCommerceInventoryBookedQuantityId() <=
					0)) {

				continue;
			}

			CommerceInventoryBookedQuantity commerceInventoryBookedQuantity =
				_commerceInventoryBookedQuantityLocalService.
					fetchCommerceInventoryBookedQuantity(
						commerceOrderItem.
							getCommerceInventoryBookedQuantityId());

			if (commerceInventoryBookedQuantity == null) {
				continue;
			}

			User currentUser = _userService.getCurrentUser();

			_commerceInventoryBookedQuantityLocalService.
				restockCommerceInventoryBookedQuantity(
					currentUser.getUserId(),
					commerceOrderItem.getCommerceInventoryBookedQuantityId(),
					HashMapBuilder.put(
						CommerceInventoryAuditTypeConstants.ORDER_ID,
						String.valueOf(commerceOrderItem.getCommerceOrderId())
					).put(
						CommerceInventoryAuditTypeConstants.ORDER_ITEM_ID,
						String.valueOf(
							commerceOrderItem.getCommerceOrderItemId())
					).build());
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