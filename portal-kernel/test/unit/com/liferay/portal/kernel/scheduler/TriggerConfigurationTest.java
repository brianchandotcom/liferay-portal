/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Mariano Álvaro Sáiz
 */
public class TriggerConfigurationTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testCronExpressionTriggerConfiguration() {
		TriggerConfiguration triggerConfiguration =
			TriggerConfiguration.createTriggerConfiguration("0 0 7 1/2 * ? *");

		Assert.assertEquals(
			"0 0 7 1/2 * ? *", triggerConfiguration.getCronExpression());
		Assert.assertEquals(0, triggerConfiguration.getInterval());
		Assert.assertNull(triggerConfiguration.getTimeUnit());
	}

	@Test
	public void testIllegalArgumentExceptionOnEmptyCronExpression() {
		try {
			TriggerConfiguration.createTriggerConfiguration(StringPool.BLANK);

			Assert.fail("Should have failed due to empty cron expression");
		}
		catch (Exception exception) {
			Assert.assertEquals(
				IllegalArgumentException.class, exception.getClass());
			Assert.assertEquals(
				"Cron expression is null or empty", exception.getMessage());
		}
	}

	@Test
	public void testIllegalArgumentExceptionOnNegativeInterval() {
		try {
			TriggerConfiguration.createTriggerConfiguration(
				-1, TimeUnit.MINUTE);

			Assert.fail("Should have failed due to negative interval");
		}
		catch (Exception exception) {
			Assert.assertEquals(
				IllegalArgumentException.class, exception.getClass());
			Assert.assertEquals(
				"Interval is either equal or less than 0",
				exception.getMessage());
		}
	}

	@Test
	public void testIllegalArgumentExceptionOnNullCronExpression() {
		try {
			TriggerConfiguration.createTriggerConfiguration(null);

			Assert.fail("Should have failed due to null cron expression");
		}
		catch (Exception exception) {
			Assert.assertEquals(
				IllegalArgumentException.class, exception.getClass());
			Assert.assertEquals(
				"Cron expression is null or empty", exception.getMessage());
		}
	}

	@Test
	public void testIllegalArgumentExceptionOnNullTimeUnit() {
		try {
			TriggerConfiguration.createTriggerConfiguration(16, null);

			Assert.fail("Should have failed due to null time unit");
		}
		catch (Exception exception) {
			Assert.assertEquals(
				IllegalArgumentException.class, exception.getClass());
			Assert.assertEquals("Time unit is null", exception.getMessage());
		}
	}

	@Test
	public void testIllegalArgumentExceptionOnZeroInterval() {
		try {
			TriggerConfiguration.createTriggerConfiguration(0, TimeUnit.MINUTE);

			Assert.fail("Should have failed due to zero interval");
		}
		catch (Exception exception) {
			Assert.assertEquals(
				IllegalArgumentException.class, exception.getClass());
			Assert.assertEquals(
				"Interval is either equal or less than 0",
				exception.getMessage());
		}
	}

	@Test
	public void testIntervalTriggerConfiguration() {
		TriggerConfiguration triggerConfiguration =
			TriggerConfiguration.createTriggerConfiguration(
				16, TimeUnit.MINUTE);

		Assert.assertNull(triggerConfiguration.getCronExpression());
		Assert.assertEquals(16, triggerConfiguration.getInterval());
		Assert.assertEquals(
			TimeUnit.MINUTE, triggerConfiguration.getTimeUnit());
	}

	@Test
	public void testStartDateTriggerConfiguration() {
		TriggerConfiguration triggerConfiguration =
			TriggerConfiguration.createTriggerConfiguration("0 0 7 1/2 * ? *");

		Assert.assertNull(triggerConfiguration.getStartDate());

		Date now = new Date();

		triggerConfiguration.setStartDate(now);

		Assert.assertEquals(now, triggerConfiguration.getStartDate());

		triggerConfiguration.setStartDate(null);

		Assert.assertNull(triggerConfiguration.getStartDate());
	}

}