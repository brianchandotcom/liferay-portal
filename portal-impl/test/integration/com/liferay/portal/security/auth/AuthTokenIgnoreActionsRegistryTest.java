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

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class AuthTokenIgnoreActionsRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.authtokenignoreactions"));

	@After
	public void tearDown() {
		AuthTokenIgnoreActionsRegistry.unregister();
	}

	@Test
	public void testRegisterBundleProperty() {
		Set<String> portletCSRFWhitelistActions =
			AuthTokenIgnoreActionsRegistry.getAuthTokenIgnoreActions();

		Assert.assertTrue(
			"The property has not being registered",
			portletCSRFWhitelistActions.contains(
				TestAuthTokenIgnoreActions.TEST_AUTH_TOKEN_IGNORE_ACTION_URL));
	}

	@Test
	public void testRegisterWithMultipleProperty() {
		AuthTokenIgnoreActionsRegistry.register("property1");
		AuthTokenIgnoreActionsRegistry.register("property2");

		Set<String> portletCSRFWhitelistActions =
			AuthTokenIgnoreActionsRegistry.getAuthTokenIgnoreActions();

		Assert.assertTrue(
			"The property has not being registered",
			portletCSRFWhitelistActions.contains("property1"));

		Assert.assertTrue(
			"The property has not being registered",
			portletCSRFWhitelistActions.contains("property2"));
	}

	@Test
	public void testRegisterWithOneProperty() {
		AuthTokenIgnoreActionsRegistry.register("property1");

		Set<String> portletCSRFWhitelistActions =
			AuthTokenIgnoreActionsRegistry.getAuthTokenIgnoreActions();

		Assert.assertTrue(
			"The property has not being registered",
			portletCSRFWhitelistActions.contains("property1"));
	}

	@Test
	public void testUnregisterAll() {
		AuthTokenIgnoreActionsRegistry.register("property1");
		AuthTokenIgnoreActionsRegistry.register("property2");
		AuthTokenIgnoreActionsRegistry.register("property3");

		AuthTokenIgnoreActionsRegistry.unregister();

		Set<String> portletCSRFWhitelistActions =
			AuthTokenIgnoreActionsRegistry.getAuthTokenIgnoreActions();

		Assert.assertFalse(
			"The property has not being unregistered",
			portletCSRFWhitelistActions.contains("property1"));

		Assert.assertFalse(
			"The property has not being unregistered",
			portletCSRFWhitelistActions.contains("property2"));

		Assert.assertFalse(
			"The property has not being unregistered",
			portletCSRFWhitelistActions.contains("property3"));
	}

	@Test
	public void testUnregisterWithMultiplesPropertys() {
		AuthTokenIgnoreActionsRegistry.register("property1");
		AuthTokenIgnoreActionsRegistry.register("property2");
		AuthTokenIgnoreActionsRegistry.register("property3");

		AuthTokenIgnoreActionsRegistry.unregister("property1");

		Set<String> portletCSRFWhitelistActions =
			AuthTokenIgnoreActionsRegistry.getAuthTokenIgnoreActions();

		Assert.assertFalse(
			"The property has not being unregistered",
			portletCSRFWhitelistActions.contains("property1"));

		Assert.assertTrue(
			"The property has not being registered",
			portletCSRFWhitelistActions.contains("property2"));

		Assert.assertTrue(
			"The property has not being registered",
			portletCSRFWhitelistActions.contains("property3"));
	}

	@Test
	public void testUnregisterWithOneProperty() {
		AuthTokenIgnoreActionsRegistry.register("property1");

		AuthTokenIgnoreActionsRegistry.unregister("property1");

		Set<String> portletCSRFWhitelistActions =
			AuthTokenIgnoreActionsRegistry.getAuthTokenIgnoreActions();

		Assert.assertFalse(
			"The property has not being unregistered",
			portletCSRFWhitelistActions.contains("property1"));
	}

}