/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BookmarksSubscriptionTest extends BaseSubscriptionTestCase {

	public long addContainer(long containerId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), containerId,
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);

		return folder.getFolderId();
	}

	public long addEntry(long containerId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), group.getGroupId(), containerId,
			ServiceTestUtil.randomString(), "http://localhost",
			StringPool.BLANK, serviceContext);

		return entry.getEntryId();
	}

	public void addSubscriptionContainer(long containerId) throws Exception {
		long classPK = containerId;

		if (containerId == DEFAULT_PARENT_CONTAINER_ID) {
			classPK = group.getGroupId();
		}

		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			BookmarksFolder.class.getName(), classPK);
	}

	public void addSubscriptionEntry(long entryId) throws Exception {
		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			BookmarksEntry.class.getName(), entryId);
	}

	public long updateEntry(long entryId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");

		BookmarksEntry oldEntry = BookmarksEntryLocalServiceUtil.getEntry(
			entryId);

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), entryId, oldEntry.getGroupId(),
			oldEntry.getFolderId(), ServiceTestUtil.randomString(),
			oldEntry.getUrl(), oldEntry.getDescription(), serviceContext);

		return entry.getEntryId();
	}

}