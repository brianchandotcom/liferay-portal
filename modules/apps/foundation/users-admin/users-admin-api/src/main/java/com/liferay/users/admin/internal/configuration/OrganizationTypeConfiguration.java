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
@ExtendedObjectClassDefinition(
	category = "foundation", scope = ExtendedObjectClassDefinition.Scope.SYSTEM,
	factoryInstanceLabelAttribute = "name"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.users.admin.internal.configuration.OrganizationTypeConfiguration",
	localization = "content/Language",
	name = "organization-type-configuration-name"
)
public interface OrganizationTypeConfiguration {

	@Meta.AD(
		name = "name",
		deflt = "organization", required = false
	)
	public String name();

	@Meta.AD(
		name = "country-enabled",
		deflt = "true", required = false
	)
	public boolean countryEnabled();

	@Meta.AD(
		name = "country-required",
		deflt = "false", required = false
	)
	public boolean countryRequired();

	@Meta.AD(
		name = "rootable",
		deflt = "true", required = false
	)
	public boolean rootable();

	@Meta.AD(
		name = "children-types",
		deflt = "", required = false
	)
	public String[] childrenTypes();

}