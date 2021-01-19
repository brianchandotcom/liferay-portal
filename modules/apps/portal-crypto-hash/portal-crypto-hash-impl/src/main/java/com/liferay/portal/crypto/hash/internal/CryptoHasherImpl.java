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

import com.liferay.portal.crypto.hash.CryptoHasher;
import com.liferay.portal.crypto.hash.generation.CryptoHashGenerationResponse;
import com.liferay.portal.crypto.hash.verification.CryptoHashVerificationContext;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@Component(service = CryptoHasher.class)
public class CryptoHasherImpl implements CryptoHasher {

	public CryptoHasherImpl() throws NoSuchAlgorithmException {
		_messageDigestCryptoHashProvider = new MessageDigestCryptoHashProvider(
			"SHA-256",
			HashMapBuilder.put(
				"saltSize", 16
			).build());
	}

	@Override
	public CryptoHashGenerationResponse generate(byte[] input)
		throws Exception {

		byte[] pepper = null;
		String pepperId = null;

		CryptoHashProviderResponse cryptoHashProviderResponse =
			_messageDigestCryptoHashProvider.generate(
				pepper, _messageDigestCryptoHashProvider.generateSalt(), input);

		return new CryptoHashGenerationResponse(
			cryptoHashProviderResponse.getHash(),
			new CryptoHashVerificationContext(
				pepperId, cryptoHashProviderResponse.getSalt(),
				cryptoHashProviderResponse.getCryptoHashProviderName(),
				cryptoHashProviderResponse.getCryptoHashProviderProperties()));
	}

	@Override
	public boolean verify(
			byte[] input, byte[] hash,
			CryptoHashVerificationContext... cryptoHashVerificationContexts)
		throws Exception {

		for (CryptoHashVerificationContext cryptoHashVerificationContext :
				cryptoHashVerificationContexts) {

			MessageDigestCryptoHashProvider messageDigestCryptoHashProvider =
				new MessageDigestCryptoHashProvider(
					cryptoHashVerificationContext.getCryptoHashProviderName(),
					cryptoHashVerificationContext.
						getCryptoHashProviderProperties());

			// process pepper

			byte[] pepper = null;

			// process salt

			Optional<byte[]> optionalSalt =
				cryptoHashVerificationContext.getSaltOptional();

			CryptoHashProviderResponse hashProviderResponse =
				messageDigestCryptoHashProvider.generate(
					pepper, optionalSalt.orElse(null), input);

			input = hashProviderResponse.getHash();
		}

		return Arrays.equals(input, hash);
	}

	private final MessageDigestCryptoHashProvider
		_messageDigestCryptoHashProvider;

	private static class MessageDigestCryptoHashProvider {

		public MessageDigestCryptoHashProvider(
				String cryptoHashProviderName,
				Map<String, ?> cryptoHashProviderProperties)
			throws NoSuchAlgorithmException {

			_messageDigestCryptoHashProviderName = cryptoHashProviderName;
			_messageDigestCryptoHashProviderProperties =
				cryptoHashProviderProperties;

			_messageDigest = MessageDigest.getInstance(cryptoHashProviderName);
		}

		public CryptoHashProviderResponse generate(
			byte[] pepper, byte[] salt, byte[] input) {

			if (pepper == null) {
				pepper = new byte[0];
			}

			if (salt == null) {
				salt = new byte[0];
			}

			byte[] bytes = new byte[pepper.length + salt.length + input.length];

			System.arraycopy(pepper, 0, bytes, 0, pepper.length);
			System.arraycopy(salt, 0, bytes, pepper.length, salt.length);
			System.arraycopy(
				input, 0, bytes, pepper.length + salt.length, input.length);

			return new CryptoHashProviderResponse(
				_messageDigest.digest(bytes), salt,
				_messageDigestCryptoHashProviderName,
				_messageDigestCryptoHashProviderProperties);
		}

		public byte[] generateSalt() {
			int saltSize = MapUtil.getInteger(
				_messageDigestCryptoHashProviderProperties, "saltSize");

			byte[] salt = new byte[saltSize];

			for (int i = 0; i < saltSize; ++i) {
				salt[i] = SecureRandomUtil.nextByte();
			}

			return salt;
		}

		private final MessageDigest _messageDigest;
		private final String _messageDigestCryptoHashProviderName;
		private final Map<String, ?> _messageDigestCryptoHashProviderProperties;

	}

}