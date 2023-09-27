/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class TimeSpanTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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