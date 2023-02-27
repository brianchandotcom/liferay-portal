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
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		user = TestPropsValues.getUser();

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				user.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					new TextObjectFieldBuilder(
					).labelMap(
						LocalizedMapUtil.getLocalizedMap(
							RandomTestUtil.randomString())
					).name(
						"textObjectFieldName"
					).objectFieldSettings(
						Collections.emptyList()
					).build()));

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				user.getUserId(), _objectDefinition.getObjectDefinitionId());
	}

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
				createNotificationRecipientSetting("term", _getTerm("creator")),
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
		String recipientType) {

		NotificationContext notificationContext = new NotificationContext();

		notificationContext.setClassName(RandomTestUtil.randomString());
		notificationContext.setClassPK(RandomTestUtil.randomLong());

		NotificationTemplate notificationTemplate =
			notificationTemplateLocalService.createNotificationTemplate(0L);

		notificationTemplate.setEditorType(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT);
		notificationTemplate.setName(RandomTestUtil.randomString());
		notificationTemplate.setRecipientType(recipientType);
		notificationTemplate.setSubject(
			"Subject " + _getTerm("textObjectFieldName"));
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

	private String _getTerm(String objectFieldName) {
		return StringBundler.concat(
			"[%", StringUtil.upperCase(_objectDefinition.getShortName()), "_",
			StringUtil.upperCase(objectFieldName), "%]");
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

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			user.getUserId(), 0, _objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"textObjectFieldName", "textObjectFieldNameValue"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			0,
			_userNotificationEventLocalService.getUserNotificationEventsCount(
				user.getUserId()));

		sendNotification(
			new NotificationContextBuilder(
			).className(
				_objectDefinition.getClassName()
			).classPK(
				objectEntry.getObjectEntryId()
			).notificationTemplate(
				notificationTemplateLocalService.addNotificationTemplate(
					_createNotificationContext(
						notificationRecipientSettings, recipientType))
			).termValues(
				HashMapBuilder.<String, Object>put(
					"creator", String.valueOf(user.getUserId())
				).put(
					"textObjectFieldName", "textObjectFieldNameValue"
				).build()
			).userId(
				user.getUserId()
			).build(),
			NotificationConstants.TYPE_USER_NOTIFICATION);

		notificationQueueEntries =
			notificationQueueEntryLocalService.getNotificationQueueEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			expectedUserNotificationEventsCount,
			_userNotificationEventLocalService.getUserNotificationEventsCount(
				user.getUserId()));

		_userNotificationEventLocalService.deleteUserNotificationEvents(
			user.getUserId());

		Assert.assertEquals(
			notificationQueueEntries.toString(), 1,
			notificationQueueEntries.size());

		_notificationQueueEntry = notificationQueueEntries.get(0);

		Assert.assertEquals(
			NotificationQueueEntryConstants.STATUS_SENT,
			_notificationQueueEntry.getStatus());
		Assert.assertEquals(
			"Subject textObjectFieldNameValue",
			_notificationQueueEntry.getSubject());

		NotificationRecipient notificationRecipient =
			_notificationQueueEntry.getNotificationRecipient();

		for (NotificationRecipientSetting notificationRecipientSetting :
				notificationRecipient.getNotificationRecipientSettings()) {

			Assert.assertEquals(
				"userFullName", notificationRecipientSetting.getName());
			Assert.assertEquals(
				"Test Test", notificationRecipientSetting.getValue());
		}
	}

	@DeleteAfterTestRun
	private static ObjectDefinition _objectDefinition;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private NotificationQueueEntry _notificationQueueEntry;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}