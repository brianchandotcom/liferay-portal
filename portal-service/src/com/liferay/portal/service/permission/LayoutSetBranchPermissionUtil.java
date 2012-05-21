/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutSetBranchPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker,
			LayoutSetBranch layoutSetBranch, String actionId)
		throws PortalException {

		getLayoutSetBranchPermission().check(
			permissionChecker, layoutSetBranch, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long layoutSetBranchId,
			String actionId)
		throws PortalException, SystemException {

		getLayoutSetBranchPermission().check(
			permissionChecker, layoutSetBranchId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, LayoutSetBranch layoutSetBranch,
		String actionId) {

		return getLayoutSetBranchPermission().contains(
			permissionChecker, layoutSetBranch, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long layoutSetBranchId,
			String actionId)
		throws PortalException, SystemException {

		return getLayoutSetBranchPermission().contains(
			permissionChecker, layoutSetBranchId, actionId);
	}

	public static LayoutSetBranchPermission getLayoutSetBranchPermission() {
		PortalRuntimePermission.checkGetBeanProperty(
			LayoutSetBranchPermissionUtil.class);

		return _layoutSetBranchPermission;
	}

	public void setLayoutSetBranchPermission(
		LayoutSetBranchPermission layoutSetBranchPermission) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_layoutSetBranchPermission = layoutSetBranchPermission;
	}

	private static LayoutSetBranchPermission _layoutSetBranchPermission;

}