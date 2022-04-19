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

package com.liferay.portal.kernel.encryptor;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.security.Key;

/**
 * @author Julius Lee
 */
public class EncryptorUtil {

	public static String decrypt(Key key, String encryptedString)
		throws EncryptorException {

		return _encryptor.decrypt(key, encryptedString);
	}

	public static byte[] decryptUnencodedAsBytes(Key key, byte[] encryptedBytes)
		throws EncryptorException {

		return _encryptor.decryptUnencodedAsBytes(key, encryptedBytes);
	}

	public static Key deserializeKey(String base64String) {
		return _encryptor.deserializeKey(base64String);
	}

	public static String encrypt(Key key, String plainText)
		throws EncryptorException {

		return _encryptor.encrypt(key, plainText);
	}

	public static byte[] encryptUnencoded(Key key, byte[] plainBytes)
		throws EncryptorException {

		return _encryptor.encryptUnencoded(key, plainBytes);
	}

	public static byte[] encryptUnencoded(Key key, String plainText)
		throws EncryptorException {

		return _encryptor.encryptUnencoded(key, plainText);
	}

	public static Key generateKey() throws EncryptorException {
		return _encryptor.generateKey();
	}

	public static String serializeKey(Key key) {
		return _encryptor.serializeKey(key);
	}

	private static volatile Encryptor _encryptor =
		ServiceProxyFactory.newServiceTrackedInstance(
			Encryptor.class, EncryptorUtil.class, "_encryptor", false);

}