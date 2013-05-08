/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;

/**
 * @author Juan Fernández
 * @author Zsolt Berentey
 */
public class JournalFolderPermission {

	public static void check(
			PermissionChecker permissionChecker, JournalFolder folder,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, folder, actionId)) {
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
			PermissionChecker permissionChecker, JournalFolder folder,
			String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_FOLDER)) {
			actionId = ActionKeys.ADD_SUBFOLDER;
		}

		long folderId = folder.getFolderId();

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
			long originalFolderId = folderId;

			try {
				while (folderId !=
							JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

					folder = JournalFolderLocalServiceUtil.getFolder(folderId);

					if (!permissionChecker.hasOwnerPermission(
							folder.getCompanyId(),
							JournalFolder.class.getName(), folderId,
							folder.getUserId(), ActionKeys.VIEW) &&
						!permissionChecker.hasPermission(
							folder.getGroupId(), JournalFolder.class.getName(),
							folderId, ActionKeys.VIEW)) {

						return false;
					}

					folderId = folder.getParentFolderId();
				}
			}
			catch (NoSuchFolderException nsfe) {
				if (!folder.isInTrash()) {
					throw nsfe;
				}
			}

			if (actionId.equals(ActionKeys.VIEW)) {
				return true;
			}

			folderId = originalFolderId;
		}

		try {
			while (folderId !=
						JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folder = JournalFolderLocalServiceUtil.getFolder(folderId);

				if (permissionChecker.hasOwnerPermission(
						folder.getCompanyId(), JournalFolder.class.getName(),
						folderId, folder.getUserId(), actionId) ||
					permissionChecker.hasPermission(
						folder.getGroupId(), JournalFolder.class.getName(),
						folderId, actionId)) {

					return true;
				}

				folderId = folder.getParentFolderId();
			}
		}
		catch (NoSuchFolderException nsfe) {
			if (!folder.isInTrash()) {
				throw nsfe;
			}
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException, SystemException {

		if (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return JournalPermission.contains(
				permissionChecker, groupId, actionId);
		}
		else {
			JournalFolder folder =
				JournalFolderLocalServiceUtil.getJournalFolder(folderId);

			return contains(permissionChecker, folder, actionId);
		}
	}

}