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

package com.liferay.portal.crypto.hash.internal;

import com.liferay.portal.crypto.hash.CryptoHashGenerator;
import com.liferay.portal.crypto.hash.CryptoHashResponse;
import com.liferay.portal.crypto.hash.CryptoHashVerificationContext;
import com.liferay.portal.crypto.hash.provider.bcrypt.internal.BCryptCryptoHashProviderFactory;
import com.liferay.portal.crypto.hash.provider.message.digest.internal.MessageDigestCryptoHashProviderFactory;
import com.liferay.portal.crypto.hash.spi.CryptoHashProviderFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public class CryptoHashGeneratorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_cryptoHashVerifierImpl = new CryptoHashVerifierImpl();

		_cryptoHashVerifierImpl.activate(bundleContext);

		BCryptCryptoHashProviderFactory bCryptCryptoHashProviderFactory =
			new BCryptCryptoHashProviderFactory();

		_serviceRegistration1 = bundleContext.registerService(
			CryptoHashProviderFactory.class, bCryptCryptoHashProviderFactory,
			null);

		MessageDigestCryptoHashProviderFactory
			messageDigestCryptoHashProviderFactory =
				new MessageDigestCryptoHashProviderFactory();

		_serviceRegistration2 = bundleContext.registerService(
			CryptoHashProviderFactory.class,
			messageDigestCryptoHashProviderFactory, null);

		_cryptoHashGenerators = Arrays.asList(
			new CryptoHashGeneratorImpl(
				bCryptCryptoHashProviderFactory.create(
					Collections.singletonMap("bcrypt.rounds", "10"))),
			new CryptoHashGeneratorImpl(
				messageDigestCryptoHashProviderFactory.create(
					Collections.singletonMap(
						"message.digest.algorithm", "SHA-256"))));
	}

	@After
	public void tearDown() {
		_serviceRegistration2.unregister();

		_serviceRegistration1.unregister();
	}

	@Test
	public void testGenerate() throws Exception {
		for (CryptoHashGenerator cryptoHashGenerator : _cryptoHashGenerators) {
			CryptoHashResponse cryptoHashResponse =
				cryptoHashGenerator.generate(_INPUT);

			Assert.assertFalse(
				_cryptoHashVerifierImpl.verify(
					RandomTestUtil.randomBytes(), cryptoHashResponse.getHash(),
					cryptoHashResponse.getCryptoHashVerificationContext()));
			Assert.assertTrue(
				_cryptoHashVerifierImpl.verify(
					_INPUT, cryptoHashResponse.getHash(),
					cryptoHashResponse.getCryptoHashVerificationContext()));
		}

		List<CryptoHashVerificationContext> cryptoHashVerificationContexts =
			new ArrayList<>();
		byte[] hash = _INPUT;

		for (CryptoHashGenerator cryptoHashGenerator : _cryptoHashGenerators) {
			CryptoHashResponse cryptoHashResponse =
				cryptoHashGenerator.generate(hash);

			cryptoHashVerificationContexts.add(
				cryptoHashResponse.getCryptoHashVerificationContext());

			hash = cryptoHashResponse.getHash();
		}

		Assert.assertFalse(
			_cryptoHashVerifierImpl.verify(
				RandomTestUtil.randomBytes(), hash,
				cryptoHashVerificationContexts));
		Assert.assertTrue(
			_cryptoHashVerifierImpl.verify(
				_INPUT, hash, cryptoHashVerificationContexts));
	}

	private static final byte[] _INPUT = RandomTestUtil.randomBytes();

	private List<CryptoHashGenerator> _cryptoHashGenerators;
	private CryptoHashVerifierImpl _cryptoHashVerifierImpl;
	private ServiceRegistration<?> _serviceRegistration1;
	private ServiceRegistration<?> _serviceRegistration2;

}