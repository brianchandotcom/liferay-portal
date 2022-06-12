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

package com.liferay.object.storage.salesforce.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Guilherme Camacho
 */
@ExtendedObjectClassDefinition(
	category = "third-party", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.object.storage.salesforce.internal.configuration.SalesforceConfiguration",
	localization = "content/Language", name = "salesforce-configuration-name"
)
public interface SalesforceConfiguration {

	@Meta.AD(name = "login-url", required = false)
	public String loginURL();

	@Meta.AD(name = "consumer-key", required = false)
	public String consumerKey();

	@Meta.AD(name = "consumer-secret", required = false)
	public String consumerSecret();

	@Meta.AD(name = "username", required = false)
	public String username();

	@Meta.AD(name = "password", required = false, type = Meta.Type.Password)
	public String password();

}