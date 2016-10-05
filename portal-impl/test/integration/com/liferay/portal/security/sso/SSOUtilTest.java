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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.PrefsPropsUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_setLoginDialogDisable(1, true);
		_setLoginDialogDisable(2, false);
	}

	@After
	public void tearDown() throws Exception {
		for (Entry<Long, String> entry : _oldLoginDialogDisableds.entrySet()) {
			PortletPreferences portletPreferences =
				PrefsPropsUtil.getPreferences(entry.getKey());

			String disabledString = entry.getValue();

			if (disabledString == null) {
				portletPreferences.reset(PropsKeys.LOGIN_DIALOG_DISABLED);
			}
			else {
				portletPreferences.setValue(
					PropsKeys.LOGIN_DIALOG_DISABLED, disabledString);
			}

			portletPreferences.store();
		}
	}

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

	private void _setLoginDialogDisable(long companyId, boolean disabled)
		throws Exception {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId);

		_oldLoginDialogDisableds.put(
			companyId,
			portletPreferences.getValue(PropsKeys.LOGIN_DIALOG_DISABLED, null));

		portletPreferences.setValue(
			PropsKeys.LOGIN_DIALOG_DISABLED, String.valueOf(disabled));

		portletPreferences.store();
	}

	private final Map<Long, String> _oldLoginDialogDisableds = new HashMap<>();

}