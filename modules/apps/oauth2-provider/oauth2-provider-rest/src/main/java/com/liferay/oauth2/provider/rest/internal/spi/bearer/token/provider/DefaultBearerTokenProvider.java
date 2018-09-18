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

import com.liferay.oauth2.provider.rest.internal.spi.bearer.token.provider.configuration.DefaultBearerTokenProviderConfiguration;
import com.liferay.oauth2.provider.rest.spi.bearer.token.provider.BearerTokenProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.security.SecureRandomUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.rest.internal.spi.bearer.token.provider.configuration.DefaultBearerTokenProviderConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {"name=default", "token.format=opaque"},
	service = BearerTokenProvider.class
)
public class DefaultBearerTokenProvider implements BearerTokenProvider {

	@Override
	public boolean isValid(AccessToken accessToken) {
		return isValid(accessToken.getExpiresIn(), accessToken.getIssuedAt());
	}

	@Override
	public boolean isValid(RefreshToken refreshToken) {
		return isValid(refreshToken.getExpiresIn(), refreshToken.getIssuedAt());
	}

	@Override
	public void onBeforeCreate(AccessToken accessToken) {
		String tokenKey = generateTokenKey(
			_defaultBearerTokenProviderConfiguration.accessTokenKeyByteSize());

		accessToken.setTokenKey(tokenKey);

		accessToken.setExpiresIn(
			_defaultBearerTokenProviderConfiguration.accessTokenExpiresIn());
	}

	@Override
	public void onBeforeCreate(RefreshToken refreshToken) {
		String tokenKey = generateTokenKey(
			_defaultBearerTokenProviderConfiguration.refreshTokenKeyByteSize());

		refreshToken.setTokenKey(tokenKey);

		refreshToken.setExpiresIn(
			_defaultBearerTokenProviderConfiguration.refreshTokenExpiresIn());
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_defaultBearerTokenProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				DefaultBearerTokenProviderConfiguration.class, properties);
	}

	protected String generateTokenKey(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Token key size is less than 0");
		}

		int count = (int)Math.ceil((double)size / 8);

		byte[] buffer = new byte[count * 8];

		for (int i = 0; i < count; i++) {
			BigEndianCodec.putLong(buffer, i * 8, SecureRandomUtil.nextLong());
		}

		StringBundler sb = new StringBundler(size);

		for (int i = 0; i < size; i++) {
			sb.append(Integer.toHexString(0xFF & buffer[i]));
		}

		return sb.toString();
	}

	protected boolean isValid(long expiresIn, long issuedAt) {
		long expiresInMillis = expiresIn * 1000;

		if (expiresInMillis < 0) {
			return false;
		}

		long issuedAtMillis = issuedAt * 1000;

		if (issuedAtMillis > System.currentTimeMillis()) {
			return false;
		}

		if ((issuedAtMillis + expiresInMillis) < System.currentTimeMillis()) {
			return false;
		}

		return true;
	}

	private DefaultBearerTokenProviderConfiguration
		_defaultBearerTokenProviderConfiguration;

}