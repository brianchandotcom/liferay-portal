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
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 */
@Component(
	immediate = true,
	property = "destination.name=" + CommerceDestinationNames.STOCK_QUANTITY,
	service = MessageListener.class
)
public class CommerceStockQuantityMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long cpInstanceId = message.getLong("cpInstanceId");

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return;
		}

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

		int stockQuantity = _commerceInventoryEngine.getStockQuantity(
			cpInstance.getCompanyId(), cpInstance.getSku());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		if (stockQuantity <= cpDefinitionInventoryEngine.getMinStockQuantity(
				cpInstance)) {

			commerceLowStockActivity.execute(cpInstance);
		}
	}

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CommerceLowStockActivityRegistry _commerceLowStockActivityRegistry;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}