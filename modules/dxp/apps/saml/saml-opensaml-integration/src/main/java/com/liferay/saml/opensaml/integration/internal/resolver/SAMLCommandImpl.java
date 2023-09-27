/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.saml.opensaml.integration.resolver.Resolver;

import java.util.function.Function;

import org.opensaml.messaging.context.MessageContext;

/**
 * @author Tomas Polesovsky
 */
public class SAMLCommandImpl<MessageType, T, R extends Resolver>
	implements Resolver.SAMLCommand<T, R> {

	public SAMLCommandImpl(
		Function<MessageContext<MessageType>, T> messageContextFunction) {

		_messageContextFunction = messageContextFunction;
	}

	protected Function<MessageContext<MessageType>, T>
		getMessageContextFunction() {

		return _messageContextFunction;
	}

	private final Function<MessageContext<MessageType>, T>
		_messageContextFunction;

}