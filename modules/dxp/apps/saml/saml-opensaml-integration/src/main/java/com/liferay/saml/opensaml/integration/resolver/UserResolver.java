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