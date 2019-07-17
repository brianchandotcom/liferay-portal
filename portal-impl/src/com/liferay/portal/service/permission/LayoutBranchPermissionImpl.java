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
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.LayoutBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutBranchPermission;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutBranchPermissionImpl implements LayoutBranchPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, LayoutBranch layoutBranch,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, layoutBranch, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutBranch.class.getName(),
				layoutBranch.getLayoutBranchId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long layoutBranchId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, layoutBranchId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, LayoutBranch.class.getName(), layoutBranchId,
				actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, LayoutBranch layoutBranch,
		String actionId) {

		return permissionChecker.hasPermission(
			layoutBranch.getGroupId(), LayoutBranch.class.getName(),
			layoutBranch.getLayoutBranchId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long layoutBranchId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			LayoutBranchLocalServiceUtil.getLayoutBranch(layoutBranchId),
			actionId);
	}

}