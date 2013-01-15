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

package com.liferay.portlet.bookmarks.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class BookmarksFolderPermission {

	public static void check(
			PermissionChecker permissionChecker, BookmarksFolder folder,
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
			PermissionChecker permissionChecker, BookmarksFolder folder,
			String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_FOLDER)) {
			actionId = ActionKeys.ADD_SUBFOLDER;
		}

		long folderId = folder.getFolderId();

		if (actionId.equals(ActionKeys.VIEW)) {
			while (folderId !=
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				try {
					folder = BookmarksFolderLocalServiceUtil.getFolder(
						folderId);

					folderId = folder.getParentFolderId();

					if (!permissionChecker.hasOwnerPermission(
							folder.getCompanyId(),
							BookmarksFolder.class.getName(),
							folder.getFolderId(), folder.getUserId(),
							actionId) &&
						!permissionChecker.hasPermission(
							folder.getGroupId(),
							BookmarksFolder.class.getName(),
							folder.getFolderId(), actionId)) {

						return false;
					}

					if (!PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
						break;
					}
				}
				catch (NoSuchFolderException nsfe) {
					if (!folder.isInTrash()) {
						throw nsfe;
					}
				}
			}

			return true;
		}
		else {
			while (folderId !=
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folder = BookmarksFolderLocalServiceUtil.getFolder(folderId);

				folderId = folder.getParentFolderId();

				if (permissionChecker.hasOwnerPermission(
						folder.getCompanyId(), BookmarksFolder.class.getName(),
						folder.getFolderId(), folder.getUserId(), actionId)) {

					return true;
				}

				if (permissionChecker.hasPermission(
						folder.getGroupId(), BookmarksFolder.class.getName(),
						folder.getFolderId(), actionId)) {

					return true;
				}

				if (actionId.equals(ActionKeys.VIEW)) {
					break;
				}
			}

			return false;
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException, SystemException {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return BookmarksPermission.contains(
				permissionChecker, groupId, actionId);
		}
		else {
			BookmarksFolder folder =
				BookmarksFolderLocalServiceUtil.getBookmarksFolder(folderId);

			return contains(permissionChecker, folder, actionId);
		}
	}

}