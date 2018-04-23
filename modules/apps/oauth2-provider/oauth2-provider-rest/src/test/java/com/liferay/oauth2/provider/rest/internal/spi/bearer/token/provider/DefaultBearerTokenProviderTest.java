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

package com.liferay.oauth2.provider.rest.internal.spi.bearer.token.provider;

import com.liferay.oauth2.provider.rest.spi.bearer.token.provider.BearerTokenProvider;
import com.liferay.portal.kernel.security.SecureRandomUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Tomas Polesovsky
 */
@PrepareForTest(SecureRandomUtil.class)
@RunWith(PowerMockRunner.class)
public class DefaultBearerTokenProviderTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		Map<String, Object> properties = new HashMap<>();

		properties.put("access.token.expires.in", _ACCESS_TOKEN_EXPIRES_IN);
		properties.put(
			"access.token.key.byte.size", _ACCESS_TOKEN_KEY_BYTE_SIZE);
		properties.put("refresh.token.expires.in", _REFRESH_TOKEN_EXPIRES_IN);
		properties.put(
			"refresh.token.key.byte.size", _REFRESH_TOKEN_KEY_BYTE_SIZE);

		_defaultBearerTokenProvider = new DefaultBearerTokenProvider();

		_defaultBearerTokenProvider.activate(properties);

		mockStatic(SecureRandomUtil.class);

		when(
			SecureRandomUtil.nextLong()
		).thenReturn(
			_TOKEN_KEY_LONG
		);
	}

	@Test
	public void testGenerateTokenKey() {
		Assert.assertEquals(
			"", _defaultBearerTokenProvider.generateTokenKey(0));
		Assert.assertEquals(
			_TOKEN_KEY_STRING_32_BYTES_HEX.substring(0, 4),
			_defaultBearerTokenProvider.generateTokenKey(2));
		Assert.assertEquals(
			_TOKEN_KEY_STRING_32_BYTES_HEX.substring(0, 16),
			_defaultBearerTokenProvider.generateTokenKey(8));
		Assert.assertEquals(
			_TOKEN_KEY_STRING_32_BYTES_HEX.substring(0, 20),
			_defaultBearerTokenProvider.generateTokenKey(10));
		Assert.assertEquals(
			_TOKEN_KEY_STRING_32_BYTES_HEX + _TOKEN_KEY_STRING_32_BYTES_HEX,
			_defaultBearerTokenProvider.generateTokenKey(64));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateTokenKeyWithNegativeKeySize() {
		_defaultBearerTokenProvider.generateTokenKey(-1);
	}

	@Test
	public void testIsValidAccessToken() {
		long issuedAtNow = System.currentTimeMillis() / 1000;

		Assert.assertTrue(
			_defaultBearerTokenProvider.isValid(
				generateAccessToken(_ACCESS_TOKEN_EXPIRES_IN, issuedAtNow)));
		Assert.assertFalse(
			_defaultBearerTokenProvider.isValid(
				generateAccessToken(-1, issuedAtNow)));
		Assert.assertFalse(
			_defaultBearerTokenProvider.isValid(
				generateAccessToken(
					_ACCESS_TOKEN_EXPIRES_IN,
					issuedAtNow - (_ACCESS_TOKEN_EXPIRES_IN + 1))));
		Assert.assertFalse(
			_defaultBearerTokenProvider.isValid(
				generateAccessToken(
					_ACCESS_TOKEN_EXPIRES_IN,
					issuedAtNow + _ACCESS_TOKEN_EXPIRES_IN)));
	}

	@Test
	public void testIsValidRefreshToken() {
		long issuedAtNow = System.currentTimeMillis() / 1000;

		Assert.assertTrue(
			_defaultBearerTokenProvider.isValid(
				generateRefreshToken(_REFRESH_TOKEN_EXPIRES_IN, issuedAtNow)));
		Assert.assertFalse(
			_defaultBearerTokenProvider.isValid(
				generateRefreshToken(-1, issuedAtNow)));
		Assert.assertFalse(
			_defaultBearerTokenProvider.isValid(
				generateRefreshToken(
					_REFRESH_TOKEN_EXPIRES_IN,
					issuedAtNow - (_REFRESH_TOKEN_EXPIRES_IN + 1))));
		Assert.assertFalse(
			_defaultBearerTokenProvider.isValid(
				generateRefreshToken(
					_REFRESH_TOKEN_EXPIRES_IN,
					issuedAtNow + _REFRESH_TOKEN_EXPIRES_IN)));
	}

	@Test
	public void testOnBeforeCreateAccessToken() {
		BearerTokenProvider.AccessToken accessToken = generateAccessToken(0, 0);

		_defaultBearerTokenProvider.onBeforeCreate(accessToken);

		Assert.assertEquals(
			accessToken.getExpiresIn(), _ACCESS_TOKEN_EXPIRES_IN);
		Assert.assertEquals(
			_TOKEN_KEY_STRING_32_BYTES_HEX, accessToken.getTokenKey());
	}

	@Test
	public void testOnBeforeCreateRefreshToken() {
		BearerTokenProvider.RefreshToken refreshToken = generateRefreshToken(
			0, 0);

		_defaultBearerTokenProvider.onBeforeCreate(refreshToken);

		Assert.assertEquals(
			refreshToken.getExpiresIn(), _REFRESH_TOKEN_EXPIRES_IN);
		Assert.assertEquals(
			_TOKEN_KEY_STRING_32_BYTES_HEX, refreshToken.getTokenKey());
	}

	protected BearerTokenProvider.AccessToken generateAccessToken(
		long expiresIn, long issuedAt) {

		return new BearerTokenProvider.AccessToken(
			null, null, null, expiresIn, null, null, null, issuedAt, null, null,
			null, null, null, null, null, null, 0, null);
	}

	protected BearerTokenProvider.RefreshToken generateRefreshToken(
		long expiresIn, long issuedAt) {

		return new BearerTokenProvider.RefreshToken(
			null, null, null, expiresIn, null, issuedAt, null, null, null, 0,
			null);
	}

	private static final int _ACCESS_TOKEN_EXPIRES_IN = 600;

	private static final int _ACCESS_TOKEN_KEY_BYTE_SIZE = 32;

	private static final int _REFRESH_TOKEN_EXPIRES_IN = 604800;

	private static final int _REFRESH_TOKEN_KEY_BYTE_SIZE = 32;

	private static final long _TOKEN_KEY_LONG = 0xdecadefeededbabeL;

	private static final String _TOKEN_KEY_STRING_32_BYTES_HEX =
		"decadefeededbabedecadefeededbabedecadefeededbabedecadefeededbabe";

	private DefaultBearerTokenProvider _defaultBearerTokenProvider;

}