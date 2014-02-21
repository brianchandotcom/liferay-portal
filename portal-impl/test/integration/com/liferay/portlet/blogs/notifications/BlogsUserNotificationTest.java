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

package com.liferay.portlet.blogs.notifications;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDelivery;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserNotificationDeliveryLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseMailTestCase;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.BlogsTestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@ExecutionTestListeners(listeners = {
	MainServletExecutionTestListener.class,
	SynchronousDestinationExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BlogsUserNotificationTest extends BaseMailTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();

		_user = TestPropsValues.getUser();

		BlogsEntryLocalServiceUtil.subscribe(
			_user.getUserId(), _group.getGroupId());

		_userNotificationDeliveries =
			getUserNotificationDeliveries(_user.getUserId());

		_logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		GroupLocalServiceUtil.deleteGroup(_group);

		deleteUserNotificationEvents(_user.getUserId());

		deleteUserNotificationDeliveries();
	}

	@Test
	public void testAddBlogUserNotificationWhenEmailNotificationsDisabled()
		throws Exception {

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_EMAIL, false);

		BlogsEntry entry = addBlogsEntry();

		Assert.assertEquals(0, _logRecords.size());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(
				_user.getUserId(), entry.getEntryId());

		Assert.assertEquals(1, entryUserNotificationEventsJsonObjects.size());

		for (JSONObject jsonObject : entryUserNotificationEventsJsonObjects) {
			Assert.assertEquals(
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				jsonObject.getInt("notificationType"));
		}
	}

	@Test
	public void testUpdateBlogUserNotificationWhenEmailNotificationsDisabled()
		throws Exception {

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_EMAIL, false);

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			UserNotificationDeliveryConstants.TYPE_EMAIL, false);

		BlogsEntry entry = addBlogsEntry();

		updateEntry(entry);

		Assert.assertEquals(0, _logRecords.size());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(
				_user.getUserId(), entry.getEntryId());

		Assert.assertEquals(2, entryUserNotificationEventsJsonObjects.size());

		int[] notificationTypes = new int[0];

		for (JSONObject entryUserNotificationEventsJsonObject :
				entryUserNotificationEventsJsonObjects) {

			notificationTypes = ArrayUtil.append(
				notificationTypes,
				entryUserNotificationEventsJsonObject.getInt(
					"notificationType"));
		}

		Assert.assertNotEquals(notificationTypes[0], notificationTypes[1]);

		Assert.assertTrue(
			ArrayUtil.contains(
				notificationTypes,
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY));

		Assert.assertTrue(
			ArrayUtil.contains(
				notificationTypes,
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY));
	}

	@Test
	public void testAddBlogUserNotificationWhenNotificationsDisabled()
		throws Exception {

		updateUserNotificationsDelivery(false);

		BlogsEntry entry = addBlogsEntry();

		Assert.assertEquals(0, _logRecords.size());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(
				_user.getUserId(), entry.getEntryId());

		Assert.assertEquals(0, entryUserNotificationEventsJsonObjects.size());
	}

	@Test
	public void testUpdateBlogUserNotificationWhenNotificationsDisabled()
		throws Exception {

		updateUserNotificationsDelivery(false);

		BlogsEntry entry = addBlogsEntry();

		updateEntry(entry);

		Assert.assertEquals(0, _logRecords.size());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(
				_user.getUserId(), entry.getEntryId());

		Assert.assertEquals(0, entryUserNotificationEventsJsonObjects.size());
	}

	@Test
	public void testAddBlogUserNotificationWhenWebsiteNotificationsDisabled()
		throws Exception {

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		BlogsEntry entry = addBlogsEntry();

		Assert.assertEquals(1, _logRecords.size());

		LogRecord logRecord = _logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(
				_user.getUserId(), entry.getEntryId());

		Assert.assertEquals(0, entryUserNotificationEventsJsonObjects.size());
	}

	@Test
	public void testUpdateBlogUserNotificationWhenWebsiteNotificationsDisabled()
		throws Exception {

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		BlogsEntry entry = addBlogsEntry();

		updateEntry(entry);

		Assert.assertEquals(2, _logRecords.size());

		LogRecord logRecord = _logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		logRecord = _logRecords.get(1);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(
				_user.getUserId(), entry.getEntryId());

		Assert.assertEquals(0, entryUserNotificationEventsJsonObjects.size());
	}

	@Test
	public void testAddBlogUserNotification() throws Exception {
		BlogsEntry entry = addBlogsEntry();

		Assert.assertEquals(1, _logRecords.size());

		LogRecord logRecord = _logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(
				_user.getUserId(), entry.getEntryId());

		Assert.assertEquals(1, entryUserNotificationEventsJsonObjects.size());

		for (JSONObject entryUserNotificationEventsJsonObject :
				entryUserNotificationEventsJsonObjects) {

			Assert.assertEquals(
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				entryUserNotificationEventsJsonObject.getInt(
					"notificationType"));
		}
	}

	@Test
	public void testUpdateBlogUserNotification() throws Exception {
		BlogsEntry entry = addBlogsEntry();

		updateEntry(entry);

		Assert.assertEquals(2, _logRecords.size());

		LogRecord logRecord = _logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		logRecord = _logRecords.get(1);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(
				_user.getUserId(), entry.getEntryId());

		Assert.assertEquals(2, entryUserNotificationEventsJsonObjects.size());

		int[] notificationTypes = new int[0];

		for (JSONObject entryUserNotificationEventsJsonObject :
				entryUserNotificationEventsJsonObjects) {

			notificationTypes = ArrayUtil.append(
				notificationTypes,
				entryUserNotificationEventsJsonObject.getInt(
					"notificationType"));
		}

		Assert.assertNotEquals(notificationTypes[0], notificationTypes[1]);

		Assert.assertTrue(
			ArrayUtil.contains(
				notificationTypes,
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY));

		Assert.assertTrue(
			ArrayUtil.contains(
				notificationTypes,
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY));
	}

	protected BlogsEntry addBlogsEntry() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");
		serviceContext.setScopeGroupId(_group.getGroupId());

		return BlogsTestUtil.addEntry(
			_user.getUserId(), ServiceTestUtil.randomString(), true,
			serviceContext);
	}

	protected List<UserNotificationDelivery> getUserNotificationDeliveries(
			long userId)
		throws Exception {

		List<UserNotificationDelivery> userNotificationDeliveries =
			new ArrayList<UserNotificationDelivery>();

		userNotificationDeliveries.add(
			UserNotificationDeliveryLocalServiceUtil.
				getUserNotificationDelivery(
					userId, PortletKeys.BLOGS, 0,
					UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
					UserNotificationDeliveryConstants.TYPE_EMAIL, true));

		userNotificationDeliveries.add(
			UserNotificationDeliveryLocalServiceUtil.
				getUserNotificationDelivery(
					userId, PortletKeys.BLOGS, 0,
					UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
					UserNotificationDeliveryConstants.TYPE_WEBSITE, true));

		userNotificationDeliveries.add(
			UserNotificationDeliveryLocalServiceUtil.
				getUserNotificationDelivery(
					userId, PortletKeys.BLOGS, 0,
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
					UserNotificationDeliveryConstants.TYPE_EMAIL, true));

		userNotificationDeliveries.add(
			UserNotificationDeliveryLocalServiceUtil.
				getUserNotificationDelivery(
					userId, PortletKeys.BLOGS, 0,
					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
					UserNotificationDeliveryConstants.TYPE_WEBSITE, true));

		return userNotificationDeliveries;
	}

	protected void deleteUserNotificationDeliveries() throws Exception {
		UserNotificationDeliveryLocalServiceUtil.
			deleteUserNotificationDeliveries(_user.getUserId());
	}

	protected void deleteUserNotificationEvents(long userId) throws Exception {
		List<UserNotificationEvent> userNotificationEvents =
			UserNotificationEventLocalServiceUtil.getUserNotificationEvents(
				userId);

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent);
		}
	}

	protected List<JSONObject> getEntryUserNotificationEventsJsonObjects(
			long userId, long entryId)
		throws Exception {

		List<UserNotificationEvent> userNotificationEvents =
			UserNotificationEventLocalServiceUtil.getUserNotificationEvents(
				userId);

		List<JSONObject> userNotificationEventJsonObjects =
			new ArrayList<JSONObject>(userNotificationEvents.size());

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());

			long classPK = jsonObject.getLong("classPK");

			if (classPK != entryId) {
				continue;
			}

			userNotificationEventJsonObjects.add(jsonObject);
		}

		return userNotificationEventJsonObjects;
	}

	protected void updateUserNotificationsDelivery(boolean deliver)
		throws Exception {

		for (UserNotificationDelivery userNotificationDelivery :
				_userNotificationDeliveries) {

			UserNotificationDeliveryLocalServiceUtil.
				updateUserNotificationDelivery(
					userNotificationDelivery.getUserNotificationDeliveryId(),
					deliver);
		}
	}

	protected void updateUserNotificationDelivery(
			int notificationType, int deliveryType, boolean deliver)
		throws Exception {

		for (UserNotificationDelivery userNotificationDelivery :
				_userNotificationDeliveries) {

			if ((userNotificationDelivery.getDeliveryType() != deliveryType) ||
				(userNotificationDelivery.getNotificationType() !=
						notificationType)) {

				continue;
			}

			UserNotificationDeliveryLocalServiceUtil.
				updateUserNotificationDelivery(
					userNotificationDelivery.getUserNotificationDeliveryId(),
					deliver);

			return;
		}

		Assert.fail("User notification does not exist");
	}

	private void updateEntry(BlogsEntry entry) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");
		serviceContext.setScopeGroupId(_group.getGroupId());

		BlogsEntryLocalServiceUtil.updateEntry(
			entry.getUserId(), entry.getEntryId(),
			ServiceTestUtil.randomString(), entry.getDescription(),
			entry.getContent(), 1, 1, 2012, 12, 00, true, true, new String[0],
			entry.getSmallImage(), entry.getSmallImageURL(), StringPool.BLANK,
			null, serviceContext);
	}

	private Group _group;
	private List<LogRecord> _logRecords;
	private User _user;
	private List<UserNotificationDelivery> _userNotificationDeliveries =
		new ArrayList<UserNotificationDelivery>();

}