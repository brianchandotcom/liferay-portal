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
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.crypto.hash.CryptoHashGenerator;
import com.liferay.portal.crypto.hash.CryptoHashResponse;
import com.liferay.portal.crypto.hash.CryptoHashVerificationContext;
import com.liferay.portal.crypto.hash.CryptoHashVerifier;
import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jodd.util.BCrypt;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedServiceFactory;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class CryptoHashTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(CryptoHashTest.class);

		_bundleContext = bundle.getBundleContext();

		_password = "This is a test".getBytes(StandardCharsets.US_ASCII);
		_salt = RandomTestUtil.randomBytes();

		String bCryptSalt = BCrypt.gensalt();

		_bCryptSalt = bCryptSalt.getBytes(StandardCharsets.US_ASCII);

		String expectedBCryptHash = BCrypt.hashpw(
			new String(_password, StandardCharsets.US_ASCII), bCryptSalt);

		_expectedBCryptHash = expectedBCryptHash.getBytes(
			StandardCharsets.US_ASCII);

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

		_expectedMessageDigestHash = messageDigest.digest(
			ArrayUtil.append(_salt, _password));
	}

	@After
	public void tearDown() {
		ListIterator<AutoCloseable> listIterator = _autoCloseables.listIterator(
			_autoCloseables.size());

		while (listIterator.hasPrevious()) {
			AutoCloseable autoCloseable = listIterator.previous();

			try {
				autoCloseable.close();
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}
	}

	@Test
	public void testCryptoHashGeneratorWithConfiguration() throws Exception {
		_addFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.message.digest.internal." +
				"configuration.MessageDigestCryptoHashProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"crypto.hash.provider.configuration.name", "test-message-digest"
			).put(
				"message.digest.algorithm", "SHA-256"
			).put(
				"salt.size", "32"
			).build());

		byte[] randomBytes = RandomTestUtil.randomBytes();

		CryptoHashResponse cryptoHashResponse = _callService(
			CryptoHashGenerator.class,
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

		_addFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.message.digest.internal." +
				"configuration.MessageDigestCryptoHashProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"crypto.hash.provider.configuration.name",
				"test-message-digest-1"
			).put(
				"message.digest.algorithm", "SHA-256"
			).put(
				"message.digest.salt.size", "32"
			).build());
		_addFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.bcrypt.internal." +
				"configuration.BCryptCryptoHashProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"bcrypt.rounds", "5"
			).put(
				"crypto.hash.provider.configuration.name",
				"test-message-digest-2"
			).build());

		byte[] randomBytes = RandomTestUtil.randomBytes();

		CryptoHashResponse cryptoHashResponse1 = _callService(
			CryptoHashGenerator.class,
			"(crypto.hash.provider.configuration.name=test-message-digest-1)",
			cryptoHashGenerator -> {
				Assert.assertNotNull(cryptoHashGenerator);

				return cryptoHashGenerator.generate(randomBytes);
			});

		CryptoHashResponse cryptoHashResponse2 = _callService(
			CryptoHashGenerator.class,
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
	public void testCryptoHashGeneratorWithNoConfigurations() throws Exception {
		_callService(
			CryptoHashGenerator.class,
			"(crypto.hash.provider.configuration.name=empty-message-digest)",
			object -> {
				Assert.assertNull(object);

				return null;
			});
	}

	@Test
	public void testCryptoHashVerifierWithNoConfigurations() throws Exception {
		_addFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.message.digest.internal." +
				"configuration.MessageDigestCryptoHashProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"crypto.hash.provider.configuration.name",
				"test-message-digest-1"
			).put(
				"message.digest.algorithm", "SHA-256"
			).put(
				"message.digest.salt.size", "32"
			).build());

		AutoCloseable autoCloseable1 = _autoCloseables.remove(
			_autoCloseables.size() - 1);

		_addFactoryConfiguration(
			"com.liferay.portal.crypto.hash.provider.bcrypt.internal." +
				"configuration.BCryptCryptoHashProviderConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"bcrypt.rounds", "5"
			).put(
				"crypto.hash.provider.configuration.name",
				"test-message-digest-2"
			).build());

		AutoCloseable autoCloseable2 = _autoCloseables.remove(
			_autoCloseables.size() - 1);

		byte[] randomBytes = RandomTestUtil.randomBytes();

		CryptoHashResponse cryptoHashResponse1 = _callService(
			CryptoHashGenerator.class,
			"(crypto.hash.provider.configuration.name=test-message-digest-1)",
			cryptoHashGenerator -> {
				Assert.assertNotNull(cryptoHashGenerator);

				return cryptoHashGenerator.generate(randomBytes);
			});

		CryptoHashResponse cryptoHashResponse2 = _callService(
			CryptoHashGenerator.class,
			"(crypto.hash.provider.configuration.name=test-message-digest-2)",
			cryptoHashGenerator -> {
				Assert.assertNotNull(cryptoHashGenerator);

				return cryptoHashGenerator.generate(
					cryptoHashResponse1.getHash());
			});

		autoCloseable1.close();
		autoCloseable2.close();

		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				randomBytes, cryptoHashResponse2.getHash(),
				Arrays.asList(
					cryptoHashResponse1.getCryptoHashVerificationContext(),
					cryptoHashResponse2.getCryptoHashVerificationContext())));
	}

	@Test(expected = CryptoHashException.class)
	public void testCryptoHashVerifierWithNonexistingCryptoHashFactory()
		throws Exception {

		_cryptoHashVerifier.verify(
			_password, _expectedMessageDigestHash,
			new CryptoHashVerificationContext(
				RandomTestUtil.randomString(), Collections.emptyMap(), _salt));
	}

	@Test
	public void testCryptoHashVerifierWithStaticInput() throws Exception {
		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				_password, _expectedMessageDigestHash,
				new CryptoHashVerificationContext(
					"MessageDigest",
					HashMapBuilder.<String, Object>put(
						"message.digest.algorithm", "SHA-512"
					).build(),
					_salt)));

		Assert.assertFalse(
			_cryptoHashVerifier.verify(
				_password, _expectedMessageDigestHash,
				new CryptoHashVerificationContext(
					"MessageDigest",
					HashMapBuilder.<String, Object>put(
						"message.digest.algorithm", "SHA-256"
					).build(),
					_salt)));

		Assert.assertTrue(
			_cryptoHashVerifier.verify(
				_password, _expectedBCryptHash,
				new CryptoHashVerificationContext(
					"BCrypt", Collections.emptyMap(), _bCryptSalt)));
	}

	private Configuration _addFactoryConfiguration(
		String factoryPid, Dictionary<String, ?> properties) {

		Configuration configuration = _registerFactoryConfiguration(
			factoryPid, properties);

		String configurationPid = configuration.getPid();

		_autoCloseables.add(
			() -> {
				CountDownLatch countDownLatch = new CountDownLatch(1);

				ServiceRegistration<ManagedServiceFactory> serviceRegistration =
					_bundleContext.registerService(
						ManagedServiceFactory.class,
						new ManagedServiceFactory() {

							@Override
							public void deleted(String pid) {
								if (configurationPid.equals(pid)) {
									countDownLatch.countDown();
								}
							}

							@Override
							public String getName() {
								return "Test managed service factory for PID " +
									factoryPid;
							}

							@Override
							public void updated(
								String pid,
								Dictionary<String, ?> updatedProperties) {
							}

						},
						HashMapDictionaryBuilder.put(
							Constants.SERVICE_PID, factoryPid
						).build());

				try {
					configuration.delete();

					countDownLatch.await(10, TimeUnit.SECONDS);
				}
				catch (Exception exception) {
					_log.error(exception, exception);
				}
				finally {
					serviceRegistration.unregister();
				}
			});

		return configuration;
	}

	private <S, R, E extends Throwable> R _callService(
		Class<S> serviceClass, String filterString,
		UnsafeFunction<S, R, E> unsafeFunction) {

		ServiceReference<S>[] serviceReferences = null;

		try {
			serviceReferences =
				(ServiceReference<S>[])_bundleContext.getAllServiceReferences(
					serviceClass.getName(), filterString);
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			ReflectionUtil.throwException(invalidSyntaxException);
		}

		try {
			if ((serviceReferences == null) ||
				ArrayUtil.isEmpty(serviceReferences)) {

				return unsafeFunction.apply(null);
			}
		}
		catch (Throwable throwable) {
			ReflectionUtil.throwException(throwable);
		}

		ServiceReference<S> serviceReference = serviceReferences[0];

		try {
			return unsafeFunction.apply(
				_bundleContext.getService(serviceReference));
		}
		catch (Throwable throwable) {
			ReflectionUtil.throwException(throwable);
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}

		return null;
	}

	private boolean _contains(
		Dictionary<String, ?> properties1, Dictionary<String, ?> properties2) {

		if (properties2.size() > properties1.size()) {
			return false;
		}

		Enumeration<String> enumeration = properties2.keys();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			if (!Objects.deepEquals(
					properties2.get(key), properties1.get(key))) {

				return false;
			}
		}

		return true;
	}

	private Configuration _registerFactoryConfiguration(
		String factoryPid, Dictionary<String, ?> properties) {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		ServiceRegistration<ManagedServiceFactory> serviceRegistration =
			_bundleContext.registerService(
				ManagedServiceFactory.class,
				new ManagedServiceFactory() {

					@Override
					public void deleted(String pid) {
					}

					@Override
					public String getName() {
						return "Test managed service factory for PID " +
							factoryPid;
					}

					@Override
					public void updated(
						String pid, Dictionary<String, ?> updatedProperties) {

						if (updatedProperties == null) {
							return;
						}

						if (_contains(updatedProperties, properties)) {
							countDownLatch.countDown();
						}
					}

				},
				HashMapDictionaryBuilder.put(
					Constants.SERVICE_PID, factoryPid
				).build());

		try {
			ServiceReference<ConfigurationAdmin> serviceReference =
				_bundleContext.getServiceReference(ConfigurationAdmin.class);

			ConfigurationAdmin configurationAdmin = _bundleContext.getService(
				serviceReference);

			Configuration configuration = null;

			try {
				configuration = configurationAdmin.createFactoryConfiguration(
					factoryPid, StringPool.QUESTION);

				configuration.update(properties);

				countDownLatch.await(5, TimeUnit.MINUTES);

				return configuration;
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
			catch (InterruptedException interruptedException) {
				try {
					configuration.delete();
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}

				throw new RuntimeException(interruptedException);
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CryptoHashTest.class);

	private static byte[] _bCryptSalt;
	private static BundleContext _bundleContext;
	private static byte[] _expectedBCryptHash;
	private static byte[] _expectedMessageDigestHash;
	private static byte[] _password;
	private static byte[] _salt;

	private final List<AutoCloseable> _autoCloseables = new ArrayList<>();

	@Inject
	private CryptoHashVerifier _cryptoHashVerifier;

}