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
 * Provides a wrapper for {@link CommerceInventoryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceInventoryService
 * @generated
 */
@ProviderType
public class CommerceInventoryServiceWrapper implements CommerceInventoryService,
	ServiceWrapper<CommerceInventoryService> {
	public CommerceInventoryServiceWrapper(
		CommerceInventoryService commerceInventoryService) {
		_commerceInventoryService = commerceInventoryService;
	}

	@Override
	public com.liferay.commerce.model.CommerceInventory addCommerceInventory(
		long cpDefinitionId, java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryService.addCommerceInventory(cpDefinitionId,
			commerceInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minCartQuantity, maxCartQuantity, allowedCartQuantities,
			multipleCartQuantity, serviceContext);
	}

	@Override
	public void deleteCommerceInventory(long commerceInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceInventoryService.deleteCommerceInventory(commerceInventoryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceInventory fetchCommerceInventory(
		long commerceInventoryId) {
		return _commerceInventoryService.fetchCommerceInventory(commerceInventoryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceInventory fetchCommerceInventoryByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryService.fetchCommerceInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceInventoryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceInventory updateCommerceInventory(
		long commerceInventoryId, java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryService.updateCommerceInventory(commerceInventoryId,
			commerceInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minCartQuantity, maxCartQuantity, allowedCartQuantities,
			multipleCartQuantity, serviceContext);
	}

	@Override
	public CommerceInventoryService getWrappedService() {
		return _commerceInventoryService;
	}

	@Override
	public void setWrappedService(
		CommerceInventoryService commerceInventoryService) {
		_commerceInventoryService = commerceInventoryService;
	}

	private CommerceInventoryService _commerceInventoryService;
}