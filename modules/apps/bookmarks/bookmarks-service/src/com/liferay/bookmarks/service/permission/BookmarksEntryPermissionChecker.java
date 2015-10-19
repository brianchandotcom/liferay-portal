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

package com.liferay.bookmarks.service.permission;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.exception.NoSuchFolderException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.bookmarks.model.BookmarksEntry"}
)
public class BookmarksEntryPermissionChecker
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, BookmarksEntry entry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, BookmarksEntry.class.getName(),
				entry.getEntryId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, BookmarksEntry.class.getName(), entryId,
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, BookmarksEntry entry,
			String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, entry.getGroupId(),
			BookmarksEntry.class.getName(), entry.getEntryId(),
			BookmarksPortletKeys.BOOKMARKS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (actionId.equals(ActionKeys.VIEW) &&
			PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {

			long folderId = entry.getFolderId();

			if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				if (!BookmarksResourcePermissionChecker.contains(
						permissionChecker, entry.getGroupId(), actionId)) {

					return false;
				}
			}
			else {
				try {
					BookmarksFolder folder =
						_bookmarksFolderLocalService.getFolder(folderId);

					if (!BookmarksFolderPermissionChecker.contains(
							permissionChecker, folder, ActionKeys.ACCESS) &&
						!BookmarksFolderPermissionChecker.contains(
							permissionChecker, folder, ActionKeys.VIEW)) {

						return false;
					}
				}
				catch (NoSuchFolderException nsfe) {
					if (!entry.isInTrash()) {
						throw nsfe;
					}
				}
			}
		}

		if (permissionChecker.hasOwnerPermission(
				entry.getCompanyId(), BookmarksEntry.class.getName(),
				entry.getEntryId(), entry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			entry.getGroupId(), BookmarksEntry.class.getName(),
			entry.getEntryId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(entryId);

		return contains(permissionChecker, entry, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setBookmarksEntryLocalService(
		BookmarksEntryLocalService bookmarksEntryLocalService) {

		_bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {

		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	private static BookmarksEntryLocalService _bookmarksEntryLocalService;
	private static BookmarksFolderLocalService _bookmarksFolderLocalService;

}