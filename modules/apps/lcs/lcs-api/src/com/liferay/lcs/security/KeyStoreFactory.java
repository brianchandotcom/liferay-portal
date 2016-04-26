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

package com.liferay.lcs.security;

import java.io.FileInputStream;
import java.io.InputStream;

import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides {@link KeyStore} factory methods. The LCS system performs
 * cryptographic operations and needs the <code>KeyStore</code> object to hold
 * keys and certificates. Instances of loaded key stores are cached here and
 * accessed by a unique combination of the key store's path, type, and password
 * digest.
 *
 * @author  Igor Beslic
 * @version LCS 1.7.1
 * @since   LCS 0.1
 */
public class KeyStoreFactory {

	/**
	 * Returns the SHA digest for the value.
	 *
	 * @param  value the value
	 * @return the SHA digest for the value
	 * @throws Exception if an exception occurred
	 */
	public static String getDigestString(String value) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA");

		byte[] digest = messageDigest.digest(value.getBytes());

		return new String(digest);
	}

	/**
	 * Returns the {@link KeyStore} instance.
	 *
	 * <p>
	 * Each key store instance is uniquely defined by the
	 * <code>keyStorePath</code> parameter. The first access to the key store
	 * results in a new key store instance. All subsequent requests to the key
	 * store return a cached instance.
	 * </p>
	 *
	 * @param  keyStorePath the key store's path
	 * @param  keyStoreType the key store's type
	 * @param  keyStorePassword the key store's password
	 * @return the key store instance, or <code>null</code> if the key store's
	 *         path, type, or password is not valid
	 * @throws Exception if the key store was not found at the specified path,
	 *         if the key store type or password does not match, or if an
	 *         exception occurred
	 */
	public static KeyStore getInstance(
			String keyStorePath, String keyStoreType, String keyStorePassword)
		throws Exception {

		if (!validate(keyStorePath, keyStoreType, keyStorePassword)) {
			return null;
		}

		String passwordDigestString = getDigestString(keyStorePassword);

		KeyStore keyStore = _keyStores.get(
			keyStorePath + keyStoreType + passwordDigestString);

		if (keyStore != null) {
			return keyStore;
		}

		keyStore = KeyStore.getInstance(keyStoreType);

		InputStream inputStream = null;

		int index = keyStorePath.indexOf("classpath:");

		if (index != -1) {
			Class<?> clazz = KeyStoreFactory.class;

			inputStream = clazz.getResourceAsStream(
				keyStorePath.substring(index + 10));
		}
		else {
			inputStream = new FileInputStream(keyStorePath);
		}

		try {
			keyStore.load(inputStream, keyStorePassword.toCharArray());
		}
		finally {
			inputStream.close();
		}

		_keyStores.put(
			keyStorePath + keyStoreType + passwordDigestString, keyStore);

		return keyStore;
	}

	public static Date getSigningKeyExpirationDate(
			String keyStorePath, String keyStoreType, String keyStorePassword,
			String keyAlias)
		throws Exception {

		KeyStore.ProtectionParameter protectionParameter =
			new KeyStore.PasswordProtection(keyStorePassword.toCharArray());

		KeyStore keyStore = KeyStoreFactory.getInstance(
			keyStorePath, keyStoreType, keyStorePassword);

		KeyStore.PrivateKeyEntry privateKeyEntry =
			(KeyStore.PrivateKeyEntry)keyStore.getEntry(
				keyAlias, protectionParameter);

		X509Certificate x509Certificate =
			(X509Certificate)privateKeyEntry.getCertificate();

		return x509Certificate.getNotAfter();
	}

	/**
	 * Returns <code>true</code> if the key store's path, type, and password are
	 * valid.
	 *
	 * @param  keyStorePath the key store's path
	 * @param  keyStoreType the key store's type
	 * @param  keyStorePassword the key store's password
	 * @return <code>true</code> if the key store's path, type, and password are
	 *         valid; <code>false</code> otherwise
	 * @throws Exception if an exception occurred
	 */
	private static boolean validate(
			String keyStorePath, String keyStoreType, String keyStorePassword)
		throws Exception {

		if ((keyStorePath == null) || keyStorePath.equals("")) {
			return false;
		}

		if ((keyStoreType == null) || keyStoreType.equals("")) {
			return false;
		}

		if ((keyStorePassword == null) || keyStorePassword.equals("")) {
			return false;
		}

		return true;
	}

	private static Map<String, KeyStore> _keyStores =
		new HashMap<String, KeyStore>();

}