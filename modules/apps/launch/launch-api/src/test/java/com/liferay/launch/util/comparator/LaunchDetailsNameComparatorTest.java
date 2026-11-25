/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.util.comparator;

import com.liferay.launch.details.LaunchDetails;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Carlos Correa
 */
public class LaunchDetailsNameComparatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCompare() {
		LaunchDetails launchDetails1 = new LaunchDetails(
			RandomTestUtil.randomLong(), "a");
		LaunchDetails launchDetails2 = new LaunchDetails(
			RandomTestUtil.randomLong(), "B");

		Assert.assertEquals(
			-1,
			Integer.signum(
				LaunchDetailsNameComparator.INSTANCE.compare(
					launchDetails1, launchDetails2)));
		Assert.assertEquals(
			1,
			Integer.signum(
				LaunchDetailsNameComparator.INSTANCE.compare(
					launchDetails2, launchDetails1)));

		Assert.assertEquals(
			0,
			LaunchDetailsNameComparator.INSTANCE.compare(
				launchDetails1,
				new LaunchDetails(RandomTestUtil.randomLong(), "A")));
	}

}