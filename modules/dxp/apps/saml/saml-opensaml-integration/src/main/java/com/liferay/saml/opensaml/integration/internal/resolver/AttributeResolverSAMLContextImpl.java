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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.saml.opensaml.integration.resolver.AttributeResolver;

import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.saml2.core.AuthnRequest;

/**
 * @author Carlos Sierra Andrés
 * @author Stian Sigvartsen
 */
public class AttributeResolverSAMLContextImpl
	extends SAMLContextImpl<AuthnRequest, AttributeResolver>
	implements AttributeResolver.AttributeResolverSAMLContext {

	public AttributeResolverSAMLContextImpl(
		MessageContext<AuthnRequest> messageContext) {

		super(messageContext);
	}

}