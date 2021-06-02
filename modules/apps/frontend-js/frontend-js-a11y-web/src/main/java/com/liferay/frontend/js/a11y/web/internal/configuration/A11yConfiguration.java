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

package com.liferay.frontend.js.a11y.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Matuzalem Teles
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	description = "a11y-configuration-description",
	id = "com.liferay.frontend.js.a11y.web.internal.configuration.A11yConfiguration",
	localization = "content/Language", name = "a11y-configuration-name"
)
public @interface A11yConfiguration {

	@Meta.AD(
		deflt = "false", description = "enable-description",
		name = "enable-name", required = false
	)
	public boolean enable();

	@Meta.AD(
		description = "denylist-description", name = "denylist-name",
		required = false
	)
	public String[] denylist();

}