/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.saml.opensaml.integration.resolver.NameIdResolver;

import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.saml2.core.AuthnRequest;

/**
 * @author Carlos Sierra Andrés
 * @author Stian Sigvartsen
 */
public class NameIdResolverSAMLContextImpl
	extends SAMLContextImpl<AuthnRequest, NameIdResolver>
	implements NameIdResolver.NameIdResolverSAMLContext {

	public NameIdResolverSAMLContextImpl(
		MessageContext<AuthnRequest> messageContext) {

		super(messageContext);
	}

}