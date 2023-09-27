/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.manager;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.cookies.CookiesManager;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

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

	@BeforeClass
	public static void setUpClass() {
		CookiesManager cookiesManager = new CookiesManagerImpl();

		ReflectionTestUtil.setFieldValue(
			cookiesManager, "_configurationProvider",
			Mockito.mock(ConfigurationProvider.class));

		ReflectionTestUtil.setFieldValue(
			CookiesManagerUtil.class, "_cookiesManager", cookiesManager);
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

}