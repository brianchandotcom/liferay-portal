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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
public class AuthTokenUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.authtokenutil"));

	@Test
	public void testGetToken() {
		Assert.assertEquals(
			"TEST_TOKEN", AuthTokenUtil.getToken(new MockHttpServletRequest()));
	}

	@Test
	public void testGetTokenByPlidAndPortletId() {
		Assert.assertEquals(
			"TEST_TOKEN_BY_PLID_AND_PORTLET_ID",
			AuthTokenUtil.getToken(
				new MockHttpServletRequest(), 0L,
				RandomTestUtil.randomString()));
	}

	@Test
	public void testIsValidPortletInvocationToken() {
		Assert.assertTrue(
			AuthTokenUtil.isValidPortletInvocationToken(
				new MockHttpServletRequest(), 0L, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				"VALID_PORTLET_INVOCATION_TOKEN"));
		Assert.assertFalse(
			AuthTokenUtil.isValidPortletInvocationToken(
				new MockHttpServletRequest(), 0L, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				"INVALID_PORTLET_INVOCATION_TOKEN"));
	}

}