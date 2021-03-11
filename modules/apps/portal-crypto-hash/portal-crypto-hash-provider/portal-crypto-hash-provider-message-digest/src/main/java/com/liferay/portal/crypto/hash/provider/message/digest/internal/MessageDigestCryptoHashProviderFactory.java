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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Collections;
import java.util.Map;

/**
 * @author Arthur Chan
 */
public class MessageDigestCryptoHashProviderFactory
	implements CryptoHashProviderFactory {

	@Override
	public CryptoHashProvider create(
			Map<String, ?> cryptoHashProviderProperties)
		throws CryptoHashException {

		try {
			if ((cryptoHashProviderProperties == null) ||
				cryptoHashProviderProperties.isEmpty()) {

				return new MessageDigestCryptoHashProvider();
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

		public static final String ALGORITHM = "algorithm";

		public MessageDigestCryptoHashProvider()
			throws NoSuchAlgorithmException {

			_messageDigest = MessageDigest.getInstance(_DEFAULT_ALGORITHM);
		}

		public MessageDigestCryptoHashProvider(
				Map<String, ?> cryptoHashProviderProperties)
			throws NoSuchAlgorithmException {

			_messageDigest = MessageDigest.getInstance(
				MapUtil.getString(cryptoHashProviderProperties, ALGORITHM));
		}

		@Override
		public CryptoHashProviderResponse generate(byte[] salt, byte[] input) {
			return new CryptoHashProviderResponse(
				_CRYPTO_HASH_PROVIDER_FACTORY_NAME,
				Collections.singletonMap(
					"algorithm", _messageDigest.getAlgorithm()),
				_messageDigest.digest(ArrayUtil.append(salt, input)));
		}

		private static final String _DEFAULT_ALGORITHM = "SHA-256";

		private final MessageDigest _messageDigest;

	}

}