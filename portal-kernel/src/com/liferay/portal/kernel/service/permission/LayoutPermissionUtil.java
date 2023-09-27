/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class LayoutPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException {

		_layoutPermission.check(
			permissionChecker, layout, checkViewableGroup, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		_layoutPermission.check(permissionChecker, layout, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException {

		_layoutPermission.check(
			permissionChecker, groupId, privateLayout, layoutId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException {

		_layoutPermission.check(permissionChecker, plid, actionId);
	}

	public static void checkLayoutRestrictedUpdatePermission(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		_layoutPermission.checkLayoutRestrictedUpdatePermission(
			permissionChecker, layout);
	}

	public static void checkLayoutRestrictedUpdatePermission(
			PermissionChecker permissionChecker, long plid)
		throws PortalException {

		_layoutPermission.checkLayoutRestrictedUpdatePermission(
			permissionChecker, plid);
	}

	public static void checkLayoutUpdatePermission(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		_layoutPermission.checkLayoutUpdatePermission(
			permissionChecker, layout);
	}

	public static void checkLayoutUpdatePermission(
			PermissionChecker permissionChecker, long plid)
		throws PortalException {

		_layoutPermission.checkLayoutUpdatePermission(permissionChecker, plid);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException {

		return _layoutPermission.contains(
			permissionChecker, layout, checkViewableGroup, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		return _layoutPermission.contains(permissionChecker, layout, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException {

		return _layoutPermission.contains(
			permissionChecker, groupId, privateLayout, layoutId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException {

		return _layoutPermission.contains(permissionChecker, plid, actionId);
	}

	public static boolean containsLayoutRestrictedUpdatePermission(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return _layoutPermission.containsLayoutRestrictedUpdatePermission(
			permissionChecker, layout);
	}

	public static boolean containsLayoutRestrictedUpdatePermission(
			PermissionChecker permissionChecker, long plid)
		throws PortalException {

		return _layoutPermission.containsLayoutRestrictedUpdatePermission(
			permissionChecker, plid);
	}

	public static boolean containsLayoutUpdatePermission(
			PermissionChecker permissionChecker, Layout layout)
		throws PortalException {

		return _layoutPermission.containsLayoutUpdatePermission(
			permissionChecker, layout);
	}

	public static boolean containsLayoutUpdatePermission(
			PermissionChecker permissionChecker, long plid)
		throws PortalException {

		return _layoutPermission.containsLayoutUpdatePermission(
			permissionChecker, plid);
	}

	public static boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkLayoutUpdateable, String actionId)
		throws PortalException {

		return _layoutPermission.containsWithoutViewableGroup(
			permissionChecker, layout, checkLayoutUpdateable, actionId);
	}

	public static boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		return _layoutPermission.containsWithoutViewableGroup(
			permissionChecker, layout, true, actionId);
	}

	public static LayoutPermission getLayoutPermission() {
		return _layoutPermission;
	}

	public void setLayoutPermission(LayoutPermission layoutPermission) {
		_layoutPermission = layoutPermission;
	}

	private static LayoutPermission _layoutPermission;

}