/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.internal.messaging;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.BigDecimalUtil;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
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

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrder.getCommerceOrderItems();

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			CommerceVirtualOrderItem commerceVirtualOrderItem =
				_commerceVirtualOrderItemLocalService.
					fetchCommerceVirtualOrderItemByCommerceOrderItemId(
						commerceOrderItem.getCommerceOrderItemId(), false);

			if ((commerceVirtualOrderItem != null) &&
				(orderStatus ==
					commerceVirtualOrderItem.getActivationStatus())) {

				// Set commerce virtual order item active

				_commerceVirtualOrderItemLocalService.setActive(
					commerceVirtualOrderItem.getCommerceVirtualOrderItemId(),
					true);
			}

			if (orderStatus == CommerceOrderConstants.ORDER_STATUS_PENDING) {
				_checkLowStockActivity(commerceOrderItem);
			}
		}
	}

	private void _checkLowStockActivity(CommerceOrderItem commerceOrderItem)
		throws Exception {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			commerceOrderItem.getCPInstanceId());

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CommerceLowStockActivity commerceLowStockActivity =
			_commerceLowStockActivityRegistry.getCommerceLowStockActivity(
				cpDefinitionInventory);

		if (commerceLowStockActivity == null) {
			return;
		}

		BigDecimal stockQuantity = _commerceInventoryEngine.getStockQuantity(
			commerceOrderItem.getCompanyId(), cpInstance.getGroupId(),
			commerceOrderItem.getSku(),
			commerceOrderItem.getUnitOfMeasureKey());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		if (BigDecimalUtil.lte(
				stockQuantity,
				cpDefinitionInventoryEngine.getMinStockQuantity(cpInstance))) {

			commerceLowStockActivity.execute(cpInstance);
		}
	}

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CommerceLowStockActivityRegistry _commerceLowStockActivityRegistry;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceVirtualOrderItemLocalService
		_commerceVirtualOrderItemLocalService;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}