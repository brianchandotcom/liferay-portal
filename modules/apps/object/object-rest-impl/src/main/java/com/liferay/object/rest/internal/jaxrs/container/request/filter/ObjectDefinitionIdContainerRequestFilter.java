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

package com.liferay.object.rest.internal.jaxrs.container.request.filter;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.deployer.ObjectDefinitionDeployerUtil;

import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * @author Javier Gamarra
 */
@PreMatching
@Provider
public class ObjectDefinitionIdContainerRequestFilter
	implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext) {
		UriInfo uriInfo = containerRequestContext.getUriInfo();

		UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();

		MultivaluedMap<String, String> queryParameters =
			uriInfo.getQueryParameters();

		queryParameters.add(
			"objectDefinitionId",
			String.valueOf(_objectDefinition.getObjectDefinitionId()));

		for (Map.Entry<String, List<String>> entry :
				queryParameters.entrySet()) {

			uriBuilder.queryParam(entry.getKey(), entry.getValue());
		}

		uriBuilder.queryParam(
			"taskItemDelegateName",
			ObjectDefinitionDeployerUtil.createOSGIJAXRsName(
				_objectDefinition.getCompanyId(), _objectDefinition.getName()));

		containerRequestContext.setRequestUri(uriBuilder.build());
	}

	@Context
	private ObjectDefinition _objectDefinition;

}