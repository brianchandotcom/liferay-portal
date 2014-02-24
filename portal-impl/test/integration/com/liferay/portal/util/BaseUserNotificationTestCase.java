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

package com.liferay.portal.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
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
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BaseUserNotificationTestCase extends BaseMailTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		user = TestPropsValues.getUser();

		group = GroupTestUtil.addGroup();

		BlogsEntryLocalServiceUtil.subscribe(
			user.getUserId(), group.getGroupId());

		userNotificationDeliveries = getUserNotificationDeliveries(
			user.getUserId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		GroupLocalServiceUtil.deleteGroup(group);

		deleteUserNotificationEvents(user.getUserId());

		deleteUserNotificationDeliveries();
	}

	@Test
	public void testAddUserNotification() throws Exception {
		BaseModel baseModel = addBaseModel();

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(
				user.getUserId(), (Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(1, userNotificationEventsJSONObjects.size());

		for (JSONObject userNotificationEventsJSONObject :
				userNotificationEventsJSONObjects) {

			Assert.assertEquals(
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				userNotificationEventsJSONObject.getInt("notificationType"));
		}
	}

	@Test
	public void testAddUserNotificationWhenEmailNotificationsDisabled()
		throws Exception {

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_EMAIL, false);

		BaseModel baseModel = addBaseModel();

		Assert.assertEquals(0, logRecords.size());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(
				user.getUserId(), (Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(1, userNotificationEventsJSONObjects.size());

		for (JSONObject userNotificationEventsJSONObject :
				userNotificationEventsJSONObjects) {

			Assert.assertEquals(
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				userNotificationEventsJSONObject.getInt("notificationType"));
		}
	}

	@Test
	public void testAddUserNotificationWhenNotificationsDisabled()
		throws Exception {

		updateUserNotificationsDelivery(false);

		BaseModel baseModel = addBaseModel();

		Assert.assertEquals(0, logRecords.size());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(
				user.getUserId(), (Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(0, userNotificationEventsJSONObjects.size());
	}

	@Test
	public void testAddUserNotificationWhenWebsiteNotificationsDisabled()
		throws Exception {

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		BaseModel baseModel = addBaseModel();

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(
				user.getUserId(), (Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(0, userNotificationEventsJSONObjects.size());
	}

	@Test
	public void testUpdateUserNotification() throws Exception {
		BaseModel baseModel = addBaseModel();

		updateBaseModel(baseModel);

		Assert.assertEquals(2, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		logRecord = logRecords.get(1);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(
				user.getUserId(), (Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(2, userNotificationEventsJSONObjects.size());

		int[] notificationTypes = new int[0];

		for (JSONObject userNotificationEventsJSONObject :
				userNotificationEventsJSONObjects) {

			notificationTypes = ArrayUtil.append(
				notificationTypes,
				userNotificationEventsJSONObject.getInt("notificationType"));
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
	public void testUpdateUserNotificationWhenEmailNotificationsDisabled()
		throws Exception {

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_EMAIL, false);
		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			UserNotificationDeliveryConstants.TYPE_EMAIL, false);

		BaseModel baseModel = addBaseModel();

		updateBaseModel(baseModel);

		Assert.assertEquals(0, logRecords.size());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(
				user.getUserId(), (Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(2, userNotificationEventsJSONObjects.size());

		int[] notificationTypes = new int[0];

		for (JSONObject userNotificationEventsJSONObject :
				userNotificationEventsJSONObjects) {

			notificationTypes = ArrayUtil.append(
				notificationTypes,
				userNotificationEventsJSONObject.getInt("notificationType"));
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
	public void testUpdateUserNotificationWhenNotificationsDisabled()
		throws Exception {

		updateUserNotificationsDelivery(false);

		BaseModel baseModel = addBaseModel();

		updateBaseModel(baseModel);

		Assert.assertEquals(0, logRecords.size());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(
				user.getUserId(), (Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(0, userNotificationEventsJSONObjects.size());
	}

	@Test
	public void
			testUpdateUserNotificationWhenWebsiteNotificationsDisabled()
		throws Exception {

		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);
		updateUserNotificationDelivery(
			UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

		BaseModel baseModel = addBaseModel();

		updateBaseModel(baseModel);

		Assert.assertEquals(2, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		logRecord = logRecords.get(1);

		Assert.assertEquals("Sending email", logRecord.getMessage());

		List<JSONObject> userNotificationEventsJSONObjects =
			getUserNotificationEventsJSONObjects(
				user.getUserId(), (Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(0, userNotificationEventsJSONObjects.size());
	}

	protected BaseModel addBaseModel() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");
		serviceContext.setScopeGroupId(group.getGroupId());

		return BlogsTestUtil.addEntry(
			user.getUserId(), ServiceTestUtil.randomString(), true,
			serviceContext);
	}

	protected void deleteUserNotificationDeliveries() throws Exception {
		UserNotificationDeliveryLocalServiceUtil.
			deleteUserNotificationDeliveries(user.getUserId());
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

	protected List<JSONObject> getUserNotificationEventsJSONObjects(
			long userId, long blogsEntryId)
		throws Exception {

		List<UserNotificationEvent> userNotificationEvents =
			UserNotificationEventLocalServiceUtil.getUserNotificationEvents(
				userId);

		List<JSONObject> userNotificationEventJSONObjects =
			new ArrayList<JSONObject>(userNotificationEvents.size());

		for (UserNotificationEvent userNotificationEvent :
				userNotificationEvents) {

			JSONObject userNotificationEventJSONObject =
				JSONFactoryUtil.createJSONObject(
					userNotificationEvent.getPayload());

			long classPK = userNotificationEventJSONObject.getLong("classPK");

			if (classPK != blogsEntryId) {
				continue;
			}

			userNotificationEventJSONObjects.add(
				userNotificationEventJSONObject);
		}

		return userNotificationEventJSONObjects;
	}

	protected void updateBaseModel(BaseModel baseModel) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");
		serviceContext.setScopeGroupId(group.getGroupId());

		BlogsEntryLocalServiceUtil.updateEntry(
			blogsEntry.getUserId(), blogsEntry.getEntryId(),
			ServiceTestUtil.randomString(), blogsEntry.getDescription(),
			blogsEntry.getContent(), 1, 1, 2012, 12, 00, true, true,
			new String[0], blogsEntry.getSmallImage(),
			blogsEntry.getSmallImageURL(), StringPool.BLANK, null,
			serviceContext);
	}

	protected void updateUserNotificationDelivery(
			int notificationType, int deliveryType, boolean deliver)
		throws Exception {

		for (UserNotificationDelivery userNotificationDelivery :
			userNotificationDeliveries) {

			if ((userNotificationDelivery.getNotificationType() !=
					notificationType) ||
				(userNotificationDelivery.getDeliveryType() != deliveryType)) {

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

	protected void updateUserNotificationsDelivery(boolean deliver)
		throws Exception {

		for (UserNotificationDelivery userNotificationDelivery :
			userNotificationDeliveries) {

			UserNotificationDeliveryLocalServiceUtil.
				updateUserNotificationDelivery(
					userNotificationDelivery.getUserNotificationDeliveryId(),
					deliver);
		}
	}

	protected Group group;
	protected List<LogRecord> logRecords;
	protected User user;
	protected List<UserNotificationDelivery> userNotificationDeliveries =
		new ArrayList<UserNotificationDelivery>();

}