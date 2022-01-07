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

package com.liferay.commerce.product.internal.availability;

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.availability.CPAvailabilityChecker;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = CPAvailabilityChecker.class
)
public class CPAvailabilityCheckerImpl implements CPAvailabilityChecker {

	@Override
	public boolean check(
			long commerceChannelGroupId, CPInstance cpInstance, int quantity)
		throws PortalException {

		if (isAvailable(commerceChannelGroupId, cpInstance, quantity) &&
			isPurchasable(cpInstance)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isAvailable(
			long commerceChannelGroupId, CPInstance cpInstance, int quantity)
		throws PortalException {

		if (cpInstance == null) {
			return false;
		}

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		if (cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {
			return true;
		}

		int stockQuantity;

		if (commerceChannelGroupId > 0) {
			stockQuantity = _commerceInventoryEngine.getStockQuantity(
				cpInstance.getCompanyId(), commerceChannelGroupId,
				cpInstance.getSku());
		}
		else {
			stockQuantity = _commerceInventoryEngine.getStockQuantity(
				cpInstance.getCompanyId(), cpInstance.getSku());
		}

		if (quantity > stockQuantity) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isPurchasable(CPInstance cpInstance) throws PortalException {
		if (cpInstance == null) {
			return false;
		}

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		if (!cpDefinition.isApproved() || !cpInstance.isApproved() ||
			!cpInstance.isPublished() || !cpInstance.isPurchasable()) {

			return false;
		}

		return true;
	}

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}