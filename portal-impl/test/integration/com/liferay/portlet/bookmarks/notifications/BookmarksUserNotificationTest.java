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

package com.liferay.portlet.bookmarks.notifications;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseUserNotificationTestCase;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BookmarksUserNotificationTest
	extends BaseUserNotificationTestCase {

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		return BookmarksTestUtil.addEntry(
			_folder.getFolderId(), true, serviceContext);
	}

	@Override
	protected void addContainerModel() throws Exception {
		_folder = BookmarksTestUtil.addFolder(
			group.getGroupId(), ServiceTestUtil.randomString());
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.BOOKMARKS;
	}

	@Override
	protected void subscribeToContainer() throws Exception {
		BookmarksFolderLocalServiceUtil.subscribeFolder(
			user.getUserId(), group.getGroupId(), _folder.getFolderId());
	}

	@Override
	protected BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception {

		return BookmarksTestUtil.updateEntry((BookmarksEntry)baseModel);
	}

	private BookmarksFolder _folder;

}