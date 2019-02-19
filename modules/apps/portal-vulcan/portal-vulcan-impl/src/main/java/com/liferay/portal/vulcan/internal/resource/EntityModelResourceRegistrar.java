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

package com.liferay.portal.vulcan.internal.resource;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.ContextProviderUtil;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.apache.cxf.message.Message;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Cristina González
 */
@Component(service = EntityModelResourceRegistrar.class)
public class EntityModelResourceRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, EntityModelResource.class,
			"(&(osgi.jaxrs.resource=true)(component.name=*))",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty("component.name")));
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	public EntityModel getEntityModel(Message message) throws Exception {
		Method method = ContextProviderUtil.getMethod(message);

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

		EntityModelResource entityModelResource = _serviceTrackerMap.getService(
			clazz.getCanonicalName());

		if (entityModelResource == null) {
			return null;
		}

		MultivaluedMap multivaluedMap =
			(MetadataMap)message.getContextualProperty(
				"jaxrs.template.parameters");

		return entityModelResource.getEntityModel(multivaluedMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EntityModelResourceRegistrar.class);

	private ServiceTrackerMap<String, EntityModelResource> _serviceTrackerMap;

}