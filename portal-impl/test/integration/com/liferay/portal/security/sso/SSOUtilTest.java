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

package com.liferay.portal.security.sso;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class SSOUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.ssoutil"));

	@Test
	public void testGetSessionExpirationRedirectURL() {
		Assert.assertEquals(
			"getSessionExpirationRedirectUrl:1",
			SSOUtil.getSessionExpirationRedirectURL(
				1, "sessionExpirationRedirectURL"));
	}

	@Test
	public void testGetSignInURL() {
		Assert.assertEquals(
			"signInURL:1", SSOUtil.getSignInURL(1, "signInURL"));
	}

	@Test
	public void testIsLoginRedirectRequired() {
		Assert.assertTrue(SSOUtil.isLoginRedirectRequired(1));
		Assert.assertFalse(SSOUtil.isLoginRedirectRequired(2));
	}

	@Test
	public void testIsRedirectRequired() {
		Assert.assertTrue(SSOUtil.isRedirectRequired(1));
		Assert.assertFalse(SSOUtil.isRedirectRequired(2));
	}

	@Test
	public void testIsSessionRedirectOnExpire() {
		Assert.assertTrue(SSOUtil.isSessionRedirectOnExpire(1));
		Assert.assertFalse(SSOUtil.isSessionRedirectOnExpire(2));
	}

}