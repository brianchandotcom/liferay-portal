/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.matcher;

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alicia García
 */
public class UserAgentMatcherImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsCrawlerUserAgent() {
		_userAgentMatcherImpl.setCrawlerUserAgents(_crawlerUserAgents);

		Assert.assertFalse(_userAgentMatcherImpl.isCrawlerUserAgent("another"));
		Assert.assertTrue(
			_userAgentMatcherImpl.isCrawlerUserAgent("crawlerbot"));
		Assert.assertFalse(
			_userAgentMatcherImpl.isCrawlerUserAgent("crawler_bot"));
		Assert.assertTrue(
			_userAgentMatcherImpl.isCrawlerUserAgent("W3C_Validator"));
		Assert.assertTrue(
			_userAgentMatcherImpl.isCrawlerUserAgent("w3c_validator"));
		Assert.assertTrue(
			_userAgentMatcherImpl.isCrawlerUserAgent("W3C_Validator/1.3"));
		Assert.assertFalse(
			_userAgentMatcherImpl.isCrawlerUserAgent("W3C Validator 1.3"));
	}

	private static final Set<String> _crawlerUserAgents = SetUtil.fromArray(
		"crawlerbot", "w3c_validator");

	private final UserAgentMatcherImpl _userAgentMatcherImpl =
		new UserAgentMatcherImpl();

}