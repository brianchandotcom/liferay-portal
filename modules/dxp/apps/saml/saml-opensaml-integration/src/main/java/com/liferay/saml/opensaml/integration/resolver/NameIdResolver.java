/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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