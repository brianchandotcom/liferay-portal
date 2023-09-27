/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.rest.extender.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Carlos Sierra Andrés
 */
@ExtendedObjectClassDefinition(
	category = "web-api", factoryInstanceLabelAttribute = "contextPaths",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.remote.rest.extender.configuration.RestExtenderConfiguration",
	localization = "content/Language", name = "rest-extender-configuration-name"
)
public interface RestExtenderConfiguration {

	@Meta.AD(name = "context-paths", required = false)
	public String[] contextPaths();

	@Meta.AD(name = "jax-rs-applications-filters", required = false)
	public String[] jaxRsApplicationFilterStrings();

	@Meta.AD(name = "jax-rs-provider-filters", required = false)
	public String[] jaxRsProviderFilterStrings();

	@Meta.AD(name = "jax-rs-service-filters", required = false)
	public String[] jaxRsServiceFilterStrings();

}