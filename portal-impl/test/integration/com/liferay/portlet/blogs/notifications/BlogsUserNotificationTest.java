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
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		GroupLocalServiceUtil.deleteGroup(_group);

		for (UserNotificationEvent userNotificationEvent :
				_userNotificationEvents) {

			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent);
		}

		UserNotificationDeliveryLocalServiceUtil.
			deleteUserNotificationDeliveries(_user.getUserId());

		activateAllUserNotificationDeliveries();
	}

	@Test
	public void testBlogUserNotification() throws Exception {
		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		BlogsEntry entry = addBlogsEntry();

		long[] userNotificationEventsClassPKs = getUserNotificationClassPKs();

		if (!ArrayUtil.contains(
				userNotificationEventsClassPKs, entry.getEntryId())) {

			Assert.fail("No userNotificationEvent exists for the blogs entry");
		}

		Assert.assertTrue(
			"More than 1 userNotificationEvent was created",
			(1 + initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"No mail has been sent", "Sending email", logRecord.getMessage());
	}

	@Test
	public void testBlogUserNotificationEmailType() throws Exception {
		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		deactivateWebsiteUserNotificationDelivery();

		BlogsEntry entry = addBlogsEntry();

		long[] userNotificationEventsClassPKs = getUserNotificationClassPKs();

		if (ArrayUtil.contains(
				userNotificationEventsClassPKs, entry.getEntryId())) {

			Assert.fail("A userNotificationEvent exists for the blogs entry");
		}

		Assert.assertTrue(
			"A userNotificationEvent was created",
			(initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"No mail has been sent", "Sending email", logRecord.getMessage());
	}

	@Test
	public void testBlogUserNotificationWebsiteType() throws Exception {
		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		deactivateMailUserNotificationDelivery();

		BlogsEntry entry = addBlogsEntry();

		long[] userNotificationEventsClassPKs = getUserNotificationClassPKs();

		if (!ArrayUtil.contains(
				userNotificationEventsClassPKs, entry.getEntryId())) {

			Assert.fail("No userNotificationEvent exists for the blogs entry");
		}

		Assert.assertTrue(
			"More than 1 userNotificationEvent was created",
			(1 + initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals("An email has been sent", 0, logRecords.size());
	}

	@Test
	public void testBlogUserNotificationWhenDeactivatingAllUserNotifications()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		int initialUserNotificationEventsCount =
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(_user.getUserId());

		deactivateAllUserNotificationDeliveries();

		BlogsEntry entry = addBlogsEntry();

		long[] userNotificationEventsClassPKs = getUserNotificationClassPKs();

		if (ArrayUtil.contains(
				userNotificationEventsClassPKs, entry.getEntryId())) {

			Assert.fail("A userNotificationEvent exists for the blogs entry");
		}

		Assert.assertTrue(
			"A userNotificationEvent was created",
			(initialUserNotificationEventsCount) ==
				_userNotificationEvents.size());

		Assert.assertEquals("An email has been sent", 0, logRecords.size());
	}

	protected void activateAllUserNotificationDeliveries() throws Exception {
		List<UserNotificationDelivery> userNotificationDeliveries =
			addUserNotificationDeliveries();

		for (UserNotificationDelivery userNotificationDelivery :
				userNotificationDeliveries) {

			UserNotificationDeliveryLocalServiceUtil.
				updateUserNotificationDelivery(
					userNotificationDelivery.getUserNotificationDeliveryId(),
					true);
		}
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

		List<UserNotificationDelivery> userNotificationDeliveries =
			new ArrayList<UserNotificationDelivery>();

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

					userNotificationDeliveries.add(
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

		if (userNotificationDeliveries.isEmpty()) {
			Assert.fail("userNotificationDeliveries could not be empty");
		}

		return userNotificationDeliveries;
	}

	protected void deactivateAllUserNotificationDeliveries() throws Exception {
		deactivateMailUserNotificationDelivery();
		deactivateWebsiteUserNotificationDelivery();
	}

	protected void deactivateMailUserNotificationDelivery() throws Exception {
		List<UserNotificationDelivery> userNotificationDeliveries =
			addUserNotificationDeliveries();

		if (userNotificationDeliveries.isEmpty()) {
			Assert.fail("userNotificationDelivery could not be empty");
		}

		for (UserNotificationDelivery userNotificationDelivery :
				userNotificationDeliveries) {

			int deliveryType = userNotificationDelivery.getDeliveryType();

			if (deliveryType == UserNotificationDeliveryConstants.TYPE_EMAIL) {
				UserNotificationDeliveryLocalServiceUtil.
					updateUserNotificationDelivery(
						userNotificationDelivery.
							getUserNotificationDeliveryId(),
						false);
			}
		}
	}

	protected void deactivateWebsiteUserNotificationDelivery()
		throws Exception {

		List<UserNotificationDelivery> userNotificationDeliveries =
			addUserNotificationDeliveries();

		for (UserNotificationDelivery userNotificationDelivery :
				userNotificationDeliveries) {

			int deliveryType = userNotificationDelivery.getDeliveryType();

			if (deliveryType ==
					UserNotificationDeliveryConstants.TYPE_WEBSITE) {

				UserNotificationDeliveryLocalServiceUtil.
					updateUserNotificationDelivery(
						userNotificationDelivery.
							getUserNotificationDeliveryId(),
						false);
			}
		}
	}

	protected long[] getUserNotificationClassPKs() throws Exception {
		_userNotificationEvents =
			UserNotificationEventLocalServiceUtil.getUserNotificationEvents(
				_user.getUserId());

		long[] userNotificationEventsClassPKs =
			new long[_userNotificationEvents.size()];

		for (UserNotificationEvent userNotificationEvent :
				_userNotificationEvents) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());

			long classPK = jsonObject.getLong("classPK");

			userNotificationEventsClassPKs = ArrayUtil.append(
				userNotificationEventsClassPKs, classPK);
		}

		return userNotificationEventsClassPKs;
	}

	private Group _group;
	private User _user;
	private List<UserNotificationEvent> _userNotificationEvents;

}