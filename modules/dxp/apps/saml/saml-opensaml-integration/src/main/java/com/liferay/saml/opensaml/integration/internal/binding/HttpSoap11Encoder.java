/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.binding;

import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClients;

import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.httpclient.HttpClientRequestContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPSOAP11Encoder;

/**
 * @author Mika Koivisto
 */
public class HttpSoap11Encoder extends HTTPSOAP11Encoder {

	public HttpSoap11Encoder() {
		this(HttpClients.createDefault());
	}

	public HttpSoap11Encoder(HttpClient httpClient) {
		_httpClient = httpClient;
	}

	@Override
	public void encode() throws MessageEncodingException {
		super.encode();

		MessageContext<SAMLObject> messageContext = getMessageContext();

		HttpClientRequestContext httpClientRequestContext =
			messageContext.getSubcontext(HttpClientRequestContext.class, false);

		if (httpClientRequestContext != null) {
			HttpClientContext httpClientContext =
				httpClientRequestContext.getHttpClientContext();

			HttpRequest httpRequest = httpClientContext.getRequest();

			try {
				_httpClient.execute((HttpUriRequest)httpRequest);
			}
			catch (IOException ioException) {
				throw new MessageEncodingException(ioException);
			}
		}
	}

	private final HttpClient _httpClient;

}