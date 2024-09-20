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
	public void testSendNotificationBecauseIsNoNotificationDayButNotSentNotificationBefore() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis - (Time.DAY * 200)),
				new Date(currentTimeMillis - (Time.DAY * 190)));

		Assert.assertFalse(notification);
	}

	@Test
	public void testSendNotificationBecauseIsNotificationDay() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis + (Time.DAY * 30)),
				new Date(0));

		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationBecauseIsNotNeeded() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis + (Time.DAY * 50)),
				new Date(0));

		Assert.assertFalse(notification);
	}

	@Test
	public void testSendNotificationBecauseIsNotNotificationDayAndSentNotificationBefore() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis + (Time.DAY * 28)),
				new Date(currentTimeMillis - (Time.DAY * 1)));

		Assert.assertFalse(notification);
	}

	@Test
	public void testSendNotificationBecauseIsNotNotificationDayButNotSentNotificationBefore() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis + (Time.DAY * 28)),
				new Date(0));

		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationTokenExpiredAndNotNotifyYet() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis - Time.DAY),
				new Date(0));

		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationTokenExpiredAndNotSentExpirationNotificationYet() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis - (Time.DAY + 2)),
				new Date(currentTimeMillis - (Time.DAY + 2)));

		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationTokenExpiredAndNotSentNotificationAndExpirationYet() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis - (Time.DAY * 2)),
				new Date(currentTimeMillis - (Time.DAY * 2)));

		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationTokenExpiredAndNotSentNotificationAndTwoExpirationYet() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis - (Time.DAY * 3)),
				new Date(currentTimeMillis - (Time.DAY * 4)));

		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationTokenExpiredAndNotSentNotificationYet() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis - (Time.DAY * 3)),
				new Date(currentTimeMillis - (Time.DAY * 3)));

		Assert.assertTrue(notification);
	}

	@Test
	public void testSendNotificationTokenNotExpiredAndNotificationDay() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis),
				new Date(currentTimeMillis - (Time.DAY + 1)));

		Assert.assertFalse(notification);
	}

	@Test
	public void testSendNotificationTokenNotExpiredAndNotSentNotificationYet() {
		ScimNotificationSchedulerJobConfiguration
			scimNotificationSchedulerJobConfiguration =
				new ScimNotificationSchedulerJobConfiguration();

		long currentTimeMillis = System.currentTimeMillis();

		boolean notification =
			scimNotificationSchedulerJobConfiguration.hasToSendNotification(
				new Date(currentTimeMillis),
				new Date(currentTimeMillis - (Time.DAY * 2)));

		Assert.assertTrue(notification);
	}

}