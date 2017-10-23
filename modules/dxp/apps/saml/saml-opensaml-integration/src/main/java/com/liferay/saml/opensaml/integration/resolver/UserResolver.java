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