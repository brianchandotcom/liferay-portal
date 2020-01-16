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