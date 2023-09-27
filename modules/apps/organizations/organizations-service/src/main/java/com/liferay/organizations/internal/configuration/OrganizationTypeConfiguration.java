/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.internal.configuration;

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
	id = "com.liferay.organizations.internal.configuration.OrganizationTypeConfiguration",
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