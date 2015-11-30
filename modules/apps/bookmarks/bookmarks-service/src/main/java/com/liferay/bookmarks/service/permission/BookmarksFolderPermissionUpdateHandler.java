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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.PermissionUpdateHandler;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.bookmarks.model.BookmarksFolder"},
	service = PermissionUpdateHandler.class
)
public class BookmarksFolderPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		BookmarksFolder bookmarksFolder =
			_bookmarksFolderLocalService.fetchBookmarksFolder(
				GetterUtil.getLong(primKey));

		if (bookmarksFolder == null) {
			return;
		}

		bookmarksFolder.setModifiedDate(new Date());

		_bookmarksFolderLocalService.updateBookmarksFolder(bookmarksFolder);
	}

	@Reference(unbind = "-")
	protected void setBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {

		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	private volatile BookmarksFolderLocalService _bookmarksFolderLocalService;

}