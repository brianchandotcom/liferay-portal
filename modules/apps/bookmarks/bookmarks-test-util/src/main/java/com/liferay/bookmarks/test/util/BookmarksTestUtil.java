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

package com.liferay.bookmarks.test.util;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class BookmarksTestUtil {

	public static BookmarksEntry addEntry() throws Exception {
		return addEntry(TestPropsValues.getGroupId());
	}

	public static BookmarksEntry addEntry(long groupId) throws Exception {
		return addEntry(
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static BookmarksEntry addEntry(
			long folderId, ServiceContext serviceContext)
		throws Exception {

		return addEntry("Test Entry", folderId, serviceContext);
	}

	public static BookmarksEntry addEntry(
			String name, long folderId, ServiceContext serviceContext)
		throws Exception {

		String url = "http://www.liferay.com";
		String description = "This is a test entry.";

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return BookmarksEntryServiceUtil.addEntry(
			serviceContext.getScopeGroupId(), folderId, name, url, description,
			serviceContext);
	}

	public static BookmarksFolder addFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		return addFolder(
			parentFolderId, name,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static BookmarksFolder addFolder(long groupId, String name)
		throws Exception {

		return addFolder(
			groupId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);
	}

	public static BookmarksFolder addFolder(
			long parentFolderId, String name, ServiceContext serviceContext)
		throws Exception {

		String description = "This is a test folder.";

		return BookmarksFolderServiceUtil.addFolder(
			parentFolderId, name, description, serviceContext);
	}

	public static BookmarksFolder addFolder(String name) throws Exception {
		return addFolder(TestPropsValues.getGroupId(), name);
	}

	public static SearchContext getSearchContext(
		long companyId, long groupId, long folderId, String keywords) {

		return getSearchContext(
			companyId, groupId, folderId, keywords, false, false);
	}

	public static SearchContext getSearchContext(
		long companyId, long groupId, long folderId, String keywords,
		boolean highlight, boolean score) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setFolderIds(new long[] {folderId});
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(highlight);
		queryConfig.setScoreEnabled(score);

		return searchContext;
	}

	public static void populateNotificationsServiceContext(
		ServiceContext serviceContext, String command) {

		serviceContext.setAttribute("entryURL", "http://localhost");

		if (Validator.isNotNull(command)) {
			serviceContext.setCommand(command);
		}

		serviceContext.setLayoutFullURL("http://localhost");
	}

	public static BookmarksEntry updateEntry(BookmarksEntry entry)
		throws Exception {

		return updateEntry(entry, RandomTestUtil.randomString());
	}

	public static BookmarksEntry updateEntry(BookmarksEntry entry, String name)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(entry.getGroupId());

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");

		return BookmarksEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), entry.getEntryId(), entry.getGroupId(),
			entry.getFolderId(), name, entry.getUrl(), entry.getDescription(),
			serviceContext);
	}

}