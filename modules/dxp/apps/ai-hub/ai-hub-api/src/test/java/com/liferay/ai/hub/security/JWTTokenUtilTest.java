/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.security;

import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public class JWTTokenUtilTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGenerateToken() throws Exception {
		String token = JWTTokenUtil.generateToken(
			_COMPANY_ID, TimeUnit.MINUTES.toMillis(1), _ISSUER, _USER_ID);

		Assert.assertNotNull(token);
		Assert.assertFalse(token.isEmpty());

		SignedJWT signedJWT = SignedJWT.parse(token);

		JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

		Assert.assertEquals(_COMPANY_ID, jwtClaimsSet.getClaim("companyId"));
		Assert.assertEquals(_ISSUER, jwtClaimsSet.getIssuer());
		Assert.assertEquals(
			String.valueOf(_USER_ID), jwtClaimsSet.getSubject());
	}

	@Test
	public void testGetUserId() {
		String token = JWTTokenUtil.generateToken(
			_COMPANY_ID, TimeUnit.MINUTES.toMillis(1), _ISSUER, _USER_ID);

		Assert.assertEquals(_USER_ID, JWTTokenUtil.getUserId(token));
	}

	@Test
	public void testGetUserIdWithInvalidToken() throws Exception {
		String token = JWTTokenUtil.generateToken(
			_COMPANY_ID, TimeUnit.MINUTES.toMillis(1), _ISSUER, _USER_ID);

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					JWTTokenUtil.class, "_SECRET", _SECRET)) {

			_testGetUserIdWithInvalidToken("Invalid JWT signature", token);
		}

		_testGetUserIdWithInvalidToken(
			"Invalid JWT signature",
			token.substring(0, token.length() - 5) + "abcde");
		_testGetUserIdWithInvalidToken(
			"The JWT token has been expired",
			JWTTokenUtil.generateToken(_COMPANY_ID, 0, _ISSUER, _USER_ID));
		_testGetUserIdWithInvalidToken(
			"Unable to parse and verify the JWT token",
			RandomTestUtil.randomString());
	}

	private void _testGetUserIdWithInvalidToken(
		String expectedExceptionMessage, String token) {

		try {
			JWTTokenUtil.getUserId(token);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				expectedExceptionMessage, exception.getMessage());
		}
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _ISSUER = RandomTestUtil.randomString();

	private static final byte[] _SECRET;

	private static final long _USER_ID = RandomTestUtil.randomLong();

	static {
		int sha256BlockSize = 64;

		byte[] secret = new byte[sha256BlockSize];

		for (int i = 0; i < secret.length; i++) {
			secret[i] = SecureRandomUtil.nextByte();
		}

		_SECRET = secret;
	}

}