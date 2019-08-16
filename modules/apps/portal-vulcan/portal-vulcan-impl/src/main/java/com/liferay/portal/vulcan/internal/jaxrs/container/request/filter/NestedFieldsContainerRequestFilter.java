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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsContextThreadLocal;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.utils.JAXRSUtils;

/**
 * @author Ivica Cardic
 */
@Provider
public class NestedFieldsContainerRequestFilter
	implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
		throws IOException {

		UriInfo uriInfo = containerRequestContext.getUriInfo();

		MultivaluedMap<String, String> queryParameters =
			uriInfo.getQueryParameters();

		String nestedFields = queryParameters.getFirst("nestedFields");

		if (Validator.isNotNull(nestedFields)) {
			List<String> fieldNames = Arrays.asList(
				nestedFields.split("\\s*,\\s*"));

			NestedFieldsContextThreadLocal.setNestedFieldsContext(
				new NestedFieldsContext(
					fieldNames, JAXRSUtils.getCurrentMessage(),
					uriInfo.getPathParameters(),
					_getResourceVersion(uriInfo.getPathSegments()),
					queryParameters));
		}
	}

	private String _getResourceVersion(List<PathSegment> pathSegments) {
		PathSegment pathSegment = pathSegments.get(0);

		return pathSegment.getPath();
	}

}