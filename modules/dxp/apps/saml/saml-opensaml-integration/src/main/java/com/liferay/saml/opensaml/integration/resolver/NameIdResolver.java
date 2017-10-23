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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

/**
 * @author Mika Koivisto
 * @author Carlos Sierra
 * @author Stian Sigvartsen
 */
public interface NameIdResolver extends Resolver {

	public String resolve(
			User user, String entityId, String format, String spQualifierName,
			boolean allowCreate,
			NameIdResolverSAMLContext nameIdResolverSAMLContext)
		throws PortalException;

	public interface NameIdResolverSAMLContext
		extends SAMLContext<NameIdResolver> {
	}

}