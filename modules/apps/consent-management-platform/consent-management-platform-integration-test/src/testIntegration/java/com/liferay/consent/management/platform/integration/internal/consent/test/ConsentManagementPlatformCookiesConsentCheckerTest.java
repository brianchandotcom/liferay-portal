/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.consent.management.platform.integration.internal.consent.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.cookies.consent.CookiesConsentChecker;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.Cookie;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Christian Moura
 */
@RunWith(Arquillian.class)
public class ConsentManagementPlatformCookiesConsentCheckerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testHasConsent() throws Exception {
		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL,
				_createMockHttpServletRequest()));

		MockHttpServletRequest mockHttpServletRequest =
			_createMockHttpServletRequest();

		mockHttpServletRequest.setCookies(
			new Cookie(CookiesConstants.NAME_CONSENT_STATE, "%%%notjson"));

		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL,
				mockHttpServletRequest));

		mockHttpServletRequest = _createMockHttpServletRequest();

		mockHttpServletRequest.setCookies(
			new Cookie(
				CookiesConstants.NAME_CONSENT_STATE,
				URLEncoder.encode("not-json", StandardCharsets.UTF_8)));

		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL,
				mockHttpServletRequest));

		mockHttpServletRequest = _createMockHttpServletRequest();

		mockHttpServletRequest.setCookies(
			new Cookie(
				CookiesConstants.NAME_CONSENT_STATE,
				_encodeConsentState(
					JSONUtil.put(
						CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, true
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, true
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE, true
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION, true
					))));

		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL,
				mockHttpServletRequest));
		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_NECESSARY,
				mockHttpServletRequest));
		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERFORMANCE,
				mockHttpServletRequest));
		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERSONALIZATION,
				mockHttpServletRequest));

		mockHttpServletRequest = _createMockHttpServletRequest();

		mockHttpServletRequest.setCookies(
			new Cookie(
				CookiesConstants.NAME_CONSENT_STATE,
				_encodeConsentState(
					JSONUtil.put(
						CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, false
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, false
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE, false
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
						false
					))));

		Assert.assertFalse(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL,
				mockHttpServletRequest));
		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_NECESSARY,
				mockHttpServletRequest));
		Assert.assertFalse(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERFORMANCE,
				mockHttpServletRequest));
		Assert.assertFalse(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERSONALIZATION,
				mockHttpServletRequest));

		mockHttpServletRequest = _createMockHttpServletRequest();

		mockHttpServletRequest.setCookies(
			new Cookie(
				CookiesConstants.NAME_CONSENT_STATE,
				_encodeConsentState(
					JSONUtil.put(
						CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, true
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, true
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE, false
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION, true
					))));

		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL,
				mockHttpServletRequest));
		Assert.assertFalse(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERFORMANCE,
				mockHttpServletRequest));
		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERSONALIZATION,
				mockHttpServletRequest));

		mockHttpServletRequest = _createMockHttpServletRequest();

		mockHttpServletRequest.setCookies(
			new Cookie(
				CookiesConstants.NAME_CONSENT_STATE,
				_encodeConsentState(
					JSONUtil.put(
						CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, false
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, true
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
						false
					))));

		Assert.assertFalse(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL,
				mockHttpServletRequest));
		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERFORMANCE,
				mockHttpServletRequest));

		mockHttpServletRequest = _createMockHttpServletRequest();

		mockHttpServletRequest.setCookies(
			new Cookie(
				CookiesConstants.NAME_CONSENT_STATE,
				_encodeConsentState(
					JSONUtil.put(
						CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, "true"
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, "true"
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE, "false"
					).put(
						CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
						"false"
					))));

		Assert.assertTrue(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL,
				mockHttpServletRequest));
		Assert.assertFalse(
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERFORMANCE,
				mockHttpServletRequest));
	}

	private MockHttpServletRequest _createMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.COMPANY_ID, TestPropsValues.getCompanyId());

		return mockHttpServletRequest;
	}

	private String _encodeConsentState(JSONObject consentStateJSONObject) {
		return URLEncoder.encode(
			consentStateJSONObject.toString(), StandardCharsets.UTF_8);
	}

	@Inject(
		filter = "component.name=com.liferay.consent.management.platform.integration.internal.consent.ConsentManagementPlatformCookiesConsentChecker"
	)
	private CookiesConsentChecker _cookiesConsentChecker;

}