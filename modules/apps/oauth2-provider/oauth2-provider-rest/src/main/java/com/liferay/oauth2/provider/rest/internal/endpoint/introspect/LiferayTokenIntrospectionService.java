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

package com.liferay.oauth2.provider.rest.internal.endpoint.introspect;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRESTEndpointConstants;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.oauth2.provider.rest.spi.bearer.token.provider.BearerTokenProvider;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.remote.cors.annotation.CORS;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.Encoded;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.OAuthPermission;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.services.AbstractTokenService;
import org.apache.cxf.rs.security.oauth2.tokens.refresh.RefreshToken;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;
import org.apache.cxf.rs.security.oauth2.utils.OAuthUtils;

/**
 * @author Tomas Polesovsky
 */
@Path("introspect")
public class LiferayTokenIntrospectionService extends AbstractTokenService {

	public LiferayTokenIntrospectionService(
		LiferayOAuthDataProvider liferayOAuthDataProvider,
		boolean canSupportPublicClients) {

		_liferayOAuthDataProvider = liferayOAuthDataProvider;

		setCanSupportPublicClients(canSupportPublicClients);
		setDataProvider(liferayOAuthDataProvider);
	}

	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@CORS(allowMethods = "POST")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTokenIntrospection(
		@Encoded MultivaluedMap<String, String> params) {

		Client client = authenticateClientIfNeeded(params);

		String tokenId = params.getFirst(OAuthConstants.TOKEN_ID);
		String tokenTypeHint = params.getFirst(OAuthConstants.TOKEN_TYPE_HINT);

		if (tokenTypeHint == null) {
			ServerAccessToken serverAccessToken =
				_liferayOAuthDataProvider.getAccessToken(tokenId);

			if (serverAccessToken != null) {
				return handleAccessToken(client, serverAccessToken);
			}

			RefreshToken refreshToken =
				_liferayOAuthDataProvider.getRefreshToken(tokenId);

			if (refreshToken != null) {
				return handleRefreshToken(client, refreshToken);
			}
		}
		else if (OAuthConstants.ACCESS_TOKEN.equals(tokenTypeHint)) {
			ServerAccessToken serverAccessToken =
				_liferayOAuthDataProvider.getAccessToken(tokenId);

			if (serverAccessToken != null) {
				return handleAccessToken(client, serverAccessToken);
			}
		}
		else if (OAuthConstants.REFRESH_TOKEN.equals(tokenTypeHint)) {
			RefreshToken refreshToken =
				_liferayOAuthDataProvider.getRefreshToken(tokenId);

			if (refreshToken != null) {
				return handleRefreshToken(client, refreshToken);
			}
		}
		else {
			return createErrorResponseFromErrorCode(
				OAuthConstants.UNSUPPORTED_TOKEN_TYPE);
		}

		return Response.ok(
			new TokenIntrospection(false)
		).build();
	}

	@Override
	protected Client authenticateClientIfNeeded(
		MultivaluedMap<String, String> params) {

		String clientId = params.getFirst("client_id");

		if ((clientId != null) && clientId.isEmpty()) {
			reportInvalidClient();
		}

		String clientSecret = params.getFirst("client_secret");

		if ((clientSecret != null) && clientSecret.isEmpty()) {
			params.remove("client_secret");
		}

		return super.authenticateClientIfNeeded(params);
	}

	protected boolean clientsMatch(Client client1, Client client2) {
		if (!Objects.equals(client1.getClientId(), client2.getClientId())) {
			return false;
		}

		String companyId1 = MapUtil.getString(
			client1.getProperties(),
			OAuth2ProviderRESTEndpointConstants.PROPERTY_KEY_COMPANY_ID);
		String companyId2 = MapUtil.getString(
			client2.getProperties(),
			OAuth2ProviderRESTEndpointConstants.PROPERTY_KEY_COMPANY_ID);

		if (Objects.equals(companyId1, companyId2)) {
			return true;
		}

		return false;
	}

	protected TokenIntrospection createTokenIntrospection(
		ServerAccessToken serverAccessToken) {

		TokenIntrospection tokenIntrospection = new TokenIntrospection(true);

		List<String> audiences = serverAccessToken.getAudiences();

		if (ListUtil.isNotEmpty(audiences)) {
			tokenIntrospection.setAud(audiences);
		}

		Client client = serverAccessToken.getClient();

		tokenIntrospection.setClientId(client.getClientId());

		tokenIntrospection.setExp(
			serverAccessToken.getIssuedAt() + serverAccessToken.getExpiresIn());

		Map<String, String> extraProperties =
			serverAccessToken.getExtraProperties();

		if (extraProperties != null) {
			Map<String, String> extensions = tokenIntrospection.getExtensions();

			extensions.putAll(extraProperties);
		}

		String issuer = serverAccessToken.getIssuer();

		if (issuer != null) {
			tokenIntrospection.setIss(issuer);
		}

		tokenIntrospection.setIat(serverAccessToken.getIssuedAt());

		List<OAuthPermission> oAuthPermissions = serverAccessToken.getScopes();

		if (ListUtil.isNotEmpty(oAuthPermissions)) {
			tokenIntrospection.setScope(
				OAuthUtils.convertPermissionsToScope(oAuthPermissions));
		}

		UserSubject userSubject = serverAccessToken.getSubject();

		if (userSubject != null) {
			tokenIntrospection.setUsername(userSubject.getLogin());
			tokenIntrospection.setSub(userSubject.getId());
		}

		tokenIntrospection.setTokenType(serverAccessToken.getTokenType());

		return tokenIntrospection;
	}

	protected Response handleAccessToken(
		Client client, ServerAccessToken serverAccessToken) {

		if (!verifyClient(client, serverAccessToken)) {
			return createErrorResponseFromErrorCode(
				OAuthConstants.UNAUTHORIZED_CLIENT);
		}

		if (!verifyServerAccessToken(serverAccessToken)) {
			return Response.ok(
				new TokenIntrospection(false)
			).build();
		}

		BearerTokenProvider.AccessToken bearerAccessToken =
			_liferayOAuthDataProvider.fromCXFAccessToken(serverAccessToken);

		OAuth2Application oAuth2Application =
			bearerAccessToken.getOAuth2Application();

		BearerTokenProvider bearerTokenProvider =
			_liferayOAuthDataProvider.getBearerTokenProvider(
				oAuth2Application.getCompanyId(),
				oAuth2Application.getClientId());

		if (!bearerTokenProvider.isValid(bearerAccessToken)) {
			return Response.ok(
				new TokenIntrospection(false)
			).build();
		}

		return Response.ok(
			createTokenIntrospection(serverAccessToken)
		).build();
	}

	protected Response handleRefreshToken(
		Client client, RefreshToken refreshToken) {

		if (!verifyClient(client, refreshToken)) {
			return createErrorResponseFromErrorCode(
				OAuthConstants.UNAUTHORIZED_CLIENT);
		}

		if (!verifyServerAccessToken(refreshToken)) {
			return Response.ok(
				new TokenIntrospection(false)
			).build();
		}

		BearerTokenProvider.RefreshToken bearerRefreshToken =
			_liferayOAuthDataProvider.fromCXFRefreshToken(refreshToken);

		OAuth2Application oAuth2Application =
			bearerRefreshToken.getOAuth2Application();

		BearerTokenProvider bearerTokenProvider =
			_liferayOAuthDataProvider.getBearerTokenProvider(
				oAuth2Application.getCompanyId(),
				oAuth2Application.getClientId());

		if (!bearerTokenProvider.isValid(bearerRefreshToken)) {
			return Response.ok(
				new TokenIntrospection(false)
			).build();
		}

		return Response.status(
			Response.Status.OK
		).entity(
			createTokenIntrospection(refreshToken)
		).build();
	}

	protected boolean verifyClient(
		Client client, ServerAccessToken serverAccessToken) {

		if (!clientsMatch(client, serverAccessToken.getClient())) {
			return false;
		}

		Map<String, String> properties = client.getProperties();

		if (!properties.containsKey(
				OAuth2ProviderRESTEndpointConstants.
					PROPERTY_KEY_CLIENT_FEATURE_PREFIX +
						OAuth2ProviderRESTEndpointConstants.
							PROPERTY_KEY_CLIENT_FEATURE_TOKEN_INTROSPECTION)) {

			return false;
		}

		return true;
	}

	protected boolean verifyServerAccessToken(
		ServerAccessToken serverAccessToken) {

		if (OAuthUtils.isExpired(
				serverAccessToken.getIssuedAt(),
				serverAccessToken.getExpiresIn())) {

			return false;
		}

		return true;
	}

	private final LiferayOAuthDataProvider _liferayOAuthDataProvider;

}