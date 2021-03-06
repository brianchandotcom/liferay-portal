/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class TimeRangeTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testOf() {
		TimeSpan timeSpan = TimeSpan.LAST_7_DAYS;
		int timeSpanOffset = RandomTestUtil.randomInt();

		TimeRange timeRange = TimeRange.of(timeSpan, timeSpanOffset);

		Assert.assertNotNull(timeRange);
		Assert.assertEquals(timeSpan, timeRange.getTimeSpan());
		Assert.assertEquals(timeSpanOffset, timeRange.getTimeSpanOffset());
	}

	@Test
	public void testOfWithLast7DaysTimeSpan() {
		TimeRange timeRange = TimeRange.of(TimeSpan.LAST_7_DAYS, 0);

		LocalDate localDate = LocalDate.now();

		Assert.assertEquals(
			localDate.minusDays(1), timeRange.getEndLocalDate());
		Assert.assertEquals(
			localDate.minusDays(7), timeRange.getStartLocalDate());
	}

	@Test
	public void testOfWithLast30DaysTimeSpan() {
		TimeRange timeRange = TimeRange.of(TimeSpan.LAST_30_DAYS, 0);

		LocalDate localDate = LocalDate.now();

		Assert.assertEquals(
			localDate.minusDays(1), timeRange.getEndLocalDate());
		Assert.assertEquals(
			localDate.minusDays(30), timeRange.getStartLocalDate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfWithNegativeTimeSpanOffset() {
		TimeRange.of(TimeSpan.LAST_7_DAYS, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfWithNullKey() {
		TimeRange.of(null, RandomTestUtil.randomInt());
	}

	@Test
	public void testOfWithTodayTimeSpan() {
		TimeRange timeRange = TimeRange.of(TimeSpan.TODAY, 0);

		Assert.assertEquals(LocalDate.now(), timeRange.getEndLocalDate());
		Assert.assertEquals(LocalDate.now(), timeRange.getStartLocalDate());
	}

	@Test
	public void testOfWithTodayTimeSpanWithTimeSpanOffset1() {
		TimeRange timeRange = TimeRange.of(TimeSpan.TODAY, 1);

		Assert.assertEquals(LocalDate.now(), timeRange.getEndLocalDate());
		Assert.assertEquals(LocalDate.now(), timeRange.getStartLocalDate());
	}

}