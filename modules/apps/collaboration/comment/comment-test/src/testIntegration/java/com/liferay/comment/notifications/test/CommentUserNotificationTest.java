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

package com.liferay.comment.notifications.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.comment.web.constants.CommentPortletKeys;
import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageConstants;
import com.liferay.message.boards.kernel.model.MBMessageDisplay;
import com.liferay.message.boards.kernel.model.MBThread;
import com.liferay.message.boards.kernel.service.MBDiscussionLocalServiceUtil;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;
import com.liferay.portlet.notifications.test.BaseUserNotificationTestCase;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class CommentUserNotificationTest extends BaseUserNotificationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		_entry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				TestPropsValues.getUserId(), group.getGroupId(),
				BlogsEntry.class.getName(), _entry.getEntryId(),
				WorkflowConstants.STATUS_APPROVED);

		MBThread thread = messageDisplay.getThread();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		MBTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.ADD);

		return MBMessageLocalServiceUtil.addDiscussionMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			group.getGroupId(), BlogsEntry.class.getName(), _entry.getEntryId(),
			thread.getThreadId(), MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(50),
			serviceContext);
	}

	@Override
	protected String getPortletId() {
		return CommentPortletKeys.COMMENT;
	}

	@Override
	protected boolean isValidUserNotificationEventObject(
			long baseEntryId, JSONObject userNotificationEventJSONObject)
		throws Exception {

		long classPK = userNotificationEventJSONObject.getLong("classPK");

		MBMessage mbMessage = MBMessageLocalServiceUtil.getMessage(baseEntryId);

		if (!mbMessage.isDiscussion()) {
			return false;
		}

		MBDiscussion mbDiscussion =
			MBDiscussionLocalServiceUtil.getThreadDiscussion(
				mbMessage.getThreadId());

		if (mbDiscussion.getDiscussionId() != classPK) {
			return false;
		}

		return true;
	}

	@Override
	protected void subscribeToContainer() throws Exception {
		MBDiscussionLocalServiceUtil.subscribeDiscussion(
			user.getUserId(), group.getGroupId(), BlogsEntry.class.getName(),
			_entry.getEntryId());
	}

	@Override
	protected BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		MBTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.UPDATE);

		return MBMessageLocalServiceUtil.updateDiscussionMessage(
			TestPropsValues.getUserId(), (Long)baseModel.getPrimaryKeyObj(),
			BlogsEntry.class.getName(), _entry.getEntryId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(50),
			serviceContext);
	}

	private BlogsEntry _entry;

}