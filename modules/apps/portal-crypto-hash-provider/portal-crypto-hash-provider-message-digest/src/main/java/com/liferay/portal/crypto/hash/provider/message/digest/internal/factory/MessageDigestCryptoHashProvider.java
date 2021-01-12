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

package com.liferay.portal.crypto.hash.provider.message.digest.internal.factory;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.crypto.hash.provider.message.digest.internal.configuration.MessageDigestCryptoHashProviderConfiguration;
import com.liferay.portal.crypto.hash.provider.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.provider.spi.CryptoHashProviderResponse;
import com.liferay.portal.crypto.hash.provider.spi.salt.VariableSizeSaltProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Map;

/**
 * @author Arthur Chan
 */
public class MessageDigestCryptoHashProvider
	implements CryptoHashProvider, VariableSizeSaltProvider {

	public MessageDigestCryptoHashProvider(
			String cryptoHashProviderName,
			Map<String, ?> cryptoHashProviderProperties)
		throws NoSuchAlgorithmException {

		_cryptoHashProviderName = cryptoHashProviderName;
		_cryptoHashProviderProperties = cryptoHashProviderProperties;

		_messageDigest = MessageDigest.getInstance(cryptoHashProviderName);

		_messageDigestCryptoHashProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				MessageDigestCryptoHashProviderConfiguration.class,
				cryptoHashProviderProperties);
	}

	@Override
	public CryptoHashProviderResponse generate(
		byte[] pepper, byte[] salt, byte[] input) {

		if (pepper == null) {
			pepper = new byte[0];
		}

		if (salt == null) {
			salt = new byte[0];
		}

		byte[] bytes = new byte[pepper.length + salt.length + input.length];

		System.arraycopy(pepper, 0, bytes, 0, pepper.length);
		System.arraycopy(salt, 0, bytes, pepper.length, salt.length);
		System.arraycopy(
			input, 0, bytes, pepper.length + salt.length, input.length);

		return new CryptoHashProviderResponse(
			_messageDigest.digest(bytes), salt, _cryptoHashProviderName,
			_cryptoHashProviderProperties);
	}

	@Override
	public byte[] generateSalt() {
		return generateSalt(
			_messageDigestCryptoHashProviderConfiguration.saltSize());
	}

	private final String _cryptoHashProviderName;
	private final Map<String, ?> _cryptoHashProviderProperties;
	private final MessageDigest _messageDigest;
	private final MessageDigestCryptoHashProviderConfiguration
		_messageDigestCryptoHashProviderConfiguration;

}