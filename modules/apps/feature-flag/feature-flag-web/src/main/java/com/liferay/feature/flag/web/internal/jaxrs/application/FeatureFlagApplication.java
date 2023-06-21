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

package com.liferay.feature.flag.web.internal.jaxrs.application;

import com.liferay.feature.flag.web.internal.company.feature.flags.CompanyFeatureFlagsProvider;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Drew Brokke
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/com-liferay-feature-flag-web",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=com.liferay.feature.flag.web.internal.jaxrs.application.FeatureFlagApplication",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=false", "liferay.oauth2=false"
	},
	service = Application.class
)
public class FeatureFlagApplication extends Application {

	@Path("/set-enabled")
	@POST
	public Response confirm(
		@Context HttpServletRequest httpServletRequest,
		@Context HttpServletResponse httpServletResponse,
		@FormParam("enabled") boolean enabled, @FormParam("key") String key) {

		_companyFeatureFlagsProvider.setEnabled(
			_portal.getCompanyId(httpServletRequest), key, enabled);

		return Response.ok(
		).build();
	}

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@Reference
	private CompanyFeatureFlagsProvider _companyFeatureFlagsProvider;

	@Reference
	private Portal _portal;

}