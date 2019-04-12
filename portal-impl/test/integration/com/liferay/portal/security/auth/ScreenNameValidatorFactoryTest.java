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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.ScreenNameValidator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class ScreenNameValidatorFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule(
				"bundle.screennamevalidatorfactory"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		_screenNameValidator = ScreenNameValidatorFactory.getInstance();
	}

	@Test
	public void testGetAUIValidatorJS() {
		Assert.assertEquals(
			"function() {return true;}",
			_screenNameValidator.getAUIValidatorJS());
	}

	@Test
	public void testGetDescription() {
		Locale locale = LocaleUtil.getDefault();

		Assert.assertEquals(
			locale.toString(), _screenNameValidator.getDescription(locale));
	}

	@Test
	public void testValidate() {
		Assert.assertTrue(_screenNameValidator.validate(1, "lftest"));
		Assert.assertFalse(_screenNameValidator.validate(2, "lftest"));
		Assert.assertFalse(_screenNameValidator.validate(1, "bob"));
	}

	private static ScreenNameValidator _screenNameValidator;

}