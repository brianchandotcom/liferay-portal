/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.TestPropsValues;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 * @author Daniel Reuther
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DefaultScreenNameGeneratorTest {

	@BeforeClass
	public static void setUp() throws Exception {
		ClassLoader classLoader = PACLClassLoaderUtil.getPortalClassLoader();

		_screenNameGenerator = (ScreenNameGenerator)classLoader.loadClass(
			DefaultScreenNameGenerator.class.getName()).newInstance();

		_usersScreenNameAllowNumeric = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.USERS_SCREEN_NAME_ALLOW_NUMERIC));
	}

	@Test
	public void testGenerate() throws Exception {
		String generatedScreenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			"user123@liferay.com");

		Assert.assertEquals("user123", generatedScreenName);
	}

	@Test
	public void testGenerateAlreadyExisting() throws Exception {
		User user = TestPropsValues.getUser();

		String existingScreenName = user.getScreenName();

		String generatedScreenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			existingScreenName + "@liferay.com");

		Assert.assertNotSame(existingScreenName, generatedScreenName);

		Assert.assertEquals(existingScreenName + ".1", generatedScreenName);
	}

	@Test
	public void testGenerateNoMailAddress() throws Exception {
		long userId = TestPropsValues.getUserId();

		String generatedScreenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), userId, null);

		if (_usersScreenNameAllowNumeric) {
			Assert.assertEquals(String.valueOf(userId), generatedScreenName);
		}
		else {
			Assert.assertEquals("user." + userId, generatedScreenName);
		}
	}

	@Test
	public void testGenerateNumeric() throws Exception {
		String generatedScreenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			"123@liferay.com");

		if (_usersScreenNameAllowNumeric) {
			Assert.assertNotSame("user.123", generatedScreenName);
			Assert.assertEquals("123", generatedScreenName);
		}
		else {
			Assert.assertNotSame("123", generatedScreenName);
			Assert.assertEquals("user.123", generatedScreenName);
		}
	}

	@Test
	public void testGeneratePostfixUser() throws Exception {
		long userId = TestPropsValues.getUserId();

		String generatedScreenName = _screenNameGenerator.generate(
			TestPropsValues.getCompanyId(), userId, "postfix@liferay.com");

		Assert.assertEquals("postfix." + userId, generatedScreenName);
	}

	private static ScreenNameGenerator _screenNameGenerator;
	private static boolean _usersScreenNameAllowNumeric = false;

}