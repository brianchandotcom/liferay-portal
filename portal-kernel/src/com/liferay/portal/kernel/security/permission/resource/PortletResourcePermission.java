/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Preston Crary
 */
public interface PortletResourcePermission {

	public void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PrincipalException;

	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PrincipalException;

	public boolean contains(
		PermissionChecker permissionChecker, Group group, String actionId);

	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId);

	public String getResourceName();

}