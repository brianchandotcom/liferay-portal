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
	public void testNullWorkflowDefinitionManager() throws Exception {


		ScimNotificationSchedulerJobConfiguration scimNotificationSchedulerJobConfiguration = new ScimNotificationSchedulerJobConfiguration();

		int daysToExpire = 30;

		Integer daysLastNotification = null;


		boolean notification = scimNotificationSchedulerJobConfiguration.hasToSendNotification(daysToExpire, daysLastNotification, notificationTime);


		Assert.assertTrue(notification);
	}


}