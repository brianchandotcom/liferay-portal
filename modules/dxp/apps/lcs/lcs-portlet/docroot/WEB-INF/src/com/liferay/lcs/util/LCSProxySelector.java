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

package com.liferay.lcs.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mladen Cikara
 */
public class LCSProxySelector extends ProxySelector {

	public LCSProxySelector() {
		Authenticator.setDefault(new LCSAuthenticator());

		ProxySelector.setDefault(this);
	}

	@Override
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
		if (uri == null) {
			throw new IllegalArgumentException("URI is null");
		}

		_logger.error("Unable to connect: " + sa.toString());
	}

	@Override
	public List<Proxy> select(URI uri) {
		if (uri == null) {
			throw new IllegalArgumentException("URI is null");
		}

		if (_logger.isTraceEnabled()) {
			_logger.trace("URI: " + uri.toString());
		}

		List<Proxy> proxies = new ArrayList<>();

		if (Validator.isNotNull(PortletPropsValues.PROXY_HOST_NAME)) {
			String uriString = uri.toString();

			if (uriString.contains(
					PortletPropsValues.OSB_LCS_GATEWAY_WEB_HOST_NAME) ||
				uriString.contains(
					PortletPropsValues.OSB_LCS_PORTLET_HOST_NAME)) {

				if (_logger.isDebugEnabled()) {
					_logger.debug("Returning local proxy");
				}

				proxies.add(
					getProxy(
						PortletPropsValues.PROXY_HOST_NAME,
						PortletPropsValues.PROXY_HOST_PORT));

				return proxies;
			}
		}

		if (_defaultProxySelector != null) {
			if (_logger.isTraceEnabled()) {
				_logger.trace("Returning proxy from default proxy selector");
			}

			return _defaultProxySelector.select(uri);
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug("Returning no proxy");
		}

		proxies.add(Proxy.NO_PROXY);

		return proxies;
	}

	protected Proxy getProxy(String proxyHostName, int proxyPort) {
		SocketAddress socketAddress = new InetSocketAddress(
			proxyHostName, proxyPort);

		Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);

		return proxy;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LCSProxySelector.class);

	private static final ProxySelector _defaultProxySelector =
		ProxySelector.getDefault();

	private class LCSAuthenticator extends Authenticator {

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			String requestingHost = getRequestingHost();

			if (requestingHost == null) {
				_logger.error("Requesting host is null");

				return null;
			}

			String localProxyHostName = PortletPropsValues.PROXY_HOST_NAME;

			if (Validator.isNotNull(localProxyHostName) &&
				StringUtil.equalsIgnoreCase(
					localProxyHostName, requestingHost)) {

				String password = PortletPropsValues.PROXY_HOST_PASSWORD;

				if (password == null) {
					_logger.error("Proxy host password is null");
				}

				if (_logger.isDebugEnabled()) {
					_logger.debug(
						"Returning password authentication for local proxy");
				}

				PasswordAuthentication passwordAuthentication =
					new PasswordAuthentication(
						PortletPropsValues.PROXY_HOST_LOGIN,
						password.toCharArray());

				return passwordAuthentication;
			}

			String protocol = StringUtil.toLowerCase(getRequestingProtocol());

			String globalProxyHostName = System.getProperty(
				protocol + ".proxyHost");

			if (Validator.isNotNull(globalProxyHostName) &&
				StringUtil.equalsIgnoreCase(
					globalProxyHostName, requestingHost)) {

				String user = System.getProperty(protocol + ".proxyUser");
				String password = System.getProperty(
					protocol + ".proxyPassword");

				if ((user == null) || (password == null)) {
					return null;
				}

				if (_logger.isDebugEnabled()) {
					_logger.debug(
						"Returning password authentication for global " +
							protocol + " proxy");
				}

				PasswordAuthentication passwordAuthentication =
					new PasswordAuthentication(user, password.toCharArray());

				return passwordAuthentication;
			}

			_logger.error("Unable to find appropriate password authentication");

			return null;
		}

		private final Logger _logger = LoggerFactory.getLogger(
			LCSAuthenticator.class);

	}

}