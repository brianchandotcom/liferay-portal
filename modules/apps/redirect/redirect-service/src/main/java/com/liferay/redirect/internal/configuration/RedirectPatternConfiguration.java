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
	public String[] patterns();

}