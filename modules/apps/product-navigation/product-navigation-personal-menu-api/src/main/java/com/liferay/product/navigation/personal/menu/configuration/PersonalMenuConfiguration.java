/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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