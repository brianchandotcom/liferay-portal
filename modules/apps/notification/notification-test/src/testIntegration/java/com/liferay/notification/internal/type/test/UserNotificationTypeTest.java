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
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashMap;
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
public class UserNotificationTypeTest extends BaseNotificationTypeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSendNotificationRecipientTypeRole() throws Exception {
		_testSendNotification(
			1,
			Arrays.asList(
				createNotificationRecipientSetting("roleName", "Administrator"),
				createNotificationRecipientSetting("roleName", "User")),
			NotificationRecipientConstants.TYPE_ROLE);
	}

	@Test
	public void testSendNotificationRecipientTypeTerm() throws Exception {
		_testSendNotification(
			2,
			Arrays.asList(
				createNotificationRecipientSetting("term", getTerm("creator")),
				createNotificationRecipientSetting("term", "test")),
			NotificationRecipientConstants.TYPE_TERM);
	}

	@Test
	public void testSendNotificationRecipientTypeUser() throws Exception {
		_testSendNotification(
			1,
			Arrays.asList(
				createNotificationRecipientSetting("userScreenName", "test")),
			NotificationRecipientConstants.TYPE_USER);
	}

	private NotificationContext _createNotificationContext(
		List<NotificationRecipientSetting> notificationRecipientSettings,
		String recipientType, List<String> termNames) {

		NotificationContext notificationContext = new NotificationContext();

		notificationContext.setClassName(RandomTestUtil.randomString());
		notificationContext.setClassPK(RandomTestUtil.randomLong());

		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.createNotificationTemplate(0L);

		StringBuilder sb = new StringBuilder();

		termNames.forEach(termName -> sb.append(StringPool.SPACE + termName));

		notificationTemplate.setEditorType(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT);
		notificationTemplate.setName(RandomTestUtil.randomString());
		notificationTemplate.setRecipientType(recipientType);
		notificationTemplate.setSubject("Subject" + sb.toString());
		notificationTemplate.setType(
			NotificationConstants.TYPE_USER_NOTIFICATION);

		notificationContext.setNotificationTemplate(notificationTemplate);

		notificationContext.setNotificationRecipient(
			notificationRecipientLocalService.createNotificationRecipient(0L));
		notificationContext.setNotificationRecipientSettings(
			notificationRecipientSettings);
		notificationContext.setType(
			NotificationConstants.TYPE_USER_NOTIFICATION);

		return notificationContext;
	}

	private void _testRelevantUserTermValues(
			List<NotificationQueueEntry> notificationQueueEntries,
			List<NotificationRecipientSetting> notificationRecipientSettings,
			String recipientType, HashMap<String, String> values)
		throws Exception {

		ObjectEntry objectEntry = objectEntryLocalService.addObjectEntry(
			user2.getUserId(), 0, objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"textObjectFieldName", StringPool.BLANK
			).build(),
			ServiceContextTestUtil.getServiceContext());

		sendNotification(
			new NotificationContextBuilder(
			).className(
				objectDefinition.getClassName()
			).classPK(
				objectEntry.getObjectEntryId()
			).notificationTemplate(
				notificationTemplateLocalService.addNotificationTemplate(
					_createNotificationContext(
						notificationRecipientSettings, recipientType,
						ListUtil.fromMapKeys(values)))
			).termValues(
				HashMapBuilder.<String, Object>put(
					"creator", String.valueOf(user2.getUserId())
				).put(
					"currentUserId", String.valueOf(user2.getUserId())
				).build()
			).userId(
				user2.getUserId()
			).build(),
			NotificationConstants.TYPE_USER_NOTIFICATION);

		notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationQueueEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		notificationQueueEntry = notificationQueueEntries.get(
			notificationQueueEntries.size() - 1);

		assertTerms(
			ListUtil.fromMapValues(values),
			ListUtil.fromString(
				StringUtil.removeSubstring(
					notificationQueueEntry.getSubject(), "Subject "),
				StringPool.BLANK));
	}

	private void _testSendNotification(
			long expectedUserNotificationEventsCount,
			List<NotificationRecipientSetting> notificationRecipientSettings,
			String recipientType)
		throws Exception {

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationQueueEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			notificationQueueEntries.toString(), 0,
			notificationQueueEntries.size());

		ObjectEntry objectEntry = objectEntryLocalService.addObjectEntry(
			user1.getUserId(), 0, objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"textObjectFieldName", "textObjectFieldNameValue"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			0,
			userNotificationEventLocalService.getUserNotificationEventsCount(
				user1.getUserId()));

		Map<String, String> values = HashMapBuilder.put(
			getTerm("textObjectFieldName"), "textObjectFieldNameValue"
		).build();

		sendNotification(
			new NotificationContextBuilder(
			).className(
				objectDefinition.getClassName()
			).classPK(
				objectEntry.getObjectEntryId()
			).notificationTemplate(
				notificationTemplateLocalService.addNotificationTemplate(
					_createNotificationContext(
						notificationRecipientSettings, recipientType,
						ListUtil.fromMapKeys(values)))
			).termValues(
				HashMapBuilder.<String, Object>put(
					"creator", String.valueOf(user1.getUserId())
				).put(
					"currentUser", String.valueOf(user1.getUserId())
				).put(
					"textObjectFieldName", "textObjectFieldNameValue"
				).build()
			).userId(
				user1.getUserId()
			).build(),
			NotificationConstants.TYPE_USER_NOTIFICATION);

		notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationQueueEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			expectedUserNotificationEventsCount,
			userNotificationEventLocalService.getUserNotificationEventsCount(
				user1.getUserId()));

		userNotificationEventLocalService.deleteUserNotificationEvents(
			user1.getUserId());

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		notificationQueueEntry = notificationQueueEntries.get(0);

		Assert.assertEquals(
			NotificationQueueEntryConstants.STATUS_SENT,
			notificationQueueEntry.getStatus());

		assertTerms(
			ListUtil.fromMapValues(values),
			ListUtil.fromString(
				StringUtil.removeSubstring(
					notificationQueueEntry.getSubject(), "Subject "),
				StringPool.BLANK));

		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipient.getNotificationRecipientSettings()) {

			Assert.assertEquals(
				"userFullName", notificationRecipientSetting.getName());
			Assert.assertEquals(
				"Test Test", notificationRecipientSetting.getValue());
		}

		_testRelevantUserTermValues(
			notificationQueueEntries, notificationRecipientSettings,
			recipientType, getAuthorValues());

		_testRelevantUserTermValues(
			notificationQueueEntries, notificationRecipientSettings,
			recipientType, getCurrentUserValues());
	}

}