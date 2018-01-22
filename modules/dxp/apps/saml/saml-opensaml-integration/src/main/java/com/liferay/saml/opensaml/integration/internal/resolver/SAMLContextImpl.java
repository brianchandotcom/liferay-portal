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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.saml.opensaml.integration.resolver.Resolver;
import com.liferay.saml.opensaml.integration.resolver.Resolver.SAMLCommand;
import com.liferay.saml.opensaml.integration.resolver.Resolver.SAMLContext;

import java.util.function.Function;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.NameID;

/**
 * @author Carlos Sierra Andrés
 */
public class SAMLContextImpl
	<InboundMessageType extends SAMLObject,
		OutboundMessageType extends SAMLObject, R extends Resolver>
			implements SAMLContext<R> {

	public SAMLContextImpl(
		SAMLMessageContext<InboundMessageType, OutboundMessageType, NameID>
			samlMessageContext) {

		_samlMessageContext = samlMessageContext;
	}

	public <T> T resolve(SAMLCommand<T, ? super R> samlCommand) {
		Function
			<SAMLMessageContext
				<InboundMessageType, OutboundMessageType, NameID>, T> function =
					((SAMLCommandImpl
						<InboundMessageType, OutboundMessageType, T, R>)
							samlCommand).getSamlMessageContextFunction();

		return function.apply(_samlMessageContext);
	}

	private final SAMLMessageContext
		<InboundMessageType, OutboundMessageType, NameID> _samlMessageContext;

}