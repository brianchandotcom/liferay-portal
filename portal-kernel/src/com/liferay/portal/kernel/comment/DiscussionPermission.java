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