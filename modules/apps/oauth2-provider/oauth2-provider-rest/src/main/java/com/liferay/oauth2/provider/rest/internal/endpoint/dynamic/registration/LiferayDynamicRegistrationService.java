/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.dynamic.registration;

import com.liferay.oauth2.provider.rest.internal.endpoint.dynamic.registration.model.LiferayClientRegistration;
import com.liferay.oauth2.provider.rest.internal.endpoint.dynamic.registration.model.LiferayClientRegistrationResponse;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.remote.cors.annotation.CORS;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxrs.utils.ExceptionUtils;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.OAuthError;
import org.apache.cxf.rs.security.oauth2.services.ClientRegistration;
import org.apache.cxf.rs.security.oauth2.services.DynamicRegistrationService;
import org.apache.cxf.rs.security.oauth2.utils.OAuthUtils;

/**
 * @author Jorge García Jiménez
 */
@Path("/register")
public class LiferayDynamicRegistrationService
	extends DynamicRegistrationService {

	@GET
	@Override
	@Path("{clientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientRegistration readClientRegistrationWithPath(
		@PathParam("clientId") String clientId) {

		return super.readClientRegistrationWithPath(clientId);
	}

	@GET
	@Override
	@Produces(MediaType.APPLICATION_JSON)
	public ClientRegistration readClientRegistrationWithQuery(
		@QueryParam("client_id") String clientId) {

		return super.readClientRegistrationWithQuery(clientId);
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@CORS(allowMethods = "POST")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(LiferayClientRegistration clientRegistration) {
		return super.register(clientRegistration);
	}

	@Override
	protected void checkRegistrationAccessToken(
		Client client, String accessToken) {
	}

	@Override
	protected void fromClientRegistrationToClient(
		ClientRegistration clientRegistration, Client client) {

		client.setApplicationName(clientRegistration.getClientName());

		List<String> allowedGrantTypes = client.getAllowedGrantTypes();

		_checkValidGrantResponseTypes(
			allowedGrantTypes, clientRegistration.getResponseTypes());

		List<String> redirectUris = clientRegistration.getRedirectUris();

		if (redirectUris != null) {
			String applicationType = clientRegistration.getApplicationType();

			if (applicationType == null) {
				applicationType = "web";
			}

			for (String redirectUri : redirectUris) {
				validateRequestUri(
					redirectUri, applicationType, allowedGrantTypes);
			}

			client.setRedirectUris(redirectUris);
		}

		if (ListUtil.isEmpty(client.getRedirectUris()) &&
			(allowedGrantTypes.contains("authorization_code") ||
			 allowedGrantTypes.contains("implicit"))) {

			OAuthError oAuthError = new OAuthError(
				"invalid_request", "A Redirection URI is required");

			_reportInvalidRequestError(oAuthError);
		}

		List<String> resourceUris = clientRegistration.getResourceUris();

		if (resourceUris != null) {
			client.setRegisteredAudiences(resourceUris);
		}

		String scope = clientRegistration.getScope();

		if (!Validator.isBlank(scope)) {
			client.setRegisteredScopes(OAuthUtils.parseScope(scope));
		}

		String clientUri = clientRegistration.getClientUri();

		if (clientUri != null) {
			client.setApplicationWebUri(clientUri);
		}

		String clientLogoUri = clientRegistration.getLogoUri();

		if (Validator.isNotNull(clientLogoUri)) {
			client.setApplicationLogoUri(clientLogoUri);
		}

		Map<String, String> properties = client.getProperties();

		String jwks = clientRegistration.getStringProperty("jwks");

		if (Validator.isNotNull(jwks)) {
			properties.put("jwks", jwks);
		}

		String jwksUri = clientRegistration.getStringProperty("jwks_uri");

		if (Validator.isNotNull(jwksUri)) {
			properties.put("jwks_uri", jwksUri);
		}

		String softwareId = clientRegistration.getStringProperty("software_id");

		if (Validator.isNotNull(softwareId)) {
			properties.put("software_id", softwareId);
		}

		String tosUri = clientRegistration.getTosUri();

		if (Validator.isNotNull(tosUri)) {
			properties.put("tos_uri", tosUri);
		}
	}

	@Override
	protected LiferayClientRegistrationResponse
		fromClientToRegistrationResponse(Client client) {

		LiferayClientRegistrationResponse liferayClientRegistrationResponse =
			new LiferayClientRegistrationResponse();

		liferayClientRegistrationResponse.setClientId(client.getClientId());

		if (Validator.isNotNull(client.getApplicationName())) {
			liferayClientRegistrationResponse.setClientName(
				client.getApplicationName());
		}

		if (client.getClientSecret() != null) {
			liferayClientRegistrationResponse.setClientSecret(
				client.getClientSecret());
			liferayClientRegistrationResponse.setClientSecretExpiresAt(0L);
		}

		liferayClientRegistrationResponse.setClientIdIssuedAt(
			client.getRegisteredAt());
		liferayClientRegistrationResponse.setGrantTypes(
			client.getAllowedGrantTypes());
		liferayClientRegistrationResponse.setLogoUri(
			client.getApplicationLogoUri());
		liferayClientRegistrationResponse.setRedirectUris(
			client.getRedirectUris());
		liferayClientRegistrationResponse.setRegistrationAccessToken(
			client.getProperties(
			).get(
				"registration_access_token"
			));

		UriBuilder uriBuilder = getMessageContext(
		).getUriInfo(
		).getAbsolutePathBuilder();

		liferayClientRegistrationResponse.setRegistrationClientUri(
			uriBuilder.path(
				client.getClientId()
			).build(
				new Object[0]
			).toString());

		if (ListUtil.isNotEmpty(client.getRegisteredScopes())) {
			liferayClientRegistrationResponse.setScope(
				client.getRegisteredScopes());
		}

		Map<String, String> properties = client.getProperties();

		if (properties.get("jwks") != null) {
			liferayClientRegistrationResponse.setJwks(properties.get("jwks"));
		}

		if (properties.get("jwks_uri") != null) {
			liferayClientRegistrationResponse.setJwksUri(
				properties.get("jwks_uri"));
		}

		if (properties.get("software_id") != null) {
			liferayClientRegistrationResponse.setSoftwareId(
				properties.get("software_id"));
		}

		if (properties.get("tos_uri") != null) {
			liferayClientRegistrationResponse.setTosUri(
				properties.get("tos_uri"));
		}

		return liferayClientRegistrationResponse;
	}

	@Override
	protected String generateClientId() {
		return OAuth2SecureRandomGenerator.generateClientId();
	}

	@Override
	protected String generateClientSecret(ClientRegistration request) {
		return OAuth2SecureRandomGenerator.generateClientSecret();
	}

	private void _checkValidGrantResponseTypes(
		List<String> grantTypes, List<String> responseTypes) {

		if (grantTypes == null) {
			return;
		}

		List<String> allowedResponseTypeList = new ArrayList<>();

		for (String grantType : grantTypes) {
			String allowedResponseType = _responseTypeAllowedByGrantType.get(
				grantType);

			if (Validator.isNotNull(allowedResponseType)) {
				allowedResponseTypeList.add(allowedResponseType);
			}
		}

		if (ListUtil.isEmpty(responseTypes) &&
			!allowedResponseTypeList.isEmpty()) {

			OAuthError oAuthError = new OAuthError(
				"invalid_client_metadata",
				"A response type '" + allowedResponseTypeList.get(0) +
					"' is needed to match provided grant types");

			_reportInvalidRequestError(oAuthError);
		}

		if (ListUtil.isNotEmpty(responseTypes)) {
			for (String responseType : responseTypes) {
				if (!allowedResponseTypeList.contains(responseType)) {
					OAuthError oAuthError = new OAuthError(
						"invalid_client_metadata",
						"Invalid response type '" + responseType +
							"' by provided grant types");

					_reportInvalidRequestError(oAuthError);
				}
			}
		}
	}

	private void _reportInvalidRequestError(OAuthError oAuthError) {
		Response.ResponseBuilder responseBuilder = JAXRSUtils.toResponseBuilder(
			400);

		responseBuilder.type(MediaType.APPLICATION_JSON);

		throw ExceptionUtils.toBadRequestException(
			(Throwable)null,
			responseBuilder.entity(
				oAuthError
			).build());
	}

	private static final Map<String, String> _responseTypeAllowedByGrantType =
		HashMapBuilder.put(
			"authorization_code", "code"
		).put(
			"implicit", "token"
		).build();

}