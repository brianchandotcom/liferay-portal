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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionInventoryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryService
 * @generated
 */
@ProviderType
public class CPDefinitionInventoryServiceWrapper
	implements CPDefinitionInventoryService,
		ServiceWrapper<CPDefinitionInventoryService> {
	public CPDefinitionInventoryServiceWrapper(
		CPDefinitionInventoryService cpDefinitionInventoryService) {
		_cpDefinitionInventoryService = cpDefinitionInventoryService;
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionInventory addCPDefinitionInventory(
		long cpDefinitionId, java.lang.String cpDefinitionInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		java.lang.String allowedOrderQuantities, int multipleOrderQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryService.addCPDefinitionInventory(cpDefinitionId,
			cpDefinitionInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minOrderQuantity, maxOrderQuantity, allowedOrderQuantities,
			multipleOrderQuantity, serviceContext);
	}

	@Override
	public void deleteCPDefinitionInventory(long cpDefinitionInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionInventoryService.deleteCPDefinitionInventory(cpDefinitionInventoryId);
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionInventory fetchCPDefinitionInventory(
		long cpDefinitionInventoryId) {
		return _cpDefinitionInventoryService.fetchCPDefinitionInventory(cpDefinitionInventoryId);
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionInventory fetchCPDefinitionInventoryByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryService.fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionInventoryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionInventory updateCPDefinitionInventory(
		long cpDefinitionInventoryId,
		java.lang.String cpDefinitionInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		java.lang.String allowedOrderQuantities, int multipleOrderQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryService.updateCPDefinitionInventory(cpDefinitionInventoryId,
			cpDefinitionInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minOrderQuantity, maxOrderQuantity, allowedOrderQuantities,
			multipleOrderQuantity, serviceContext);
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