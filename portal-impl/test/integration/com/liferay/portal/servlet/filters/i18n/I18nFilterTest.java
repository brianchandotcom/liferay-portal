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

package com.liferay.portal.servlet.filters.i18n;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Manuel de la Peña
 * @author Sergio González
 */
public class I18nFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_i18nFilter = new I18nFilter();
		_mockHttpServletRequest = new MockHttpServletRequest();
		_mockHttpServletResponse = new MockHttpServletResponse();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testEnglishUserEnglishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, LocaleUtil.US, LocaleUtil.US, LocaleUtil.US);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserEnglishSessionPathWithSpanishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, LocaleUtil.US, LocaleUtil.US, LocaleUtil.SPAIN);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserEnglishSessionWithoutCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, LocaleUtil.US, LocaleUtil.US, null);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserSpanishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.US);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN), prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserSpanishSessionWithoutCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, LocaleUtil.US, LocaleUtil.SPAIN, null);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN), prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserSpanishSessionWithSpanishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.SPAIN);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN), prependI18nLanguageId);
	}

	@Test
	public void testGuestEnglishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, LocaleUtil.US, LocaleUtil.US);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testGuestEnglishSessionPathWithSpanishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, LocaleUtil.US, LocaleUtil.SPAIN);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testGuestEnglishSessionWithoutCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, LocaleUtil.US, null);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testGuestSpanishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, LocaleUtil.SPAIN, LocaleUtil.US);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN), prependI18nLanguageId);
	}

	@Test
	public void testGuestSpanishSessionWithoutCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, LocaleUtil.SPAIN, null);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN), prependI18nLanguageId);
	}

	@Test
	public void testGuestSpanishSessionWithSpanishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, LocaleUtil.SPAIN, LocaleUtil.SPAIN);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN), prependI18nLanguageId);
	}

	protected String getPrependI18nLanguageId(
			int localePrependFriendlyURLStyle, Locale userLocale,
			Locale sessionLocale, Locale cookieLocale)
		throws Exception {

		HttpSession session = _mockHttpServletRequest.getSession();

		session.setAttribute(Globals.LOCALE_KEY, sessionLocale);

		if (userLocale != null) {
			User user = UserTestUtil.addUser(
				null, userLocale, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				new long[] {_group.getGroupId()});

			_mockHttpServletRequest.setAttribute(WebKeys.USER, user);
		}

		if (cookieLocale != null) {
			LanguageUtil.updateCookie(
				_mockHttpServletRequest, _mockHttpServletResponse,
				cookieLocale);

			// Passing cookies from mock HTTP servlet response to mock HTTP
			// servlet request

			_mockHttpServletRequest.setCookies(
				_mockHttpServletResponse.getCookies());
		}

		return _i18nFilter.prependI18nLanguageId(
			_mockHttpServletRequest, localePrependFriendlyURLStyle);
	}

	@DeleteAfterTestRun
	private Group _group;

	private I18nFilter _i18nFilter;
	private MockHttpServletRequest _mockHttpServletRequest;
	private MockHttpServletResponse _mockHttpServletResponse;

}