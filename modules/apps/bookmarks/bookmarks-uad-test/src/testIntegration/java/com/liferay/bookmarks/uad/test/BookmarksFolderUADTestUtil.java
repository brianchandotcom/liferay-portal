/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.uad.test;

import com.liferay.bookmarks.constants.BookmarksFolderConstants;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksFolderUADTestUtil {

	public static BookmarksFolder addBookmarksFolder(
			BookmarksFolderLocalService bookmarksFolderLocalService,
			long userId)
		throws Exception {

		return bookmarksFolderLocalService.addFolder(
			userId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

	public static BookmarksFolder addBookmarksFolderWithStatusByUserId(
			BookmarksFolderLocalService bookmarksFolderLocalService,
			long userId, long statusByUserId)
		throws Exception {

		BookmarksFolder bookmarksFolder = addBookmarksFolder(
			bookmarksFolderLocalService, userId);

		return bookmarksFolderLocalService.updateStatus(
			statusByUserId, bookmarksFolder, WorkflowConstants.STATUS_APPROVED);
	}

}