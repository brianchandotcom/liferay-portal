/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.saml.opensaml.integration.resolver.NameIdResolver;

import java.util.function.Function;

import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;

/**
 * @author Tomas Polesovsky
 */
public class NameIdResolverSAMLCommand<T, R extends NameIdResolver>
	extends SAMLCommandImpl<SAMLObject, T, R> {

	public NameIdResolverSAMLCommand(
		Function<MessageContext<SAMLObject>, T> messageContextFunction) {

		super(messageContextFunction);
	}

}