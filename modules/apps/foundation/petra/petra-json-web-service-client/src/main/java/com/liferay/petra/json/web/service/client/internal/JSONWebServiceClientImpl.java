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
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

import java.security.KeyStore;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.nio.reactor.IOReactorException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

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

		_setHeaders(_getString("headers", properties));

		setHostName(_getString("hostName", properties));
		setHostPort(Integer.parseInt(_getString("hostPort", properties)));
		setKeyStore((KeyStore)properties.get("keyStore"));
		setLogin(_getString("login", properties));
		setPassword(_getString("password", properties));
		setProtocol(_getString("protocol", properties));

		if (properties.containsKey("proxyAuthType")) {
			setProxyAuthType(_getString("proxyAuthType", properties));
			setProxyDomain(_getString("proxyDomain", properties));
			setProxyWorkstation(_getString("proxyWorkstation", properties));
		}

		if (properties.containsKey("proxyHostName")) {
			setProxyHostName(_getString("proxyHostName", properties));
			setProxyHostPort(
				Integer.parseInt(_getString("proxyHostPort", properties)));
			setProxyLogin(_getString("proxyLogin", properties));
			setProxyPassword(_getString("proxyPassword", properties));
		}

		afterPropertiesSet();
	}

	@Override
	public void afterPropertiesSet() throws IOReactorException {
		super.afterPropertiesSet();
	}

	@Deactivate
	protected void deactivate() {
		super.destroy();
	}

	@Override
	protected void signRequest(HttpRequestBase httpRequestBase)
		throws JSONWebServiceTransportException.SigningFailure {
	}

	private String _getString(String key, Map<String, Object> properties) {
		if (!properties.containsKey(key)) {
			return null;
		}

		return String.valueOf(properties.get(key));
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