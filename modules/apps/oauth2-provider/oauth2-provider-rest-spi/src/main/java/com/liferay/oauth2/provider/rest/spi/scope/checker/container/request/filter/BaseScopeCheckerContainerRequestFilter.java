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

package com.liferay.oauth2.provider.rest.spi.scope.checker.container.request.filter;

import com.liferay.oauth2.provider.scope.liferay.OAuth2ProviderScopeLiferayAccessControlContext;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

/**
 * @author Marta Medio
 */
public abstract class BaseScopeCheckerContainerRequestFilter
	implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		if (OAuth2ProviderScopeLiferayAccessControlContext.
				isOAuth2AuthVerified() &&
			!isContainerRequestContextAllowed(containerRequestContext)) {

			containerRequestContext.abortWith(
				Response.status(
					Response.Status.FORBIDDEN
				).build());
		}
	}

	protected abstract boolean isContainerRequestContextAllowed(
		ContainerRequestContext containerRequestContext);

}