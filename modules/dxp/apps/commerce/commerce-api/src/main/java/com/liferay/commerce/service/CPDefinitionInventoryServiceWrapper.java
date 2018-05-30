/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		long cpDefinitionId, String cpDefinitionInventoryEngine,
		String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		String allowedOrderQuantities, int multipleOrderQuantity,
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
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionInventoryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionInventory updateCPDefinitionInventory(
		long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
		String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		String allowedOrderQuantities, int multipleOrderQuantity,
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