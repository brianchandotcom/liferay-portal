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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.ContextProviderUtil;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.apache.cxf.message.Message;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Cristina González
 */
@Component(service = EntityModelResourceRegistry.class)
public class EntityModelResourceRegistry {

	@Activate
	public void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter("(osgi.jaxrs.resource=true)"),
			new EntityModelResourceServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Deactivate
	public void deactivate() {
		_serviceTracker.close();
	}

	public EntityModel getEntityModel(Message message) throws Exception {
		Method method = ContextProviderUtil.getMethod(message);

		Class<?> clazz = method.getDeclaringClass();

		EntityModelResource entityModelResource = _entityModelResourceMap.get(
			clazz.getCanonicalName());

		if (entityModelResource == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Class ", clazz.getName(),
						" does not have a registered EntityModelResource ",
						EntityModelResource.class.getName()));
			}

			return null;
		}

		MultivaluedMap multivaluedMap =
			(MetadataMap)message.getContextualProperty(
				"jaxrs.template.parameters");

		return entityModelResource.getEntityModel(multivaluedMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EntityModelResourceRegistry.class);

	private BundleContext _bundleContext;
	private final Map<String, EntityModelResource> _entityModelResourceMap =
		new HashMap<>();
	private ServiceTracker<Object, EntityModelResource> _serviceTracker;

	private class EntityModelResourceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, EntityModelResource> {

		@Override
		public EntityModelResource addingService(
			ServiceReference<Object> serviceReference) {

			Object object = _bundleContext.getService(serviceReference);

			Class<?> clazz = object.getClass();

			if (!ClassUtil.isSubclass(clazz, EntityModelResource.class)) {
				return null;
			}

			EntityModelResource entityModelResource =
				(EntityModelResource)object;

			_entityModelResourceMap.put(
				clazz.getCanonicalName(), entityModelResource);

			return entityModelResource;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference,
			EntityModelResource entityModelResource) {

			removedService(serviceReference, entityModelResource);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference,
			EntityModelResource entityModelResource) {

			Class<? extends EntityModelResource> clazz =
				entityModelResource.getClass();

			_entityModelResourceMap.remove(clazz.getCanonicalName());
		}

	}

}