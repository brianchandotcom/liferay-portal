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

package com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Carlos Sierra Andrés
 */
@ExtendedObjectClassDefinition(
	category = "oauth2", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration.OAuth2AuthorizationFlowConfiguration",
	localization = "content/Language",
	name = "authorization-flow-configuration-name"
)
public interface OAuth2AuthorizationFlowConfiguration {

	@Meta.AD(
		deflt = "300", description = "authorization-code-grant-ttl-description",
		id = "authorization.code.grant.ttl",
		name = "authorization-code-grant-ttl", required = false
	)
	public int authorizationCodeGrantTTL();

}