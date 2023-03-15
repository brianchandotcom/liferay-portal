/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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