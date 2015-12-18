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
import com.liferay.portal.security.auth.bundle.authtokenwhitelistutil.TestAuthTokenIgnoreActions;
import com.liferay.portal.security.auth.bundle.authtokenwhitelistutil.TestAuthTokenIgnoreOrigins;
import com.liferay.portal.security.auth.bundle.authtokenwhitelistutil.TestAuthTokenIgnorePortlets;
import com.liferay.portal.security.auth.bundle.authtokenwhitelistutil.TestPortalAddDefaultResourceCheckWhitelist;
import com.liferay.portal.security.auth.bundle.authtokenwhitelistutil.TestPortalAddDefaultResourceCheckWhitelistActions;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.PropsValues;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 * @author Tomas Polesovsky
 */
public class AuthTokenWhitelistUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.authtokenwhitelistutil"));

	@Test
	public void testGetPortletCSRFWhitelistActionsFromBundle() {
		Set<String> portletCSRFWhitelistActions =
			AuthTokenWhitelistUtil.getPortletCSRFWhitelistActions();

		Assert.assertTrue(
			portletCSRFWhitelistActions.contains(
				TestAuthTokenIgnoreActions.TEST_AUTH_TOKEN_IGNORE_ACTION_URL));
	}

	@Test
	public void testGetPortletCSRFWhitelistActionsFromPortalProperties() {
		Set<String> portletCSRFWhitelistActions =
			AuthTokenWhitelistUtil.getPortletCSRFWhitelistActions();

		for (String authTokenIgnoreAction :
				PropsValues.AUTH_TOKEN_IGNORE_ACTIONS) {

			Assert.assertTrue(
				portletCSRFWhitelistActions.contains(authTokenIgnoreAction));
		}
	}

	@Test
	public void testGetPortletCSRFWhitelistFromBundle() {
		Set<String> portletCSRFWhitelist =
			AuthTokenWhitelistUtil.getPortletCSRFWhitelist();

		Assert.assertTrue(
			portletCSRFWhitelist.contains(
				TestAuthTokenIgnorePortlets.
					TEST_AUTH_TOKEN_IGNORE_PORTLETS_URL));
	}

	@Test
	public void testGetPortletCSRFWhitelistFromProperties() {
		Set<String> portletCSRFWhitelist =
			AuthTokenWhitelistUtil.getPortletCSRFWhitelist();

		for (String authTokenIgnoreAction :
				PropsValues.AUTH_TOKEN_IGNORE_PORTLETS) {

			Assert.assertTrue(
				portletCSRFWhitelist.contains(authTokenIgnoreAction));
		}
	}

	@Test
	public void testGetPortletInvocationWhitelistActionsFromBundle() {
		Set<String> portletInvocationWhitelistActions =
			AuthTokenWhitelistUtil.getPortletInvocationWhitelistActions();

		String action =
			TestPortalAddDefaultResourceCheckWhitelistActions.
				TEST_PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS_URL;

		Assert.assertTrue(portletInvocationWhitelistActions.contains(action));
	}

	@Test
	public void testGetPortletInvocationWhitelistActionsFromPortalProperties() {
		Set<String> portletInvocationWhitelistActions =
			AuthTokenWhitelistUtil.getPortletInvocationWhitelistActions();

		String[] actions =
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_ACTIONS;

		for (String action : actions) {
			Assert.assertTrue(
				portletInvocationWhitelistActions.contains(action));
		}
	}

	@Test
	public void testGetPortletInvocationWhitelistFromBundle() {
		Set<String> portletInvocationWhitelist =
			AuthTokenWhitelistUtil.getPortletInvocationWhitelist();

		String action =
			TestPortalAddDefaultResourceCheckWhitelist.
				TEST_PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST_URL;

		Assert.assertTrue(portletInvocationWhitelist.contains(action));
	}

	@Test
	public void testGetPortletInvocationWhitelistFromPortalProperties() {
		Set<String> portletInvocationWhitelist =
			AuthTokenWhitelistUtil.getPortletInvocationWhitelist();

		String[] actions =
			PropsValues.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_WHITELIST;

		for (String action : actions) {
			Assert.assertTrue(portletInvocationWhitelist.contains(action));
		}
	}

	@Test
	public void testIsCSRFOrigintWhitelistedFromBundle() {
		Assert.assertTrue(
			AuthTokenWhitelistUtil.isCSRFOrigintWhitelisted(
				0,
				TestAuthTokenIgnoreOrigins.TEST_AUTH_TOKEN_IGNORE_ORIGINS_URL));
	}

	@Test
	public void testIsCSRFOrigintWhitelistedFromPortalProperties() {
		String[] origins = PropsValues.AUTH_TOKEN_IGNORE_ORIGINS;

		for (String origin : origins) {
			Assert.assertTrue(
				AuthTokenWhitelistUtil.isCSRFOrigintWhitelisted(0, origin));
		}
	}

}