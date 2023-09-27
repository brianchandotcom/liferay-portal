/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.soap.extender.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Carlos Sierra Andrés
 */
@ExtendedObjectClassDefinition(
	category = "web-api", factoryInstanceLabelAttribute = "contextPaths"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.remote.soap.extender.internal.configuration.SoapExtenderConfiguration",
	localization = "content/Language", name = "soap-extender-configuration-name"
)
public interface SoapExtenderConfiguration {

	@Meta.AD(name = "context-paths", required = false)
	public String[] contextPaths();

	@Meta.AD(name = "jax-ws-handler-filters", required = false)
	public String[] jaxWsHandlerFilterStrings();

	@Meta.AD(name = "jax-ws-service-filters", required = false)
	public String[] jaxWsServiceFilterStrings();

	@Meta.AD(name = "soap-descriptor-builder", required = false)
	public String soapDescriptorBuilderFilter();

}