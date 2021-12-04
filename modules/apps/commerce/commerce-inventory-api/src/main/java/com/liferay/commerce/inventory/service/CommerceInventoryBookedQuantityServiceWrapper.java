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
					long companyId, String sku, int start, int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceInventoryBookedQuantityService.
			getCommerceInventoryBookedQuantities(companyId, sku, start, end);
	}

	@Override
	public int getCommerceInventoryBookedQuantitiesCount(
			long companyId, String sku)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceInventoryBookedQuantityService.
			getCommerceInventoryBookedQuantitiesCount(companyId, sku);
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