/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.availability;

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.availability.CPAvailabilityChecker;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.BigDecimalUtil;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPAvailabilityChecker.class)
public class CPAvailabilityCheckerImpl implements CPAvailabilityChecker {

	@Override
	public boolean check(
			long commerceChannelGroupId, CPInstance cpInstance,
			String unitOfMeasure, BigDecimal quantity)
		throws PortalException {

		if (isAvailable(
				commerceChannelGroupId, cpInstance, unitOfMeasure, quantity) &&
			isPurchasable(cpInstance)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isAvailable(
			long commerceChannelGroupId, CPInstance cpInstance,
			String unitOfMeasure, BigDecimal quantity)
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

		BigDecimal stockQuantity = BigDecimal.ZERO;

		if (commerceChannelGroupId > 0) {
			stockQuantity = _commerceInventoryEngine.getStockQuantity(
				cpInstance.getCompanyId(), cpInstance.getGroupId(),
				commerceChannelGroupId, cpInstance.getSku(), unitOfMeasure);
		}
		else {
			stockQuantity = _commerceInventoryEngine.getStockQuantity(
				cpInstance.getCompanyId(), cpDefinition.getGroupId(),
				cpInstance.getSku(), unitOfMeasure);
		}

		if (BigDecimalUtil.gt(quantity, stockQuantity)) {
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
	private CommerceInventoryEngine _commerceInventoryEngine;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}