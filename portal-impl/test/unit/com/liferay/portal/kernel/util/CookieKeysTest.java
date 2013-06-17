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

package com.liferay.portal.kernel.util;

import com.liferay.portal.util.PropsImpl;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Raymond Augé
 */
public class CookieKeysTest {

	@Test
	public void domainTest1() throws Exception {
		String domain = CookieKeys.getDomain("www.liferay.com");

		Assert.assertEquals(".liferay.com", domain);
	}

	@Test
	public void domainTest2() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("www.liferay.com");

		String domain = CookieKeys.getDomain(mockHttpServletRequest);

		Assert.assertEquals(".liferay.com", domain);
	}

	@Test
	public void domainTest3() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("www.liferay.com");

		Field field = ReflectionUtil.getDeclaredField(
			CookieKeys.class, "_SESSION_COOKIE_DOMAIN");

		Object value = field.get(null);

		try {
			field.set(null, "www.example.com");

			String domain = CookieKeys.getDomain(mockHttpServletRequest);

			Assert.assertEquals("www.example.com", domain);
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void domainTest4() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("www.liferay.com");

		Field field = ReflectionUtil.getDeclaredField(
			CookieKeys.class, "_SESSION_COOKIE_USE_FULL_HOSTNAME");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE);

			String domain = CookieKeys.getDomain(mockHttpServletRequest);

			Assert.assertEquals(".liferay.com", domain);
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void domainTest5() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("www.liferay.com");

		Field field = ReflectionUtil.getDeclaredField(
			CookieKeys.class, "_SESSION_COOKIE_USE_FULL_HOSTNAME");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			String domain = CookieKeys.getDomain(mockHttpServletRequest);

			Assert.assertEquals("www.liferay.com", domain);
		}
		finally {
			field.set(null, value);
		}
	}

	@Before
	public void setUp() {
		PropsUtil.setProps(new PropsImpl());
	}

}