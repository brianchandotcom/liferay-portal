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

package com.liferay.oauth2.provider.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Stian Sigvartsen
 */
@ExtendedObjectClassDefinition(
	category = "oauth2", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	localization = "content/Language",
	name = "oauth2-provider-configuration-name"
)
@ProviderType
public interface OAuth2ProviderConfiguration {

	@Meta.AD(
		deflt = "true",
		description = "oauth2-allow-authorization-code-grant-description",
		id = "oauth2.allow.authorization.code.grant",
		name = "oauth2-allow-authorization-code-grant", required = false
	)
	public boolean allowAuthorizationCodeGrant();

	@Meta.AD(
		deflt = "true",
		description = "oauth2-allow-authorization-code-pkce-grant-description",
		id = "oauth2.allow.authorization.code.pkce.grant",
		name = "oauth2-allow-authorization-code-pkce-grant", required = false
	)
	public boolean allowAuthorizationCodePKCEGrant();

	@Meta.AD(
		deflt = "true",
		description = "oauth2-allow-resource-owner-password-credentials-grant-description",
		id = "oauth2.allow.resource.owner.password.credentials.grant",
		name = "oauth2-allow-resource-owner-password-credentials-grant",
		required = false
	)
	public boolean allowResourceOwnerPasswordCredentialsGrant();

	@Meta.AD(
		deflt = "true",
		description = "oauth2-allow-client-credentials-grant-description",
		id = "oauth2.allow.client.credentials.grant",
		name = "oauth2-allow-client-credentials-grant", required = false
	)
	public boolean allowClientCredentialsGrant();

	@Meta.AD(
		deflt = "true",
		description = "oauth2-allow-refresh-token-grant-description",
		id = "oauth2.allow.refresh.token.grant",
		name = "oauth2-allow-refresh-token-grant", required = false
	)
	public boolean allowRefreshTokenGrant();

	@Meta.AD(
		deflt = "true",
		description = "oauth2-recycle-refresh-token-description",
		id = "oauth2.recycle.refresh.token",
		name = "oauth2-recycle-refresh-token", required = false
	)
	public boolean recycleRefreshToken();

}