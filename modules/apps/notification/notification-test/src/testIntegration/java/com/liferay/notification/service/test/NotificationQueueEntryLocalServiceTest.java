/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.exception.NotificationQueueEntryStatusException;
import com.liferay.notification.exception.NotificationQueueEntrySubjectException;
import com.liferay.notification.exception.NotificationRecipientSettingValueException;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.test.util.NotificationTemplateUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() {
		_notificationRecipientSettings = Arrays.asList(
			NotificationTemplateUtil.createNotificationRecipientSetting(
				"bcc", "bcc@liferay.com"),
			NotificationTemplateUtil.createNotificationRecipientSetting(
				"cc", "cc@liferay.com"),
			NotificationTemplateUtil.createNotificationRecipientSetting(
				"from", "from@liferay.com"),
			NotificationTemplateUtil.createNotificationRecipientSetting(
				"fromName", "From Name"),
			NotificationTemplateUtil.createNotificationRecipientSetting(
				"to", "to@liferay.com"));
	}

	@Test
	public void testAddNotificationQueueEntry() throws Exception {
		Assert.assertEquals(
			0,
			_notificationQueueEntryLocalService.
				getNotificationQueueEntriesCount());

		AssertUtils.assertFailure(
			NotificationRecipientSettingValueException.FromMustNotBeNull.class,
			"From is null",
			() -> _addNotificationQueueEntry(
				Arrays.asList(
					NotificationTemplateUtil.createNotificationRecipientSetting(
						"fromName", "From Name"),
					NotificationTemplateUtil.createNotificationRecipientSetting(
						"to", "to@liferay.com"))));
		AssertUtils.assertFailure(
			NotificationRecipientSettingValueException.FromNameMustNotBeNull.
				class,
			"From name is null",
			() -> _addNotificationQueueEntry(
				Arrays.asList(
					NotificationTemplateUtil.createNotificationRecipientSetting(
						"from", "from@liferay.com"),
					NotificationTemplateUtil.createNotificationRecipientSetting(
						"to", "to@liferay.com"))));

		User user = TestPropsValues.getUser();

		AssertUtils.assertFailure(
			NotificationQueueEntrySubjectException.class, "Subject is null",
			() -> _notificationQueueEntryLocalService.addNotificationQueueEntry(
				NotificationTemplateUtil.createNotificationContext(
					user, null, null, null, NotificationConstants.TYPE_EMAIL)));

		AssertUtils.assertFailure(
			NotificationRecipientSettingValueException.ToMustNotBeNull.class,
			"To is null",
			() -> _addNotificationQueueEntry(
				Arrays.asList(
					NotificationTemplateUtil.createNotificationRecipientSetting(
						"from", "from@liferay.com"),
					NotificationTemplateUtil.createNotificationRecipientSetting(
						"fromName", "From Name"))));

		String body = StringUtil.randomString();
		String subject = StringUtil.randomString();

		NotificationQueueEntry notificationQueueEntry =
			_notificationQueueEntryLocalService.addNotificationQueueEntry(
				NotificationTemplateUtil.createNotificationContext(
					user, body, null, _notificationRecipientSettings, subject,
					NotificationConstants.TYPE_EMAIL));

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

		NotificationRecipient notificationRecipient =
			notificationQueueEntry.getNotificationRecipient();

		Assert.assertEquals(
			notificationRecipient.getNotificationRecipientSettings(),
			_notificationRecipientSettings);

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
			_addNotificationQueueEntry(_notificationRecipientSettings);

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
			_addNotificationQueueEntry(_notificationRecipientSettings);

		long notificationQueueEntryId =
			notificationQueueEntry.getNotificationQueueEntryId();

		_notificationQueueEntryLocalService.updateStatus(
			notificationQueueEntryId,
			NotificationQueueEntryConstants.STATUS_SENT);

		AssertUtils.assertFailure(
			NotificationQueueEntryStatusException.class,
			"Notification queue entry " + notificationQueueEntryId +
				" was already sent",
			() ->
				_notificationQueueEntryLocalService.
					resendNotificationQueueEntry(notificationQueueEntryId));

		_notificationQueueEntryLocalService.updateStatus(
			notificationQueueEntryId,
			NotificationQueueEntryConstants.STATUS_FAILED);

		_notificationQueueEntryLocalService.resendNotificationQueueEntry(
			notificationQueueEntryId);

		_notificationQueueEntryLocalService.updateStatus(
			notificationQueueEntryId,
			NotificationQueueEntryConstants.STATUS_UNSENT);

		_notificationQueueEntryLocalService.resendNotificationQueueEntry(
			notificationQueueEntryId);
	}

	private NotificationQueueEntry _addNotificationQueueEntry(
			List<NotificationRecipientSetting> notificationRecipientSettings)
		throws Exception {

		return _notificationQueueEntryLocalService.addNotificationQueueEntry(
			NotificationTemplateUtil.createNotificationContext(
				notificationRecipientSettings,
				NotificationConstants.TYPE_EMAIL));
	}

	private static List<NotificationRecipientSetting>
		_notificationRecipientSettings = new ArrayList<>();

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

}