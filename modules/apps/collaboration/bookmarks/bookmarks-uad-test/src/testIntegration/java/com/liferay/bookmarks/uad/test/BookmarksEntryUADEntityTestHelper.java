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

package com.liferay.bookmarks.uad.test;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(immediate = true, service = BookmarksEntryUADEntityTestHelper.class)
public class BookmarksEntryUADEntityTestHelper {

	public BookmarksEntry addBookmarksEntry(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return _bookmarksEntryLocalService.addEntry(
			userId, serviceContext.getScopeGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), "http://www.liferay.com",
			RandomTestUtil.randomString(), serviceContext);
	}

	public BookmarksEntry addBookmarksEntryWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		BookmarksEntry bookmarksEntry = addBookmarksEntry(userId);

		_bookmarksEntryLocalService.updateStatus(
			statusByUserId, bookmarksEntry, WorkflowConstants.STATUS_APPROVED);

		return bookmarksEntry;
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

}