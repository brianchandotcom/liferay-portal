/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "pages", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.GROUP, strictScope = true
)
@Meta.OCD(
	description = "redirect-pattern-configuration-description",
	id = "com.liferay.redirect.internal.configuration.RedirectPatternConfiguration",
	localization = "content/Language",
	name = "redirect-pattern-configuration-name"
)
public interface RedirectPatternConfiguration {

	@Meta.AD(
		description = "redirect-patterns",
		name = "redirect-patterns-description", required = false
	)
	public String[] patternStrings();

}