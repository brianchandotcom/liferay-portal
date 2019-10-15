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