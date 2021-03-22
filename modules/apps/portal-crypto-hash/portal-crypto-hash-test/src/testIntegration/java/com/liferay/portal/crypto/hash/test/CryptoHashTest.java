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
import com.liferay.portal.crypto.hash.CryptoHashGenerator;
import com.liferay.portal.crypto.hash.CryptoHashResponse;
import com.liferay.portal.crypto.hash.CryptoHashVerificationContext;
import com.liferay.portal.crypto.hash.CryptoHashVerifier;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class CryptoHashTest extends BaseCryptoHashTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCryptoHashGeneratorWithConfiguration() throws Exception {
		createFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.message.digest.internal." +
				"configuration.MessageDigestCryptoHashProviderConfiguration",
			new HashMapDictionary<>(
				HashMapBuilder.<String, Object>put(
					"crypto.hash.provider.configuration.name",
					"test-message-digest"
				).put(
					"message.digest.algorithm", "SHA-256"
				).put(
					"salt.size", "32"
				).build()));

		byte[] randomBytes = randomBytes();

		CryptoHashResponse cryptoHashResponse = callService(
			bundleContext, CryptoHashGenerator.class,
			"(crypto.hash.provider.configuration.name=test-message-digest)",
			cryptoHashGenerator -> {
				Assert.assertNotNull(cryptoHashGenerator);

				return cryptoHashGenerator.generate(randomBytes);
			});

		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				randomBytes, cryptoHashResponse.getHash(),
				cryptoHashResponse.getCryptoHashVerificationContext()));
	}

	@Test
	public void testCryptoHashGeneratorWithMultipleConfigurations()
		throws Exception {

		createFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.message.digest.internal." +
				"configuration.MessageDigestCryptoHashProviderConfiguration",
			new HashMapDictionary<>(
				HashMapBuilder.<String, Object>put(
					"crypto.hash.provider.configuration.name",
					"test-message-digest-1"
				).put(
					"message.digest.algorithm", "SHA-256"
				).put(
					"message.digest.salt.size", "32"
				).build()));

		createFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.bcrypt.internal." +
				"configuration.BCryptCryptoHashProviderConfiguration",
			new HashMapDictionary<>(
				HashMapBuilder.<String, Object>put(
					"bcrypt.rounds", "5"
				).put(
					"crypto.hash.provider.configuration.name",
					"test-message-digest-2"
				).build()));

		byte[] randomBytes = randomBytes();

		CryptoHashResponse cryptoHashResponse1 = callService(
			bundleContext, CryptoHashGenerator.class,
			"(crypto.hash.provider.configuration.name=test-message-digest-1)",
			cryptoHashGenerator -> {
				Assert.assertNotNull(cryptoHashGenerator);

				return cryptoHashGenerator.generate(randomBytes);
			});

		CryptoHashResponse cryptoHashResponse2 = callService(
			bundleContext, CryptoHashGenerator.class,
			"(&(bcrypt.rounds=5)(crypto.hash.provider.factory.name=BCrypt))",
			cryptoHashGenerator -> {
				Assert.assertNotNull(cryptoHashGenerator);

				return cryptoHashGenerator.generate(
					cryptoHashResponse1.getHash());
			});

		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				randomBytes, cryptoHashResponse2.getHash(),
				Arrays.asList(
					cryptoHashResponse1.getCryptoHashVerificationContext(),
					cryptoHashResponse2.getCryptoHashVerificationContext())));
	}

	@Test
	public void testCryptoHashGeneratorWithNoConfiguration() throws Exception {
		callService(
			bundleContext, CryptoHashGenerator.class,
			"(crypto.hash.provider.configuration.name=test-message-digest)",
			object -> {
				Assert.assertNull(object);

				return null;
			});
	}

	@Test
	public void testCryptoHashVerifierWithNoConfigurations() throws Exception {
		createFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.message.digest.internal." +
				"configuration.MessageDigestCryptoHashProviderConfiguration",
			new HashMapDictionary<>(
				HashMapBuilder.<String, Object>put(
					"crypto.hash.provider.configuration.name",
					"test-message-digest-1"
				).put(
					"message.digest.algorithm", "SHA-256"
				).put(
					"message.digest.salt.size", "32"
				).build()));

		AutoCloseable autoCloseable1 = autoCloseables.remove(
			autoCloseables.size() - 1);

		createFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.bcrypt.internal." +
				"configuration.BCryptCryptoHashProviderConfiguration",
			new HashMapDictionary<>(
				HashMapBuilder.<String, Object>put(
					"bcrypt.rounds", "5"
				).put(
					"crypto.hash.provider.configuration.name",
					"test-message-digest-2"
				).build()));

		AutoCloseable autoCloseable2 = autoCloseables.remove(
			autoCloseables.size() - 1);

		byte[] randomBytes = randomBytes();

		CryptoHashResponse cryptoHashResponse1 = callService(
			bundleContext, CryptoHashGenerator.class,
			"(crypto.hash.provider.configuration.name=test-message-digest-1)",
			cryptoHashGenerator -> {
				Assert.assertNotNull(cryptoHashGenerator);

				return cryptoHashGenerator.generate(randomBytes);
			});

		CryptoHashResponse cryptoHashResponse2 = callService(
			bundleContext, CryptoHashGenerator.class,
			"(crypto.hash.provider.configuration.name=test-message-digest-2)",
			cryptoHashGenerator -> {
				Assert.assertNotNull(cryptoHashGenerator);

				return cryptoHashGenerator.generate(
					cryptoHashResponse1.getHash());
			});

		autoCloseable2.close();
		autoCloseable1.close();

		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				randomBytes, cryptoHashResponse2.getHash(),
				Arrays.asList(
					cryptoHashResponse1.getCryptoHashVerificationContext(),
					cryptoHashResponse2.getCryptoHashVerificationContext())));
	}

	@Test(expected = Exception.class)
	public void testCryptoHashVerifierWithNonexistingCryptoHashFactory()
		throws Exception {

		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				"This is a test".getBytes(StandardCharsets.UTF_8),
				Base64.decode(
					"83XLsWN2CdnxOOMcjYZ82PqSlQxrD6/9VhZ33Rp+V6uBZPzzzJhe0aMx" +
						"ZtOX4fwaiTytq6REBAKyej5/UVmoLw=="),
				new CryptoHashVerificationContext(
					RandomTestUtil.randomString(), Collections.emptyMap(),
					Base64.decode(
						"PZ0KUrMwjvcAaJdiYuRuxgUA6EQu3GzhWtduzJXwzX+4NfqWwl94" +
							"XxpjABA1gLeXMpfsjc4PmXhNIlKZpU1k6Q=="))));
	}

	@Test
	public void testCryptoHashVerifierWithStaticInput() throws Exception {
		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				"This is a test".getBytes(StandardCharsets.UTF_8),
				Base64.decode(
					"83XLsWN2CdnxOOMcjYZ82PqSlQxrD6/9VhZ33Rp+V6uBZPzzzJhe0aMx" +
						"ZtOX4fwaiTytq6REBAKyej5/UVmoLw=="),
				new CryptoHashVerificationContext(
					"MessageDigest",
					HashMapBuilder.<String, Object>put(
						"message.digest.algorithm", "SHA-512"
					).build(),
					Base64.decode(
						"PZ0KUrMwjvcAaJdiYuRuxgUA6EQu3GzhWtduzJXwzX+4NfqWwl94" +
							"XxpjABA1gLeXMpfsjc4PmXhNIlKZpU1k6Q=="))));

		Assert.assertFalse(
			_cryptoHashVerifier.verify(
				"This is a test".getBytes(StandardCharsets.UTF_8),
				Base64.decode(
					"83XLsWN2CdnxOOMcjYZ82PqSlQxrD6/9VhZ33Rp+V6uBZPzzzJhe0aMx" +
						"ZtOX4fwaiTytq6REBAKyej5/UVmoLw=="),
				new CryptoHashVerificationContext(
					"MessageDigest",
					HashMapBuilder.<String, Object>put(
						"message.digest.algorithm", "SHA-256"
					).build(),
					Base64.decode(
						"PZ0KUrMwjvcAaJdiYuRuxgUA6EQu3GzhWtduzJXwzX+4NfqWwl94" +
							"XxpjABA1gLeXMpfsjc4PmXhNIlKZpU1k6Q=="))));

		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				"This is a test".getBytes(StandardCharsets.UTF_8),
				Base64.decode(
					"JDJhJDEwJHVxZVh5YjF1dUdHZjZ2UWtvalljU09lbjdaUjVaTEE0Lmxi" +
						"S3IzUFpCWDRaNk1XVTlrYnJD"),
				new CryptoHashVerificationContext(
					"BCrypt",
					HashMapBuilder.<String, Object>put(
						"bcrypt.rounds", "15"
					).build(),
					Base64.decode(
						"JDJhJDEwJHVxZVh5YjF1dUdHZjZ2UWtvalljU08="))));
	}

	@Inject
	private CryptoHashVerifier _cryptoHashVerifier;

}