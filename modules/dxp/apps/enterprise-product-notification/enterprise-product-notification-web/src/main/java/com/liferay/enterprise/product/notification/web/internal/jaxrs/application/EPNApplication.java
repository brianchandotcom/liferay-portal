/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.enterprise.product.notification.web.internal.jaxrs.application;

import com.liferay.enterprise.product.notification.web.internal.EPNManager;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/com-liferay-enterprise-product-notification-web",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=com.liferay.enterprise.product.notification.web.Application",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=false", "liferay.oauth2=false"
	},
	service = Application.class
)
public class EPNApplication extends Application {

	@Path("/confirm")
	@POST
	public Response confirm(
		@Context HttpServletRequest httpServletRequest,
		@Context HttpServletResponse httpServletResponse) {

		long userId = _portal.getUserId(httpServletRequest);

		if (userId <= 0) {
			return Response.serverError(
			).build();
		}

		_epnManager.confirm(userId);

		return Response.ok(
		).build();
	}

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@Reference
	private EPNManager _epnManager;

	@Reference
	private Portal _portal;

}