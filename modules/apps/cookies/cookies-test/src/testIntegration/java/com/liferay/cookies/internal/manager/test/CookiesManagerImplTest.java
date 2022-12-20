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

package com.liferay.cookies.internal.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.util.List;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Tamas Molnar
 * @author Olivér Kecskeméty
 */
@RunWith(Arquillian.class)
public class CookiesManagerImplTest {

	@Test
	public void testCookiesConsent() throws Exception {
		_testCookiesConsentType(CookiesConstants.CONSENT_TYPE_FUNCTIONAL);
		_testCookiesConsentType(CookiesConstants.CONSENT_TYPE_NECESSARY);
		_testCookiesConsentType(CookiesConstants.CONSENT_TYPE_PERFORMANCE);
		_testCookiesConsentType(CookiesConstants.CONSENT_TYPE_PERSONALIZATION);
	}

	@Test
	public void testCookiesWithoutConsentTypeShouldBeDeleted()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_COOKIES_MANAGER_IMPL, LoggerTestUtil.WARN)) {

			Cookie cookie = new Cookie(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

			CookiesManagerUtil.addCookie(
				cookie, _mockHttpServletRequest, _mockHttpServletResponse);

			Assert.assertNull(
				CookiesManagerUtil.getCookieValue(
					cookie.getName(), _mockHttpServletRequest));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"The following cookie is trying to be added without consent " +
					"type: " + cookie.getName(),
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"The cookie will be deleted. Use the API with explicitly " +
					"declared consent type.",
				logEntry.getMessage());
		}
	}

	@Test
	public void testInternalCookiesAddedWithoutConsentType() throws Exception {
		_testInternalCookieWithoutConsentType(
			CookiesConstants.NAME_GUEST_LANGUAGE_ID,
			CookiesConstants.CONSENT_TYPE_FUNCTIONAL);
		_testInternalCookieWithoutConsentType(
			CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL,
			CookiesConstants.CONSENT_TYPE_NECESSARY);
		_testInternalCookieWithoutConsentType(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY,
			CookiesConstants.CONSENT_TYPE_NECESSARY);
		_testInternalCookieWithoutConsentType(
			CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
			CookiesConstants.CONSENT_TYPE_NECESSARY);
		_testInternalCookieWithoutConsentType(
			CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
			CookiesConstants.CONSENT_TYPE_NECESSARY);
		_testInternalCookieWithoutConsentType(
			CookiesConstants.NAME_COOKIE_SUPPORT,
			CookiesConstants.CONSENT_TYPE_NECESSARY);
		_testInternalCookieWithoutConsentType(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED,
			CookiesConstants.CONSENT_TYPE_NECESSARY);
	}

	@Test
	public void testKnownCookiesAddedWithPreviouslyKnownConsentType()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_COOKIES_MANAGER_IMPL, LoggerTestUtil.WARN)) {

			_addCookie(true, CookiesConstants.CONSENT_TYPE_PERFORMANCE);

			Cookie cookie = new Cookie(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

			CookiesManagerUtil.addCookie(
				CookiesConstants.CONSENT_TYPE_PERFORMANCE, cookie,
				_mockHttpServletRequest, _mockHttpServletResponse);

			Assert.assertEquals(
				cookie.getValue(),
				CookiesManagerUtil.getCookieValue(
					cookie.getName(), _mockHttpServletRequest));

			cookie.setValue(RandomTestUtil.randomString());

			CookiesManagerUtil.addCookie(
				cookie, _mockHttpServletRequest, _mockHttpServletResponse);

			Assert.assertEquals(
				cookie.getValue(),
				CookiesManagerUtil.getCookieValue(
					cookie.getName(), _mockHttpServletRequest));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"The following cookie is trying to be added without consent " +
					"type: " + cookie.getName(),
				logEntry.getMessage());

			logEntry = logEntries.get(1);

			Assert.assertEquals(
				"The cookie will be added with the consent type used " +
					"previously. Use the API with explicitly declared " +
						"consent type.",
				logEntry.getMessage());
		}
	}

	@Test
	public void testSetDifferentConsentTypeToAlreadyKnownCookie()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_COOKIES_MANAGER_IMPL, LoggerTestUtil.WARN)) {

			_addCookie(true, CookiesConstants.CONSENT_TYPE_FUNCTIONAL);

			Cookie cookie = new Cookie(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

			CookiesManagerUtil.addCookie(
				CookiesConstants.CONSENT_TYPE_NECESSARY, cookie,
				_mockHttpServletRequest, _mockHttpServletResponse);

			Assert.assertNotNull(
				CookiesManagerUtil.getCookieValue(
					cookie.getName(), _mockHttpServletRequest));

			CookiesManagerUtil.addCookie(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL, cookie,
				_mockHttpServletRequest, _mockHttpServletResponse);

			Assert.assertNotNull(
				CookiesManagerUtil.getCookieValue(
					cookie.getName(), _mockHttpServletRequest));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"The ", cookie.getName(),
					" cookie was previously added with consent type ",
					CookiesConstants.CONSENT_TYPE_NECESSARY,
					" and will now be upgraded to consent type ",
					CookiesConstants.CONSENT_TYPE_FUNCTIONAL),
				logEntry.getMessage());
		}
	}

	private void _addCookie(boolean accepted, int consentType) {
		if (consentType == CookiesConstants.CONSENT_TYPE_NECESSARY) {
			return;
		}

		String cookieName = StringPool.BLANK;

		if (consentType == CookiesConstants.CONSENT_TYPE_FUNCTIONAL) {
			cookieName = CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL;
		}
		else if (consentType == CookiesConstants.CONSENT_TYPE_PERFORMANCE) {
			cookieName = CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE;
		}
		else if (consentType == CookiesConstants.CONSENT_TYPE_PERSONALIZATION) {
			cookieName = CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION;
		}

		CookiesManagerUtil.addCookie(
			CookiesConstants.CONSENT_TYPE_NECESSARY,
			new Cookie(cookieName, String.valueOf(accepted)),
			_mockHttpServletRequest, _mockHttpServletResponse);
	}

	private void _testCookiesConsentType(int consentType) {
		_addCookie(false, consentType);

		Cookie cookie = new Cookie(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		CookiesManagerUtil.addCookie(
			consentType, cookie, _mockHttpServletRequest,
			_mockHttpServletResponse);

		if (consentType == CookiesConstants.CONSENT_TYPE_NECESSARY) {
			Assert.assertNotNull(
				CookiesManagerUtil.getCookieValue(
					cookie.getName(), _mockHttpServletRequest));
		}
		else {
			Assert.assertNull(
				CookiesManagerUtil.getCookieValue(
					cookie.getName(), _mockHttpServletRequest));
		}

		_addCookie(true, consentType);

		CookiesManagerUtil.addCookie(
			consentType, cookie, _mockHttpServletRequest,
			_mockHttpServletResponse);

		Assert.assertNotNull(
			CookiesManagerUtil.getCookieValue(
				cookie.getName(), _mockHttpServletRequest));
	}

	private void _testInternalCookieWithoutConsentType(
		String name, int consentType) {

		_addCookie(false, consentType);

		Cookie cookie = new Cookie(name, RandomTestUtil.randomString());

		CookiesManagerUtil.addCookie(
			cookie, _mockHttpServletRequest, _mockHttpServletResponse);

		if (consentType == CookiesConstants.CONSENT_TYPE_NECESSARY) {
			Assert.assertNotNull(
				CookiesManagerUtil.getCookieValue(
					cookie.getName(), _mockHttpServletRequest));
		}
		else {
			Assert.assertNull(
				CookiesManagerUtil.getCookieValue(
					cookie.getName(), _mockHttpServletRequest));
		}

		_addCookie(true, consentType);

		CookiesManagerUtil.addCookie(
			cookie, _mockHttpServletRequest, _mockHttpServletResponse);

		Assert.assertNotNull(
			CookiesManagerUtil.getCookieValue(
				cookie.getName(), _mockHttpServletRequest));
	}

	private static final String _CLASS_NAME_COOKIES_MANAGER_IMPL =
		"com.liferay.cookies.internal.manager.CookiesManagerImpl";

	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();
	private final MockHttpServletResponse _mockHttpServletResponse =
		new MockHttpServletResponse();

}