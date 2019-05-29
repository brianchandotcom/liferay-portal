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

package com.liferay.sharing.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public interface SharingPermission {

	public void check(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId, Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException;

	public void checkManageCollaboratorsPermission(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId)
		throws PortalException;

	public void checkSharePermission(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId, Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException;

	public boolean containsManageCollaboratorsPermission(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId)
		throws PortalException;

	public boolean containsSharePermission(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			long groupId)
		throws PortalException;

}