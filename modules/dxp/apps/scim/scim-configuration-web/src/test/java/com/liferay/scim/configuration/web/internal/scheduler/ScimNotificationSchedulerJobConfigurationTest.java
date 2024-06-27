/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.scheduler;


import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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


	public static final int DAY = 1;

	public static final int MONTH = 30;

	public static final int TEN_DAYS = 10;

	public static final List<Integer> notificationTime = Arrays.asList(
		MONTH,TEN_DAYS, DAY);

	@Test
	public void testSendNotificationBecauseIsNotificationDay() throws Exception {


		ScimNotificationSchedulerJobConfiguration scimNotificationSchedulerJobConfiguration = new ScimNotificationSchedulerJobConfiguration();

		int daysToExpire = 30;

		int daysLastNotification = 20000;


		boolean notification = scimNotificationSchedulerJobConfiguration.hasToSendNotification(daysToExpire, daysLastNotification, notificationTime);


		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationBecauseIsNotNotificationDayButNotSentNotificationBefore() throws Exception {


		ScimNotificationSchedulerJobConfiguration scimNotificationSchedulerJobConfiguration = new ScimNotificationSchedulerJobConfiguration();

		int daysToExpire = 28;

		int daysLastNotification = 20000;


		boolean notification = scimNotificationSchedulerJobConfiguration.hasToSendNotification(daysToExpire, daysLastNotification, notificationTime);


		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationBecauseIsNotNotificationDayAndSentNotificationBefore() throws Exception {


		ScimNotificationSchedulerJobConfiguration scimNotificationSchedulerJobConfiguration = new ScimNotificationSchedulerJobConfiguration();

		int daysToExpire = 28;

		int daysLastNotification = 29;


		boolean notification = scimNotificationSchedulerJobConfiguration.hasToSendNotification(daysToExpire, daysLastNotification, notificationTime);


		Assert.assertFalse(notification);
	}

	@Test
	public void testSendNotificationBecauseIsNotNeeded() throws Exception {


		ScimNotificationSchedulerJobConfiguration scimNotificationSchedulerJobConfiguration = new ScimNotificationSchedulerJobConfiguration();

		int daysToExpire = 50;

		int daysLastNotification = 20000;


		boolean notification = scimNotificationSchedulerJobConfiguration.hasToSendNotification(daysToExpire, daysLastNotification, notificationTime);


		Assert.assertFalse(notification);
	}

	@Test
	public void testSendNotificationTokenExpiredAndNotNotifyYet() throws Exception {


		ScimNotificationSchedulerJobConfiguration scimNotificationSchedulerJobConfiguration = new ScimNotificationSchedulerJobConfiguration();

		int daysToExpire = -1;

		int daysLastNotification = 20000;


		boolean notification = scimNotificationSchedulerJobConfiguration.hasToSendNotification(daysToExpire, daysLastNotification, notificationTime);


		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationBecauseIsNoNotificationDayButNotSentNotificationBefore() throws Exception {


		ScimNotificationSchedulerJobConfiguration scimNotificationSchedulerJobConfiguration = new ScimNotificationSchedulerJobConfiguration();

		int daysToExpire = -2;

		int daysLastNotification = -1;


		boolean notification = scimNotificationSchedulerJobConfiguration.hasToSendNotification(daysToExpire, daysLastNotification, notificationTime);


		Assert.assertFalse(notification);
	}


}