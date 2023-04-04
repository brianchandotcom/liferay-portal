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

package com.liferay.frontend.taglib.internal.util;

import com.liferay.frontend.js.module.launcher.JSModuleLauncher;
import com.liferay.frontend.js.module.launcher.JSModuleResolver;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = {})
public class ServicesProvider {

	public static AbsolutePortalURLBuilderFactory
		getAbsolutePortalURLBuilderFactory() {

		return _absolutePortalURLBuilderFactorySnapshot.get();
	}

	public static Map<String, Bundle> getBundleMap() {
		return _bundleConcurrentMap;
	}

	public static JSModuleLauncher getJSModuleLauncher() {
		return _jsModuleLauncherSnapshot.get();
	}

	public static JSModuleResolver getJSModuleResolver() {
		return _jsModuleResolverSnapshot.get();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleConcurrentMap = new ConcurrentHashMap<>();

		_bundleTracker = new BundleTracker(
			bundleContext, Bundle.ACTIVE,
			new BundleTrackerCustomizer<String>() {

				@Override
				public String addingBundle(
					Bundle bundle, BundleEvent bundleEvent) {

					_bundleConcurrentMap.put(bundle.getSymbolicName(), bundle);

					return bundle.getSymbolicName();
				}

				@Override
				public void modifiedBundle(
					Bundle bundle, BundleEvent bundleEvent,
					String symbolicName) {
				}

				@Override
				public void removedBundle(
					Bundle bundle, BundleEvent bundleEvent,
					String symbolicName) {

					_bundleConcurrentMap.remove(symbolicName);
				}

			});

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();

		_bundleTracker = null;

		_bundleConcurrentMap = null;
	}

	private static final Snapshot<AbsolutePortalURLBuilderFactory>
		_absolutePortalURLBuilderFactorySnapshot = new Snapshot<>(
			ServicesProvider.class, AbsolutePortalURLBuilderFactory.class);
	private static ConcurrentMap<String, Bundle> _bundleConcurrentMap;
	private static BundleTracker<String> _bundleTracker;
	private static final Snapshot<JSModuleLauncher> _jsModuleLauncherSnapshot =
		new Snapshot<>(ServicesProvider.class, JSModuleLauncher.class);
	private static final Snapshot<JSModuleResolver> _jsModuleResolverSnapshot =
		new Snapshot<>(ServicesProvider.class, JSModuleResolver.class);

}