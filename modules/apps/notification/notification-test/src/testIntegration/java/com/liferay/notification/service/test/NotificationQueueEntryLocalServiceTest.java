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
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

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
	public void testNotificationQueueEntry() throws Exception {
		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.addNotificationTemplate(
				TestPropsValues.getUserId(), 0, RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				Collections.emptyList());

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
				RandomTestUtil.randomString(), Collections.emptyList());

		Assert.assertNotNull(notificationQueueEntry);
		Assert.assertNotNull(
			_notificationQueueEntryLocalService.fetchNotificationQueueEntry(
				notificationQueueEntry.getNotificationQueueEntryId()));
	}

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Inject
	private NotificationTemplateLocalService _notificationTemplateLocalService;

}