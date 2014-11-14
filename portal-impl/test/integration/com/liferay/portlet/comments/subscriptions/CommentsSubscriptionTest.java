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

package com.liferay.portlet.comments.subscriptions;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.MailServiceTestUtil;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousMailExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class CommentsSubscriptionTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER);
	}

	@Test
	public void testSubscriptionMBDiscussionWhenAddingMBMessage()
		throws Exception {

		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(_group, true);

		MBDiscussionLocalServiceUtil.subscribeDiscussion(
			_user.getUserId(), _group.getGroupId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		MBTestUtil.addDiscussionMessage(
			_group.getGroupId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@Test
	public void testSubscriptionMBDiscussionWhenUpdatingMBMessage()
		throws Exception {

		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(_group, true);

		MBMessage mbMessage = MBTestUtil.addDiscussionMessage(
			_group.getGroupId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		MBDiscussionLocalServiceUtil.subscribeDiscussion(
			_user.getUserId(), _group.getGroupId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		MBTestUtil.updateDiscussionMessage(
			_group.getGroupId(), mbMessage.getMessageId(),
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());
	}

	@DeleteAfterTestRun
	private Group _group;

	private User _user;

}