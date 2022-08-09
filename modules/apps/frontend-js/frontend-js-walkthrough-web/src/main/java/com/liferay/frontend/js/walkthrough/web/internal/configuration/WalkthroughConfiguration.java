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

package com.liferay.frontend.js.walkthrough.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Matuzalem Teles
 */
@ExtendedObjectClassDefinition(
	category = "frontend-walkthrough",
	scope = ExtendedObjectClassDefinition.Scope.GROUP, strictScope = true
)
@Meta.OCD(
	id = "com.liferay.frontend.js.walkthrough.web.internal.configuration.WalkthroughConfiguration",
	localization = "content/Language", name = "walkthrough-configuration-name"
)
public @interface WalkthroughConfiguration {

	@Meta.AD(deflt = "false", name = "enable-walkthrough", required = false)
	public boolean enableWalkthrough();

	@Meta.AD(name = "steps-walkthrough", required = false)
	public String steps();

}