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

package com.liferay.oauth2.provider.scope.internal.jaxrs.filter;

import com.liferay.oauth2.provider.rest.spi.request.scope.checker.filter.RequestScopeCheckerFilter;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.liferay.ScopedServiceTrackerMap;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author Carlos Sierra Andrés
 */
public class ScopeCheckerContainerRequestFilter
	extends AbstractContextContainerRequestFilter {

	public ScopeCheckerContainerRequestFilter(
		ScopedServiceTrackerMap<RequestScopeCheckerFilter>
			scopedServiceTrackerMap, ScopeChecker scopeChecker) {

		_scopedServiceTrackerMap = scopedServiceTrackerMap;
		_scopeChecker = scopeChecker;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) {
		String applicationName = getApplicationName();

		if (applicationName == null) {
			requestContext.abortWith(
				Response.status(
					500
				).build());
		}

		RequestScopeCheckerFilter requestScopeCheckerFilter =
			_scopedServiceTrackerMap.getService(
				getCompanyId(), applicationName);

		if (requestScopeCheckerFilter.isAllowed(
				_scopeChecker, requestContext.getRequest(), _resourceInfo)) {

			return;
		}

		requestContext.abortWith(
			Response.status(
				403
			).build());
	}

	@Context
	private ResourceInfo _resourceInfo;

	private final ScopeChecker _scopeChecker;
	private final ScopedServiceTrackerMap<RequestScopeCheckerFilter>
		_scopedServiceTrackerMap;

}