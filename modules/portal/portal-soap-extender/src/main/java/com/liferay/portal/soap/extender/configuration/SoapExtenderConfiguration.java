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

package com.liferay.portal.soap.extender.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Carlos Sierra Andrés
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.soap.extender.configuration.SoapExtenderConfiguration"
)
public interface SoapExtenderConfiguration {

	@Meta.AD(required = false)
	public String[] contextPaths();

	@Meta.AD(name = "jax.ws.handler.filters", required = false)
	public String[] jaxWsHandlerFilterStrings();

	@Meta.AD(name = "jax.ws.service.filters", required = false)
	public String[] jaxWsServiceFilterStrings();

	@Meta.AD(name = "soap.descriptor.builder", required = false)
	public String soapDescriptorBuilderFilter();

}