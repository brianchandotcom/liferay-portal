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

package com.liferay.site.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Mikel Lorza
 */
@ExtendedObjectClassDefinition(
	category = "site-configuration", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.site.configuration.MenuAccessConfiguration",
	localization = "content/Language"
)
public interface MenuAccessConfiguration {

	@Meta.AD(deflt = "false", required = false)
	public boolean showControlMenuByRole();

	@Meta.AD(deflt = "", required = false)
	public String[] accessToControlMenuRoleIds();

}