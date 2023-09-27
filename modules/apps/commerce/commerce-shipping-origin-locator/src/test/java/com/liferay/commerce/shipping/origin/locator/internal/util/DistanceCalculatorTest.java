/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.origin.locator.internal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class DistanceCalculatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDistance() {
		Assert.assertEquals(
			69.382,
			_distanceCalculator.getDistance(
				_LATITUDE, _LONGITUDE, 34.045886, -118.564861),
			_DELTA);
		Assert.assertEquals(
			9070.629,
			_distanceCalculator.getDistance(
				_LATITUDE, _LONGITUDE, 48.856614, 2.352222),
			_DELTA);
	}

	private static final int _DELTA = 1;

	private static final double _LATITUDE = 33.997673;

	private static final double _LONGITUDE = -117.814508;

	private static final DistanceCalculator _distanceCalculator =
		new DistanceCalculator();

}