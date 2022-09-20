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

package com.liferay.portal.file.install.internal.activator;

import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.file.install.internal.DefaultJarInstaller;
import com.liferay.portal.file.install.internal.DirectoryWatcher;
import com.liferay.portal.file.install.internal.Scanner;
import com.liferay.portal.file.install.internal.configuration.ConfigurationFileInstaller;
import com.liferay.portal.file.install.internal.configuration.FileSyncConfigurationListener;
import com.liferay.portal.kernel.util.ModuleFrameworkPropsValues;

import java.io.File;

import java.util.Arrays;
import java.util.List;

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

					return Arrays.asList(
						_bundleContext.registerService(
							FileInstaller.class.getName(),
							new ConfigurationFileInstaller(
								configurationAdmin,
								ModuleFrameworkPropsValues.
									MODULE_FRAMEWORK_FILE_INSTALL_CONFIG_ENCODING),
							null),
						_bundleContext.registerService(
							ConfigurationListener.class.getName(),
							new FileSyncConfigurationListener(
								configurationAdmin,
								FileInstallImplBundleActivator.this,
								ModuleFrameworkPropsValues.
									MODULE_FRAMEWORK_FILE_INSTALL_CONFIG_ENCODING),
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

		_directoryWatcher = new DirectoryWatcher(_bundleContext);

		_directoryWatcher.start();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_directoryWatcher.close();

		_serviceTracker.close();

		_jarFileInstallerServiceRegistration.unregister();
	}

	public void updateChecksum(File file) {
		Scanner scanner = _directoryWatcher.getScanner();

		scanner.updateChecksum(file);
	}

	private BundleContext _bundleContext;
	private DirectoryWatcher _directoryWatcher;
	private ServiceRegistration<FileInstaller>
		_jarFileInstallerServiceRegistration;
	private ServiceTracker<ConfigurationAdmin, List<ServiceRegistration<?>>>
		_serviceTracker;

}