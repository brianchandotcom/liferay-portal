/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.scheduler;

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

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
	public void testIsSendNotification() {
		Date accessTokenExpirationDate = new Date(
			System.currentTimeMillis() + Time.YEAR);

		_testIsSendNotification(
			accessTokenExpirationDate.getTime() - (Time.DAY * 30),
			accessTokenExpirationDate);

		_testIsSendNotification(
			accessTokenExpirationDate.getTime() - (Time.DAY * 10),
			accessTokenExpirationDate);

		_testIsSendNotification(
			accessTokenExpirationDate.getTime() - Time.DAY,
			accessTokenExpirationDate);

		_testIsSendNotification(
			accessTokenExpirationDate.getTime(), accessTokenExpirationDate);
	}

	private ScimNotificationSchedulerJobConfiguration
		_prepareScimNotificationSchedulerJobConfiguration() {

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		return new ScimNotificationSchedulerJobConfiguration();
	}

	private void _testIsSendNotification(
		long notificationDurationMillis, Date accessTokenExpirationDate) {

		Assert.assertTrue(
			_scimNotificationSchedulerJobConfiguration.isSendNotification(
				accessTokenExpirationDate, notificationDurationMillis,
				new Date(0)));

		Assert.assertTrue(
			_scimNotificationSchedulerJobConfiguration.isSendNotification(
				accessTokenExpirationDate, notificationDurationMillis,
				new Date(notificationDurationMillis - 1)));

		Assert.assertFalse(
			_scimNotificationSchedulerJobConfiguration.isSendNotification(
				accessTokenExpirationDate, notificationDurationMillis,
				new Date(notificationDurationMillis)));

		Assert.assertFalse(
			_scimNotificationSchedulerJobConfiguration.isSendNotification(
				accessTokenExpirationDate, notificationDurationMillis,
				new Date(notificationDurationMillis + 1)));
	}

	private final ScimNotificationSchedulerJobConfiguration
		_scimNotificationSchedulerJobConfiguration =
			_prepareScimNotificationSchedulerJobConfiguration();

}