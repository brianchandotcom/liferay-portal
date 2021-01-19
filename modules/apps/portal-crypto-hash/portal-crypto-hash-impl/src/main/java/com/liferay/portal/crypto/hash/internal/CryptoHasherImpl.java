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

			// process salt

			Optional<byte[]> optionalSalt =
				cryptoHashVerificationContext.getSaltOptional();

			byte[] pepper = null;

			CryptoHashProviderResponse hashProviderResponse =
				messageDigestCryptoHashProvider.generate(
					pepper, optionalSalt.orElse(null), input);

			input = hashProviderResponse.getHash();
		}

		return _compare(input, hash);
	}

	/**
	 * A comparison algorithm that prevents timing attack
	 *
	 * @param bytes1 the input bytes
	 * @param bytes2 the expected bytes
	 * @return true if two given arrays of bytes are the same, otherwise false
	 */
	private boolean _compare(byte[] bytes1, byte[] bytes2) {
		int diff = bytes1.length ^ bytes2.length;

		for (int i = 0; (i < bytes1.length) && (i < bytes2.length); ++i) {
			diff |= bytes1[i] ^ bytes2[i];
		}

		if (diff == 0) {
			return true;
		}

		return false;
	}

	private final MessageDigestCryptoHashProvider
		_messageDigestCryptoHashProvider;

	private static final class CryptoHashProviderResponse {

		public CryptoHashProviderResponse(
			byte[] hash, byte[] salt, String name, Map<String, ?> properties) {

			_hash = hash;
			_salt = salt;
			_name = name;
			_properties = properties;
		}

		public String getCryptoHashProviderName() {
			return _name;
		}

		public Map<String, ?> getCryptoHashProviderProperties() {
			return _properties;
		}

		public byte[] getHash() {
			return _hash;
		}

		public byte[] getSalt() {
			return _salt;
		}

		private final byte[] _hash;
		private final String _name;
		private final Map<String, ?> _properties;
		private final byte[] _salt;

	}

	private static class MessageDigestCryptoHashProvider {

		public MessageDigestCryptoHashProvider(
				String cryptoHashProviderName,
				Map<String, ?> cryptoHashProviderProperties)
			throws NoSuchAlgorithmException {

			_cryptoHashProviderName = cryptoHashProviderName;
			_cryptoHashProviderProperties = cryptoHashProviderProperties;

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
				_messageDigest.digest(bytes), salt, _cryptoHashProviderName,
				_cryptoHashProviderProperties);
		}

		public byte[] generateSalt() {
			int saltSize = MapUtil.getInteger(
				_cryptoHashProviderProperties, "saltSize");

			byte[] salt = new byte[saltSize];

			for (int i = 0; i < saltSize; ++i) {
				salt[i] = SecureRandomUtil.nextByte();
			}

			return salt;
		}

		private final String _cryptoHashProviderName;
		private final Map<String, ?> _cryptoHashProviderProperties;
		private final MessageDigest _messageDigest;

	}

}