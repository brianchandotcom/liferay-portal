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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFolderPermission {

	public static void check(
			PermissionChecker permissionChecker, DLFolder dlFolder,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, dlFolder, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, Folder folder, String actionId)
		throws PortalException, SystemException {

		if (!folder.containsPermission(permissionChecker, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, folderId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DLFolder dlFolder,
			String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_FOLDER)) {
			actionId = ActionKeys.ADD_SUBFOLDER;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, dlFolder.getGroupId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), PortletKeys.DOCUMENT_LIBRARY, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		long folderId = dlFolder.getFolderId();

		if (actionId.equals(ActionKeys.VIEW)) {
			while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				try {
					dlFolder = DLFolderLocalServiceUtil.getFolder(folderId);

					folderId = dlFolder.getParentFolderId();

					if (!permissionChecker.hasOwnerPermission(
							dlFolder.getCompanyId(), DLFolder.class.getName(),
							dlFolder.getFolderId(), dlFolder.getUserId(),
							actionId) &&
						!permissionChecker.hasPermission(
							dlFolder.getGroupId(), DLFolder.class.getName(),
							dlFolder.getFolderId(), actionId)) {

						return false;
					}

					if (!PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
						break;
					}
				}
				catch (NoSuchFolderException nsfe) {
					if (dlFolder.isInTrash()) {
						break;
					}

					throw nsfe;
				}

			}

			return true;
		}
		else {
			while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				dlFolder = DLFolderLocalServiceUtil.getFolder(folderId);

				folderId = dlFolder.getParentFolderId();

				if (permissionChecker.hasOwnerPermission(
						dlFolder.getCompanyId(), DLFolder.class.getName(),
						dlFolder.getFolderId(), dlFolder.getUserId(),
						actionId)) {

					return true;
				}

				if (permissionChecker.hasPermission(
						dlFolder.getGroupId(), DLFolder.class.getName(),
						dlFolder.getFolderId(), actionId)) {

					return true;
				}
			}

			return false;
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Folder folder, String actionId)
		throws PortalException, SystemException {

		return folder.containsPermission(permissionChecker, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException, SystemException {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Prevent the propagation of checks for actions that are not
			// supported at the application resource level. See LPS-24245.

			if (actionId.equals(ActionKeys.ACCESS) ||
				actionId.equals(ActionKeys.ADD_SUBFOLDER) ||
				actionId.equals(ActionKeys.DELETE)) {

				return false;
			}

			return DLPermission.contains(permissionChecker, groupId, actionId);
		}
		else {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			return folder.containsPermission(permissionChecker, actionId);
		}
	}

}