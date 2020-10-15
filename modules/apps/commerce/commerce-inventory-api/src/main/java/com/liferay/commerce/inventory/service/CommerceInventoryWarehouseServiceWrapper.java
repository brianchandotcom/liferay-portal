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
 * Provides a wrapper for {@link CommerceInventoryWarehouseService}.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseService
 * @generated
 */
public class CommerceInventoryWarehouseServiceWrapper
	implements CommerceInventoryWarehouseService,
			   ServiceWrapper<CommerceInventoryWarehouseService> {

	public CommerceInventoryWarehouseServiceWrapper(
		CommerceInventoryWarehouseService commerceInventoryWarehouseService) {

		_commerceInventoryWarehouseService = commerceInventoryWarehouseService;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommerceInventoryWarehouse(String, String, String,
	 boolean, String, String, String, String, String, String,
	 String, double, double, serviceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			addCommerceInventoryWarehouse(
				String name, String description, boolean active, String street1,
				String street2, String street3, String city, String zip,
				String commerceRegionCode, String commerceCountryCode,
				double latitude, double longitude, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.addCommerceInventoryWarehouse(
			name, description, active, street1, street2, street3, city, zip,
			commerceRegionCode, commerceCountryCode, latitude, longitude,
			externalReferenceCode, serviceContext);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			addCommerceInventoryWarehouse(
				String externalReferenceCode, String name, String description,
				boolean active, String street1, String street2, String street3,
				String city, String zip, String commerceRegionCode,
				String commerceCountryCode, double latitude, double longitude,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.addCommerceInventoryWarehouse(
			externalReferenceCode, name, description, active, street1, street2,
			street3, city, zip, commerceRegionCode, commerceCountryCode,
			latitude, longitude, serviceContext);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			deleteCommerceInventoryWarehouse(long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			deleteCommerceInventoryWarehouse(commerceInventoryWarehouseId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			geolocateCommerceInventoryWarehouse(
				long commerceInventoryWarehouseId, double latitude,
				double longitude)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			geolocateCommerceInventoryWarehouse(
				commerceInventoryWarehouseId, latitude, longitude);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			getCommerceInventoryWarehouse(long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
			commerceInventoryWarehouseId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouse>
				getCommerceInventoryWarehouses(
					long companyId, boolean active, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.inventory.model.
							CommerceInventoryWarehouse> orderByComparator)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceInventoryWarehouseService.
			getCommerceInventoryWarehouses(
				companyId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouse>
				getCommerceInventoryWarehouses(
					long companyId, boolean active, String commerceCountryCode,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.inventory.model.
							CommerceInventoryWarehouse> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			getCommerceInventoryWarehouses(
				companyId, active, commerceCountryCode, start, end,
				orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouse>
				getCommerceInventoryWarehouses(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.inventory.model.
							CommerceInventoryWarehouse> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			getCommerceInventoryWarehouses(
				companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouse>
				getCommerceInventoryWarehouses(
					long companyId, long groupId, boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			getCommerceInventoryWarehouses(companyId, groupId, active);
	}

	@Override
	public int getCommerceInventoryWarehousesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			getCommerceInventoryWarehousesCount(companyId);
	}

	@Override
	public int getCommerceInventoryWarehousesCount(
			long companyId, boolean active, String commerceCountryCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			getCommerceInventoryWarehousesCount(
				companyId, active, commerceCountryCode);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceInventoryWarehouseService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouse>
				searchCommerceInventoryWarehouses(
					long companyId, Boolean active, String commerceCountryCode,
					String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			searchCommerceInventoryWarehouses(
				companyId, active, commerceCountryCode, keywords, start, end,
				sort);
	}

	@Override
	public int searchCommerceInventoryWarehousesCount(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			searchCommerceInventoryWarehousesCount(
				companyId, active, commerceCountryCode, keywords);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			setActive(long commerceInventoryWarehouseId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.setActive(
			commerceInventoryWarehouseId, active);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			updateCommerceInventoryWarehouse(
				long commerceInventoryWarehouseId, String name,
				String description, boolean active, String street1,
				String street2, String street3, String city, String zip,
				String commerceRegionCode, String commerceCountryCode,
				double latitude, double longitude, long mvccVersion,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseService.
			updateCommerceInventoryWarehouse(
				commerceInventoryWarehouseId, name, description, active,
				street1, street2, street3, city, zip, commerceRegionCode,
				commerceCountryCode, latitude, longitude, mvccVersion,
				serviceContext);
	}

	@Override
	public CommerceInventoryWarehouseService getWrappedService() {
		return _commerceInventoryWarehouseService;
	}

	@Override
	public void setWrappedService(
		CommerceInventoryWarehouseService commerceInventoryWarehouseService) {

		_commerceInventoryWarehouseService = commerceInventoryWarehouseService;
	}

	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

}