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

package com.liferay.analytics.reports.web.internal.data.model;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.time.LocalDateTime;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Arques
 */
public class TimeRangeTest {

	@Test
	public void testGetIntervalsWithLast7DaysTimeSpan() {
		TimeRange timeRange = TimeRange.of(TimeSpan.LAST_7_DAYS, 0);

		List<LocalDateTime> intervals = timeRange.getIntervals();

		Assert.assertEquals(intervals.toString(), 7, intervals.size());
		Assert.assertEquals(
			timeRange.getStartLocalDateTime(), intervals.get(0));
	}

	@Test
	public void testGetIntervalsWithLast24HoursTimeSpan() {
		TimeRange timeRange = TimeRange.of(TimeSpan.LAST_24_HOURS, 0);

		List<LocalDateTime> intervals = timeRange.getIntervals();

		Assert.assertEquals(intervals.toString(), 24, intervals.size());
		Assert.assertEquals(
			timeRange.getStartLocalDateTime(), intervals.get(0));
	}

	@Test
	public void testGetIntervalsWithLast30DaysTimeSpan() {
		TimeRange timeRange = TimeRange.of(TimeSpan.LAST_30_DAYS, 0);

		List<LocalDateTime> intervals = timeRange.getIntervals();

		Assert.assertEquals(intervals.toString(), 30, intervals.size());
		Assert.assertEquals(
			timeRange.getStartLocalDateTime(), intervals.get(0));
	}

	@Test
	public void testOf() {
		TimeSpan timeSpan = TimeSpan.LAST_24_HOURS;
		int timeSpanOffset = RandomTestUtil.randomInt();

		TimeRange timeRange = TimeRange.of(timeSpan, timeSpanOffset);

		Assert.assertNotNull(timeRange);
		Assert.assertEquals(timeSpan, timeRange.getTimeSpan());
		Assert.assertEquals(timeSpanOffset, timeRange.getTimeSpanOffset());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfWithNegativeTimeSpanOffset() {
		TimeRange.of(TimeSpan.LAST_24_HOURS, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfWithNullKey() {
		TimeRange.of(null, RandomTestUtil.randomInt());
	}

}