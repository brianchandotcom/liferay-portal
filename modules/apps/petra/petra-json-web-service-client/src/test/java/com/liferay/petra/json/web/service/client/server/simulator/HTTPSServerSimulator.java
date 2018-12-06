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

package com.liferay.petra.json.web.service.client.server.simulator;

import com.liferay.petra.json.web.service.client.keystore.KeyStoreLoader;

import com.sun.net.httpserver.HttpHandler;
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
		catch (Exception e) {
			throw new HTTPSServerException(e);
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

		KeyStore keyStore = keyStoreLoader.getKeystore(
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

		HttpHandler httpHandler = new BaseHttpHandlerImpl();

		_httpsServer.createContext("/testGet/", httpHandler);

		_httpsServer.start();
	}

	private static HttpsServer _httpsServer;
	private static HTTPSServerSimulator _httpsServerSimulator;

}