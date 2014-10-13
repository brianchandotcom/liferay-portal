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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.User;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.TestPropsValues;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 * @author Daniel Reuther
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DefaultScreenNameGeneratorTest {

	@Test
	public void testGenerate() throws Exception {
		String generatedScreenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			"user123@liferay.com");

		Assert.assertEquals("user123", generatedScreenName);
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
		String screenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			"postfix@liferay.com");

		Assert.assertEquals(
			"postfix." + TestPropsValues.getUserId(), screenName);
	}

	private final ScreenNameGenerator _screenNameGenerator =
		new DefaultScreenNameGenerator();

}