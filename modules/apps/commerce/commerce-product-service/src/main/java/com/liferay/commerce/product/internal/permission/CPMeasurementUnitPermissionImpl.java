/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.permission;

import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.permission.CPMeasurementUnitPermission;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = CPMeasurementUnitPermission.class)
public class CPMeasurementUnitPermissionImpl
	implements CPMeasurementUnitPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CPMeasurementUnit cpMeasurementUnit, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpMeasurementUnit, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPMeasurementUnit.class.getName(),
				cpMeasurementUnit.getCPMeasurementUnitId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpMeasurementUnitId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpMeasurementUnitId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPMeasurementUnit.class.getName(),
				cpMeasurementUnitId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CPMeasurementUnit cpMeasurementUnit, String actionId)
		throws PortalException {

		return contains(
			permissionChecker, cpMeasurementUnit.getCPMeasurementUnitId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpMeasurementUnitId,
			String actionId)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitLocalService.fetchCPMeasurementUnit(
				cpMeasurementUnitId);

		if (cpMeasurementUnit == null) {
			return false;
		}

		return _contains(permissionChecker, cpMeasurementUnit, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] cpMeasurementUnitIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(cpMeasurementUnitIds)) {
			return false;
		}

		for (long cpMeasurementUnitId : cpMeasurementUnitIds) {
			if (!contains(permissionChecker, cpMeasurementUnitId, actionId)) {
				return false;
			}
		}

		return true;
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CPMeasurementUnit cpMeasurementUnit, String actionId)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin(
				cpMeasurementUnit.getCompanyId()) ||
			permissionChecker.hasOwnerPermission(
				cpMeasurementUnit.getCompanyId(),
				CPMeasurementUnit.class.getName(),
				cpMeasurementUnit.getCPMeasurementUnitId(),
				cpMeasurementUnit.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, CPMeasurementUnit.class.getName(),
			cpMeasurementUnit.getCPMeasurementUnitId(), actionId);
	}

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

}