/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public interface DiscussionPermission {

	public void checkAddPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException;

	public void checkDeletePermission(
			PermissionChecker permissionChecker, long commentId)
		throws PortalException;

	public void checkSubscribePermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException;

	public void checkUpdatePermission(
			PermissionChecker permissionChecker, long commentId)
		throws PortalException;

	public void checkViewPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException;

	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException;

	public boolean hasDeletePermission(
			PermissionChecker permissionChecker, long commentId)
		throws PortalException;

	public default boolean hasPermission(
			PermissionChecker permissionChecker, Comment comment,
			String actionId)
		throws PortalException {

		return hasPermission(
			permissionChecker, comment.getCommentId(), actionId);
	}

	public boolean hasPermission(
		PermissionChecker permissionChecker, long companyId, long groupId,
		String className, long classPK, String actionId);

	public boolean hasPermission(
			PermissionChecker permissionChecker, long commentId,
			String actionId)
		throws PortalException;

	public boolean hasSubscribePermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException;

	public boolean hasUpdatePermission(
			PermissionChecker permissionChecker, long commentId)
		throws PortalException;

	public boolean hasViewPermission(
			PermissionChecker permissionChecker, long companyId, long groupId,
			String className, long classPK)
		throws PortalException;

}