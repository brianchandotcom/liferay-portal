/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceInventoryBookedQuantityService}.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryBookedQuantityService
 * @generated
 */
public class CommerceInventoryBookedQuantityServiceWrapper
	implements CommerceInventoryBookedQuantityService,
			   ServiceWrapper<CommerceInventoryBookedQuantityService> {

	public CommerceInventoryBookedQuantityServiceWrapper() {
		this(null);
	}

	public CommerceInventoryBookedQuantityServiceWrapper(
		CommerceInventoryBookedQuantityService
			commerceInventoryBookedQuantityService) {

		_commerceInventoryBookedQuantityService =
			commerceInventoryBookedQuantityService;
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity>
				getCommerceInventoryBookedQuantities(
					long companyId, String sku, String unitOfMeasureKey,
					int start, int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceInventoryBookedQuantityService.
			getCommerceInventoryBookedQuantities(
				companyId, sku, unitOfMeasureKey, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity>
				getCommerceInventoryBookedQuantities(
					long companyId, String keywords, String sku,
					String unitOfMeasureKey, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryBookedQuantityService.
			getCommerceInventoryBookedQuantities(
				companyId, keywords, sku, unitOfMeasureKey, start, end);
	}

	@Override
	public int getCommerceInventoryBookedQuantitiesCount(
			long companyId, String sku, String unitOfMeasureKey)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceInventoryBookedQuantityService.
			getCommerceInventoryBookedQuantitiesCount(
				companyId, sku, unitOfMeasureKey);
	}

	@Override
	public int getCommerceInventoryBookedQuantitiesCount(
			long companyId, String keywords, String sku,
			String unitOfMeasureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryBookedQuantityService.
			getCommerceInventoryBookedQuantitiesCount(
				companyId, keywords, sku, unitOfMeasureKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceInventoryBookedQuantityService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommerceInventoryBookedQuantityService getWrappedService() {
		return _commerceInventoryBookedQuantityService;
	}

	@Override
	public void setWrappedService(
		CommerceInventoryBookedQuantityService
			commerceInventoryBookedQuantityService) {

		_commerceInventoryBookedQuantityService =
			commerceInventoryBookedQuantityService;
	}

	private CommerceInventoryBookedQuantityService
		_commerceInventoryBookedQuantityService;

}