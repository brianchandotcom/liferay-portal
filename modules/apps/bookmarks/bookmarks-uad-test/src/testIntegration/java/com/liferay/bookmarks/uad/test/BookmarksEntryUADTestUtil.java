/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.uad.test;

import com.liferay.bookmarks.constants.BookmarksFolderConstants;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Noah Sherrill
 */
public class BookmarksEntryUADTestUtil {

	public static BookmarksEntry addBookmarksEntry(
			BookmarksEntryLocalService bookmarksEntryLocalService, long userId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return bookmarksEntryLocalService.addEntry(
			userId, serviceContext.getScopeGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), "http://www.liferay.com",
			RandomTestUtil.randomString(), serviceContext);
	}

	public static BookmarksEntry addBookmarksEntryWithStatusByUserId(
			BookmarksEntryLocalService bookmarksEntryLocalService, long userId,
			long statusByUserId)
		throws Exception {

		BookmarksEntry bookmarksEntry = addBookmarksEntry(
			bookmarksEntryLocalService, userId);

		return bookmarksEntryLocalService.updateStatus(
			statusByUserId, bookmarksEntry, WorkflowConstants.STATUS_APPROVED);
	}

}