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

import java.io.FileOutputStream;

import java.security.KeyStore;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Igor Beslic
 */
@RunWith(JUnit4.class)
public class KeyStoreAdvisorTest extends BaseTest {

	@Test
	public void testGetKeyAlias() throws Exception {
		KeyStore keyStore = KeyStoreFactory.getInstance(
			KEY_STORE_PATH_DEFAULT_PASSWORD, "JCEKS");

		int buildNumber = 130;

		KeyStore.ProtectionParameter protectionParameter =
			new KeyStore.PasswordProtection("testpassword".toCharArray());

		while (buildNumber < 200) {
			keyStore.setEntry(
				DEFAULT_ALIAS + buildNumber, getSecretKeyEntry(),
				protectionParameter);

			buildNumber = buildNumber + 15;
		}

		keyStore.store(
			new FileOutputStream(KEY_STORE_PATH_DEFAULT_PASSWORD),
			"keystorepass".toCharArray());

		KeyStoreAdvisor keyStoreAdvisor = new KeyStoreAdvisor();

		Assert.assertEquals(
			DEFAULT_ALIAS,
			keyStoreAdvisor.getKeyAlias(0, DEFAULT_ALIAS, keyStore));
		Assert.assertEquals(
			DEFAULT_ALIAS + 130,
			keyStoreAdvisor.getKeyAlias(130, DEFAULT_ALIAS, keyStore));
		Assert.assertEquals(
			DEFAULT_ALIAS + 130,
			keyStoreAdvisor.getKeyAlias(140, DEFAULT_ALIAS, keyStore));
		Assert.assertEquals(
			DEFAULT_ALIAS + 145,
			keyStoreAdvisor.getKeyAlias(150, DEFAULT_ALIAS, keyStore));
		Assert.assertEquals(
			DEFAULT_ALIAS + 190,
			keyStoreAdvisor.getKeyAlias(205, DEFAULT_ALIAS, keyStore));
	}

}