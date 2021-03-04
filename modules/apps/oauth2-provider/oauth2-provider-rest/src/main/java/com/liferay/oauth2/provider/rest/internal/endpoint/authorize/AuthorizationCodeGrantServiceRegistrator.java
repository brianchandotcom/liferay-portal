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

package com.liferay.oauth2.provider.rest.internal.endpoint.authorize;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRESTEndpointConstants;
import com.liferay.oauth2.provider.rest.internal.endpoint.liferay.LiferayOAuthDataProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;

import java.net.URI;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.OAuthError;
import org.apache.cxf.rs.security.oauth2.common.OAuthPermission;
import org.apache.cxf.rs.security.oauth2.common.OAuthRedirectionState;
import org.apache.cxf.rs.security.oauth2.common.OOBAuthorizationResponse;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.grants.code.ServerAuthorizationCodeGrant;
import org.apache.cxf.rs.security.oauth2.provider.OAuthServiceException;
import org.apache.cxf.rs.security.oauth2.provider.SubjectCreator;
import org.apache.cxf.rs.security.oauth2.services.AuthorizationCodeGrantService;
import org.apache.cxf.rs.security.oauth2.tokens.refresh.RefreshToken;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;
import org.apache.cxf.rs.security.oauth2.utils.OAuthUtils;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Marta Medio
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
	immediate = true, service = {}
)
public class AuthorizationCodeGrantServiceRegistrator {

	public static class LiferayAuthorizationCodeGrantService
		extends AuthorizationCodeGrantService {

		public LiferayOAuthDataProvider getDataProvider() {
			return getDataProvider();
		}

		@Override
		public ServerAuthorizationCodeGrant getGrantRepresentation(
			OAuthRedirectionState state, Client client,
			List<String> requestedScope, List<String> approvedScope,
			UserSubject userSubject, ServerAccessToken preauthorizedToken) {

			ServerAuthorizationCodeGrant serverAuthorizationCodeGrant =
				super.getGrantRepresentation(
					state, client, requestedScope, approvedScope, userSubject,
					preauthorizedToken);

			String rememberDeviceCookieContent =
				_getRememberDeviceCookieContent();

			if (rememberDeviceCookieContent != null) {
				long userId = GetterUtil.getLong(userSubject.getId());

				OAuth2Authorization oAuth2Authorization =
					getDataProvider().
						getOAuth2AuthorizationByRememberDeviceContent(
							client, rememberDeviceCookieContent, userId);

				if ((oAuth2Authorization != null) &&
					rememberDeviceCookieContent.equals(
						oAuth2Authorization.getRememberDeviceContent())) {

					Cookie cookie = _setRememberDeviceCookie();

					Map<String, String> extraProperties =
						serverAuthorizationCodeGrant.getExtraProperties();

					extraProperties.put(
						OAuth2ProviderRESTEndpointConstants.
							COOKIE_REMEMBER_DEVICE,
						cookie.getValue());

					serverAuthorizationCodeGrant.setExtraProperties(
						extraProperties);
				}
			}

			return serverAuthorizationCodeGrant;
		}

		@Override
		protected boolean canAuthorizationBeSkipped(
			MultivaluedMap<String, String> params, Client client,
			UserSubject userSubject, List<String> requestedScope,
			List<OAuthPermission> permissions) {

			if (MapUtil.getBoolean(
					client.getProperties(),
					OAuth2ProviderRESTEndpointConstants.
						PROPERTY_KEY_CLIENT_TRUSTED_APPLICATION)) {

				return true;
			}

			if (MapUtil.getBoolean(
					client.getProperties(),
					OAuth2ProviderRESTEndpointConstants.
						PROPERTY_KEY_CLIENT_REMEMBER_DEVICE)) {

				String rememberDeviceCookieContent =
					_getRememberDeviceCookieContent();

				if (rememberDeviceCookieContent != null) {
					long userId = GetterUtil.getLong(userSubject.getId());

					OAuth2Authorization oAuth2Authorization =
						getDataProvider().
							getOAuth2AuthorizationByRememberDeviceContent(
								client, rememberDeviceCookieContent, userId);

					if ((oAuth2Authorization != null) &&
						rememberDeviceCookieContent.equals(
							oAuth2Authorization.getRememberDeviceContent())) {

						RefreshToken refreshToken =
							getDataProvider().getRefreshToken(
								oAuth2Authorization.getRefreshTokenContent());

						if ((refreshToken != null) &&
							!OAuthUtils.isExpired(
								refreshToken.getIssuedAt(),
								refreshToken.getExpiresIn())) {

							getDataProvider().doRevokeRefreshToken(
								refreshToken);

							return true;
						}
					}
				}
			}

			return super.canAuthorizationBeSkipped(
				params, client, userSubject, requestedScope, permissions);
		}

		@Override
		protected Response deliverOOBResponse(
			OOBAuthorizationResponse oobAuthorizationResponse) {

			_log.error(
				"The parameter \"redirect_uri\" was not found in the request " +
					"for client " + oobAuthorizationResponse.getClientId());

			return Response.status(
				500
			).build();
		}

		@Override
		protected Client getClient(
			String clientId, MultivaluedMap<String, String> params) {

			try {
				Client client = getValidClient(clientId, params);

				if (client != null) {
					return client;
				}
			}
			catch (OAuthServiceException oAuthServiceException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to validate remote client",
						oAuthServiceException);
				}

				if (oAuthServiceException.getError() != null) {
					reportInvalidRequestError(
						oAuthServiceException.getError(), null);
				}
			}

			reportInvalidRequestError(
				new OAuthError(OAuthConstants.INVALID_CLIENT), null);

			return null;
		}

		@Override
		protected OAuthRedirectionState recreateRedirectionStateFromParams(
			MultivaluedMap<String, String> params) {

			OAuthRedirectionState oAuthRedirectionState =
				super.recreateRedirectionStateFromParams(params);

			Client client = getDataProvider().getClient(
				oAuthRedirectionState.getClientId());

			if (MapUtil.getBoolean(
					client.getProperties(),
					OAuth2ProviderRESTEndpointConstants.
						PROPERTY_KEY_CLIENT_REMEMBER_DEVICE) &&
				params.containsKey(
					_OAUTH2_AUTHORIZE_PORTLET_REMEMBER_DEVICE_PARAMETER)) {

				Cookie cookie = _setRememberDeviceCookie();

				Map<String, String> extraProperties =
					oAuthRedirectionState.getExtraProperties();

				extraProperties.put(
					OAuth2ProviderRESTEndpointConstants.COOKIE_REMEMBER_DEVICE,
					cookie.getValue());

				oAuthRedirectionState.setExtraProperties(extraProperties);
			}

			return oAuthRedirectionState;
		}

		private String _getRememberDeviceCookieContent() {
			HttpServletRequest httpServletRequest =
				getMessageContext().getHttpServletRequest();

			return Stream.of(
				httpServletRequest.getCookies()
			).filter(
				cookie -> Objects.equals(
					cookie.getName(),
					OAuth2ProviderRESTEndpointConstants.COOKIE_REMEMBER_DEVICE)
			).map(
				Cookie::getValue
			).findFirst(
			).orElse(
				null
			);
		}

		private Cookie _setRememberDeviceCookie() {
			UUID uuid = new UUID(
				SecureRandomUtil.nextLong(), SecureRandomUtil.nextLong());

			Cookie cookie = new Cookie(
				OAuth2ProviderRESTEndpointConstants.COOKIE_REMEMBER_DEVICE,
				uuid.toString());

			URI baseURI = _uriInfo.getBaseUri();

			cookie.setPath(baseURI.getPath());

			CookieKeys.addCookie(
				getMessageContext().getHttpServletRequest(),
				getMessageContext().getHttpServletResponse(), cookie);

			return cookie;
		}

		private static final String
			_OAUTH2_AUTHORIZE_PORTLET_REMEMBER_DEVICE_PARAMETER =
				"_com_liferay_oauth2_provider_web_internal_portlet_" +
					"OAuth2AuthorizePortlet_rememberDevice";

		@Context
		private UriInfo _uriInfo;

	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		OAuth2ProviderConfiguration oAuth2ProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				OAuth2ProviderConfiguration.class, properties);

		if (!oAuth2ProviderConfiguration.allowAuthorizationCodeGrant() &&
			!oAuth2ProviderConfiguration.allowAuthorizationCodePKCEGrant()) {

			return;
		}

		AuthorizationCodeGrantService authorizationCodeGrantService =
			new LiferayAuthorizationCodeGrantService();

		authorizationCodeGrantService.setCanSupportPublicClients(
			oAuth2ProviderConfiguration.allowAuthorizationCodePKCEGrant());
		authorizationCodeGrantService.setDataProvider(
			_liferayOAuthDataProvider);
		authorizationCodeGrantService.setSubjectCreator(_subjectCreator);

		Dictionary<String, Object> authorizationCodeGrantProperties =
			new HashMapDictionary<>();

		authorizationCodeGrantProperties.put(
			"osgi.jaxrs.application.select",
			"(osgi.jaxrs.name=Liferay.OAuth2.Application)");
		authorizationCodeGrantProperties.put(
			"osgi.jaxrs.name", "Liferay.Authorization.Code.Grant.Service");
		authorizationCodeGrantProperties.put("osgi.jaxrs.resource", true);

		_serviceRegistration = bundleContext.registerService(
			Object.class, authorizationCodeGrantService,
			authorizationCodeGrantProperties);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AuthorizationCodeGrantServiceRegistrator.class);

	@Reference
	private LiferayOAuthDataProvider _liferayOAuthDataProvider;

	private ServiceRegistration<Object> _serviceRegistration;

	@Reference
	private SubjectCreator _subjectCreator;

}