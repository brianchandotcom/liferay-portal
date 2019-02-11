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

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.odata.entity.EntityModel;
import org.apache.cxf.message.Message;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Víctor Galán
 */
public class ContextProviderUtil {

	public static HttpServletRequest getHttpServletRequest(Message message) {
		return (HttpServletRequest)message.getContextualProperty(
			"HTTP.REQUEST");
	}

	public static EntityModel getEntityModel(BundleContext bundleContext, Message message)
		throws Exception {

		HttpServletRequest httpServletRequest = getHttpServletRequest(message);

		httpServletRequest.getParameterMap();

		Method method = (Method)message.get("org.apache.cxf.resource.method");

		if (method == null) {
			return null;
		}

		Class<?> clazz = method.getDeclaringClass();

		Class<?> superclass = clazz.getSuperclass();


		Class<?>[] interfaces = superclass.getInterfaces();

		Class<?> anInterface = interfaces[0];


		Method getEntityModelMethod = clazz.getMethod("getEntityModel");

		if (getEntityModelMethod == null) {
			return null;
		}

		return (EntityModel)getEntityModelMethod.invoke(
			getResourceClass(bundleContext, anInterface), null);
	}

	public static String getODataEntityModelName(Message message)
		throws Exception {

		Method method = (Method)message.get("org.apache.cxf.resource.method");

		if (method == null) {
			return null;
		}

		Class<?> clazz = method.getDeclaringClass();

		Field field = clazz.getField("ODATA_ENTITY_MODEL_NAME");

		if (field == null) {
			return null;
		}

		return (String)field.get(null);
	}

	public static <T> T getResourceClass(
			BundleContext bundleContext, Class<T> clazz)
		throws Exception {

		ServiceReference<?> serviceReference =
			bundleContext.getServiceReference(clazz.getCanonicalName());

		return (T)bundleContext.getService(serviceReference);
	}

	public static <T> T getODataEntityModelService(
			BundleContext bundleContext, Class<T> clazz,
			String oDataEntityModelName)
		throws Exception {

		Collection<ServiceReference<T>> serviceReferences =
			bundleContext.getServiceReferences(
				clazz, "(entity.model.name=" + oDataEntityModelName + ")");

		if (serviceReferences.isEmpty()) {
			return null;
		}

		Iterator<ServiceReference<T>> iterator = serviceReferences.iterator();

		ServiceReference<T> serviceReference = iterator.next();

		return bundleContext.getService(serviceReference);
	}

}