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

package com.liferay.client.extension.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(
	generateUI = false, scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.client.extension.configuration.ClientExtensionConfiguration",
	localization = "content/Language",
	name = "client-extension-configuration-name"
)
public interface ClientExtensionConfiguration {

	@Meta.AD(name = "base-url", type = Meta.Type.String)
	public String baseURL();

	@Meta.AD(name = "name", type = Meta.Type.String)
	public String name();

	@Meta.AD(name = "description", required = false, type = Meta.Type.String)
	public String description();

	@Meta.AD(
		name = "source-code-url", required = false, type = Meta.Type.String
	)
	public String sourceCodeURL();

	@Meta.AD(name = "type", type = Meta.Type.String)
	public String type();

	@Meta.AD(name = "type-settings", required = false, type = Meta.Type.String)
	public String[] typeSettings();

}