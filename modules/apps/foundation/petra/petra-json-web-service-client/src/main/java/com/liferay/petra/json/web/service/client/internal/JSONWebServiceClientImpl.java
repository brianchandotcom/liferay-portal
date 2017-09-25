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

import com.liferay.petra.json.web.service.client.BaseJSONWebServiceClientImpl;

import java.security.KeyStore;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.nio.reactor.IOReactorException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(factory = "JSONWebServiceClient")
public class JSONWebServiceClientImpl extends BaseJSONWebServiceClientImpl {

	@Activate
	public void activate(Map<String, Object> properties)
		throws IOReactorException {

		_setHeaders(String.valueOf(properties.get("headers")));

		setHostName(String.valueOf(properties.get("hostName")));
		setHostPort(
			Integer.parseInt(String.valueOf(properties.get("hostPort"))));
		setKeyStore((KeyStore)properties.get("keyStore"));
		setLogin(String.valueOf(properties.get("login")));
		setPassword(String.valueOf(properties.get("password")));
		setProtocol(String.valueOf(properties.get("protocol")));

		if (properties.containsKey("proxyAuthType")) {
			setProxyAuthType(String.valueOf(properties.get("proxyAuthType")));
			setProxyDomain(String.valueOf(properties.get("proxyDomain")));
			setProxyWorkstation(
				String.valueOf(properties.get("proxyWorkstation")));
		}

		if (properties.containsKey("proxyHostName")) {
			setProxyHostName(String.valueOf(properties.get("proxyHostName")));
			setProxyHostPort(
				Integer.parseInt(
					String.valueOf(properties.get("proxyHostPort"))));
			setProxyLogin(String.valueOf(properties.get("proxyLogin")));
			setProxyPassword(String.valueOf(properties.get("proxyPassword")));
		}

		afterPropertiesSet();
	}

	public void afterPropertiesSet() throws IOReactorException {
		super.afterPropertiesSet();
	}

	private void _setHeaders(String headersString) {
		if (headersString == null) {
			return;
		}

		headersString = headersString.trim();

		if (headersString.length() < 3) {
			return;
		}

		Map<String, String> headers = new HashMap<String, String>();

		for (String header : headersString.split(";")) {
			String[] headerParts = header.split("=");

			if (headerParts.length != 2) {
				if (_logger.isDebugEnabled()) {
					_logger.debug("Ignoring invalid header " + header);
				}

				continue;
			}

			headers.put(headerParts[0], headerParts[1]);
		}

		setHeaders(headers);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		JSONWebServiceClientImpl.class);

}