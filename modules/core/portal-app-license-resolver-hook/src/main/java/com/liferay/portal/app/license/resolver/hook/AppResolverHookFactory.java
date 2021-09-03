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

package com.liferay.portal.app.license.resolver.hook;

import com.liferay.portal.app.license.AppLicenseVerifier;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSync;
import com.liferay.portal.kernel.util.ReleaseInfo;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.hooks.resolver.ResolverHook;
import org.osgi.framework.hooks.resolver.ResolverHookFactory;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Amos Fong
 */
public class AppResolverHookFactory implements ResolverHookFactory {

	public AppResolverHookFactory(BundleContext bundleContext)
		throws IOException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext, AppLicenseVerifier.class, null);

		_serviceTracker.open();

		String name = ReleaseInfo.getName();

		if (name.contains("Community")) {
			_allowedSymbolicNames = Collections.emptySet();

			return;
		}

		File file = bundleContext.getDataFile("allowed_bundle_symbolic_names");

		if (file.exists()) {
			_allowedSymbolicNames = Collections.unmodifiableSet(
				new HashSet<>(Files.readAllLines(file.toPath())));
		}
		else {
			Set<String> allowedSymbolicNames = Collections.newSetFromMap(
				new ConcurrentHashMap<>());

			ServiceTracker<?, ?> serviceTracker =
				new ServiceTracker
					<DependencyManagerSync, DependencyManagerSync>(
						bundleContext, DependencyManagerSync.class, null) {

					@Override
					public DependencyManagerSync addingService(
						ServiceReference<DependencyManagerSync>
							serviceReference) {

						DependencyManagerSync dependencyManagerSync =
							bundleContext.getService(serviceReference);

						dependencyManagerSync.registerSyncCallable(
							() -> {
								for (Bundle curBundle :
										bundleContext.getBundles()) {

									allowedSymbolicNames.add(
										curBundle.getSymbolicName());
								}

								Files.write(
									file.toPath(), _allowedSymbolicNames,
									StandardOpenOption.CREATE,
									StandardOpenOption.TRUNCATE_EXISTING);

								return null;
							});

						close();

						return null;
					}

				};

			serviceTracker.open();

			_allowedSymbolicNames = Collections.unmodifiableSet(
				allowedSymbolicNames);
		}
	}

	@Override
	public ResolverHook begin(Collection<BundleRevision> triggers) {
		return new AppResolverHook(
			_serviceTracker, _filteredBundleSymbolicNames, _filteredProductIds,
			_allowedSymbolicNames);
	}

	public void close() {
		_serviceTracker.close();
	}

	private final Set<String> _allowedSymbolicNames;
	private final Set<String> _filteredBundleSymbolicNames =
		Collections.newSetFromMap(new ConcurrentHashMap<>());
	private final Set<String> _filteredProductIds = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private final ServiceTracker<AppLicenseVerifier, AppLicenseVerifier>
		_serviceTracker;

}