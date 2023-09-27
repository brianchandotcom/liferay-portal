/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.saml.opensaml.integration.resolver.Resolver;

import java.util.function.Function;

import org.opensaml.messaging.context.MessageContext;

/**
 * @author Carlos Sierra Andrés
 */
public class SAMLContextImpl<MessageType, R extends Resolver>
	implements Resolver.SAMLContext<R> {

	public SAMLContextImpl(MessageContext<MessageType> messageContext) {
		_messageContext = messageContext;
	}

	@Override
	public <T> T resolve(Resolver.SAMLCommand<T, ? super R> samlCommand) {
		SAMLCommandImpl<MessageType, T, R> samlCommandImpl =
			(SAMLCommandImpl<MessageType, T, R>)samlCommand;

		Function<MessageContext<MessageType>, T> function =
			samlCommandImpl.getMessageContextFunction();

		return function.apply(_messageContext);
	}

	private final MessageContext<MessageType> _messageContext;

}