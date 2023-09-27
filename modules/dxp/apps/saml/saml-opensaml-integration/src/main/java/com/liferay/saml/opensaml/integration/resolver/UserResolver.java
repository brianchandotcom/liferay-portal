/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.resolver;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.saml.opensaml.integration.internal.resolver.SAMLCommands;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Mika Koivisto
 * @author Carlos Sierra
 * @author Stian Sigvartsen
 */
public interface UserResolver extends Resolver {

	public User resolveUser(
			UserResolverSAMLContext userResolverSAMLContext,
			ServiceContext serviceContext)
		throws Exception;

	public interface UserResolverSAMLContext extends SAMLContext<UserResolver> {

		public default Map<String, List<Serializable>>
			resolveBearerAssertionAttributesWithMapping(
				Properties userAttributeMappingsProperties) {

			return resolve(
				SAMLCommands.bearerAssertionAttributesWithMapping(
					userAttributeMappingsProperties));
		}

	}

}