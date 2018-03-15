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

import org.apache.http.client.HttpClient;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.decoding.HTTPSOAP11Decoder;
import org.opensaml.xml.parse.ParserPool;

/**
 * @author Mika Koivisto
 */
public class HttpSoap11Binding extends BaseSamlBinding {

	public HttpSoap11Binding(ParserPool parserPool, HttpClient httpClient) {
		super(
			new HTTPSOAP11Decoder(parserPool),
			new HttpSoap11Encoder(httpClient));
	}

	@Override
	public String getCommunicationProfileId() {
		return SAMLConstants.SAML2_SOAP11_BINDING_URI;
	}

}