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

import com.liferay.portal.vulcan.internal.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.internal.fields.NestedFieldsContextThreadLocal;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.impl.PathSegmentImpl;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Ivica Cardic
 */
public class NestedFieldsContainerRequestFilterTest {

	@Test
	public void testFilter() throws IOException {
		NestedFieldsContainerRequestFilter nestedFieldsContainerRequestFilter =
			new NestedFieldsContainerRequestFilter();

		ContainerRequestContext containerRequestContext = Mockito.mock(
			ContainerRequestContext.class);

		UriInfo uriInfo = Mockito.mock(UriInfo.class);

		Mockito.when(
			containerRequestContext.getUriInfo()
		).thenReturn(
			uriInfo
		);

		List<PathSegment> pathSegments = new ArrayList<>();

		pathSegments.add(new PathSegmentImpl("v1.0"));
		pathSegments.add(new PathSegmentImpl("products"));

		Mockito.when(
			uriInfo.getPathSegments()
		).thenReturn(
			pathSegments
		);

		MultivaluedMap<String, String> queryParameters =
			new MultivaluedHashMap<>();

		Mockito.when(
			uriInfo.getQueryParameters()
		).thenReturn(
			queryParameters
		);

		nestedFieldsContainerRequestFilter.filter(containerRequestContext);

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		Assert.assertNull(nestedFieldsContext);

		queryParameters.putSingle("nestedFields", "skus,productOptions");

		nestedFieldsContainerRequestFilter.filter(containerRequestContext);

		nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		List<String> fieldNames = nestedFieldsContext.getFieldNames();

		Assert.assertEquals(fieldNames.toString(), 2, fieldNames.size());
	}

}