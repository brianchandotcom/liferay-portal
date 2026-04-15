/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.permission;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Crescenzo Rega
 */
public interface CommerceShipmentPermission {

	public void check(
			PermissionChecker permissionChecker,
			CommerceShipment commerceShipment, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long commerceShipmentId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceShipment commerceShipment, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long commerceShipmentId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceShipmentIds,
			String actionId)
		throws PortalException;

}