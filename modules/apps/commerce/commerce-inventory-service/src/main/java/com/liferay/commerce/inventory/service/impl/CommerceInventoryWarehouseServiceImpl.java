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

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.base.CommerceInventoryWarehouseServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryWarehouseServiceImpl
	extends CommerceInventoryWarehouseServiceBaseImpl {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addCommerceInventoryWarehouse(String, String, String,
	 *             boolean, String, String, String, String, String, String,
	 *             String, double, double, serviceContext)}
	 */
	@Deprecated
	@Override
	public CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String name, String description, boolean active, String street1,
			String street2, String street3, String city, String zip,
			String commerceRegionCode, String commerceCountryCode,
			double latitude, double longitude, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommerceInventoryWarehouse(
			externalReferenceCode, name, description, active, street1, street2,
			street3, city, zip, commerceRegionCode, commerceCountryCode,
			latitude, longitude, serviceContext);
	}

	@Override
	public CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String externalReferenceCode, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.ADD_WAREHOUSE);

		return commerceInventoryWarehouseLocalService.
			addCommerceInventoryWarehouse(
				externalReferenceCode, name, description, active, street1,
				street2, street3, city, zip, commerceRegionCode,
				commerceCountryCode, latitude, longitude, serviceContext);
	}

	@Override
	public CommerceInventoryWarehouse deleteCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.DELETE);

		return commerceInventoryWarehouseLocalService.
			deleteCommerceInventoryWarehouse(commerceInventoryWarehouseId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public CommerceInventoryWarehouse fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return fetchByExternalReferenceCode(externalReferenceCode, companyId);
	}

	@Override
	public CommerceInventoryWarehouse fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouseLocalService.
				fetchCommerceInventoryWarehouseByReferenceCode(
					companyId, externalReferenceCode);

		if (commerceInventoryWarehouse != null) {
			_commerceInventoryWarehouseModelResourcePermission.check(
				getPermissionChecker(), commerceInventoryWarehouse,
				ActionKeys.VIEW);
		}

		return commerceInventoryWarehouse;
	}

	@Override
	public CommerceInventoryWarehouse geolocateCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId, double latitude,
			double longitude)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		return commerceInventoryWarehouseLocalService.
			geolocateCommerceInventoryWarehouse(
				commerceInventoryWarehouseId, latitude, longitude);
	}

	@Override
	public CommerceInventoryWarehouse getCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryWarehouseLocalService.
			getCommerceInventoryWarehouse(commerceInventoryWarehouseId);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
			long companyId, boolean active, int start, int end,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws PrincipalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseLocalService.
			getCommerceInventoryWarehouses(
				companyId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
			long companyId, boolean active, String commerceCountryCode,
			int start, int end,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseLocalService.
			getCommerceInventoryWarehouses(
				companyId, active, commerceCountryCode, start, end,
				orderByComparator);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
			long companyId, int start, int end,
			OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseLocalService.
			getCommerceInventoryWarehouses(
				companyId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses(
			long companyId, long groupId, boolean active)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseLocalService.
			getCommerceInventoryWarehouses(companyId, groupId, active);
	}

	@Override
	public int getCommerceInventoryWarehousesCount(long companyId)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseLocalService.
			getCommerceInventoryWarehousesCount(companyId);
	}

	@Override
	public int getCommerceInventoryWarehousesCount(
			long companyId, boolean active, String commerceCountryCode)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseLocalService.
			getCommerceInventoryWarehousesCount(
				companyId, active, commerceCountryCode);
	}

	@Override
	public List<CommerceInventoryWarehouse> searchCommerceInventoryWarehouses(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords, int start, int end, Sort sort)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseLocalService.
			searchCommerceInventoryWarehouses(
				companyId, active, commerceCountryCode, keywords, start, end,
				sort);
	}

	@Override
	public int searchCommerceInventoryWarehousesCount(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseLocalService.
			searchCommerceInventoryWarehousesCount(
				companyId, active, commerceCountryCode, keywords);
	}

	@Override
	public CommerceInventoryWarehouse setActive(
			long commerceInventoryWarehouseId, boolean active)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		return commerceInventoryWarehouseLocalService.setActive(
			commerceInventoryWarehouseId, active);
	}

	@Override
	public CommerceInventoryWarehouse updateCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			long mvccVersion, ServiceContext serviceContext)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		return commerceInventoryWarehouseLocalService.
			updateCommerceInventoryWarehouse(
				commerceInventoryWarehouseId, name, description, active,
				street1, street2, street3, city, zip, commerceRegionCode,
				commerceCountryCode, latitude, longitude, mvccVersion,
				serviceContext);
	}

	private static volatile ModelResourcePermission<CommerceInventoryWarehouse>
		_commerceInventoryWarehouseModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceInventoryWarehouseServiceImpl.class,
				"_commerceInventoryWarehouseModelResourcePermission",
				CommerceInventoryWarehouse.class);

}