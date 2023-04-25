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
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.test.util.NotificationTemplateUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gustavo Lima
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class NotificationQueueEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddNotificationQueueEntry() throws Exception {
		Assert.assertEquals(
			0,
			_notificationQueueEntryLocalService.
				getNotificationQueueEntriesCount());

		User user = TestPropsValues.getUser();
		String body = StringUtil.randomString();
		String subject = StringUtil.randomString();

		NotificationQueueEntry notificationQueueEntry =
			_addNotificationQueueEntry(
				user, body, subject, NotificationConstants.TYPE_EMAIL);

		Assert.assertNotNull(notificationQueueEntry);
		Assert.assertEquals(
			user.getCompanyId(), notificationQueueEntry.getCompanyId());
		Assert.assertEquals(
			user.getUserId(), notificationQueueEntry.getUserId());
		Assert.assertEquals(
			user.getFullName(), notificationQueueEntry.getUserName());
		Assert.assertEquals(body, notificationQueueEntry.getBody());
		Assert.assertEquals(subject, notificationQueueEntry.getSubject());
		Assert.assertEquals(
			NotificationConstants.TYPE_EMAIL, notificationQueueEntry.getType());
		Assert.assertEquals(
			NotificationQueueEntryConstants.STATUS_UNSENT,
			notificationQueueEntry.getStatus());

		Assert.assertEquals(
			1,
			_notificationQueueEntryLocalService.
				getNotificationQueueEntriesCount());

		_notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntry);
	}

	@Test
	public void testDeleteNotificationQueueEntry() throws Exception {
		NotificationQueueEntry notificationQueueEntry =
			_addNotificationQueueEntry();

		_notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntry.getNotificationQueueEntryId());

		Assert.assertEquals(
			0,
			_notificationQueueEntryLocalService.
				getNotificationQueueEntriesCount());
	}

	@Test
	public void testResendNotificationQueueEntry() throws Exception {
		NotificationQueueEntry notificationQueueEntry =
			_addNotificationQueueEntry();

		notificationQueueEntry =
			_notificationQueueEntryLocalService.updateStatus(
				notificationQueueEntry.getNotificationQueueEntryId(),
				NotificationQueueEntryConstants.STATUS_FAILED);

		Assert.assertEquals(
			NotificationQueueEntryConstants.STATUS_FAILED,
			notificationQueueEntry.getStatus());

		notificationQueueEntry =
			_notificationQueueEntryLocalService.resendNotificationQueueEntry(
				notificationQueueEntry.getNotificationQueueEntryId());

		Assert.assertEquals(
			NotificationQueueEntryConstants.STATUS_UNSENT,
			notificationQueueEntry.getStatus());
	}

	private NotificationQueueEntry _addNotificationQueueEntry()
		throws Exception {

		return _addNotificationQueueEntry(
			TestPropsValues.getUser(), StringUtil.randomString(),
			StringUtil.randomString(), NotificationConstants.TYPE_EMAIL);
	}

	private NotificationQueueEntry _addNotificationQueueEntry(
			User user, String body, String subject, String type)
		throws Exception {

		return _notificationQueueEntryLocalService.addNotificationQueueEntry(
			NotificationTemplateUtil.createNotificationContext(
				user, body, null, subject, type));
	}

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

}