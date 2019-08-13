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
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBMessageDisplay;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBDiscussionLocalServiceUtil;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.notifications.test.util.BaseUserNotificationTestCase;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import java.util.Dictionary;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@RunWith(Arquillian.class)
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

	@Test
	public void testAddUserNotificationWhenDiscussionEmailPortalPropertyDisabled()
		throws Exception {

		_withDiscussionEmailCommentsAddedDisabled(
			() -> {
				subscribeToContainer();

				BaseModel<?> baseModel = addBaseModel();

				Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());

				List<JSONObject> userNotificationEventsJSONObjects =
					getUserNotificationEventsJSONObjects(user.getUserId());

				Assert.assertEquals(
					userNotificationEventsJSONObjects.toString(), 1,
					userNotificationEventsJSONObjects.size());

				for (JSONObject userNotificationEventsJSONObject :
						userNotificationEventsJSONObjects) {

					Assert.assertTrue(
						isValidUserNotificationEventObject(
							(Long)baseModel.getPrimaryKeyObj(),
							userNotificationEventsJSONObject));
					Assert.assertEquals(
						UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
						userNotificationEventsJSONObject.getInt(
							"notificationType"));
				}
			});
	}

	@Test
	public void testUpdateUserNotificationWhenDiscussionEmailPortalPropertyDisabled()
		throws Exception {

		_withDiscussionEmailCommentsAddedDisabled(
			() -> {
				BaseModel<?> baseModel = addBaseModel();

				subscribeToContainer();

				updateBaseModel(baseModel);

				Assert.assertEquals(0, MailServiceTestUtil.getInboxSize());

				List<JSONObject> userNotificationEventsJSONObjects =
					getUserNotificationEventsJSONObjects(user.getUserId());

				Assert.assertEquals(
					userNotificationEventsJSONObjects.toString(), 1,
					userNotificationEventsJSONObjects.size());

				for (JSONObject userNotificationEventsJSONObject :
						userNotificationEventsJSONObjects) {

					Assert.assertTrue(
						isValidUserNotificationEventObject(
							(Long)baseModel.getPrimaryKeyObj(),
							userNotificationEventsJSONObject));
					Assert.assertEquals(
						UserNotificationDefinition.
							NOTIFICATION_TYPE_UPDATE_ENTRY,
						userNotificationEventsJSONObject.getInt(
							"notificationType"));
				}
			});
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
		return "com_liferay_comment_web_portlet_CommentPortlet";
	}

	@Override
	protected boolean isValidUserNotificationEventObject(
			long baseEntryId, JSONObject userNotificationEventJSONObject)
		throws Exception {

		MBMessage mbMessage = MBMessageLocalServiceUtil.getMessage(baseEntryId);

		if (!mbMessage.isDiscussion()) {
			return false;
		}

		long classPK = userNotificationEventJSONObject.getLong("classPK");

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

	private void _withDiscussionEmailCommentsAddedDisabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("discussionEmailCommentsAddedEnabled", false);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.comment.configuration." +
						"CommentGroupServiceConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	private BlogsEntry _entry;

}