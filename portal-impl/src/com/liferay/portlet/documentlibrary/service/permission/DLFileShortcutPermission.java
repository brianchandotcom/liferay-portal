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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutPermission {

	public static void check(
			PermissionChecker permissionChecker, DLFileShortcut dlFileShortcut,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, dlFileShortcut, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DLFileShortcut.class.getName(),
				dlFileShortcut.getFileShortcutId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, FileShortcut fileShortcut,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, fileShortcut, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, FileShortcut.class.getName(),
				fileShortcut.getFileShortcutId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long fileShortcutId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, fileShortcutId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DLFileShortcut.class.getName(),
				fileShortcutId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DLFileShortcut dlFileShortcut,
		String actionId) {

		String portletId = PortletProviderUtil.getPortletId(
			FileShortcut.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, dlFileShortcut.getGroupId(),
			DLFileShortcutConstants.getClassName(),
			dlFileShortcut.getFileShortcutId(), portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				dlFileShortcut.getCompanyId(),
				DLFileShortcutConstants.getClassName(),
				dlFileShortcut.getFileShortcutId(), dlFileShortcut.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			dlFileShortcut.getGroupId(), DLFileShortcutConstants.getClassName(),
			dlFileShortcut.getFileShortcutId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, FileShortcut fileShortcut,
			String actionId)
		throws PortalException {

		return fileShortcut.containsPermission(permissionChecker, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fileShortcutId,
			String actionId)
		throws PortalException {

		DLFileShortcut dlFileShortcut =
			DLFileShortcutLocalServiceUtil.getFileShortcut(fileShortcutId);

		return contains(permissionChecker, dlFileShortcut, actionId);
	}

}