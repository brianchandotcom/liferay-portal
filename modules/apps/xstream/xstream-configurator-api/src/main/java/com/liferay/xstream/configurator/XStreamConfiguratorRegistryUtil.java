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
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.util.AggregateClassLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Máté Thurzó
 */
public class XStreamConfiguratorRegistryUtil {

	public static ClassLoader getConfiguratorsClassLoader(
		ClassLoader masterClassLoader) {

		Set<ClassLoader> classLoaders = new HashSet<>();

		for (XStreamConfigurator xStreamConfigurator : _xStreamConfigurators) {
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

	public static List<XStreamConfigurator> getXStreamConfigurators() {
		return _xStreamConfigurators.toList();
	}

	private static final AtomicLong _modifiedCount = new AtomicLong(0);
	private static final ServiceTrackerList<XStreamConfigurator>
		_xStreamConfigurators;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			XStreamConfiguratorRegistryUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_xStreamConfigurators = ServiceTrackerListFactory.open(
			bundleContext, XStreamConfigurator.class, null,
			new ServiceTrackerCustomizer
				<XStreamConfigurator, XStreamConfigurator>() {

				@Override
				public XStreamConfigurator addingService(
					ServiceReference<XStreamConfigurator> serviceReference) {

					_modifiedCount.getAndIncrement();

					return bundleContext.getService(serviceReference);
				}

				@Override
				public void modifiedService(
					ServiceReference<XStreamConfigurator> serviceReference,
					XStreamConfigurator xStreamConfigurator) {
				}

				@Override
				public void removedService(
					ServiceReference<XStreamConfigurator> serviceReference,
					XStreamConfigurator xStreamConfigurator) {

					_modifiedCount.getAndIncrement();

					bundleContext.ungetService(serviceReference);
				}

			});
	}

}