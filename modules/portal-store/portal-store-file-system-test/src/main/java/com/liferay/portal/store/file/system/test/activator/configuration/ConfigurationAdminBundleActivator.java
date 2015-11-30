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

package com.liferay.portal.store.file.system.test.activator.configuration;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portlet.documentlibrary.store.Store;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Manuel de la Peña
 */
public class ConfigurationAdminBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			Configuration advancedFileSystemStoreConfiguration =
				getConfiguration(
					bundleContext, serviceReference,
					"com.liferay.portal.store.file.system.configuration." +
						"AdvancedFileSystemStoreConfiguration");

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("rootDir", _ADVANCED_ROOT_DIR);

			advancedFileSystemStoreConfiguration.update(properties);

			waitForService(
				bundleContext, advancedFileSystemStoreConfiguration,
				"com.liferay.portal.store.file.system.AdvancedFileSystemStore");

			Configuration fileSystemStoreConfiguration = getConfiguration(
				bundleContext, serviceReference,
				"com.liferay.portal.store.file.system.configuration." +
					"FileSystemStoreConfiguration");

			properties = new Hashtable<>();

			properties.put("rootDir", _ROOT_DIR);

			fileSystemStoreConfiguration.update(properties);

			waitForService(
				bundleContext, fileSystemStoreConfiguration,
				"com.liferay.portal.store.file.system.FileSystemStore");
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			Configuration advancedFileSystemStoreConfiguration =
				getConfiguration(
					bundleContext, serviceReference,
					"com.liferay.portal.store.file.system.configuration." +
						"AdvancedFileSystemStoreConfiguration");

			advancedFileSystemStoreConfiguration.delete();

			FileUtil.deltree(_ADVANCED_ROOT_DIR);

			Configuration fileSystemStoreConfiguration = getConfiguration(
				bundleContext, serviceReference,
				"com.liferay.portal.store.file.system.configuration." +
					"FileSystemStoreConfiguration");

			fileSystemStoreConfiguration.delete();

			FileUtil.deltree(_ROOT_DIR);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	protected Configuration getConfiguration(
			BundleContext bundleContext,
			ServiceReference<ConfigurationAdmin> serviceReference,
			String configurationPid)
		throws Exception {

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		return configurationAdmin.getConfiguration(configurationPid, null);
	}

	protected void waitForService(
			BundleContext bundleContext, Configuration configuration,
			String storeType)
		throws Exception {

		ServiceTracker<?, ?> serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			"(&(objectClass=" + Store.class.getName() + ")(store.type=" +
				storeType + "))");

		Object service = serviceTracker.waitForService(10000);

		serviceTracker.close();

		if (service == null) {
			configuration.delete();

			throw new IllegalStateException(
				"Service " + storeType +
					" was not registered within 10 seconds");
		}
	}

	private static final String _ADVANCED_ROOT_DIR =
		PropsUtil.get(PropsKeys.LIFERAY_HOME) +
			"/test/store/advanced_file_system";

	private static final String _ROOT_DIR =
		PropsUtil.get(PropsKeys.LIFERAY_HOME) + "/test/store/file_system";

}