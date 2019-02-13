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

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.apache.cxf.message.Message;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Cristina González
 */
@Component(service = EntityModelResourceRegistrar.class)
public class EntityModelResourceRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.create(
			bundleContext, "(osgi.jaxrs.resource=true)");

		_serviceTracker.open();
	}

	@Deactivate
	public void deactivate() {
		_serviceTracker.close();
	}

	public EntityModel getEntityModel(Message message) throws Exception {
		EntityModelResource entityModelResource = _getEntityModelResource(
			message);

		MultivaluedMap multivaluedMap =
			(MetadataMap)message.getContextualProperty(
				"jaxrs.template.parameters");

		return entityModelResource.getEntityModel(multivaluedMap);
	}

	private EntityModelResource _getEntityModelResource(Class<?> clazz) {
		ServiceReference<Object>[] serviceReferences =
			_serviceTracker.getServiceReferences();

		for (ServiceReference<Object> serviceReference : serviceReferences) {
			if (Objects.equals(
					serviceReference.getProperty("component.name"),
					clazz.getCanonicalName())) {

				return (EntityModelResource)
					_bundleContext.getService(serviceReference);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Class ", clazz.getName(),
					" not registered as OSGi component ",
					EntityModelResource.class.getName()));
		}

		return null;
	}

	private EntityModelResource _getEntityModelResource(Message message) {
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

		return _getEntityModelResource(clazz);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EntityModelResourceRegistrar.class);

	private BundleContext _bundleContext;
	private ServiceTracker<Object, Object> _serviceTracker;

}