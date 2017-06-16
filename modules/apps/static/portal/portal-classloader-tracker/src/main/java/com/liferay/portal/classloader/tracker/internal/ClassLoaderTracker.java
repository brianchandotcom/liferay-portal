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

package com.liferay.portal.classloader.tracker.internal;

import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Tina Tian
 */
public class ClassLoaderTracker implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<ClassLoader>(
			bundleContext, Bundle.ACTIVE, null) {

			@Override
			public ClassLoader addingBundle(
				Bundle bundle, BundleEvent bundleEvent) {

				BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

				ClassLoader classLoader = bundleWiring.getClassLoader();

				ClassLoaderPool.register(
					_toClassLoaderName(bundle), classLoader);

				return classLoader;
			}

			@Override
			public void removedBundle(
				Bundle bundle, BundleEvent bundleEvent,
				ClassLoader classLoader) {

				ClassLoaderPool.unregister(classLoader);
			}

		};

		_bundleTracker.open();
	}

	@Override
	public void stop(BundleContext context) {
		_bundleTracker.close();
	}

	private String _toClassLoaderName(Bundle bundle) {
		String classLoaderName = bundle.getSymbolicName();

		classLoaderName = classLoaderName.concat(StringPool.UNDERLINE);

		return classLoaderName.concat(String.valueOf(bundle.getVersion()));
	}

	private BundleTracker<ClassLoader> _bundleTracker;

}