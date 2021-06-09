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