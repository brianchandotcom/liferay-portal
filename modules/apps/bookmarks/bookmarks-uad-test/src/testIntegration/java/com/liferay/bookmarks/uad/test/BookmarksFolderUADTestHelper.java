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

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = BookmarksFolderUADTestHelper.class)
public class BookmarksFolderUADTestHelper {

	/**
	 * Implement addBookmarksFolder() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid BookmarksFolders with a specified user ID in order to execute correctly. Implement addBookmarksFolder() such that it creates a valid BookmarksFolder with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public BookmarksFolder addBookmarksFolder(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return _bookmarksFolderLocalService.addFolder(
			userId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	/**
	 * Implement addBookmarksFolderWithStatusByUserId() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid BookmarksFolders with specified user ID and status by user ID in order to execute correctly. Implement addBookmarksFolderWithStatusByUserId() such that it creates a valid BookmarksFolder with the specified user ID and status by user ID values and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public BookmarksFolder addBookmarksFolderWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		BookmarksFolder bookmarksFolder = addBookmarksFolder(userId);

		bookmarksFolder = _bookmarksFolderLocalService.updateStatus(
			statusByUserId, bookmarksFolder, WorkflowConstants.STATUS_APPROVED);

		return bookmarksFolder;
	}

	/**
	 * Implement cleanUpDependencies(List<BookmarksFolder> bookmarksFolders) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid BookmarksFolders with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<BookmarksFolder> bookmarksFolders) such that any additional objects created during the construction of bookmarksFolders are safely removed.
	 * </p>
	 */
	public void cleanUpDependencies(List<BookmarksFolder> bookmarksFolders)
		throws Exception {
	}

	@Reference
	private BookmarksFolderLocalService _bookmarksFolderLocalService;

}