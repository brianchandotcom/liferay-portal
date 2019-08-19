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

package com.liferay.organizations.service.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marco Leo
 */
@ExtendedObjectClassDefinition(
	category = "users", factoryInstanceLabelAttribute = "name",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.organizations.service.internal.configuration.OrganizationTypeConfiguration",
	localization = "content/Language",
	name = "organization-type-configuration-name"
)
public interface OrganizationTypeConfiguration {

	@Meta.AD(deflt = "organization", name = "name", required = false)
	public String name();

	@Meta.AD(deflt = "true", name = "country-enabled", required = false)
	public boolean countryEnabled();

	@Meta.AD(deflt = "false", name = "country-required", required = false)
	public boolean countryRequired();

	@Meta.AD(deflt = "true", name = "rootable", required = false)
	public boolean rootable();

	@Meta.AD(deflt = "organization", name = "children-types", required = false)
	public String[] childrenTypes();

}