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