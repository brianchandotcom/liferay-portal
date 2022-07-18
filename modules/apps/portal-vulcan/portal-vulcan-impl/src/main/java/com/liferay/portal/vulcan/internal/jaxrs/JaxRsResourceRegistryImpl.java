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

package com.liferay.portal.vulcan.internal.jaxrs;

import com.liferay.portal.vulcan.jaxrs.JaxRsResourceRegistry;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Correa
 */
@Component(immediate = true, service = JaxRsResourceRegistry.class)
public class JaxRsResourceRegistryImpl implements JaxRsResourceRegistry {

	@Override
	public Object getPropertyValue(String className, String propertyName) {
		Object object = null;

		Map<String, Object> properties = _jaxRsResourceProperties.get(
			className);

		if (properties != null) {
			object = properties.get(propertyName);
		}

		return object;
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		Filter filter = bundleContext.createFilter(
			"(" + JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true)");

		_serviceTracker = new ServiceTracker<>(
			bundleContext, filter,
			new JaxRsResourceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private final Map<String, Map<String, Object>> _jaxRsResourceProperties =
		new HashMap<>();
	private ServiceTracker<?, ?> _serviceTracker;

	private class JaxRsResourceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			Object object = _bundleContext.getService(serviceReference);

			Map<String, Object> properties = new HashMap<>();

			for (String propertyKey : serviceReference.getPropertyKeys()) {
				properties.put(
					propertyKey, serviceReference.getProperty(propertyKey));
			}

			_jaxRsResourceProperties.put(_getClassName(object), properties);

			return object;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object object) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object object) {

			_jaxRsResourceProperties.remove(_getClassName(object));
		}

		private JaxRsResourceTrackerCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		private String _getClassName(Object object) {
			Class<?> clazz = object.getClass();

			return clazz.getName();
		}

		private final BundleContext _bundleContext;

	}

}