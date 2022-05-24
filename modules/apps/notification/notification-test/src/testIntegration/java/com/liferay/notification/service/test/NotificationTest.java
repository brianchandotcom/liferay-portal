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

package com.liferay.notification.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.util.NotificationHelper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gustavo Lima
 */
@RunWith(Arquillian.class)
public class NotificationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testEmailAddressRecipient() throws Exception {
		_user.setEmailAddress("mail@mail.com");

		_userLocalService.updateUser(_user);

		_notificationTemplate = NotificationTestUtil.addNotificationTemplate(
			_user.getUserId(), _user.getEmailAddress(), "Test");

		_notificationHelper.sendNotification(
			_user.getUserId(), _notificationTemplate, _notificationTemplate);

		Assert.assertEquals(
			1,
			_notificationQueueEntryLocalService.
				getNotificationQueueEntriesCount());
	}

	private static User _user;

	@Inject
	private NotificationHelper _notificationHelper;

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@DeleteAfterTestRun
	private NotificationTemplate _notificationTemplate;

	@Inject
	private UserLocalService _userLocalService;

}