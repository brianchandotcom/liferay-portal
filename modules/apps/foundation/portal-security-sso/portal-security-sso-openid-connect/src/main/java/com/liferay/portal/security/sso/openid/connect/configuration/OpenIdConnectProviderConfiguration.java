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

package com.liferay.portal.security.sso.openid.connect.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(
	category = "foundation", factoryInstanceLabelAttribute = "providerName",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectProviderConfiguration",
	localization = "content/Language",
	name = "open.id.connect.provider.configuration.name"
)
public interface OpenIdConnectProviderConfiguration {

	@Meta.AD(
		deflt = "google", description = "provider-name-help", required = false
	)
	public String providerName();

	@Meta.AD(
		deflt = "", description = "open-id-connect-client-id-help",
		required = false
	)
	public String openIdConnectClientId();

	@Meta.AD(
		deflt = "", description = "open-id-connect-client-secret-help",
		required = false
	)
	public String openIdConnectClientSecret();

	@Meta.AD(
		deflt = "https://accounts.google.com/o/oauth2/v2/auth",
		description = "authorization-end-point-help", required = false
	)
	public String authorizationEndPoint();

	@Meta.AD(
		deflt = "https://accounts.google.com/.well-known/openid-configuration",
		description = "discovery-end-point-help", required = false
	)
	public String discoveryEndPoint();

	@Meta.AD(
		deflt = "https://accounts.google.com", description = "issuer-url-help",
		required = false
	)
	public String issuerUrl();

	@Meta.AD(
		deflt = "https://www.googleapis.com/oauth2/v3/certs",
		description = "jwks-uri-help", required = false
	)
	public String jwksUri();

	@Meta.AD(
		deflt = "public", description = "subject-types-help", required = false
	)
	public String[] subjectTypes();

	@Meta.AD(
		deflt = "https://www.googleapis.com/oauth2/v4/token",
		description = "token-end-point-help", required = false
	)
	public String tokenEndPoint();

	@Meta.AD(
		deflt = "https://www.googleapis.com/oauth2/v3/userinfo",
		description = "user-info-end-point-help", required = false
	)
	public String userInfoEndPoint();

}