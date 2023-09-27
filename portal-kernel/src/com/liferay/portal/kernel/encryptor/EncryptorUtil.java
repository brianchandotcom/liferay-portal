/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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