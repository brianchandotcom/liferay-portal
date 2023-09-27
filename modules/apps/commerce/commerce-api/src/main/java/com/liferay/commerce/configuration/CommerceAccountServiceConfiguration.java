/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "users", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.configuration.CommerceAccountServiceConfiguration",
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