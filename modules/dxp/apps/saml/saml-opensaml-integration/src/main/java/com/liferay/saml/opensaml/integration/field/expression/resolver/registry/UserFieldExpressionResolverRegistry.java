/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.field.expression.resolver.registry;

import aQute.bnd.annotation.ProviderType;

import com.liferay.saml.opensaml.integration.field.expression.resolver.UserFieldExpressionResolver;

import java.util.List;
import java.util.Map;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface UserFieldExpressionResolverRegistry {

	public String getDefaultUserFieldExpressionResolverKey();

	public List<Map.Entry<String, UserFieldExpressionResolver>>
		getOrderedUserFieldExpressionResolvers();

	public UserFieldExpressionResolver getUserFieldExpressionResolver(
		String key);

}