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
			String name, String description, boolean active, String street1,
			String street2, String street3, String city, String zip,
			long commerceRegionId, long commerceCountryId, double latitude,
			double longitude, ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_WAREHOUSES);

		return commerceWarehouseLocalService.addCommerceWarehouse(
			name, description, active, street1, street2, street3, city, zip,
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
	public CommerceWarehouse fetchDefaultCommerceWarehouse(long groupId)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_WAREHOUSES);

		return commerceWarehouseLocalService.fetchDefaultCommerceWarehouse(
			groupId);
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
		long groupId, boolean active, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {

		return commerceWarehouseLocalService.getCommerceWarehouses(
			groupId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
			long groupId, boolean active, long commerceCountryId, int start,
			int end, OrderByComparator<CommerceWarehouse> orderByComparator)
		throws PortalException {

		return commerceWarehouseLocalService.getCommerceWarehouses(
			groupId, active, commerceCountryId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceWarehouse> getCommerceWarehouses(
		long groupId, long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {

		return commerceWarehouseLocalService.getCommerceWarehouses(
			groupId, commerceCountryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehousesCount(
			long groupId, boolean active, long commerceCountryId)
		throws PortalException {

		return commerceWarehouseLocalService.getCommerceWarehousesCount(
			groupId, active, commerceCountryId);
	}

	@Override
	public int getCommerceWarehousesCount(
		long groupId, long commerceCountryId) {

		return commerceWarehouseLocalService.getCommerceWarehousesCount(
			groupId, commerceCountryId);
	}

	@Override
	public List<CommerceWarehouse> search(
			long groupId, String keywords, boolean all, long commerceCountryId,
			int start, int end,
			OrderByComparator<CommerceWarehouse> orderByComparator)
		throws PortalException {

		return commerceWarehouseLocalService.search(
			groupId, keywords, all, commerceCountryId, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(
			long groupId, String keywords, boolean all, long commerceCountryId)
		throws PortalException {

		return commerceWarehouseLocalService.searchCount(
			groupId, keywords, all, commerceCountryId);
	}

	@Override
	public CommerceWarehouse setActive(long commerceWarehouseId, boolean active)
		throws PortalException {

		return commerceWarehouseLocalService.setActive(
			commerceWarehouseId, active);
	}

	@Override
	public CommerceWarehouse updateCommerceWarehouse(
			long commerceWarehouseId, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, long commerceRegionId,
			long commerceCountryId, double latitude, double longitude,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_WAREHOUSES);

		return commerceWarehouseLocalService.updateCommerceWarehouse(
			commerceWarehouseId, name, description, active, street1, street2,
			street3, city, zip, commerceRegionId, commerceCountryId, latitude,
			longitude, serviceContext);
	}

	@Override
	public CommerceWarehouse updateDefaultCommerceWarehouse(
			String name, String street1, String street2, String street3,
			String city, String zip, long commerceRegionId,
			long commerceCountryId, double latitude, double longitude,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_WAREHOUSES);

		return commerceWarehouseLocalService.updateDefaultCommerceWarehouse(
			name, street1, street2, street3, city, zip, commerceRegionId,
			commerceCountryId, latitude, longitude, serviceContext);
	}

}