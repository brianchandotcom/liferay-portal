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

package com.liferay.portlet.bookmarks.util;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksTestUtil {

	public static BookmarksEntry addEntry() throws Exception {
		BookmarksFolder folder = addFolder();

		String name = "Test Entry";
		String url = "http://www.liferay.com";
		String description = "This is a test entry.";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return BookmarksEntryServiceUtil.addEntry(
			folder.getGroupId(), folder.getFolderId(), name, url, description,
			serviceContext);
	}

	public static BookmarksFolder addFolder() throws Exception {
		long parentFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		return addFolder(parentFolderId);
	}

	public static BookmarksFolder addFolder(long parentFolderId)
		throws Exception {

		String name = "Test Folder";
		String description = "This is a test folder.";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(TestPropsValues.getGroupId());

		return BookmarksFolderServiceUtil.addFolder(
			parentFolderId, name, description, serviceContext);
	}

}