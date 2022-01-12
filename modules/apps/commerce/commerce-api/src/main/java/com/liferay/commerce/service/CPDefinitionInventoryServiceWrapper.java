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
			boolean displayStockQuantity, int minStockQuantity,
			boolean backOrders, int minOrderQuantity, int maxOrderQuantity,
			String allowedOrderQuantities, int multipleOrderQuantity)
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

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CPDefinitionInventory updateCPDefinitionInventory(
			long groupId, long cpDefinitionInventoryId,
			String cpDefinitionInventoryEngine, String lowStockActivity,
			boolean displayAvailability, boolean displayStockQuantity,
			int minStockQuantity, boolean backOrders, int minOrderQuantity,
			int maxOrderQuantity, String allowedOrderQuantities,
			int multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryService.updateCPDefinitionInventory(
			groupId, cpDefinitionInventoryId, cpDefinitionInventoryEngine,
			lowStockActivity, displayAvailability, displayStockQuantity,
			minStockQuantity, backOrders, minOrderQuantity, maxOrderQuantity,
			allowedOrderQuantities, multipleOrderQuantity);
	}

	@Override
	public CPDefinitionInventory updateCPDefinitionInventory(
			long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, int minStockQuantity,
			boolean backOrders, int minOrderQuantity, int maxOrderQuantity,
			String allowedOrderQuantities, int multipleOrderQuantity)
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