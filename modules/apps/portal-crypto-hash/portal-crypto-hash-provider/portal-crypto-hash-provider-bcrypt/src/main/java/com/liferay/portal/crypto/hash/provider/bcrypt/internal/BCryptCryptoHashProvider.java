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

package com.liferay.portal.crypto.hash.provider.bcrypt.internal;

import com.liferay.portal.crypto.hash.spi.CryptoHashProvider;

import java.nio.charset.StandardCharsets;

import jodd.crypt.BCrypt;

/**
 * @author Arthur Chan
 */
public class BCryptCryptoHashProvider implements CryptoHashProvider {

	@Override
	public byte[] generate(byte[] salt, byte[] input) {
		String hashpw = BCrypt.hashpw(
			new String(input, StandardCharsets.US_ASCII),
			new String(salt, StandardCharsets.US_ASCII));

		return hashpw.getBytes(StandardCharsets.US_ASCII);
	}

	@Override
	public byte[] generateSalt() {
		String salt = BCrypt.gensalt();

		return salt.getBytes(StandardCharsets.US_ASCII);
	}

}