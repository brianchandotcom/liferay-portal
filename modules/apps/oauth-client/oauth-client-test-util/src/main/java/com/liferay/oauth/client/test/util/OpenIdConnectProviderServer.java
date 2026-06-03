/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.test.util;

import com.liferay.portal.kernel.json.JSONUtil;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.nio.charset.StandardCharsets;

/**
 * @author Christian Moura
 */
public class OpenIdConnectProviderServer implements AutoCloseable {

	public OpenIdConnectProviderServer() throws IOException {
		_httpServer = HttpServer.create(
			new InetSocketAddress("127.0.0.1", 0), 0);

		InetSocketAddress inetSocketAddress = _httpServer.getAddress();

		String issuerURL = "http://127.0.0.1:" + inetSocketAddress.getPort();

		String openIdConfigurationJSON = JSONUtil.put(
			"authorization_endpoint", issuerURL + "/authorize"
		).put(
			"id_token_signing_alg_values_supported", JSONUtil.putAll("RS256")
		).put(
			"issuer", issuerURL
		).put(
			"jwks_uri", issuerURL + "/certs"
		).put(
			"response_types_supported", JSONUtil.putAll("code")
		).put(
			"subject_types_supported", JSONUtil.putAll("public")
		).put(
			"token_endpoint", issuerURL + "/token"
		).put(
			"userinfo_endpoint", issuerURL + "/userinfo"
		).toString();

		byte[] openIdConfigurationBytes = openIdConfigurationJSON.getBytes(
			StandardCharsets.UTF_8);

		_httpServer.createContext(
			"/.well-known/openid-configuration",
			httpExchange -> {
				Headers responseHeaders = httpExchange.getResponseHeaders();

				responseHeaders.add("Content-Type", "application/json");

				httpExchange.sendResponseHeaders(
					200, openIdConfigurationBytes.length);

				try (OutputStream outputStream =
						httpExchange.getResponseBody()) {

					outputStream.write(openIdConfigurationBytes);
				}
			});

		_httpServer.start();

		_url = issuerURL + "/.well-known/openid-configuration";
	}

	@Override
	public void close() {
		_httpServer.stop(0);
	}

	public String getURL() {
		return _url;
	}

	private final HttpServer _httpServer;
	private final String _url;

}