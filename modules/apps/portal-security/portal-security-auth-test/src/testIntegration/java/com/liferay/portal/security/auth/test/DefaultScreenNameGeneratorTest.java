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

package com.liferay.portal.security.auth.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.DefaultScreenNameGenerator;
import com.liferay.portal.kernel.security.auth.ScreenNameGenerator;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 * @author Daniel Reuther
 */
@RunWith(Arquillian.class)
public class DefaultScreenNameGeneratorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGenerate() throws Exception {
		Assert.assertEquals(
			"user123",
			_screenNameGenerator.generate(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				"user123@liferay.com"));
	}

	@Test
	public void testGenerateDuplicateScreenName() throws Exception {
		User user = TestPropsValues.getUser();

		String screenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			user.getScreenName() + "@liferay.com");

		Assert.assertNotSame(user.getScreenName(), screenName);
		Assert.assertEquals(user.getScreenName() + ".1", screenName);
	}

	@Test
	public void testGenerateNoEmailAddress() throws Exception {
		String screenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), null);

		if (PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC) {
			Assert.assertEquals(
				String.valueOf(TestPropsValues.getUserId()), screenName);
		}
		else {
			Assert.assertEquals(
				"user." + TestPropsValues.getUserId(), screenName);
		}
	}

	@Test
	public void testGenerateNumericEmailAddress() throws Exception {
		String screenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			"123@liferay.com");

		if (PropsValues.USERS_SCREEN_NAME_ALLOW_NUMERIC) {
			Assert.assertNotSame("user.123", screenName);
			Assert.assertEquals("123", screenName);
		}
		else {
			Assert.assertNotSame("123", screenName);
			Assert.assertEquals("user.123", screenName);
		}
	}

	@Test
	public void testGeneratePostfixEmailAddress() throws Exception {
		Assert.assertEquals(
			"postfix." + TestPropsValues.getUserId(),
			_screenNameGenerator.generate(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				"postfix@liferay.com"));
	}

	private final ScreenNameGenerator _screenNameGenerator =
		new DefaultScreenNameGenerator();

}