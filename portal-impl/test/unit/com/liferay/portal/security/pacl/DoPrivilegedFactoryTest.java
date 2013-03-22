/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.lang.DoPrivilegedBean;
import com.liferay.portal.security.lang.DoPrivilegedFactory;

import org.junit.Before;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Raymond Augé
 */
public class DoPrivilegedFactoryTest {

	@Before
	public void setup() {
		PortalClassLoaderUtil.setClassLoader(UserImpl.class.getClassLoader());
	}

	@Test
	public void test_boolean() {
		boolean testBoolean = Boolean.TRUE;

		Boolean wrappedBoolean = DoPrivilegedFactory.wrap(testBoolean);

		Assert.assertTrue(wrappedBoolean);
		Assert.assertEquals(wrappedBoolean.getClass(), Boolean.class);
	}

	@Test
	public void test_NoInterfaces() {
		NoInterfaces testNoInterfaces = new NoInterfaces();

		NoInterfaces wrappedNoInterfaces = DoPrivilegedFactory.wrap(
			testNoInterfaces);

		Assert.assertEquals(wrappedNoInterfaces, testNoInterfaces);
		Assert.assertFalse(wrappedNoInterfaces instanceof DoPrivilegedBean);
	}

	@Test
	public void test_String() {
		String testString = "test string";

		String wrappedString = DoPrivilegedFactory.wrap(testString);

		Assert.assertEquals(wrappedString, testString);
		Assert.assertEquals(wrappedString.getClass(), String.class);
	}

	@Test
	public void test_User() {
		User user = new UserImpl();

		User wrappedUser = DoPrivilegedFactory.wrap(user);

		Assert.assertTrue(wrappedUser instanceof DoPrivilegedBean);
	}

	private class NoInterfaces {}

}