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

package com.liferay.portal.search.solr.internal.http;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.solr.configuration.SolrSSLSocketFactoryConfiguration;
import com.liferay.portal.search.solr.http.KeyStoreLoader;
import com.liferay.portal.search.solr.http.SSLSocketFactoryBuilder;

import java.security.KeyStore;

import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author László Csontos
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr.configuration.SolrSSLSocketFactoryConfiguration",
	immediate = true, service = SSLSocketFactoryBuilder.class
)
public class SSLSocketFactoryBuilderImpl implements SSLSocketFactoryBuilder {

	@Override
	public SSLConnectionSocketFactory build() throws Exception {
		KeyStore keyStore = _keyStoreLoader.load(
			_keyStoreType, _keyStorePath, _keyStorePassword);

		if (keyStore == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Use system defaults because there is no custom key store");
			}

			return SSLConnectionSocketFactory.getSystemSocketFactory();
		}

		KeyStore trustKeyStore = null;

		TrustStrategy trustStrategy = null;

		if (_verifyServerCertificate) {
			trustKeyStore = _keyStoreLoader.load(
				_trustStoreType, _trustStorePath, _trustStorePassword);

			if (trustKeyStore == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Use system defaults because there is no custom " +
							"trust store");
				}

				return SSLConnectionSocketFactory.getSystemSocketFactory();
			}
		}
		else {
			trustStrategy = new TrustSelfSignedStrategy();
		}

		HostnameVerifier hostnameVerifier = null;

		if (_verifyServerHostname) {
			hostnameVerifier =
				SSLConnectionSocketFactory.getDefaultHostnameVerifier();
		}

		SSLContextBuilder sslContextBuilder = SSLContexts.custom();

		sslContextBuilder.loadKeyMaterial(keyStore, _keyStorePassword);
		sslContextBuilder.loadTrustMaterial(trustStrategy);

		SSLContext sslContext = sslContextBuilder.build();

		try {
			return new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Use system defaults because the custom SSL socket " +
						"factory was not able to initialize",
					e);
			}

			return SSLConnectionSocketFactory.getSystemSocketFactory();
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_solrSSLSocketFactoryConfiguration = Configurable.createConfigurable(
			SolrSSLSocketFactoryConfiguration.class, properties);

		String keyStorePassword =
			_solrSSLSocketFactoryConfiguration.keyStorePassword();

		_keyStorePassword = keyStorePassword.toCharArray();

		_keyStorePath = _solrSSLSocketFactoryConfiguration.keyStorePath();
		_keyStoreType = _solrSSLSocketFactoryConfiguration.keyStoreType();

		String trustStorePassword =
			_solrSSLSocketFactoryConfiguration.trustStorePassword();

		_trustStorePassword = trustStorePassword.toCharArray();

		_trustStorePath = _solrSSLSocketFactoryConfiguration.trustStorePath();
		_trustStoreType = _solrSSLSocketFactoryConfiguration.trustStoreType();
		_verifyServerCertificate =
			_solrSSLSocketFactoryConfiguration.verifyServerCertificate();
		_verifyServerHostname =
			_solrSSLSocketFactoryConfiguration.verifyServerName();
	}

	@Reference(unbind = "-")
	protected void setKeyStoreLoader(KeyStoreLoader keyStoreLoader) {
		_keyStoreLoader = keyStoreLoader;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SSLSocketFactoryBuilderImpl.class);

	private volatile KeyStoreLoader _keyStoreLoader;
	private char[] _keyStorePassword;
	private String _keyStorePath;
	private String _keyStoreType = KeyStore.getDefaultType();
	private volatile SolrSSLSocketFactoryConfiguration
		_solrSSLSocketFactoryConfiguration;
	private char[] _trustStorePassword;
	private String _trustStorePath;
	private String _trustStoreType = KeyStore.getDefaultType();
	private boolean _verifyServerCertificate = true;
	private boolean _verifyServerHostname = true;

}