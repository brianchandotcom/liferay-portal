/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.dynamic.registration.container.request.filter;

import com.liferay.oauth2.provider.constants.OAuth2ApplicationConstants;
import com.liferay.oauth2.provider.constants.OAuth2ProviderActionKeys;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.ProtectedPrincipal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.annotation.Priority;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

import java.security.Principal;

import java.util.concurrent.TimeUnit;

import org.apache.cxf.jaxrs.utils.ExceptionUtils;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.jose.jws.JwsJwtCompactConsumer;
import org.apache.cxf.rs.security.jose.jwt.JwtClaims;
import org.apache.cxf.rs.security.jose.jwt.JwtToken;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge García Jiménez
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=DynamicRegistrationServiceContainerRequestFilter"
	},
	service = ContainerRequestFilter.class
)
@PreMatching
@Priority(Priorities.AUTHENTICATION)
@Provider
public class DynamicRegistrationServiceContainerRequestFilter
	implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		UriInfo uriInfo = containerRequestContext.getUriInfo();

		if (!StringUtil.startsWith(uriInfo.getPath(), "register")) {
			return;
		}

		Message message = JAXRSUtils.getCurrentMessage();

		HttpServletRequest httpServletRequest = (HttpServletRequest)message.get(
			AbstractHTTPDestination.HTTP_REQUEST);

		if (!FeatureFlagManagerUtil.isEnabled(
				_portal.getCompanyId(httpServletRequest), "LPD-63416")) {

			containerRequestContext.abortWith(
				Response.status(
					Response.Status.NOT_FOUND
				).build());

			return;
		}

		User user = null;

		try {
			JwtToken jwtToken = _getJwtToken(httpServletRequest);

			long currentTime = TimeUnit.SECONDS.convert(
				System.currentTimeMillis(), TimeUnit.MILLISECONDS);
			long expirationTime = GetterUtil.getLong(jwtToken.getClaim("exp"));

			if (currentTime > expirationTime) {
				throw ExceptionUtils.toNotAuthorizedException(null, null);
			}

			user = _userLocalService.getUser(
				GetterUtil.getLong(jwtToken.getClaim("sub")));

			OAuth2Application oAuth2Application;

			if (!Validator.isBlank(
					GetterUtil.getString(jwtToken.getClaim("client_id")))) {

				oAuth2Application =
					_oAuth2ApplicationLocalService.fetchOAuth2Application(
						user.getCompanyId(),
						GetterUtil.getString(jwtToken.getClaim("client_id")));
			}
			else {
				oAuth2Application =
					_oAuth2ApplicationLocalService.fetchOAuth2Application(
						GetterUtil.getLong(
							jwtToken.getClaim("application_id")));
			}

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			String clientId = _getClientId(httpServletRequest);

			if ((oAuth2Application == null) ||
				(StringUtil.equalsIgnoreCase(
					httpServletRequest.getMethod(), "POST") &&
				 !StringUtil.equalsIgnoreCase(
					 OAuth2ApplicationConstants.NAME_DYNAMIC_REGISTRATOR,
					 oAuth2Application.getName())) ||
				!_oAuth2ApplicationModelResourcePermission.contains(
					permissionChecker, oAuth2Application,
					OAuth2ProviderActionKeys.REGISTER_APPLICATION) ||
				(StringUtil.equalsIgnoreCase(
					httpServletRequest.getMethod(), "DELETE") &&
				 !_oAuth2ApplicationModelResourcePermission.contains(
					 permissionChecker, oAuth2Application,
					 ActionKeys.DELETE)) ||
				(StringUtil.equalsIgnoreCase(
					httpServletRequest.getMethod(), "PUT") &&
				 !_oAuth2ApplicationModelResourcePermission.contains(
					 permissionChecker, oAuth2Application,
					 ActionKeys.UPDATE)) ||
				(Validator.isNotNull(clientId) &&
				 !StringUtil.equalsIgnoreCase(
					 clientId, oAuth2Application.getClientId()) &&
				 !StringUtil.equalsIgnoreCase(
					 OAuth2ApplicationConstants.NAME_DYNAMIC_REGISTRATOR,
					 oAuth2Application.getName()))) {

				throw ExceptionUtils.toNotAuthorizedException(null, null);
			}
		}
		catch (WebApplicationException webApplicationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(webApplicationException);
			}

			throw webApplicationException;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw ExceptionUtils.toNotAuthorizedException(null, null);
		}

		try {
			if (!user.isGuestUser()) {
				long userId = user.getUserId();

				containerRequestContext.setSecurityContext(
					new PortalCXFSecurityContext() {

						@Override
						public Principal getUserPrincipal() {
							return new ProtectedPrincipal(
								String.valueOf(userId));
						}

						@Override
						public boolean isSecure() {
							return _portal.isSecure(httpServletRequest);
						}

					});
			}
		}
		catch (Exception exception) {
			_log.error("Unable to resolve authenticated user", exception);

			containerRequestContext.abortWith(
				Response.status(
					Response.Status.INTERNAL_SERVER_ERROR
				).build());
		}
	}

	private String _getClientId(HttpServletRequest httpServletRequest) {
		String requestURI = httpServletRequest.getRequestURI();

		String clientId = requestURI.substring(
			requestURI.lastIndexOf(StringPool.SLASH) + 1);

		if (clientId.startsWith("id-")) {
			return clientId;
		}

		return null;
	}

	private JwtToken _getJwtToken(HttpServletRequest httpServletRequest)
		throws WebApplicationException {

		String authorizationHeader = httpServletRequest.getHeader(
			"Authorization");

		if (!StringUtil.startsWith(authorizationHeader, "Bearer ")) {
			throw ExceptionUtils.toNotAuthorizedException(null, null);
		}

		String accessTokenContent = authorizationHeader.substring(
			"Bearer ".length());

		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(
					accessTokenContent);

		if (oAuth2Authorization != null) {
			JwtClaims jwtClaims = new JwtClaims();

			jwtClaims.setClaim("sub", oAuth2Authorization.getUserId());
			jwtClaims.setClaim(
				"application_id", oAuth2Authorization.getOAuth2ApplicationId());
			jwtClaims.setExpiryTime(
				oAuth2Authorization.getAccessTokenExpirationDate(
				).getTime());

			return new JwtToken(jwtClaims);
		}

		return new JwsJwtCompactConsumer(
			accessTokenContent
		).getJwtToken();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicRegistrationServiceContainerRequestFilter.class);

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.oauth2.provider.model.OAuth2Application)"
	)
	private ModelResourcePermission<OAuth2Application>
		_oAuth2ApplicationModelResourcePermission;

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	private abstract static class PortalCXFSecurityContext
		implements org.apache.cxf.security.SecurityContext, SecurityContext {

		@Override
		public String getAuthenticationScheme() {
			return "session";
		}

		@Override
		public boolean isUserInRole(String role) {
			return false;
		}

	}

}