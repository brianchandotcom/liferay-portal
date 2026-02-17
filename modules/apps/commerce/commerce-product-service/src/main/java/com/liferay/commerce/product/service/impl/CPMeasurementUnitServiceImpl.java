/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.base.CPMeasurementUnitServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CPMeasurementUnit"
	},
	service = AopService.class
)
public class CPMeasurementUnitServiceImpl
	extends CPMeasurementUnitServiceBaseImpl {

	@Override
	public CPMeasurementUnit addCPMeasurementUnit(
			String externalReferenceCode, Map<Locale, String> nameMap,
			String key, double rate, boolean primary, double priority, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_hasPortletResourcePermission(
			CPActionKeys.ADD_COMMERCE_PRODUCT_MEASUREMENT_UNIT, getUser());

		return cpMeasurementUnitLocalService.addCPMeasurementUnit(
			externalReferenceCode, nameMap, key, rate, primary, priority, type,
			serviceContext);
	}

	@Override
	public void deleteCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException {

		_cpMeasurementUnitModelResourcePermission.check(
			getPermissionChecker(), cpMeasurementUnitId, ActionKeys.DELETE);

		cpMeasurementUnitLocalService.deleteCPMeasurementUnit(
			cpMeasurementUnitId);
	}

	@Override
	public CPMeasurementUnit fetchCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			cpMeasurementUnitLocalService.fetchCPMeasurementUnit(
				cpMeasurementUnitId);

		if (cpMeasurementUnit != null) {
			_cpMeasurementUnitModelResourcePermission.check(
				getPermissionChecker(), cpMeasurementUnitId, ActionKeys.VIEW);
		}

		return cpMeasurementUnit;
	}

	@Override
	public CPMeasurementUnit fetchCPMeasurementUnit(long companyId, String key)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			cpMeasurementUnitLocalService.fetchCPMeasurementUnit(
				companyId, key);

		if (cpMeasurementUnit != null) {
			_cpMeasurementUnitModelResourcePermission.check(
				getPermissionChecker(),
				cpMeasurementUnit.getCPMeasurementUnitId(), ActionKeys.VIEW);
		}

		return cpMeasurementUnit;
	}

	@Override
	public CPMeasurementUnit fetchCPMeasurementUnitByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			cpMeasurementUnitLocalService.
				fetchCPMeasurementUnitByExternalReferenceCode(
					externalReferenceCode, companyId);

		if (cpMeasurementUnit != null) {
			_cpMeasurementUnitModelResourcePermission.check(
				getPermissionChecker(),
				cpMeasurementUnit.getCPMeasurementUnitId(), ActionKeys.VIEW);
		}

		return cpMeasurementUnit;
	}

	@Override
	public CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
			long companyId, int type)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			cpMeasurementUnitLocalService.fetchPrimaryCPMeasurementUnit(
				companyId, type);

		if (cpMeasurementUnit != null) {
			_cpMeasurementUnitModelResourcePermission.check(
				getPermissionChecker(),
				cpMeasurementUnit.getCPMeasurementUnitId(), ActionKeys.VIEW);
		}

		return cpMeasurementUnit;
	}

	@Override
	public CPMeasurementUnit getCPMeasurementUnit(long cpMeasurementUnitId)
		throws PortalException {

		_cpMeasurementUnitModelResourcePermission.check(
			getPermissionChecker(), cpMeasurementUnitId, ActionKeys.VIEW);

		return cpMeasurementUnitLocalService.getCPMeasurementUnit(
			cpMeasurementUnitId);
	}

	@Override
	public List<CPMeasurementUnit> getCPMeasurementUnits(
			long companyId, int type, int start, int end,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws PortalException {

		_hasPortletResourcePermission(
			CPActionKeys.VIEW_COMMERCE_PRODUCT_MEASUREMENT_UNITS, getUser());

		return cpMeasurementUnitLocalService.getCPMeasurementUnits(
			companyId, type, start, end, orderByComparator);
	}

	@Override
	public List<CPMeasurementUnit> getCPMeasurementUnits(
			long companyId, int start, int end,
			OrderByComparator<CPMeasurementUnit> orderByComparator)
		throws PortalException {

		_hasPortletResourcePermission(
			CPActionKeys.VIEW_COMMERCE_PRODUCT_MEASUREMENT_UNITS, getUser());

		return cpMeasurementUnitLocalService.getCPMeasurementUnits(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCPMeasurementUnitsCount(long companyId)
		throws PortalException {

		_hasPortletResourcePermission(
			CPActionKeys.VIEW_COMMERCE_PRODUCT_MEASUREMENT_UNITS, getUser());

		return cpMeasurementUnitLocalService.getCPMeasurementUnitsCount(
			companyId);
	}

	@Override
	public int getCPMeasurementUnitsCount(long companyId, int type)
		throws PortalException {

		_hasPortletResourcePermission(
			CPActionKeys.VIEW_COMMERCE_PRODUCT_MEASUREMENT_UNITS, getUser());

		return cpMeasurementUnitLocalService.getCPMeasurementUnitsCount(
			companyId, type);
	}

	@Override
	public CPMeasurementUnit setPrimary(
			long cpMeasurementUnitId, boolean primary)
		throws PortalException {

		_cpMeasurementUnitModelResourcePermission.check(
			getPermissionChecker(), cpMeasurementUnitId, ActionKeys.UPDATE);

		return cpMeasurementUnitLocalService.setPrimary(
			cpMeasurementUnitId, primary);
	}

	@Override
	public CPMeasurementUnit updateCPMeasurementUnit(
			String externalReferenceCode, long cpMeasurementUnitId,
			Map<Locale, String> nameMap, String key, double rate,
			boolean primary, double priority, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_cpMeasurementUnitModelResourcePermission.check(
			getPermissionChecker(), cpMeasurementUnitId, ActionKeys.UPDATE);

		return cpMeasurementUnitLocalService.updateCPMeasurementUnit(
			externalReferenceCode, cpMeasurementUnitId, nameMap, key, rate,
			primary, priority, type, serviceContext);
	}

	private void _hasPortletResourcePermission(String actionId, User user)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_cpMeasurementUnitModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), user.getGroupId(), actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPMeasurementUnit)"
	)
	private ModelResourcePermission<CPMeasurementUnit>
		_cpMeasurementUnitModelResourcePermission;

}