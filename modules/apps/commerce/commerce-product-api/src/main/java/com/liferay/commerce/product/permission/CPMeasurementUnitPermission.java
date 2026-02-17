/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.permission;

import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Crescenzo Rega
 */
public interface CPMeasurementUnitPermission {

	public void check(
			PermissionChecker permissionChecker,
			CPMeasurementUnit cpMeasurementUnit, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long cpMeasurementUnitId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker,
			CPMeasurementUnit cpMeasurementUnit, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long cpMeasurementUnitId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long[] cpMeasurementUnitIds,
			String actionId)
		throws PortalException;

}