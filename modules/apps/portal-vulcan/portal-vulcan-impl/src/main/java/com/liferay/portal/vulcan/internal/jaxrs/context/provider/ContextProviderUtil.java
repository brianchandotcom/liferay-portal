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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.impl.ResourceContextImpl;
import org.apache.cxf.jaxrs.impl.UriInfoImpl;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;

/**
 * @author Víctor Galán
 * @author Cristina González
 * @author Brian Wing Shun Chan
 */
public class ContextProviderUtil {

	public static EntityModel getEntityModel(Message message) throws Exception {
		Object matchedResource = getMatchedResource(message);

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

	public static Object getMatchedResource(Message message) {
		Exchange exchange = message.getExchange();

		Object root = exchange.get(JAXRSUtils.ROOT_INSTANCE);

		if (root != null) {
			return root;
		}

		OperationResourceInfo operationResourceInfo = exchange.get(
			OperationResourceInfo.class);

		ResourceContext resourceContext = new ResourceContextImpl(
			message, operationResourceInfo);

		ClassResourceInfo classResourceInfo =
			operationResourceInfo.getClassResourceInfo();

		ResourceProvider resourceProvider =
			classResourceInfo.getResourceProvider();

		if (resourceProvider != null) {
			Object instance = resourceProvider.getInstance(message);

			resourceContext.initResource(instance);

			return instance;
		}

		UriInfo uriInfo = new UriInfoImpl(message);

		List<Object> matchedResources = uriInfo.getMatchedResources();

		Class<?> matchedResourceClass = (Class<?>)matchedResources.get(0);

		return resourceContext.getResource(matchedResourceClass);
	}

	public static MultivaluedHashMap<String, String> getMultivaluedHashMap(
		Map<String, String[]> parameterMap) {

		return new MultivaluedHashMap<String, String>() {
			{
				for (Entry<String, String[]> entry : parameterMap.entrySet()) {
					put(entry.getKey(), Arrays.asList(entry.getValue()));
				}
			}
		};
	}

	private static MultivaluedMap<String, String> _getPathParameters(
		Message message) {

		UriInfoImpl uriInfoImpl = new UriInfoImpl(message);

		return uriInfoImpl.getPathParameters();
	}

}