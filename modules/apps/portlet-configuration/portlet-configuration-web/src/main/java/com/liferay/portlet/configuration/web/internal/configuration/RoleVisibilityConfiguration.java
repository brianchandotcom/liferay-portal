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

package com.liferay.portlet.configuration.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Peter Petrekanics
 */
@ExtendedObjectClassDefinition(category = "users")
@Meta.OCD(
	id = "com.liferay.portlet.configuration.web.internal.configuration.RoleVisibilityConfiguration",
	localization = "content/Language",
	name = "role-visibility-configuration-name"
)
public interface RoleVisibilityConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "restrict-permission-selector-role-visibility-description",
		name = "restrict-permission-selector-role-visibility-name",
		required = false
	)
	public boolean restrictPermissionSelectorRoleVisibility();

}