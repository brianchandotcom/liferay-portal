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

package com.liferay.portal.vulcan.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Javier Gamarra
 */
@ExtendedObjectClassDefinition(
	category = "third-party", factoryInstanceLabelAttribute = "path",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration",
	localization = "content/Language", name = "headless-api-configuration-name"
)
public interface VulcanConfiguration {

	@Meta.AD(deflt = "/api", name = "path")
	public String path();

	@Meta.AD(deflt = "true", name = "graphql-api", required = false)
	public boolean graphQLEnabled();

	@Meta.AD(deflt = "true", name = "rest-api", required = false)
	public boolean restEnabled();

}