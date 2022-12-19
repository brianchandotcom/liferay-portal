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

package com.liferay.cookies.internal.manager;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cookies.CookiesManager;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Raymond Augé
 * @author Olivér Kecskeméty
 */
public class CookiesDomainTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			CookiesManagerUtil.class, "_cookiesManager", _cookiesManager);
	}

	@Test
	public void testDomain1() throws Exception {
		Assert.assertEquals(
			".liferay.com", CookiesManagerUtil.getDomain("www.liferay.com"));
	}

	@Test
	public void testDomain2() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("www.liferay.com");

		Assert.assertEquals(
			".liferay.com",
			CookiesManagerUtil.getDomain(mockHttpServletRequest));
	}

	@Test
	public void testDomain3() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("www.liferay.com");

		Object value = ReflectionTestUtil.getAndSetFieldValue(
			CookiesManagerImpl.class, "_SESSION_COOKIE_DOMAIN",
			"www.example.com");

		try {
			Assert.assertEquals(
				"www.example.com",
				CookiesManagerUtil.getDomain(mockHttpServletRequest));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				CookiesManagerImpl.class, "_SESSION_COOKIE_DOMAIN", value);
		}
	}

	@Test
	public void testDomain4() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("www.liferay.com");

		Object value = ReflectionTestUtil.getAndSetFieldValue(
			CookiesManagerImpl.class, "_SESSION_COOKIE_USE_FULL_HOSTNAME",
			Boolean.FALSE);

		try {
			Assert.assertEquals(
				".liferay.com",
				CookiesManagerUtil.getDomain(mockHttpServletRequest));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				CookiesManagerImpl.class, "_SESSION_COOKIE_USE_FULL_HOSTNAME",
				value);
		}
	}

	@Test
	public void testDomain5() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName("www.liferay.com");

		Object value = ReflectionTestUtil.getAndSetFieldValue(
			CookiesManagerImpl.class, "_SESSION_COOKIE_USE_FULL_HOSTNAME",
			Boolean.TRUE);

		try {
			Assert.assertEquals(
				StringPool.BLANK,
				CookiesManagerUtil.getDomain(mockHttpServletRequest));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				CookiesManagerImpl.class, "_SESSION_COOKIE_USE_FULL_HOSTNAME",
				value);
		}
	}

	private final CookiesManager _cookiesManager = new CookiesManagerImpl();

}