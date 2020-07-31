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
import com.liferay.portal.file.install.internal.properties.InterpolationUtil;
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

/**
 * @author Matthew Tambara
 */
public class FileInstallImplBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_bundleContext = bundleContext;

		_jarFileInstallerServiceRegistration = _bundleContext.registerService(
			FileInstaller.class, new DefaultJarInstaller(), null);

		_tracker = new Tracker(bundleContext, this);

		_tracker.open();

		Map<String, String> map = new HashMap<>();

		_set(map, DirectoryWatcher.POLL);
		_set(map, DirectoryWatcher.DIR);
		_set(map, DirectoryWatcher.FILTER);
		_set(map, DirectoryWatcher.TMPDIR);
		_set(map, DirectoryWatcher.START_NEW_BUNDLES);
		_set(map, DirectoryWatcher.USE_START_TRANSIENT);
		_set(map, DirectoryWatcher.USE_START_ACTIVATION_POLICY);
		_set(map, DirectoryWatcher.NO_INITIAL_DELAY);
		_set(map, DirectoryWatcher.DISABLE_CONFIG_SAVE);
		_set(map, DirectoryWatcher.ENABLE_CONFIG_SAVE);
		_set(map, DirectoryWatcher.CONFIG_ENCODING);
		_set(map, DirectoryWatcher.START_LEVEL);
		_set(map, DirectoryWatcher.ACTIVE_LEVEL);
		_set(map, DirectoryWatcher.UPDATE_WITH_LISTENERS);
		_set(map, DirectoryWatcher.OPTIONAL_SCOPE);
		_set(map, DirectoryWatcher.FRAGMENT_SCOPE);
		_set(map, DirectoryWatcher.SUBDIR_MODE);
		_set(map, DirectoryWatcher.WEB_START_LEVEL);

		Set<String> dirs = new HashSet<>(
			Arrays.asList(StringUtil.split(map.get(DirectoryWatcher.DIR))));

		for (String dir : dirs) {
			map.put(DirectoryWatcher.DIR, dir);

			_updated(new HashMap<>(map));
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

		_tracker.close();

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

	private void _updated(Map<String, String> properties) {
		InterpolationUtil.performSubstitution(properties, _bundleContext);

		DirectoryWatcher directoryWatcher = new DirectoryWatcher(
			properties, _bundleContext);

		directoryWatcher.setDaemon(true);

		_directoryWatchers.add(directoryWatcher);
	}

	private BundleContext _bundleContext;
	private final Set<DirectoryWatcher> _directoryWatchers = new HashSet<>();
	private ServiceRegistration<FileInstaller>
		_jarFileInstallerServiceRegistration;
	private Tracker _tracker;

	private class Tracker
		extends ServiceTracker<ConfigurationAdmin, ConfigInstaller> {

		@Override
		public ConfigInstaller addingService(
			ServiceReference<ConfigurationAdmin> serviceReference) {

			ConfigurationAdmin configurationAdmin = context.getService(
				serviceReference);

			ConfigInstaller configInstaller = new ConfigInstaller(
				context, configurationAdmin, _fileInstall);

			configInstaller.init();

			return configInstaller;
		}

		@Override
		public void removedService(
			ServiceReference<ConfigurationAdmin> serviceReference,
			ConfigInstaller configInstaller) {

			configInstaller.destroy();

			context.ungetService(serviceReference);
		}

		private Tracker(
			BundleContext bundleContext,
			FileInstallImplBundleActivator fileInstall) {

			super(bundleContext, ConfigurationAdmin.class.getName(), null);

			_fileInstall = fileInstall;
		}

		private final FileInstallImplBundleActivator _fileInstall;

	}

}