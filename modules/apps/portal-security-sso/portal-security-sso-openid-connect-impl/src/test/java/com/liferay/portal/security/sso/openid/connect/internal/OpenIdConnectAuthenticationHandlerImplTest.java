/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Tamas Biro
 */
public class OpenIdConnectAuthenticationHandlerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_openIdConnectAuthenticationHandlerImpl =
			new OpenIdConnectAuthenticationHandlerImpl();
		_mockHttpServletRequest = new MockHttpServletRequest();
		_mockHttpServletResponse = new MockHttpServletResponse();
		_mockHttpSession = new MockHttpSession();
		_userIdUnsafeConsumer = new UnsafeConsumer<Long, Exception>() {

			@Override
			public void accept(Long aLong) {
			}

		};
	}

	@Test
	public void testWhenEmailIsInJWTClaimSet() throws Exception {
		_jsonObject = JSONUtil.put(
			"email", "exists@test.com"
		).put(
			"name", "test_account"
		).put(
			"sub", "subject"
		);

		Map<String, Object> claims = _processClaimSet(_jsonObject.toString());

		Assert.assertEquals("exists@test.com", claims.get("email"));
	}

	@Test
	public void testWhenEmailIsNotInJWTClaimSet() throws Exception {
		_jsonObject = JSONUtil.put(
			"name", "test_account"
		).put(
			"sub", "subject"
		);

		Map<String, Object> claims = _processClaimSet(_jsonObject.toString());

		Assert.assertNull(claims.get("email"));
	}

	private Map<String, Object> _processClaimSet(String claimSetJSON)
		throws Exception {

		JWT mockJWT = Mockito.mock(JWT.class);

		Mockito.when(
			mockJWT.getJWTClaimsSet()
		).thenReturn(
			JWTClaimsSet.parse(claimSetJSON)
		);

		return _openIdConnectAuthenticationHandlerImpl.getUserInfoClaims(
			mockJWT);
	}

	private JSONObject _jsonObject;
	private MockHttpServletRequest _mockHttpServletRequest;
	private MockHttpServletResponse _mockHttpServletResponse;
	private MockHttpSession _mockHttpSession;
	private OpenIdConnectAuthenticationHandlerImpl
		_openIdConnectAuthenticationHandlerImpl;
	private UnsafeConsumer<Long, Exception> _userIdUnsafeConsumer;

}