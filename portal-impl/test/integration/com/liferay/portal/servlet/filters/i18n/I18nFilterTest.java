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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.UserTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-37809.
 * </p>
 *
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class I18nFilterTest {

	@Before
	public void setUp() throws Exception {
		_i18nFilter = new I18nFilter();
		_request = new MockHttpServletRequest();
		_response = new MockHttpServletResponse();

		_user = null;
	}

	@After
	public void tearDown() throws Exception {
		if (_user != null) {
			UserLocalServiceUtil.deleteUser(_user);
		}
	}

	@Test
	public void testPrependI18nLanguageAlgorithm3_Login_Cookie_I18n()
		throws Exception {

		String prependedLocale = getPrependI18nLanguage(3, true, true, true);

		Assert.assertNull(prependedLocale);
	}

	@Test
	public void testPrependI18nLanguageAlgorithm3_Login_Cookie_NoI18n()
		throws Exception {

		String prependedLocale = getPrependI18nLanguage(3, true, true, false);

		Assert.assertNull(prependedLocale);
	}

	@Test
	public void testPrependI18nLanguageAlgorithm3_Login_NoCookie_I18n()
		throws Exception {

		String prependedLocale = getPrependI18nLanguage(3, true, false, true);

		Assert.assertNull(prependedLocale);
	}

	@Test
	public void testPrependI18nLanguageAlgorithm3_Login_NoCookie_NoI18n()
		throws Exception {

		String prependedLocale = getPrependI18nLanguage(3, true, false, false);

		Assert.assertNull(prependedLocale);
	}

	@Test
	public void testPrependI18nLanguageAlgorithm3_NoLogin_Cookie_I18n()
		throws Exception {

		String prependedLocale = getPrependI18nLanguage(3, false, true, true);

		Assert.assertEquals(_DEFAULT_I18N_LANGUAGE_ID, prependedLocale);
	}

	@Test
	public void testPrependI18nLanguageAlgorithm3_NoLogin_Cookie_NoI18n()
		throws Exception {

		String prependedLocale = getPrependI18nLanguage(3, false, true, false);

		Assert.assertEquals(_DEFAULT_I18N_LANGUAGE_ID, prependedLocale);
	}

	@Test
	public void testPrependI18nLanguageAlgorithm3_NoLogin_NoCookie_I18n()
		throws Exception {

		String prependedLocale = getPrependI18nLanguage(3, false, false, true);

		Assert.assertEquals(_DEFAULT_I18N_LANGUAGE_ID, prependedLocale);
	}

	@Test
	public void testPrependI18nLanguageAlgorithm3_NoLogin_NoCookie_NoI18n()
		throws Exception {

		String prependedLocale = getPrependI18nLanguage(3, false, false, false);

		Assert.assertEquals(_DEFAULT_WEB_LANGUAGE_ID, prependedLocale);
	}

	protected String getPrependI18nLanguage(
			int algorithm, boolean login, boolean cookie, boolean i18n)
		throws Exception {

		if (login) {
			_user = UserTestUtil.addUser();

			_request.setAttribute(WebKeys.USER, _user);
		}

		String i18nLanguageId = _DEFAULT_WEB_LANGUAGE_ID;

		if (cookie) {
			i18nLanguageId = _DEFAULT_I18N_LANGUAGE_ID;

			LanguageUtil.updateCookie(
				_request, _response, LocaleUtil.fromLanguageId("es_ES"));

			// passing cookies from mockresponse to mockrequest

			_request.setCookies(_response.getCookies());
		}

		if (i18n) {
			i18nLanguageId = _DEFAULT_I18N_LANGUAGE_ID;
		}

		return _i18nFilter.prependI18nLanguageId(
			_request, i18nLanguageId, algorithm);
	}

	private static final String _DEFAULT_I18N_LANGUAGE_ID = "es";

	private static final String _DEFAULT_WEB_LANGUAGE_ID = "web";

	private I18nFilter _i18nFilter;
	private MockHttpServletRequest _request;
	private MockHttpServletResponse _response;
	private User _user;

}