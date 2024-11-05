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
import org.junit.Before;
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

	@Before
	public void setUp() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		_scimNotificationSchedulerJobConfiguration =
			new ScimNotificationSchedulerJobConfiguration();
	}

	@Test
	public void testIsSendNotification() {
		Date accessTokenExpirationDate = new Date(
			System.currentTimeMillis() + Time.YEAR);

		_testIsSendNotification(
			accessTokenExpirationDate,
			accessTokenExpirationDate.getTime() - (Time.DAY * 30));
		_testIsSendNotification(
			accessTokenExpirationDate,
			accessTokenExpirationDate.getTime() - (Time.DAY * 10));
		_testIsSendNotification(
			accessTokenExpirationDate,
			accessTokenExpirationDate.getTime() - Time.DAY);
		_testIsSendNotification(
			accessTokenExpirationDate, accessTokenExpirationDate.getTime());
	}

	private void _testIsSendNotification(
		Date accessTokenExpirationDate, long currentTimeMillis) {

		Assert.assertTrue(
			_scimNotificationSchedulerJobConfiguration.isSendNotification(
				accessTokenExpirationDate, currentTimeMillis, new Date(0)));
		Assert.assertTrue(
			_scimNotificationSchedulerJobConfiguration.isSendNotification(
				accessTokenExpirationDate, currentTimeMillis,
				new Date(currentTimeMillis - 1)));
		Assert.assertFalse(
			_scimNotificationSchedulerJobConfiguration.isSendNotification(
				accessTokenExpirationDate, currentTimeMillis,
				new Date(currentTimeMillis)));
		Assert.assertFalse(
			_scimNotificationSchedulerJobConfiguration.isSendNotification(
				accessTokenExpirationDate, currentTimeMillis,
				new Date(currentTimeMillis + 1)));
	}

	private ScimNotificationSchedulerJobConfiguration
		_scimNotificationSchedulerJobConfiguration;

}