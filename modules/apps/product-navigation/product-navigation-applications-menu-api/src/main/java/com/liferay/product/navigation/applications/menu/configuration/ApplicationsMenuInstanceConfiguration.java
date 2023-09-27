/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.applications.menu.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Eudaldo Alonso
 */
@ExtendedObjectClassDefinition(
	category = "navigation", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.product.navigation.applications.menu.configuration.ApplicationsMenuInstanceConfiguration",
	localization = "content/Language",
	name = "applications-menu-instance-configuration-name"
)
public interface ApplicationsMenuInstanceConfiguration {

	@Meta.AD(
		deflt = "true", description = "enable-applications-menu-description",
		name = "enable-applications-menu", required = false
	)
	public boolean enableApplicationsMenu();

}