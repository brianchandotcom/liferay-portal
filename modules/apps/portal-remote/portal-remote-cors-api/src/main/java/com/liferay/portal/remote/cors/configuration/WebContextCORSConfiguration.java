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

package com.liferay.portal.remote.cors.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "security-tools",
	factoryInstanceLabelAttribute = "servlet.context.helper.select.filter",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	description = "web-context-cors-configuration-description", factory = true,
	id = "com.liferay.portal.remote.cors.configuration.WebContextCORSConfiguration",
	localization = "content/Language",
	name = "web-context-cors-configuration-name"
)
public interface WebContextCORSConfiguration {

	@Meta.AD(
		deflt = "(&(!(liferay.cors=false))(osgi.jaxrs.name=*))",
		description = "servlet-context-helper-select-filter-description",
		id = "servlet.context.helper.select.filter",
		name = "servlet-context-helper-select-filter-name", required = false
	)
	public String servletContextHelperSelectFilter();

	@Meta.AD(
		deflt = "*",
		description = "cors-configuration-filter-mapping-url-pattern-description",
		id = "filter.mapping.url.patterns",
		name = "cors-configuration-filter-mapping-url-pattern", required = false
	)
	public String[] filterMappingURLPatterns();

	@Meta.AD(
		deflt = "Access-Control-Allow-Credentials: true|Access-Control-Allow-Headers: *|Access-Control-Allow-Methods: *|Access-Control-Allow-Origin: *",
		description = "cors-configuration-cors-headers-description",
		id = "headers", name = "cors-configuration-cors-headers",
		required = false
	)
	public String[] headers();

}