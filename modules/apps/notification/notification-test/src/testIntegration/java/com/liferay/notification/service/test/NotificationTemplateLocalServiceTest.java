/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.exception.NotificationTemplateDescriptionException;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.service.test.util.NotificationTemplateUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_externalReferenceCode = RandomTestUtil.randomString();
	}

	@Test
	public void testAddNotificationTemplate() throws Exception {
		User user = TestPropsValues.getUser();

		try {
			_notificationTemplateLocalService.addNotificationTemplate(
				NotificationTemplateUtil.createNotificationContext(
					user, StringUtil.randomString(256),
					NotificationConstants.TYPE_USER_NOTIFICATION));

			Assert.fail();
		}
		catch (NotificationTemplateDescriptionException
					notificationTemplateDescriptionException) {

			Assert.assertEquals(
				"The description cannot contain more than 255 characters",
				notificationTemplateDescriptionException.getMessage());
		}

		_notificationTemplateLocalService.addNotificationTemplate(
			NotificationTemplateUtil.createNotificationContext(
				user, StringUtil.randomString(255),
				NotificationConstants.TYPE_USER_NOTIFICATION));

		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.addNotificationTemplate(
				_externalReferenceCode, user.getUserId(),
				NotificationConstants.TYPE_EMAIL);

		Assert.assertEquals(
			_externalReferenceCode,
			notificationTemplate.getExternalReferenceCode());
		Assert.assertEquals(user.getUserId(), notificationTemplate.getUserId());
		Assert.assertEquals(
			user.getFullName(), notificationTemplate.getUserName());
		Assert.assertEquals(0, notificationTemplate.getObjectDefinitionId());
		Assert.assertEquals(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT,
			notificationTemplate.getEditorType());
		Assert.assertEquals(
			_externalReferenceCode, notificationTemplate.getName());
		Assert.assertEquals(
			NotificationConstants.TYPE_EMAIL, notificationTemplate.getType());

		NotificationRecipient notificationRecipient =
			notificationTemplate.getNotificationRecipient();

		Assert.assertNotNull(notificationRecipient);

		long notificationRecipientId =
			notificationRecipient.getNotificationRecipientId();

		_assertNotificationRecipientSetting("from", notificationRecipientId);
		_assertNotificationRecipientSetting(
			"fromName", notificationRecipientId);
		_assertNotificationRecipientSetting("to", notificationRecipientId);

		_notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate);
	}

	private void _assertNotificationRecipientSetting(
			String name, long notificationRecipientId)
		throws Exception {

		NotificationRecipientSetting notificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				getNotificationRecipientSetting(notificationRecipientId, name);

		Assert.assertEquals(name, notificationRecipientSetting.getName());
		Assert.assertEquals(
			_externalReferenceCode,
			notificationRecipientSetting.getValue(LocaleUtil.getDefault()));
	}

	private String _externalReferenceCode;

	@Inject
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	@Inject
	private NotificationTemplateLocalService _notificationTemplateLocalService;

}