/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Thiago Buarque
 */
public class ConfigurationPIDUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetRawPid() {
		String expectedRawPid =
			"com.liferay.scoped.configuration.SampleConfiguration";

		Assert.assertEquals(
			expectedRawPid,
			ConfigurationPIDUtil.getRawPid(
				"com.liferay.scoped.configuration.SampleConfiguration~123"));
		Assert.assertEquals(
			expectedRawPid,
			ConfigurationPIDUtil.getRawPid(
				"com.liferay.scoped.configuration.SampleConfiguration.scoped" +
					"~123"));
	}

}