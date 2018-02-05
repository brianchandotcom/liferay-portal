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

package com.liferay.apio.architect.sample.internal.filter;

import com.liferay.apio.architect.sample.internal.auth.PermissionChecker;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.osgi.service.component.annotations.Component;

/**
 * Filters all requests and stores the authorization data in {@link
 * PermissionChecker}.
 *
 * @author Alejandro Hernández
 * @see    PermissionChecker
 * @review
 */
@Component(
	immediate = true,
	property = "liferay.apio.architect.container.request.filter=true"
)
public class GeneralFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
		throws IOException {

		String authorization = containerRequestContext.getHeaderString(
			"Authorization");

		PermissionChecker.setAuthorization(authorization);
	}

}