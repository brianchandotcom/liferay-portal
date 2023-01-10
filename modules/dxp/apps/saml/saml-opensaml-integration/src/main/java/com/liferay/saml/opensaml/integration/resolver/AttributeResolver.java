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

		public void publish(
			String name, String nameFormat, String... attributeValues);

	}

	public interface AttributeResolverSAMLContext
		extends SAMLContext<AttributeResolver> {

		public default List<String> resolveSsoServicesLocationForBinding(
			String binding) {

			return resolve(SAMLCommands.ssoServicesLocationForBinding(binding));
		}

	}

}