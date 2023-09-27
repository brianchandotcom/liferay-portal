/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionInventoryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryService
 * @generated
 */
public class CPDefinitionInventoryServiceWrapper
	implements CPDefinitionInventoryService,
			   ServiceWrapper<CPDefinitionInventoryService> {

	public CPDefinitionInventoryServiceWrapper() {
		this(null);
	}

	public CPDefinitionInventoryServiceWrapper(
		CPDefinitionInventoryService cpDefinitionInventoryService) {

		_cpDefinitionInventoryService = cpDefinitionInventoryService;
	}

	@Override
	public CPDefinitionInventory addCPDefinitionInventory(
			long cpDefinitionId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, java.math.BigDecimal minStockQuantity,
			boolean backOrders, java.math.BigDecimal minOrderQuantity,
			java.math.BigDecimal maxOrderQuantity,
			String allowedOrderQuantities,
			java.math.BigDecimal multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryService.addCPDefinitionInventory(
			cpDefinitionId, cpDefinitionInventoryEngine, lowStockActivity,
			displayAvailability, displayStockQuantity, minStockQuantity,
			backOrders, minOrderQuantity, maxOrderQuantity,
			allowedOrderQuantities, multipleOrderQuantity);
	}

	@Override
	public void deleteCPDefinitionInventory(long cpDefinitionInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpDefinitionInventoryService.deleteCPDefinitionInventory(
			cpDefinitionInventoryId);
	}

	@Override
	public CPDefinitionInventory fetchCPDefinitionInventoryByCPDefinitionId(
			long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryService.
			fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionInventoryService.getOSGiServiceIdentifier();
	}

	@Override
	public CPDefinitionInventory updateCPDefinitionInventory(
			long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, java.math.BigDecimal minStockQuantity,
			boolean backOrders, java.math.BigDecimal minOrderQuantity,
			java.math.BigDecimal maxOrderQuantity,
			String allowedOrderQuantities,
			java.math.BigDecimal multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryService.updateCPDefinitionInventory(
			cpDefinitionInventoryId, cpDefinitionInventoryEngine,
			lowStockActivity, displayAvailability, displayStockQuantity,
			minStockQuantity, backOrders, minOrderQuantity, maxOrderQuantity,
			allowedOrderQuantities, multipleOrderQuantity);
	}

	@Override
	public CPDefinitionInventoryService getWrappedService() {
		return _cpDefinitionInventoryService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionInventoryService cpDefinitionInventoryService) {

		_cpDefinitionInventoryService = cpDefinitionInventoryService;
	}

	private CPDefinitionInventoryService _cpDefinitionInventoryService;

}