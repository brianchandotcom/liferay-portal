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

package com.liferay.exportimport.xstream.configurator;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.util.AggregateClassLoader;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Mate Thurzo
 */
@ProviderType
public class XStreamConfiguratorRegistryUtil {

	public static Set<XStreamConfigurator> getXStreamConfigurators() {
		return _instance._getXStreamConfigurators();
	}

	private XStreamConfiguratorRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			XStreamConfiguratorRegistryUtil.class);

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, XStreamConfigurator.class,
			new XStreamConfiguratorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private Set<XStreamConfigurator> _getXStreamConfigurators() {
		return _xStreamConfigurators;
	}

	private static final XStreamConfiguratorRegistryUtil
		_instance = new XStreamConfiguratorRegistryUtil();

	private final BundleContext _bundleContext;
	private final ServiceTracker<XStreamConfigurator, XStreamConfigurator>
		_serviceTracker;
	private final Set<XStreamConfigurator>
		_xStreamConfigurators = new ConcurrentHashSet<>();

	private class XStreamConfiguratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<XStreamConfigurator, XStreamConfigurator> {

		@Override
		public XStreamConfigurator addingService(
			ServiceReference<XStreamConfigurator> serviceReference) {

			XStreamConfigurator xStreamConfigurator = _bundleContext.getService(
				serviceReference);

			_xStreamConfigurators.add(xStreamConfigurator);

			return xStreamConfigurator;
		}

		@Override
		public void modifiedService(
			ServiceReference<XStreamConfigurator> serviceReference,
			XStreamConfigurator xStreamConfigurator) {

			removedService(serviceReference, xStreamConfigurator);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<XStreamConfigurator> serviceReference,
			XStreamConfigurator xStreamConfigurator) {

			_bundleContext.ungetService(serviceReference);

			_xStreamConfigurators.remove(xStreamConfigurator);
		}

	}

}