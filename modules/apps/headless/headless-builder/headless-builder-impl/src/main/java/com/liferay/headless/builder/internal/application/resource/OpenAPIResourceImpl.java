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

package com.liferay.headless.builder.internal.application.resource;

import com.liferay.portal.vulcan.resource.OpenAPIResource;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Carlos Correa
 */
public class OpenAPIResourceImpl extends BaseOpenAPIResourceImpl {

	public OpenAPIResourceImpl(OpenAPIResource openAPIResource) {
		_openAPIResource = openAPIResource;
	}

	@Override
	public Response getOpenAPI(
			HttpServletRequest httpServletRequest, String type, UriInfo uriInfo)
		throws Exception {

		return _openAPIResource.getOpenAPI(
			httpServletRequest, _resourceClasses, type, uriInfo);
	}

	private final OpenAPIResource _openAPIResource;
	private final Set<Class<?>> _resourceClasses = new HashSet<Class<?>>() {
		{
			add(OpenAPIResourceImpl.class);
		}
	};

}