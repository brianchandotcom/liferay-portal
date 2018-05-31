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

package com.liferay.portal.security.wedeploy.auth.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Supritha Sundaram
 */
@ExtendedObjectClassDefinition(category = "security-tools")
@Meta.OCD(
	id = "com.liferay.portal.security.wedeploy.auth.configuration.WeDeployAuthWebConfiguration",
	localization = "content/Language",
	name = "portal-security-wedeploy-auth-web-configuration-name"
)
public interface WeDeployAuthWebConfiguration {

	@Meta.AD(
		deflt = "3600000",
		description = "authorization-token-expiration-time-help",
		name = "authorization-token-expiration-time", required = false
	)
	public long authorizationTokenExpirationTime();

}