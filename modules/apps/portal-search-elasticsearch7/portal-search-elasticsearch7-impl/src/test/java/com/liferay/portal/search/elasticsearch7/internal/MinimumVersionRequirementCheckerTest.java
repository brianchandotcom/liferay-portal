/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class MinimumVersionRequirementCheckerTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMeetsRequirement() {
		_testMeetsRequirement("7.17.1", "8.3.2", true);
		_testMeetsRequirement("7.17.0", "7.17.1", true);
		_testMeetsRequirement("7.15.3", "7.14.4", false);
		_testMeetsRequirement("7.15", "7.16.4", true);
		_testMeetsRequirement("7.15", "7.15.1", true);
		_testMeetsRequirement("7.15", "7.15.0", true);
		_testMeetsRequirement("7.15", "7.14.4", false);
		_testMeetsRequirement("7.5.3", "7.6.5", true);
		_testMeetsRequirement("7.5.3", "7.5.3", true);
		_testMeetsRequirement("7.5.3", "7.5.2", false);
		_testMeetsRequirement("7.5.3", "7.4.4", false);
		_testMeetsRequirement("7.5.3", "7.4.3", false);
		_testMeetsRequirement("7.5.3", "6.6.5", false);
	}

	private void _testMeetsRequirement(
		String minimumVersion, String version, boolean meetsRequirement) {

		MinimumVersionRequirementChecker minimumVersionRequirementChecker =
			new MinimumVersionRequirementChecker(minimumVersion);

		Assert.assertEquals(
			minimumVersion + " -> " + version, meetsRequirement,
			minimumVersionRequirementChecker.meetsRequirement(version));
	}

}