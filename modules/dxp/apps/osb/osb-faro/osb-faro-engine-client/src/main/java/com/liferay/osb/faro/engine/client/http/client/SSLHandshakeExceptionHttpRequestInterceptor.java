/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.http.client;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import javax.net.ssl.SSLHandshakeException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author Matthew Kong
 */
public class SSLHandshakeExceptionHttpRequestInterceptor
	implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(
			HttpRequest httpRequest, byte[] bytes,
			ClientHttpRequestExecution clientHttpRequestExecution)
		throws IOException {

		for (int i = 0; i < 5; i++) {
			try {
				return clientHttpRequestExecution.execute(httpRequest, bytes);
			}
			catch (SSLHandshakeException sslHandshakeException) {
				try {
					Thread.sleep(5000);
				}
				catch (Exception exception) {
					_log.error(sslHandshakeException, exception);

					throw new IOException(exception);
				}
			}
		}

		throw new IOException("Unable to recover from SSL Handshake exception");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SSLHandshakeExceptionHttpRequestInterceptor.class);

}