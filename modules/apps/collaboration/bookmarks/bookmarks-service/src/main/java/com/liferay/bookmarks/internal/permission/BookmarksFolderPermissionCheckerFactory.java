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

package com.liferay.bookmarks.internal.permission;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.checker.DynamicInheritancePermissionCheck;
import com.liferay.portal.kernel.security.permission.checker.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.checker.ModelResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.checker.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.checker.StagedModelPermissionCheck;
import com.liferay.portal.util.PropsValues;

/**
 * @author Preston Crary
 */
public class BookmarksFolderPermissionCheckerFactory {

	public static ModelResourcePermission<BookmarksFolder> create(
		BookmarksFolderLocalService bookmarksFolderLocalService,
		PortletResourcePermission portletResourcePermission) {

		return ModelResourcePermissionChecker.create(
			BookmarksFolder.class.getName(), BookmarksFolder::getFolderId,
			bookmarksFolderLocalService::getFolder, portletResourcePermission,
			(modelResourcePermission, checksCollector) -> {
				checksCollector.accept(
					new StagedModelPermissionCheck<>(
						BookmarksPortletKeys.BOOKMARKS,
						BookmarksFolder::getFolderId));

				if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
					checksCollector.accept(
						new DynamicInheritancePermissionCheck<>(
							modelResourcePermission,
							_getFetchParentFunction(
								bookmarksFolderLocalService),
							true));
				}
			},
			actionId -> {
				if (ActionKeys.ADD_FOLDER.equals(actionId)) {
					return ActionKeys.ADD_SUBFOLDER;
				}

				return actionId;
			});
	}

	private static UnsafeFunction
		<BookmarksFolder, BookmarksFolder, ? extends PortalException>
			_getFetchParentFunction(
				BookmarksFolderLocalService bookmarksFolderLocalService) {

		return folder -> {
			long folderId = folder.getParentFolderId();

			if (BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID == folderId) {
				return null;
			}

			if (folder.isInTrash()) {
				return bookmarksFolderLocalService.fetchBookmarksFolder(
					folderId);
			}

			return bookmarksFolderLocalService.getFolder(folderId);
		};
	}

}