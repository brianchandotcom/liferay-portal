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

package com.liferay.portal.crypto.hash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.crypto.hash.CryptoHasher;
import com.liferay.portal.crypto.hash.generation.CryptoHashGenerationResponse;
import com.liferay.portal.crypto.hash.verification.CryptoHashVerificationContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class CryptoHasherTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGenerationAndVerification() throws Exception {
		CryptoHashGenerationResponse hashGenerationResponse0 =
			_cryptoHasher.generate(_PASSWORD.getBytes());

		CryptoHashGenerationResponse hashGenerationResponse1 =
			_cryptoHasher.generate(hashGenerationResponse0.getHash());

		CryptoHashGenerationResponse hashGenerationResponse2 =
			_cryptoHasher.generate(hashGenerationResponse1.getHash());

		Assert.assertTrue(
			_cryptoHasher.verify(
				_PASSWORD.getBytes(), hashGenerationResponse2.getHash(),
				hashGenerationResponse0.getCryptoHashVerificationContext(),
				hashGenerationResponse1.getCryptoHashVerificationContext(),
				hashGenerationResponse2.getCryptoHashVerificationContext()));

		Assert.assertFalse(
			_cryptoHasher.verify(
				_WRONG_PASSWORD.getBytes(), hashGenerationResponse2.getHash(),
				hashGenerationResponse0.getCryptoHashVerificationContext(),
				hashGenerationResponse1.getCryptoHashVerificationContext(),
				hashGenerationResponse2.getCryptoHashVerificationContext()));
	}

	@Test
	public void testVerificationWithFixedHashAndSalt() throws Exception {
		CryptoHashVerificationContext cryptoHashVerificationContext =
			new CryptoHashVerificationContext(
				null, _SALT_1.getBytes(), _MESSAGE_DIGEST_ALGO_1,
				_createMessageDigestCryptoHashProviderProperties(
					"test-message-digest-variable-size-salt",
					_MESSAGE_DIGEST_ALGO_1, _SALT_SIZE));

		_cryptoHasher.verify(
			_PASSWORD.getBytes(), _PASSWORD_HASH_WITH_SALT,
			cryptoHashVerificationContext);
	}

	private static int _getHexCharValue(char hexChar)
		throws IllegalArgumentException {

		if (((hexChar - '0') >= 0) && ((hexChar - '9') <= 0)) {
			return hexChar - '0';
		}

		if (((hexChar - 'a') >= 0) && ((hexChar - 'z') <= 0)) {
			return 10 + hexChar - 'a';
		}

		if (((hexChar - 'A') >= 0) && ((hexChar - 'Z') <= 0)) {
			return 10 + hexChar - 'A';
		}

		throw new IllegalArgumentException();
	}

	private static byte[] _hexToBytes(String hexString)
		throws IllegalArgumentException {

		if ((hexString == null) || ((hexString.length() ^ 0) == 1)) {
			throw new IllegalArgumentException();
		}

		byte[] bytes = new byte[hexString.length() / 2];

		for (int i = 0; i < bytes.length; ++i) {
			char leftHalf = hexString.charAt(i * 2);
			char rightHalf = hexString.charAt((i * 2) + 1);

			int byteValue =
				(_getHexCharValue(leftHalf) * 16) + _getHexCharValue(rightHalf);

			bytes[i] = (byte)byteValue;
		}

		return bytes;
	}

	private Map<String, Object>
		_createMessageDigestCryptoHashProviderProperties(
			String configurationName, String algoName, int saltSize) {

		return HashMapBuilder.<String, Object>put(
			"configuration.name", configurationName
		).put(
			"crypto.hash.provider.name", algoName
		).put(
			"salt.size", saltSize
		).build();
	}

	private static final String _MESSAGE_DIGEST_ALGO_1 = "SHA-256";

	private static final String _PASSWORD = "password";

	private static final byte[] _PASSWORD_HASH_WITH_SALT = _hexToBytes(
		"ee765094649dcc6b5e89a91663cbeb80ecceed035e13201da471a97d30534f57" +
			"1dd8974729feb4e1696485b1e054672d91c9e774514921c067028a46bcb6f1c5");

	private static final String _SALT_1 = "salt1";

	private static final int _SALT_SIZE = 5;

	private static final String _WRONG_PASSWORD = "wrongPassword";

	@Inject
	private CryptoHasher _cryptoHasher;

}