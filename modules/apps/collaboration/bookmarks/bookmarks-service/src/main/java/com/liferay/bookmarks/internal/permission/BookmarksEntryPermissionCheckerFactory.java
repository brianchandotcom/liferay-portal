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
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.checker.DynamicInheritancePermissionCheck;
import com.liferay.portal.kernel.security.permission.checker.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.checker.ModelResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.checker.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.checker.StagedModelPermissionCheck;
import com.liferay.portal.util.PropsValues;

/**
 * @author Preston Crary
 */
public class BookmarksEntryPermissionCheckerFactory {

	public static ModelResourcePermission<BookmarksEntry> create(
		BookmarksEntryLocalService bookmarksEntryLocalService,
		BookmarksFolderLocalService bookmarksFolderLocalService,
		ModelResourcePermission<BookmarksFolder> folderModelResourcePermission,
		PortletResourcePermission portletResourcePermission) {

		return ModelResourcePermissionChecker.create(
			BookmarksEntry.class.getName(), BookmarksEntry::getEntryId,
			bookmarksEntryLocalService::getEntry, portletResourcePermission,
			(modelResourcePermission, checksCollector) -> {
				checksCollector.accept(
					new StagedModelPermissionCheck<>(
						BookmarksPortletKeys.BOOKMARKS,
						BookmarksEntry::getEntryId));

				if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
					checksCollector.accept(
						new DynamicInheritancePermissionCheck<>(
							folderModelResourcePermission,
							_getFetchContainerFunction(
								bookmarksFolderLocalService),
							true));
				}
			});
	}

	private static UnsafeFunction
		<BookmarksEntry, BookmarksFolder, PortalException>
			_getFetchContainerFunction(
				BookmarksFolderLocalService bookmarksFolderLocalService) {

		return entry -> {
			long folderId = entry.getFolderId();

			if (BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID == folderId) {
				return null;
			}

			if (entry.isInTrash()) {
				return bookmarksFolderLocalService.fetchBookmarksFolder(
					folderId);
			}

			return bookmarksFolderLocalService.getFolder(folderId);
		};
	}

}