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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousMailTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.MailServiceTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
@Sync
public class CommentsSubscriptionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER);
	}

	@Test
	public void testSubscriptionMBDiscussionWhenAddingMBMessage()
		throws Exception {

		BlogsEntry blogsEntry = addEntry();

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

		BlogsEntry blogsEntry = addEntry();

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

	protected BlogsEntry addEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		BlogsEntry entry =
			BlogsEntryLocalServiceUtil.addEntry(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), serviceContext);

		return entry;
	}

	@DeleteAfterTestRun
	private Group _group;

	private User _user;

}