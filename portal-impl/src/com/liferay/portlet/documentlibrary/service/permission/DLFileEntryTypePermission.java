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

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Alexander Chow
 */
public class DLFileEntryTypePermission {

	public static void check(
			PermissionChecker permissionChecker, DLFileEntryType fileEntryType,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, fileEntryType, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DLFileEntryType.class.getName(),
				fileEntryType.getFileEntryTypeId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long fileEntryTypeId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, fileEntryTypeId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DLFileEntryType.class.getName(),
				fileEntryTypeId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DLFileEntryType fileEntryType,
		String actionId) {

		String portletId = PortletProviderUtil.getPortletId(
			DLFileEntryType.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, fileEntryType.getGroupId(),
			DLFileEntryType.class.getName(), fileEntryType.getFileEntryTypeId(),
			portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				fileEntryType.getCompanyId(), DLFileEntryType.class.getName(),
				fileEntryType.getFileEntryTypeId(), fileEntryType.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			fileEntryType.getGroupId(), DLFileEntryType.class.getName(),
			fileEntryType.getFileEntryTypeId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long fileEntryTypeId,
			String actionId)
		throws PortalException {

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);

		return contains(permissionChecker, fileEntryType, actionId);
	}

}