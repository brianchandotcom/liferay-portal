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

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties(
	property =
		{"model.class.name=com.liferay.document.library.kernel.model.DLFolder"}
)
public class DLFolderPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, DLFolder dlFolder,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, dlFolder, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DLFolder.class.getName(),
				dlFolder.getFolderId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, Folder folder, String actionId)
		throws PortalException {

		if (!folder.containsPermission(permissionChecker, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Folder.class.getName(), folder.getFolderId(),
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, folderId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Folder.class.getName(), folderId, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DLFolder dlFolder,
			String actionId)
		throws PortalException {

		if (actionId.equals(ActionKeys.ADD_FOLDER)) {
			actionId = ActionKeys.ADD_SUBFOLDER;
		}

		String portletId = PortletProviderUtil.getPortletId(
			Folder.class.getName(), PortletProvider.Action.EDIT);

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, dlFolder.getGroupId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			try {
				long dlFolderId = dlFolder.getFolderId();

				while (dlFolderId !=
							DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

					dlFolder = DLFolderLocalServiceUtil.getFolder(dlFolderId);

					if (!_hasPermission(
							permissionChecker, dlFolder, actionId)) {

						return false;
					}

					dlFolderId = dlFolder.getParentFolderId();
				}
			}
			catch (NoSuchFolderException nsfe) {
				if (!dlFolder.isInTrash()) {
					throw nsfe;
				}
			}

			return DLPermission.contains(
				permissionChecker, dlFolder.getGroupId(), actionId);
		}

		return _hasPermission(permissionChecker, dlFolder, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Folder folder, String actionId)
		throws PortalException {

		return folder.containsPermission(permissionChecker, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

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

		Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

		return folder.containsPermission(permissionChecker, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, groupId, primaryKey, actionId);
	}

	private static boolean _hasPermission(
		PermissionChecker permissionChecker, DLFolder dlFolder,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				dlFolder.getCompanyId(), DLFolder.class.getName(),
				dlFolder.getFolderId(), dlFolder.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				dlFolder.getGroupId(), DLFolder.class.getName(),
				dlFolder.getFolderId(), actionId)) {

			return true;
		}

		return false;
	}

}