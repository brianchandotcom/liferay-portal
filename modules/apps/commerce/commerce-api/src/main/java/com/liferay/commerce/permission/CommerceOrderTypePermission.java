/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.permission;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Riccardo Alberti
 */
public interface CommerceOrderTypePermission {

	public void check(
			PermissionChecker permissionChecker,
			CommerceOrderType commerceOrderType, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long commerceOrderTypeId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceOrderType commerceOrderType, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long commerceOrderTypeId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceOrderTypeIds,
			String actionId)
		throws PortalException;

}