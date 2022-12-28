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
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.UUID;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Murilo Stodolni
 */
@RunWith(Arquillian.class)
public class NotificationTemplateLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddNotificationTemplateByExternalReferenceCode()
		throws Exception {

		User user = TestPropsValues.getUser();

		String externalReferenceCode = String.valueOf(UUID.randomUUID());

		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.addNotificationTemplate(
				externalReferenceCode, user.getUserId(),
				NotificationConstants.TYPE_EMAIL);

		Assert.assertEquals(
			externalReferenceCode,
			notificationTemplate.getExternalReferenceCode());
		Assert.assertEquals(user.getUserId(), notificationTemplate.getUserId());
		Assert.assertEquals(
			user.getFullName(), notificationTemplate.getUserName());
		Assert.assertEquals(0, notificationTemplate.getObjectDefinitionId());
		Assert.assertEquals(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
			notificationTemplate.getEditorType());
		Assert.assertEquals(
			externalReferenceCode, notificationTemplate.getName());
		Assert.assertEquals(
			NotificationConstants.TYPE_EMAIL, notificationTemplate.getType());

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		Assert.assertNotNull(notificationRecipient);

		NotificationRecipientSetting notificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				getNotificationRecipientSetting(
					notificationRecipient.getNotificationRecipientId(), "from");

		Assert.assertEquals("from", notificationRecipientSetting.getName());
		Assert.assertEquals(
			externalReferenceCode,
			notificationRecipientSetting.getValue(LocaleUtil.getDefault()));

		notificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				getNotificationRecipientSetting(
					notificationRecipient.getNotificationRecipientId(),
					"fromName");

		Assert.assertEquals("fromName", notificationRecipientSetting.getName());
		Assert.assertEquals(
			externalReferenceCode,
			notificationRecipientSetting.getValue(LocaleUtil.getDefault()));

		notificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				getNotificationRecipientSetting(
					notificationRecipient.getNotificationRecipientId(), "to");

		Assert.assertEquals("to", notificationRecipientSetting.getName());
		Assert.assertEquals(
			externalReferenceCode,
			notificationRecipientSetting.getValue(LocaleUtil.getDefault()));

		_notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate);
	}

	@Inject
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	@Inject
	private NotificationTemplateLocalService _notificationTemplateLocalService;

}