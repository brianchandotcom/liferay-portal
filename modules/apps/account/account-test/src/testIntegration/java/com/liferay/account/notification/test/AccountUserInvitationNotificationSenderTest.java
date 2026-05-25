/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.notification.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.notification.AccountUserInvitationNotificationSender;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.test.util.NotificationTemplateUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class AccountUserInvitationNotificationSenderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry();
	}

	@Test
	public void testSendUserInvitationNotification() throws Exception {
		try {
			_accountUserInvitationNotificationSender.sendNotification(
				_accountEntry.getAccountEntryId(),
				RandomTestUtil.randomString(),
				"A" + RandomTestUtil.randomString() + "@liferay.com",
				RandomTestUtil.randomString(), TestPropsValues.getUser());

			Assert.fail();
		}
		catch (NoSuchNotificationTemplateException
					noSuchNotificationTemplateException) {

			Assert.assertNotNull(noSuchNotificationTemplateException);
		}

		NotificationContext notificationContext =
			NotificationTemplateUtil.createNotificationContext(
				NotificationConstants.TYPE_EMAIL);

		NotificationTemplate notificationTemplate =
			notificationContext.getNotificationTemplate();

		notificationTemplate.setExternalReferenceCode(
			RandomTestUtil.randomString());

		_notificationTemplateLocalService.addNotificationTemplate(
			notificationContext);

		int count =
			_notificationQueueEntryLocalService.
				getNotificationQueueEntriesCount();

		_accountUserInvitationNotificationSender.sendNotification(
			_accountEntry.getAccountEntryId(), RandomTestUtil.randomString(),
			"A" + RandomTestUtil.randomString() + "@liferay.com",
			notificationTemplate.getExternalReferenceCode(),
			TestPropsValues.getUser());

		Assert.assertEquals(
			count + 1,
			_notificationQueueEntryLocalService.
				getNotificationQueueEntriesCount());
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountUserInvitationNotificationSender
		_accountUserInvitationNotificationSender;

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Inject
	private NotificationTemplateLocalService _notificationTemplateLocalService;

}