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

package com.liferay.oauth2.provider.scope.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.oauth2.provider.scope.internal.constants.OAuth2ProviderScopeConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Stian Sigvartsen
 */
@ExtendedObjectClassDefinition(
	category = "oauth2",
	factoryInstanceLabelAttribute = OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.oauth2.provider.scope.internal.configuration.ScopeLocatorConfiguration",
	localization = "content/Language",
	name = "oauth2-scope-locator-configuration-name"
)
public interface ScopeLocatorConfiguration {

	@Meta.AD(
		deflt = "Default", description = "osgi-jaxrs-name-description",
		id = OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
		name = "osgi-jaxrs-name", required = false
	)
	public String osgiJaxRsName();

	@Meta.AD(
		deflt = "true",
		description = "include-scopes-implied-before-scope-mapping-description",
		id = "include.scopes.implied.before.scope.mapping",
		name = "include-scopes-implied-before-scope-mapping", required = false
	)
	public boolean includeScopesImpliedBeforeScopeMapping();

}