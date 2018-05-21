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