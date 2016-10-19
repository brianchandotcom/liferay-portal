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

package com.liferay.exportimport.kernel.xstream;

import aQute.bnd.annotation.ProviderType;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public class XStreamConverterRegistryUtil {

	public static Set<XStreamConverter> getXStreamConverters() {
		return _instance._getXStreamConverters();
	}

	public static void register(XStreamConverter xStreamConverter) {
		_instance._register(xStreamConverter);
	}

	public static void unregister(XStreamConverter xStreamConverter) {
		_instance._unregister(xStreamConverter);
	}

	private XStreamConverterRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			XStreamConverter.class,
			new XStreamConverterServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private Set<XStreamConverter> _getXStreamConverters() {
		return _xStreamConverters;
	}

	private void _register(XStreamConverter xStreamConverter) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<XStreamConverter> serviceRegistration =
			registry.registerService(XStreamConverter.class, xStreamConverter);

		_serviceRegistrations.put(xStreamConverter, serviceRegistration);
	}

	private void _unregister(XStreamConverter xStreamConverter) {
		ServiceRegistration<XStreamConverter> serviceRegistration =
			_serviceRegistrations.remove(xStreamConverter);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final XStreamConverterRegistryUtil _instance =
		new XStreamConverterRegistryUtil();

	private final ServiceRegistrationMap<XStreamConverter>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final ServiceTracker<XStreamConverter, XStreamConverter>
		_serviceTracker;
	private final Set<XStreamConverter> _xStreamConverters = new HashSet<>();

	private class XStreamConverterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<XStreamConverter, XStreamConverter> {

		@Override
		public XStreamConverter addingService(
			ServiceReference<XStreamConverter> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			XStreamConverter xStreamConverter = registry.getService(
				serviceReference);

			_xStreamConverters.add(xStreamConverter);

			return xStreamConverter;
		}

		@Override
		public void modifiedService(
			ServiceReference<XStreamConverter> serviceReference,
			XStreamConverter xStreamConverter) {
		}

		@Override
		public void removedService(
			ServiceReference<XStreamConverter> serviceReference,
			XStreamConverter xStreamConverter) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_xStreamConverters.remove(xStreamConverter);
		}

	}

}