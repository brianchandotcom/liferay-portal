/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public interface LayoutPermission {

	public void check(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             Layout, boolean, String)}
	 */
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, boolean checkViewableGroup,
			String actionId)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             Layout, String)}
	 */
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             long, boolean, long, String)}
	 */
	public boolean contains(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String controlPanelCategory,
			String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException, SystemException;

	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkLayoutUpdateable, String actionId)
		throws PortalException, SystemException;

	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #containsWithoutViewableGroup(PermissionChecker, Layout,
	 *             boolean, String)}
	 */
	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, boolean checkLayoutUpdateable,
			String actionId)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #containsWithoutViewableGroup(PermissionChecker, Layout,
	 *             String)}
	 */
	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, String actionId)
		throws PortalException, SystemException;

}