/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.upgrade.v3_10_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryAttachmentLocalService;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateAttachmentLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Paulo Albuquerque
 */
@RunWith(Arquillian.class)
public class
	DeleteStaleNotificationQueueEntriesAndNotificationTemplatesUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		_insertNotificationQueueEntry();
		_insertNotificationQueueEntryAttachment();
		_insertNotificationRecipient(
			PortalUtil.getClassNameId(NotificationQueueEntry.class),
			_NOTIFICATION_QUEUE_ENTRY_ID);
		_insertNotificationRecipient(
			PortalUtil.getClassNameId(NotificationTemplate.class),
			_NOTIFICATION_TEMPLATE_ID);
		_insertNotificationRecipientSetting(
			_NOTIFICATION_RECIPIENT_SETTING_ID_1);
		_insertNotificationRecipientSetting(
			_NOTIFICATION_RECIPIENT_SETTING_ID_2);
		_insertNotificationTemplate();
		_insertNotificationTemplateAttachment();

		Assert.assertNotNull(
			_notificationQueueEntryLocalService.fetchNotificationQueueEntry(
				_NOTIFICATION_QUEUE_ENTRY_ID));
		Assert.assertNotNull(
			_notificationQueueEntryAttachmentLocalService.
				fetchNotificationQueueEntryAttachment(
					_NOTIFICATION_QUEUE_ENTRY_ATTACHMENT_ID));
		Assert.assertNotNull(
			_notificationRecipientLocalService.fetchNotificationRecipient(
				_notificationRecipientId));
		Assert.assertNotNull(
			_notificationRecipientSettingLocalService.
				fetchNotificationRecipientSetting(
					_NOTIFICATION_RECIPIENT_SETTING_ID_1));
		Assert.assertNotNull(
			_notificationRecipientSettingLocalService.
				fetchNotificationRecipientSetting(
					_NOTIFICATION_RECIPIENT_SETTING_ID_2));
		Assert.assertNotNull(
			_notificationTemplateLocalService.fetchNotificationTemplate(
				_NOTIFICATION_TEMPLATE_ID));
		Assert.assertNotNull(
			_notificationTemplateAttachmentLocalService.
				fetchNotificationTemplateAttachment(
					_NOTIFICATION_TEMPLATE_ATTACHMENT_ID));

		_runUpgradeProcess();

		Assert.assertNull(
			_notificationQueueEntryLocalService.fetchNotificationQueueEntry(
				_NOTIFICATION_QUEUE_ENTRY_ID));
		Assert.assertNull(
			_notificationQueueEntryAttachmentLocalService.
				fetchNotificationQueueEntryAttachment(
					_NOTIFICATION_QUEUE_ENTRY_ATTACHMENT_ID));
		Assert.assertNull(
			_notificationRecipientLocalService.fetchNotificationRecipient(
				_notificationRecipientId));
		Assert.assertNull(
			_notificationRecipientSettingLocalService.
				fetchNotificationRecipientSetting(
					_NOTIFICATION_RECIPIENT_SETTING_ID_1));
		Assert.assertNull(
			_notificationRecipientSettingLocalService.
				fetchNotificationRecipientSetting(
					_NOTIFICATION_RECIPIENT_SETTING_ID_2));
		Assert.assertNull(
			_notificationTemplateLocalService.fetchNotificationTemplate(
				_NOTIFICATION_TEMPLATE_ID));
		Assert.assertNull(
			_notificationTemplateAttachmentLocalService.
				fetchNotificationTemplateAttachment(
					_NOTIFICATION_TEMPLATE_ATTACHMENT_ID));
	}

	private void _insertNotificationQueueEntry() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into NotificationQueueEntry (mvccVersion, ",
					"notificationQueueEntryId, companyId, userId, userName, ",
					"createDate, modifiedDate, notificationTemplateId, body, ",
					"classNameId, classPK, priority, sentDate, subject, ",
					"type_, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
					"?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, RandomTestUtil.nextLong());
			preparedStatement.setLong(2, _NOTIFICATION_QUEUE_ENTRY_ID);
			preparedStatement.setLong(3, _COMPANY_ID);
			preparedStatement.setLong(4, RandomTestUtil.nextLong());
			preparedStatement.setString(5, RandomTestUtil.randomString());
			preparedStatement.setTimestamp(6, _timestamp);
			preparedStatement.setTimestamp(7, _timestamp);
			preparedStatement.setLong(8, RandomTestUtil.nextLong());
			preparedStatement.setString(9, RandomTestUtil.randomString());
			preparedStatement.setLong(10, RandomTestUtil.nextLong());
			preparedStatement.setLong(11, RandomTestUtil.nextLong());
			preparedStatement.setLong(12, RandomTestUtil.nextLong());
			preparedStatement.setTimestamp(13, _timestamp);
			preparedStatement.setString(14, RandomTestUtil.randomString());
			preparedStatement.setString(15, RandomTestUtil.randomString());
			preparedStatement.setLong(16, RandomTestUtil.nextLong());

			preparedStatement.executeUpdate();
		}
	}

	private void _insertNotificationQueueEntryAttachment() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into NQueueEntryAttachment (mvccVersion, ",
					"NQueueEntryAttachmentId, companyId, fileEntryId, ",
					"notificationQueueEntryId) values (?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, RandomTestUtil.nextLong());
			preparedStatement.setLong(
				2, _NOTIFICATION_QUEUE_ENTRY_ATTACHMENT_ID);
			preparedStatement.setLong(3, _COMPANY_ID);
			preparedStatement.setLong(4, RandomTestUtil.nextLong());
			preparedStatement.setLong(5, _NOTIFICATION_QUEUE_ENTRY_ID);

			preparedStatement.executeUpdate();
		}
	}

	private void _insertNotificationRecipient(long classNameId, long classPK)
		throws Exception {

		_notificationRecipientId = RandomTestUtil.nextLong();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into NotificationRecipient (mvccVersion, uuid_, ",
					"notificationRecipientId, companyId, userId, userName, ",
					"createDate, modifiedDate, classNameId, classPK) values ",
					"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, RandomTestUtil.nextLong());
			preparedStatement.setString(2, RandomTestUtil.randomString());
			preparedStatement.setLong(3, _notificationRecipientId);
			preparedStatement.setLong(4, RandomTestUtil.nextLong());
			preparedStatement.setLong(5, RandomTestUtil.nextLong());
			preparedStatement.setString(6, RandomTestUtil.randomString());
			preparedStatement.setTimestamp(7, _timestamp);
			preparedStatement.setTimestamp(8, _timestamp);
			preparedStatement.setLong(9, classNameId);
			preparedStatement.setLong(10, classPK);

			preparedStatement.executeUpdate();
		}
	}

	private void _insertNotificationRecipientSetting(
			long notificationRecipientSettingId)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into NotificationRecipientSetting (mvccVersion, ",
					"uuid_, notificationRecipientSettingId, companyId, ",
					"userId, userName, createDate, modifiedDate, ",
					"notificationRecipientId, name, value) values (?, ?, ?, ",
					"?, ?, ?, ?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, RandomTestUtil.nextLong());
			preparedStatement.setString(2, RandomTestUtil.randomString());
			preparedStatement.setLong(3, notificationRecipientSettingId);
			preparedStatement.setLong(4, RandomTestUtil.nextLong());
			preparedStatement.setLong(5, RandomTestUtil.nextLong());
			preparedStatement.setString(6, RandomTestUtil.randomString());
			preparedStatement.setTimestamp(7, _timestamp);
			preparedStatement.setTimestamp(8, _timestamp);
			preparedStatement.setLong(9, _notificationRecipientId);
			preparedStatement.setString(10, RandomTestUtil.randomString());
			preparedStatement.setString(11, RandomTestUtil.randomString());

			preparedStatement.executeUpdate();
		}
	}

	private void _insertNotificationTemplate() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into NotificationTemplate (mvccVersion, uuid_, ",
					"externalReferenceCode, notificationTemplateId, ",
					"companyId, userId, userName, createDate, modifiedDate, ",
					"objectDefinitionId, body, description, editorType, name, ",
					"recipientType, subject, system_, type_) values (?, ?, ?, ",
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, RandomTestUtil.nextLong());
			preparedStatement.setString(2, RandomTestUtil.randomString());
			preparedStatement.setString(3, RandomTestUtil.randomString());
			preparedStatement.setLong(4, _NOTIFICATION_TEMPLATE_ID);
			preparedStatement.setLong(5, RandomTestUtil.nextLong());
			preparedStatement.setLong(6, RandomTestUtil.nextLong());
			preparedStatement.setString(7, RandomTestUtil.randomString());
			preparedStatement.setTimestamp(8, _timestamp);
			preparedStatement.setTimestamp(9, _timestamp);
			preparedStatement.setLong(10, RandomTestUtil.nextLong());
			preparedStatement.setString(11, RandomTestUtil.randomString());
			preparedStatement.setString(12, RandomTestUtil.randomString());
			preparedStatement.setString(13, RandomTestUtil.randomString());
			preparedStatement.setString(14, RandomTestUtil.randomString());
			preparedStatement.setString(15, RandomTestUtil.randomString());
			preparedStatement.setString(16, RandomTestUtil.randomString());
			preparedStatement.setLong(17, 0);
			preparedStatement.setString(18, RandomTestUtil.randomString());

			preparedStatement.executeUpdate();
		}
	}

	private void _insertNotificationTemplateAttachment() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into NTemplateAttachment (mvccVersion, ",
					"NTemplateAttachmentId, companyId, ",
					"notificationTemplateId, objectFieldId) values (?, ?, ?, ",
					"?, ?)"))) {

			preparedStatement.setLong(1, RandomTestUtil.nextLong());
			preparedStatement.setLong(2, _NOTIFICATION_TEMPLATE_ATTACHMENT_ID);
			preparedStatement.setLong(3, _COMPANY_ID);
			preparedStatement.setLong(4, _NOTIFICATION_TEMPLATE_ID);
			preparedStatement.setLong(5, RandomTestUtil.nextLong());

			preparedStatement.executeUpdate();
		}
	}

	private void _runUpgradeProcess() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.notification.internal.upgrade.v3_10_4." +
			"DeleteStaleNotificationQueueEntriesAndNotification" +
				"TemplatesUpgradeProcess";

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _NOTIFICATION_QUEUE_ENTRY_ATTACHMENT_ID =
		RandomTestUtil.randomLong();

	private static final long _NOTIFICATION_QUEUE_ENTRY_ID =
		RandomTestUtil.randomLong();

	private static final long _NOTIFICATION_RECIPIENT_SETTING_ID_1 =
		RandomTestUtil.randomLong();

	private static final long _NOTIFICATION_RECIPIENT_SETTING_ID_2 =
		RandomTestUtil.randomLong();

	private static final long _NOTIFICATION_TEMPLATE_ATTACHMENT_ID =
		RandomTestUtil.randomLong();

	private static final long _NOTIFICATION_TEMPLATE_ID =
		RandomTestUtil.randomLong();

	@Inject(
		filter = "component.name=com.liferay.notification.internal.upgrade.registry.NotificationUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private NotificationQueueEntryAttachmentLocalService
		_notificationQueueEntryAttachmentLocalService;

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	private long _notificationRecipientId;

	@Inject
	private NotificationRecipientLocalService
		_notificationRecipientLocalService;

	@Inject
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	@Inject
	private NotificationTemplateAttachmentLocalService
		_notificationTemplateAttachmentLocalService;

	@Inject
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	private final Timestamp _timestamp = new Timestamp(
		System.currentTimeMillis());

}