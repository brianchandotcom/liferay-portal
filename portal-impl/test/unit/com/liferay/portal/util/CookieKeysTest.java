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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Raymond Augé
 */
public class CookieKeysTest {

	@Before
	public void setup() {
		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void domainTest1() throws Exception {
		String domain = CookieKeys.getDomain("www.liferay.com");

		Assert.assertEquals(".liferay.com", domain);
	}

	@Test
	public void domainTest2() throws Exception {
		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setServerName("www.liferay.com");

		String domain = CookieKeys.getDomain(httpServletRequest);

		Assert.assertEquals(".liferay.com", domain);
	}

	@Test
	public void domainTest3() throws Exception {
		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setServerName("www.liferay.com");

		Field field = CookieKeys.class.getDeclaredField(
			"_SESSION_COOKIE_DOMAIN");

		setAccessible(field);

		Object originalValue = field.get(null);

		try {
			field.set(null, "www.example.com");

			String domain = CookieKeys.getDomain(httpServletRequest);

			Assert.assertEquals("www.example.com", domain);
		}
		finally {
			field.set(null, originalValue);
		}
	}

	@Test
	public void domainTest4() throws Exception {
		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setServerName("www.liferay.com");

		Field field = CookieKeys.class.getDeclaredField(
			"_SESSION_COOKIE_USE_FULL_HOSTNAME");

		setAccessible(field);

		Object originalValue = field.get(null);

		try {
			field.set(null, Boolean.FALSE);

			String domain = CookieKeys.getDomain(httpServletRequest);

			Assert.assertEquals(".liferay.com", domain);
		}
		finally {
			field.set(null, originalValue);
		}
	}

	@Test
	public void domainTest5() throws Exception {
		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setServerName("www.liferay.com");

		Field field = CookieKeys.class.getDeclaredField(
			"_SESSION_COOKIE_USE_FULL_HOSTNAME");

		setAccessible(field);

		Object originalValue = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			String domain = CookieKeys.getDomain(httpServletRequest);

			Assert.assertEquals("www.liferay.com", domain);
		}
		finally {
			field.set(null, originalValue);
		}
	}

	protected void setAccessible(Field field) throws Exception {
		field.setAccessible(true);

		int modifiers = field.getModifiers();

		if ((modifiers & Modifier.FINAL) == Modifier.FINAL) {
			Field modifiersField = Field.class.getDeclaredField("modifiers");

			modifiersField.setAccessible(true);
			modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
		}
	}

}