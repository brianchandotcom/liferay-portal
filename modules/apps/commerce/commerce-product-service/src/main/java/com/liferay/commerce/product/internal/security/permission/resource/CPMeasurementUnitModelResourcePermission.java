/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.permission.CPMeasurementUnitPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPMeasurementUnit",
	service = ModelResourcePermission.class
)
public class CPMeasurementUnitModelResourcePermission
	implements ModelResourcePermission<CPMeasurementUnit> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CPMeasurementUnit cpMeasurementUnit, String actionId)
		throws PortalException {

		_cpMeasurementUnitPermission.check(
			permissionChecker, cpMeasurementUnit, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long cpMeasurementUnitId,
			String actionId)
		throws PortalException {

		_cpMeasurementUnitPermission.check(
			permissionChecker, cpMeasurementUnitId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CPMeasurementUnit cpMeasurementUnit, String actionId)
		throws PortalException {

		return _cpMeasurementUnitPermission.contains(
			permissionChecker, cpMeasurementUnit, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long cpMeasurementUnitId,
			String actionId)
		throws PortalException {

		return _cpMeasurementUnitPermission.contains(
			permissionChecker, cpMeasurementUnitId, actionId);
	}

	@Override
	public String getModelName() {
		return CPMeasurementUnit.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private CPMeasurementUnitPermission _cpMeasurementUnitPermission;

	@Reference(
		target = "(resource.name=" + CPConstants.RESOURCE_NAME_PRODUCT + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}