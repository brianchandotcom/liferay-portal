/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.scheduler;

import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alvaro Saugar
 */
public class ScimNotificationSchedulerJobConfigurationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testNotifications() {
		Date tokenExpiryDate = new Date(System.currentTimeMillis() + Time.YEAR);

		_testNotificationDuration(
			tokenExpiryDate, tokenExpiryDate.getTime() - (Time.DAY * 30));

		_testNotificationDuration(
			tokenExpiryDate, tokenExpiryDate.getTime() - (Time.DAY * 10));

		_testNotificationDuration(
			tokenExpiryDate, tokenExpiryDate.getTime() - Time.DAY);

		_testNotificationDuration(tokenExpiryDate, tokenExpiryDate.getTime());
	}

	private void _testNotificationDuration(
		Date tokenExpiryDate, long notificationDurationMillis) {

		Assert.assertTrue(
			_scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				tokenExpiryDate, _NO_NOTIFICATION_YET,
				notificationDurationMillis));

		Assert.assertTrue(
			_scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				tokenExpiryDate, new Date(notificationDurationMillis - 1),
				notificationDurationMillis));

		Assert.assertFalse(
			_scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				tokenExpiryDate, new Date(notificationDurationMillis),
				notificationDurationMillis));

		Assert.assertFalse(
			_scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				tokenExpiryDate, new Date(notificationDurationMillis + 1),
				notificationDurationMillis));
	}

	private static final Date _NO_NOTIFICATION_YET = new Date(0);

	private final ScimNotificationSchedulerJobConfiguration
		_scimNotificationSchedulerJobConfiguration =
			new ScimNotificationSchedulerJobConfiguration();

}