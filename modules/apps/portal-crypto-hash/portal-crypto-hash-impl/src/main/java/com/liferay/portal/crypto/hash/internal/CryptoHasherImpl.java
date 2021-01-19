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
import com.liferay.portal.kernel.security.SecureRandomUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@Component(service = CryptoHasher.class)
public class CryptoHasherImpl implements CryptoHasher {

	public CryptoHasherImpl() throws NoSuchAlgorithmException {
		_messageDigestCryptoHashProvider =
			new MessageDigestCryptoHashProvider();
	}

	@Override
	public CryptoHashGenerationResponse generate(byte[] input)
		throws Exception {

		byte[] salt = _messageDigestCryptoHashProvider.generateSalt();

		return new CryptoHashGenerationResponse(
			_messageDigestCryptoHashProvider.generate(salt, input), salt);
	}

	@Override
	public boolean verify(byte[] input, byte[] hash, byte[] salt)
		throws Exception {

		return _compare(
			_messageDigestCryptoHashProvider.generate(salt, input), hash);
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

	private static class MessageDigestCryptoHashProvider {

		public MessageDigestCryptoHashProvider()
			throws NoSuchAlgorithmException {

			_messageDigest = MessageDigest.getInstance("SHA-256");
		}

		public byte[] generate(byte[] salt, byte[] input) {
			byte[] bytes = new byte[salt.length + input.length];

			System.arraycopy(salt, 0, bytes, 0, salt.length);
			System.arraycopy(input, 0, bytes, salt.length, input.length);

			return _messageDigest.digest(bytes);
		}

		public byte[] generateSalt() {
			byte[] salt = new byte[16];

			for (int i = 0; i < 16; ++i) {
				salt[i] = SecureRandomUtil.nextByte();
			}

			return salt;
		}

		private final MessageDigest _messageDigest;

	}

}