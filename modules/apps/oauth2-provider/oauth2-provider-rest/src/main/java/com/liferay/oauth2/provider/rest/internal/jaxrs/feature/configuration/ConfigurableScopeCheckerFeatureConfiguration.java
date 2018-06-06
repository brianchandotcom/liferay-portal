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

package com.liferay.oauth2.provider.rest.internal.jaxrs.feature.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "oauth2",
	factoryInstanceLabelAttribute = "osgi.jaxrs.application.select"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.oauth2.provider.rest.internal.jaxrs.feature.configuration.ConfigurableScopeCheckerFeatureConfiguration",
	localization = "content/Language",
	name = "oauth2-configurable-scope-checker-feature-configuration-name"
)
public interface ConfigurableScopeCheckerFeatureConfiguration {

	@Meta.AD(
		deflt = "(component.name=)",
		description = "osgi-jaxrs-application-select-description",
		id = "osgi.jaxrs.application.select",
		name = "osgi-jaxrs-application-select", required = true
	)
	public String osgiJAXRSSelect();

	@Meta.AD(
		deflt = "", description = "patterns-description", id = "patters",
		name = "patterns", required = true
	)
	public String[] patterns();

	@Meta.AD(
		deflt = "false", description = "allow-unmatched-description",
		id = "allow.unmatched", name = "allow-unmatched", required = false
	)
	public boolean allowUnmatched();

}