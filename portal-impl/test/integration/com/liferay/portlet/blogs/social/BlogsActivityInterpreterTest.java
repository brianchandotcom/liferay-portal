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

package com.liferay.portlet.blogs.social;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.BlogsTestUtil;
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
public class BlogsActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@Override
	protected void addActivities() throws Exception {
		_entry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return new BlogsActivityInterpreter();
	}

	@Override
	protected int[] getActivityTypes() {
		return new int[] {
			BlogsActivityKeys.ADD_ENTRY, BlogsActivityKeys.UPDATE_ENTRY,
			SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH
		};
	}

	@Override
	protected void moveModelsToTrash() throws Exception {
		BlogsEntryLocalServiceUtil.moveEntriesToTrash(
			group.getGroupId(), TestPropsValues.getUserId());
	}

	@Override
	protected void renameModels() throws Exception {
		_entry.setTitle(ServiceTestUtil.randomString());

		serviceContext.setCommand(Constants.UPDATE);

		BlogsEntryLocalServiceUtil.updateEntry(
			_entry.getUserId(), _entry.getEntryId(), _entry.getTitle(),
			_entry.getDescription(), _entry.getContent(), 1, 1, 2012, 12, 00,
			true, true, new String[0], _entry.getSmallImage(),
			_entry.getSmallImageURL(), StringPool.BLANK, null, serviceContext);
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		BlogsEntryLocalServiceUtil.restoreEntryFromTrash(
			TestPropsValues.getUserId(), _entry.getEntryId());
	}

	private BlogsEntry _entry;

}