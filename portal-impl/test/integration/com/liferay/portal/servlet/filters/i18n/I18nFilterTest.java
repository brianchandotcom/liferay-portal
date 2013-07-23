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

package com.liferay.portal.servlet.filters.i18n;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Manuel de la Peña
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class I18nFilterTest {

	@Before
	public void setUp() throws Exception {
		_i18nFilter = new I18nFilter();
		_request = new MockHttpServletRequest();
		_response = new MockHttpServletResponse();
	}

	@Test
	public void tesGuestSpanishSessionWithSpanishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, _esLocale, _esLocale);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(_esLocale), prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserEnglishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, _enLocale, _enLocale, _enLocale);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserEnglishSessionPathWithSpanishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, _enLocale, _enLocale, _esLocale);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserEnglishSessionWithoutCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, _enLocale, _enLocale, null);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserSpanishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, _enLocale, _esLocale, _enLocale);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(_esLocale), prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserSpanishSessionWithoutCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, _enLocale, _esLocale, null);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(_esLocale), prependI18nLanguageId);
	}

	@Test
	public void testEnglishUserSpanishSessionWithSpanishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, _enLocale, _esLocale, _esLocale);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(_esLocale), prependI18nLanguageId);
	}

	@Test
	public void testGuestEnglishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, _enLocale, _enLocale);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testGuestEnglishSessionPathWithSpanishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, _enLocale, _esLocale);

		Assert.assertEquals(
			LocaleUtil.toLanguageId(_esLocale), prependI18nLanguageId);
	}

	@Test
	public void testGuestEnglishSessionWithoutCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, _enLocale, null);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testGuestSpanishSessionPathWithEnglishCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, _esLocale, _enLocale);

		Assert.assertNull(prependI18nLanguageId);
	}

	@Test
	public void testGuestSpanishSessionWithoutCookieAlgorithm3()
		throws Exception {

		String prependI18nLanguageId = getPrependI18nLanguageId(
			3, null, _esLocale, null);

		Assert.assertNull(prependI18nLanguageId);
	}

	protected String getPrependI18nLanguageId(
			int localePrependFriendlyURLStyle, Locale userLocale,
			Locale sessionLocale, Locale cookieLocale)
		throws Exception {

		HttpSession session = _request.getSession();

		session.setAttribute(Globals.LOCALE_KEY, sessionLocale);

		if (userLocale != null) {
			User user = UserTestUtil.addUser(
				ServiceTestUtil.randomString(), true, userLocale,
				ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
				new long[] {TestPropsValues.getGroupId()});

			_request.setAttribute(WebKeys.USER, user);
		}

		if (cookieLocale != null) {
			LanguageUtil.updateCookie(_request, _response, cookieLocale);

			// passing cookies from mockresponse to mockrequest

			_request.setCookies(_response.getCookies());
		}

		return _i18nFilter.prependI18nLanguageId(
			_request, StringPool.BLANK, localePrependFriendlyURLStyle);
	}

	private static Locale _enLocale = new Locale("en", "US");
	private static Locale _esLocale = new Locale("es", "ES");

	private I18nFilter _i18nFilter;
	private MockHttpServletRequest _request;
	private MockHttpServletResponse _response;

}