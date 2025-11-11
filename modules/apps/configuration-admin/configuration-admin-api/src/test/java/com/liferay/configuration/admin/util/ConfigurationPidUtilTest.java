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
public class ConfigurationPidUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetRawPid() {
		_testGetRawPid(
			"com.acme.scoped.configuration.SampleConfiguration",
			"com.acme.scoped.configuration.SampleConfiguration",
			"com.acme.scoped.configuration.SampleConfiguration.scoped",
			"com.acme.scoped.configuration.SampleConfiguration.scoped~123",
			"com.acme.scoped.configuration.SampleConfiguration~123");
	}

	private void _testGetRawPid(String rawPid, String... pids) {
		for (String pid : pids) {
			Assert.assertEquals(rawPid, ConfigurationPidUtil.getRawPid(pid));
		}
	}

}