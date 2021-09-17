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

import com.liferay.portal.kernel.util.ArrayUtil;

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
			_defaultX509TrustManager = _getX509TrustManager(null);
			_extraX509TrustManager = null;
			_trustSelfSignedCertificates = true;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public X509TrustManagerImpl(
		KeyStore keyStore, boolean trustSelfSignedCertificates) {

		try {
			_defaultX509TrustManager = _getX509TrustManager(null);

			if (keyStore != null) {
				_extraX509TrustManager = _getX509TrustManager(keyStore);
			}
			else {
				_extraX509TrustManager = null;
			}

			_trustSelfSignedCertificates = trustSelfSignedCertificates;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void checkClientTrusted(
			X509Certificate[] x509Certificates, String authType)
		throws CertificateException {

		if (!_trustSelfSignedCertificates || (x509Certificates.length != 1)) {
			try {
				_defaultX509TrustManager.checkClientTrusted(
					x509Certificates, authType);
			}
			catch (CertificateException certificateException) {
				if (_extraX509TrustManager != null) {
					_extraX509TrustManager.checkClientTrusted(
						x509Certificates, authType);
				}
				else {
					throw certificateException;
				}
			}
		}
	}

	@Override
	public void checkServerTrusted(
			X509Certificate[] x509Certificates, String authType)
		throws CertificateException {

		if (!_trustSelfSignedCertificates || (x509Certificates.length != 1)) {
			try {
				_defaultX509TrustManager.checkServerTrusted(
					x509Certificates, authType);
			}
			catch (CertificateException certificateException) {
				if (_extraX509TrustManager != null) {
					_extraX509TrustManager.checkServerTrusted(
						x509Certificates, authType);
				}
				else {
					throw certificateException;
				}
			}
		}
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		if (_extraX509TrustManager != null) {
			return ArrayUtil.append(
				_defaultX509TrustManager.getAcceptedIssuers(),
				_extraX509TrustManager.getAcceptedIssuers());
		}

		return _defaultX509TrustManager.getAcceptedIssuers();
	}

	private X509TrustManager _getX509TrustManager(KeyStore keyStore)
		throws Exception {

		TrustManagerFactory trustManagerFactory =
			TrustManagerFactory.getInstance(
				TrustManagerFactory.getDefaultAlgorithm());

		trustManagerFactory.init(keyStore);

		for (TrustManager trustManager :
				trustManagerFactory.getTrustManagers()) {

			if (trustManager instanceof X509TrustManager) {
				return (X509TrustManager)trustManager;
			}
		}

		return null;
	}

	private final X509TrustManager _defaultX509TrustManager;
	private final X509TrustManager _extraX509TrustManager;
	private final boolean _trustSelfSignedCertificates;

}