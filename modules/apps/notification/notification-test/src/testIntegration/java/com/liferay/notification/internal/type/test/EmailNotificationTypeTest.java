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

package com.liferay.notification.internal.type.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.context.NotificationContextBuilder;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.util.NotificationRecipientSettingUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class EmailNotificationTypeTest extends BaseNotificationTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSendNotification() throws Exception {
		Assert.assertEquals(
			0,
			notificationQueueEntryLocalService.
				getNotificationQueueEntriesCount());

		sendNotification(
			new NotificationContextBuilder(
			).notificationTemplate(
				notificationTemplateLocalService.addNotificationTemplate(
					_createNotificationContext())
			).termValues(
				HashMapBuilder.<String, Object>put(
					"[%emailAddressTerm%]", "test@liferay.com"
				).put(
					"[%term%]", "termValue"
				).build()
			).userId(
				user.getUserId()
			).build(),
			NotificationConstants.TYPE_EMAIL);

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryLocalService.getUnsentNotificationEntries(
				NotificationConstants.TYPE_EMAIL);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntries.get(0);

		Assert.assertEquals("Body termValue", notificationQueueEntry.getBody());
		Assert.assertEquals(
			NotificationQueueEntryConstants.STATUS_UNSENT,
			notificationQueueEntry.getStatus());
		Assert.assertEquals(
			"Subject termValue", notificationQueueEntry.getSubject());

		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		Map<String, Object> notificationRecipientSettingsMap =
			NotificationRecipientSettingUtil.toMap(
				notificationRecipient.getNotificationRecipientSettings());

		Assert.assertEquals(
			"test@liferay.com,bcc@liferay.com",
			notificationRecipientSettingsMap.get("bcc"));
		Assert.assertEquals(
			"test@liferay.com,cc@liferay.com",
			notificationRecipientSettingsMap.get("cc"));
		Assert.assertEquals(
			"test@liferay.com", notificationRecipientSettingsMap.get("from"));
		Assert.assertEquals(
			"test@liferay.com",
			notificationRecipientSettingsMap.get("fromName"));
		Assert.assertEquals(
			"test@liferay.com", notificationRecipientSettingsMap.get("to"));
	}

	private NotificationContext _createNotificationContext() {
		NotificationContext notificationContext = new NotificationContext();

		notificationContext.setNotificationRecipient(
			notificationRecipientLocalService.createNotificationRecipient(0L));
		notificationContext.setNotificationRecipientSettings(
			Arrays.asList(
				createNotificationRecipientSetting(
					"bcc", "[%emailAddressTerm%],bcc@liferay.com"),
				createNotificationRecipientSetting(
					"cc", "[%emailAddressTerm%],cc@liferay.com"),
				createNotificationRecipientSetting(
					"from", "[%emailAddressTerm%]"),
				createNotificationRecipientSetting(
					"fromName",
					Collections.singletonMap(
						LocaleUtil.US, "[%emailAddressTerm%]")),
				createNotificationRecipientSetting(
					"to",
					Collections.singletonMap(
						LocaleUtil.US, "[%emailAddressTerm%]"))));

		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.createNotificationTemplate(0L);

		notificationTemplate.setBody("Body [%term%]");
		notificationTemplate.setEditorType(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT);
		notificationTemplate.setName(RandomTestUtil.randomString());
		notificationTemplate.setRecipientType(
			NotificationRecipientConstants.TYPE_EMAIL);
		notificationTemplate.setSubject("Subject [%term%]");
		notificationTemplate.setType(NotificationConstants.TYPE_EMAIL);

		notificationContext.setNotificationTemplate(notificationTemplate);

		notificationContext.setType(NotificationConstants.TYPE_EMAIL);

		return notificationContext;
	}

}