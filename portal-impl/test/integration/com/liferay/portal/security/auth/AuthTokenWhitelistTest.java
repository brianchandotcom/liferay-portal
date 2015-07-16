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
import com.liferay.portal.util.PropsValues;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class AuthTokenWhitelistTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.authtokenignoreactions"));

	@Test
	public void testGetPortletCSRFWhitelistActionsDefinedInPortalProperties() {
		Set<String> portletCSRFWhitelistActions =
			AuthTokenWhitelistUtil.getPortletCSRFWhitelistActions();

		for (String authTokenIgnoreAction :
				PropsValues.AUTH_TOKEN_IGNORE_ACTIONS) {

			Assert.assertTrue(
				"The URL a can't be found in " + portletCSRFWhitelistActions,
				portletCSRFWhitelistActions.contains(authTokenIgnoreAction));
		}
	}

	@Test
	public void testGetPortletCSRFWhitelistActionsWithBundle() {
		Set<String> portletCSRFWhitelistActions =
			AuthTokenWhitelistUtil.getPortletCSRFWhitelistActions();

		Assert.assertTrue(
			"The URL " +
				TestAuthTokenIgnoreActions.TEST_AUTH_TOKEN_IGNORE_ACTION_URL +
				" can't be found in " + portletCSRFWhitelistActions,
			portletCSRFWhitelistActions.contains(
				TestAuthTokenIgnoreActions.TEST_AUTH_TOKEN_IGNORE_ACTION_URL));
	}

}