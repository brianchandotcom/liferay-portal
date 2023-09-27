/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		description = "oauth2-allow-jwt-bearer-grant-description",
		id = "oauth2.allow.jwt.bearer.grant",
		name = "oauth2-allow-jwt-bearer-grant", required = false
	)
	public boolean allowJWTBearerGrant();

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

	@Meta.AD(
		deflt = "86400",
		description = "oauth2-expired-authorizations-afterlife-duration-description",
		id = "oauth2.expired.authorizations.afterlife.duration",
		name = "oauth2-expired-authorizations-afterlife-duration",
		required = false
	)
	public int expiredAuthorizationsAfterlifeDuration();

	@Meta.AD(
		deflt = "60",
		description = "oauth2-expired-authorizations-check-interval-description",
		id = "oauth2.expired.authorizations.check.interval",
		name = "oauth2-expired-authorizations-check-interval", required = false
	)
	public int expiredAuthorizationsCheckInterval();

}