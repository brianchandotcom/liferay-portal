/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.accessibility.menu.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Evan Thibodeau
 */
@ExtendedObjectClassDefinition(
	category = "accessibility",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.accessibility.menu.web.internal.configuration.AccessibilityMenuConfiguration",
	localization = "content/Language",
	name = "accessibility-menu-configuration-name"
)
public interface AccessibilityMenuConfiguration {

	@Meta.AD(
		deflt = "false", description = "enable-accessibility-menu-description",
		name = "enable-accessibility-menu", required = false
	)
	public boolean enableAccessibilityMenu();

}