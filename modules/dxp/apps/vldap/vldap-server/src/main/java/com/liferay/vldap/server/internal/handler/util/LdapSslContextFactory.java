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

package com.liferay.vldap.server.internal.handler.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class LdapSslContextFactory {

	public static SSLContext getSSLContext(boolean server) {
		return _instance._getSSLContext(server);
	}

	private LdapSslContextFactory() {
		SSLContext clientSSLContext = null;
		SSLContext serverSSLContext = null;

		try {
			clientSSLContext = _createClientSSLContext();
			serverSSLContext = _createServerSSLContext();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_clientSSLContext = clientSSLContext;
		_serverSSLContext = serverSSLContext;
	}

	private SSLContext _createClientSSLContext() throws Exception {
		SSLContext sslContext = SSLContext.getInstance(
			PortletPropsValues.SSL_PROTOCOL);

		sslContext.init(null, LdapTrustManagerFactory.TRUST_MANAGERS, null);

		return sslContext;
	}

	private SSLContext _createServerSSLContext() throws Exception {
		KeyStore keyStore = KeyStore.getInstance("JKS");

		InputStream inputStream = null;

		try {
			File file = new File(PortletPropsValues.SSL_KEYSTORE_FILE_NAME);

			if (!file.exists()) {
				throw new IOException(file.toString() + " does not exist");
			}

			inputStream = new FileInputStream(file);

			keyStore.load(
				inputStream, PortletPropsValues.SSL_KEYSTORE_PASSWORD);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (IOException ioe) {
					if (_log.isDebugEnabled()) {
						_log.debug(ioe, ioe);
					}
				}
			}
		}

		String algorithm = Security.getProperty(
			"ssl.KeyManagerFactory.algorithm");

		if (algorithm == null) {
			algorithm = KeyManagerFactory.getDefaultAlgorithm();
		}

		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
			algorithm);

		keyManagerFactory.init(
			keyStore, PortletPropsValues.SSL_KEYSTORE_PASSWORD);

		SSLContext sslContext = SSLContext.getInstance(
			PortletPropsValues.SSL_PROTOCOL);

		sslContext.init(
			keyManagerFactory.getKeyManagers(),
			LdapTrustManagerFactory.TRUST_MANAGERS, null);

		return sslContext;
	}

	private SSLContext _getSSLContext(boolean server) {
		if (server) {
			return _serverSSLContext;
		}
		else {
			return _clientSSLContext;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LdapSslContextFactory.class);

	private static final LdapSslContextFactory _instance =
		new LdapSslContextFactory();

	private final SSLContext _clientSSLContext;
	private final SSLContext _serverSSLContext;

}