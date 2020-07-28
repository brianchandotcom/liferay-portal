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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.file.install.internal.properties.InterpolationUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

	public Iterable<FileInstaller> getFileInstallers() {
		return _fileInstallers;
	}

	public Lock getReadLock() {
		return _readLock;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_bundleContext = bundleContext;

		_jarFileInstallerServiceRegistration = _bundleContext.registerService(
			FileInstaller.class, new DefaultJarInstaller(), null);

		_fileInstallers = ServiceTrackerListFactory.open(
			_bundleContext, FileInstaller.class, null,
			new ServiceTrackerCustomizer<FileInstaller, FileInstaller>() {

				@Override
				public FileInstaller addingService(
					ServiceReference<FileInstaller> serviceReference) {

					return _bundleContext.getService(serviceReference);
				}

				@Override
				public void modifiedService(
					ServiceReference<FileInstaller> serviceReference,
					FileInstaller fileInstaller) {
				}

				@Override
				public void removedService(
					ServiceReference<FileInstaller> serviceReference,
					FileInstaller fileInstaller) {

					_bundleContext.ungetService(serviceReference);

					List<DirectoryWatcher> toNotify = new ArrayList<>();

					synchronized (_watchers) {
						toNotify.addAll(_watchers.values());
					}

					for (DirectoryWatcher directoryWatcher : toNotify) {
						directoryWatcher.removeFileInstaller(fileInstaller);
					}
				}

			});

		_writeLock.lock();

		try {
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

			String dirs = map.get(DirectoryWatcher.DIR);

			if ((dirs != null) && (dirs.indexOf(CharPool.COMMA) != -1)) {
				int index = 0;

				for (String dir : StringUtil.split(dirs, CharPool.COMMA)) {
					map.put(DirectoryWatcher.DIR, dir);

					String name = "initial";

					if (index > 0) {
						name = name + index;
					}

					_updated(name, new HashMap<>(map));

					index++;
				}
			}
			else {
				_updated("initial", map);
			}
		}
		finally {
			_writeLock.unlock();
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_writeLock.lock();

		try {
			List<DirectoryWatcher> watchers = new ArrayList<>();

			synchronized (_watchers) {
				watchers.addAll(_watchers.values());

				_watchers.clear();
			}

			for (DirectoryWatcher directoryWatcher : watchers) {
				try {
					directoryWatcher.close();
				}
				catch (Exception exception) {
				}
			}

			_tracker.close();
		}
		finally {
			_writeLock.unlock();
		}

		_fileInstallers.close();

		_jarFileInstallerServiceRegistration.unregister();
	}

	public void updateChecksum(File file) {
		List<DirectoryWatcher> toUpdate = new ArrayList<>();

		synchronized (_watchers) {
			toUpdate.addAll(_watchers.values());
		}

		for (DirectoryWatcher directoryWatcher : toUpdate) {
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

	private void _updated(String pid, Map<String, String> properties) {
		InterpolationUtil.performSubstitution(properties, _bundleContext);

		DirectoryWatcher directoryWatcher = null;

		synchronized (_watchers) {
			directoryWatcher = _watchers.get(pid);

			if ((directoryWatcher != null) &&
				Objects.equals(directoryWatcher.getProperties(), properties)) {

				return;
			}
		}

		if (directoryWatcher != null) {
			directoryWatcher.close();
		}

		directoryWatcher = new DirectoryWatcher(
			this, properties, _bundleContext);

		directoryWatcher.setDaemon(true);

		synchronized (_watchers) {
			_watchers.put(pid, directoryWatcher);
		}

		directoryWatcher.start();
	}

	private BundleContext _bundleContext;
	private ServiceTrackerList<FileInstaller, FileInstaller> _fileInstallers;
	private ServiceRegistration<FileInstaller>
		_jarFileInstallerServiceRegistration;
	private final ReadWriteLock _lock = new ReentrantReadWriteLock();
	private final Lock _readLock = _lock.readLock();
	private Tracker _tracker;
	private final Map<String, DirectoryWatcher> _watchers = new HashMap<>();
	private final Lock _writeLock = _lock.writeLock();

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