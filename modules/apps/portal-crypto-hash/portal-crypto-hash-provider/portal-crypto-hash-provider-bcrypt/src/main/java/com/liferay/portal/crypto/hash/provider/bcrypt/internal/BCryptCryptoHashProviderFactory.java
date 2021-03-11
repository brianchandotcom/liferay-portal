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

package com.liferay.portal.crypto.hash.provider.bcrypt.internal;

import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderFactory;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderResponse;
import com.liferay.portal.kernel.util.MapUtil;

import java.nio.charset.StandardCharsets;

import java.util.Map;

import jodd.crypt.BCrypt;

/**
 * @author Arthur Chan
 */
public class BCryptCryptoHashProviderFactory
	implements CryptoHashProviderFactory {

	@Override
	public CryptoHashProvider create(
			Map<String, ?> cryptoHashProviderProperties)
		throws CryptoHashException {

		if ((cryptoHashProviderProperties == null) ||
			cryptoHashProviderProperties.isEmpty()) {

			return new BCryptCryptoHashProvider();
		}

		return new BCryptCryptoHashProvider(cryptoHashProviderProperties);
	}

	@Override
	public String getCryptoHashProviderFactoryName() {
		return _CRYPTO_HASH_PROVIDER_FACTORY_NAME;
	}

	private static final String _CRYPTO_HASH_PROVIDER_FACTORY_NAME = "BCrypt";

	private static class BCryptCryptoHashProvider
		implements CryptoHashProvider {

		public static final String ROUNDS = "rounds";

		public BCryptCryptoHashProvider() {
			_rounds = _DEFAULT_ROUNDS;
		}

		public BCryptCryptoHashProvider(
			Map<String, ?> cryptoHashProviderProperties) {

			_rounds = MapUtil.getInteger(cryptoHashProviderProperties, ROUNDS);
		}

		@Override
		public CryptoHashProviderResponse generate(byte[] salt, byte[] input) {
			String hashedPassword = BCrypt.hashpw(
				new String(input, StandardCharsets.US_ASCII),
				new String(salt, StandardCharsets.US_ASCII));

			return new CryptoHashProviderResponse(
				_CRYPTO_HASH_PROVIDER_FACTORY_NAME, null,
				hashedPassword.getBytes(StandardCharsets.US_ASCII));
		}

		@Override
		public byte[] generateSalt() {
			String salt = BCrypt.gensalt(_rounds);

			return salt.getBytes(StandardCharsets.US_ASCII);
		}

		private static final int _DEFAULT_ROUNDS = 10;

		private final int _rounds;

	}

}