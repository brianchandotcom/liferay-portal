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

package com.liferay.user.associated.data.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Fernando Vilela
 */
@ExtendedObjectClassDefinition(
	category = "users", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.user.associated.data.web.internal.configuration.UserLayoutConfiguration",
	localization = "content/Language", name = "user-layout-configuration-name"
)
public interface UserLayoutConfiguration {

	public String userPrivateLayout();

	public String userPrivateLayoutAutoCreate();

	public String userPublicLayout();

	public String userPublicLayoutAutoCreate();

}