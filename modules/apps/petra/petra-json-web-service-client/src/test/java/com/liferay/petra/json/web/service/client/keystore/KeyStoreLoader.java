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

package com.liferay.petra.json.web.service.client.keystore;

import java.io.IOException;
import java.io.InputStream;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @author Igor Beslic
 */
public class KeyStoreLoader {

	public KeyStore getKeystore(
			String keyStoreFileName, String keyStorePassword)
		throws CertificateException, IOException, KeyStoreException,
			   NoSuchAlgorithmException {

		InputStream inputStream = null;

		try {
			Class<?> clazz = KeyStoreLoader.class;

			inputStream = clazz.getResourceAsStream(
				"dependencies/" + keyStoreFileName);

			KeyStore keyStore = KeyStore.getInstance("jks");

			keyStore.load(inputStream, keyStorePassword.toCharArray());

			return keyStore;
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

}