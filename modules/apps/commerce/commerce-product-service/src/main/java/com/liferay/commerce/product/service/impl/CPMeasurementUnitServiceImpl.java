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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.base.CPMeasurementUnitServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPMeasurementUnitServiceImpl
	extends CPMeasurementUnitServiceBaseImpl {

	@Override
	public CPMeasurementUnit addCPMeasurementUnit(
			Map<Locale, String> nameMap, String key, double rate,
			boolean primary, double priority, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		return cpMeasurementUnitLocalService.addCPMeasurementUnit(
			null, nameMap, key, rate, primary, priority, type, serviceContext);
	}

	@Override
	public void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		cpMeasurementUnitLocalService.deleteCPMeasurementUnit(
			cpMeasurementUnitId);
	}

	@Override
	public CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
			long companyId, int type)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		return cpMeasurementUnitLocalService.fetchPrimaryCPMeasurementUnit(
			companyId, type);
	}

	@Override
	public CPMeasurementUnit getCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		return cpMeasurementUnitLocalService.getCPMeasurementUnit(
			cpMeasurementUnitId);
	}

	@Override
	public List<CPMeasurementUnit> getCPMeasurementUnits(
			long companyId, int type, int start, int end,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		return cpMeasurementUnitLocalService.getCPMeasurementUnits(
			companyId, type, start, end, orderByComparator);
	}

	@Override
	public List<CPMeasurementUnit> getCPMeasurementUnits(
			long companyId, int start, int end,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		return cpMeasurementUnitLocalService.getCPMeasurementUnits(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCPMeasurementUnitsCount(long companyId)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		return cpMeasurementUnitLocalService.getCPMeasurementUnitsCount(
			companyId);
	}

	@Override
	public int getCPMeasurementUnitsCount(long companyId, int type)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		return cpMeasurementUnitLocalService.getCPMeasurementUnitsCount(
			companyId, type);
	}

	@Override
	public CPMeasurementUnit setPrimary(
			long cpMeasurementUnitId, boolean primary)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		return cpMeasurementUnitLocalService.setPrimary(
			cpMeasurementUnitId, primary);
	}

	@Override
	public CPMeasurementUnit updateCPMeasurementUnit(
			long cpMeasurementUnitId, Map<Locale, String> nameMap, String key,
			double rate, boolean primary, double priority, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);

		return cpMeasurementUnitLocalService.updateCPMeasurementUnit(
			cpMeasurementUnitId, nameMap, key, rate, primary, priority, type,
			serviceContext);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CPMeasurementUnitServiceImpl.class,
				"_portletResourcePermission",
				CPConstants.RESOURCE_NAME_PRODUCT);

}