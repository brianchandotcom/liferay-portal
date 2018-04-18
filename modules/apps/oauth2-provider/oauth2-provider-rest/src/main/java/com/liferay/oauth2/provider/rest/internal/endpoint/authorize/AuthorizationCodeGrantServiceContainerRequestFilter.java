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

import com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration.AuthorizeScreenConfiguration;
import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRestEndpointConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.ProtectedPrincipal;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
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
	immediate = true,
	property = OAuth2ProviderRestEndpointConstants.LIFERAY_OAUTH2_ENDPOINT_PROVIDER + "=true",
	service = Object.class
)
@PreMatching
@Priority(Priorities.AUTHENTICATION)
@Provider
public class AuthorizationCodeGrantServiceContainerRequestFilter
	implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) {
		UriInfo uriInfo = requestContext.getUriInfo();

		if (!StringUtil.startsWith(uriInfo.getPath(), "authorize")) {
			return;
		}

		try {
			User user = _portal.getUser(_request);

			if ((user != null) && !user.isDefaultUser()) {
				SecurityContext securityContext =
					requestContext.getSecurityContext();

				requestContext.setSecurityContext(
					new PortalCXFSecurityContext() {

						@Override
						public Principal getUserPrincipal() {
							return new ProtectedPrincipal(
								GetterUtil.getString(user.getUserId()));
						}

						@Override
						public boolean isSecure() {
							return securityContext.isSecure();
						}

					});

				return;
			}
		}
		catch (Exception e) {
			_log.error("Unable to resolve authenticated user", e);

			requestContext.abortWith(
				Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());

			return;
		}

		String loginURL = null;

		try {
			loginURL = getLoginURL();
		}
		catch (ConfigurationException ce) {
			_log.error("Unable to locate configuration", ce);

			throw new WebApplicationException(
				Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}

		URI requestURI = uriInfo.getRequestUri();

		loginURL = _http.addParameter(
			loginURL, "redirect", requestURI.toASCIIString());

		requestContext.abortWith(
			Response.status(
				Response.Status.FOUND
			).location(
				URI.create(loginURL)
			).build());
	}

	protected String getLoginURL() throws ConfigurationException {
		long companyId = _portal.getCompanyId(_request);

		AuthorizeScreenConfiguration authorizeScreenRedirectConfiguration =
			_configurationProvider.getConfiguration(
				AuthorizeScreenConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, AuthorizeScreenConfiguration.class.getName()));

		String loginURL = authorizeScreenRedirectConfiguration.loginURL();

		if (Validator.isBlank(loginURL)) {
			StringBundler sb = new StringBundler(4);

			sb.append(_portal.getPortalURL(_request));
			sb.append(_portal.getPathContext());
			sb.append(_portal.getPathMain());
			sb.append("/portal/login");

			loginURL = sb.toString();
		}
		else if (!_http.hasDomain(loginURL)) {
			String portalURL = _portal.getPortalURL(_request);

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

	@Reference
	private Portal _portal;

	@Context
	private HttpServletRequest _request;

	private abstract class PortalCXFSecurityContext
		implements SecurityContext, org.apache.cxf.security.SecurityContext {

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