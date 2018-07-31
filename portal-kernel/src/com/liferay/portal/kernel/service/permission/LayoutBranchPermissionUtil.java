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

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class LayoutBranchPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, LayoutBranch layoutBranch,
			String actionId)
		throws PortalException {

		getLayoutBranchPermission().check(
			permissionChecker, layoutBranch, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long layoutBranchId,
			String actionId)
		throws PortalException {

		getLayoutBranchPermission().check(
			permissionChecker, layoutBranchId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, LayoutBranch layoutBranch,
		String actionId) {

		return getLayoutBranchPermission().contains(
			permissionChecker, layoutBranch, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long layoutBranchId,
			String actionId)
		throws PortalException {

		return getLayoutBranchPermission().contains(
			permissionChecker, layoutBranchId, actionId);
	}

	public static LayoutBranchPermission getLayoutBranchPermission() {
		return _layoutBranchPermission;
	}

	public void setLayoutBranchPermission(
		LayoutBranchPermission layoutBranchPermission) {

		_layoutBranchPermission = layoutBranchPermission;
	}

	private static LayoutBranchPermission _layoutBranchPermission;

}