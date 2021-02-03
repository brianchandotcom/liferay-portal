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
import com.liferay.portal.crypto.hash.exception.CryptoHashException;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
@Component(service = CryptoHashGenerator.class)
public class CryptoHashGeneratorImpl implements CryptoHashGenerator {

	public CryptoHashGeneratorImpl() throws NoSuchAlgorithmException {
		_messageDigest = MessageDigest.getInstance("SHA-256");
	}

	@Override
	public CryptoHashResponse generate(byte[] input)
		throws CryptoHashException {

		byte[] salt = new byte[16];

		for (int i = 0; i < 16; ++i) {
			salt[i] = SecureRandomUtil.nextByte();
		}

		return new CryptoHashResponse(_digest(salt, input), salt);
	}

	@Override
	public boolean verify(byte[] input, byte[] hash, byte[] salt)
		throws CryptoHashException {

		return _compare(_digest(salt, input), hash);
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

	private byte[] _digest(byte[] salt, byte[] input) {
		return _messageDigest.digest(ArrayUtil.append(salt, input));
	}

	private final MessageDigest _messageDigest;

}