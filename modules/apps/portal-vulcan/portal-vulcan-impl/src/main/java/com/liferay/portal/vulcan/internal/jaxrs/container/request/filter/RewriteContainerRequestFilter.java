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

package com.liferay.portal.vulcan.internal.jaxrs.container.request.filter;

import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * @author Javier Gamarra
 */
@PreMatching
@Provider
public class RewriteContainerRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) {
		UriInfo uriInfo = requestContext.getUriInfo();

		UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();

		uriBuilder.uri(uriInfo.getRequestUri());

		MultivaluedMap<String, String> parameterMap =
			uriInfo.getQueryParameters();

		for (Map.Entry<String, List<String>> entry : parameterMap.entrySet()) {
			String key = entry.getKey();

			if (key.equals("sort")) {
				List<String> values = entry.getValue();

				for (String value : values) {
					if (value.contains(",")) {
						uriBuilder.replaceQueryParam("sort", value.split(","));
					}
				}
			}
		}

		requestContext.setRequestUri(uriInfo.getBaseUri(), uriBuilder.build());
	}

}