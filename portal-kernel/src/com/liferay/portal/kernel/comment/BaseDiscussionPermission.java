/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public abstract class BaseDiscussionPermission implements DiscussionPermission {

	@Override
	public void checkAddPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException {

		if (!hasAddPermission(
				permissionChecker, companyId, groupId, className, classPK)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, className, classPK,
				ActionKeys.ADD_DISCUSSION);
		}
	}

	@Override
	public void checkDeletePermission(
			PermissionChecker permissionChecker, long commentId)
		throws PortalException {

		if (!hasDeletePermission(permissionChecker, commentId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ActionKeys.DELETE_DISCUSSION);
		}
	}

	@Override
	public void checkSubscribePermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException {

		if (!hasSubscribePermission(
				permissionChecker, companyId, groupId, className, classPK)) {

			throw new PrincipalException();
		}
	}

	@Override
	public void checkUpdatePermission(
			PermissionChecker permissionChecker, long commentId)
		throws PortalException {

		if (!hasUpdatePermission(permissionChecker, commentId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ActionKeys.UPDATE_DISCUSSION);
		}
	}

	@Override
	public void checkViewPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException {

		if (!hasViewPermission(
				permissionChecker, companyId, groupId, className, classPK)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, className, classPK, ActionKeys.VIEW);
		}
	}

	@Override
	public boolean hasDeletePermission(
			PermissionChecker permissionChecker, long commentId)
		throws PortalException {

		return hasPermission(
			permissionChecker, commentId, ActionKeys.DELETE_DISCUSSION);
	}

	@Override
	public boolean hasUpdatePermission(
			PermissionChecker permissionChecker, long commentId)
		throws PortalException {

		return hasPermission(
			permissionChecker, commentId, ActionKeys.UPDATE_DISCUSSION);
	}

}