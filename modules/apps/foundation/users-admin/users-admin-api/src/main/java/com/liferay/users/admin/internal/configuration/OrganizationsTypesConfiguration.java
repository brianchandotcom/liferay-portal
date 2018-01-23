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

package com.liferay.users.admin.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marco Leo
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.users.admin.internal.configuration.OrganizationsTypesConfiguration",
	localization = "content/Language",
	name = "organizations-types-configuration-name"
)
public interface OrganizationsTypesConfiguration {

	@Meta.AD(
		deflt = "{\"organization\":{\"childrenTypes\":[\"organization\"]\\,\"countryEnabled\":true\\,\"countryRequired\":false\\,\"rootable\":true}}",
		description = "organizations-types-help", name = "organizations-types",
		required = false
	)
	public String json();

}