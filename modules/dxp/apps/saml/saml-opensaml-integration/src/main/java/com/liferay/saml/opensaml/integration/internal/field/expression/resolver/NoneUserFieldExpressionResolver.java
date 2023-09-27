/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.field.expression.resolver;

import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.saml.opensaml.integration.field.expression.resolver.UserFieldExpressionResolver;
import com.liferay.saml.opensaml.integration.resolver.UserResolver;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = {"display.index:Integer=0", "key=none"},
	service = UserFieldExpressionResolver.class
)
public class NoneUserFieldExpressionResolver
	implements UserFieldExpressionResolver {

	@Override
	public String getDescription(Locale locale) {
		return ResourceBundleUtil.getString(
			ResourceBundleUtil.getBundle(
				locale, NoneUserFieldExpressionResolver.class),
			"no-matching");
	}

	@Override
	public String resolveUserFieldExpression(
		Map<String, List<Serializable>> incomingAttributeValues,
		UserResolver.UserResolverSAMLContext userResolverSAMLContext) {

		return null;
	}

}