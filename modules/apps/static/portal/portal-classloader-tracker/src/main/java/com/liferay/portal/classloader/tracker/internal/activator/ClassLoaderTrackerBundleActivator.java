/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.classloader.tracker.internal.activator;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.StringPool;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Tina Tian
 */
public class ClassLoaderTrackerBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<ClassLoader>(
			bundleContext, Bundle.STARTING | Bundle.ACTIVE, null) {

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
	public void stop(BundleContext bundleContext) {
		_bundleTracker.close();
	}

	private String _toClassLoaderName(Bundle bundle) {
		String classLoaderName = bundle.getSymbolicName();

		classLoaderName = classLoaderName.concat(StringPool.UNDERLINE);

		return classLoaderName.concat(String.valueOf(bundle.getVersion()));
	}

	private BundleTracker<ClassLoader> _bundleTracker;

}