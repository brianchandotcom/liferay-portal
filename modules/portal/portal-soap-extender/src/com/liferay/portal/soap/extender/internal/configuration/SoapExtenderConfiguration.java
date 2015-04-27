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

package com.liferay.portal.soap.extender.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Carlos Sierra Andrés
 */
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.soap.extender.configuration.SoapExtenderConfiguration"
)
public interface SoapExtenderConfiguration {

	@Meta.AD(required = false)
	public String[] contextPaths();

	@Meta.AD(name = "soap.descriptor.builder", required = false)
	public String soapDescriptorBuilderFilter();

	@Meta.AD(name = "jaxws.handlers.filters", required = false)
	public String[] jaxwsHandlersFilters();

	@Meta.AD(name = "jaxws.service.filters", required = false)
	public String[] jaxwsServiceFilters();

}