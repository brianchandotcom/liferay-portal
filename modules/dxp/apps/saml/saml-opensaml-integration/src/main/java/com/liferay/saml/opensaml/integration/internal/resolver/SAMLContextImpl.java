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

	public <T> T resolve(Resolver.SAMLCommand<T, ? super R> samlCommand) {
		SAMLCommandImpl<MessageType, T, R> samlCommandImpl =
			(SAMLCommandImpl<MessageType, T, R>)samlCommand;

		Function<MessageContext<MessageType>, T> function =
			samlCommandImpl.getMessageContextFunction();

		return function.apply(_messageContext);
	}

	private final MessageContext<MessageType> _messageContext;

}