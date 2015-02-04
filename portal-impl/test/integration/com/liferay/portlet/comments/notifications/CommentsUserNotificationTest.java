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

package com.liferay.portlet.comments.notifications;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousMailTestRule;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;
import com.liferay.portlet.notifications.test.BaseUserNotificationTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@Sync
public class CommentsUserNotificationTest extends BaseUserNotificationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_entry = BlogsTestUtil.addEntry(group, true);
	}

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		return MBTestUtil.addDiscussionMessage(
			group.getGroupId(), BlogsEntry.class.getName(),
			_entry.getEntryId());
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.COMMENTS;
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

		return MBTestUtil.updateDiscussionMessage(
			group.getGroupId(), (Long)baseModel.getPrimaryKeyObj(),
			BlogsEntry.class.getName(), _entry.getEntryId());
	}

	private BlogsEntry _entry;

}