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
 * Provides a wrapper for {@link CommerceWarehouseService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseService
 * @generated
 */
@ProviderType
public class CommerceWarehouseServiceWrapper implements CommerceWarehouseService,
	ServiceWrapper<CommerceWarehouseService> {
	public CommerceWarehouseServiceWrapper(
		CommerceWarehouseService commerceWarehouseService) {
		_commerceWarehouseService = commerceWarehouseService;
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse addCommerceWarehouse(
		java.lang.String name, java.lang.String description,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseService.addCommerceWarehouse(name,
			description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, latitude, longitude,
			serviceContext);
	}

	@Override
	public void deleteCommerceWarehouse(long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceWarehouseService.deleteCommerceWarehouse(commerceWarehouseId);
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse geolocateCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseService.geolocateCommerceWarehouse(commerceWarehouseId);
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse getCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseService.getCommerceWarehouse(commerceWarehouseId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return _commerceWarehouseService.getCommerceWarehouses(groupId, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseService.getCommerceWarehouses(groupId,
			commerceCountryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehousesCount(long groupId, long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseService.getCommerceWarehousesCount(groupId,
			commerceCountryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceWarehouseService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> search(
		long groupId, java.lang.String keywords, long commerceCountryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseService.search(groupId, keywords,
			commerceCountryId, start, end, orderByComparator);
	}

	@Override
	public int searchCount(long groupId, java.lang.String keywords,
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseService.searchCount(groupId, keywords,
			commerceCountryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse updateCommerceWarehouse(
		long commerceWarehouseId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, double latitude, double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseService.updateCommerceWarehouse(commerceWarehouseId,
			name, description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, latitude, longitude,
			serviceContext);
	}

	@Override
	public CommerceWarehouseService getWrappedService() {
		return _commerceWarehouseService;
	}

	@Override
	public void setWrappedService(
		CommerceWarehouseService commerceWarehouseService) {
		_commerceWarehouseService = commerceWarehouseService;
	}

	private CommerceWarehouseService _commerceWarehouseService;
}