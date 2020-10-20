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
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.base.CommerceRegionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CommerceRegionServiceImpl extends CommerceRegionServiceBaseImpl {

	@Override
	public CommerceRegion addCommerceRegion(
			long commerceCountryId, String name, String code, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.addCommerceRegion(
			commerceCountryId, name, code, priority, active, serviceContext);
	}

	@Override
	public void deleteCommerceRegion(long commerceRegionId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		commerceRegionLocalService.deleteCommerceRegion(commerceRegionId);
	}

	@Override
	public CommerceRegion getCommerceRegion(long commerceRegionId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.getCommerceRegion(commerceRegionId);
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active) {

		return commerceRegionLocalService.getCommerceRegions(
			commerceCountryId, active);
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
			long commerceCountryId, boolean active, int start, int end,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.getCommerceRegions(
			commerceCountryId, active, start, end, orderByComparator);
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
			long commerceCountryId, int start, int end,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.getCommerceRegions(
			commerceCountryId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceRegion> getCommerceRegions(
			long companyId, String countryTwoLettersISOCode, boolean active)
		throws PortalException {

		return commerceRegionLocalService.getCommerceRegions(
			companyId, countryTwoLettersISOCode, active);
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.getCommerceRegionsCount(
			commerceCountryId);
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.getCommerceRegionsCount(
			commerceCountryId, active);
	}

	@Override
	public CommerceRegion setActive(long commerceRegionId, boolean active)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.setActive(commerceRegionId, active);
	}

	@Override
	public CommerceRegion updateCommerceRegion(
			long commerceRegionId, String name, String code, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommerceRegion commerceRegion =
			commerceRegionPersistence.findByPrimaryKey(commerceRegionId);

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_COUNTRIES);

		return commerceRegionLocalService.updateCommerceRegion(
			commerceRegion.getCommerceRegionId(), name, code, priority, active,
			serviceContext);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceRegionServiceImpl.class, "_portletResourcePermission",
				CommerceConstants.ADDRESS_RESOURCE_NAME);

}