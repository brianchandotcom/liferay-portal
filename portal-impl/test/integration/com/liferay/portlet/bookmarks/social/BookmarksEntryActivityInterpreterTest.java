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

package com.liferay.portlet.bookmarks.social;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;
import com.liferay.portlet.social.BaseSocialActivityInterpreterTestCase;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityInterpreter;

import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BookmarksEntryActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@Override
	protected void addActivities() throws Exception {
		_entry = BookmarksTestUtil.addEntry(group.getGroupId(), true);
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return new BookmarksEntryActivityInterpreter();
	}

	@Override
	protected int[] getActivityTypes() {
		return new int[] {
			BookmarksActivityKeys.ADD_ENTRY, BookmarksActivityKeys.UPDATE_ENTRY,
			SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH
		};
	}

	@Override
	protected void moveModelsToTrash() throws Exception {
		BookmarksEntryLocalServiceUtil.moveEntryToTrash(
			TestPropsValues.getUserId(), _entry.getEntryId());
	}

	@Override
	protected void renameModels() throws Exception {
		_entry.setName(ServiceTestUtil.randomString());

		serviceContext.setCommand(Constants.UPDATE);

		BookmarksEntryServiceUtil.updateEntry(
			_entry.getEntryId(), serviceContext.getScopeGroupId(),
			_entry.getFolderId(), _entry.getName(), _entry.getUrl(),
			_entry.getUrl(), serviceContext);
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		BookmarksEntryLocalServiceUtil.restoreEntryFromTrash(
			TestPropsValues.getUserId(), _entry.getEntryId());
	}

	private BookmarksEntry _entry;

}