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

package com.liferay.frontend.taglib.form.navigator.internal;

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import java.util.function.Function;

/**
 * @author Eudaldo Alonso
 */
public class WrapperRegistratorServiceTrackerCustomizer<T, W>
	implements ServiceTrackerCustomizer<T, ServiceRegistration<W>> {

	public WrapperRegistratorServiceTrackerCustomizer(
		BundleContext bundleContext, Class<W> clazz,
		Function<T, W> wrapperFunction, String... propertyNames) {

		_bundleContext = bundleContext;
		_clazz = clazz;
		_wrapperFunction = wrapperFunction;
		_propertyNames = propertyNames;
	}

	@Override
	public ServiceRegistration<W> addingService(
		ServiceReference<T> serviceReference) {

		return _bundleContext.registerService(
			_clazz,
			_wrapperFunction.apply(_bundleContext.getService(serviceReference)),
			_buildProperties(serviceReference));
	}

	@Override
	public void modifiedService(
		ServiceReference<T> serviceReference,
		ServiceRegistration<W> serviceRegistration) {

		serviceRegistration.setProperties(_buildProperties(serviceReference));
	}

	@Override
	public void removedService(
		ServiceReference<T> serviceReference,
		ServiceRegistration<W> serviceRegistration) {

		serviceRegistration.unregister();
	}

	private Dictionary<String, Object> _buildProperties(
		ServiceReference<?> serviceReference) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		for (String propertyName : _propertyNames) {
			properties.put(
				propertyName, serviceReference.getProperty(propertyName));
		}

		return properties;
	}

	private final BundleContext _bundleContext;
	private final Class<W> _clazz;
	private final String[] _propertyNames;
	private final Function<T, W> _wrapperFunction;

}