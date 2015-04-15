/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class LayoutPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException {

		getLayoutPermission().check(
			permissionChecker, layout, checkViewableGroup, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		getLayoutPermission().check(permissionChecker, layout, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long layoutId,
			String actionId)
		throws PortalException {

		getLayoutPermission().check(
			permissionChecker, groupId, layoutId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException {

		getLayoutPermission().check(permissionChecker, plid, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException {

		return getLayoutPermission().contains(
			permissionChecker, layout, checkViewableGroup, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		return getLayoutPermission().contains(
			permissionChecker, layout, actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             Layout, boolean, String)}
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, boolean checkViewableGroup,
			String actionId)
		throws PortalException {

		return getLayoutPermission().contains(
			permissionChecker, layout, controlPanelCategory, checkViewableGroup,
			actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             Layout, String)}
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, String actionId)
		throws PortalException {

		return getLayoutPermission().contains(
			permissionChecker, layout, controlPanelCategory, actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             long, long, String)}
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String controlPanelCategory,
			String actionId)
		throws PortalException {

		return getLayoutPermission().contains(
			permissionChecker, groupId, privateLayout, layoutId,
			controlPanelCategory, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long layoutId,
			String actionId)
		throws PortalException {

		return getLayoutPermission().contains(
			permissionChecker, groupId, layoutId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException {

		return getLayoutPermission().contains(
			permissionChecker, plid, actionId);
	}

	public static boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkLayoutUpdateable, String actionId)
		throws PortalException {

		return getLayoutPermission().containsWithoutViewableGroup(
			permissionChecker, layout, checkLayoutUpdateable, actionId);
	}

	public static boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		return getLayoutPermission().containsWithoutViewableGroup(
			permissionChecker, layout, true, actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #containsWithoutViewableGroup(PermissionChecker, Layout,
	 *             boolean, String)}
	 */
	@Deprecated
	public static boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, boolean checkLayoutUpdateable,
			String actionId)
		throws PortalException {

		return getLayoutPermission().containsWithoutViewableGroup(
			permissionChecker, layout, controlPanelCategory,
			checkLayoutUpdateable, actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #containsWithoutViewableGroup(PermissionChecker, Layout,
	 *             String)}
	 */
	@Deprecated
	public static boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, String actionId)
		throws PortalException {

		return getLayoutPermission().containsWithoutViewableGroup(
			permissionChecker, layout, controlPanelCategory, true, actionId);
	}

	public static LayoutPermission getLayoutPermission() {
		PortalRuntimePermission.checkGetBeanProperty(
			LayoutPermissionUtil.class);

		return _layoutPermission;
	}

	public void setLayoutPermission(LayoutPermission layoutPermission) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_layoutPermission = layoutPermission;
	}

	private static LayoutPermission _layoutPermission;

}