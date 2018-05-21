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

import com.liferay.saml.opensaml.integration.internal.transport.HttpClientOutTransport;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import org.opensaml.saml2.binding.encoding.HTTPSOAP11Encoder;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.OutTransport;

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
	public void encode(MessageContext messageContext)
		throws MessageEncodingException {

		super.encode(messageContext);

		OutTransport outTransport =
			messageContext.getOutboundMessageTransport();

		if (outTransport instanceof HttpClientOutTransport) {
			HttpClientOutTransport httpClientTransport =
				(HttpClientOutTransport)outTransport;

			HttpPost httpPost = httpClientTransport.getHttpPost();

			try {
				_httpClient.execute(httpPost);
			}
			catch (Exception e) {
				throw new MessageEncodingException(e);
			}
		}
	}

	private final HttpClient _httpClient;

}