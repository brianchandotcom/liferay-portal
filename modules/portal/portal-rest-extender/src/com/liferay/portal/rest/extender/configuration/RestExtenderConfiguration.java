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

package com.liferay.portal.rest.extender.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Carlos Sierra Andrés
 */
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.rest.extender.configuration.RestExtenderConfiguration"
)
public interface RestExtenderConfiguration {

	@Meta.AD(name = "jaxrs.applications.filters", required = false)
	public String[] applicationFilters();

	@Meta.AD(required = false)
	public String[] contextPaths();

	@Meta.AD(name = "jaxrs.providers.filters", required = false)
	public String[] providerFilters();

	@Meta.AD(name = "jaxrs.service.filters", required = false)
	public String[] serviceFilters();

}