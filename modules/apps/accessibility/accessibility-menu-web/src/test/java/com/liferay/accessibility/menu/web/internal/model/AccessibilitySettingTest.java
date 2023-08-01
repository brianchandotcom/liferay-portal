/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.accessibility.menu.web.internal.model;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Evan Thibodeau
 */
public class AccessibilitySettingTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_accessibilitySetting = new AccessibilitySetting(
			"c-test-classname", true, "TEST_DESCRIPTION", "TEST_KEY",
			"Test Label", null);
	}

	@Test
	public void testIsEnabled() {
		Assert.assertEquals(_accessibilitySetting.isEnabled(), true);

		_accessibilitySetting.setDefaultValue(false);

		Assert.assertEquals(_accessibilitySetting.isEnabled(), false);

		_accessibilitySetting.setDefaultValue(true);
		_accessibilitySetting.setSessionClicksValue(false);

		Assert.assertEquals(_accessibilitySetting.isEnabled(), false);

		_accessibilitySetting.setDefaultValue(false);
		_accessibilitySetting.setSessionClicksValue(true);

		Assert.assertEquals(_accessibilitySetting.isEnabled(), true);
	}

	private AccessibilitySetting _accessibilitySetting;

}