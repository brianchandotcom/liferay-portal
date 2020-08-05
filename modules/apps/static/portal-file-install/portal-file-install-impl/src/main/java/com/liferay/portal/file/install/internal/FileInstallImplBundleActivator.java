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

package com.liferay.portal.file.install.internal;

import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Matthew Tambara
 */
public class FileInstallImplBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_bundleContext = bundleContext;

		_jarFileInstallerServiceRegistration = _bundleContext.registerService(
			FileInstaller.class, new DefaultJarInstaller(), null);

		_serviceTracker = new ServiceTracker<>(
			bundleContext, ConfigurationAdmin.class.getName(),
			new ServiceTrackerCustomizer
				<ConfigurationAdmin, ConfigInstaller>() {

				@Override
				public ConfigInstaller addingService(
					ServiceReference<ConfigurationAdmin> serviceReference) {

					ConfigurationAdmin configurationAdmin =
						bundleContext.getService(serviceReference);

					ConfigInstaller configInstaller = new ConfigInstaller(
						bundleContext, configurationAdmin,
						FileInstallImplBundleActivator.this);

					configInstaller.init();

					return configInstaller;
				}

				@Override
				public void modifiedService(
					ServiceReference<ConfigurationAdmin> serviceReference,
					ConfigInstaller configInstaller) {
				}

				@Override
				public void removedService(
					ServiceReference<ConfigurationAdmin> serviceReference,
					ConfigInstaller configInstaller) {

					configInstaller.destroy();

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();

		Map<String, String> map = new HashMap<>();

		_set(map, DirectoryWatcher.DIR);

		Set<String> dirs = new HashSet<>(
			Arrays.asList(StringUtil.split(map.get(DirectoryWatcher.DIR))));

		for (String dir : dirs) {
			map.put(DirectoryWatcher.DIR, dir);

			_directoryWatchers.add(new DirectoryWatcher(map, _bundleContext));
		}

		for (DirectoryWatcher directoryWatcher : _directoryWatchers) {
			directoryWatcher.start();
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		for (DirectoryWatcher directoryWatcher : _directoryWatchers) {
			try {
				directoryWatcher.close();
			}
			catch (Exception exception) {
			}
		}

		_serviceTracker.close();

		_directoryWatchers.clear();

		_jarFileInstallerServiceRegistration.unregister();
	}

	public void updateChecksum(File file) {
		for (DirectoryWatcher directoryWatcher : _directoryWatchers) {
			Scanner scanner = directoryWatcher.getScanner();

			scanner.updateChecksum(file);
		}
	}

	private void _set(Map<String, String> map, String key) {
		String property = _bundleContext.getProperty(key);

		if (property == null) {
			return;
		}

		map.put(key, property);
	}

	private BundleContext _bundleContext;
	private final Set<DirectoryWatcher> _directoryWatchers = new HashSet<>();
	private ServiceRegistration<FileInstaller>
		_jarFileInstallerServiceRegistration;
	private ServiceTracker<ConfigurationAdmin, ConfigInstaller> _serviceTracker;

}