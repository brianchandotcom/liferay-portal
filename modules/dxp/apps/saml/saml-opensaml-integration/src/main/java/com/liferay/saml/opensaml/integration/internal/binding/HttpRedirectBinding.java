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

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.decoding.HTTPRedirectDeflateDecoder;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.xml.parse.ParserPool;

/**
 * @author Mika Koivisto
 */
public class HttpRedirectBinding extends BaseSamlBinding {

	public HttpRedirectBinding(ParserPool parserPool) {
		super(
			new HTTPRedirectDeflateDecoder(parserPool),
			new HTTPRedirectDeflateEncoder());
	}

	@Override
	public String getCommunicationProfileId() {
		return SAMLConstants.SAML2_REDIRECT_BINDING_URI;
	}

}