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

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @deprecated As of 1.2.0, with no direct replacement
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.bookmarks.model.BookmarksFolder"}
)
@Deprecated
public class BookmarksFolderPermissionChecker
	implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, BookmarksFolder folder,
			String actionId)
		throws PortalException {

		_folderModelResourcePermission.check(
			permissionChecker, folder, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

		ModelResourcePermissionHelper.check(
			_folderModelResourcePermission, permissionChecker, groupId,
			folderId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, BookmarksFolder folder,
			String actionId)
		throws PortalException {

		return _folderModelResourcePermission.contains(
			permissionChecker, folder, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

		return ModelResourcePermissionHelper.contains(
			_folderModelResourcePermission, permissionChecker, groupId,
			folderId, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		ModelResourcePermissionHelper.check(
			_folderModelResourcePermission, permissionChecker, groupId,
			primaryKey, actionId);
	}

	protected void setBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {
	}

	@Reference(
		target = "(model.class.name=com.liferay.bookmarks.model.BookmarksFolder)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<BookmarksFolder> modelResourcePermission) {

		_folderModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<BookmarksFolder>
		_folderModelResourcePermission;

}