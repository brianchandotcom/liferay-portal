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

package com.liferay.portal.configurator.extender.internal;

import com.liferay.portal.configurator.extender.Configuration;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;

import java.util.Dictionary;

import org.apache.felix.utils.extender.Extension;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.ConfigurationAdmin;

/**
* @author Carlos Sierra Andrés
*/
public class ConfiguratorExtension implements Extension {

	public ConfiguratorExtension(
		ConfigurationAdmin configurationAdmin, String namespace,
		Configuration... configurations) {

		_configurationAdmin = configurationAdmin;
		_namespace = namespace;
		_configurations = configurations;
	}

	@Override
	public void destroy() throws Exception {
	}

	@Override
	public void start() throws Exception {
		for (Configuration configuration : _configurations) {
			try {
				if (configuration.getFactoryPid() == null) {
					_processConfiguration(configuration);
				}
				else {
					_processFactoryConfiguration(configuration);
				}
			}
			catch (IOException ioe) {
				continue;
			}
		}
	}

	private boolean _configurationExists(String filter)
		throws InvalidSyntaxException, IOException {

		org.osgi.service.cm.Configuration[] configurations =
			_configurationAdmin.listConfigurations(filter);

		if (ArrayUtil.isNotEmpty(configurations)) {
			return true;
		}

		return false;
	}

	private void _processConfiguration(Configuration configuration)
		throws InvalidSyntaxException, IOException {

		String pid = configuration.getPid();

		if (_configurationExists("(service.pid=" + pid + ")")) {
			return;
		}

		org.osgi.service.cm.Configuration osgiConfiguration =
			_configurationAdmin.getConfiguration(pid, null);

		osgiConfiguration.update(configuration.getProperties());
	}

	private void _processFactoryConfiguration(Configuration configuration)
		throws InvalidSyntaxException, IOException {

		String factoryPid = configuration.getFactoryPid();
		String pid = configuration.getPid();

		String configuratorUrl = _namespace + "#" + pid;

		if (_configurationExists(
				"(configurator.url=" + configuratorUrl + ")")) {

			return;
		}

		org.osgi.service.cm.Configuration osgiConfiguration =
			_configurationAdmin.createFactoryConfiguration(factoryPid, null);

		Dictionary<String, Object> properties = configuration.getProperties();

		properties.put("configurator.url", configuratorUrl);

		osgiConfiguration.update(properties);
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final Configuration[] _configurations;
	private final String _namespace;

}