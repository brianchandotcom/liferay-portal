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
			"c-test-classname", true, "TEST_KEY", "Test Label", null);
	}

	@Test
	public void testIsEnabled() {
		Assert.assertEquals(_accessibilitySetting.isEnabled(), true);

		_accessibilitySetting.setDefaultValue(false);

		Assert.assertEquals(_accessibilitySetting.isEnabled(), false);

		_accessibilitySetting.setDefaultValue(true);
		_accessibilitySetting.setSessionClicksValue(false);

		Assert.assertEquals(_accessibilitySetting.isEnabled(), false);

		_accessibilitySetting.setSessionClicksValue(true);
		_accessibilitySetting.setDefaultValue(false);

		Assert.assertEquals(_accessibilitySetting.isEnabled(), true);
	}

	private AccessibilitySetting _accessibilitySetting;

}