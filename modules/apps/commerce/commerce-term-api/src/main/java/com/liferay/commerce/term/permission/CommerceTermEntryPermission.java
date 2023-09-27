/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.permission;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceTermEntryPermission {

	public void check(
			PermissionChecker permissionChecker,
			CommerceTermEntry commerceTermEntry, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long commerceTermEntryId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceTermEntry commerceTermEntry, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long commerceTermEntryId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceTermEntryIds,
			String actionId)
		throws PortalException;

}