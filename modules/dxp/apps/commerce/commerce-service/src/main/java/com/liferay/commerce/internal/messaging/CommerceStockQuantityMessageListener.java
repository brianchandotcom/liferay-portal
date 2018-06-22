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

import com.liferay.commerce.constants.CommerceDestinationNames;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceWarehouseItemLocalService;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "destination.name=" + CommerceDestinationNames.STOCK_QUANTITY,
	service = MessageListener.class
)
public class CommerceStockQuantityMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long commerceWarehouseItemId = message.getLong(
			"commerceWarehouseItemId");

		CommerceWarehouseItem commerceWarehouseItem =
			_commerceWarehouseItemLocalService.getCommerceWarehouseItem(
				commerceWarehouseItemId);

		CPInstance cpInstance = commerceWarehouseItem.getCPInstance();

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

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		int stockQuantity =
			_commerceWarehouseItemLocalService.getCPInstanceQuantity(
				cpInstance.getCPInstanceId());

		if (stockQuantity <=
				cpDefinitionInventoryEngine.getMinStockQuantity(cpInstance)) {

			commerceLowStockActivity.execute(commerceWarehouseItem);
		}
	}

	@Reference
	private CommerceLowStockActivityRegistry _commerceLowStockActivityRegistry;

	@Reference
	private CommerceWarehouseItemLocalService
		_commerceWarehouseItemLocalService;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}