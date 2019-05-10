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

package com.liferay.product.navigation.personal.menu.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Samuel Trong Tran
 */
@ExtendedObjectClassDefinition(
	category = "users", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.product.navigation.personal.menu.configuration.PersonalMenuConfiguration",
	localization = "content/Language", name = "personal-menu-configuration-name"
)
@ProviderType
public interface PersonalMenuConfiguration {

	@Meta.AD(
		deflt = "current-site",
		description = "personal-applications-look-and-feel-help",
		name = "personal-applications-look-and-feel",
		optionLabels = {"current-site", "my-dashboard"},
		optionValues = {"current-site", "my-dashboard"}, required = false
	)
	public String personalApplicationsLookAndFeel();

	@Meta.AD(
		deflt = "false", description = "show-in-control-menu-help",
		name = "show-in-control-menu", required = false
	)
	public boolean showInControlMenu();

}