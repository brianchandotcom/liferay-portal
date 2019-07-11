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

package com.liferay.portal.servlet.filters.i18n.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Manuel de la Peña
 * @author Sergio González
 */
@RunWith(Arquillian.class)
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

		Assert.assertNull(
			_getPrependI18nLanguageId(
				3, LocaleUtil.US, LocaleUtil.US, LocaleUtil.US));
	}

	@Test
	public void testEnglishUserEnglishSessionPathWithSpanishCookieAlgorithm3()
		throws Exception {

		Assert.assertNull(
			_getPrependI18nLanguageId(
				3, LocaleUtil.US, LocaleUtil.US, LocaleUtil.SPAIN));
	}

	@Test
	public void testEnglishUserEnglishSessionWithoutCookieAlgorithm3()
		throws Exception {

		Assert.assertNull(
			_getPrependI18nLanguageId(3, LocaleUtil.US, LocaleUtil.US, null));
	}

	@Test
	public void testEnglishUserSpanishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			_getPrependI18nLanguageId(
				3, LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.US));
	}

	@Test
	public void testEnglishUserSpanishSessionWithoutCookieAlgorithm3()
		throws Exception {

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			_getPrependI18nLanguageId(
				3, LocaleUtil.US, LocaleUtil.SPAIN, null));
	}

	@Test
	public void testEnglishUserSpanishSessionWithSpanishCookieAlgorithm3()
		throws Exception {

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			_getPrependI18nLanguageId(
				3, LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.SPAIN));
	}

	@Test
	public void testGuestEnglishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		Assert.assertNull(
			_getPrependI18nLanguageId(3, null, LocaleUtil.US, LocaleUtil.US));
	}

	@Test
	public void testGuestEnglishSessionPathWithSpanishCookieAlgorithm3()
		throws Exception {

		Assert.assertNull(
			_getPrependI18nLanguageId(
				3, null, LocaleUtil.US, LocaleUtil.SPAIN));
	}

	@Test
	public void testGuestEnglishSessionWithoutCookieAlgorithm3()
		throws Exception {

		Assert.assertNull(
			_getPrependI18nLanguageId(3, null, LocaleUtil.US, null));
	}

	@Test
	public void testGuestSpanishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			_getPrependI18nLanguageId(
				3, null, LocaleUtil.SPAIN, LocaleUtil.US));
	}

	@Test
	public void testGuestSpanishSessionWithoutCookieAlgorithm3()
		throws Exception {

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			_getPrependI18nLanguageId(3, null, LocaleUtil.SPAIN, null));
	}

	@Test
	public void testGuestSpanishSessionWithSpanishCookieAlgorithm3()
		throws Exception {

		Assert.assertEquals(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN),
			_getPrependI18nLanguageId(
				3, null, LocaleUtil.SPAIN, LocaleUtil.SPAIN));
	}

	private String _getPrependI18nLanguageId(
			int localePrependFriendlyURLStyle, Locale userLocale,
			Locale sessionLocale, Locale cookieLocale)
		throws Exception {

		HttpSession session = _mockHttpServletRequest.getSession();

		session.setAttribute(WebKeys.LOCALE, sessionLocale);

		if (userLocale != null) {
			_user = UserTestUtil.addUser(
				null, userLocale, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				new long[] {_group.getGroupId()});

			_mockHttpServletRequest.setAttribute(WebKeys.USER, _user);
		}

		if (cookieLocale != null) {
			_language.updateCookie(
				_mockHttpServletRequest, _mockHttpServletResponse,
				cookieLocale);

			// Passing cookies from mock HTTP servlet response to mock HTTP
			// servlet request

			_mockHttpServletRequest.setCookies(
				_mockHttpServletResponse.getCookies());
		}

		return ReflectionTestUtil.invoke(
			_i18nFilter, "prependI18nLanguageId",
			new Class<?>[] {HttpServletRequest.class, int.class},
			_mockHttpServletRequest, localePrependFriendlyURLStyle);
	}

	@DeleteAfterTestRun
	private Group _group;

	private I18nFilter _i18nFilter;

	@Inject
	private Language _language;

	private MockHttpServletRequest _mockHttpServletRequest;
	private MockHttpServletResponse _mockHttpServletResponse;

	@DeleteAfterTestRun
	private User _user;

}