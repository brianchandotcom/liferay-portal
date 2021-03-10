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

import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderResponse;
import com.liferay.portal.kernel.util.MapUtil;

import java.nio.charset.StandardCharsets;

import java.util.Map;

import jodd.crypt.BCrypt;

/**
 * @author Arthur Chan
 */
public class BCryptCryptoHashProvider implements CryptoHashProvider {

	public static final String ROUNDS = "rounds";

	public BCryptCryptoHashProvider(String cryptoHashProviderFactoryName) {
		_cryptoHashProviderFactoryName = cryptoHashProviderFactoryName;

		_rounds = _DEFAULT_ROUNDS;
	}

	public BCryptCryptoHashProvider(
		String cryptoHashProviderFactoryName,
		Map<String, ?> cryptoHashProviderProperties) {

		_cryptoHashProviderFactoryName = cryptoHashProviderFactoryName;

		_rounds = MapUtil.getInteger(cryptoHashProviderProperties, ROUNDS);
	}

	@Override
	public CryptoHashProviderResponse generate(byte[] salt, byte[] input) {
		String hashedPassword = BCrypt.hashpw(
			new String(input, StandardCharsets.US_ASCII),
			new String(salt, StandardCharsets.US_ASCII));

		return new CryptoHashProviderResponse(
			_cryptoHashProviderFactoryName, null,
			hashedPassword.getBytes(StandardCharsets.US_ASCII));
	}

	@Override
	public byte[] generateSalt() {
		String salt = BCrypt.gensalt(_rounds);

		return salt.getBytes(StandardCharsets.US_ASCII);
	}

	private static final int _DEFAULT_ROUNDS = 10;

	private final String _cryptoHashProviderFactoryName;
	private final int _rounds;

}