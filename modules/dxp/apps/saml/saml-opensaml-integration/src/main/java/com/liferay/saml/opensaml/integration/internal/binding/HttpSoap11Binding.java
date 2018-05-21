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