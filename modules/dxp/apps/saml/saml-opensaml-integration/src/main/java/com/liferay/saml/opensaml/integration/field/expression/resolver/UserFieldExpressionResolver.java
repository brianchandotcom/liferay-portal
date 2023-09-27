/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.field.expression.resolver;

import aQute.bnd.annotation.ProviderType;

import com.liferay.saml.opensaml.integration.resolver.UserResolver;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface UserFieldExpressionResolver {

	public String getDescription(Locale locale);

	public String resolveUserFieldExpression(
			Map<String, List<Serializable>> incomingAttributeValues,
			UserResolver.UserResolverSAMLContext userResolverSAMLContext)
		throws Exception;

}