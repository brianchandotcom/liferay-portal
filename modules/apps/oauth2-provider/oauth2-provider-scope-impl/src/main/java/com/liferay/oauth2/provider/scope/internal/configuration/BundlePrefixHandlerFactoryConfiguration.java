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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "oauth2",
	factoryInstanceLabelAttribute = OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.oauth2.provider.scope.internal.configuration.BundlePrefixHandlerFactoryConfiguration",
	localization = "content/Language",
	name = "oauth2-bundle-prefix-handler-factory-configuration-name"
)
public interface BundlePrefixHandlerFactoryConfiguration {

	@Meta.AD(
		deflt = "Default", description = "osgi-jaxrs-name-description",
		id = OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
		name = "osgi-jaxrs-name", required = false
	)
	public String osgiJaxRsName();

	@Meta.AD(
		deflt = "true",
		description = "include-bundle-symbolic-name-description",
		id = "include.bundle.symbolic.name",
		name = "include-bundle-symbolic-name", required = false
	)
	public boolean includeBundleSymbolicName();

	@Meta.AD(
		deflt = "", description = "excluded-scopes-description",
		id = "excluded.scopes", name = "excluded-scopes", required = false
	)
	public String[] excludedScopes();

	@Meta.AD(
		deflt = OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
		description = "service-properties-description",
		id = "service.properties", name = "service-properties", required = false
	)
	public String[] serviceProperties();

	@Meta.AD(
		deflt = StringPool.SLASH, description = "separator-description",
		id = "separator", name = "separator", required = false
	)
	public String delimiter();

}