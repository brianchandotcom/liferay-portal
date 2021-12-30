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

package com.liferay.oauth2.provider.rest.internal.endpoint.authorize.container.request.filter;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration.AuthorizeScreenConfiguration;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.ProtectedPrincipal;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URI;

import java.security.Principal;

import javax.annotation.Priority;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=AuthorizationCodeGrantServiceContainerRequestFilter"
	},
	service = ContainerRequestFilter.class
)
@PreMatching
@Priority(Priorities.AUTHENTICATION)
@Provider
public class AuthorizationCodeGrantServiceContainerRequestFilter
	implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		UriInfo uriInfo = containerRequestContext.getUriInfo();

		if (!StringUtil.startsWith(uriInfo.getPath(), "authorize")) {
			return;
		}

		try {
			User user = _portal.getUser(_httpServletRequest);

			if (user == null) {
				user = _userLocalService.getDefaultUser(
					_portal.getCompanyId(_httpServletRequest));
			}

			boolean guestAuthorized = false;

			if (user.isDefaultUser()) {
				String clientId = ParamUtil.getString(
					_httpServletRequest, "client_id");

				if (!Validator.isBlank(clientId)) {
					guestAuthorized = _containsOAuth2ApplicationViewPermission(
						clientId, user);
				}
			}

			if (!user.isDefaultUser() || guestAuthorized) {
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
							return _portal.isSecure(_httpServletRequest);
						}

					});

				return;
			}
		}
		catch (Exception exception) {
			_log.error("Unable to resolve authenticated user", exception);

			containerRequestContext.abortWith(
				Response.status(
					Response.Status.INTERNAL_SERVER_ERROR
				).build());

			return;
		}

		String loginURL = null;

		try {
			loginURL = _getLoginURL();
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to locate configuration", configurationException);

			throw new WebApplicationException(
				Response.status(
					Response.Status.INTERNAL_SERVER_ERROR
				).build());
		}

		URI requestURI = uriInfo.getRequestUri();

		String requestURIString = requestURI.toASCIIString();

		String portalURL = _portal.getPortalURL(_httpServletRequest);

		if (requestURIString.startsWith(portalURL)) {
			requestURIString = requestURIString.substring(portalURL.length());
		}

		// Workaround for LPS-94559

		requestURIString = requestURIString.replaceFirst(
			"\\?.*",
			StringUtil.replace(
				"?" + requestURI.getRawQuery(), CharPool.COLON, "%3a"));

		loginURL = _http.addParameter(loginURL, "redirect", requestURIString);

		containerRequestContext.abortWith(
			Response.status(
				Response.Status.FOUND
			).location(
				URI.create(loginURL)
			).build());
	}

	private boolean _containsOAuth2ApplicationViewPermission(
			String clientId, User user)
		throws Exception {

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				user.getCompanyId(), clientId);

		if (oAuth2Application == null) {
			return false;
		}

		if (_oAuth2ApplicationModelResourcePermission.contains(
				_permissionCheckerFactory.create(user), oAuth2Application,
				ActionKeys.VIEW)) {

			return true;
		}

		return false;
	}

	private String _getLoginURL() throws ConfigurationException {
		AuthorizeScreenConfiguration authorizeScreenConfiguration =
			_configurationProvider.getConfiguration(
				AuthorizeScreenConfiguration.class,
				new CompanyServiceSettingsLocator(
					_portal.getCompanyId(_httpServletRequest),
					AuthorizeScreenConfiguration.class.getName()));

		String loginURL = authorizeScreenConfiguration.loginURL();

		if (Validator.isBlank(loginURL)) {
			loginURL = StringBundler.concat(
				_portal.getPortalURL(_httpServletRequest),
				_portal.getPathContext(), _portal.getPathMain(),
				"/portal/login");
		}
		else if (!_http.hasDomain(loginURL)) {
			String portalURL = _portal.getPortalURL(_httpServletRequest);

			loginURL = portalURL + loginURL;
		}

		return loginURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AuthorizationCodeGrantServiceContainerRequestFilter.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.oauth2.provider.model.OAuth2Application)"
	)
	private ModelResourcePermission<OAuth2Application>
		_oAuth2ApplicationModelResourcePermission;

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