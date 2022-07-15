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

package com.liferay.oauth2.provider.rest.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Arthur Chan
 */
@ExtendedObjectClassDefinition(
	category = "oauth2", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.oauth2.provider.rest.internal.configuration.OAuth2AuthorizationServerConfiguration",
	localization = "content/Language",
	name = "oauth2-authorization-server-configuration-name"
)
public interface OAuth2AuthorizationServerConfiguration {

	@Meta.AD(
		deflt = "600",
		description = "oauth2-authorization-server-access-token-duration-description",
		id = "oauth2.authorization.server.access.token.duration",
		name = "oauth2-authorization-server-access-token-duration",
		required = false
	)
	public int accessTokenDuration();

	@Meta.AD(
		deflt = "604800",
		description = "oauth2-authorization-server-refresh-token-duration-description",
		id = "oauth2.authorization.server.refresh.token.duration",
		name = "oauth2-authorization-server-refresh-token-duration",
		required = false
	)
	public int refreshTokenDuration();

	@Meta.AD(
		deflt = "false",
		description = "oauth2-authorization-server-issue-jwt-access-token-description",
		id = "oauth2.authorization.server.issue.jwt.access.token",
		name = "oauth2-authorization-server-issue-jwt-access-token",
		required = false
	)
	public boolean issueJWTAccessToken();

	@Meta.AD(
		deflt = "{}",
		description = "oauth2-authorization-server-jwt-access-token-signing-json-web-key-description",
		id = "oauth2.authorization.server.jwt.access.token.signing.json.web.key",
		name = "oauth2-authorization-server-jwt-access-token-signing-json-web-key",
		required = false
	)
	public String jwtAccessTokenSigningJSONWebKey();

}