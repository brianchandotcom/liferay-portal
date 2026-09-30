/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jorge García Jiménez
 */
public class OpenIdConnectRequestParametersUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsUpstreamTokenForwardingAllowed() throws Exception {
		Assert.assertFalse(
			OpenIdConnectRequestParametersUtil.isUpstreamTokenForwardingAllowed(
				JSONObjectUtils.parse("{}")));
		Assert.assertFalse(
			OpenIdConnectRequestParametersUtil.isUpstreamTokenForwardingAllowed(
				JSONObjectUtils.parse(
					"{\"allow_upstream_token_forwarding\": false}")));
		Assert.assertTrue(
			OpenIdConnectRequestParametersUtil.isUpstreamTokenForwardingAllowed(
				JSONObjectUtils.parse(
					"{\"allow_upstream_token_forwarding\": true}")));
	}

	@Test(expected = ParseException.class)
	public void testIsUpstreamTokenForwardingAllowedWithInvalidValue()
		throws Exception {

		OpenIdConnectRequestParametersUtil.isUpstreamTokenForwardingAllowed(
			JSONObjectUtils.parse(
				"{\"allow_upstream_token_forwarding\": \"yes\"}"));
	}

}