/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.binding;

import net.shibboleth.utilities.java.support.xml.ParserPool;

import org.apache.velocity.app.VelocityEngine;

import org.opensaml.saml.common.xml.SAMLConstants;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPPostDecoder;
import org.opensaml.saml.saml2.binding.encoding.impl.HTTPPostEncoder;

/**
 * @author Mika Koivisto
 */
public class HttpPostBinding extends BaseSamlBinding {

	public HttpPostBinding(
		ParserPool parserPool, VelocityEngine velocityEngine) {

		super(
			() -> new HTTPPostDecoder() {
				{
					setParserPool(parserPool);
				}
			},
			() -> new HTTPPostEncoder() {
				{
					setVelocityEngine(velocityEngine);
				}
			});
	}

	@Override
	public String getCommunicationProfileId() {
		return SAMLConstants.SAML2_POST_BINDING_URI;
	}

}