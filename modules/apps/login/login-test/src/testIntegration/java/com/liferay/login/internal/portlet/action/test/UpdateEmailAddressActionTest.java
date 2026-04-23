/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.Cookie;

import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Debora Buriti
 */
@RunWith(Arquillian.class)
public class UpdateEmailAddressActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testRefererParameterIsSanitized() throws Exception {
		Http.Options loginOptions = new Http.Options();

		loginOptions.addPart(
			"login",
			TestPropsValues.getUser(
			).getEmailAddress());
		loginOptions.addPart("password", TestPropsValues.USER_PASSWORD);
		loginOptions.setCookies(new Cookie[] {new Cookie("_", "_")});
		loginOptions.setFollowRedirects(false);
		loginOptions.setLocation(
			TestPropsValues.PORTAL_URL + "/c/portal/login");
		loginOptions.setPost(true);

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"AUTH_TOKEN_CHECK_ENABLED", false)) {

			HttpUtil.URLtoString(loginOptions);
		}

		String jsessionId = _getJsessionId(HttpUtil.getCookies());

		String randomDomain = RandomTestUtil.randomString(
		).toLowerCase();

		String maliciousReferer = StringBundler.concat(
			"http://www.", randomDomain, ".com");

		Http.Options options = new Http.Options();

		options.addHeader("Cookie", "JSESSIONID=" + jsessionId);
		options.setCookieSpec(Http.CookieSpec.IGNORE_COOKIES);
		options.setLocation(
			StringBundler.concat(
				TestPropsValues.PORTAL_URL,
				"/c/portal/update_email_address?referer=", maliciousReferer));
		options.setMethod(Http.Method.GET);

		String response = HttpUtil.URLtoString(options);

		Assert.assertFalse(
			response.contains("value=\"" + maliciousReferer + "\""));
	}

	private String _getJsessionId(Cookie[] cookies) {
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (Objects.equals(cookie.getName(), "JSESSIONID")) {
				return cookie.getValue();
			}
		}

		return null;
	}

}