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

package com.liferay.xstream.configurator;

import com.liferay.exportimport.kernel.xstream.XStreamAliasRegistryUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = {})
public class XStreamConfiguratorRegistryUtil {

	public static ClassLoader getConfiguratorsClassLoader(
		ClassLoader masterClassLoader) {

		Set<ClassLoader> classLoaders = new HashSet<>();

		Set<XStreamConfigurator> xStreamConfigurators = _xStreamConfigurators;

		for (XStreamConfigurator xStreamConfigurator : xStreamConfigurators) {
			Class<?> clazz = xStreamConfigurator.getClass();

			classLoaders.add(clazz.getClassLoader());
		}

		// Temporary code to fetch class loaders from the old framework too

		Map<Class<?>, String> aliases = XStreamAliasRegistryUtil.getAliases();

		if (!aliases.isEmpty()) {
			for (Class<?> clazz : aliases.keySet()) {
				classLoaders.add(clazz.getClassLoader());
			}
		}

		return AggregateClassLoader.getAggregateClassLoader(
			masterClassLoader, classLoaders.toArray(new ClassLoader[0]));
	}

	public static long getModifiedCount() {
		return _modifiedCount.get();
	}

	public static Set<XStreamConfigurator> getXStreamConfigurators() {
		return _xStreamConfigurators;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext, XStreamConfigurator.class,
			new XStreamConfiguratorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static final AtomicLong _modifiedCount = new AtomicLong(0);
	private static final Set<XStreamConfigurator> _xStreamConfigurators =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

	private BundleContext _bundleContext;
	private ServiceTracker<XStreamConfigurator, XStreamConfigurator>
		_serviceTracker;

	private class XStreamConfiguratorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<XStreamConfigurator, XStreamConfigurator> {

		@Override
		public XStreamConfigurator addingService(
			ServiceReference<XStreamConfigurator> serviceReference) {

			XStreamConfigurator xStreamConfigurator = _bundleContext.getService(
				serviceReference);

			_xStreamConfigurators.add(xStreamConfigurator);

			_modifiedCount.getAndIncrement();

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

			_modifiedCount.getAndIncrement();
		}

	}

}