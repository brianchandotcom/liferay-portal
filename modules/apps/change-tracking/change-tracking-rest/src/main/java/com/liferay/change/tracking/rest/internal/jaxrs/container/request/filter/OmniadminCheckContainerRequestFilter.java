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

package com.liferay.change.tracking.rest.internal.jaxrs.container.request.filter;

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import java.io.IOException;

import javax.annotation.Priority;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=change-tracking-application)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=OmniadminCheckContainerRequestFilter"
	},
	service = ContainerRequestFilter.class
)
@Priority(Priorities.AUTHENTICATION)
public class OmniadminCheckContainerRequestFilter
	implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
		throws IOException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker == null) || !permissionChecker.isOmniadmin()) {
			containerRequestContext.abortWith(
				Response.status(
					Response.Status.FORBIDDEN
				).build());

			return;
		}
	}

}