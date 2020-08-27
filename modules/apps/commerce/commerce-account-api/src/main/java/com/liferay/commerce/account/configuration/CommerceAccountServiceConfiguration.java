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

package com.liferay.commerce.account.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "users", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.account.configuration.CommerceAccountServiceConfiguration",
	localization = "content/Language",
	name = "commerce-account-service-configuration-name"
)
public interface CommerceAccountServiceConfiguration {

	@Meta.AD(
		deflt = "false", name = "apply-default-role-to-existing-users",
		required = false
	)
	public boolean applyDefaultRoleToExistingUsers();

	@Meta.AD(name = "site-roles", required = false)
	public String[] siteRoles();

}