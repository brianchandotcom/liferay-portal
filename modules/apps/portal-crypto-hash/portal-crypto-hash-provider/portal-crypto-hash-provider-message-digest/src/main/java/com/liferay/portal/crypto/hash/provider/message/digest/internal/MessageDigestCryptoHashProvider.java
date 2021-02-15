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

package com.liferay.portal.crypto.hash.provider.message.digest.internal;

import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Arthur Chan
 */
public class MessageDigestCryptoHashProvider implements CryptoHashProvider {

	public MessageDigestCryptoHashProvider() throws NoSuchAlgorithmException {
		_messageDigest = MessageDigest.getInstance("SHA-256");
	}

	@Override
	public byte[] generate(byte[] salt, byte[] input) {
		return _messageDigest.digest(ArrayUtil.append(salt, input));
	}

	private final MessageDigest _messageDigest;

}