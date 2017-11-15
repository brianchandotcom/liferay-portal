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

package com.liferay.saml.opensaml.integration.resolver;

import com.liferay.portal.kernel.model.User;
import com.liferay.saml.opensaml.integration.internal.resolver.SAMLCommands;

import java.net.URI;

import java.util.Date;
import java.util.List;

/**
 * @author Mika Koivisto
 * @author Carlos Sierra
 * @author Stian Sigvartsen
 */
public interface AttributeResolver extends Resolver {

	public void resolve(
		User user, AttributeResolverSAMLContext attributeResolverSAMLContext,
		AttributePublisher assetPublisher);

	public interface AttributePublisher {

		public AttributeValue buildBase64(String value);

		public AttributeValue buildBoolean(boolean value);

		public AttributeValue buildDateTime(Date value);

		public AttributeValue buildInt(int value);

		public AttributeValue buildString(String value);

		public AttributeValue buildURI(URI value);

		public void publish(String name, AttributeValue... attributeValues);

		public void publish(
			String name, String nameFormat, AttributeValue... attributeValues);

		public void publish(
			String name, String friendlyName, String nameFormat,
			AttributeValue... attributeValues);

		public interface AttributeValue {
		}

	}

	public interface AttributeResolverSAMLContext
		extends SAMLContext<AttributeResolver> {

		public default List<String> resolveSsoServicesLocationForBinding(
			String binding) {

			return resolve(SAMLCommands.ssoServicesLocationForBinding(binding));
		}

	}

}