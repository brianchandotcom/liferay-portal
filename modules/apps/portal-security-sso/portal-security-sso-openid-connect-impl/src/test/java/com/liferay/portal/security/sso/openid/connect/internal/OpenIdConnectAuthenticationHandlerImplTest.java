/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Tamas Biro
 */
public class OpenIdConnectAuthenticationHandlerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetUserInfoClaims() throws Exception {
		Map<String, Object> claims = _getUserInfoClaims(
			JSONUtil.put(
				"email", "exists@test.com"
			).put(
				"name", "test_account"
			).put(
				"sub", "subject"
			).toString());

		Assert.assertEquals("exists@test.com", claims.get("email"));

		claims = _getUserInfoClaims(
			JSONUtil.put(
				"name", "test_account"
			).put(
				"sub", "subject"
			).toString());

		Assert.assertNull(claims.get("email"));
	}

	private Map<String, Object> _getUserInfoClaims(String claimSetJSON)
		throws Exception {

		OpenIdConnectAuthenticationHandlerImpl
			openIdConnectAuthenticationHandlerImpl =
				new OpenIdConnectAuthenticationHandlerImpl();

		JWT mockJWT = Mockito.mock(JWT.class);

		Mockito.when(
			mockJWT.getJWTClaimsSet()
		).thenReturn(
			JWTClaimsSet.parse(claimSetJSON)
		);

		return openIdConnectAuthenticationHandlerImpl.getUserInfoClaims(
			mockJWT);
	}

}