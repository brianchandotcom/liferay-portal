/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.petra.function.UnsafeConsumer;
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
	public void testWhenEmailAndEmailsAreNotInJWTClaimSet() throws Exception {
		String jsonRequest = "{\"sub\":\"subject\",\"name\": \"test_account\"}";

		Map<String, Object> claims = _getEmailClaimFromJWT(jsonRequest);

		Assert.assertNull(claims.get("email"));
	}

	@Test
	public void testWhenEmailIsInJWTClaimSet() throws Exception {
		String jsonRequest =
			"{\"sub\":\"subject\",\"name\": \"test_account\",\"email\": " +
				"\"exists@test.com\"}";

		Map<String, Object> claims = _getEmailClaimFromJWT(jsonRequest);

		Assert.assertEquals("exists@test.com", claims.get("email"));
	}

	private Map<String, Object> _getEmailClaimFromJWT(String jsonRequest)
		throws Exception {

		JWT mockJWT = Mockito.mock(JWT.class);

		_mockJWTClaimSet(jsonRequest, mockJWT);

		return _openIdConnectAuthenticationHandlerImpl.
			requestUserInfoClaimsFromTokens(mockJWT);
	}

	private void _mockJWTClaimSet(String jsonRequest, JWT mockJWT)
		throws Exception {

		JWTClaimsSet jwtClaimsSet = JWTClaimsSet.parse(jsonRequest);

		Mockito.when(
			mockJWT.getJWTClaimsSet()
		).thenReturn(
			jwtClaimsSet
		);
	}

	private MockHttpServletRequest _mockHttpServletRequest;
	private MockHttpServletResponse _mockHttpServletResponse;
	private MockHttpSession _mockHttpSession;
	private OpenIdConnectAuthenticationHandlerImpl
		_openIdConnectAuthenticationHandlerImpl;
	private UnsafeConsumer<Long, Exception> _userIdUnsafeConsumer;

}