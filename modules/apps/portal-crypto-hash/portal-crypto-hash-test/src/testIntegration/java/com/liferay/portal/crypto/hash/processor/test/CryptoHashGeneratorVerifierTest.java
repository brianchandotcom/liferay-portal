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

package com.liferay.portal.crypto.hash.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.crypto.hash.generation.CryptoHashGenerationResponse;
import com.liferay.portal.crypto.hash.generation.CryptoHashGenerator;
import com.liferay.portal.crypto.hash.verification.CryptoHashVerificationContext;
import com.liferay.portal.crypto.hash.verification.CryptoHashVerifier;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class CryptoHashGeneratorVerifierTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_testMessageDigestPid = _createMessageDigestConfiguration(
			"test-message-digest", _MESSAGE_DIGEST_ALGO_1, 0);
		_testMessageDigestWithSaltPid = _createMessageDigestConfiguration(
			"test-message-digest-variable-size-salt", _MESSAGE_DIGEST_ALGO_1,
			_SALT_SIZE);

		_cryptoHashProcessorPid = _createCryptoHashProcessorConfiguration(
			"message-digest", "(configuration.name=test-message-digest)");

		_cryptoHashProcessorWithSaltPid =
			_createCryptoHashProcessorConfiguration(
				"message-digest-with-salt",
				"(configuration.name=test-message-digest-variable-size-salt)");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_testMessageDigestPid.close();
		_testMessageDigestWithSaltPid.close();

		_cryptoHashProcessorPid.close();
		_cryptoHashProcessorWithSaltPid.close();
	}

	@Test
	public void testGeneration() throws Exception {
		CryptoHashGenerationResponse hashGenerationResponse =
			_cryptoHashGenerator.generate(_PASSWORD.getBytes());

		Assert.assertArrayEquals(
			hashGenerationResponse.getHash(), _PASSWORD_HASH);
	}

	@Test
	public void testGenerationAndVerification() throws Exception {
		CryptoHashGenerationResponse hashGenerationResponse0 =
			_cryptoHashGenerator.generate(_PASSWORD.getBytes());

		CryptoHashGenerationResponse hashGenerationResponse1 =
			_cryptoHashGenerator.generate(hashGenerationResponse0.getHash());

		CryptoHashGenerationResponse hashGenerationResponse2 =
			_cryptoHashGenerator.generate(hashGenerationResponse1.getHash());

		CryptoHashVerificationContext[] cryptoHashVerificationContexts =
			new CryptoHashVerificationContext[3];

		cryptoHashVerificationContexts[0] =
			hashGenerationResponse0.getCryptoHashVerificationContext();
		cryptoHashVerificationContexts[1] =
			hashGenerationResponse1.getCryptoHashVerificationContext();
		cryptoHashVerificationContexts[2] =
			hashGenerationResponse2.getCryptoHashVerificationContext();

		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				_PASSWORD.getBytes(), hashGenerationResponse2.getHash(),
				cryptoHashVerificationContexts));

		Assert.assertFalse(
			_cryptoHashVerifier.verify(
				_WRONG_PASSWORD.getBytes(), hashGenerationResponse2.getHash(),
				cryptoHashVerificationContexts));
	}

	@Test
	public void testVerificationWithSalt() throws Exception {
		CryptoHashVerificationContext cryptoHashVerificationContext =
			new CryptoHashVerificationContext(
				null, _SALT_1.getBytes(), _MESSAGE_DIGEST_ALGO_1,
				_createMessageDigestCryptoHashProviderProperties(
					"test-message-digest-variable-size-salt",
					_MESSAGE_DIGEST_ALGO_1, _SALT_SIZE));

		_cryptoHashVerifierWithSalt.verify(
			_PASSWORD.getBytes(), _PASSWORD_HASH_WITH_SALT,
			cryptoHashVerificationContext);
	}

	private static AutoCloseable _createCryptoHashProcessorConfiguration(
			String configurationName, String cryptoHashProviderSelect)
		throws Exception {

		String factoryConfigurationPid =
			ConfigurationTestUtil.createFactoryConfiguration(
				"com.liferay.portal.crypto.hash.configuration." +
					"CryptoHashProcessorConfiguration",
				new HashMapDictionary<>(
					HashMapBuilder.<String, Object>put(
						"configuration.name", configurationName
					).put(
						"CryptoHashProvider.target", cryptoHashProviderSelect
					).build()));

		return () -> ConfigurationTestUtil.deleteFactoryConfiguration(
			factoryConfigurationPid,
			"com.liferay.portal.crypto.hash.configuration." +
				"CryptoHashProcessorConfiguration");
	}

	private static AutoCloseable _createMessageDigestConfiguration(
			String configurationName, String algoName, int saltSize)
		throws Exception {

		String factoryConfigurationPid =
			ConfigurationTestUtil.createFactoryConfiguration(
				"com.liferay.portal.crypto.hash.provider.message.digest." +
					"internal.configuration." +
						"MessageDigestCryptoHashProviderConfiguration",
				new HashMapDictionary<>(
					_createMessageDigestCryptoHashProviderProperties(
						configurationName, algoName, saltSize)));

		return () -> ConfigurationTestUtil.deleteFactoryConfiguration(
			factoryConfigurationPid,
			"com.liferay.portal.crypto.hash.generator.message.digest." +
				"internal.configuration." +
					"MessageDigestCryptoHashProviderConfiguration");
	}

	private static Map<String, Object>
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

	private static final String _MESSAGE_DIGEST_ALGO_1 = "SHA-256";

	private static final String _PASSWORD = "password";

	private static final byte[] _PASSWORD_HASH = _hexToBytes(
		"5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");

	private static final byte[] _PASSWORD_HASH_WITH_SALT = _hexToBytes(
		"ee765094649dcc6b5e89a91663cbeb80ecceed035e13201da471a97d30534f57" +
			"1dd8974729feb4e1696485b1e054672d91c9e774514921c067028a46bcb6f1c5");

	private static final String _SALT_1 = "salt1";

	private static final int _SALT_SIZE = 5;

	private static final String _WRONG_PASSWORD = "wrongPassword";

	private static AutoCloseable _cryptoHashProcessorPid;
	private static AutoCloseable _cryptoHashProcessorWithSaltPid;
	private static AutoCloseable _testMessageDigestPid;
	private static AutoCloseable _testMessageDigestWithSaltPid;

	@Inject(filter = "configuration.name=message-digest")
	private CryptoHashGenerator _cryptoHashGenerator;

	@Inject(filter = "configuration.name=message-digest-with-salt")
	private CryptoHashGenerator _cryptoHashGeneratorWithSalt;

	@Inject(filter = "configuration.name=message-digest")
	private CryptoHashVerifier _cryptoHashVerifier;

	@Inject(filter = "configuration.name=message-digest-with-salt")
	private CryptoHashVerifier _cryptoHashVerifierWithSalt;

}