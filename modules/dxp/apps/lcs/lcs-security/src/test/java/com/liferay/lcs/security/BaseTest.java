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

import java.io.File;
import java.io.FileOutputStream;

import java.security.KeyStore;

import javax.crypto.spec.SecretKeySpec;

import org.junit.After;
import org.junit.Before;

/**
 * @author Igor Beslic
 */
public class BaseTest {

	@Before
	public void setUp() throws Exception {
		createKeyStore(KEY_STORE_PATH_CUSTOM_PASSWORD, "JCEKS", "keystorepass");

		createKeyStore(KEY_STORE_PATH_DEFAULT_PASSWORD, "JCEKS", null);
	}

	@After
	public void tearDown() {
		File file = new File(KEY_STORE_PATH_CUSTOM_PASSWORD);

		file.delete();

		file = new File(KEY_STORE_PATH_DEFAULT_PASSWORD);

		file.delete();
	}

	protected KeyStore createKeyStore(
			String keyStorePath, String keyStoreType, String keyStorePassword)
		throws Exception {

		String password = "_k3y#5t0r3-p45S";

		if (keyStorePassword != null) {
			password = keyStorePassword;
		}

		FileOutputStream fileOutputStream = new FileOutputStream(keyStorePath);

		KeyStore keyStore = KeyStore.getInstance(keyStoreType);

		keyStore.load(null, null);

		KeyStore.ProtectionParameter protectionParameter =
			new KeyStore.PasswordProtection(password.toCharArray());

		keyStore.setEntry(
			DEFAULT_ALIAS, getSecretKeyEntry(), protectionParameter);

		keyStore.store(fileOutputStream, password.toCharArray());

		fileOutputStream.close();

		return keyStore;
	}

	protected KeyStore.SecretKeyEntry getSecretKeyEntry() {
		byte[] seed = "eru9tyighw34ilty348934i34uiq34q34ri".getBytes();

		return new KeyStore.SecretKeyEntry(
			new SecretKeySpec(seed, 0, seed.length, "AES"));
	}

	protected static final String DEFAULT_ALIAS = "testalias";

	protected static final String KEY_STORE_PATH_CUSTOM_PASSWORD =
		"keyStoreCustomPassword.jks";

	protected static final String KEY_STORE_PATH_DEFAULT_PASSWORD =
		"keyStoreDefaultPassword.jks";

}