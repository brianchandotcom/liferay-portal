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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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
 * @author Cristina González
 * @author Brian Wing Shun Chan
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

		if (!ClassUtil.isSubclass(clazz, EntityModelResource.class)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Class ", clazz.getName(), " does not implement ",
						EntityModelResource.class.getName()));
			}

			return null;
		}

		EntityModelResource entityModelResource =
			_getEntityModelResource(bundleContext, clazz);

		MultivaluedMap multivaluedMap =
			(MetadataMap)message.getContextualProperty(
				"jaxrs.template.parameters");

		return entityModelResource.getEntityModel(multivaluedMap);
	}

	public static HttpServletRequest getHttpServletRequest(Message message) {
		return (HttpServletRequest)message.getContextualProperty(
			"HTTP.REQUEST");
	}

	private static EntityModelResource _getEntityModelResource(
			BundleContext bundleContext, Class<?> clazz)
		throws InvalidSyntaxException {

		ServiceReference<?>[] serviceReferences =
			bundleContext.getServiceReferences(
				(String)null,
				"(component.name=" + clazz.getCanonicalName() + ")");

		if ((serviceReferences != null) && (serviceReferences.length == 0)) {
			return null;
		}

		return (EntityModelResource)bundleContext.getService(
			serviceReferences[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContextProviderUtil.class);

}