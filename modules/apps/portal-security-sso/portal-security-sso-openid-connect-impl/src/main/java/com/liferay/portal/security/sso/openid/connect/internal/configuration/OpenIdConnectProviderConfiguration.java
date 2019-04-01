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

package com.liferay.portal.security.sso.openid.connect.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(
	category = "sso", factoryInstanceLabelAttribute = "providerName",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.security.sso.openid.connect.internal.configuration.OpenIdConnectProviderConfiguration",
	localization = "content/Language",
	name = "open-id-connect-provider-configuration-name"
)
public interface OpenIdConnectProviderConfiguration {

	@Meta.AD(
		deflt = "", description = "provider-name-help", name = "provider-name"
	)
	public String providerName();

	@Meta.AD(
		deflt = "", description = "open-id-connect-client-id-help",
		name = "open-id-connect-client-id"
	)
	public String openIdConnectClientId();

	@Meta.AD(
		deflt = "", description = "open-id-connect-client-secret-help",
		name = "open-id-connect-client-secret"
	)
	public String openIdConnectClientSecret();

	@Meta.AD(
		deflt = "openid email profile", description = "scopes-help",
		name = "scopes"
	)
	public String scopes();

	@Meta.AD(
		deflt = "", description = "discovery-endpoint-help",
		name = "discovery-endpoint", required = false
	)
	public String discoveryEndPoint();

	@Meta.AD(
		deflt = "360000", description = "discovery-endpoint-cache-help",
		name = "discovery-endpoint-cache-in-millis", required = false
	)
	public long discoveryEndPointCacheInMillis();

	@Meta.AD(
		deflt = "", description = "authorization-endpoint-help",
		name = "authorization-endpoint", required = false
	)
	public String authorizationEndPoint();

	@Meta.AD(
		deflt = "", description = "issuer-url-help", name = "issuer-url",
		required = false
	)
	public String issuerURL();

	@Meta.AD(
		deflt = "", description = "jwks-uri-help", name = "jwks-uri",
		required = false
	)
	public String jwksURI();

	@Meta.AD(
		deflt = "RS256", description = "id-token-signing-alg-values-help",
		name = "id-token-signing-alg-values", required = false
	)
	public String[] idTokenSigningAlgValues();

	@Meta.AD(
		deflt = "", description = "subject-types-help", name = "subject-types",
		required = false
	)
	public String[] subjectTypes();

	@Meta.AD(
		deflt = "", description = "token-endpoint-help",
		name = "token-endpoint", required = false
	)
	public String tokenEndPoint();

	@Meta.AD(
		deflt = "", description = "user-info-endpoint-help",
		name = "user-info-endpoint", required = false
	)
	public String userInfoEndPoint();

}