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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.file.install.internal.configuration.ConfigurationFileInstaller;
import com.liferay.portal.file.install.internal.configuration.FileSyncConfigurationListener;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationListener;
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
				<ConfigurationAdmin, List<ServiceRegistration<?>>>() {

				@Override
				public List<ServiceRegistration<?>> addingService(
					ServiceReference<ConfigurationAdmin> serviceReference) {

					ConfigurationAdmin configurationAdmin =
						bundleContext.getService(serviceReference);

					String encoding = bundleContext.getProperty(
						DirectoryWatcher.CONFIG_ENCODING);

					if (encoding == null) {
						encoding = StringPool.UTF8;
					}

					return Arrays.asList(
						_bundleContext.registerService(
							FileInstaller.class.getName(),
							new ConfigurationFileInstaller(
								configurationAdmin, encoding),
							null),
						_bundleContext.registerService(
							ConfigurationListener.class.getName(),
							new FileSyncConfigurationListener(
								configurationAdmin,
								FileInstallImplBundleActivator.this, encoding),
							null));
				}

				@Override
				public void modifiedService(
					ServiceReference<ConfigurationAdmin> serviceReference,
					List<ServiceRegistration<?>> serviceRegistrations) {
				}

				@Override
				public void removedService(
					ServiceReference<ConfigurationAdmin> serviceReference,
					List<ServiceRegistration<?>> serviceRegistrations) {

					for (ServiceRegistration<?> serviceRegistration :
							serviceRegistrations) {

						serviceRegistration.unregister();
					}

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();

		Set<String> dirs = new HashSet<>(
			Arrays.asList(
				StringUtil.split(
					bundleContext.getProperty(DirectoryWatcher.DIR))));

		for (String dir : dirs) {
			_directoryWatchers.add(new DirectoryWatcher(dir, _bundleContext));
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

	private BundleContext _bundleContext;
	private final Set<DirectoryWatcher> _directoryWatchers = new HashSet<>();
	private ServiceRegistration<FileInstaller>
		_jarFileInstallerServiceRegistration;
	private ServiceTracker<ConfigurationAdmin, List<ServiceRegistration<?>>>
		_serviceTracker;

}