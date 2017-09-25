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

package com.liferay.petra.json.web.service.client.internal;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class X509TrustManagerImpl implements X509TrustManager {

	public X509TrustManagerImpl() {
		try {
			X509TrustManager x509TrustManager = null;

			TrustManagerFactory trustManagerFactory =
				TrustManagerFactory.getInstance(
					TrustManagerFactory.getDefaultAlgorithm());

			trustManagerFactory.init((KeyStore)null);

			for (TrustManager trustManager :
					trustManagerFactory.getTrustManagers()) {

				if (trustManager instanceof X509TrustManager) {
					x509TrustManager = (X509TrustManager)trustManager;

					break;
				}
			}

			_x509TrustManager = x509TrustManager;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void checkClientTrusted(
			X509Certificate[] x509Certificates, String authType)
		throws CertificateException {

		if (x509Certificates.length != 1) {
			_x509TrustManager.checkClientTrusted(x509Certificates, authType);
		}
	}

	@Override
	public void checkServerTrusted(
			X509Certificate[] x509Certificates, String authType)
		throws CertificateException {

		if (x509Certificates.length != 1) {
			_x509TrustManager.checkServerTrusted(x509Certificates, authType);
		}
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return _x509TrustManager.getAcceptedIssuers();
	}

	private final X509TrustManager _x509TrustManager;

}