/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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