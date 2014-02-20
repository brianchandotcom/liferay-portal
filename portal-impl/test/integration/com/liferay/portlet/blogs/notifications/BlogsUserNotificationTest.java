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
import com.liferay.portal.kernel.notifications.UserNotificationDeliveryType;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
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

		setActiveAllUserNotificationDeliveries(true);

		_logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		GroupLocalServiceUtil.deleteGroup(_group);

		deleteUserNotificationEvents();

		deleteUserNotificationDeliveries();
	}

	@Test
	public void testBlogUserNotificationInactiveMailNotificationsOnAdd()
		throws Exception {

		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_EMAIL,
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY, false);

		BlogsEntry entry = addBlogsEntry();

		Assert.assertEquals("An email has been sent", 0, _logRecords.size());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(entry.getEntryId());

		if (entryUserNotificationEventsJsonObjects.isEmpty()) {
			Assert.fail("userNotification doesn't exists for this entry");
		}

		Assert.assertTrue(
			"More than 1 userNotificationEvent exist",
			(1 + initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals(1, entryUserNotificationEventsJsonObjects.size());

		for (JSONObject jsonObject : entryUserNotificationEventsJsonObjects) {
			Assert.assertEquals(
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				jsonObject.getInt("notificationType"));
		}
	}

	@Test
	public void testBlogUserNotificationInactiveMailNotificationsOnUpdate()
		throws Exception {

		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_EMAIL,
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY, false);

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_EMAIL,
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY, false);

		BlogsEntry entry = addBlogsEntry();

		updateEntry(entry);

		Assert.assertEquals("An email has been sent", 0, _logRecords.size());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(entry.getEntryId());

		if (entryUserNotificationEventsJsonObjects.isEmpty()) {
			Assert.fail("userNotification doesn't exists for this entry");
		}

		Assert.assertTrue(
			(2 + initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals(2, entryUserNotificationEventsJsonObjects.size());

		int[]notificationTypes = new int[0];

		for (JSONObject jsonObject : entryUserNotificationEventsJsonObjects) {
			notificationTypes = ArrayUtil.append(
				notificationTypes, jsonObject.getInt("notificationType"));
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
	public void testBlogUserNotificationInactiveNotificationsOnAdd()
		throws Exception {

		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		setActiveAllUserNotificationDeliveries(false);

		BlogsEntry entry = addBlogsEntry();

		Assert.assertEquals("An email has been sent", 0, _logRecords.size());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(entry.getEntryId());

		if (!entryUserNotificationEventsJsonObjects.isEmpty()) {
			Assert.fail("A userNotification exists for this entry");
		}

		Assert.assertTrue(
			(initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());
	}

	@Test
	public void testBlogUserNotificationInactiveNotificationsOnUpdate()
		throws Exception {

		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		setActiveAllUserNotificationDeliveries(false);

		BlogsEntry entry = addBlogsEntry();

		updateEntry(entry);

		Assert.assertEquals("An email has been sent", 0, _logRecords.size());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(entry.getEntryId());

		if (!entryUserNotificationEventsJsonObjects.isEmpty()) {
			Assert.fail("A userNotification exists for this entry");
		}

		Assert.assertTrue(
			(initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals(0, entryUserNotificationEventsJsonObjects.size());
	}

	@Test
	public void testBlogUserNotificationInactiveWebsiteNotificationsOnAdd()
		throws Exception {

		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_WEBSITE,
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY, false);

		BlogsEntry entry = addBlogsEntry();

		Assert.assertEquals(1, _logRecords.size());

		LogRecord logRecord = _logRecords.get(0);

		Assert.assertEquals(
			"No mail has been sent", "Sending email", logRecord.getMessage());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(entry.getEntryId());

		if (!entryUserNotificationEventsJsonObjects.isEmpty()) {
			Assert.fail("A userNotification exists for this entry");
		}

		Assert.assertTrue(
			(initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals(0, entryUserNotificationEventsJsonObjects.size());
	}

	@Test
	public void testBlogUserNotificationInactiveWebsiteNotificationsOnUpdate()
		throws Exception {

		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_WEBSITE,
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY, false);

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_WEBSITE,
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY, false);

		BlogsEntry entry = addBlogsEntry();

		updateEntry(entry);

		Assert.assertEquals(2, _logRecords.size());

		LogRecord logRecord = _logRecords.get(0);

		Assert.assertEquals(
			"No mail has been sent", "Sending email", logRecord.getMessage());

		logRecord = _logRecords.get(1);

		Assert.assertEquals(
			"No mail has been sent", "Sending email", logRecord.getMessage());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(entry.getEntryId());

		if (!entryUserNotificationEventsJsonObjects.isEmpty()) {
			Assert.fail("A userNotification exists for this entry");
		}

		Assert.assertTrue(
			(initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals(0, entryUserNotificationEventsJsonObjects.size());
	}

	@Test
	public void testBlogUserNotificationOnAdd() throws Exception {
		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		BlogsEntry entry = addBlogsEntry();

		Assert.assertEquals(1, _logRecords.size());

		LogRecord logRecord = _logRecords.get(0);

		Assert.assertEquals(
			"No mail has been sent", "Sending email", logRecord.getMessage());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(entry.getEntryId());

		if (entryUserNotificationEventsJsonObjects.isEmpty()) {
			Assert.fail("userNotification doesn't exists for this entry");
		}

		Assert.assertTrue(
			"More than 1 userNotificationEvent exist",
			(1 + initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals(1, entryUserNotificationEventsJsonObjects.size());

		for (JSONObject jsonObject : entryUserNotificationEventsJsonObjects) {
			Assert.assertEquals(
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				jsonObject.getInt("notificationType"));
		}
	}

	@Test
	public void testBlogUserNotificationOnUpdate() throws Exception {
		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		BlogsEntry entry = addBlogsEntry();

		updateEntry(entry);

		Assert.assertEquals(2, _logRecords.size());

		LogRecord logRecord = _logRecords.get(0);

		Assert.assertEquals(
			"No mail has been sent", "Sending email", logRecord.getMessage());

		logRecord = _logRecords.get(1);

		Assert.assertEquals(
			"No mail has been sent", "Sending email", logRecord.getMessage());

		List<JSONObject> entryUserNotificationEventsJsonObjects =
			getEntryUserNotificationEventsJsonObjects(entry.getEntryId());

		if (entryUserNotificationEventsJsonObjects.isEmpty()) {
			Assert.fail("userNotification doesn't exists for this entry");
		}

		Assert.assertTrue(
			(2 + initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals(2, entryUserNotificationEventsJsonObjects.size());

		int[]notificationTypes = new int[0];

		for (JSONObject jsonObject : entryUserNotificationEventsJsonObjects) {
			notificationTypes = ArrayUtil.append(
				notificationTypes, jsonObject.getInt("notificationType"));
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

	protected List<UserNotificationDelivery> addUserNotificationDeliveries()
		throws Exception {

		_userNotificationDeliveries = new ArrayList<UserNotificationDelivery>();

		Map<String, List<UserNotificationDefinition>>
			userNotificationDefinitionsMap =
				UserNotificationManagerUtil.getUserNotificationDefinitions();

		for (Map.Entry<String, List<UserNotificationDefinition>> entry :
				userNotificationDefinitionsMap.entrySet()) {

			String portletId = entry.getKey();

			if (!portletId.equals(PortletKeys.BLOGS)) {
				continue;
			}

			List<UserNotificationDefinition> userNotificationDefinitions =
				entry.getValue();

			for (UserNotificationDefinition userNotificationDefinition :
					userNotificationDefinitions) {

				Map<Integer, UserNotificationDeliveryType>
					userNotificationDeliveryTypesMap =
						userNotificationDefinition.
							getUserNotificationDeliveryTypes();

				for (Map.Entry<Integer, UserNotificationDeliveryType>
					userNotificationDeliveryTypeEntry :
					userNotificationDeliveryTypesMap.entrySet()) {

					UserNotificationDeliveryType userNotificationDeliveryType =
						userNotificationDeliveryTypeEntry.getValue();

					_userNotificationDeliveries.add(
						UserNotificationDeliveryLocalServiceUtil.
							getUserNotificationDelivery(
								_user.getUserId(), portletId,
								userNotificationDefinition.getClassNameId(),
								userNotificationDefinition.
									getNotificationType(),
								userNotificationDeliveryType.getType(),
								userNotificationDeliveryType.isDefault()));
				}
			}
		}

		if (_userNotificationDeliveries.isEmpty()) {
			Assert.fail("userNotificationDeliveries could not be empty");
		}

		return _userNotificationDeliveries;
	}

	protected void deleteUserNotificationDeliveries() throws Exception {
		UserNotificationDeliveryLocalServiceUtil.
			deleteUserNotificationDeliveries(_user.getUserId());
	}

	protected void deleteUserNotificationEvents() throws Exception {
		for (UserNotificationEvent userNotificationEvent :
				_userNotificationEvents) {

			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent);
		}
	}

	protected List<JSONObject> getEntryUserNotificationEventsJsonObjects(
			long entryId)
		throws Exception {

		_userNotificationEvents =
			UserNotificationEventLocalServiceUtil.getUserNotificationEvents(
				_user.getUserId());

		List<JSONObject> userNotificationEventJsonObjects =
			new ArrayList<JSONObject>(_userNotificationEvents.size());

		for (UserNotificationEvent userNotificationEvent :
				_userNotificationEvents) {

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

	protected void setActiveAllUserNotificationDeliveries(boolean active)
		throws Exception {

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_EMAIL,
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY, active);

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_EMAIL,
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY, active);

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_WEBSITE,
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY, active);

		setActiveUserNotificationDeliveryType(
			UserNotificationDeliveryConstants.TYPE_WEBSITE,
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY, active);
	}

	protected void setActiveUserNotificationDeliveryType(
			int deliverType, int notificationType, boolean active)
		throws Exception {

		if (_userNotificationDeliveries.isEmpty()) {
			addUserNotificationDeliveries();
		}

		for (UserNotificationDelivery userNotificationDelivery :
				_userNotificationDeliveries) {

			if ((userNotificationDelivery.getDeliveryType() == deliverType) &&
				(userNotificationDelivery.getNotificationType() ==
						notificationType)) {

				UserNotificationDeliveryLocalServiceUtil.
					updateUserNotificationDelivery(
						userNotificationDelivery.
							getUserNotificationDeliveryId(),
						active);

				return;
			}
		}

		Assert.fail("UserNotification doesn't exist");
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
	private List<UserNotificationEvent> _userNotificationEvents =
		new ArrayList<UserNotificationEvent>();

}