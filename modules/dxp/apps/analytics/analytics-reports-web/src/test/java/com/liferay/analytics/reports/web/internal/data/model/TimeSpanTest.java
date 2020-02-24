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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Arques
 */
public class TimeSpanTest {

	@Test
	public void testDefaultTimeSpanKey() {
		Assert.assertEquals(
			TimeSpan.LAST_7_DAYS.getKey(), TimeSpan.defaultTimeSpanKey());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfWithEmptyKey() {
		TimeSpan.of(StringPool.BLANK);
	}

	@Test
	public void testOfWithExistingKey() {
		Assert.assertEquals(
			TimeSpan.LAST_7_DAYS, TimeSpan.of(TimeSpan.LAST_7_DAYS.getKey()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfWithNonexistentKey() {
		TimeSpan.of(RandomTestUtil.randomString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOfWithNullKey() {
		TimeSpan.of(null);
	}

	@Test
	public void testToTimeRange() {
		Assert.assertNotNull(TimeSpan.LAST_7_DAYS.toTimeRange(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToTimeRangeWithNegativeTimeSpanOffset() {
		TimeSpan.LAST_7_DAYS.toTimeRange(-1);
	}

}