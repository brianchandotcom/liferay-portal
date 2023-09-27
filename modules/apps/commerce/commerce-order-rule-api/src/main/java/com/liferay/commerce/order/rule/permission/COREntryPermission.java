/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.permission;

import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Alessio Antonio Rendina
 */
public interface COREntryPermission {

	public void check(
			PermissionChecker permissionChecker, COREntry corEntry,
			String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long corEntryId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, COREntry corEntry,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long corEntryId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long[] corEntryIds,
			String actionId)
		throws PortalException;

}