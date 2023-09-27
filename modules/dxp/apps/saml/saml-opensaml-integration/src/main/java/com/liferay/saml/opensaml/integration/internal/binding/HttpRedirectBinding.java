/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.binding;

import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPRedirectDeflateDecoder;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPRedirectDeflateEncoder;

/**
 * @author Mika Koivisto
 */
public class HttpRedirectBinding extends BaseSamlBinding {

	public HttpRedirectBinding(ParserPool parserPool) {
		super(
			() -> new HTTPRedirectDeflateDecoder() {
				{
					setParserPool(parserPool);
				}
			},
			() -> new HTTPRedirectDeflateEncoder());
	}

	@Override
	public String getCommunicationProfileId() {
		return SAMLConstants.SAML2_REDIRECT_BINDING_URI;
	}

}