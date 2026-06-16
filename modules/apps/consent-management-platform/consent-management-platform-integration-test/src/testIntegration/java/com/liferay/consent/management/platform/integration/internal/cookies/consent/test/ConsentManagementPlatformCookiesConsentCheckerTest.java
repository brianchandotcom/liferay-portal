/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.consent.management.platform.integration.internal.cookies.consent.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.cookies.consent.CookiesConsentChecker;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
		_testHasConsent(
			"%%%" + RandomTestUtil.randomString(), true, true, true, true);
		_testHasConsent(
			URLEncoder.encode(
				RandomTestUtil.randomString(), StandardCharsets.UTF_8),
			true, true, true, true);
		_testHasConsent(
			_encodeCookieValue(
				JSONUtil.put(
					CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, "true"
				).put(
					CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE, "false"
				).put(
					CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION, "false"
				)),
			true, true, false, false);
		_testHasConsent(
			_encodeCookieValue(
				JSONUtil.put(
					CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, false
				).put(
					CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE, false
				).put(
					CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION, false
				)),
			false, true, false, false);
		_testHasConsent(
			_encodeCookieValue(
				JSONUtil.put(
					CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, false
				).put(
					CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION, false
				)),
			false, true, true, false);
		_testHasConsent(
			_encodeCookieValue(
				JSONUtil.put(
					CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, true
				).put(
					CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE, false
				).put(
					CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION, true
				)),
			true, true, false, true);
		_testHasConsent(
			_encodeCookieValue(
				JSONUtil.put(
					CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, true
				).put(
					CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE, true
				).put(
					CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION, true
				)),
			true, true, true, true);
		_testHasConsent(null, true, true, true, true);
	}

	private MockHttpServletRequest _createMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.COMPANY_ID, TestPropsValues.getCompanyId());

		return mockHttpServletRequest;
	}

	private String _encodeCookieValue(JSONObject jsonObject) {
		return URLEncoder.encode(jsonObject.toString(), StandardCharsets.UTF_8);
	}

	private void _testHasConsent(
			String consentStateCookieValue, boolean expectedFunctionalConsent,
			boolean expectedNecessaryConsent,
			boolean expectedPerformanceConsent,
			boolean expectedPersonalizationConsent)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			_createMockHttpServletRequest();

		if (consentStateCookieValue != null) {
			mockHttpServletRequest.setCookies(
				new Cookie(
					CookiesConstants.NAME_CONSENT_STATE,
					consentStateCookieValue));
		}

		Assert.assertEquals(
			expectedFunctionalConsent,
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_FUNCTIONAL,
				mockHttpServletRequest));
		Assert.assertEquals(
			expectedNecessaryConsent,
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_NECESSARY,
				mockHttpServletRequest));
		Assert.assertEquals(
			expectedPerformanceConsent,
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERFORMANCE,
				mockHttpServletRequest));
		Assert.assertEquals(
			expectedPersonalizationConsent,
			_cookiesConsentChecker.hasConsent(
				CookiesConstants.CONSENT_TYPE_PERSONALIZATION,
				mockHttpServletRequest));
	}

	@Inject(
		filter = "component.name=com.liferay.consent.management.platform.integration.internal.cookies.consent.ConsentManagementPlatformCookiesConsentChecker"
	)
	private CookiesConsentChecker _cookiesConsentChecker;

}