/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.jaxrs.feature.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "oauth2", factoryInstanceLabelAttribute = "osgi.jaxrs.name"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.oauth2.provider.rest.internal.jaxrs.feature.configuration.ConfigurableScopeCheckerFeatureConfiguration",
	localization = "content/Language",
	name = "configurable-scope-checker-feature-configuration-name"
)
public interface ConfigurableScopeCheckerFeatureConfiguration {

	@Meta.AD(
		deflt = "(component.name=)",
		description = "osgi-jaxrs-application-select-description",
		id = "osgi.jaxrs.application.select",
		name = "osgi-jaxrs-application-select"
	)
	public String osgiJaxRsSelect();

	@Meta.AD(
		deflt = "Liferay.OAuth2.HTTP.configurable.request.checker",
		description = "set-the-name-for-this-jaxrs-instance",
		id = "osgi.jaxrs.name", name = "name"
	)
	public String osgiJaxRsName();

	@Meta.AD(
		deflt = "", description = "patterns-description", id = "patters",
		name = "patterns"
	)
	public String[] patterns();

	@Meta.AD(
		deflt = "false", description = "allow-unmatched-description",
		id = "allow.unmatched", name = "allow-unmatched", required = false
	)
	public boolean allowUnmatched();

}