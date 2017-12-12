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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.service.base.CommerceWarehouseServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseServiceImpl
	extends CommerceWarehouseServiceBaseImpl {

	@Override
	public CommerceWarehouse addCommerceWarehouse(
			String name, String description, String street1, String street2,
			String street3, String city, String zip, long commerceRegionId,
			long commerceCountryId, double latitude, double longitude,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_WAREHOUSES);

		return commerceWarehouseLocalService.addCommerceWarehouse(
			name, description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, latitude, longitude,
			serviceContext);
	}

	@Override
	public void deleteCommerceWarehouse(long commerceWarehouseId)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehouseLocalService.getCommerceWarehouse(
				commerceWarehouseId);

		CommercePermission.check(
			getPermissionChecker(), commerceWarehouse.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_WAREHOUSES);

		commerceWarehouseLocalService.deleteCommerceWarehouse(
			commerceWarehouse);
	}

	@Override
	public CommerceWarehouse geolocateCommerceWarehouse(
			long commerceWarehouseId)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehouseLocalService.getCommerceWarehouse(
				commerceWarehouseId);

		CommercePermission.check(
			getPermissionChecker(), commerceWarehouse.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_WAREHOUSES);

		return commerceWarehouseLocalService.geolocateCommerceWarehouse(
			commerceWarehouse.getCommerceWarehouseId());
	}

	@Override
	public CommerceWarehouse getCommerceWarehouse(long commerceWarehouseId)
		throws PortalException {

		return commerceWarehouseLocalService.getCommerceWarehouse(
			commerceWarehouseId);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
		long groupId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {

		return commerceWarehouseLocalService.getCommerceWarehouses(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
			long groupId, long commerceCountryId, int start, int end,
			OrderByComparator<CommerceWarehouse> orderByComparator)
		throws PortalException {

		return commerceWarehouseLocalService.getCommerceWarehouses(
			groupId, commerceCountryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehousesCount(long groupId, long commerceCountryId)
		throws PortalException {

		return commerceWarehouseLocalService.getCommerceWarehousesCount(
			groupId, commerceCountryId);
	}

	@Override
	public List<CommerceWarehouse> search(
			long groupId, String keywords, long commerceCountryId, int start,
			int end, OrderByComparator<CommerceWarehouse> orderByComparator)
		throws PortalException {

		return commerceWarehouseLocalService.search(
			groupId, keywords, commerceCountryId, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(
			long groupId, String keywords, long commerceCountryId)
		throws PortalException {

		return commerceWarehouseLocalService.searchCount(
			groupId, keywords, commerceCountryId);
	}

	@Override
	public CommerceWarehouse updateCommerceWarehouse(
			long commerceWarehouseId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			double latitude, double longitude, ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehouseLocalService.getCommerceWarehouse(
				commerceWarehouseId);

		CommercePermission.check(
			getPermissionChecker(), commerceWarehouse.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_WAREHOUSES);

		return commerceWarehouseLocalService.updateCommerceWarehouse(
			commerceWarehouse.getCommerceWarehouseId(), name, description,
			street1, street2, street3, city, zip, commerceRegionId,
			commerceCountryId, latitude, longitude, serviceContext);
	}

}