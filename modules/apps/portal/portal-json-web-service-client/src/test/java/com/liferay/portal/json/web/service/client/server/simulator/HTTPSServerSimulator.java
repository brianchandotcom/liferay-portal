/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client.server.simulator;

import com.liferay.portal.json.web.service.client.keystore.KeyStoreLoader;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author Igor Beslic
 */
public class HTTPSServerSimulator {

	public static final String HOST_ADDRESS = "localhost";

	public static final int HOST_PORT = 9443;

	public static void start(String tlsVersion) throws IOException {
		if (_httpsServerSimulator != null) {
			throw new UnsupportedOperationException(
				"HTTP server is already running");
		}

		try {
			_httpsServerSimulator = new HTTPSServerSimulator(tlsVersion);
		}
		catch (Exception exception) {
			throw new HTTPSServerException(exception);
		}
	}

	public static void stop() {
		_httpsServer.stop(0);

		_httpsServer = null;

		_httpsServerSimulator = null;
	}

	private HTTPSServerSimulator(String tlsVersion)
		throws CertificateException, IOException, KeyManagementException,
			   KeyStoreException, NoSuchAlgorithmException,
			   UnrecoverableKeyException {

		_httpsServer = HttpsServer.create(new InetSocketAddress(HOST_PORT), 0);

		SSLContext sslContext = SSLContext.getInstance(tlsVersion);

		KeyStoreLoader keyStoreLoader = new KeyStoreLoader();

		KeyStore keyStore = keyStoreLoader.getKeyStore(
			"localhost.jks", "liferay");

		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
			"SunX509");

		keyManagerFactory.init(keyStore, "liferay".toCharArray());

		TrustManagerFactory trustManagerFactory =
			TrustManagerFactory.getInstance("SunX509");

		trustManagerFactory.init(keyStore);

		sslContext.init(
			keyManagerFactory.getKeyManagers(),
			trustManagerFactory.getTrustManagers(), null);

		_httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext));

		_httpsServer.createContext("/testGet/", new BaseHttpHandlerImpl());

		_httpsServer.start();
	}

	private static HttpsServer _httpsServer;
	private static HTTPSServerSimulator _httpsServerSimulator;

}