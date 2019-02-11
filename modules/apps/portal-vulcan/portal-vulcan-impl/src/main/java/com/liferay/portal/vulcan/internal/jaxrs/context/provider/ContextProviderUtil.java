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

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.apache.cxf.message.Message;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Víctor Galán
 */
public class ContextProviderUtil {

	public static EntityModel getEntityModel(
			BundleContext bundleContext, Message message)
		throws Exception {

		Method method = (Method)message.get("org.apache.cxf.resource.method");

		if (method == null) {
			return null;
		}

		Class<?> clazz = method.getDeclaringClass();

		if (clazz == null) {
			return null;
		}

		Method getEntityModelMethod = clazz.getDeclaredMethod(
			"getEntityModel", MultivaluedMap.class);

		if (getEntityModelMethod == null) {
			return null;
		}

		Object resourceService = getResourceService(bundleContext, clazz);

		if (resourceService == null) {
			return null;
		}

		MultivaluedMap multivaluedMap =
			(MetadataMap)message.getContextualProperty(
				"jaxrs.template.parameters");

		return (EntityModel)getEntityModelMethod.invoke(
			resourceService, multivaluedMap);
	}

	public static HttpServletRequest getHttpServletRequest(Message message) {
		return (HttpServletRequest)message.getContextualProperty(
			"HTTP.REQUEST");
	}

	public static <T> T getResourceService(
			BundleContext bundleContext, Class<T> clazz)
		throws InvalidSyntaxException {

		ServiceReference<?>[] serviceReferences =
			bundleContext.getServiceReferences(
				(String)null,
				"(component.name=" + clazz.getCanonicalName() + ")");

		if ((serviceReferences != null) && (serviceReferences.length == 0)) {
			return null;
		}

		return (T)bundleContext.getService(serviceReferences[0]);
	}

}