/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPConfigurationEntryService}.
 *
 * @author Marco Leo
 * @see CPConfigurationEntryService
 * @generated
 */
public class CPConfigurationEntryServiceWrapper
	implements CPConfigurationEntryService,
			   ServiceWrapper<CPConfigurationEntryService> {

	public CPConfigurationEntryServiceWrapper() {
		this(null);
	}

	public CPConfigurationEntryServiceWrapper(
		CPConfigurationEntryService cpConfigurationEntryService) {

		_cpConfigurationEntryService = cpConfigurationEntryService;
	}

	@Override
	public CPConfigurationEntry addCPConfigurationEntry(
			String externalReferenceCode, long groupId, long classNameId,
			long classPK, long cpConfigurationListId,
			String allowedOrderQuantities, boolean backOrders,
			long commerceAvailabilityEstimateId,
			String cpDefinitionInventoryEngine, boolean displayAvailability,
			boolean displayStockQuantity, String lowStockActivity,
			java.math.BigDecimal maxOrderQuantity,
			java.math.BigDecimal minOrderQuantity,
			java.math.BigDecimal minStockQuantity,
			java.math.BigDecimal multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntryService.addCPConfigurationEntry(
			externalReferenceCode, groupId, classNameId, classPK,
			cpConfigurationListId, allowedOrderQuantities, backOrders,
			commerceAvailabilityEstimateId, cpDefinitionInventoryEngine,
			displayAvailability, displayStockQuantity, lowStockActivity,
			maxOrderQuantity, minOrderQuantity, minStockQuantity,
			multipleOrderQuantity);
	}

	@Override
	public void deleteCPConfigurationEntry(long cpConfigurationEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpConfigurationEntryService.deleteCPConfigurationEntry(
			cpConfigurationEntryId);
	}

	@Override
	public CPConfigurationEntry getCPConfigurationEntry(
			long cpConfigurationEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntryService.getCPConfigurationEntry(
			cpConfigurationEntryId);
	}

	@Override
	public CPConfigurationEntry getCPConfigurationEntry(
			long classNameId, long classPK, long cpConfigurationListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntryService.getCPConfigurationEntry(
			classNameId, classPK, cpConfigurationListId);
	}

	@Override
	public CPConfigurationEntry getCPConfigurationEntryByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntryService.
			getCPConfigurationEntryByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpConfigurationEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public CPConfigurationEntry updateCPConfigurationEntry(
			String externalReferenceCode, long cpConfigurationEntryId,
			long cpConfigurationListId, String allowedOrderQuantities,
			boolean backOrders, long commerceAvailabilityEstimateId,
			String cpDefinitionInventoryEngine, boolean displayAvailability,
			boolean displayStockQuantity, String lowStockActivity,
			java.math.BigDecimal maxOrderQuantity,
			java.math.BigDecimal minOrderQuantity,
			java.math.BigDecimal minStockQuantity,
			java.math.BigDecimal multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpConfigurationEntryService.updateCPConfigurationEntry(
			externalReferenceCode, cpConfigurationEntryId,
			cpConfigurationListId, allowedOrderQuantities, backOrders,
			commerceAvailabilityEstimateId, cpDefinitionInventoryEngine,
			displayAvailability, displayStockQuantity, lowStockActivity,
			maxOrderQuantity, minOrderQuantity, minStockQuantity,
			multipleOrderQuantity);
	}

	@Override
	public CPConfigurationEntryService getWrappedService() {
		return _cpConfigurationEntryService;
	}

	@Override
	public void setWrappedService(
		CPConfigurationEntryService cpConfigurationEntryService) {

		_cpConfigurationEntryService = cpConfigurationEntryService;
	}

	private CPConfigurationEntryService _cpConfigurationEntryService;

}