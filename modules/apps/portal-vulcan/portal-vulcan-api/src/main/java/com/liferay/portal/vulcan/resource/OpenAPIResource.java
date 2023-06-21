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

package com.liferay.portal.vulcan.resource;

import com.liferay.portal.vulcan.openapi.OpenAPIContext;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;

import io.swagger.v3.oas.models.media.Schema;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public interface OpenAPIResource {

	public Response getOpenAPI(
			HttpServletRequest httpServletRequest,
			Set<Class<?>> resourceClasses, String type, UriInfo uriInfo)
		throws Exception;

	public default Response getOpenAPI(
			OpenAPIContributor openAPIContributor,
			OpenAPISchemaFilter openAPISchemaFilter,
			Set<Class<?>> resourceClasses, String type, UriInfo uriInfo)
		throws Exception {

		return null;
	}

	public default Response getOpenAPI(
			Set<Class<?>> resourceClasses, String type)
		throws Exception {

		return null;
	}

	public default Response getOpenAPI(
			Set<Class<?>> resourceClasses, String type, UriInfo uriInfo)
		throws Exception {

		return null;
	}

	public Map<String, Schema> getSchemas(Class<?> entityClass);

	public Map<String, Schema> getSchemas(Set<Class<?>> resourceClasses)
		throws Exception;

	public Response mergeOpenAPIs(
		String description, Map<OpenAPIContext, Response> openAPIResponses,
		String path, String title, String type);

}