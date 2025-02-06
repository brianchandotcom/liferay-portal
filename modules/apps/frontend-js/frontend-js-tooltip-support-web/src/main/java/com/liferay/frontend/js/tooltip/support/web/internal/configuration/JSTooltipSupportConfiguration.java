/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.tooltip.support.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Bryce Osterhaus
 */
@ExtendedObjectClassDefinition(
	category = "infrastructure",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.frontend.js.tooltip.support.web.internal.configuration.JSTooltipSupportConfiguration",
	localization = "content/Language",
	name = "tooltip-support-configuration-name"
)
public interface JSTooltipSupportConfiguration {

	@Meta.AD(
		deflt = "false", description = "enable-native-tooltip-description",
		name = "enable-native-tooltip", required = false
	)
	public boolean enableNativeTooltip();

}