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

package com.liferay.portal.kernel.test.util;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.util.Http;

import javax.servlet.http.Cookie;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mikel Lorza
 */
public class MockHttp implements Http {

	public MockHttp(
		Cookie[] cookies, boolean nonProxyHost, boolean proxyConfig,
		boolean proxyHost,
		Map<String, UnsafeSupplier<String, Exception>> requests) {

		if (requests != null) {
			_requests = Collections.unmodifiableMap(requests);
		}
		else {
			_requests = Collections.emptyMap();
		}
	}

	@Override
	public Cookie[] getCookies() {
		return _cookies;
	}

	@Override
	public boolean hasProxyConfig() {
		return _proxyConfig;
	}

	@Override
	public boolean isNonProxyHost(String host) {
		return _nonProxyHost;
	}

	@Override
	public boolean isProxyHost(String host) {
		return _proxyHost;
	}

	@Override
	public byte[] URLtoByteArray(Options options) throws IOException {
		return _getResponse(options.getLocation(), options);
	}

	@Override
	public byte[] URLtoByteArray(String location) throws IOException {
		return URLtoByteArray(location, false);
	}

	@Override
	public byte[] URLtoByteArray(String location, boolean post)
		throws IOException {

		return _getResponse(location, null);
	}

	@Override
	public InputStream URLtoInputStream(Options options) throws IOException {
		byte[] response = _getResponse(options.getLocation(), options);

		return new ByteArrayInputStream(response);
	}

	@Override
	public InputStream URLtoInputStream(String location) throws IOException {
		return URLtoInputStream(location, false);
	}

	@Override
	public InputStream URLtoInputStream(String location, boolean post)
		throws IOException {

		byte[] response = _getResponse(location, null);

		return new ByteArrayInputStream(response);
	}

	@Override
	public String URLtoString(Options options) throws IOException {
		byte[] response = _getResponse(options.getLocation(), options);

		return new String(response);
	}

	@Override
	public String URLtoString(String location) throws IOException {
		return URLtoString(location, false);
	}

	@Override
	public String URLtoString(String location, boolean post)
		throws IOException {

		byte[] response = _getResponse(location, null);

		return new String(response);
	}

	@Override
	public String URLtoString(URL url) throws IOException {
		byte[] response = _getResponse(url, null);

		return new String(response);
	}

	private byte[] _getResponse(String location, Options httpOptions)
		throws IOException {

		return _getResponse(new URL(location), httpOptions);
	}

	private byte[] _getResponse(URL url, Options httpOptions)
		throws IOException {

		if (_requests.containsKey(url.getPath())) {
			Response httpResponse = new Response();

			httpResponse.setResponseCode(200);

			httpOptions.setResponse(httpResponse);

			UnsafeSupplier<String, Exception> unsafeSupplier = _requests.get(
				url.getPath());

			try {
				String response = unsafeSupplier.get();

				return response.getBytes();
			}
			catch (Exception exception) {
				if (httpOptions == null) {
					throw new IOException(exception);
				}
			}
		}

		Response httpResponse = new Response();

		httpResponse.setResponseCode(400);

		httpOptions.setResponse(httpResponse);

		return "error".getBytes();
	}

	private final Cookie[] _cookies;
	private final boolean _nonProxyHost;
	private final boolean _proxyConfig;
	private final boolean _proxyHost;
	private final Map<String, UnsafeSupplier<String, Exception>> _requests;

}