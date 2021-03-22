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

package com.liferay.portal.crypto.hash.provider.message.digest.internal;

import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderFactory;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderResponse;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 */
@Component(
	property = "configuration.pid=com.liferay.portal.crypto.hash.provider.message.digest.internal.configuration.MessageDigestCryptoHashProviderConfiguration",
	service = CryptoHashProviderFactory.class
)
public class MessageDigestCryptoHashProviderFactory
	implements CryptoHashProviderFactory {

	@Override
	public CryptoHashProvider create(
			Map<String, ?> cryptoHashProviderProperties)
		throws CryptoHashException {

		try {
			if (cryptoHashProviderProperties == null) {
				return new MessageDigestCryptoHashProvider(
					Collections.emptyMap());
			}

			return new MessageDigestCryptoHashProvider(
				cryptoHashProviderProperties);
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new CryptoHashException(noSuchAlgorithmException);
		}
	}

	@Override
	public String getCryptoHashProviderFactoryName() {
		return _CRYPTO_HASH_PROVIDER_FACTORY_NAME;
	}

	private static final String _CRYPTO_HASH_PROVIDER_FACTORY_NAME =
		"MessageDigest";

	private static class MessageDigestCryptoHashProvider
		implements CryptoHashProvider {

		public MessageDigestCryptoHashProvider(
				Map<String, ?> cryptoHashProviderProperties)
			throws NoSuchAlgorithmException {

			_cryptoHashProviderProperties = cryptoHashProviderProperties;

			_messageDigest = MessageDigest.getInstance(
				MapUtil.getString(
					cryptoHashProviderProperties, "message.digest.algorithm",
					"SHA-256"));
			_saltSize = MapUtil.getInteger(
				cryptoHashProviderProperties, "message.digest.salt.size", 32);
		}

		@Override
		public CryptoHashProviderResponse generate(byte[] salt, byte[] input) {
			return new CryptoHashProviderResponse(
				_CRYPTO_HASH_PROVIDER_FACTORY_NAME,
				Collections.unmodifiableMap(_cryptoHashProviderProperties),
				_messageDigest.digest(ArrayUtil.append(salt, input)));
		}

		@Override
		public byte[] generateSalt() {
			byte[] salt = new byte[_saltSize];

			for (int i = 0; i < salt.length; ++i) {
				salt[i] = SecureRandomUtil.nextByte();
			}

			return salt;
		}

		private final Map<String, ?> _cryptoHashProviderProperties;
		private final MessageDigest _messageDigest;
		private final int _saltSize;

	}

}