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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.lang.DoPrivilegedBean;
import com.liferay.portal.security.lang.DoPrivilegedFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class DoPrivilegedFactoryTest {

	@Before
	public void setUp() {
		PortalClassLoaderUtil.setClassLoader(UserImpl.class.getClassLoader());
	}

	@Test
	public void testBoolean() {
		Boolean wrappedBoolean = DoPrivilegedFactory.wrap(Boolean.TRUE);

		Assert.assertTrue(wrappedBoolean);
		Assert.assertEquals(wrappedBoolean.getClass(), Boolean.class);
	}

	@Test
	public void testClassWithNoInterfaces() {
		ClassWithNoInterfaces classWithNoInterfaces =
			new ClassWithNoInterfaces();

		ClassWithNoInterfaces wrappedClassWithNoInterfaces =
			DoPrivilegedFactory.wrap(classWithNoInterfaces);

		Assert.assertEquals(
			wrappedClassWithNoInterfaces, classWithNoInterfaces);
		Assert.assertFalse(
			wrappedClassWithNoInterfaces instanceof DoPrivilegedBean);
	}

	@Test
	public void testString() {
		String string = DoPrivilegedFactory.wrap("Test");

		Assert.assertEquals(string, "Test");
		Assert.assertEquals(string.getClass(), String.class);
	}

	@Test
	public void testUser() {
		User user = DoPrivilegedFactory.wrap(new UserImpl());

		Assert.assertTrue(user instanceof DoPrivilegedBean);
	}

	private class ClassWithNoInterfaces {
	}

}