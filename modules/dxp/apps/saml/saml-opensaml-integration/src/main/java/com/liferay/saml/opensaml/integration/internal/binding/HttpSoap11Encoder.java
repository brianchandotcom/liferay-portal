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

			HttpPost postMethod = httpClientTransport.getHttpPost();

			try {
				_httpClient.execute(postMethod);
			}
			catch (Exception e) {
				throw new MessageEncodingException(e);
			}
		}
	}

	private final HttpClient _httpClient;

}