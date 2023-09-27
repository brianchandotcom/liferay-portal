/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public interface LayoutPermission {

	public void check(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException;

	public void checkLayoutRestrictedUpdatePermission(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException;

	public void checkLayoutRestrictedUpdatePermission(
			PermissionChecker permissionChecker, long plid)
		throws PortalException;

	public void checkLayoutUpdatePermission(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException;

	public void checkLayoutUpdatePermission(
			PermissionChecker permissionChecker, long plid)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException;

	public boolean containsLayoutRestrictedUpdatePermission(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException;

	public boolean containsLayoutRestrictedUpdatePermission(
			PermissionChecker permissionChecker, long plid)
		throws PortalException;

	public boolean containsLayoutUpdatePermission(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException;

	public boolean containsLayoutUpdatePermission(
			PermissionChecker permissionChecker, long plid)
		throws PortalException;

	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkLayoutUpdateable, String actionId)
		throws PortalException;

	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException;

}