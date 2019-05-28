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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.impl.ResourceContextImpl;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;

/**
 * @author Víctor Galán
 * @author Cristina González
 * @author Brian Wing Shun Chan
 */
public class ContextProviderUtil {

	public static EntityModel getEntityModel(Message message) throws Exception {
		Object matchedResource = _getMatchedResource(message);

		if (matchedResource instanceof EntityModelResource) {
			EntityModelResource entityModelResource =
				(EntityModelResource)matchedResource;

			return entityModelResource.getEntityModel(
				_getPathParameters(message));
		}

		return null;
	}

	public static HttpServletRequest getHttpServletRequest(Message message) {
		return (HttpServletRequest)message.getContextualProperty(
			"HTTP.REQUEST");
	}

	private static Object _getMatchedResource(Message message) {
		Exchange exchange = message.getExchange();

		ResourceContext resourceContext = new ResourceContextImpl(
			message, exchange.get(OperationResourceInfo.class));

		UriInfo uriInfo = new UriInfoImpl(message);

		List<Object> matchedResources = uriInfo.getMatchedResources();

		Class<?> matchedResourceClass = (Class<?>)matchedResources.get(0);

		return resourceContext.getResource(matchedResourceClass);
	}

	private static MultivaluedMap<String, String> _getPathParameters(
		Message message) {

		UriInfoImpl uriInfo = new UriInfoImpl(message);

		return uriInfo.getPathParameters();
	}

}