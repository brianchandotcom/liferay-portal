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
import com.liferay.notification.exception.NotificationTemplateFromException;
import com.liferay.notification.exception.NotificationTemplateNameException;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.After;
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
public class NotificationTemplateLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		List<NotificationQueueEntry> notificationQueueEntries =
			_notificationQueueEntryLocalService.getNotificationQueueEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntries) {

			_notificationQueueEntryLocalService.deleteNotificationQueueEntry(
				notificationQueueEntry.getNotificationQueueEntryId());
		}

		List<NotificationTemplate> notificationTemplates =
			_notificationTemplateLocalService.getNotificationTemplates(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (NotificationTemplate notificationTemplate :
				notificationTemplates) {

			_notificationTemplateLocalService.deleteNotificationTemplate(
				notificationTemplate.getNotificationTemplateId());
		}
	}

	@Test
	public void testAddNotificationTemplate() throws Exception {
		try {
			_addNotificationTemplate("", RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (NotificationTemplateNameException
					notificationTemplateNameException) {

			Assert.assertEquals(
				"Name is null", notificationTemplateNameException.getMessage());
		}

		try {
			_addNotificationTemplate(RandomTestUtil.randomString(), "");

			Assert.fail();
		}
		catch (NotificationTemplateFromException
					notificationTemplateFromException) {

			Assert.assertEquals(
				"From is null", notificationTemplateFromException.getMessage());
		}

		NotificationTemplate notificationTemplate = _addNotificationTemplate(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationTemplate);
		Assert.assertNotNull(
			_notificationTemplateLocalService.fetchNotificationTemplate(
				notificationTemplate.getNotificationTemplateId()));
	}

	@Test
	public void testDeleteNotificationTemplate() throws Exception {
		NotificationTemplate notificationTemplate = _addNotificationTemplate(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		NotificationQueueEntry notificationQueueEntry =
			_notificationQueueEntryLocalService.addNotificationQueueEntry(
				TestPropsValues.getUserId(),
				notificationTemplate.getNotificationTemplateId(),
				notificationTemplate.getBcc(),
				notificationTemplate.getBody(LocaleUtil.US),
				notificationTemplate.getCc(), RandomTestUtil.randomString(), 0,
				notificationTemplate.getFrom(),
				notificationTemplate.getFromName(LocaleUtil.US), 0,
				notificationTemplate.getSubject(LocaleUtil.US),
				notificationTemplate.getTo(LocaleUtil.US),
				RandomTestUtil.randomString(), NotificationConstants.TYPE_EMAIL,
				Collections.emptyList());

		Assert.assertEquals(
			notificationTemplate.getNotificationTemplateId(),
			notificationQueueEntry.getNotificationTemplateId());

		_notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate.getNotificationTemplateId());

		notificationQueueEntry =
			_notificationQueueEntryLocalService.fetchNotificationQueueEntry(
				notificationQueueEntry.getNotificationQueueEntryId());

		Assert.assertEquals(
			0, notificationQueueEntry.getNotificationTemplateId());

		_notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntry.getNotificationQueueEntryId());
	}

	private NotificationTemplate _addNotificationTemplate(
			String name, String from)
		throws PortalException {

		return _notificationTemplateLocalService.addNotificationTemplate(
			TestPropsValues.getUserId(), 0, RandomTestUtil.randomString(),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), from,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			name, null,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()),
			NotificationConstants.TYPE_EMAIL, Collections.emptyList());
	}

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Inject
	private NotificationTemplateLocalService _notificationTemplateLocalService;

}