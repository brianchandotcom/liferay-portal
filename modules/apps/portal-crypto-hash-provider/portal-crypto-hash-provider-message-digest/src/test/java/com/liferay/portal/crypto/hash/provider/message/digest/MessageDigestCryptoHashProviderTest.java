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

package com.liferay.portal.crypto.hash.provider.message.digest;

import com.liferay.portal.crypto.hash.provider.message.digest.internal.factory.MessageDigestCryptoHashProvider;
import com.liferay.portal.crypto.hash.provider.message.digest.internal.factory.MessageDigestCryptoHashProviderFactory;
import com.liferay.portal.crypto.hash.provider.spi.CryptoHashProvider;
import com.liferay.portal.crypto.hash.provider.spi.CryptoHashProviderResponse;
import com.liferay.portal.crypto.hash.provider.spi.salt.VariableSizeSaltProvider;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Arthur Chan
 */
@PrepareForTest(MessageDigestCryptoHashProvider.class)
@RunWith(PowerMockRunner.class)
public class MessageDigestCryptoHashProviderTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_cryptoHashProvider = _cryptoHashProviderFactory.create(
			_MESSAGE_DIGEST_ALGORITHM,
			HashMapBuilder.<String, Object>put(
				"configuration.name", "test-configuration"
			).put(
				"crypto.hash.provider.name", _MESSAGE_DIGEST_ALGORITHM
			).put(
				"salt.size", _VARIABLE_SIZE
			).build());
	}

	@Test
	public void testGenerateHashWithoutSaltAndPepper() throws Exception {
		final CryptoHashProviderResponse hashGeneratorProviderResponse =
			_cryptoHashProvider.generate(null, null, _PASSWORD.getBytes());

		Assert.assertEquals(
			_PASSWORD_HASH, _toHex(hashGeneratorProviderResponse.getHash()));
	}

	@Test
	public void testGenerateHashWithPepper() throws Exception {
		CryptoHashProviderResponse hashGeneratorProviderResponse =
			_cryptoHashProvider.generate(
				_USE_PEPPER.getBytes(), null, _PASSWORD.getBytes());

		Assert.assertEquals(
			_PASSWORD_PEPPER_HASH,
			_toHex(hashGeneratorProviderResponse.getHash()));
	}

	@Test
	public void testGenerateHashWithSalt() throws Exception {
		CryptoHashProviderResponse hashGeneratorProviderResponse =
			_cryptoHashProvider.generate(
				null, _USE_SALT.getBytes(), _PASSWORD.getBytes());

		Assert.assertEquals(
			_PASSWORD_SALT_HASH,
			_toHex(hashGeneratorProviderResponse.getHash()));
	}

	@Test
	public void testGenerateVariableSizeSalt() throws Exception {
		VariableSizeSaltProvider variableSizeSaltProvider =
			(VariableSizeSaltProvider)_cryptoHashProvider;

		byte[] salt = variableSizeSaltProvider.generateSalt(_VARIABLE_SIZE);

		Assert.assertEquals(salt.length, _VARIABLE_SIZE);
	}

	private String _toHex(byte[] hash) {
		StringBuilder sb = new StringBuilder(hash.length);

		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	private static final String _MESSAGE_DIGEST_ALGORITHM = "SHA-256";

	private static final String _PASSWORD = "password";

	private static final String _PASSWORD_HASH =
		"5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

	private static final String _PASSWORD_PEPPER_HASH =
		"4b65d30b048d9eab292a2ea50fd60423d3d5d581a6ed85169b8a0c4f7dd10c00";

	private static final String _PASSWORD_SALT_HASH =
		"13601bda4ea78e55a07b98866d2be6be0744e3866f13c00c811cab608a28f322";

	private static final String _USE_PEPPER = "pepper";

	private static final String _USE_SALT = "salt";

	private static final int _VARIABLE_SIZE = 10;

	private static final MessageDigestCryptoHashProviderFactory
		_cryptoHashProviderFactory =
			new MessageDigestCryptoHashProviderFactory();

	private CryptoHashProvider _cryptoHashProvider;

}