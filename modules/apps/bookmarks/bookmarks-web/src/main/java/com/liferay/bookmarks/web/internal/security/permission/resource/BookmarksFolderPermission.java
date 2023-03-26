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

package com.liferay.bookmarks.web.internal.security.permission.resource;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;

/**
 * @author Preston Crary
 */
public class BookmarksFolderPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, BookmarksFolder folder,
			String actionId)
		throws PortalException {

		ModelResourcePermission<BookmarksFolder> modelResourcePermission =
			_bookmarksFolderModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, folder, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<BookmarksFolder> modelResourcePermission =
			_bookmarksFolderModelResourcePermissionSnapshot.get();

		return ModelResourcePermissionUtil.contains(
			modelResourcePermission, permissionChecker, groupId, folderId,
			actionId);
	}

	private static final Snapshot<ModelResourcePermission<BookmarksFolder>>
		_bookmarksFolderModelResourcePermissionSnapshot = new Snapshot<>(
			BookmarksFolderPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.bookmarks.model.BookmarksFolder)");

}