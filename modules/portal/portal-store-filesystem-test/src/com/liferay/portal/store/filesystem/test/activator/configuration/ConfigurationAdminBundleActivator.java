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

package com.liferay.portal.store.filesystem.test.activator.configuration;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Manuel de la Peña
 */
public class ConfigurationAdminBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			_advancedFileSystemConfiguration = _getConfiguration(
				bundleContext, serviceReference,
				_ADVANCED_FILE_SYSTEM_CONFIGURATION_PID);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("rootDir", _ADVANCED_ROOT_DIR);

			_advancedFileSystemConfiguration.update(properties);

			_fileSystemConfiguration = _getConfiguration(
				bundleContext, serviceReference,
				_FILE_SYSTEM_CONFIGURATION_PID);

			properties = new Hashtable<>();

			properties.put("rootDir", _ROOT_DIR);

			_fileSystemConfiguration.update(properties);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		try {
			_advancedFileSystemConfiguration.delete();

			_fileSystemConfiguration.delete();

			FileUtil.deltree(_ADVANCED_ROOT_DIR);

			FileUtil.deltree(_ROOT_DIR);
		}
		catch (Exception e) {
		}
	}

	private Configuration _getConfiguration(
			BundleContext bundleContext,
			ServiceReference<ConfigurationAdmin> serviceReference,
			String configurationPid)
		throws Exception {

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		return configurationAdmin.getConfiguration(configurationPid, null);
	}

	private Configuration _advancedFileSystemConfiguration;
	private Configuration _fileSystemConfiguration;

	private static final String _ADVANCED_FILE_SYSTEM_CONFIGURATION_PID =
		"com.liferay.portal.store.filesystem.configuration." +
			"AdvancedFileSystemConfiguration";

	private static final String _ADVANCED_ROOT_DIR =
		PropsUtil.get(PropsKeys.LIFERAY_HOME) + "/advanced-filesystem";

	private static final String _FILE_SYSTEM_CONFIGURATION_PID =
		"com.liferay.portal.store.filesystem.configuration." +
			"FileSystemConfiguration";

	private static final String _ROOT_DIR =
		PropsUtil.get(PropsKeys.LIFERAY_HOME) + "/filesystem";

}