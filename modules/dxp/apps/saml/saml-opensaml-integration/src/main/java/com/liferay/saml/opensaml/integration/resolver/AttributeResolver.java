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
		AttributePublisher attributePublisher);

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