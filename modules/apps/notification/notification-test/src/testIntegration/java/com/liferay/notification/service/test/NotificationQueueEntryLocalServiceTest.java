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
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;

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
				NotificationQueueEntryConstants.STATUS_SENT);

		Assert.assertEquals(
			NotificationQueueEntryConstants.STATUS_SENT,
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

		return _notificationQueueEntryLocalService.addNotificationQueueEntry(
			_createNotificationContext(
				TestPropsValues.getUser(), StringUtil.randomString(),
				StringUtil.randomString(), NotificationConstants.TYPE_EMAIL));
	}

	private NotificationQueueEntry _addNotificationQueueEntry(
			User user, String body, String subject, String type)
		throws Exception {

		return _notificationQueueEntryLocalService.addNotificationQueueEntry(
			_createNotificationContext(user, body, subject, type));
	}

	private NotificationContext _createNotificationContext(
			User user, String body, String subject, String type)
		throws Exception {

		NotificationContext notificationContext = new NotificationContext();

		NotificationQueueEntry notificationQueueEntry =
			_notificationQueueEntryLocalService.createNotificationQueueEntry(
				RandomTestUtil.randomInt());

		notificationQueueEntry.setUserId(user.getUserId());
		notificationQueueEntry.setUserName(user.getFullName());
		notificationQueueEntry.setBody(body);
		notificationQueueEntry.setSubject(subject);
		notificationQueueEntry.setType(type);
		notificationQueueEntry.setStatus(
			NotificationQueueEntryConstants.STATUS_UNSENT);

		notificationContext.setNotificationQueueEntry(notificationQueueEntry);

		NotificationRecipient notificationRecipient =
			_notificationRecipientLocalService.createNotificationRecipient(
				_counterLocalService.increment());

		notificationRecipient.setClassName(
			NotificationQueueEntry.class.getName());
		notificationRecipient.setClassPK(
			notificationQueueEntry.getNotificationQueueEntryId());

		notificationContext.setNotificationRecipient(notificationRecipient);

		NotificationRecipientSetting notificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				createNotificationRecipientSetting(
					_counterLocalService.increment());

		notificationRecipientSetting.setNotificationRecipientId(
			notificationRecipient.getNotificationRecipientId());

		notificationContext.setNotificationRecipientSettings(
			Arrays.asList(notificationRecipientSetting));

		notificationContext.setType(
			NotificationConstants.TYPE_USER_NOTIFICATION);

		return notificationContext;
	}

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Inject
	private NotificationRecipientLocalService
		_notificationRecipientLocalService;

	@Inject
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	@Inject
	private NotificationTemplateLocalService _notificationTemplateLocalService;

}