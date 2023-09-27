/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		deflt = "Default",
		description = "if-this-configuration-should-apply-to-a-specific-application,-then-specify-it-here.-otherwise,-leave-blank-or-enter-default",
		id = OAuth2ProviderScopeConstants.OSGI_JAXRS_NAME,
		name = "osgi-jaxrs-application-name", required = false
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