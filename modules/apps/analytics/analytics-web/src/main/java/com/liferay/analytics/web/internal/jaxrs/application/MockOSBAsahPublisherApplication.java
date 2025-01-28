/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.web.internal.jaxrs.application;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Marcos Martins
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/mock/osb-asah-publisher",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=com.liferay.analytics.web.internal.jaxrs.application.MockOSBAsahPublisherApplication",
		"auth.verifier.guest.allowed=true",
		"liferay.access.control.disable=true"
	},
	service = Application.class
)
public class MockOSBAsahPublisherApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response mockPostAnalyticsEvent(
		@Context HttpServletRequest httpServletRequest) {

		return Response.ok(
		).build();
	}

	@Path("/identity")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response mockPostIdentity(
		@Context HttpServletRequest httpServletRequest) {

		return Response.ok(
		).build();
	}

	@Activate
	protected void activate(
		ComponentContext componentContext, Map<String, Object> properties) {

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.ANALYTICS_CLOUD_MOCK_ENABLED))) {

			return;
		}

		componentContext.disableComponent(
			MockOSBAsahPublisherApplication.class.getName());
	}

}