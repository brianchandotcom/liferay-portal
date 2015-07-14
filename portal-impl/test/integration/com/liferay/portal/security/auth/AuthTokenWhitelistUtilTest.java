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
import com.liferay.portal.security.auth.bundle.authtokenignoreactions.TestAuthTokenIgnoreActions;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class AuthTokenWhitelistUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.authtokenignoreactions"));

	@Before
	public void setUp() {
		AuthTokenIgnoreActionsRegistry.register(new String[] {"a", "b"});
	}

	@Test
	public void testGetPortletCSRFWhitelistActionsWithBundle() {
		Set<String> portletCSRFWhitelistActions =
			AuthTokenWhitelistUtil.getPortletCSRFWhitelistActions();

		Assert.assertTrue(
			"The URL " +
				TestAuthTokenIgnoreActions.TEST_AUTH_TOKEN_IGNORE_ACTION_URL +
				" can't be found in the list portletCSRFWhitelistActions",
			portletCSRFWhitelistActions.contains(
				TestAuthTokenIgnoreActions.TEST_AUTH_TOKEN_IGNORE_ACTION_URL));

		Assert.assertTrue(
			"The URL a can't be found in the list portletCSRFWhitelistActions",
			portletCSRFWhitelistActions.contains("a"));
	}

	@Test
	public void testGetPortletCSRFWhitelistActionsWithRegistry() {
		Set<String> portletCSRFWhitelistActions =
			AuthTokenWhitelistUtil.getPortletCSRFWhitelistActions();

		Assert.assertTrue(
			"The URL a can't be found in the list portletCSRFWhitelistActions",
			portletCSRFWhitelistActions.contains("a"));

		Assert.assertTrue(
			"The URL a can't be found in the list portletCSRFWhitelistActions",
			portletCSRFWhitelistActions.contains("b"));
	}

}